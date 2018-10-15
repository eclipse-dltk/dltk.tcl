/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.ui.editor;

import org.eclipse.osgi.util.NLS;

public final class ActionMessages extends NLS {

	private static final String BUNDLE_NAME= "org.eclipse.dltk.tcl.internal.ui.editor.ActionMessages";//$NON-NLS-1$

	private ActionMessages() {
		// Do not instantiate
	}

	public static String MemberFilterActionGroup_hide_variables_label;
	public static String MemberFilterActionGroup_hide_variables_tooltip;
	public static String MemberFilterActionGroup_hide_variables_description;
	public static String MemberFilterActionGroup_hide_procedures_label;
	public static String MemberFilterActionGroup_hide_procedures_tooltip;
	public static String MemberFilterActionGroup_hide_procedures_description;
	public static String MemberFilterActionGroup_hide_namespaces_label;
	public static String MemberFilterActionGroup_hide_namespaces_tooltip;
	public static String MemberFilterActionGroup_hide_namespaces_description;
	public static String MemberFilterActionGroup_hide_private_label;
	public static String MemberFilterActionGroup_hide_private_tooltip;
	public static String MemberFilterActionGroup_hide_private_description;
	//public static String MemberFilterActionGroup_hide_classes_label;
	//public static String MemberFilterActionGroup_hide_classes_tooltip;
	//public static String MemberFilterActionGroup_hide_classes_description;

	public static String TclEditor_MatchingBracketOutsideSelectedElement;
	public static String TclEditor_NoBracketSelected;
	public static String TclEditor_NoMatchongBracket;

	static {
		NLS.initializeMessages(BUNDLE_NAME, ActionMessages.class);
	}

}
