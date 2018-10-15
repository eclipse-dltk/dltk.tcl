/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.debug.ui.interpreters;

import org.eclipse.dltk.debug.ui.launchConfigurations.IMainLaunchConfigurationTab;
import org.eclipse.dltk.debug.ui.launchConfigurations.InterpreterTab;
import org.eclipse.dltk.internal.debug.ui.interpreters.AbstractInterpreterComboBlock;
import org.eclipse.dltk.internal.debug.ui.interpreters.IInterpreterComboBlockContext;

public class TclInterpreterTab extends InterpreterTab {

	/**
	 * @since 2.0
	 */
	public TclInterpreterTab(IMainLaunchConfigurationTab mainTab) {
		super(mainTab);
	}

	@Override
	protected AbstractInterpreterComboBlock createInterpreterBlock(
			IInterpreterComboBlockContext context) {
		return new TclInterpreterComboBlock(context);
	}

	@Override
	protected void refreshInterpreters() {
		((TclInterpreterComboBlock) fInterpreterBlock)
				.initialize(getScriptProject());
		super.refreshInterpreters();
	}

}
