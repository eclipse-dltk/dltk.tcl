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

package org.eclipse.dltk.tcl.validators.internal.tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.dltk.tcl.ast.TclCommand;
import org.eclipse.dltk.tcl.internal.validators.ICheckKinds;
import org.eclipse.dltk.tcl.internal.validators.checks.UnreachableCodeCheck;
import org.eclipse.dltk.tcl.parser.TclError;
import org.eclipse.dltk.tcl.parser.TclErrorCollector;
import org.eclipse.dltk.tcl.parser.TclParser;
import org.eclipse.dltk.tcl.parser.definitions.DefinitionManager;
import org.eclipse.dltk.tcl.parser.definitions.NamespaceScopeProcessor;
import org.eclipse.dltk.tcl.parser.tests.TestUtils;
import org.eclipse.dltk.tcl.validators.ITclCheck;
import org.eclipse.dltk.utils.TextUtils;

public class UnreachableCodeCheckTest extends TestCase {
	NamespaceScopeProcessor processor = DefinitionManager.getInstance()
			.createProcessor();

	public void test001() throws Exception {
		String source = "puts 1; return; puts 2";
		List<Integer> errorCodes = new ArrayList<Integer>();
		errorCodes.add(ICheckKinds.UNREACHABLE_CODE);
		typedCheck(source, errorCodes);
	}

	public void test002() throws Exception {
		String source = "puts 1; return;";
		List<Integer> errorCodes = new ArrayList<Integer>();
		typedCheck(source, errorCodes);
	}

	public void test003() throws Exception {
		String source = "puts 1; return; puts 2; puts 3; puts 4";
		List<Integer> errorCodes = new ArrayList<Integer>();
		errorCodes.add(ICheckKinds.UNREACHABLE_CODE);
		typedCheck(source, errorCodes);
	}

	public void test004() throws Exception {
		String source = "proc my {} {puts 1; return; puts 2; puts 3; puts 4}";
		List<Integer> errorCodes = new ArrayList<Integer>();
		errorCodes.add(ICheckKinds.UNREACHABLE_CODE);
		typedCheck(source, errorCodes);
	}

	public void test005() throws Exception {
		String source = "proc namespace2 {args} {alpha;return;beta;beta;beta}; proc lala {} {}";
		List<Integer> errorCodes = new ArrayList<Integer>();
		errorCodes.add(ICheckKinds.UNREACHABLE_CODE);
		typedCheck(source, errorCodes);
	}

	public void test006() throws Exception {
		String source = "proc lala {} {proc mumu {} {alpha; return beta;beta;beta; return}; return; beta}";
		List<Integer> errorCodes = new ArrayList<Integer>();
		errorCodes.add(ICheckKinds.UNREACHABLE_CODE);
		errorCodes.add(ICheckKinds.UNREACHABLE_CODE);
		typedCheck(source, errorCodes);
	}

	public void test007() throws Exception {
		String source = "proc lala {} {proc gamma {} {alpha; return}; proc beta {} {alpha; return}}";
		List<Integer> errorCodes = new ArrayList<Integer>();
		typedCheck(source, errorCodes);
	}

	public void test008() throws Exception {
		String source = "if 1 {return} {return; puts 2}";
		List<Integer> errorCodes = new ArrayList<Integer>();
		errorCodes.add(ICheckKinds.UNREACHABLE_CODE);
		typedCheck(source, errorCodes);
	}

	public void test009() throws Exception {
		String source = "if 1 {return} {return; puts 2}; return; puts 4;";
		List<Integer> errorCodes = new ArrayList<Integer>();
		errorCodes.add(ICheckKinds.UNREACHABLE_CODE);
		errorCodes.add(ICheckKinds.UNREACHABLE_CODE);
		typedCheck(source, errorCodes);
	}

	public void test010() throws Exception {
		String source = "after 340 {return 30; puts alpha}; cmd; return 50; puts 34";
		List<Integer> errorCodes = new ArrayList<Integer>();
		errorCodes.add(ICheckKinds.UNREACHABLE_CODE);
		errorCodes.add(ICheckKinds.UNREACHABLE_CODE);
		typedCheck(source, errorCodes);
	}

	public void test011() throws Exception {
		String source = "switch alpha {1 {return [alpha]} 2 {return 2} 3 {return 3}}";
		List<Integer> errorCodes = new ArrayList<Integer>();
		typedCheck(source, errorCodes);
	}

	public void test012() throws Exception {
		String source = "switch -- $match_type {"
				+ " \"regexp\" { return [regexp $pattern_data $result_data] }"
				+ " \"exact\" { return [string equal $result_data $pattern_data] }"
				+ " \"glob\" { return [string match $result_data $pattern_data] }"
				+ "}";
		List<Integer> errorCodes = new ArrayList<Integer>();
		typedCheck(source, errorCodes);
	}

	public void test013() throws Exception {
		String source = "puts [puts 4; return; puts 5]";
		List<Integer> errorCodes = new ArrayList<Integer>();
		errorCodes.add(ICheckKinds.UNREACHABLE_CODE);
		typedCheck(source, errorCodes);
	}

	public void test014() throws Exception {
		String source = "puts beta[puts 4; return]";
		List<Integer> errorCodes = new ArrayList<Integer>();
		typedCheck(source, errorCodes);
	}

	private void typedCheck(String source, List<Integer> errorCodes)
			throws Exception {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		StackTraceElement element = stackTrace[2];
		System.out.println("%%%%%%%%%%%%%%%%Test:" + element.getMethodName());
		TclParser parser = TestUtils.createParser("8.4");
		TclErrorCollector errors = new TclErrorCollector();
		List<TclCommand> module = parser.parse(source, errors, processor);
		ITclCheck check = new UnreachableCodeCheck();
		check.checkCommands(module, errors, new HashMap<String, String>(),
				null, TextUtils.createLineTracker(source));
		if (errors.getCount() > 0) {
			TestUtils.outErrors(source, errors);
		}
		TestCase.assertEquals(errorCodes.size(), errors.getCount());
		int count = 0;
		for (int code : errorCodes) {
			for (TclError tclError : errors.getErrors()) {
				if (tclError.getCode() == code) {
					count++;
					break;
				}
			}
		}
		TestCase.assertEquals(errorCodes.size(), count);
	}
}
