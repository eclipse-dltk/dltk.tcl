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

package org.eclipse.dltk.tcl.internal.validators;

import org.eclipse.dltk.tcl.parser.ITclErrorConstants;

public interface ICheckKinds extends ITclErrorConstants {
	public static final int CHECK_UNDEFINED_PROC = 10000;
	public static final int CHECK_UNDEFINED_VARIABLE = CHECK_UNDEFINED_PROC + 1;
	public static final int CHECK_TOO_MANY_FIELD_ARG = CHECK_UNDEFINED_PROC + 2;
	public static final int CHECK_BAD_ARG_DEFINITION = CHECK_UNDEFINED_PROC + 3;
	public static final int CHECK_NON_DEF_AFTER_DEF = CHECK_UNDEFINED_PROC + 4;
	public static final int CHECK_ARG_AFTER_ARGS = CHECK_UNDEFINED_PROC + 5;
	public static final int CHECK_ARGS_DEFAULT = CHECK_UNDEFINED_PROC + 6;
	public static final int CHECK_SAME_ARG_NAME = CHECK_UNDEFINED_PROC + 7;
	public static final int UNREACHABLE_CODE = 8;
	public static final int BUILTIN_COMMAND_REDEFINITION = 9;
	public static final int USER_COMMAND_REDEFINITION = 10;
}
