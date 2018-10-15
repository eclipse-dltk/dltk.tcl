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

import java.util.List;

import org.eclipse.dltk.itcl.internal.core.parser.structure.model.IMember.Visibility;

public interface IClass {

	String getName();

	Visibility peekVisibility();

	void pushVisibility(Visibility visibility);

	Visibility popVisibility();

	void addSuperclass(String name);

	void addMember(IMember member);

	String[] getSuperClasses();

	List<IMember> getMembers();

	IMethod findMethod(String name);

}
