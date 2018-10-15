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
package org.eclipse.dltk.tcl.internal.ui.wizards;

import org.eclipse.dltk.internal.ui.wizards.dialogfields.SelectionButtonDialogField;
import org.eclipse.dltk.ui.dialogs.IProjectTemplate;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

class TclProjectTemplateEntry {
	private final IProjectTemplate template;
	final String id;
	final SelectionButtonDialogField fLinkRadio;

	/**
	 * @param descriptor
	 */
	public TclProjectTemplateEntry(String id, String name,
			IProjectTemplate template) {
		this.template = template;
		this.id = id;
		fLinkRadio = new SelectionButtonDialogField(SWT.RADIO);
		fLinkRadio.setLabelText(name);
	}

	/**
	 * @param group
	 */
	public void createControls(Composite group, int numColumns) {
		fLinkRadio.doFillIntoGrid(group, numColumns);
	}

	protected boolean isSelected() {
		return fLinkRadio.isSelected();
	}

	/**
	 * @return
	 */
	public IProjectTemplate getTemplate() {
		return template;
	}

}
