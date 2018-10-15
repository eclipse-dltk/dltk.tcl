/*******************************************************************************
 * Copyright (c) 2008, 2017 xored software, Inc. and others.
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

public class CodeScanner implements ICodeScanner {

	private char[] content;
	private int pos;

	public CodeScanner(String content) {
		if (content != null) {
			this.content = content.toCharArray();
		} else {
			this.content = null;
		}
		pos = 0;
	}

	@Override
	public int read() {
		if (isEOF())
			return EOF;
		char c = content[pos];
		pos++;
		return c;
	}

	@Override
	public boolean isEOF() {
		return pos >= content.length;
	}

	@Override
	public void unread() {
		pos--;
	}

	@Override
	public int getPosition() {
		if (isEOF()) {
			return content.length - 1;
		} else {
			return pos;
		}
	}

}
