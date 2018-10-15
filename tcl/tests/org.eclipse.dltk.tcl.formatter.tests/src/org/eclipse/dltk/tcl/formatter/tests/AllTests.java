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

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("org.eclipse.dltk.tcl.formatter.tests"); //$NON-NLS-1$
		// $JUnit-BEGIN$
		suite.addTestSuite(SimpleTests.class);
		suite.addTest(CodeTest.suite());
		suite.addTest(IndentationTest.suite());
		suite.addTest(IndentationOffTest.suite());
		suite.addTest(BlankLinesTest.suite());
		suite.addTestSuite(CommentsWrapTest.class);
		suite.addTest(PreserveEmptyLinesTest.suite());
		suite.addTest(FullTest.suite());
		suite.addTest(SwitchTests.suite());
		suite.addTest(XOTclTests.suite());
		suite.addTest(IncrTclTests.suite());
		// $JUnit-END$
		return suite;
	}
}
