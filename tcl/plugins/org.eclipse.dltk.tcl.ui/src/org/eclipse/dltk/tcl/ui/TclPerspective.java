/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.tcl.ui;

import static org.eclipse.dltk.ui.DLTKUIPlugin.ID_SCRIPT_EXPLORER;

import org.eclipse.dltk.tcl.internal.ui.TclUI;
import org.eclipse.dltk.tcl.internal.ui.wizards.TclFileCreationWizard;
import org.eclipse.dltk.tcl.internal.ui.wizards.TclPackageCreationWizard;
import org.eclipse.dltk.tcl.internal.ui.wizards.TclProjectCreationWizard;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.progress.IProgressConstants;

public class TclPerspective implements IPerspectiveFactory {
	/**
	 * @since 2.0
	 */
	public static final String PERSPECTIVE_ID = "org.eclipse.dltk.tcl.ui.TclPerspective"; //$NON-NLS-1$

	// TODO: move to general class like ScriptPerspective

	public static final String NEW_FOLDER_WIZARD = "org.eclipse.ui.wizards.new.folder"; //$NON-NLS-1$

	public static final String NEW_FILE_WIZARD = "org.eclipse.ui.wizards.new.file"; //$NON-NLS-1$

	public static final String NEW_UNTITLED_TEXT_FILE_WIZARD = "org.eclipse.ui.editors.wizards.UntitledTextFileWizard"; //$NON-NLS-1$

	public static final String ID_NEW_SOURCE_WIZARD = "org.eclipse.dltk.tcl.ui.wizards.NewSourceFolderCreationWizard"; //$NON-NLS-1$

	public static final String ID_NEW_PACKAGE_WIZARD = "org.eclipse.dltk.tcl.ui.wizards.NewPackageCreationWizard"; //$NON-NLS-1$

	protected void addNewWizardShortcuts(IPageLayout layout) {
		layout.addNewWizardShortcut(TclProjectCreationWizard.ID_WIZARD);
		layout.addNewWizardShortcut(TclFileCreationWizard.ID_WIZARD);

		layout.addNewWizardShortcut(ID_NEW_SOURCE_WIZARD);
		layout.addNewWizardShortcut(TclPackageCreationWizard.ID_WIZARD);

		layout.addNewWizardShortcut(NEW_FOLDER_WIZARD);
		layout.addNewWizardShortcut(NEW_FILE_WIZARD);
		layout.addNewWizardShortcut(NEW_UNTITLED_TEXT_FILE_WIZARD);
	}

	protected void addShowViewShortcuts(IPageLayout layout) {
		layout.addShowViewShortcut(IPageLayout.ID_OUTLINE);
		layout.addShowViewShortcut(IPageLayout.ID_PROBLEM_VIEW);
		layout.addShowViewShortcut(IConsoleConstants.ID_CONSOLE_VIEW);

		layout.addShowViewShortcut(IPageLayout.ID_TASK_LIST);
		layout.addShowViewShortcut(IProgressConstants.PROGRESS_VIEW_ID);

		layout.addShowViewShortcut(ID_SCRIPT_EXPLORER);
	}

	protected void addActionSets(IPageLayout layout) {
		layout.addActionSet(IPageLayout.ID_NAVIGATE_ACTION_SET);
		layout.addActionSet(TclUI.ID_ACTION_SET);
	}

	protected void addViews(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		IFolderLayout folder = layout.createFolder("right", IPageLayout.RIGHT, //$NON-NLS-1$
				(float) 0.75, editorArea);

		folder.addView(IPageLayout.ID_OUTLINE);
		// folder
		// .addView("org.eclipse.dltk.tcl.internal.ui.navigation.PackagesView");
	}

	protected void createFolders(IPageLayout layout) {
		final String editorArea = layout.getEditorArea();

		// Folder
		IFolderLayout folder = layout.createFolder("left", IPageLayout.LEFT, //$NON-NLS-1$
				(float) 0.2, editorArea);

		folder.addView(ID_SCRIPT_EXPLORER);
		folder.addView("org.eclipse.dltk.ui.TypeHierarchy"); //$NON-NLS-1$
		folder.addPlaceholder(IPageLayout.ID_BOOKMARKS);

		// Output folder
		IFolderLayout outputFolder = layout.createFolder("bottom", //$NON-NLS-1$
				IPageLayout.BOTTOM, (float) 0.75, editorArea);

		outputFolder.addView(IPageLayout.ID_PROBLEM_VIEW);
		outputFolder.addView(IPageLayout.ID_TASK_LIST);
		outputFolder.addView(IConsoleConstants.ID_CONSOLE_VIEW);
		outputFolder.addView("org.eclipse.dltk.tcl.ui.TclDocumentationView"); //$NON-NLS-1$

		outputFolder.addPlaceholder(IConsoleConstants.ID_CONSOLE_VIEW);
		outputFolder.addPlaceholder(IPageLayout.ID_BOOKMARKS);
		outputFolder.addPlaceholder(IProgressConstants.PROGRESS_VIEW_ID);
	}

	protected void addPerspectiveShotcuts(IPageLayout layout) {
		layout.addPerspectiveShortcut("org.eclipse.debug.ui.DebugPerspective"); //$NON-NLS-1$
	}

	@Override
	public void createInitialLayout(IPageLayout layout) {
		createFolders(layout);
		addViews(layout);
		addActionSets(layout);
		addShowViewShortcuts(layout);
		addNewWizardShortcuts(layout);
		addPerspectiveShotcuts(layout);
	}
}
