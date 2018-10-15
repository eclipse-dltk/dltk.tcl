/*******************************************************************************
 * Copyright (c) 2008 xored software, Inc.  
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0  
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Andrei Sobolev)
 *******************************************************************************/
package org.eclipse.dltk.tcl.parser.definitions;

import java.util.List;

import org.eclipse.dltk.tcl.ast.StringArgument;
import org.eclipse.dltk.tcl.ast.TclArgument;
import org.eclipse.dltk.tcl.ast.TclCommand;
import org.eclipse.dltk.tcl.definitions.Command;

public class BuiltinCommandUtils {
	public static String getNamespaceEvalName(TclCommand command,
			Command definition) {
		List<TclArgument> list = command.getArguments();
		TclArgument argument = list.get(0);
		if (argument instanceof StringArgument) {
			if ("eval".equals(((StringArgument) argument).getValue())) {
				TclArgument namespaceName = list.get(1);
				if( namespaceName instanceof StringArgument ) {
					return ((StringArgument)namespaceName).getValue();
				}
			}
		}
		return null;
	}
}
