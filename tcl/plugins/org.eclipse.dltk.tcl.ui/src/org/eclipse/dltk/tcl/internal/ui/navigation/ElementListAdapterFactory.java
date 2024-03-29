/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.ui.navigation;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.tcl.internal.ui.navigation.ElementsView.ElementList;

public class ElementListAdapterFactory implements IAdapterFactory {
	private static Class<?>[] PROPERTIES = new Class[] { IModelElement.class };

	@Override
	public Class<?>[] getAdapterList() {
		return PROPERTIES;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getAdapter(Object element, Class<T> key) {
		if (IModelElement.class.equals(key)) {
			if (element instanceof ElementList) {
				return (T) ((ElementList) element).getFirstElement();
			}
		}
		return null;
	}
}
