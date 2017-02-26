package org.eclipse.dltk.itcl.internal.core.parser;

import java.util.List;

import org.eclipse.dltk.ast.ASTListNode;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.Declaration;
import org.eclipse.dltk.ast.declarations.FieldDeclaration;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.itcl.internal.core.IIncrTclModifiers;
import org.eclipse.dltk.itcl.internal.core.parser.ast.IncrTclBodyDeclaration;
import org.eclipse.dltk.itcl.internal.core.parser.ast.IncrTclMethodCallStatement;
import org.eclipse.dltk.tcl.core.ast.ExtendedTclMethodDeclaration;
import org.eclipse.dltk.tcl.core.extensions.ISourceElementRequestVisitorExtension;
import org.eclipse.dltk.tcl.internal.parser.TclSourceElementRequestVisitor;
import org.eclipse.dltk.tcl.internal.parser.TclSourceElementRequestVisitor.ExitFromType;

public class IncrTclSourceElementRequestVisitorExtension implements ISourceElementRequestVisitorExtension {

	@Override
	public int getModifiers(Declaration s) {
		if ((s.getModifiers() & IIncrTclModifiers.AccIncrTcl) != 0) {
			// This is ordinary class.
			return s.getModifiers();
		}
		return 0;
	}

	@Override
	public boolean visit(Statement statement, TclSourceElementRequestVisitor original) {
		if (statement instanceof IncrTclMethodCallStatement) {
			IncrTclMethodCallStatement call = (IncrTclMethodCallStatement) statement;
			SimpleReference callName = call.getCallName();
			int len = 0;
			if (call.getArgs() != null) {
				ASTListNode arguments = call.getArgs();
				List<ASTNode> childs = arguments.getChilds();
				if (childs != null) {
					len = childs.size();
				}
			}

			original.getRequestor().acceptMethodReference(callName.getName(), len, call.sourceStart(),
					call.sourceEnd());
			return true;

			// Also lets add type references from here.
		}
		return false;
	}

	@Override
	public ExitFromType getExitExtended(MethodDeclaration method, TclSourceElementRequestVisitor original) {
		String tName = method.getDeclaringTypeName();
		if (tName == null) {
			tName = "";
		}
		return original.resolveType(method, tName + "::dummy", false);
	}

	@Override
	public boolean extendedExitRequired(MethodDeclaration method, TclSourceElementRequestVisitor original) {
		if (method instanceof IncrTclBodyDeclaration) {
			return true;
		}
		return false;// (method.getModifiers() & IIncrTclModifiers.AccIncrTcl)
		// != 0;
	}

	@Override
	public ExitFromType processField(FieldDeclaration decl, TclSourceElementRequestVisitor original) {
		// TclParseUtil.getScopeParent(decl,)
		MethodDeclaration method = original.getCurrentMethod();
		if (method != null && method instanceof IncrTclBodyDeclaration) {
			IncrTclBodyDeclaration body = (IncrTclBodyDeclaration) method;
			Declaration dtn = (Declaration) body.getDeclaringType();
			// return original.resolveType(dtn, body.getDeclaringTypeName() +
			// "::"
			// + body.getName(), false);
			return null;
		}
		return null;
	}

	@Override
	public boolean skipMethod(MethodDeclaration method, TclSourceElementRequestVisitor tclSourceElementRequestVisitor) {
		// if (method instanceof IncrTclBodyDeclaration) {
		// return true;
		// }
		return false;
	}

	private static final int MOD_CONSTRUCTOR = IIncrTclModifiers.AccIncrTcl | IIncrTclModifiers.AccConstructor;

	@Override
	public boolean isConstructor(MethodDeclaration declaration) {
		if (declaration instanceof ExtendedTclMethodDeclaration
				&& (declaration.getModifiers() & MOD_CONSTRUCTOR) == MOD_CONSTRUCTOR) {
			return true;
		}
		return false;
	}
}
