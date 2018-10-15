/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.ui.wizards;

import org.eclipse.osgi.util.NLS;

public class TclWizardMessages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.dltk.tcl.internal.ui.wizards.TclWizardMessages";//$NON-NLS-1$

	private TclWizardMessages() {
	}

	public static String ProjectCreationWizard_title;

	public static String ProjectCreationWizardFirstPage_title;
	public static String ProjectCreationWizardFirstPage_description;

	public static String TclBuildpathDetector_AnalysingSubTask;

	public static String TclBuildpathDetector_AnalysingTask;

	/**
	 * @since 2.0
	 */
	public static String TclFileCreationWizardTitle;

	public static String TclProjectWizardFirstPage_packageDetector;
	public static String TclProjectWizardFirstPage_packageDetector_checkbox;
	public static String TclProjectWizardFirstPage_packageDetector_description;

	/**
	 * @since 2.0
	 */
	public static String TclProjectWizardFirstPage_LocationGroup_modeTitle;
	/**
	 * @since 2.0
	 */
	public static String TclProjectWizardFirstPage_LocationGroup_locationTitle;

	static {
		NLS.initializeMessages(BUNDLE_NAME, TclWizardMessages.class);
	}
}
