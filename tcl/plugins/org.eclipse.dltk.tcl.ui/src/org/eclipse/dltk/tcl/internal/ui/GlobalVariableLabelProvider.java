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
package org.eclipse.dltk.tcl.internal.ui;

import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * @since 2.0
 */
public class GlobalVariableLabelProvider extends LabelProvider {

	@Override
	public Image getImage(Object element) {
		return DLTKPluginImages.get(DLTKPluginImages.IMG_FIELD_PUBLIC);
	}

	@Override
	public String getText(Object element) {
		if (element instanceof GlobalVariableEntry) {
			GlobalVariableEntry var = (GlobalVariableEntry) element;
			return var.getName() + "=" + var.getValue(); //$NON-NLS-1$
		}
		return null;
	}

}
