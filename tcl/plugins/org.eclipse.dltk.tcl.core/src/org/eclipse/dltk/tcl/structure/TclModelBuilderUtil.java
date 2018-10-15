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

import org.eclipse.dltk.tcl.ast.Substitution;
import org.eclipse.dltk.tcl.ast.TclArgument;
import org.eclipse.dltk.tcl.core.TclParseUtil;

/**
 * @since 2.0
 */
public class TclModelBuilderUtil {

	/**
	 * @param argument
	 * @return
	 */
	protected static boolean isLevel(TclArgument argument) {
		final String value = TclProcessorUtil.asString(argument);
		if (value.length() == 0) {
			return false;
		}
		if (value.charAt(0) == '#') {
			return isNumber(value, 1);
		} else {
			return isNumber(value, 0);
		}
	}

	/**
	 * @param value
	 * @param beginIndex
	 * @return
	 */
	protected static boolean isNumber(String value, int beginIndex) {
		if (beginIndex < value.length()) {
			for (int i = beginIndex, len = value.length(); i < len; ++i) {
				if (!Character.isDigit(value.charAt(i))) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	protected static boolean isSymbol(TclArgument argument) {
		return !(argument instanceof Substitution);
	}

	protected static String asSymbol(final TclArgument nameArg) {
		return TclParseUtil.escapeName(TclProcessorUtil.asString(nameArg));
		// TODO Check TclParseUtil.makeVariable()
	}

}
