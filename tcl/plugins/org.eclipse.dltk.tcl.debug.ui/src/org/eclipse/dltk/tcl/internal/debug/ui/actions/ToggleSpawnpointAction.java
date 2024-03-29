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
package org.eclipse.dltk.tcl.internal.debug.ui.actions;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdapterManager;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.text.source.IVerticalRulerInfo;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.IUpdate;

/**
 * Action to toggle a breakpoint in a vertical ruler of a workbench part
 * containing a document. The part must provide an
 * <code>IToggleSpawnpointsTarget</code> adapter.
 * <p>
 * Clients may instantiate this class.
 * </p>
 *
 * @since 3.1
 * @see org.eclipse.dltk.tcl.internal.debug.ui.actions.RulerToggleSpawnpointActionDelegate
 * @noextend This class is not intended to be subclassed by clients.
 */
public class ToggleSpawnpointAction extends Action implements IUpdate {

	private IWorkbenchPart fPart;
	private IDocument fDocument;
	private IVerticalRulerInfo fRulerInfo;

	/**
	 * Constructs a new action to toggle a breakpoint in the given part containing
	 * the given document and ruler.
	 *
	 * @param part
	 *            the part in which to toggle the breakpoint - provides an
	 *            <code>IToggleBreakpointsTarget</code> adapter
	 * @param document
	 *            the document breakpoints are being set in or <code>null</code>
	 *            when the document should be derived from the given part
	 * @param rulerInfo
	 *            specifies location the user has double-clicked
	 */
	public ToggleSpawnpointAction(IWorkbenchPart part, IDocument document, IVerticalRulerInfo rulerInfo) {
		super(ActionMessages.ToggleSpawnpointAction_0);
		fPart = part;
		fDocument = document;
		fRulerInfo = rulerInfo;
	}

	@Override
	public void run() {
		IDocument document = getDocument();
		if (document == null) {
			return;
		}
		IToggleSpawnpointsTarget adapter = fPart.getAdapter(IToggleSpawnpointsTarget.class);
		if (adapter == null) {
			// attempt to force load adapter
			IAdapterManager manager = Platform.getAdapterManager();
			if (manager.hasAdapter(fPart, IToggleSpawnpointsTarget.class.getName())) {
				adapter = (IToggleSpawnpointsTarget) manager.loadAdapter(fPart,
						IToggleSpawnpointsTarget.class.getName());
			}
		}
		if (adapter == null) {
			return;
		}
		int line = fRulerInfo.getLineOfLastMouseButtonActivity();

		// Test if line is valid
		if (line == -1)
			return;

		try {
			ITextSelection selection = getTextSelection(document, line);
			if (adapter.canToggleSpawnpoints(fPart, selection)) {
				adapter.toggleSpawnpoints(fPart, selection);
			}
		} catch (BadLocationException e) {
			reportException(e);
		} catch (CoreException e) {
			reportException(e);
		}
	}

	/**
	 * Report an error to the user.
	 *
	 * @param e
	 *            underlying exception
	 */
	private void reportException(Exception e) {
		DebugUIPlugin.errorDialog(fPart.getSite().getShell(), ActionMessages.ToggleSpawnpointAction_1,
				ActionMessages.ToggleSpawnpointAction_2, e);
	}

	/**
	 * Disposes this action. Clients must call this method when this action is no
	 * longer needed.
	 */
	public void dispose() {
		fDocument = null;
		fPart = null;
		fRulerInfo = null;
	}

	/**
	 * Returns the document on which this action operates.
	 *
	 * @return the document or <code>null</code> if none
	 */
	private IDocument getDocument() {
		if (fDocument != null)
			return fDocument;

		if (fPart instanceof ITextEditor) {
			ITextEditor editor = (ITextEditor) fPart;
			IDocumentProvider provider = editor.getDocumentProvider();
			if (provider != null)
				return provider.getDocument(editor.getEditorInput());
		}

		IDocument doc = fPart.getAdapter(IDocument.class);
		if (doc != null) {
			return doc;
		}

		return null;
	}

	@Override
	public void update() {
		IDocument document = getDocument();
		if (document != null) {
			IToggleSpawnpointsTarget adapter = fPart.getAdapter(IToggleSpawnpointsTarget.class);
			if (adapter == null) {
				// attempt to force load adapter
				IAdapterManager manager = Platform.getAdapterManager();
				if (manager.hasAdapter(fPart, IToggleSpawnpointsTarget.class.getName())) {
					adapter = (IToggleSpawnpointsTarget) manager.loadAdapter(fPart,
							IToggleSpawnpointsTarget.class.getName());
				}
			}
			if (adapter != null) {
				int line = fRulerInfo.getLineOfLastMouseButtonActivity();
				if (line > -1) {
					try {
						ITextSelection selection = getTextSelection(document, line);
						if (adapter.canToggleSpawnpoints(fPart, selection)) {
							setEnabled(true);
							return;
						}
					} catch (BadLocationException e) {
						reportException(e);
					}
				}
			}
		}
		setEnabled(false);
	}

	/**
	 * Determines the text selection for the breakpoint action. If clicking on the
	 * ruler inside the highlighted text, return the text selection for the
	 * highlighted text. Otherwise, return a text selection representing the start
	 * of the line.
	 *
	 * @param document
	 *            The IDocument backing the Editor.
	 * @param line
	 *            The line clicked on in the ruler.
	 * @return An ITextSelection as described.
	 * @throws BadLocationException
	 *             If underlying operations throw.
	 */
	private ITextSelection getTextSelection(IDocument document, int line) throws BadLocationException {
		IRegion region = document.getLineInformation(line);
		ITextSelection textSelection = new TextSelection(document, region.getOffset(), 0);
		ISelectionProvider provider = fPart.getSite().getSelectionProvider();
		if (provider != null) {
			ISelection selection = provider.getSelection();
			if (selection instanceof ITextSelection && ((ITextSelection) selection).getStartLine() <= line
					&& ((ITextSelection) selection).getEndLine() >= line) {
				textSelection = (ITextSelection) selection;
			}
		}
		return textSelection;
	}

}
