/*******************************************************************************
 * Copyright (c) 2008 xored software, Inc.  
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
package org.eclipse.dltk.tcl.activestatedebugger;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.dltk.compiler.util.Util;
import org.eclipse.dltk.core.IPreferencesLookupDelegate;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.environment.EnvironmentManager;
import org.eclipse.dltk.core.environment.IEnvironment;
import org.eclipse.dltk.dbgp.IDbgpFeature;
import org.eclipse.dltk.dbgp.IDbgpSpawnpoint;
import org.eclipse.dltk.dbgp.commands.IDbgpSpawnpointCommands;
import org.eclipse.dltk.dbgp.exceptions.DbgpException;
import org.eclipse.dltk.debug.core.model.IScriptBreakpointPathMapper;
import org.eclipse.dltk.debug.core.model.IScriptDebugTarget;
import org.eclipse.dltk.debug.core.model.IScriptDebugThreadConfigurator;
import org.eclipse.dltk.debug.core.model.IScriptThread;
import org.eclipse.dltk.internal.debug.core.model.ScriptLineBreakpoint;
import org.eclipse.dltk.internal.debug.core.model.ScriptThread;
import org.eclipse.dltk.internal.debug.core.model.operations.DbgpDebugger;
import org.eclipse.dltk.launching.IInterpreterInstall;
import org.eclipse.dltk.launching.ScriptRuntime;
import org.eclipse.dltk.tcl.activestatedebugger.InstrumentationSetup.PatternEntry;
import org.eclipse.dltk.tcl.activestatedebugger.preferences.InstrumentationConfig;
import org.eclipse.dltk.tcl.activestatedebugger.preferences.PatternListIO;

public class TclActiveStateDebugThreadConfigurator implements
		IScriptDebugThreadConfigurator {
	private boolean initialized = false;

	private final IScriptProject project;
	private final IPreferencesLookupDelegate delegate;

	/**
	 * @param project
	 * @param delegate
	 */
	public TclActiveStateDebugThreadConfigurator(IScriptProject project,
			IPreferencesLookupDelegate delegate) {
		this.project = project;
		this.delegate = delegate;
	}

	public void configureThread(DbgpDebugger engine, ScriptThread scriptThread) {
		if (initialized) {
			return;
		}
		initialized = true;
		try {
			IDbgpFeature tclFeature = engine.getFeature("tcl_instrument_set"); //$NON-NLS-1$
			if (tclFeature.isSupported()) {
				ActiveStateInstrumentCommands commands = new ActiveStateInstrumentCommands(
						engine.getSession().getCommunicator());
				final ILaunchConfiguration launchConf = scriptThread
						.getLaunch().getLaunchConfiguration();
				if (launchConf != null) {
					final IInterpreterInstall install = ScriptRuntime
							.computeInterpreterInstall(launchConf);
					if (install != null) {
						initializeDebugger(commands, install);
					}
				}
			}
		} catch (DbgpException e) {
			TclActiveStateDebuggerPlugin.warn(e);
		} catch (CoreException e) {
			TclActiveStateDebuggerPlugin.warn(e);
		}
	}

	// private static abstract class PatternRef {
	// final boolean include;
	//
	// public PatternRef(boolean include) {
	// this.include = include;
	// }
	//
	// }
	//
	// private static class LibraryContainerRef extends PatternRef {
	//
	// public LibraryContainerRef(boolean include) {
	// super(include);
	// }
	//
	// }
	//
	// private static class ModelElementRef extends PatternRef {
	// final IModelElement element;
	//
	// public ModelElementRef(IModelElement element, boolean include) {
	// super(include);
	// this.element = element;
	// }
	//
	// }

	// private static List<PatternRef> parsePatterns(InstrumentationConfig
	// config) {
	// List<PatternRef> result = new ArrayList<PatternRef>();
	// if (config != null) {
	// for (Pattern pattern : config.getModelElements()) {
	// if (pattern instanceof ModelElementPattern) {
	// final IModelElement element = DLTKCore
	// .create(((ModelElementPattern) pattern)
	// .getHandleIdentifier());
	// if (element != null) {
	// result.add(new ModelElementRef(element, pattern
	// .isInclude()));
	// }
	// } else if (pattern instanceof LibraryPattern) {
	// result.add(new LibraryContainerRef(pattern.isInclude()));
	// }
	// }
	// }
	// return result;
	// }

	private void initializeDebugger(ActiveStateInstrumentCommands commands,
			IInterpreterInstall install) throws DbgpException {
		final Set<InstrumentationFeature> selectedFeatures = InstrumentationFeature
				.decode(getString(TclActiveStateDebuggerConstants.INSTRUMENTATION_FEATURES));
		for (InstrumentationFeature feature : InstrumentationFeature.values()) {
			commands.instrumentSet(feature, selectedFeatures.contains(feature));
		}
		final ErrorAction errorAction = ErrorAction
				.decode(getString(TclActiveStateDebuggerConstants.INSTRUMENTATION_ERROR_ACTION));
		if (errorAction != null) {
			commands.setErrorAction(errorAction);
		}
		final InstrumentationConfig config = PatternListIO
				.decode(getString(TclActiveStateDebuggerConstants.INSTRUMENTATION_PATTERNS));
		final InstrumentationConfigProcessor configurator = new InstrumentationConfigProcessor(
				project, install);
		configurator.configure(config);
		for (PatternEntry entry : configurator.getPatterns()) {
			if (entry.isInclude()) {
				commands.instrumentInclude(entry.getPatternText());
			} else {
				commands.instrumentExclude(entry.getPatternText());
			}
		}
	}

	private String getString(final String key) {
		return delegate.getString(TclActiveStateDebuggerPlugin.PLUGIN_ID, key);
	}

	private List<IMarker> loadSpawnpoints() throws CoreException {
		final IWorkspaceRoot root = InstrumentationUtils.getWorkspaceRoot();
		final IMarker[] markers = root.findMarkers(
				TclActiveStateDebuggerConstants.SPAWNPOINT_MARKER_TYPE, true,
				IResource.DEPTH_INFINITE);
		if (markers.length == 0) {
			return Collections.emptyList();
		}
		final IEnvironment environment = EnvironmentManager
				.getEnvironment(project);
		if (environment == null) {
			return Arrays.asList(markers);
		}
		final Map<IProject, Boolean> projectStatus = new HashMap<IProject, Boolean>();
		final List<IMarker> result = new ArrayList<IMarker>();
		for (IMarker marker : markers) {
			final IProject project = marker.getResource().getProject();
			Boolean status = projectStatus.get(project);
			if (status == null) {
				if (environment.equals(EnvironmentManager
						.getEnvironment(project))) {
					status = Boolean.TRUE;
				} else {
					status = Boolean.FALSE;
				}
				projectStatus.put(project, status);
			}
			if (status.booleanValue()) {
				result.add(marker);
			}
		}
		return result;
	}

	public void initializeBreakpoints(IScriptThread thread,
			IProgressMonitor monitor) {
		try {
			final List<IMarker> spawnpoints = loadSpawnpoints();
			monitor.beginTask(Util.EMPTY_STRING, spawnpoints.size());
			final IDbgpSpawnpointCommands commands = (IDbgpSpawnpointCommands) thread
					.getDbgpSession().get(IDbgpSpawnpointCommands.class);
			final IScriptBreakpointPathMapper pathMapper = ((IScriptDebugTarget) thread
					.getDebugTarget()).getPathMapper();
			for (final IMarker spawnpoint : spawnpoints) {
				final URI uri = ScriptLineBreakpoint.makeUri(new Path(
						spawnpoint.getResource().getLocationURI().getPath()));
				try {
					final int lineNumber = spawnpoint.getAttribute(
							IMarker.LINE_NUMBER, 0) + 1;
					final IDbgpSpawnpoint p = commands.setSpawnpoint(pathMapper
							.map(uri), lineNumber, true);
					// TODO save spawnpoint id - need SpawnpointManager?
				} catch (DbgpException e) {
					// TODO save spawnpoint error - need SpawnpointManager?
					TclActiveStateDebuggerPlugin.warn(e);
				}
				monitor.worked(1);
			}
		} catch (CoreException e) {
			TclActiveStateDebuggerPlugin.warn(e);
		}
		monitor.done();
	}
}
