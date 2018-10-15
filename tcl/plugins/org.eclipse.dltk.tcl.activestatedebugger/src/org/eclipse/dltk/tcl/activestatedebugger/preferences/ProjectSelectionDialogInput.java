/*******************************************************************************
 * Copyright (c) 2009 xored software, Inc.
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
package org.eclipse.dltk.tcl.activestatedebugger.preferences;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.tcl.activestatedebugger.InstrumentationUtils;

class ProjectSelectionDialogInput extends SelectionDialogInput {

	final IScriptProject project;

	/**
	 * @param parentProject
	 */
	public ProjectSelectionDialogInput(IProject project) {
		this.project = DLTKCore.create(project);
	}

	public Set<IScriptProject> collectProjects() {
		final Set<IScriptProject> projects = new HashSet<IScriptProject>();
		InstrumentationUtils.collectProjects(projects, project);
		return projects;
	}

	@Override
	public int hashCode() {
		return project.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ProjectSelectionDialogInput) {
			return project.equals(((ProjectSelectionDialogInput) obj).project);
		}
		return false;
	}

}
