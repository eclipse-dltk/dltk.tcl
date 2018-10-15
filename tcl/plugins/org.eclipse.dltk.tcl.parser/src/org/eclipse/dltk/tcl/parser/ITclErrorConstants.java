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
package org.eclipse.dltk.tcl.parser;

import org.eclipse.dltk.compiler.problem.ProblemSeverity;

public interface ITclErrorConstants {
	public static final int DEFAULT = 0;
	public static final int UNKNOWN = 2;
	public static final int UNKNOWN_COMMAND = 3;
	public static final int BAD_COMMAND = 4;
	public static final int INVALID_ARGUMENT_COUNT = 5;
	public static final int EXTRA_ARGUMENTS = 6;
	public static final int MISSING_ARGUMENT = 7;

	public static final int ARGUMENT_SPECIFIED_AFTER_ARGS = 8;
	public static final int ARGS_CANNOT_BE_DEFAULTED = 9;
	public static final int TOO_MANY_FIELDS_IN_ARGUMENT_SPECIFIER = 10;
	public static final int INVALID_ARGUMENT_VALUE = 11;
	public static final int COMMAND_WITH_NAME_SUBSTITUTION = 12;
	public static final int INVALID_COMMAND_VERSION = 13;
	public static final int DEPRECATED_COMMAND = 14;
	public static final int COMMAND_OUT_OF_SCOPE = 15;

	public static final ProblemSeverity ERROR = ProblemSeverity.ERROR;
	public static final ProblemSeverity WARNING = ProblemSeverity.WARNING;
}
