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
package org.eclipse.dltk.tcl.internal.parser.raw;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.dltk.tcl.internal.parser.raw.messages"; //$NON-NLS-1$
	public static String BracesSubstitution_Error;
	public static String CommandSubstitution_0;
	public static String QuotesSubstitution_1;
	public static String SimpleTclParser_ExtraCharactersAfterCloseBrace;
	public static String SimpleTclParser_ExtraCharactersAfterCloseQuote;
	public static String VariableSubstitution_BracesVariableName;
	public static String VariableSubstitution_VariableIndex;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
