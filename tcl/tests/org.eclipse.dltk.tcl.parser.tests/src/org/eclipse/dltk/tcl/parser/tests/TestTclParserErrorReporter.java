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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.dltk.compiler.problem.ProblemSeverity;
import org.eclipse.dltk.tcl.parser.ITclErrorReporter;

public class TestTclParserErrorReporter implements ITclErrorReporter {
	public Map<Integer, Integer> errors = new HashMap<>();
	public int total = 0;

	public TestTclParserErrorReporter() {
	}

	@Override
	public void report(int code, String value, String[] extraMessage, int start,
			int end, ProblemSeverity kind) {
		Integer key = new Integer(code);
		Integer old = errors.get(key);
		if (old == null) {
			errors.put(key, new Integer(1));
		} else {
			errors.put(key, new Integer(old.intValue() + 1));
		}
		total++;
	}
}
