/*******************************************************************************
 * Copyright (c) 2008, 2017 xored software, Inc. and others.
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
package org.eclipse.dltk.tcl.internal.tclchecker.qfix;

import org.eclipse.core.resources.IMarker;
import org.eclipse.dltk.core.CorrectionEngine;
import org.eclipse.dltk.tcl.internal.tclchecker.TclCheckerMarker;
import org.eclipse.dltk.ui.text.ScriptMarkerResoltionUtils;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator;

public class TclCheckerMarkerResolutionGenerator implements IMarkerResolutionGenerator {

	@Override
	public IMarkerResolution[] getResolutions(IMarker marker) {
		final String[] corrections = CorrectionEngine
				.decodeArguments(marker.getAttribute(TclCheckerMarker.SUGGESTED_CORRECTIONS, null));
		if (corrections != null) {
			final IMarkerResolution[] result = new IMarkerResolution[corrections.length];
			for (int i = 0; i < corrections.length; ++i) {
				result[i] = new TclCheckerMarkerResolution(corrections[i]);
			}
			return result;
		}
		return ScriptMarkerResoltionUtils.NO_RESOLUTIONS;
	}
}
