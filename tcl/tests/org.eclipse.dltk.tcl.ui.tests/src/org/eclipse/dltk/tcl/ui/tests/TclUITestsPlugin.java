/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.tcl.ui.tests;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * The main plugin class to be used in the desktop.
 */
public class TclUITestsPlugin extends AbstractUIPlugin {

	public static final String PLUGIN_NAME = "org.eclipse.dltk.tcl.ui.tests";
	// The shared instance.
	private static TclUITestsPlugin plugin;

	/**
	 * The constructor.
	 */
	public TclUITestsPlugin() {
		plugin = this;
	}

	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
	}

	/**
	 * Returns the shared instance.
	 */
	public static TclUITestsPlugin getDefault() {
		return plugin;
	}

	public IPath getPluginFilePath(String path) throws IOException {

		Bundle bundle = this.getBundle();
		URL url = bundle.getEntry(path);
		if (url == null)
			return null;
		url = FileLocator.resolve(url);
		return new Path(url.getPath()).makeAbsolute();
	}

	public String getPluginFileContent(String path) throws IOException {

		IPath filePath = this.getPluginFilePath(path);
		if (filePath == null)
			return null;

		InputStream contentStream = new FileInputStream(filePath.toOSString());
		BufferedReader bReader = new BufferedReader(new InputStreamReader(
				contentStream));
		String content = "";
		String line;
		while ((line = bReader.readLine()) != null) {
			content += line + "\n";
		}
		return content;
	}

}
