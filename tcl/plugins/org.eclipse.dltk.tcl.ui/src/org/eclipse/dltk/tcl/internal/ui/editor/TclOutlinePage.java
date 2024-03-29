/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.ui.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.internal.ui.editor.ScriptEditor;
import org.eclipse.dltk.internal.ui.editor.ScriptOutlinePage;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.dltk.ui.actions.MemberFilterActionGroup;
import org.eclipse.dltk.ui.viewsupport.MemberFilterAction;
import org.eclipse.dltk.ui.viewsupport.ModelElementFilter;
import org.eclipse.dltk.ui.viewsupport.ModelElementFlagsFilter;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.ui.IActionBars;

public class TclOutlinePage extends ScriptOutlinePage {

	public TclOutlinePage(ScriptEditor editor, IPreferenceStore store) {
		super(editor, store);
	}

	@Override
	protected void registerSpecialToolbarActions(IActionBars actionBars) {
		IToolBarManager toolBarManager = actionBars.getToolBarManager();
		MemberFilterActionGroup fMemberFilterActionGroup = new MemberFilterActionGroup(
				fOutlineViewer, fStore);
		List<MemberFilterAction> actions = new ArrayList<>(3);

		// variables
		// TODO help support IDLTKHelpContextIds.FILTER_FIELDS_ACTION;
		MemberFilterAction hideVariables = new MemberFilterAction(
				fMemberFilterActionGroup,
				ActionMessages.MemberFilterActionGroup_hide_variables_label,
				new ModelElementFilter(IModelElement.FIELD), null, true);
		hideVariables.setDescription(
				ActionMessages.MemberFilterActionGroup_hide_variables_description);
		hideVariables.setToolTipText(
				ActionMessages.MemberFilterActionGroup_hide_variables_tooltip);
		DLTKPluginImages.setLocalImageDescriptors(hideVariables,
				"filter_fields.gif"); //$NON-NLS-1$
		actions.add(hideVariables);

		// procedures
		// TODO help support IDLTKHelpContextIds.FILTER_STATIC_ACTION;
		MemberFilterAction hideProcedures = new MemberFilterAction(
				fMemberFilterActionGroup,
				ActionMessages.MemberFilterActionGroup_hide_procedures_label,
				new ModelElementFilter(IModelElement.METHOD), null, true);
		hideProcedures.setDescription(
				ActionMessages.MemberFilterActionGroup_hide_procedures_description);
		hideProcedures.setToolTipText(
				ActionMessages.MemberFilterActionGroup_hide_procedures_tooltip);
		// TODO: add correct icon
		DLTKPluginImages.setLocalImageDescriptors(hideProcedures,
				"filter_methods.gif"); //$NON-NLS-1$
		actions.add(hideProcedures);

		// namespaces
		// TODO help support IDLTKHelpContextIds.FILTER_PUBLIC_ACTION;
		MemberFilterAction hideNamespaces = new MemberFilterAction(
				fMemberFilterActionGroup,
				ActionMessages.MemberFilterActionGroup_hide_namespaces_label,
				new ModelElementFilter(IModelElement.TYPE), null, true);
		hideNamespaces.setDescription(
				ActionMessages.MemberFilterActionGroup_hide_namespaces_description);
		hideNamespaces.setToolTipText(
				ActionMessages.MemberFilterActionGroup_hide_namespaces_tooltip);
		DLTKPluginImages.setLocalImageDescriptors(hideNamespaces,
				"filter_classes.gif"); //$NON-NLS-1$
		actions.add(hideNamespaces);

		// private
		MemberFilterAction hidePrivate = new MemberFilterAction(
				fMemberFilterActionGroup,
				ActionMessages.MemberFilterActionGroup_hide_private_label,
				new ModelElementFlagsFilter(Modifiers.AccPrivate), null, true);
		hidePrivate.setDescription(
				ActionMessages.MemberFilterActionGroup_hide_private_description);
		hidePrivate.setToolTipText(
				ActionMessages.MemberFilterActionGroup_hide_private_tooltip);
		DLTKPluginImages.setLocalImageDescriptors(hidePrivate,
				"filter_private.gif"); //$NON-NLS-1$
		actions.add(hidePrivate);

		// order corresponds to ordeutilusr in toolbar
		MemberFilterAction[] fFilterActions = actions
				.toArray(new MemberFilterAction[actions.size()]);
		fMemberFilterActionGroup.setActions(fFilterActions);
		fMemberFilterActionGroup.contributeToToolBar(toolBarManager);
	}

	@Override
	protected ILabelDecorator getLabelDecorator() {
		return new TclOutlineLabelDecorator();
	}
}
