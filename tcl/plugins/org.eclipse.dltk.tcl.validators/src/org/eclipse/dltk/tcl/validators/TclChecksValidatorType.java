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
 *     xored software, Inc. - initial API and Implementation (Andrei Sobolev)
 *******************************************************************************/

package org.eclipse.dltk.tcl.validators;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.builder.AbstractBuildParticipantType;
import org.eclipse.dltk.core.builder.IBuildParticipant;
import org.eclipse.dltk.tcl.internal.validators.TclCheckBuildParticipant;

public class TclChecksValidatorType extends AbstractBuildParticipantType {

	@Override
	public IBuildParticipant createBuildParticipant(IScriptProject project)
			throws CoreException {
		return new TclCheckBuildParticipant(project);
	}

}
