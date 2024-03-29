/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.ui.wizards;

import java.io.ByteArrayInputStream;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.tcl.core.TclNature;
import org.eclipse.dltk.ui.wizards.NewPackageCreationWizard;
import org.eclipse.dltk.ui.wizards.NewPackageWizardPage;

public class TclPackageCreationWizard extends NewPackageCreationWizard {
	public static final String ID_WIZARD = "org.eclipse.dltk.tcl.ui.wizards.NewPackageCreationWizard";

	private static final String INDEX_PACKAGE_NAME = "pkgIndex.tcl";

	@Override
	protected NewPackageWizardPage createNewPackageWizardPage() {
		return new NewPackageWizardPage() {
			@Override
			public void createPackage(IProgressMonitor monitor)
					throws CoreException, InterruptedException {
				super.createPackage(monitor);
				if (this.fCreatedScriptFolder != null) {
					IContainer folder = (IContainer) this.fCreatedScriptFolder
							.getResource();
					IFile file = folder.getFile(new Path(INDEX_PACKAGE_NAME));
					file.create(new ByteArrayInputStream(new byte[] {}),
							IResource.FORCE, monitor);
				}
			}

			@Override
			protected String getRequiredNature() {
				return TclNature.NATURE_ID;
			}
		};
	}
}
