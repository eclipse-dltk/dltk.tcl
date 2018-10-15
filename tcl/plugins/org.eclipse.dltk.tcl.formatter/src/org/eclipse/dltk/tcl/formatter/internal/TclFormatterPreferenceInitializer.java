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
 *     xored software, Inc. - initial API and Implementation (Sergey Kanshin)
 *******************************************************************************/

package org.eclipse.dltk.tcl.formatter.internal;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.dltk.tcl.formatter.TclFormatterConstants;
import org.eclipse.jface.preference.IPreferenceStore;

public class TclFormatterPreferenceInitializer extends
		AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = TclFormatterPlugin.getDefault()
				.getPreferenceStore();
		store.setDefault(TclFormatterConstants.INDENT_SCRIPT, true);
		store.setDefault(TclFormatterConstants.INDENT_AFTER_BACKSLASH, true);
		//
		store.setDefault(TclFormatterConstants.LINES_PRESERVE, 1);
		//
		store.setDefault(TclFormatterConstants.LINES_FILE_AFTER_PACKAGE, 1);
		store.setDefault(TclFormatterConstants.LINES_FILE_BETWEEN_PROC, 1);
		//
		store.setDefault(TclFormatterConstants.WRAP_COMMENTS, false);
		store.setDefault(TclFormatterConstants.WRAP_COMMENTS_LENGTH, 80);
		//
	}

}
