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
package org.eclipse.dltk.xotcl.internal.core.parser.structure;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.dltk.tcl.core.TclParseUtil;
import org.eclipse.dltk.tcl.structure.ITclModelBuildContext;
import org.eclipse.dltk.tcl.structure.ITclTypeHandler;

public class XOTclNames {

	private static final String ATTR = XOTclNames.class.getName();

	public static XOTclNames get(ITclModelBuildContext context) {
		return (XOTclNames) context.getAttribute(ATTR);
	}

	public static XOTclNames create(ITclModelBuildContext context) {
		XOTclNames names = get(context);
		if (names == null) {
			names = new XOTclNames();
			context.setAttribute(ATTR, names);
		}
		return names;
	}

	static class XOTclTypeInfo {
		final String[] segments;

		public XOTclTypeInfo(String[] segments) {
			this.segments = segments;
		}

		/**
		 * @param parts
		 * @return
		 */
		public boolean endsWith(String[] parts) {
			final int pLen = parts.length;
			final int sLen = segments.length;
			if (pLen <= sLen) {
				final int offset = sLen - pLen;
				for (int i = 0; i < pLen; ++i) {
					if (!parts[i].equals(segments[offset + i])) {
						return false;
					}
				}
				return true;
			}
			return false;
		}

		/**
		 * @return
		 */
		public String getSimpleName() {
			return segments[segments.length - 1];
		}

	}

	private final Map<String, Map<String, XOTclTypeInfo>> names = new HashMap<String, Map<String, XOTclTypeInfo>>();

	/**
	 * @param typeInfo
	 * @param resolvedType
	 */
	public void addType(ITclTypeHandler resolvedType) {
		String namespace = resolvedType.getNamespace();
		if (namespace.startsWith("::")) { //$NON-NLS-1$
			namespace = namespace.substring(2);
		}
		if (namespace.length() == 0) {
			return;
		}
		final String[] segments = TclParseUtil.tclSplit(namespace);
		if (segments.length == 0) {
			return;
		}
		final String lastName = segments[segments.length - 1];
		Map<String, XOTclTypeInfo> types = names.get(lastName);
		if (types == null) {
			types = new HashMap<String, XOTclTypeInfo>();
			names.put(lastName, types);
		}
		if (!types.containsKey(namespace)) {
			types.put(namespace, new XOTclTypeInfo(segments));
		}
	}

	/**
	 * @param name
	 * @param nameArgument
	 * @return
	 */
	public XOTclType resolve(String name) {
		String[] segments = TclParseUtil.tclSplit(name);
		if (segments.length == 0) {
			return null;
		}
		final Map<String, XOTclTypeInfo> types = names
				.get(segments[segments.length - 1]);
		if (types == null) {
			return null;
		}
		for (Map.Entry<String, XOTclTypeInfo> entry : types.entrySet()) {
			if (entry.getValue().endsWith(segments)) {
				return new XOTclType(entry.getKey(), entry.getValue());
			}
		}
		return null;
	}

}
