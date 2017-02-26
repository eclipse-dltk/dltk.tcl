/*******************************************************************************
 * Copyright (c) 2008, 2017 xored software, Inc. and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Andrei Sobolev)
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.parser.raw;

public class BracesSubstitution extends TclElement
		implements ISubstitution, IWordSubstitution {

	public static boolean iAm(ICodeScanner scanner) {
		int c = scanner.read();
		if (c == ICodeScanner.EOF)
			return false;
		scanner.unread();
		return (c == '{');
	}

	@Override
	public boolean readMe(ICodeScanner input, SimpleTclParser parser)
			throws TclParseException {
		if (!iAm(input))
			return false;
		setStart(input.getPosition());
		input.read();
		int c;
		int nest = 1;
		while (nest > 0) {
			c = input.read();
			if (c == ICodeScanner.EOF) {
				parser.handleError(new ErrorDescription(
						Messages.BracesSubstitution_Error, getStart(),
						input.getPosition(), ErrorDescription.ERROR));
				break;
			}
			if (c == '\\') {
				c = input.read();
				if (c == '{' || c == '}')
					continue;
				if (c == '\r') {
					int c1 = input.read();
					if (c1 == '\n') {
						do {
							c = input.read();
						} while (c != ICodeScanner.EOF
								&& TclTextUtils.isTrueWhitespace(c));
						input.unread();
						continue;
					} else
						input.unread();
				}
			}
			if (c == '{') {
				nest++;
				continue;
			}
			if (c == '}') {
				nest--;
				continue;
			}
		}
		if (!input.isEOF()) {
			setEnd(input.getPosition() - 1);
		} else
			setEnd(input.getPosition());
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName()).append("{"); //$NON-NLS-1$
		sb.append(getStart()).append("..").append(getEnd()); //$NON-NLS-1$
		sb.append("}"); //$NON-NLS-1$
		return sb.toString();
	}

}
