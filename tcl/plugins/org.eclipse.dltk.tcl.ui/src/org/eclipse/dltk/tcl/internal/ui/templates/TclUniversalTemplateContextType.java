/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *

 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.ui.templates;

import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.ui.templates.ScriptTemplateContext;
import org.eclipse.dltk.ui.templates.ScriptTemplateContextType;
import org.eclipse.jface.text.IDocument;

public class TclUniversalTemplateContextType extends ScriptTemplateContextType {
	public static final String CONTEXT_TYPE_ID = "tclUniversalTemplateContextType";

	public TclUniversalTemplateContextType() {
		// empty constructor
	}

	public TclUniversalTemplateContextType(String id) {
		super(id);
	}

	public TclUniversalTemplateContextType(String id, String name) {
		super(id, name);
	}

	@Override
	public ScriptTemplateContext createContext(IDocument document, int offset,
			int length, ISourceModule sourceModule) {
		return new TclTemplateContext(this, document, offset, length,
				sourceModule);
	}
}
