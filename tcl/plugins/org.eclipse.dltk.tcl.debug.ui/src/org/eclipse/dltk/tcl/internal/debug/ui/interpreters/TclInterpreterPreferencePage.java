/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.debug.ui.interpreters;

import org.eclipse.dltk.internal.debug.ui.interpreters.ScriptInterpreterPreferencePage;
import org.eclipse.dltk.internal.debug.ui.interpreters.InterpretersBlock;

public class TclInterpreterPreferencePage extends ScriptInterpreterPreferencePage {

	public static final String PAGE_ID = "org.eclipse.dltk.tcl.preferences.interpreters";

	public InterpretersBlock createInterpretersBlock() {
		return new TclInterpretersBlock();
	}
}
