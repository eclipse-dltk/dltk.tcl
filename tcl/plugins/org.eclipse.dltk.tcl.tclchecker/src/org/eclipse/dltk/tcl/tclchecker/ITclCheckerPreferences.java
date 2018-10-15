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
package org.eclipse.dltk.tcl.tclchecker;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.tcl.tclchecker.model.configs.CheckerConfig;
import org.eclipse.dltk.tcl.tclchecker.model.configs.CheckerInstance;

/**
 * Represents the system or project-specific TclChecker preferences.
 */
public interface ITclCheckerPreferences {

	/**
	 * Returns list of the common TclChecker configurations (contributed for the
	 * extension point).
	 * 
	 * @return
	 */
	List<CheckerConfig> getCommonConfigurations();

	/**
	 * Returns the configured TclChecker instances
	 * 
	 * @param environmentId
	 * @return
	 */
	List<CheckerInstance> getInstances();

	/**
	 * Creates new TclChecker instance
	 * 
	 * @return
	 */
	CheckerInstance newInstance();

	/**
	 * Removes the specified TclChecker instance
	 * 
	 * @param instance
	 * @return
	 */
	boolean removeInstance(CheckerInstance instance);

	/**
	 * Saves changes in this object to the permanent storage
	 * 
	 * @throws CoreException
	 */
	void save() throws CoreException;

	/**
	 * Completely deletes TclChecker configuration
	 */
	void delete();

}
