/*******************************************************************************
 * Copyright (c) 2005, 2016 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.debug.ui;

import org.eclipse.dltk.debug.ui.ScriptDebugModelPresentation;
import org.eclipse.ui.IEditorInput;

public class TclDebugModelPresentation extends ScriptDebugModelPresentation {
	private static final String TCL_EDITOR_ID = "org.eclipse.dltk.tcl.ui.editor.TclEditor";
	
	@Override
	public String getEditorId(IEditorInput input, Object element) {
		return TCL_EDITOR_ID;
	}
}
