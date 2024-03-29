/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.ui.text;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.tcl.internal.ui.rules.TclCommentRule;
import org.eclipse.dltk.tcl.internal.ui.rules.TclEscapedCharRule;
import org.eclipse.dltk.tcl.ui.text.TclPartitions;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;

public class TclPartitionScanner extends RuleBasedPartitionScanner {

	/**
	 * Creates the partitioner and sets up the appropriate rules.
	 */
	public TclPartitionScanner() {
		super();

		IToken string = new Token(TclPartitions.TCL_STRING);
		IToken comment = new Token(TclPartitions.TCL_COMMENT);
		IToken stuff = new Token("dummy"); //$NON-NLS-1$

		List<IPredicateRule> rules = new ArrayList<>();

		// rules.add(new EndOfLineRule("#", comment ));
		rules.add(new TclCommentRule(comment));

		rules.add(new TclEscapedCharRule(stuff, '\\'));

		rules.add(new SingleLineRule("\"", "\"", string, '\\')); //$NON-NLS-1$ //$NON-NLS-2$
		// rules.add(new MultiLineRule("\"", "\"", string, '\\'));

		IPredicateRule[] result = new IPredicateRule[rules.size()];
		rules.toArray(result);
		setPredicateRules(result);
	}
}
