/*******************************************************************************
 * Copyright (c) 2008 xored software, Inc.  
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html  
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Andrei Sobolev)
 *******************************************************************************/

package org.eclipse.dltk.tcl.parser.tests;

import java.util.List;

import junit.framework.TestCase;

import org.eclipse.dltk.tcl.ast.Script;
import org.eclipse.dltk.tcl.ast.TclArgument;
import org.eclipse.dltk.tcl.ast.TclCommand;
import org.eclipse.dltk.tcl.definitions.ArgumentType;
import org.eclipse.dltk.tcl.definitions.Command;
import org.eclipse.dltk.tcl.definitions.Constant;
import org.eclipse.dltk.tcl.definitions.DefinitionsFactory;
import org.eclipse.dltk.tcl.definitions.Group;
import org.eclipse.dltk.tcl.definitions.TypedArgument;
import org.eclipse.dltk.tcl.parser.TclErrorCollector;
import org.eclipse.dltk.tcl.parser.TclParser;
import org.eclipse.emf.common.util.EList;

public class GroupParseTests extends TestCase {
	public Command createConstantsCommand() throws Exception {
		DefinitionsFactory factory = DefinitionsFactory.eINSTANCE;

		Command command = factory.createCommand();

		command.setName("group");
		{
			Group group = factory.createGroup();
			group.setConstant(null);
			group.setLowerBound(0);
			group.setUpperBound(-1);
			{
				Constant arg = factory.createConstant();
				arg.setLowerBound(1);
				arg.setUpperBound(1);
				arg.setName("const");
				group.getArguments().add(arg);
			}
			{
				TypedArgument arg = factory.createTypedArgument();
				arg.setType(ArgumentType.ANY);
				arg.setLowerBound(1);
				arg.setUpperBound(1);
				arg.setName("varName");
				group.getArguments().add(arg);
			}
			{
				TypedArgument arg = factory.createTypedArgument();
				arg.setType(ArgumentType.ANY);
				arg.setLowerBound(1);
				arg.setUpperBound(1);
				arg.setName("varName");
				group.getArguments().add(arg);
			}
			command.getArguments().add(group);
		}
		return command;
	}

	public void test001() throws Exception {
		String source = "group";
		typedCheck(source, 0, 0);
	}

	public void test002() throws Exception {
		String source = "group const val";
		typedCheck(source, 1, 0);
	}

	public void test003() throws Exception {
		String source = "group const val val";
		typedCheck(source, 0, 0);
	}

	public void test004() throws Exception {
		String source = "group val val";
		typedCheck(source, 1, 0);
	}

	public void test005() throws Exception {
		String source = "group const val val val";
		typedCheck(source, 1, 0);
	}

	public void test006() throws Exception {
		String source = "group const val val const val val";
		typedCheck(source, 0, 0);
	}

	private void typedCheck(String source, int errs, int code) throws Exception {
		TclParser parser = TestUtils.createParser();
		TestScopeProcessor manager = new TestScopeProcessor();
		TclErrorCollector errors = new TclErrorCollector();
		manager.add(createConstantsCommand());
		List<TclCommand> module = parser.parse(source, errors, manager);
		TestCase.assertEquals(1, module.size());
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
		TestCase.assertEquals(code, scripts);
		TestCase.assertEquals(errs, errors.getCount());
	}
}
