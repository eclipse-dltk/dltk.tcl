/*******************************************************************************
 * Copyright (c) 2009 xored software, Inc.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.tcl.activestatedebugger.preferences;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

class SourceElement {
	final IPath path;

	public SourceElement(IPath path) {
		this.path = path;
	}

	public SourceElement(String path) {
		this(new Path(path));
	}

	@Override
	public int hashCode() {
		return path.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SourceElement) {
			final SourceElement other = (SourceElement) obj;
			return path.equals(other.path);
		} else {
			return false;
		}
	}

}
