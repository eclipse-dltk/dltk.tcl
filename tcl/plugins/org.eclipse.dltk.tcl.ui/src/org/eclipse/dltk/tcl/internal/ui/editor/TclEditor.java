/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.ui.editor;

import org.eclipse.core.filebuffers.IDocumentSetupParticipant;
import org.eclipse.dltk.core.IDLTKLanguageToolkit;
import org.eclipse.dltk.internal.ui.editor.BracketInserter;
import org.eclipse.dltk.internal.ui.editor.ScriptEditor;
import org.eclipse.dltk.internal.ui.editor.ScriptOutlinePage;
import org.eclipse.dltk.tcl.core.TclLanguageToolkit;
import org.eclipse.dltk.tcl.internal.ui.TclUI;
import org.eclipse.dltk.tcl.internal.ui.text.TclPairMatcher;
import org.eclipse.dltk.tcl.internal.ui.text.folding.TclFoldingStructureProvider;
import org.eclipse.dltk.tcl.ui.TclPreferenceConstants;
import org.eclipse.dltk.tcl.ui.text.TclPartitions;
import org.eclipse.dltk.ui.text.ScriptTextTools;
import org.eclipse.dltk.ui.text.folding.IFoldingStructureProvider;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentExtension3;
import org.eclipse.jface.text.ITextViewerExtension;
import org.eclipse.jface.text.source.ICharacterPairMatcher;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;

public class TclEditor extends ScriptEditor {
	public static final String EDITOR_ID = "org.eclipse.dltk.tcl.ui.editor.TclEditor"; //$NON-NLS-1$

	public static final String EDITOR_CONTEXT = "#TclEditorContext"; //$NON-NLS-1$

	public static final String RULER_CONTEXT = "#TclRulerContext"; //$NON-NLS-1$

	private BracketInserter fBracketInserter = new TclBracketInserter(this);

	private IFoldingStructureProvider foldingProvider;

	@Override
	protected void initializeEditor() {
		super.initializeEditor();

		setEditorContextMenuId(EDITOR_CONTEXT);
		setRulerContextMenuId(RULER_CONTEXT);
	}

	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		ISourceViewer sourceViewer = getSourceViewer();
		if (sourceViewer instanceof ITextViewerExtension)
			((ITextViewerExtension) sourceViewer)
					.prependVerifyKeyListener(fBracketInserter);
	}

	final static String[] properties = new String[] {
			TclPreferenceConstants.EDITOR_FOLDING_BLOCKS,
			TclPreferenceConstants.EDITOR_FOLDING_EXCLUDE_LIST,
			TclPreferenceConstants.EDITOR_FOLDING_INCLUDE_LIST, };

	@Override
	protected String[] getFoldingEventPreferenceKeys() {
		return properties;
	}

	@Override
	public IPreferenceStore getScriptPreferenceStore() {
		return TclUI.getDefault().getPreferenceStore();
	}

	@Override
	public ScriptTextTools getTextTools() {
		return TclUI.getDefault().getTextTools();
	}

	@Override
	protected ScriptOutlinePage doCreateOutlinePage() {
		return new TclOutlinePage(this,
				TclUI.getDefault().getPreferenceStore());
	}

	@Override
	protected void connectPartitioningToElement(IEditorInput input,
			IDocument document) {
		if (document instanceof IDocumentExtension3) {
			IDocumentExtension3 doc = (IDocumentExtension3) document;
			if (doc.getDocumentPartitioner(
					TclPartitions.TCL_PARTITIONING) == null) {
				IDocumentSetupParticipant participant = new TclDocumentSetupParticipant();
				participant.setup(document);
			}
		}
	}

	@Override
	protected IFoldingStructureProvider getFoldingStructureProvider() {
		if (foldingProvider == null) {
			foldingProvider = new TclFoldingStructureProvider();
		}

		return foldingProvider;
	}

	@Override
	public String getEditorId() {
		return EDITOR_ID;
	}

	@Override
	protected void initializeKeyBindingScopes() {
		setKeyBindingScopes(
				new String[] { "org.eclipse.dltk.ui.tclEditorScope" }); //$NON-NLS-1$
	}

	@Override
	public IDLTKLanguageToolkit getLanguageToolkit() {
		return TclLanguageToolkit.getDefault();
	}

	@Override
	public String getCallHierarchyID() {
		return "org.eclipse.dltk.callhierarchy.view"; //$NON-NLS-1$
	}

	@Override
	protected ICharacterPairMatcher createBracketMatcher() {
		return new TclPairMatcher();
	}

	@Override
	public void dispose() {
		ISourceViewer sourceViewer = getSourceViewer();
		if (sourceViewer instanceof ITextViewerExtension)
			((ITextViewerExtension) sourceViewer)
					.removeVerifyKeyListener(fBracketInserter);
		super.dispose();
	}
}
