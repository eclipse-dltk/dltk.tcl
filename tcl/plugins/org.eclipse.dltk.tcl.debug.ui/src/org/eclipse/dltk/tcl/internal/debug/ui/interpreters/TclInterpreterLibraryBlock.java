/*******************************************************************************
 * Copyright (c) 2000, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.debug.ui.interpreters;

import org.eclipse.dltk.internal.debug.ui.interpreters.AbstractInterpreterLibraryBlock;
import org.eclipse.dltk.internal.debug.ui.interpreters.AddScriptInterpreterDialog;
import org.eclipse.dltk.internal.debug.ui.interpreters.LibraryContentProvider;
import org.eclipse.dltk.internal.debug.ui.interpreters.LibraryLabelProvider;
import org.eclipse.jface.viewers.IBaseLabelProvider;

/**
 * Control used to edit the libraries associated with a Interpreter install
 */
public class TclInterpreterLibraryBlock extends AbstractInterpreterLibraryBlock {

	public TclInterpreterLibraryBlock(AddScriptInterpreterDialog d) {
		super(d);
	}

	@Override
	protected IBaseLabelProvider getLabelProvider() {
		return new LibraryLabelProvider();
		// return new TclLibraryLabelProvider();
	}

	@Override
	protected LibraryContentProvider createLibraryContentProvider() {
		return new LibraryContentProvider();
		// return new TclLibraryContentProvider();
	}

	// protected TreeViewer createViewer(Composite comp) {
	// return new TreeViewer(comp);
	// }

	@Override
	protected boolean isEnableButtonSupported() {
		return false;
	}

	@Override
	protected boolean isDefaultLocations() {
		return false;
	}
}
