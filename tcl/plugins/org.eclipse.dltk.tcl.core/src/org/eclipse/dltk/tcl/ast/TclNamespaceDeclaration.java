/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.tcl.ast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.dltk.ast.declarations.FieldDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.tcl.internal.parser.OldTclParserUtils;

public class TclNamespaceDeclaration extends TypeDeclaration {

	public TclNamespaceDeclaration(String name, int nameStart, int nameEnd,
			int start, int end) {
		super(name, nameStart, nameEnd, start, end);
	}

	@Override
	public FieldDeclaration[] getVariables() {
		List<String> variableNames = new ArrayList<>();
		List<FieldDeclaration> variableDeclarations = new ArrayList<>();
		List statements = this.getStatements();
		Iterator i = statements.iterator();
		while (i.hasNext()) {
			Object o = i.next();
			if (o instanceof TclStatement) {
				TclStatement statement = (TclStatement) o;
				FieldDeclaration[] decls = OldTclParserUtils
						.returnVariableDeclarations(statement);
				if (decls != null) {
					for (int j = 0; j < decls.length; ++j) {
						String name2 = decls[j].getName();
						if (!variableNames.contains(name2)) {
							variableNames.add(name2);
							variableDeclarations.add(decls[j]);
						}
					}
				}
			}
		}
		return variableDeclarations
				.toArray(new FieldDeclaration[variableDeclarations.size()]);
	}
}
