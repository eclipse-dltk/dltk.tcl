/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.launching;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.environment.IDeployment;
import org.eclipse.dltk.core.environment.IEnvironment;
import org.eclipse.dltk.core.environment.IExecutionEnvironment;
import org.eclipse.dltk.core.environment.IFileHandle;
import org.eclipse.dltk.internal.launching.AbstractInterpreterInstallType;
import org.eclipse.dltk.internal.launching.DLTKLaunchingPlugin;
import org.eclipse.dltk.internal.launching.InterpreterMessages;
import org.eclipse.dltk.launching.EnvironmentVariable;
import org.eclipse.dltk.launching.IInterpreterInstall;
import org.eclipse.dltk.launching.InterpreterConfig;
import org.eclipse.dltk.launching.LibraryLocation;
import org.eclipse.dltk.launching.ScriptLaunchUtil;
import org.eclipse.dltk.tcl.core.TclLibpathUtils;
import org.eclipse.dltk.tcl.core.TclNature;
import org.eclipse.dltk.tcl.core.TclPackagesManager;
import org.eclipse.dltk.tcl.core.TclPlugin;
import org.eclipse.dltk.tcl.core.packages.TclInterpreterInfo;
import org.eclipse.dltk.tcl.core.packages.TclPackagesFactory;
import org.eclipse.dltk.tcl.internal.core.packages.ProcessOutputCollector;
import org.eclipse.dltk.tcl.launching.TclLaunchingPlugin;
import org.osgi.framework.Bundle;

public class GenericTclInstallType extends AbstractInterpreterInstallType {

	private static final String INSTALL_TYPE_NAME = "Generic Tcl";
	/**
	 * @since 1.1
	 */
	public static final String TYPE_ID = "org.eclipse.dltk.internal.debug.ui.launcher.GenericTclInstallType";
	private static final String[] INTERPRETER_NAMES = { "tclsh", "tclsh84", "tclsh8.4", "tclsh85", "tclsh8.5",
			"tclsh8.6",

			"wish", "wish84", "wish85", "wish86", "wish8.4", "wish85", "wish8.5", "wish8.6",

			"vtk",

			"expect",

			"base-tcl-linux", "base-tk-linux",

			"base-tcl-thread", "base-tk-thread", "base-tcl8.5-thread", "base-tcl8.6-thread", "base-tk8.5-thread",
			"base-tk8.6-thread" };

	@Override
	public String getNatureId() {
		return TclNature.NATURE_ID;
	}

	@Override
	public String getName() {
		return INSTALL_TYPE_NAME;
	}

	@Override
	protected String getPluginId() {
		return TclLaunchingPlugin.PLUGIN_ID;
	}

	@Override
	protected String[] getPossibleInterpreterNames() {
		return INTERPRETER_NAMES;
	}

	@Override
	protected IInterpreterInstall doCreateInterpreterInstall(String id) {
		return new GenericTclInstall(this, id);
	}

	@Override
	protected void filterEnvironment(Map<String, String> environment) {
		// make sure that $auto_path is clean
		environment.remove("TCLLIBPATH");
		// block wish from showing window under linux
		environment.remove("DISPLAY");
	}

