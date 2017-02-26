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

import java.util.List;

import org.eclipse.dltk.ast.parser.ISourceParser;
import org.eclipse.dltk.compiler.env.ModuleSource;
import org.eclipse.dltk.tcl.ast.TclCommand;
import org.eclipse.dltk.tcl.internal.parser.TclSourceParserFactory;
import org.eclipse.dltk.tcl.parser.ITclParserOptions;
import org.eclipse.dltk.tcl.parser.PerformanceMonitor;
import org.eclipse.dltk.tcl.parser.TclErrorCollector;
import org.eclipse.dltk.tcl.parser.TclParser;
import org.eclipse.dltk.tcl.parser.definitions.DefinitionManager;
import org.eclipse.dltk.tcl.parser.definitions.NamespaceScopeProcessor;
import org.junit.Test;

public class PerfomanceTests {
	@Test
	public void testBigFilePerfomance() throws Exception {
		PerformanceMonitor.getDefault().begin("LOAD BIG FILE:");
		String contents = BigFileGenerator.generateBigFile001();
		PerformanceMonitor.getDefault().end("LOAD BIG FILE:");

		PerformanceMonitor.getDefault().begin("RAW PARSE BIG FILE:");
		TclParser parser = TestUtils.createParser();
		parser.parse(contents);
		PerformanceMonitor.getDefault().end("RAW PARSE BIG FILE:");

		// Parsing with processors and matching.
		NamespaceScopeProcessor processor = DefinitionManager.getInstance()
				.createProcessor();
		parser = TestUtils.createParser("8.5");
		TclErrorCollector errors = new TclErrorCollector();
		parser.setOptionValue(ITclParserOptions.REPORT_UNKNOWN_AS_ERROR, true);
		PerformanceMonitor.getDefault().begin("PARSE BIG FILE:");
		List<TclCommand> module = parser.parse(contents, errors, processor);
		PerformanceMonitor.getDefault().end("PARSE BIG FILE:");

		PerformanceMonitor.getDefault().begin("OLD PARSE BIG FILE:");
		ISourceParser p = (new TclSourceParserFactory()).createSourceParser();
		p.parse(new ModuleSource(contents), null);
		PerformanceMonitor.getDefault().end("OLD PARSE BIG FILE:");
		// if (errors.getCount() > 0) {
		// TestUtils.outErrors(contents, errors);
		// }
		PerformanceMonitor.getDefault().print();
	}
}
