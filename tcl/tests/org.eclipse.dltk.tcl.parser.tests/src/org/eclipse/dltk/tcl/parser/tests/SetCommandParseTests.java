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

package org.eclipse.dltk.tcl.parser.tests;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.eclipse.dltk.tcl.ast.Script;
import org.eclipse.dltk.tcl.ast.TclArgument;
import org.eclipse.dltk.tcl.ast.TclCommand;
import org.eclipse.dltk.tcl.definitions.ArgumentType;
import org.eclipse.dltk.tcl.definitions.Command;
import org.eclipse.dltk.tcl.definitions.DefinitionsFactory;
import org.eclipse.dltk.tcl.definitions.TypedArgument;
import org.eclipse.dltk.tcl.parser.TclErrorCollector;
import org.eclipse.dltk.tcl.parser.TclParser;
import org.eclipse.emf.common.util.EList;
import org.junit.Test;

public class SetCommandParseTests {
	public Command createConstantsCommand() {
		DefinitionsFactory factory = DefinitionsFactory.eINSTANCE;

		Command command = factory.createCommand();

		command.setName("set");
		{
			TypedArgument arg = factory.createTypedArgument();
			arg.setType(ArgumentType.VAR_NAME);
			arg.setLowerBound(1);
			arg.setUpperBound(1);
			arg.setName("varName");
			command.getArguments().add(arg);
		}
		{
			TypedArgument arg = factory.createTypedArgument();
			arg.setType(ArgumentType.ANY);
			arg.setName("value");
			arg.setLowerBound(0);
			arg.setUpperBound(1);
			command.getArguments().add(arg);
		}
		return command;
	}

	@Test
	public void test001() {
		String source = "set var value";
		typedCheck(source, 0, 0);
	}

	@Test
	public void test002() {
		String source = "set var";
		typedCheck(source, 0, 0);
	}

	@Test
	public void test003() {
		String source = "set var value value";
		typedCheck(source, 1, 0);
	}

	/*
	 * public void test004() throws Exception { String source =
	 * "set {var} value"; typedCheck(source, 1, 1); }
	 */

	private void typedCheck(String source, int errs, int code) {
		TclParser parser = TestUtils.createParser();
		TestScopeProcessor manager = new TestScopeProcessor();
		TclErrorCollector errors = new TclErrorCollector();
		manager.add(createConstantsCommand());
		List<TclCommand> module = parser.parse(source, errors, manager);
		assertEquals(1, module.size());
		TclCommand tclCommand = module.get(0);
		EList<TclArgument> arguments = tclCommand.getArguments();
		int scripts = 0;
		for (int i = 0; i < arguments.size(); i++) {
			if (arguments.get(i) instanceof Script) {
				scripts++;
			}
		}
		if (errors.getCount() > 0) {
			TestUtils.outErrors(source, errors);
		}
		assertEquals(code, scripts);
		assertEquals(errs, errors.getCount());
	}
}
