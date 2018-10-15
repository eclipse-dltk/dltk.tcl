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
package org.eclipse.dltk.tcl.parser.definitions;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.tcl.internal.parser.TclParserCore;

/**
 * Load all definition extension points.
 */
public class DefinitionExtensionManager {
	private static final String EXTENSION_ID = TclParserCore.PLUGIN_ID
			+ ".definitions";
	private static DefinitionExtensionManager sInstance;
	private Map<URL, String> extentions = new HashMap<>();
	private boolean initialized = false;

	private DefinitionExtensionManager() {
	}

	private void initialize() {
		if (initialized) {
			return;
		}
		initialized = true;
		IConfigurationElement[] configurationElements = Platform
				.getExtensionRegistry()
				.getConfigurationElementsFor(EXTENSION_ID);
		for (IConfigurationElement config : configurationElements) {
			String name = config.getAttribute("name");
			String url = config.getAttribute("url");
			URL urlValue;
			try {
				urlValue = new URL(url);
				extentions.put(urlValue, name);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
	}

	public URL[] getLocations() {
		initialize();
		return this.extentions.keySet()
				.toArray(new URL[this.extentions.size()]);
	}

	public Map<URL, String> getExtentions() {
		return extentions;
	}

	public static synchronized DefinitionExtensionManager getInstance() {
		if (sInstance == null) {
			sInstance = new DefinitionExtensionManager();
		}
		return sInstance;
	}
}
