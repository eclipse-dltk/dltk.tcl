/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.tcl.ui.text;

import org.eclipse.dltk.tcl.core.TclConstants;
import org.eclipse.jface.text.IDocument;

public final class TclPartitions {
	private TclPartitions() {

	}

	public static final String TCL_PARTITIONING = TclConstants.TCL_PARTITIONING;
	public static final String TCL_COMMENT = "__tcl_comment"; //$NON-NLS-1$
	public static final String TCL_STRING = "__tcl_string"; //$NON-NLS-1$
	public static final String TCL_INNER_CODE = "__tcl_inner_code"; //$NON-NLS-1$

	public final static String[] TCL_PARTITION_TYPES = new String[] {
			IDocument.DEFAULT_CONTENT_TYPE, TCL_STRING, TCL_COMMENT };
}
