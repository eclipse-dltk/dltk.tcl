/*******************************************************************************
 * Copyright (c) 2000, 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.ui.browsing;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.tcl.internal.ui.TclUI;
import org.eclipse.dltk.tcl.internal.ui.wizards.TclFileCreationWizard;
import org.eclipse.dltk.tcl.internal.ui.wizards.TclProjectCreationWizard;
import org.eclipse.dltk.tcl.ui.TclPerspective;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.IPlaceholderFolderLayout;
import org.eclipse.ui.progress.IProgressConstants;

public class TclBrowsingPerspectiveFactory implements IPerspectiveFactory {

	public static final String ID_PROJECTS_VIEW = "org.eclipse.dltk.tcl.ui.Projects";
	public static final String ID_PACKAGES_VIEW = "org.eclipse.dltk.tcl.ui.extbrowsing";
	public static final String ID_MEMBERS_VIEW = "org.eclipse.dltk.tcl.ui.Members";

	/*
	 * XXX: This is a workaround for:
	 * http://dev.eclipse.org/bugs/show_bug.cgi?id=13070
	 */
	static IModelElement fgModelElementFromAction;

	/**
	 * Constructs a new Default layout engine.
	 */
	public TclBrowsingPerspectiveFactory() {
		super();
	}

	@Override
	public void createInitialLayout(IPageLayout layout) {
		createHorizontalLayout(layout);

		// views - standard workbench
		layout.addShowViewShortcut(IPageLayout.ID_OUTLINE);
		layout.addShowViewShortcut(IPageLayout.ID_PROBLEM_VIEW);
		layout.addShowViewShortcut(IPageLayout.ID_RES_NAV);
		layout.addShowViewShortcut(IPageLayout.ID_TASK_LIST);
		layout.addShowViewShortcut(IProgressConstants.PROGRESS_VIEW_ID);
		addActionSets(layout);
		addShowViewShortcuts(layout);
		addNewWizardShortcuts(layout);
	}

	private void createHorizontalLayout(IPageLayout layout) {
		String relativePartId = IPageLayout.ID_EDITOR_AREA;
		int relativePos = IPageLayout.TOP;

		if (shouldShowProjectsView()) {
			layout.addView(ID_PROJECTS_VIEW, IPageLayout.TOP, (float) 0.25,
					IPageLayout.ID_EDITOR_AREA);
			relativePartId = ID_PROJECTS_VIEW;
			relativePos = IPageLayout.RIGHT;
		}
		if (shouldShowPackagesView()) {
			layout.addView(ID_PACKAGES_VIEW, relativePos, (float) 0.25,
					relativePartId);
			relativePartId = ID_PACKAGES_VIEW;
			relativePos = IPageLayout.RIGHT;
		}
		layout.addView(ID_MEMBERS_VIEW, IPageLayout.RIGHT, (float) 0.75,
				ID_PACKAGES_VIEW);

		IPlaceholderFolderLayout placeHolderLeft = layout
				.createPlaceholderFolder("left", IPageLayout.LEFT, (float) 0.25, //$NON-NLS-1$
						IPageLayout.ID_EDITOR_AREA);
		placeHolderLeft.addPlaceholder(IPageLayout.ID_OUTLINE);
		placeHolderLeft.addPlaceholder(DLTKUIPlugin.ID_SCRIPTEXPLORER);
		placeHolderLeft.addPlaceholder(IPageLayout.ID_RES_NAV);

		IPlaceholderFolderLayout placeHolderBottom = layout
				.createPlaceholderFolder("bottom", IPageLayout.BOTTOM, //$NON-NLS-1$
						(float) 0.75, IPageLayout.ID_EDITOR_AREA);
		placeHolderBottom.addPlaceholder(IPageLayout.ID_PROBLEM_VIEW);
		placeHolderBottom.addPlaceholder(IPageLayout.ID_BOOKMARKS);

		placeHolderBottom.addPlaceholder(IProgressConstants.PROGRESS_VIEW_ID);
	}

	protected void addNewWizardShortcuts(IPageLayout layout) {
		layout.addNewWizardShortcut(TclProjectCreationWizard.ID_WIZARD);
		layout.addNewWizardShortcut(TclFileCreationWizard.ID_WIZARD);

		layout.addNewWizardShortcut(TclPerspective.ID_NEW_SOURCE_WIZARD);
		layout.addNewWizardShortcut(TclPerspective.ID_NEW_PACKAGE_WIZARD);

		layout.addNewWizardShortcut(TclPerspective.NEW_FOLDER_WIZARD);
		layout.addNewWizardShortcut(TclPerspective.NEW_FILE_WIZARD);
		layout.addNewWizardShortcut(
				TclPerspective.NEW_UNTITLED_TEXT_FILE_WIZARD);
	}

	protected void addShowViewShortcuts(IPageLayout layout) {
		layout.addShowViewShortcut(IPageLayout.ID_OUTLINE);
		layout.addShowViewShortcut(IPageLayout.ID_PROBLEM_VIEW);

		layout.addShowViewShortcut(IPageLayout.ID_TASK_LIST);
		layout.addShowViewShortcut(IProgressConstants.PROGRESS_VIEW_ID);
	}

	protected void addActionSets(IPageLayout layout) {
		layout.addActionSet(IPageLayout.ID_NAVIGATE_ACTION_SET);
		layout.addActionSet(TclUI.ID_ACTION_SET);
	}

	private boolean shouldShowProjectsView() {
		return fgModelElementFromAction == null || fgModelElementFromAction
				.getElementType() == IModelElement.SCRIPT_MODEL;
	}

	private boolean shouldShowPackagesView() {
		if (fgModelElementFromAction == null)
			return true;
		int type = fgModelElementFromAction.getElementType();
		return type == IModelElement.SCRIPT_MODEL
				|| type == IModelElement.SCRIPT_PROJECT
				|| type == IModelElement.PROJECT_FRAGMENT;
	}

	/*
	 * XXX: This is a workaround for:
	 * http://dev.eclipse.org/bugs/show_bug.cgi?id=13070
	 */
	static void setInputFromAction(IAdaptable input) {
		if (input instanceof IModelElement)
			fgModelElementFromAction = (IModelElement) input;
		else
			fgModelElementFromAction = null;
	}
}
