/*******************************************************************************
 * Copyright (c) 2009, 2017 xored software, Inc. and others.
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
package org.eclipse.dltk.itcl.internal.core.parser.structure.model.impl;

import org.eclipse.dltk.itcl.internal.core.IIncrTclModifiers;
import org.eclipse.dltk.itcl.internal.core.parser.structure.model.IVariable;

public class Variable extends Member implements IVariable {

	private VariableKind kind = VariableKind.VARIABLE;

	@Override
	public VariableKind getKind() {
		return kind;
	}

	@Override
	public void setKind(VariableKind kind) {
		this.kind = kind;
	}

	@Override
	public int getModifiers() {
		return getVisibility().getModifiers() | IIncrTclModifiers.AccIncrTcl;
	}

}
