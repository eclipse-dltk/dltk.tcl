package org.eclipse.dltk.itcl.internal.core.parser.ast;

import org.eclipse.dltk.ast.declarations.FieldDeclaration;
import org.eclipse.dltk.ast.expressions.CallArgumentsList;
import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.ast.references.SimpleReference;

public class IncrTclMethodCallStatement extends CallExpression {
	private FieldDeclaration instanceVariable;

	public IncrTclMethodCallStatement(int start, int end, SimpleReference name,
			FieldDeclaration var, CallArgumentsList args) {
		super(start, end, var, name, args);
		this.instanceVariable = var;
	}

	public int getKind() {
		return 0;
	}

	public FieldDeclaration getInstanceVariable() {
		return instanceVariable;
	}

	public void setInstNameRef(SimpleReference at) {
		this.receiver = at;
	}

	public SimpleReference getInstNameRef() {
		return ((SimpleReference) this.getReceiver());
	}
}
