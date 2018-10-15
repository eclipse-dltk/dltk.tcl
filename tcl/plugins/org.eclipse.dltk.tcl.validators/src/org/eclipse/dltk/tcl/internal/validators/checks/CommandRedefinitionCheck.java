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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.builder.ISourceLineTracker;
import org.eclipse.dltk.tcl.ast.StringArgument;
import org.eclipse.dltk.tcl.ast.TclArgument;
import org.eclipse.dltk.tcl.ast.TclCommand;
import org.eclipse.dltk.tcl.definitions.Command;
import org.eclipse.dltk.tcl.internal.validators.ICheckKinds;
import org.eclipse.dltk.tcl.parser.ITclErrorReporter;
import org.eclipse.dltk.tcl.parser.TclParserUtils;
import org.eclipse.dltk.tcl.parser.TclVisitor;
import org.eclipse.dltk.tcl.parser.definitions.DefinitionManager;
import org.eclipse.dltk.tcl.parser.definitions.IScopeProcessor;
import org.eclipse.dltk.tcl.validators.ITclCheck;
import org.eclipse.osgi.util.NLS;

public class CommandRedefinitionCheck implements ITclCheck {
	public CommandRedefinitionCheck() {
	}

	@Override
	public void checkCommands(final List<TclCommand> tclCommands,
			final ITclErrorReporter reporter, Map<String, String> options,
			IScriptProject project,
			final ISourceLineTracker sourceLineTracker) {
		final IScopeProcessor processor = DefinitionManager.getInstance()
				.createProcessor();
		TclParserUtils.traverse(tclCommands, new TclVisitor() {
			Map<String, Integer> userCommands = new HashMap<>();

			@Override
			public boolean visit(TclCommand tclCommand) {
				Assert.isNotNull(tclCommand);
				processor.processCommand(tclCommand);
				if (tclCommand.getDefinition() == null
						|| !tclCommand.isMatched()) {
					return true;
				}
				if ("proc".equals(tclCommand.getDefinition().getName())) {
					Assert.isLegal(tclCommand.getArguments().size() >= 3);
					TclArgument nameArgument = tclCommand.getArguments().get(0);
					if (nameArgument instanceof StringArgument) {
						String current = ((StringArgument) nameArgument)
								.getValue();
						current = processor.getQualifiedName(current);
						if (current.startsWith("::")) {
							current = current.substring(2);
						}
						Command[] definitions = processor
								.getCommandDefinition(current);
						if (definitions != null && definitions.length != 0)
							reporter.report(
									ICheckKinds.BUILTIN_COMMAND_REDEFINITION,
									"Built-in command redefinition", null,
									nameArgument.getStart(),
									nameArgument.getEnd(),
									ITclErrorReporter.WARNING);
						Set<String> names = userCommands.keySet();
						for (String name : names) {
							if (name.equals(current)) {
								final String msg = NLS.bind(
										"Procedure {0} is already defined on line {1}",
										name, userCommands.get(name));
								reporter.report(
										ICheckKinds.USER_COMMAND_REDEFINITION,
										msg, null, nameArgument.getStart(),
										nameArgument.getEnd(),
										ITclErrorReporter.WARNING);
							}
						}
						int start = tclCommand.getStart();
						userCommands.put(current,
								sourceLineTracker.getLineNumberOfOffset(start));
					}
				}
				return true;
			}

			@Override
			public void endVisit(TclCommand command) {
				processor.endProcessCommand();
			}
		});
	}
}
