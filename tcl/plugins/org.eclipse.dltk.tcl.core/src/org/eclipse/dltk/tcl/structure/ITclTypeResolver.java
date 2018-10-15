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
package org.eclipse.dltk.tcl.structure;

import org.eclipse.dltk.compiler.IElementRequestor.ElementInfo;
import org.eclipse.dltk.compiler.IElementRequestor.TypeInfo;

/**
 * @since 2.0
 */
public interface ITclTypeResolver {

	/**
	 * Resolves member (proc/var) type.
	 * 
	 * @param info
	 * @param end
	 * @param name
	 * @return
	 */
	ITclTypeHandler resolveMemberType(ElementInfo info, int end, String name);

	/**
	 * Resolves type.
	 * 
	 * @param info
	 * @param end
	 * @param name
	 * @return
	 */
	ITclTypeHandler resolveType(TypeInfo info, int end, String name);

}
