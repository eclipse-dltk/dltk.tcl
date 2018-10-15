/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
/*
 * Created on Feb 16, 2006
 */
package org.eclipse.dltk.tcl.ui.tests.indenting;

import org.eclipse.jface.text.DocumentCommand;

/**
 * The subclass of {@link DocumentCommand} with public constructor
 */
public class DocCmd extends DocumentCommand {
	public DocCmd() {
		this.doit = true;
	}

	public DocCmd(int offset, int length, String text) {
		this.offset = offset;
		this.length = length;
		this.text = text;
		this.doit = true;
		this.caretOffset = -1;
		this.shiftsCaret = true;
	}

}
