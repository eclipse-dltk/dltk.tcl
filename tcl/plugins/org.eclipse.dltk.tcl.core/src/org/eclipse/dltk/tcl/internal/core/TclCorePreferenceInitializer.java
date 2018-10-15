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
 *     xored software, Inc. - initial API and Implementation (Andrei Sobolev)
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.core;

import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.dltk.compiler.task.TaskTagUtils;
import org.eclipse.dltk.tcl.core.TclCorePreferences;
import org.eclipse.dltk.tcl.core.TclPlugin;

public class TclCorePreferenceInitializer extends AbstractPreferenceInitializer {

	public TclCorePreferenceInitializer() {
	}

	public void initializeDefaultPreferences() {
		// Todo Tags
		Preferences preferences = TclPlugin.getDefault().getPluginPreferences();
		TaskTagUtils.initializeDefaultValues(preferences);
		// Check content
		preferences.setDefault(
				TclCorePreferences.CHECK_CONTENT_EMPTY_EXTENSION_LOCAL, true);
		preferences.setDefault(
				TclCorePreferences.CHECK_CONTENT_EMPTY_EXTENSION_REMOTE, false);
		preferences.setDefault(
				TclCorePreferences.CHECK_CONTENT_ANY_EXTENSION_LOCAL, true);
		preferences.setDefault(
				TclCorePreferences.CHECK_CONTENT_ANY_EXTENSION_REMOTE, false);

		preferences
				.setDefault(
						TclCorePreferences.CHECK_CONTENT_EXCLUDES,
						"*.tar.*;*.so;*.exe;*.dll;*.msi;*.zip;*.rar;*.a;*.la;*.so.*;"
								+ "*.c;*.cpp;*.h;*.jp*;*.png;*.gif;*.htm*;*.txt;*.ps;"
								+ "*.pdf;*.xsd;README;*.doc;*.xsl;*.ppt;*.odf;*.n;*.rc;"
								+ "*.pdx;*.tap;*.pc;*.enc;*.xml");
		preferences.setDefault(
				TclCorePreferences.PACKAGES_REFRESH_INTERVAL_LOCAL,
				15 * 60 * 1000);
		preferences.setDefault(
				TclCorePreferences.PACKAGES_REFRESH_INTERVAL_REMOTE,
				24 * 60 * 60 * 1000);
	}

}
