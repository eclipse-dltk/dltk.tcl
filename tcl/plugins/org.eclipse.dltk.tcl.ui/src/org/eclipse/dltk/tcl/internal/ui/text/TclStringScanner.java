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

import org.eclipse.dltk.tcl.internal.ui.rules.TclVariableRule;
import org.eclipse.dltk.ui.text.AbstractScriptScanner;
import org.eclipse.dltk.ui.text.IColorManager;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.WhitespaceRule;

public class TclStringScanner extends AbstractScriptScanner {

	private static final String[] fgTokenProperties = new String[] {
			TclColorConstants.TCL_STRING, TclColorConstants.TCL_VARIABLE };

	public TclStringScanner(IColorManager manager, IPreferenceStore store) {
		super(manager, store);

		initialize();
	}

	@Override
	protected String[] getTokenProperties() {
		return fgTokenProperties;
	}

	@Override
	protected List<IRule> createRules() {
		List<IRule> rules = new ArrayList<>();
		// IToken number = getToken(ITclColorConstants.TCL_NUMBER);
		IToken variable = getToken(TclColorConstants.TCL_VARIABLE);

		// Add generic whitespace rule.
		rules.add(new WhitespaceRule(new TclWhitespaceDetector()));
		rules.add(new TclVariableRule(variable));
		// rules.add( new FloatNumberRule( number ) );

		setDefaultReturnToken(getToken(TclColorConstants.TCL_STRING));

		return rules;
	}

}
