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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.dltk.tcl.core.TclNature;
import org.eclipse.dltk.tcl.internal.tclchecker.TclCheckerConfigUtils.ValidatorInstanceRef;
import org.eclipse.dltk.tcl.internal.tclchecker.TclCheckerConfigUtils.ValidatorInstanceResponse;
import org.eclipse.dltk.tcl.internal.tclchecker.ui.preferences.ValidatorConfigComparator;
import org.eclipse.dltk.tcl.tclchecker.model.configs.CheckerConfig;
import org.eclipse.dltk.validators.core.AbstractValidatorType;
import org.eclipse.dltk.validators.core.ISourceModuleValidator;
import org.eclipse.dltk.validators.core.IValidator;

public class TclCheckerType extends AbstractValidatorType {

	public static final String TYPE_ID = "org.eclipse.dltk.tclchecker"; //$NON-NLS-1$

	public static final String CHECKER_ID = "Tcl Checker"; //$NON-NLS-1$

	private final TclCheckerImpl checker;

	@SuppressWarnings("unchecked")
	public TclCheckerType() {
		this.checker = new TclCheckerImpl(CHECKER_ID, this);
		this.checker.setName(CHECKER_ID);
		this.validators.put(CHECKER_ID, checker);
	}

	@Override
	public IValidator createValidator(String id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getID() {
		return TYPE_ID;
	}

	@Override
	public String getName() {
		return CHECKER_ID;
	}

	@Override
	public String getNature() {
		return TclNature.NATURE_ID;
	}

	@Override
	public boolean isBuiltin() {
		return true;
	}

	@Override
	public boolean isConfigurable() {
		return false;
	}

	@Override
	public boolean supports(Class validatorType) {
		return ISourceModuleValidator.class.equals(validatorType);
	}

	@Override
	public IValidator[] getAllValidators(IProject project) {
		final ValidatorInstanceResponse response = TclCheckerConfigUtils.getConfiguration(project,
				TclCheckerConfigUtils.ALL);
		if (response == null) {
			return new IValidator[0];
		}
		final List<IValidator> result = new ArrayList<>();
		for (ValidatorInstanceRef pair : response.instances) {
			final List<CheckerConfig> configs = new ArrayList<>();
			configs.addAll(response.getCommonConfigurations());
			configs.addAll(pair.environmentInstance.getInstance().getConfigs());
			Collections.sort(configs, new ValidatorConfigComparator());
			for (CheckerConfig config : configs) {
				result.add(new TclCheckerOptional(response.environment, pair.environmentInstance, config, this));
			}
		}
		return result.toArray(new IValidator[result.size()]);
	}
}
