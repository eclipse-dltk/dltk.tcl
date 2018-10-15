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
package org.eclipse.dltk.tcl.core;

public class TclCorePreferences {

	public static final String CHECK_CONTENT_EMPTY_EXTENSION_LOCAL = "check_content.empty_extension.local";

	public static final String CHECK_CONTENT_EMPTY_EXTENSION_REMOTE = "check_content.empty_extension.remote";

	public static final String CHECK_CONTENT_ANY_EXTENSION_LOCAL = "check_content.any_extension.local";

	public static final String CHECK_CONTENT_ANY_EXTENSION_REMOTE = "check_content.any_extension.remote";

	public static final String CHECK_CONTENT_EXCLUDES = "check_content.excludes";

	/**
	 * Number of milliseconds to cache packages for local interpreter.
	 */
	public static final String PACKAGES_REFRESH_INTERVAL_LOCAL = "packages.refreshInterval.local";

	/**
	 * Number of milliseconds to cache packages for remote interpreter.
	 */
	public static final String PACKAGES_REFRESH_INTERVAL_REMOTE = "packages.refreshInterval.remote";

	public static final char CHECK_CONTENT_EXCLUDE_SEPARATOR = ';';

	public static boolean USE_PACKAGE_CONCEPT = true;

}
