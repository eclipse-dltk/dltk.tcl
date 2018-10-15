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

package org.eclipse.dltk.tcl.validators;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.dltk.tcl.core.TclPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class TclValidatorsCore extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.dltk.tcl.validators"; //$NON-NLS-1$

	// The shared instance
	private static TclValidatorsCore plugin;

	/**
	 * The constructor
	 */
	public TclValidatorsCore() {
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		TclPlugin.REPORT_PARSER_PROBLEMS = false;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static TclValidatorsCore getDefault() {
		return plugin;
	}

	/**
	 * @param e
	 */
	public static void error(Throwable e) {
		getDefault().getLog()
				.log(new Status(IStatus.ERROR, PLUGIN_ID, e.toString(), e));
	}

}
