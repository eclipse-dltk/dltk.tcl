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
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.validators.packages;

import org.eclipse.osgi.util.NLS;

/**
 * @author Alexey
 * 
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.dltk.tcl.internal.validators.packages.messages"; //$NON-NLS-1$
	public static String PackageRequireSourceAnalyser_CouldNotDetectPackageCorrectionRequired;
	public static String PackageRequireSourceAnalyser_CouldNotLocateSourcedFile;
	public static String PackageRequireSourceAnalyser_CouldNotLocateSourcedFileCorrectionRequired;
	public static String PackageRequireSourceAnalyser_FolderSourcingNotSupported;
	public static String PackageRequireSourceAnalyser_ModelUpdateFailure;
	public static String PackageRequireSourceAnalyser_SourceNotAddedToBuildpath;
	public static String TclCheckBuilder_interpreterNotFound;
	public static String TclCheckBuilder_processing;
	public static String TclCheckBuilder_retrievePackages;
	public static String TclCheckBuilder_unknownPackage;
	public static String TclCheckBuilder_unresolvedDependencies;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
