/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.xotcl.internal.ui.wizards;

import org.eclipse.osgi.util.NLS;

public final class XOTclWizardMessages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.dltk.xotcl.internal.ui.wizards.XOTclWizardMessages";//$NON-NLS-1$	

	private XOTclWizardMessages() {
	}

	// File
	public static String NewFileWizard_title;
	public static String NewFilePage_title;
	public static String NewFilePage_description;
	
	// Class	
	public static String NewClassWizard_title;
	public static String NewClassPage_title;
	public static String NewClassPage_description;
	
	// XOTcl specific
	public static String XOTcl_module_prefix;

	
	static {
		NLS.initializeMessages(BUNDLE_NAME, XOTclWizardMessages.class);
	}
}
