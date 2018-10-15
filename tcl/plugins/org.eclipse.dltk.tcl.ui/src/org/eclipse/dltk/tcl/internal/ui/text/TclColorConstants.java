/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.ui.text;

import org.eclipse.dltk.ui.text.DLTKColorConstants;

public final class TclColorConstants {
	private TclColorConstants() {
	}

	/**
	 * The color key for string and character literals in TCL code (value
	 * <code>"tcl_string"</code>).
	 */
	public static final String TCL_STRING = DLTKColorConstants.DLTK_STRING;

	/**
	 * The color key for TCL comments (value <code>"tcl_comment"</code>).
	 */
	public static final String TCL_SINGLE_LINE_COMMENT = DLTKColorConstants.DLTK_SINGLE_LINE_COMMENT;

	/**
	 * The color key for TCL variable (value <code>"tcl_variable"</code>).
	 */
	public static final String TCL_VARIABLE = "tcl_variable"; //$NON-NLS-1$

	/**
	 * The color key for TCL numbers (value <code>"tcl_number"</code>).
	 */
	public static final String TCL_NUMBER = DLTKColorConstants.DLTK_NUMBER;

	/**
	 * The color key for TCL keywords (value <code>"tcl_keyword"</code>).
	 */
	public static final String TCL_KEYWORD = DLTKColorConstants.DLTK_KEYWORD;

	/**
	 * The color key for TCL 'return' keyword (value <code>"tcl_return"</code>).
	 */
	public static final String TCL_KEYWORD_RETURN = DLTKColorConstants.DLTK_KEYWORD_RETURN;
	
	/**
	 * The color key for TCL code (value <code>"tcl_default"</code>).
	 */
	public static final String TCL_DEFAULT = DLTKColorConstants.DLTK_DEFAULT;
	
	/**
	 * The color key for TO-DO tasks in comments.
	 */
	public static final String TCL_TODO_TAG = DLTKColorConstants.TASK_TAG;
	
	/**
	 * The color key for TCL classes
	 */
	public static final String TCL_CLASS = DLTKColorConstants.DLTK_CLASS_DEFINITION;
	
	/**
	 * The color key for TCL procedures
	 */
	public static final String TCL_PROCEDURE = DLTKColorConstants.DLTK_FUNCTION_DEFINITION;
	
	/**
	 * The color key for arguments of TCL procedures
	 */
	public static final String TCL_ARGUMENT = DLTKColorConstants.DLTK_ARGUMENT;

	/**
	 * The color key for base classes in TCL class declarations
	 */
	public static final String TCL_BASE_CLASS = DLTKColorConstants.DLTK_BASE_CLASS;

	
}
