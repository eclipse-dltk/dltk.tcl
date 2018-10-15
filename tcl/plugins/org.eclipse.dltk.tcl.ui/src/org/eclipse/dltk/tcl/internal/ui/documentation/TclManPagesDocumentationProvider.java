/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Andrei Sobolev)
 *     xored software, Inc. - TCL ManPageFolder management refactoring (Alex Panchenko <alex@xored.com>)
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.ui.documentation;

import java.io.File;
import java.io.Reader;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.IMember;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IProjectFragment;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.launching.IInterpreterInstall;
import org.eclipse.dltk.launching.ScriptRuntime;
import org.eclipse.dltk.tcl.internal.ui.TclUI;
import org.eclipse.dltk.tcl.ui.manpages.Documentation;
import org.eclipse.dltk.tcl.ui.manpages.InterpreterDocumentation;
import org.eclipse.dltk.tcl.ui.manpages.ManPageFolder;
import org.eclipse.dltk.tcl.ui.manpages.ManPageLoader;
import org.eclipse.dltk.tcl.ui.manpages.ManPageResource;
import org.eclipse.dltk.tcl.ui.manpages.ManpagesPackage;
import org.eclipse.dltk.ui.documentation.DocumentationFileResponse;
import org.eclipse.dltk.ui.documentation.DocumentationUtils;
import org.eclipse.dltk.ui.documentation.IDocumentationResponse;
import org.eclipse.dltk.ui.documentation.IScriptDocumentationProvider;
import org.eclipse.dltk.ui.documentation.IScriptDocumentationProviderExtension;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;

public class TclManPagesDocumentationProvider implements
		IScriptDocumentationProvider, IScriptDocumentationProviderExtension {

	private ManPageResource manPages = null;

	@Override
	public Reader getInfo(IMember element, boolean lookIntoParents,
			boolean lookIntoExternal) {
		final IProjectFragment fragment = (IProjectFragment) element
				.getAncestor(IModelElement.PROJECT_FRAGMENT);
		if (fragment != null && fragment.isBuiltin()) {
			return DocumentationUtils.getReader(
					describeKeyword(element.getElementName(), element));
		}

		return null;
	}

	@Override
	public Reader getInfo(String content) {
		return DocumentationUtils.getReader(describeKeyword(content, null));
	}

	private IPropertyChangeListener changeListener = null;

	protected void initalizeLocations(boolean force) {
		if (!force && this.manPages != null)
			return;

		final IPreferenceStore prefStore = TclUI.getDefault()
				.getPreferenceStore();

		this.manPages = ManPageLoader.load();
		if (this.manPages != null && changeListener == null) {
			changeListener = event -> initalizeLocations(true);
			prefStore.addPropertyChangeListener(changeListener);
		}
	}

	private Documentation selectSource(IModelElement context) {
		initalizeLocations(false);
		if (manPages == null) {
			return null;
		}
		if (context != null) {
			final IScriptProject project = context.getScriptProject();
			if (project != null) {
				IInterpreterInstall install;
				try {
					install = ScriptRuntime.getInterpreterInstall(project);
				} catch (CoreException e) {
					TclUI.error(e);
					install = null;
				}
				if (install != null) {
					final InterpreterDocumentation iDoc = (InterpreterDocumentation) install
							.findExtension(
									ManpagesPackage.Literals.INTERPRETER_DOCUMENTATION);
					if (iDoc != null) {
						final Documentation doc = manPages
								.findById(iDoc.getDocumentationId());
						if (doc != null) {
							return doc;
						}
					}
				}
			}
		}
		return manPages.findDefault();
	}

	@Override
	public IDocumentationResponse describeKeyword(String keyword,
			IModelElement context) {
		final Documentation documentation = selectSource(context);
		if (documentation != null) {
			for (ManPageFolder f : documentation.getFolders()) {
				String filename = f.getKeywords().get(keyword);
				if (filename == null) {
					// Try to use first word only
					final int spacePos = keyword.indexOf(' ');
					if (spacePos != -1) {
						filename = f.getKeywords()
								.get(keyword.substring(0, spacePos));
					}
				}
				if (filename != null) {
					final File file = new File(f.getPath(), filename);
					if (file.isFile()) {
						return new DocumentationFileResponse(keyword, file);
					}
				}
			}
		}
		return null;
	}
}
