/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.tcl.launching;

import org.eclipse.dltk.launching.ScriptLaunchConfigurationConstants;

public final class TclLaunchConfigurationConstants extends
		ScriptLaunchConfigurationConstants {
	
	private TclLaunchConfigurationConstants() {
		
	}
	
	public static final String ID_TCL_SCRIPT = "org.eclipse.dltk.tcl.launching.TCLLaunchConfigurationType"; //$NON-NLS-1$

	public static final String ID_TCL_PROCESS_TYPE = "tclInterpreter"; //$NON-NLS-1$
}
