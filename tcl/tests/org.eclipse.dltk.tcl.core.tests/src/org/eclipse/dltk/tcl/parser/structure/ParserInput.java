/*******************************************************************************
 * Copyright (c) 2009, 2017 xored software, Inc. and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.tcl.parser.structure;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.eclipse.dltk.compiler.env.IModuleSource;
import org.eclipse.dltk.compiler.util.Util;
import org.eclipse.dltk.core.IModelElement;

class ParserInput implements IModuleSource {

	private final URL resource;

	public ParserInput(URL resource) {
		this.resource = resource;
	}

	@Override
	public char[] getContentsAsCharArray() {
		try {
			final InputStream stream = resource.openConnection()
					.getInputStream();
			try {
				return Util.getInputStreamAsCharArray(stream, -1, null);
			} finally {
				try {
					stream.close();
				} catch (IOException e) {
					// ignore
				}
			}
		} catch (IOException e1) {
			StructureParserTests.fail(e1.toString());
			return null;
		}
	}

	@Override
	public IModelElement getModelElement() {
		return null;
	}

	@Override
	public String getSourceContents() {
		return new String(getContentsAsCharArray());
	}

	@Override
	public String getFileName() {
		return resource.getPath();
	}

}
