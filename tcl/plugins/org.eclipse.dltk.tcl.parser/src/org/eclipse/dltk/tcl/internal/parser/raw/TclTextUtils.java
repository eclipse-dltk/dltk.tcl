/*******************************************************************************
 * Copyright (c) 2008 xored software, Inc.  
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

public class TclTextUtils {

	public static void runToLineEnd(ICodeScanner scanner) {
		boolean esc = false;
		while (true) {
			int c = scanner.read();
			switch (c) {
			case '\\':
				esc = true;
				break;
			case '\r':
				break;
			case '\n':
			case ICodeScanner.EOF:
				if (!esc)
					return;
			default:
				esc = false;
			}
		}
	}

	public static boolean isWhitespace(int c) {
		return (c == ' ' || c == '\t' || c == '\n' || c == '\r');
	}

	public static boolean isTrueWhitespace(int c) {
		return (c == ' ' || c == '\t');
	}

	public static boolean isHexDigit(int c) {
		return ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F'));
	}

	public static boolean isDigit(int c) {
		return (c >= '0' && c <= '9');
	}

	public static boolean isOctDigit(int c) {
		return (c >= '0' && c <= '7');
	}

	public static boolean isIdentifier(int c) {
		return ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')
				|| (c >= '0' && c <= '9') || (c == '-') || (c == '_') || (c == ':'));
	}

	public static boolean isNewLine(ICodeScanner input) {
		int c = input.read();
		if (c == '\n') {
			input.unread();
			return true;
		} else if (c == '\r') {
			c = input.read();
			input.unread();
			input.unread();
			if (c == '\n')
				return true;
		} else {
			if (c != ICodeScanner.EOF)
				input.unread();
		}
		return false;
	}

	public static void skipNewLine(ICodeScanner input) {
		int c = input.read();
		if (c == '\n') {
			return;
		} else if (c == '\r') {
			c = input.read();
			if (c == '\n')
				return;
			input.unread();
		}
		input.unread();

	}

}
