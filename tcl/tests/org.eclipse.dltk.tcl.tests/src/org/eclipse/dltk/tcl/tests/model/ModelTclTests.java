/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.tcl.tests.model;

import junit.framework.Test;

import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.tests.model.AbstractModelTests;
import org.eclipse.dltk.tcl.tests.TclTestsPlugin;

public class ModelTclTests extends AbstractModelTests {
	public ModelTclTests(String name) {
		super(name);
	}

	public static Test suite() {
		return new Suite(ModelTclTests.class);
	}

	public void setUpSuite() throws Exception {
		super.setUpSuite();
	}

	public void tearDownSuite() throws Exception {
		super.tearDownSuite();
	}

	public void testModel00_() throws Exception {
		String prj = "model0";
		IScriptProject project = setUpScriptProject(prj, TclTestsPlugin.PLUGIN_NAME);

		ISourceModule module = this.getSourceModule(prj, "", new Path("X.tcl"));

		// IModule module = project.findModule(new Path("/src/module0.py"));

		assertNotNull("Module X.tcl not found", module);
		// assertEquals("X.py exists", true, module.exists() );

		IModelElement[] moduleChildren = module.getChildren();
		assertNotNull(moduleChildren);
		// Check count of module childrens
		assertEquals(2, moduleChildren.length);

		// assertEquals(true, false);

		deleteProject(prj);
	}

	public void testModel01() throws Exception {
		String prj = "model1";
		IScriptProject project = setUpScriptProject(prj, TclTestsPlugin.PLUGIN_NAME);

		ISourceModule module = this.getSourceModule(prj, "", new Path("X.tcl"));

		// IModule module = project.findModule(new Path("/src/module0.py"));

		assertNotNull("Module X.tcl not found", module);
		// assertEquals("X.py exists", true, module.exists() );

		IModelElement[] moduleChildren = module.getChildren();
		assertNotNull(moduleChildren);
		// Check count of module childrens
		assertEquals(3, moduleChildren.length);

		// assertEquals(true, false);

		deleteProject(prj);
	}
}
