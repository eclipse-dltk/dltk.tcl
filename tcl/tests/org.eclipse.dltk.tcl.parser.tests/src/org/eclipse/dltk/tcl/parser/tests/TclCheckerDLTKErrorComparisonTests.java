/*******************************************************************************
 * Copyright (c) 2008, 2017 xored software, Inc. and others.
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

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.environment.EnvironmentManager;
import org.eclipse.dltk.core.environment.IEnvironment;
import org.eclipse.dltk.core.environment.IExecutionEnvironment;
import org.eclipse.dltk.tcl.internal.tclchecker.Checker4OutputProcessor;
import org.eclipse.dltk.tcl.internal.tclchecker.TclChecker;
import org.eclipse.dltk.tcl.internal.tclchecker.TclCheckerConfigUtils;
import org.eclipse.dltk.tcl.internal.tclchecker.TclCheckerConfigUtils.ValidatorInstanceResponse;
import org.eclipse.dltk.tcl.internal.tclchecker.TclCheckerProblem;
import org.eclipse.dltk.tcl.parser.ITclErrorReporter;
import org.eclipse.dltk.tcl.parser.ITclParserOptions;
import org.eclipse.dltk.tcl.parser.TclErrorCollector;
import org.eclipse.dltk.tcl.parser.TclParser;
import org.eclipse.dltk.tcl.parser.definitions.DefinitionManager;
import org.eclipse.dltk.tcl.parser.definitions.NamespaceScopeProcessor;
import org.eclipse.dltk.tcl.parser.internal.tests.Activator;
import org.eclipse.dltk.tcl.parser.tests.TestUtils.CodeModel;
import org.eclipse.dltk.validators.core.NullValidatorOutput;
import org.junit.Test;
import org.osgi.framework.Bundle;

public class TclCheckerDLTKErrorComparisonTests {
	IProject project;

	@Test
	public void test001() throws Exception {
		String fileName = "main1.tcl";

		Bundle bundle = Platform
				.getBundle("org.eclipse.dltk.tcl.parser.tests.ats");
		IPath loc = Activator.getDefault().getStateLocation();
		TestUtils.exractFilesInto(loc.toOSString(),
				bundle.getEntry("/alfa.zip"), new String[] { fileName });

		String fileLoc = loc.append(fileName).toOSString();
		final String contents = TestUtils
				.getContents(new FileInputStream(fileLoc));
		CodeModel model = new CodeModel(contents);
		List<TclCheckerProblem> list = check(fileLoc);
		TclParser parser = TestUtils.createParser();
		NamespaceScopeProcessor processor = DefinitionManager.getInstance()
				.createProcessor();
		parser.setOptionValue(ITclParserOptions.REPORT_UNKNOWN_AS_ERROR, false);
		TclErrorCollector col = new TclErrorCollector();

		/* List<TclCommand> commands = */parser.parse(contents, col, processor);

		System.out.println("-----------------source----------------------\n");
		col.reportAll(
				(code, message, extraMessage, start, end, kind) -> System.out
						.println((kind == ITclErrorReporter.ERROR ? "Error:"
								: "Warning/Info:") + code + " (" + start + ","
								+ end + ") message:" + message + "\n"
								+ contents.substring(start, end)));
		System.out.println("=============================================");
		for (TclCheckerProblem tclCheckerProblem : list) {
			System.out.println();
			int[] bounds = model
					.getBounds(tclCheckerProblem.getLineNumber() - 1);
			System.out.println(
					(tclCheckerProblem.isError() ? "Error:" : "Warning/Info:")
							+ tclCheckerProblem.getMessage() + " (" + bounds[0]
							+ "," + bounds[1] + ") message:"
							+ tclCheckerProblem.getExplanation() + "\n"
							+ contents.substring(bounds[0], bounds[1]));
		}
	}

	public List<TclCheckerProblem> check(String file) {
		final List<TclCheckerProblem> problems = new ArrayList<>();
		final IEnvironment environment = EnvironmentManager
				.getLocalEnvironment();
		final String[] cmdLine = new String[] { getTclChecker(), file };
		try {
			final Checker4OutputProcessor processor = new Checker4OutputProcessor(
					new NullProgressMonitor(), new NullValidatorOutput(),
					(module, problem) -> problems.add(problem)) {

				@Override
				protected boolean isValidModule() {
					return true;
				}
			};
			final ValidatorInstanceResponse response = TclCheckerConfigUtils
					.getConfiguration(environment, TclCheckerConfigUtils.ALL);
			if (!response.isEmpty()) {
				TclChecker checker = new TclChecker(
						response.instances.get(0).environmentInstance,
						response.instances.get(0).config, environment);
				IExecutionEnvironment execEnvironment = environment
						.getAdapter(IExecutionEnvironment.class);
				checker.executeProcess(processor, execEnvironment, cmdLine);
			}
		} catch (Exception e) {
			if (DLTKCore.DEBUG) {
				e.printStackTrace();
			}
		}
		return problems;
	}

	private String getTclChecker() {
		return "/home/dltk/apps/bin/tclchecker";
	}
}
