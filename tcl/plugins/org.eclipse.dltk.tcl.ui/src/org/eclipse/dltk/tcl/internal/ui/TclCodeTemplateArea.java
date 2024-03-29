/*******************************************************************************
 * Copyright (c) 2009, 2017 xored software, Inc. and others.
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
package org.eclipse.dltk.tcl.internal.ui;

import org.eclipse.dltk.ui.text.templates.ICodeTemplateAccess;
import org.eclipse.dltk.ui.text.templates.ICodeTemplateArea;

public class TclCodeTemplateArea implements ICodeTemplateArea {

	private static final String PREF_ID = "org.eclipse.dltk.tcl.preferences.code.templates"; //$NON-NLS-1$
	private static final String PROP_ID = "org.eclipse.dltk.tcl.propertyPage.CodeTemplatePage"; //$NON-NLS-1$

	@Override
	public ICodeTemplateAccess getTemplateAccess() {
		return TclUI.getDefault().getCodeTemplateAccess();
	}

	@Override
	public String getTemplatePreferencePageId() {
		return PREF_ID;
	}

	@Override
	public String getTemplatePropertyPageId() {
		return PROP_ID;
	}

}
