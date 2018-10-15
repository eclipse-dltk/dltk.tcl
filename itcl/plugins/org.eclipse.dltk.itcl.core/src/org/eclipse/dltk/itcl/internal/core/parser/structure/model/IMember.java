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
package org.eclipse.dltk.itcl.internal.core.parser.structure.model;

import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.tcl.ast.Node;

public interface IMember {

	public enum Visibility {
		PUBLIC(Modifiers.AccPublic), PROTECTED(Modifiers.AccProtected), PRIVATE(
				Modifiers.AccPrivate);

		private int modifiers;

		Visibility(int modifiers) {
			this.modifiers = modifiers;
		}

		public int getModifiers() {
			return modifiers;
		}
	}

	String getName();

	void setName(String name);

	int getNameStart();

	int getNameEnd();

	int getStart();

	int getEnd();

	void setNameStart(int nameStart);

	void setNameEnd(int nameEnd);

	void setStart(int start);

	void setEnd(int end);

	void setNameRange(Node node);

	void setRange(Node node);

	void setRange(IRange range);

	Visibility getVisibility();

	void setVisibility(Visibility visibility);

	int getModifiers();

}
