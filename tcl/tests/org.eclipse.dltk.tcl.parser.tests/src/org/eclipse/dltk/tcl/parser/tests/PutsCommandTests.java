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
import org.eclipse.dltk.tcl.parser.TclErrorCollector;
import org.eclipse.dltk.tcl.parser.TclParser;
import org.eclipse.dltk.tcl.parser.definitions.DefinitionManager;
import org.eclipse.dltk.tcl.parser.definitions.NamespaceScopeProcessor;
import org.eclipse.emf.common.util.EList;
import org.junit.Test;

public class PutsCommandTests {
	NamespaceScopeProcessor processor;

	@Test
	public void test001() {
		String source = "puts message";
		typedCheck(source, 0, 0);
	}

	@Test
	public void test002() {
		String source = "puts file0 message";
		typedCheck(source, 0, 0);
	}

	@Test
	public void test003() {
		String source = "puts -nonewline message";
		typedCheck(source, 0, 0);
	}

	@Test
	public void test004() {
		String source = "puts -nonewline file0 message";
		typedCheck(source, 0, 0);
	}

	@Test
	public void test005() {
		String source = "puts -nonewline";
		typedCheck(source, 1, 0);
	}

	@Test
	public void test006() {
		String source = "puts -nonewline file0";
		typedCheck(source, 0, 0);
	}

	@Test
	public void test007() {
		String source = "puts";
		typedCheck(source, 1, 0);
	}

	@Test
	public void test008() {
		String source = "puts -nonewline file0 msg0 msg1";
		typedCheck(source, 1, 0);
	}

	@Test
	public void test009() {
		String source = "puts msg0 msg1 msg2";
		typedCheck(source, 1, 0);
	}

	private void typedCheck(String source, int errs, int code) {
		processor = DefinitionManager.getInstance().createProcessor();
		TclParser parser = TestUtils.createParser("8.4");
		TclErrorCollector errors = new TclErrorCollector();
		List<TclCommand> module = parser.parse(source, errors, processor);
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
