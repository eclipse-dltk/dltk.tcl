/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.ui;

import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.ui.PluginImagesHelper;
import org.eclipse.jface.resource.ImageDescriptor;

public class TclImages {
	private static final PluginImagesHelper helper = new PluginImagesHelper(
			TclUI.getDefault().getBundle(), new Path("/icons"));

	public static final ImageDescriptor DESC_WIZBAN_PROJECT_CREATION = helper
			.createUnManaged(PluginImagesHelper.T_WIZBAN,
					"projectcreate_wiz.png");

	public static final ImageDescriptor DESC_WIZBAN_FILE_CREATION = helper
			.createUnManaged(PluginImagesHelper.T_WIZBAN, "filecreate_wiz.png");

	public static final ImageDescriptor DESC_OBJS_TEST = helper
			.createUnManaged(PluginImagesHelper.T_OBJ, "test_obj.png");
	public static final ImageDescriptor DESC_OBJS_TESTCASE = helper
			.createUnManaged(PluginImagesHelper.T_OBJ, "testcase_obj.png");
	/**
	 * @since 2.0
	 */
	public static final ImageDescriptor DESC_OBJS_FOLDER = helper
			.createUnManaged(PluginImagesHelper.T_OBJ,
					"tcl_packagefolder_obj.gif");
	/**
	 * @since 2.0
	 */
	public static final ImageDescriptor DESC_OBJS_TCL = helper.createUnManaged(
			PluginImagesHelper.T_OBJ, "tcl_obj.gif");
}
