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
package org.eclipse.dltk.tcl.internal.tclchecker;

import org.eclipse.dltk.tcl.tclchecker.TclCheckerPlugin;

public class TclCheckerMarker {

	public static final String TYPE = TclCheckerPlugin.PLUGIN_ID
			+ ".tclcheckerproblem"; //$NON-NLS-1$

	public static final String SUGGESTED_CORRECTIONS = TclCheckerPlugin.PLUGIN_ID
			+ ".suggestedCorrections"; //$NON-NLS-1$

	public static final String COMMAND_START = TclCheckerPlugin.PLUGIN_ID
			+ ".commandStart"; //$NON-NLS-1$

	public static final String COMMAND_END = TclCheckerPlugin.PLUGIN_ID
			+ ".commandEnd"; //$NON-NLS-1$

	public static final String MESSAGE_ID = TclCheckerPlugin.PLUGIN_ID
			+ ".messageId"; //$NON-NLS-1$

	public static final String TIMESTAMP = TclCheckerPlugin.PLUGIN_ID
			+ ".timestamp"; //$NON-NLS-1$

	public static final String AUTO_CORRECTABLE = TclCheckerPlugin.PLUGIN_ID
			+ ".autoCorrectable"; //$NON-NLS-1$

}
