/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.tclchecker;

import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.tcl.internal.tclchecker.TclCheckerConfigUtils.ValidatorInstanceResponse;
import org.eclipse.dltk.validators.core.AbstractValidator;
import org.eclipse.dltk.validators.core.ISourceModuleValidator;
import org.eclipse.dltk.validators.core.IValidatorType;

public class TclCheckerImpl extends AbstractValidator {

	protected TclCheckerImpl(String id, IValidatorType type) {
		super(id, null, type);
	}

	@Override
	public boolean isAutomatic(IScriptProject project) {
		final ValidatorInstanceResponse response = TclCheckerConfigUtils.getConfiguration(project,
				TclCheckerConfigUtils.AUTO);
		return !response.isEmpty();
	}

	@Override
	public boolean isValidatorValid(IScriptProject project) {
		final ValidatorInstanceResponse response = TclCheckerConfigUtils.getConfiguration(project,
				TclCheckerConfigUtils.AUTO);
		return !response.isEmpty() && TclCheckerHelper
				.canExecuteTclChecker(response.instances.get(0).environmentInstance, response.environment);
	}

	@Override
	public Object getValidator(IScriptProject project, Class validatorType) {
		if (ISourceModuleValidator.class.equals(validatorType)) {
			final ValidatorInstanceResponse response = TclCheckerConfigUtils.getConfiguration(project,
					TclCheckerConfigUtils.AUTO);
			if (!response.isEmpty()) {
				return new TclChecker(response.instances.get(0).environmentInstance, response.instances.get(0).config,
						response.environment);
			}
		}
		return null;
	}
}
