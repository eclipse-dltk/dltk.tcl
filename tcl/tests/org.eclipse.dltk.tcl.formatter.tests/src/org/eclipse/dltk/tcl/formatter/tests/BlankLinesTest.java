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
 *     xored software, Inc. - initial API and Implementation (Sergey Kanshin)
 *******************************************************************************/
package org.eclipse.dltk.tcl.formatter.tests;

import junit.framework.TestSuite;

import org.eclipse.dltk.formatter.tests.ScriptedTest;
import org.eclipse.dltk.tcl.formatter.TclFormatterConstants;

public class BlankLinesTest extends ScriptedTest {

	@SuppressWarnings("nls")
	public static TestSuite suite() {
		final BlankLinesTest factory = new BlankLinesTest();
		factory.setPreference(TclFormatterConstants.LINES_PRESERVE,
				new Integer(1));
		factory.setPreference(TclFormatterConstants.LINES_FILE_BETWEEN_PROC,
				new Integer(2));
		factory.setPreference(TclFormatterConstants.LINES_FILE_AFTER_PACKAGE,
				new Integer(1));
		return factory.createScriptedSuite(FContext.CONTEXT,
				"scripts/blank-lines-tests.tcl");
	}

}
