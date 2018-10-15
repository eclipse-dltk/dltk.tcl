/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.tcl.ast.expressions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.compiler.env.ModuleSource;
import org.eclipse.dltk.core.DLTKLanguageManager;
import org.eclipse.dltk.tcl.ast.TclConstants;
import org.eclipse.dltk.tcl.core.ITclSourceParser;
import org.eclipse.dltk.tcl.core.TclNature;

public class TclExecuteExpression extends Expression {
	private String fExecuteContent;

	public TclExecuteExpression(int start, int end, String content) {
		this.setStart(start);
		this.setEnd(end);
		this.fExecuteContent = content;
	}

	@Override
	public int getKind() {
		return TclConstants.TCL_EXECUTE_EXPRESSION;
	}

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {
		if (visitor.visit(this)) {
			List statements = this.parseExpression();
			if (statements != null) {
				for (int i = 0; i < statements.size(); ++i) {
					ASTNode nde = (ASTNode) statements.get(i);
					nde.traverse(visitor);
				}
			}
			visitor.endvisit(this);
		}
	}

	public String getExpression() {
		return this.fExecuteContent;
	}

	public List parseExpression() {
		return this.parseExpression(this.sourceStart() + 1);
	}

	public List parseExpression(int startFrom) {
		if (this.fExecuteContent == null) {
			return null;
		}

		String content = null;
		if (this.fExecuteContent.length() >= 2) {
			content = this.fExecuteContent.substring(1,
					this.fExecuteContent.length() - 1);
		} else {
			return new ArrayList();
		}

		ITclSourceParser parser = (ITclSourceParser) DLTKLanguageManager
				.getSourceParser(TclNature.NATURE_ID);
		parser.setOffset(startFrom);
		ModuleDeclaration module = parser.parse(new ModuleSource(content),
				null);
		return module.getStatements();
	}

	@Override
	public String toString() {
		return this.fExecuteContent;
	}

}
