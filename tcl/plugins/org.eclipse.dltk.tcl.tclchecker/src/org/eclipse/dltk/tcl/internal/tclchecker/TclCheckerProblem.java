/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.tclchecker;

import java.util.HashMap;
import java.util.Map;


public class TclCheckerProblem {
	private final String file;
	private final int lineNumber;
	private final TclCheckerProblemDescription description;
	private CoordRange range;
	private CoordRange errorRange;
	private Map<String, Object> attributes = null;

	public TclCheckerProblem(String source, int lineNumber, String messageID,
			String message) {
		this.file = source;
		this.lineNumber = lineNumber;
		this.description = TclCheckerProblemDescription.getProblemDescription(
				messageID, message);
	}

	public String getFile() {
		return file;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public TclCheckerProblemDescription getDescription() {
		return description;
	}

	public String toString() {
		return file + ":" + lineNumber + " " + description; //$NON-NLS-1$ //$NON-NLS-2$
	}

	public CoordRange getRange() {
		return range;
	}

	public void setRange(CoordRange range) {
		this.range = range;
	}

	public CoordRange getErrorRange() {
		return errorRange;
	}

	public void setErrorRange(CoordRange range) {
		this.errorRange = range;
	}

	/**
	 * Returns map of attributes of <code>null</code> if there are no attributes
	 * yet
	 * 
	 * @return the attributes
	 */
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	/**
	 * @param name
	 * @param value
	 */
	public void addAttribute(String name, String value) {
		if (attributes == null) {
			attributes = new HashMap<String, Object>();
		}
		attributes.put(name, value);
	}

	/**
	 * @param name
	 * @param value
	 */
	public void addAttribute(String name, int value) {
		if (attributes == null) {
			attributes = new HashMap<String, Object>();
		}
		attributes.put(name, value);
	}
}
