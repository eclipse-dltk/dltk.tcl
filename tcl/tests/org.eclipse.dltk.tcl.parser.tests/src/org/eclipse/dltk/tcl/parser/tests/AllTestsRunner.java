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

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ NotNegativeTypeParseTests.class, TclParserTests.class,
		GroupParseTests.class, IntegerTypeParseTests.class,
		SwitchReduceTest.class, SetCommandParseTests.class, SynopsisTests.class,
		ShortSynopsisTests.class, CommandOutOfScopeTests.class,
		ErrorReportingTests.class, TclComplexArgumentParseTests.class,
		SwitchCommandTests.class, ProcCommandTests.class,
		IndexTypeParseTests.class, TclSwitchArgumentsParseTests.class,
		PackageCommandTests.class, DefinitionTests.class,
		TclGroupArgumentsParseTests.class, WhileCommandTests.class,
		VersionsParserTests.class, MatchPrefixTests.class,
		PutsCommandTests.class, TclTypedArgumentsParseTests.class,
		LoadDefinitionTests.class, AfterCommandTests.class,
		SocketCommandTests.class, IfCommandTests.class,
		TclConstantsParseTests.class, NamespaceScopeProcessorTests.class })
public class AllTestsRunner {
}
