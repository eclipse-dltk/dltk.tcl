/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.console.ui.preferences;

import org.eclipse.dltk.tcl.console.TclConsoleConstants;
import org.eclipse.dltk.tcl.internal.debug.ui.TclDebugUIPlugin;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class TclConsolePreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	private Text newPrompt;

	private Text appendPrompt;

	@Override
	protected IPreferenceStore doGetPreferenceStore() {
		return TclDebugUIPlugin.getDefault().getPreferenceStore();
	}

	protected void createPrompt(Composite parent, Object data) {
		Font font = parent.getFont();

		Group group = new Group(parent, SWT.NONE);
		group.setLayoutData(data);
		group.setFont(font);
		group.setText("Prompt");

		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		group.setLayout(layout);

		// New command
		Label newPromptLabel = new Label(group, SWT.NONE);
		newPromptLabel.setFont(font);
		newPromptLabel.setText("New command:");

		newPrompt = new Text(group, SWT.BORDER);
		newPrompt.addModifyListener(e -> validateValues());
		newPrompt.setLayoutData(new GridData(GridData.FILL, SWT.NONE, true, false));

		// Append command
		Label appendCommandLabel = new Label(group, SWT.NONE);
		appendCommandLabel.setFont(font);
		appendCommandLabel.setText("Append command:");

		appendPrompt = new Text(group, SWT.BORDER);
		appendPrompt.addModifyListener(e -> validateValues());
		appendPrompt.setLayoutData(new GridData(GridData.FILL, SWT.NONE, true, false));
	}

	@Override
	protected Control createContents(Composite parent) {
		Composite top = new Composite(parent, SWT.NONE);

		GridLayout layout = new GridLayout();
		layout.numColumns = 1;

		top.setLayout(layout);

		createPrompt(top, new GridData(GridData.FILL, SWT.NONE, true, false));

		initializeValues();
		validateValues();

		return top;
	}

	protected void initializeValues() {
		IPreferenceStore store = getPreferenceStore();

		newPrompt.setText(store.getString(TclConsoleConstants.PREF_NEW_PROMPT));

		appendPrompt.setText(store.getString(TclConsoleConstants.PREF_CONTINUE_PROMPT));
	}

	protected void validateValues() {
		if ("".equals(newPrompt.getText())) {
			setErrorMessage("Empty prompt");
			setValid(false);
		} else if ("".equals(appendPrompt.getText())) {
			setErrorMessage("Empty prompt");
			setValid(false);
		} else {
			setErrorMessage(null);
			setValid(true);
		}
	}

	@Override
	public void init(IWorkbench workbench) {

	}

	@Override
	protected void performDefaults() {
		newPrompt.setText("=>");
		appendPrompt.setText("->");
	}

	@Override
	public boolean performOk() {
		IPreferenceStore store = getPreferenceStore();

		store.setValue(TclConsoleConstants.PREF_NEW_PROMPT, newPrompt.getText());
		store.setValue(TclConsoleConstants.PREF_CONTINUE_PROMPT, appendPrompt.getText());

		return true;
	}
}
