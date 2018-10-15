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
package org.eclipse.dltk.tcl.activestatedebugger;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.dltk.tcl.activestatedebugger.messages"; //$NON-NLS-1$
	public static String ErrorAction_stopAlways;
	public static String ErrorAction_stopNever;
	public static String ErrorAction_stopUncaught;
	public static String instrumentation_autoload_caption;
	public static String instrumentation_dynproc_caption;
	public static String instrumentation_expect_caption;
	public static String instrumentation_itcl_caption;
	public static String instrumentation_tclx_caption;
	public static String TclActiveStateDebuggerRunner_errorEngineNotConfigured;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