	@Override
	public IStatus validateInstallLocation(IFileHandle installLocation, EnvironmentVariable[] variables,
			LibraryLocation[] libraries, IProgressMonitor monitor) {
		/* Progress monitoring */monitor.beginTask("Validate Tcl interpreter", 100);
		try {
			if (!installLocation.exists() || !installLocation.isFile()) {
				return createStatus(IStatus.ERROR, InterpreterMessages.errNonExistentOrInvalidInstallLocation, null);
			}
			IEnvironment environment = installLocation.getEnvironment();
			IExecutionEnvironment executionEnvironment = environment.getAdapter(IExecutionEnvironment.class);
			/* Progress monitoring */monitor.subTask("Deploy validation script");
			IDeployment deployment = executionEnvironment.createDeployment();
			if (deployment == null) {
				// happens if RSE is not initialized yet or no connection
				// established
				return createStatus(IStatus.ERROR,
						"Failed to deploy validation script to host:" + environment.getName(), null);
			}
			List<String> output = null;
			Bundle bundle = TclPlugin.getDefault().getBundle();
			try {
				IFileHandle builderFile = deployment.getFile(deployment.add(bundle, "scripts/dltk.tcl"));
				/* Progress monitoring */monitor.worked(10);
				InterpreterConfig config = ScriptLaunchUtil.createInterpreterConfig(executionEnvironment, builderFile,
						builderFile.getParent());
				config.addScriptArg("get-pkgs");
				// Configure environment variables
				final Map<String, String> envVars = new HashMap<>();

				Map<String, String> envVars2 = executionEnvironment.getEnvironmentVariables(false);
				if (envVars2 != null) {
					envVars.putAll(envVars2);
				}

				config.addEnvVars(envVars);
				config.removeEnvVar("DISPLAY"); //$NON-NLS-1$
				TclLibpathUtils.addTclLibPath(config, libraries, environment);
				/* Progress monitoring */monitor.subTask("Running validation script");
				String[] cmdLine = config.renderCommandLine(executionEnvironment.getEnvironment(),
						installLocation.toOSString());

				String[] environmentAsStrings = config.getEnvironmentAsStringsIncluding(variables);
				IPath workingDirectoryPath = config.getWorkingDirectoryPath();
				if (DLTKLaunchingPlugin.TRACE_EXECUTION) {
					ScriptLaunchUtil.traceExecution("runScript with interpreter", cmdLine, //$NON-NLS-1$
							environmentAsStrings);
				}
				final Process process = executionEnvironment.exec(cmdLine, workingDirectoryPath, environmentAsStrings);
				/* Progress monitoring */monitor.worked(10);

				SubProgressMonitor sm = new SubProgressMonitor(monitor, 70);
				sm.beginTask("Running validation script", IProgressMonitor.UNKNOWN);
				sm.done();
				output = ProcessOutputCollector.execute(process, sm);

				int exitValue = 0;
				try {
					exitValue = process.exitValue();
				} catch (IllegalThreadStateException e) {
					exitValue = -1;
				}
				if (exitValue != 0) {
					return createStatus(IStatus.ERROR, "Interpreter return abnormal exit code:" + exitValue, null);
				}
			} catch (IOException e1) {
				if (DLTKCore.DEBUG) {
					e1.printStackTrace();
				}
				return null;
			} catch (CoreException e) {
				if (DLTKCore.DEBUG) {
					e.printStackTrace();
				}
				return null;
			} finally {
				deployment.dispose();
			}
			if (output == null) {
				return createStatus(IStatus.ERROR, InterpreterMessages.errNoInterpreterExecutablesFound, null);
			}
			boolean correct = output.contains(TclPackagesManager.END_OF_STREAM);

			if (correct) {
				monitor.subTask("Processing validation result");
				// Parse list of packages from output
				// Generate fake interpreter install
				TclInterpreterInfo info = TclPackagesFactory.eINSTANCE.createTclInterpreterInfo();
				TclPackagesManager.fillPackagesFromContent(output, info);
				monitor.worked(10);
				return new StatusWithPackages(info);
			} else {
				return createStatus(IStatus.ERROR, InterpreterMessages.errNoInterpreterExecutablesFound, null);
			}
		} finally {
			monitor.done();
		}
	}

	@Override
	protected ILookupRunnable createLookupRunnable(final IFileHandle installLocation,
			final List<LibraryLocation> locations, final EnvironmentVariable[] variables) {
		return monitor -> {
			// This retrieval could not receive paths in some cases.
			// String result = retrivePaths(installLocation, locations,
			// monitor, createPathFile(), variables);
			// This is safe retrieval
			// String[] autoPath = DLTKTclHelper.getDefaultPath(
			// installLocation, variables);
			// IEnvironment env = installLocation.getEnvironment();
			// if (autoPath != null) {
			// for (int i = 0; i < autoPath.length; i++) {
			// Path libraryPath = new Path(autoPath[i]);
			// IFileHandle file = env.getFile(libraryPath);
			// if (file.exists()) {
			// locations.add(new LibraryLocation(libraryPath));
			// }
			// }
			// }
		};
	}

	@Override
	protected String[] parsePaths(String res) {
		ArrayList<String> paths = new ArrayList<>();
		String subs = null;
		int index = 0;
		String result = res;
		if (result.startsWith(DLTK_PATH_PREFIX)) {
			result = result.substring(DLTK_PATH_PREFIX.length());
		}
		while (index < result.length()) {
			// skip whitespaces
			while (index < result.length() && Character.isWhitespace(result.charAt(index)))
				index++;
			if (index == result.length())
				break;

			if (result.charAt(index) == '{') {
				int start = index;
				while (index < result.length() && result.charAt(index) != '}')
					index++;
				if (index == result.length())
					break;
				subs = result.substring(start + 1, index);
			} else {
				int start = index;
				while (index < result.length() && result.charAt(index) != ' ')
					index++;
				subs = result.substring(start, index);
			}

			paths.add(subs);
			index++;
		}

		return paths.toArray(new String[paths.size()]);
	}

	@Override
	protected ILog getLog() {
		return TclLaunchingPlugin.getDefault().getLog();
	}

	@Override
	protected IPath createPathFile(IDeployment deployment) throws IOException {
		return null;
	}
}
