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
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.tcl.parser.tests;

import java.io.IOException;

import org.eclipse.dltk.compiler.problem.ProblemCollector;

public class TclIfProcessorTests extends AbstractTclParserTests {

	public void testElseIf() throws IOException {
		final ProblemCollector collector = parse("scripts/elseif.tcl"); //$NON-NLS-1$
		if (collector.hasErrors()) {
			fail(collector.getErrors().toString());
		}
	}

}
