package org.eclipse.dltk.tcl.internal.debug;

import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.dltk.compiler.util.Util;
import org.eclipse.dltk.debug.core.DLTKDebugPreferenceConstants;

public class TclDebugPreferenceInitializer extends
		AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		Preferences store = TclDebugPlugin.getDefault().getPluginPreferences();

		store.setDefault(TclDebugConstants.DEBUGGING_ENGINE_ID_KEY,
				Util.EMPTY_STRING);

		store.setDefault(
				DLTKDebugPreferenceConstants.PREF_DBGP_BREAK_ON_FIRST_LINE,
				false);
		store.setDefault(DLTKDebugPreferenceConstants.PREF_DBGP_ENABLE_LOGGING,
				false);
		store.setDefault(
				TclDebugConstants.DEBUG_STREAM_FILTER_COMMAND_RENAME_WARNING,
				true);

		store.setDefault(
				DLTKDebugPreferenceConstants.PREF_DBGP_SHOW_SCOPE_GLOBAL, true);
		store.setDefault(
				DLTKDebugPreferenceConstants.PREF_DBGP_SHOW_SCOPE_CLASS, true);
		store.setDefault(
				DLTKDebugPreferenceConstants.PREF_DBGP_SHOW_SCOPE_LOCAL, true);
	}

}
