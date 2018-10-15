/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.dltk.tcl.internal.debug.ui.launchConfigurations;

import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.dltk.core.PreferencesLookupDelegate;
import org.eclipse.dltk.debug.core.DLTKDebugPreferenceConstants;
import org.eclipse.dltk.debug.ui.launchConfigurations.MainLaunchConfigurationTab;
import org.eclipse.dltk.tcl.core.TclNature;
import org.eclipse.dltk.tcl.internal.debug.TclDebugPlugin;

/**
 * Main launch configuration tab for tcl scripts
 */
public class TclMainLaunchConfigurationTab extends MainLaunchConfigurationTab {
	public TclMainLaunchConfigurationTab(String mode) {
		super(mode);
		if (ILaunchManager.RUN_MODE.equals(mode)) {
			enableInteractiveConsoleGroup();
		}
	}

	/*
	 * @see
	 * org.eclipse.dltk.debug.ui.launchConfigurations.ScriptLaunchConfigurationTab
	 * #
	 * breakOnFirstLinePrefEnabled(org.eclipse.dltk.core.PreferencesLookupDelegate
	 * )
	 */
	@Override
	protected boolean breakOnFirstLinePrefEnabled(
			PreferencesLookupDelegate delegate) {
		return delegate.getBoolean(TclDebugPlugin.PLUGIN_ID,
				DLTKDebugPreferenceConstants.PREF_DBGP_BREAK_ON_FIRST_LINE);
	}

	/*
	 * @see
	 * org.eclipse.dltk.debug.ui.launchConfigurations.ScriptLaunchConfigurationTab
	 * #dbpgLoggingPrefEnabled(org.eclipse.dltk.core.PreferencesLookupDelegate)
	 */
	@Override
	protected boolean dbpgLoggingPrefEnabled(PreferencesLookupDelegate delegate) {
		return delegate.getBoolean(TclDebugPlugin.PLUGIN_ID,
				DLTKDebugPreferenceConstants.PREF_DBGP_ENABLE_LOGGING);
	}

	/**
	 * @since 2.0
	 */
	@Override
	public String getNatureID() {
		return TclNature.NATURE_ID;
	}
}
