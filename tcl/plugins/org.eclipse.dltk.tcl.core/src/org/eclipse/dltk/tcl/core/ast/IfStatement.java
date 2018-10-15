/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *

 *******************************************************************************/
package org.eclipse.dltk.tcl.core.ast;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.utils.CorePrinter;

/**
 * If statement.
 */
public class IfStatement extends Statement {
	/**
	 * Condition expression.
	 */
	private ASTNode fCondition;

	/**
	 * Then statement of if.
	 */
	private Statement fThenStatement;

	/**
	 * Else statement of if. Can be null, or possible EmptyStatement..
	 */
	private ASTNode fElseStatement;

	public IfStatement(int start, int end) {
		super(start, end);
	}

	public IfStatement(Statement condition, Statement thenStatement,
			Statement elseStatement) {
		this.fCondition = condition;
		this.fThenStatement = thenStatement;
		this.fElseStatement = elseStatement;
	}

	public void traverse(ASTVisitor pVisitor) throws Exception {

		if (pVisitor.visit(this)) {
			if (this.fCondition != null) {
				this.fCondition.traverse(pVisitor);
			}
			if (this.fThenStatement != null) {
				this.fThenStatement.traverse(pVisitor);
			}
			if (this.fElseStatement != null) {
				this.fElseStatement.traverse(pVisitor);
			}
			pVisitor.endvisit(this);
		}
	}

	public int getKind() {
		return S_IF;
	}

	/**
	 * Acccept Else statement.
	 * 
	 * @param elseStatement
	 */
	public void acceptElse(ASTNode elseStatement) {
		this.fElseStatement = elseStatement;
		if (this.fElseStatement != null) {
			this.setEnd(this.fElseStatement.sourceEnd());
		}
	}

	public void acceptThen(Statement statement) {
		this.fThenStatement = statement;
	}

	public void acceptCondition(ASTNode condition) {
		this.fCondition = condition;
	}

	/**
	 * Return else statement.
	 * 
	 * @return - else statement. Be aware can be null.
	 */
	public ASTNode getElse() {
		return this.fElseStatement;
	}

	public Statement getThen() {
		return this.fThenStatement;
	}

	public ASTNode getCondition() {
		return this.fCondition;
	}

	public void printNode(CorePrinter output) {
		output.formatPrintLn("if: "); //$NON-NLS-1$
		if (this.fCondition != null) {
			this.fCondition.printNode(output);
		}
		if (this.fThenStatement != null) {
			if (!(this.fThenStatement instanceof Block)) {
				output.indent();
			}
			this.fThenStatement.printNode(output);
			if (!(this.fThenStatement instanceof Block)) {
				output.dedent();
			}
		}
		if (this.fElseStatement != null) {
			output.formatPrintLn("else:"); //$NON-NLS-1$
			if (!(this.fElseStatement instanceof Block)) {
				output.indent();
			}
			this.fElseStatement.printNode(output);
			if (!(this.fElseStatement instanceof Block)) {
				output.dedent();
			}
		}

	}
}
