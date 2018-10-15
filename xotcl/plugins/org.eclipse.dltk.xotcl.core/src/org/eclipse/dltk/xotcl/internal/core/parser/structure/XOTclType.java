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
package org.eclipse.dltk.xotcl.internal.core.parser.structure;

import org.eclipse.dltk.tcl.structure.ITclModelBuildContext;
import org.eclipse.dltk.xotcl.internal.core.parser.structure.XOTclNames.XOTclTypeInfo;

public class XOTclType {

	private static final String ATTR = XOTclType.class.getName();

	/**
	 * @param context
	 * @return
	 */
	public static XOTclType get(ITclModelBuildContext context) {
		return (XOTclType) context.getAttribute(ATTR);
	}

	private final String name;
	private final XOTclTypeInfo info;

	/**
	 * @param name
	 * @param info
	 * @param nameEnd
	 * @param nameStart
	 */
	XOTclType(String name, XOTclTypeInfo info) {
		this.name = name;
		this.info = info;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	public String getSimpleName() {
		if (info != null) {
			return info.getSimpleName();
		} else {
			int pos = name.lastIndexOf("::");
			if (pos >= 0) {
				return name.substring(pos + 2);
			} else {
				return name;
			}
		}
	}

	/**
	 * @param context
	 */
	public void saveTo(ITclModelBuildContext context) {
		context.setAttribute(ATTR, this);
	}

}
