/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.search.indexing.SourceIndexerRequestor;
import org.eclipse.dltk.tcl.core.TclParseUtil;

public class TclSourceIndexerRequestor extends SourceIndexerRequestor {
	protected String[] realEnclosingTypeNames = new String[5];
	protected int realdepth = 0;
	Pattern pattern = Pattern.compile("::");

	@Override
	public void acceptMethodReference(String methodName, int argCount,
			int sourcePosition, int sourceEndPosition) {
		String[] ns = pattern.split(methodName, 0);
		if (ns.length > 0) {
			this.indexer.addMethodReference(ns[ns.length - 1], argCount);
		}
		for (int i = 0; i < ns.length - 1; ++i) {
			if (ns[i].length() > 0) {
				// System.out.println("TCLReferenceIndexing: Added namespace
				// reference:" + ns[i]);
				this.indexer.addTypeReference(ns[i]);
			}
		}
	}

	public boolean enterTypeAppend(String fullName, String delimiter) {
		if (fullName.startsWith("::")) {
			String name = fullName.substring(2);
			String[] split = TclParseUtil.tclSplit(name);

			for (int i = 0; i < split.length; i++) {
				this.indexer.addTypeDeclaration(
						Modifiers.AccNameSpace,
						namespace(),
						split[i],
						eclosingTypeNamesFrom(Collections.<String> emptyList(),
								split, i), null);
			}
			this.pushTypeName(name.toCharArray());
		} else {

			List<String> cEnclodingNames = enclosingTypeNamesAsList();
			String[] split = TclParseUtil.tclSplit(fullName);
			for (int i = 0; i < split.length; i++) {
				this.indexer.addTypeDeclaration(Modifiers.AccNameSpace,
						namespace(), split[i],
						eclosingTypeNamesFrom(cEnclodingNames, split, i), null);
			}
			this.pushTypeName(fullName.toCharArray());
		}
		return true;
	}

	private String[] eclosingTypeNamesFrom(List<String> enclosingNames,
			String[] split, int i) {
		String[] result = new String[enclosingNames.size() + i];
		int index = 0;
		for (; index < enclosingNames.size(); ++index) {
			result[index] = enclosingNames.get(index);
		}
		for (int j = 0; j < i; j++) {
			result[index++] = split[j];
		}
		if (result.length > 0) {
			return result;
		}
		return null;
	}

	private List<String> enclosingTypeNamesAsList() {
		List<String> cEnclosingNames = new ArrayList<String>();
		String[] enclosingTypeNames2 = enclosingTypeNames();
		if (enclosingTypeNames2 == null) {
			return cEnclosingNames;
		}
		for (int i = 0; i < enclosingTypeNames2.length; i++) {
			cEnclosingNames.add(enclosingTypeNames2[i]);
		}
		return cEnclosingNames;
	}

	public boolean enterTypeAppend(TypeInfo info, String fullName,
			String delimiter) {
		return false;
	}

	public void enterMethodRemoveSame(MethodInfo info) {
		enterMethod(info);
	}

	public void popTypeName() {
		if (depth > 0) {
			String name = realEnclosingTypeNames[realdepth - 1];
			realEnclosingTypeNames[--realdepth] = null;
			String[] split = TclParseUtil.tclSplit(name);
			for (int i = 0; i < split.length; ++i) {
				super.popTypeName();
			}
		}
	}

	public void pushTypeName(char[] typeName) {
		String type = new String(typeName);
		String[] split = TclParseUtil.tclSplit(type);
		for (int i = 0; i < split.length; i++) {
			super.pushTypeName(split[i]);
		}
		if (realdepth == realEnclosingTypeNames.length)
			System.arraycopy(realEnclosingTypeNames, 0,
					realEnclosingTypeNames = new String[depth * 2], 0,
					realdepth);
		realEnclosingTypeNames[realdepth++] = type;
	}

	public void enterType(TypeInfo typeInfo) {
		if ((typeInfo.modifiers & Modifiers.AccTest) == 0
				&& (typeInfo.modifiers & Modifiers.AccTestCase) == 0) {
			super.enterType(typeInfo);
		} else {
			this.pushTypeName(typeInfo.name.toCharArray());
		}
	}
}
