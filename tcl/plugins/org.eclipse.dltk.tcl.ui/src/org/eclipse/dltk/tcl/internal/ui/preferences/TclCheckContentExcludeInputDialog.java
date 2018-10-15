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
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.ui.preferences;

import java.util.List;

import org.eclipse.dltk.compiler.task.TaskTagUtils;
import org.eclipse.dltk.compiler.util.Util;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.DialogField;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.IDialogFieldListener;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.LayoutUtil;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.StringDialogField;
import org.eclipse.dltk.ui.dialogs.StatusInfo;
import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class TclCheckContentExcludeInputDialog extends StatusDialog
		implements IDialogFieldListener {

	private StringDialogField fPatternDialogField;

	private final List fExistingNames;

	public TclCheckContentExcludeInputDialog(Shell parent, String input,
			List existingEntries) {
		super(parent);
		fExistingNames = existingEntries;
		if (input == null) {
			setTitle(
					TclPreferencesMessages.TclCheckContentExcludeDialog_newTitle);
		} else {
			setTitle(
					TclPreferencesMessages.TclCheckContentExcludeDialog_editTitle);
		}
		fPatternDialogField = new StringDialogField();
		fPatternDialogField.setLabelText(
				TclPreferencesMessages.TclCheckContentExcludeDialog_pattern);
		fPatternDialogField.setDialogFieldListener(this);
		fPatternDialogField.setText(input != null ? input : Util.EMPTY_STRING);
	}

	public String getResult() {
		return fPatternDialogField.getText().trim();
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		Composite inner = new Composite(composite, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.numColumns = 2;
		inner.setLayout(layout);
		fPatternDialogField.doFillIntoGrid(inner, 2);
		LayoutUtil.setHorizontalGrabbing(
				fPatternDialogField.getTextControl(null));
		LayoutUtil.setWidthHint(fPatternDialogField.getTextControl(null),
				convertWidthInCharsToPixels(45));
		fPatternDialogField.postSetFocusOnDialogField(parent.getDisplay());
		applyDialogFont(composite);
		return composite;
	}

	private void doValidation() {
		StatusInfo status = new StatusInfo();
		String newText = fPatternDialogField.getText();
		if (newText.length() == 0) {
			status.setError(
					TclPreferencesMessages.TclCheckContentExcludeDialog_enterPatternError);
		} else {
			if (!TaskTagUtils.isValidName(newText)) {
				status.setError(
						TclPreferencesMessages.TclCheckContentExcludeDialog_invalidPatternError);
			} else if (fExistingNames.contains(newText)) {
				status.setError(
						TclPreferencesMessages.TclCheckContentExcludeDialog_duplicatePatternError);
			}
		}
		updateStatus(status);
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
	}

	@Override
	public void dialogFieldChanged(DialogField field) {
		doValidation();
	}

}
