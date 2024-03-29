/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.ui.rules;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

public class TclCommentRule implements IPredicateRule {

	IToken token;

	public TclCommentRule(IToken t) {
		token = t;
	}

	@Override
	public IToken evaluate(ICharacterScanner scanner, boolean resume) {
		return evaluate(scanner);
	}

	@Override
	public IToken getSuccessToken() {
		return token;
	}

	private void runForward(ICharacterScanner scanner, int count) {
		for (int i = 0; i < count; i++)
			scanner.read();
	}

	private void runToLineEnd(ICharacterScanner scanner) {
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
			case ICharacterScanner.EOF:
				if (!esc)
					return;
			default:
				esc = false;
			}
		}
	}

	@Override
	public IToken evaluate(ICharacterScanner scanner) {
		int ch = scanner.read();
		scanner.unread();
		if (ch != '#') { // even not a candidate to comment
			return Token.UNDEFINED;
		}
		// scan back for line start or semicolon
		int countBack = 0;
		loop: while (true) {
			if (scanner.getColumn() <= 0) {
				runForward(scanner, countBack - 1);
				runToLineEnd(scanner);
				return getSuccessToken();
			}
			scanner.unread();
			countBack++;
			int c = scanner.read();
			switch (c) {
			case ' ':
			case '\t':
				scanner.unread();
				break;
			case '\n':
			case ';':
			case ICharacterScanner.EOF:
				runForward(scanner, countBack - 1);
				runToLineEnd(scanner);
				return getSuccessToken();
			default:
				runForward(scanner, countBack - 1);
				break loop;
			}
		}
		return Token.UNDEFINED;
	}
}
