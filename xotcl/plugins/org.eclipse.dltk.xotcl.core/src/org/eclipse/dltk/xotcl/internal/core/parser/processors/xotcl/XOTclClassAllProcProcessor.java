package org.eclipse.dltk.xotcl.internal.core.parser.processors.xotcl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.Argument;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.compiler.problem.ProblemSeverities;
import org.eclipse.dltk.tcl.ast.TclStatement;
import org.eclipse.dltk.tcl.ast.expressions.TclBlockExpression;
import org.eclipse.dltk.tcl.ast.expressions.TclExecuteExpression;
import org.eclipse.dltk.tcl.core.AbstractTclCommandProcessor;
import org.eclipse.dltk.tcl.core.ITclParser;
import org.eclipse.dltk.tcl.core.ast.ExtendedTclMethodDeclaration;
import org.eclipse.dltk.tcl.internal.core.parser.processors.tcl.Messages;
import org.eclipse.dltk.tcl.internal.parser.OldTclParserUtils;
import org.eclipse.dltk.xotcl.core.IXOTclModifiers;
import org.eclipse.dltk.xotcl.core.ast.xotcl.XOTclExInstanceVariable;
import org.eclipse.dltk.xotcl.core.ast.xotcl.XOTclInstanceVariable;
import org.eclipse.dltk.xotcl.core.ast.xotcl.XOTclMethodDeclaration;
import org.eclipse.dltk.xotcl.core.ast.xotcl.XOTclObjectDeclaration;

/**
 * Process "#Class#instproc", "#Class#proc" and "#Object#proc", and instance
 * proc commands.
 * 
 * @author haiodo
 * 
 */

public class XOTclClassAllProcProcessor extends AbstractTclCommandProcessor {

	private static final String KIND_PROC_STR = "proc";
	private static final String KIND_INSTPROC_STR = "instproc";

	public XOTclClassAllProcProcessor() {
	}

	public ASTNode process(TclStatement statement, ITclParser parser,
			ASTNode parent) {
		ASTNode decl = null;

		if (getDetectedParameter() != null
				&& (getDetectedParameter() instanceof ASTNode)) {
			decl = (ASTNode) getDetectedParameter();
		}
		if (decl == null) {
			return null;
		}
		if (statement.getCount() < 5) {
			this.report(parser,
					Messages.TclProcProcessor_Wrong_Number_of_Arguments,
					statement.sourceStart(), statement.sourceEnd(),
					ProblemSeverities.Error);
			return null;
		}
		int index = 2;
		Expression procName = statement.getAt(index);

		String sName = null;
		sName = extractName(procName);

		if (sName == null || sName.length() == 0) {
			this.report(parser, Messages.TclProcProcessor_Empty_Proc_Name,
					statement.sourceStart(), statement.sourceEnd(),
					ProblemSeverities.Error);
			return null;
		}
		Expression procArguments = statement.getAt(index + 1);
		Expression procCode = statement.getAt(index + 2);

		List arguments = null;
		if (procArguments instanceof TclBlockExpression) {
			List/* < Statement > */st = null;

			st = ((TclBlockExpression) procArguments).parseBlockSimple();

			arguments = OldTclParserUtils.parseArguments(st);
		}
		if (procArguments instanceof SimpleReference) {
			arguments = new ArrayList();
			Argument a = new Argument((SimpleReference) procArguments,
					procArguments.sourceStart(), null, 0);
			arguments.add(a);
		}

		ExtendedTclMethodDeclaration method = new XOTclMethodDeclaration(
				statement.sourceStart(), statement.sourceEnd());
		method.setDeclaringType(decl);
		if (decl instanceof TypeDeclaration) {
			method.setDeclaringTypeName(((TypeDeclaration) decl).getName());
		} else if (decl instanceof XOTclInstanceVariable) {
			method.setDeclaringTypeName(((XOTclInstanceVariable) decl)
					.getName());
		} else if (decl instanceof XOTclExInstanceVariable) {
			String name = ((XOTclExInstanceVariable) decl)
					.getDeclaringClassParameter().getClassName();
			method.setDeclaringTypeName(name);
		}

		method.setModifiers(IXOTclModifiers.AccXOTcl);
		method.setName(sName);
		method.setNameStart(procName.sourceStart());
		method.setNameEnd(procName.sourceEnd());
		method.setTypeNameRef((SimpleReference) statement.getAt(0));
		method.acceptArguments(arguments);
		Block block = new Block(procCode.sourceStart(), procCode.sourceEnd());

		if (procCode instanceof Block) {
			block.getStatements().addAll(((Block) procCode).getStatements());
		} else if (procCode instanceof TclBlockExpression) {
			String content = ((TclBlockExpression) procCode).getBlock();
			if (content.startsWith("{") && content.endsWith("}")) {
				content = content.substring(1, content.length() - 1);
			}
			parser.parse(content, procCode.sourceStart() + 1, block);
		}
		method.acceptBody(block);
		this.addToParent(parent, method);

		if (decl instanceof TypeDeclaration) {
			((TypeDeclaration) decl).getMethodList().add(method);
		}
		/**
		 * Parse method kind.
		 */
		Expression kind = statement.getAt(1);
		String kindName = this.extractName(kind);
		if (kindName != null) {
			if (KIND_PROC_STR.equals(kindName)) {
				method.setKind(ExtendedTclMethodDeclaration.KIND_PROC);
			} else if (KIND_INSTPROC_STR.equals(kindName)) {
				method.setKind(ExtendedTclMethodDeclaration.KIND_INSTPROC);
				if (decl instanceof XOTclObjectDeclaration) {
					// report(parser, "Adding instproc to object", kind,
					// ProblemSeverities.Warning);
				}
			}
		}

		// parser.parse(content, procCode.sourceStart() + 1, block);
		return method;
	}

	private String extractName(Expression procName) {
		if (procName instanceof SimpleReference) {
			return ((SimpleReference) procName).getName();
		} else if (procName instanceof TclBlockExpression) {
			return ((TclBlockExpression) procName).getBlock();
		} else if (procName instanceof TclExecuteExpression) {
			return ((TclExecuteExpression) procName).getExpression();
		}
		return null;
	}
}
