/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *

 *******************************************************************************/
package org.eclipse.dltk.itcl.core.tests.internal;

import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.SourceParserUtil;
import org.eclipse.dltk.core.tests.model.AbstractModelTests;
import org.eclipse.dltk.itcl.core.tests.ITclTestsPlugin;

import junit.framework.Test;

public class ITclParseTests extends AbstractModelTests {
	public ITclParseTests(String name) {
		super(name);
	}

	public static Test suite() {
		return new Suite(ITclParseTests.class);
	}

	public void testProcArgsABC() throws Exception {
		final String projectName = "project0"; //$NON-NLS-1$
		setUpScriptProject(projectName, ITclTestsPlugin.PLUGIN_ID);
		final String folderName = ""; //$NON-NLS-1$
		final String moduleName = "proc_abc_args.tcl"; //$NON-NLS-1$
		final ISourceModule module = getSourceModule(projectName, folderName, new Path(moduleName));
		assertNotNull(module);
		final ModuleDeclaration declaration = SourceParserUtil.getModuleDeclaration(module);
		final MethodDeclaration[] children = declaration.getFunctions();
		assertNotNull(children);
		assertEquals(1, children.length);
		final MethodDeclaration proc = children[0];
		assertEquals("fooProc", proc.getName()); //$NON-NLS-1$
		assertEquals(3, proc.getArguments().size());
		deleteProject(projectName);
	}

}
