/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.debug.ui;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class TclDebugUIPlugin extends AbstractUIPlugin {
	
	/**
	 * @since 2.0
	 */
	public static final String PLUGIN_ID = "org.eclipse.dltk.tcl.debug.ui"; //$NON-NLS-1$

	public TclDebugUIPlugin() {
		plugin = this;
	}

	// The shared instance.
	private static TclDebugUIPlugin plugin;

	/**
	 * This method is called upon plug-in activation
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
	}

	/**
	 * Returns the shared instance.
	 */
	public static TclDebugUIPlugin getDefault() {
		return plugin;
	}
}
