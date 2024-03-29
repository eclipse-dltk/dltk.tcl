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

public class TclSpacedCommentRule implements IPredicateRule {

	IToken token;

	public TclSpacedCommentRule(IToken t) {
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

	@Override
	public IToken evaluate(ICharacterScanner scanner) {
		scanner.unread();
		int c = scanner.read();
		if (c != '\n') // not line start
			return Token.UNDEFINED;
		// skip spaces
		int count = 0;
		while (true) {
			count++;
			c = scanner.read();
			if (c == ' ' || c == '\t')
				continue;
			if (c != '#') {
				for (int i = 0; i < count; i++)
					scanner.unread();
				return Token.UNDEFINED;
			} else
				break;
		}
		while (true) {
			c = scanner.read();
			if (c == '\n')
				break;
		}
		return getSuccessToken();
	}
}
