/*******************************************************************************
 * Copyright (c) 2008, 2017 xored software, Inc. and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.tclchecker.v5;

import java.util.Collections;
import java.util.List;

public class WordToken implements IToken {

	private final String text;

	public WordToken(String text) {
		this.text = text;
	}

	@Override
	public List<IToken> getChildren() {
		return Collections.emptyList();
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public boolean hasChildren() {
		return false;
	}

	@Override
	public String toString() {
		return getText();
	}

}
