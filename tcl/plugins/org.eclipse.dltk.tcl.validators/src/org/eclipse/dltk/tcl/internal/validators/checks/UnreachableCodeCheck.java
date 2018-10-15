/*******************************************************************************
 * Copyright (c) 2008, 2017 xored software, Inc. and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Andrei Sobolev)
 *******************************************************************************/

package org.eclipse.dltk.tcl.internal.validators.checks;

import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.builder.ISourceLineTracker;
import org.eclipse.dltk.tcl.ast.Node;
import org.eclipse.dltk.tcl.ast.Script;
import org.eclipse.dltk.tcl.ast.Substitution;
import org.eclipse.dltk.tcl.ast.TclCommand;
import org.eclipse.dltk.tcl.internal.validators.ICheckKinds;
import org.eclipse.dltk.tcl.parser.ITclErrorReporter;
import org.eclipse.dltk.tcl.parser.TclVisitor;
import org.eclipse.dltk.tcl.validators.ITclCheck;

public class UnreachableCodeCheck implements ITclCheck {

	public UnreachableCodeCheck() {
	}

	@Override
	public void checkCommands(final List<TclCommand> tclCommands,
			final ITclErrorReporter reporter, Map<String, String> options,
			IScriptProject project, ISourceLineTracker sourceLineTracker) {
		traverse(tclCommands, new TclVisitor() {
			private boolean check = true;
			private boolean error = false;

			@Override
			public boolean visit(TclCommand tclCommand) {
				Assert.isNotNull(tclCommand);
				if (!check)
					return false;
				if (error) {

					reporter.report(ICheckKinds.UNREACHABLE_CODE,
							"Unreachable code", null, tclCommand.getStart(),
							tclCommand.getEnd(), ITclErrorReporter.WARNING);
					error = check = false;
					return false;
				} else {
					if (tclCommand == null
							|| tclCommand.getDefinition() == null) {
						return true;
					}
					if ("return".equals(tclCommand.getDefinition().getName())) {
						error = true;
						return false;
					}
					return true;
				}
			}

			@Override
			public void endVisit(TclCommand tclCommand) {
				Assert.isNotNull(tclCommand);
				if (tclCommand != null && tclCommand.getDefinition() != null
						&& "return"
								.equals(tclCommand.getDefinition().getName())) {
					error = false;
				}
				check = true;
			}
		});
	}

	public <T> void traverse(List<T> nodes, TclVisitor visitor) {
		for (int i = 0; i < nodes.size(); i++) {
			Node nde = (Node) nodes.get(i);
			if (nde instanceof Script) {
				Script script = (Script) nde;
				if (visitor.visit(script)) {
					traverse(script.getCommands(), visitor);
					int size = script.getCommands().size();
					if (size != 0)
						visitor.endVisit(script.getCommands().get(size - 1));
				}
			} else if (nde instanceof Substitution) {
				Substitution substitution = (Substitution) nde;
				if (visitor.visit(substitution)) {
					traverse(substitution.getCommands(), visitor);
					int size = substitution.getCommands().size();
					if (size != 0)
						visitor.endVisit(
								substitution.getCommands().get(size - 1));
				}
			} else if (nde instanceof TclCommand) {
				TclCommand command = (TclCommand) nde;
				if (visitor.visit(command)) {
					traverse(command.getArguments(), visitor);
				}
			}
		}
	}
}