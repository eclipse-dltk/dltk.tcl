/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *

 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.console.ui;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IStreamsProxy;
import org.eclipse.dltk.console.IScriptInterpreter;
import org.eclipse.dltk.console.ScriptConsolePrompt;
import org.eclipse.dltk.console.ui.IScriptConsole;
import org.eclipse.dltk.console.ui.IScriptConsoleFactory;
import org.eclipse.dltk.console.ui.ScriptConsoleFactoryBase;
import org.eclipse.dltk.core.internal.environment.LocalEnvironment;
import org.eclipse.dltk.debug.ui.DLTKDebugUIPlugin;
import org.eclipse.dltk.launching.IInterpreterInstall;
import org.eclipse.dltk.launching.LaunchingMessages;
import org.eclipse.dltk.launching.ScriptRuntime;
import org.eclipse.dltk.tcl.console.TclConsoleConstants;
import org.eclipse.dltk.tcl.console.TclConsoleUtil;
import org.eclipse.dltk.tcl.console.TclInterpreter;
import org.eclipse.dltk.tcl.core.TclNature;
import org.eclipse.dltk.tcl.internal.debug.ui.TclDebugUIPlugin;
import org.eclipse.dltk.ui.DLTKUILanguageManager;
import org.eclipse.dltk.ui.IDLTKUILanguageToolkit;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PreferencesUtil;

public class TclConsoleFactory extends ScriptConsoleFactoryBase implements IScriptConsoleFactory {
	protected IPreferenceStore getPreferenceStore() {
		return TclDebugUIPlugin.getDefault().getPreferenceStore();
	}

	protected ScriptConsolePrompt makeInvitation() {
		IPreferenceStore store = getPreferenceStore();
		return new ScriptConsolePrompt(store.getString(TclConsoleConstants.PREF_NEW_PROMPT),
				store.getString(TclConsoleConstants.PREF_CONTINUE_PROMPT));
	}

	protected TclConsole makeConsole(TclInterpreter interpreter, String id) {
		TclConsole console = new TclConsole(interpreter, id);
		console.setPrompt(makeInvitation());
		return console;
	}

	protected void showInterpreterPreferencePage(String natureId) {
		String preferencePageId = null;
		IDLTKUILanguageToolkit languageToolkit = null;
		languageToolkit = DLTKUILanguageManager.getLanguageToolkit(natureId);
		if (languageToolkit == null) {
			return;
		}
		preferencePageId = languageToolkit.getInterpreterPreferencePage();

		if (preferencePageId != null) {
			PreferenceDialog dialog = PreferencesUtil.createPreferenceDialogOn(null, preferencePageId, null, null);
			dialog.open();
		}
	}

	private void showQuestion() {
		final boolean result[] = new boolean[] { false };
		DLTKDebugUIPlugin.getStandardDisplay().syncExec(() -> {
			String title = LaunchingMessages.NoDefaultInterpreterStatusHandler_title;
			String message = LaunchingMessages.NoDefaultInterpreterStatusHandler_message;
			result[0] = (MessageDialog.openQuestion(DLTKDebugUIPlugin.getActiveWorkbenchShell(), title, message));
			if (result[0]) {
				showInterpreterPreferencePage(TclNature.NATURE_ID);
			}
		});
	}

	private TclConsole createConsoleInstance(IScriptInterpreter interpreter, String id) {
		ILaunch launch = null;
		if (interpreter == null) {
			try {
				id = "default"; //$NON-NLS-1$
				interpreter = new TclInterpreter();

				if (ScriptRuntime.getDefaultInterpreterInstall(TclNature.NATURE_ID,
						LocalEnvironment.getInstance()) == null) {
					showQuestion();
					if (ScriptRuntime.getDefaultInterpreterInstall(TclNature.NATURE_ID,
							LocalEnvironment.getInstance()) == null) {
						return null;
					}
				}
				launch = TclConsoleUtil.runDefaultTclInterpreter((TclInterpreter) interpreter);
			} catch (Exception e) {
				return null;
			}
		}

		final TclConsole console = makeConsole((TclInterpreter) interpreter, id);
		if (launch != null) {
			final IProcess[] processes = launch.getProcesses();
			for (int i = 0; i < processes.length; ++i) {
				final IStreamsProxy proxy = processes[i].getStreamsProxy();
				if (proxy != null) {
					console.connect(proxy);
				}
			}
		}
		return console;
	}

	@Override
	protected IScriptConsole createConsoleInstance() {
		return createConsoleInstance(null, null);
	}

	public TclConsoleFactory() {
	}

	/**
	 * @since 2.0
	 */
	@Override
	public IScriptConsole openConsole(IScriptInterpreter interpreter, String id, ILaunch launch) {
		TclConsole tclConsole = createConsoleInstance(interpreter, id);
		tclConsole.setLaunch(launch);
		registerAndOpenConsole(tclConsole);
		return tclConsole;
	}

	/**
	 * @since 2.0
	 */
	public void openConsole(IInterpreterInstall install, String consoleName) {
		final TclInterpreter interpreter = new TclInterpreter();
		final ILaunch launch;
		try {
			launch = TclConsoleUtil.runTclInterpreter(install, interpreter);
		} catch (Exception e) {
			ErrorDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
					"Error Launching Tcl Console", e.toString(),
					new Status(IStatus.ERROR, TclDebugUIPlugin.PLUGIN_ID, e.getMessage(), e));
			return;
		}
		if (launch != null) {
			IScriptConsole console = openConsole(interpreter, consoleName, launch);
			final IProcess[] processes = launch.getProcesses();
			for (IProcess process : processes) {
				final IStreamsProxy proxy = process.getStreamsProxy();
				if (proxy != null) {
					console.connect(proxy);
				}
			}
		}
	}

}
