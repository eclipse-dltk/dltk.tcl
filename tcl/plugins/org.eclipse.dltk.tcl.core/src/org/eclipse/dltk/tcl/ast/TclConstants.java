/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.tcl.ast;

import org.eclipse.dltk.ast.expressions.ExpressionConstants;

public final class TclConstants {
	
	private TclConstants() {
	}

	public static final int TCL_STATEMENT = ExpressionConstants.USER_EXPRESSION_START + 1;

	public static final int TCL_STATEMENTS = ExpressionConstants.USER_EXPRESSION_START + 2;

	public static final int TCL_BLOCK_EXPRESSION = ExpressionConstants.USER_EXPRESSION_START + 3;

	public static final int TCL_EXECUTE_EXPRESSION = ExpressionConstants.USER_EXPRESSION_START + 4;

	public static final int TCL_IDENTIFIER_EXPRESSION = ExpressionConstants.USER_EXPRESSION_START + 5;
		
	public final static int TCL_FIELD_TYPE_NAMESPACE = ExpressionConstants.USER_EXPRESSION_START + 8;

	public static final int TCL_FIELD_TYPE_INDEX = ExpressionConstants.USER_EXPRESSION_START + 9;
}
