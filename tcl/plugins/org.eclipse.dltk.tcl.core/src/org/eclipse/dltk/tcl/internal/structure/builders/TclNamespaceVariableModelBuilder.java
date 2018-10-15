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
package org.eclipse.dltk.tcl.internal.structure.builders;

import org.eclipse.dltk.tcl.ast.TclArgument;
import org.eclipse.dltk.tcl.ast.TclCommand;
import org.eclipse.dltk.tcl.structure.AbstractTclCommandModelBuilder;
import org.eclipse.dltk.tcl.structure.ITclModelBuildContext;

/**
 * <quote>This command is normally used within a <code>>namespace eval</code>
 * command to create one or more variables within a namespace. Each variable
 * name is initialized with value. The value for the last variable is
 * optional<quote>
 */
public class TclNamespaceVariableModelBuilder extends
		AbstractTclCommandModelBuilder {

	public boolean process(TclCommand command, ITclModelBuildContext context) {
		for (int i = 0; i < command.getArguments().size(); i += 2) {
			final TclArgument arg = command.getArguments().get(i);
			processField(command, arg, asSymbol(arg), 0, context);
		}
		return false;
	}
}
