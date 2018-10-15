/*******************************************************************************
 * Copyright (c) 2008 xored software, Inc.  
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

package org.eclipse.dltk.tcl.validators.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.tests.model.AbstractModelTests;
import org.eclipse.dltk.tcl.ast.TclCommand;
import org.eclipse.dltk.tcl.internal.validators.ICheckKinds;
import org.eclipse.dltk.tcl.internal.validators.TclCheckBuildParticipant;
import org.eclipse.dltk.tcl.internal.validators.TclValidatorTestsPlugin;
import org.eclipse.dltk.tcl.internal.validators.checks.UndefinedProcCheck;
import org.eclipse.dltk.tcl.parser.ITclParserOptions;
import org.eclipse.dltk.tcl.parser.TclError;
import org.eclipse.dltk.tcl.parser.TclErrorCollector;
import org.eclipse.dltk.tcl.parser.TclParser;
import org.eclipse.dltk.tcl.parser.definitions.DefinitionManager;
import org.eclipse.dltk.tcl.parser.definitions.NamespaceScopeProcessor;
import org.eclipse.dltk.tcl.parser.tests.TestUtils;

public class CheckMethodExistanceTest extends AbstractModelTests {
	private static final String CHECK_PROC_EXISTS_NAME = "CheckProcExists";
	private IProject project = null;

	public CheckMethodExistanceTest() {
		super(TclValidatorTestsPlugin.PLUGIN_ID, "CheckMethodExistanceTest");
	}

	public void setUpSuite() throws Exception {
		super.setUpSuite();
		TclCheckBuildParticipant.TESTING_DO_CHECKS = false;
		init();
	}

	private void init() throws CoreException, IOException {
		if (this.project == null) {
			this.project = setUpProject(CHECK_PROC_EXISTS_NAME);
		}
		waitForAutoBuild();
		waitUntilIndexesReady();
	}

	public void tearDownSuite() throws Exception {
		super.tearDownSuite();
		deleteProject(CHECK_PROC_EXISTS_NAME);
	}

	public void testMethodExistsCheck001() throws Exception {
		ISourceModule module = getSourceModule("/CheckProcExists/src/src1.tcl");
		TestCase.assertNotNull(module);
		TestCase.assertTrue(module.exists());
		UndefinedProcCheck check = new UndefinedProcCheck();

		TclParser parser = TestUtils.createParser();
		TclErrorCollector errorCollector = new TclErrorCollector();
		NamespaceScopeProcessor processor = DefinitionManager.getInstance()
				.createProcessor();
		parser.setOptionValue(ITclParserOptions.REPORT_UNKNOWN_AS_ERROR, false);
		List<TclCommand> commands = parser.parse(module.getSource(),
				errorCollector, processor);
		TestCase.assertEquals(0, errorCollector.getCount());
		check.checkCommands(commands, errorCollector,
				new HashMap<String, String>(), module.getScriptProject(), null);
		TestCase.assertEquals(1, errorCollector.getCount());
		TclError error = errorCollector.getErrors()[0];
		TestCase
				.assertEquals(error.getCode(), ICheckKinds.CHECK_UNDEFINED_PROC);
	}

	public void testMethodExistsCheck002() throws Exception {
		ISourceModule module = getSourceModule("/CheckProcExists/src/src2.tcl");
		TestCase.assertNotNull(module);
		TestCase.assertTrue(module.exists());
		UndefinedProcCheck check = new UndefinedProcCheck();

		TclParser parser = TestUtils.createParser();
		TclErrorCollector errorCollector = new TclErrorCollector();
		NamespaceScopeProcessor processor = DefinitionManager.getInstance()
				.createProcessor();
		parser.setOptionValue(ITclParserOptions.REPORT_UNKNOWN_AS_ERROR, false);
		List<TclCommand> commands = parser.parse(module.getSource(),
				errorCollector, processor);
		TestCase.assertEquals(0, errorCollector.getCount());
		check.checkCommands(commands, errorCollector,
				new HashMap<String, String>(), module.getScriptProject(), null);
		TestCase.assertEquals(4, errorCollector.getCount());
		TclError[] errors = errorCollector.getErrors();
		for (int i = 0; i < errors.length; i++) {
			TclError error = errors[i];
			TestCase.assertEquals(error.getCode(),
					ICheckKinds.CHECK_UNDEFINED_PROC);
		}
	}
}