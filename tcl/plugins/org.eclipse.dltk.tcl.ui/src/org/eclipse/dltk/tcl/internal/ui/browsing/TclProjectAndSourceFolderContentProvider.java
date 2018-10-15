/*******************************************************************************
 * Copyright (c) 2009, 2017 xored software, Inc. and others.
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
package org.eclipse.dltk.tcl.internal.ui.browsing;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.core.IProjectFragment;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.tcl.internal.core.sources.TclSourcesFragment;
import org.eclipse.dltk.ui.browsing.ProjectAndSourceFolderContentProvider;
import org.eclipse.dltk.ui.browsing.ScriptBrowsingPart;

public class TclProjectAndSourceFolderContentProvider
		extends ProjectAndSourceFolderContentProvider {

	public TclProjectAndSourceFolderContentProvider(
			ScriptBrowsingPart browsingPart,
			IDLTKLanguageToolkit languageToolkit) {
		super(browsingPart, languageToolkit);
	}

	@Override
	protected Object[] getProjectFragments(IScriptProject project)
			throws ModelException {
		if (!project.getProject().isOpen())
			return NO_CHILDREN;
		IProjectFragment[] roots = project.getProjectFragments();
		final List<IProjectFragment> list = new ArrayList<>(roots.length);
		// filter out package fragments that correspond to projects and
		// replace them with the package fragments directly
		for (int i = 0; i < roots.length; i++) {
			IProjectFragment root = roots[i];
			if (!isProjectProjectFragment(root)) {
				if (!(root instanceof TclSourcesFragment)) {
					// FIXME Exclude "Sources" element for now
					list.add(root);
				}
			}
		}
		return list.toArray();
	}

}
