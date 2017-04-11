/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.ui.text;

import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.internal.ui.editor.EditorUtility;
import org.eclipse.dltk.internal.ui.editor.ScriptSourceViewer;
import org.eclipse.dltk.internal.ui.text.ScriptElementProvider;
import org.eclipse.dltk.tcl.internal.ui.hierarchy.TclHierarchyInformationControl;
import org.eclipse.dltk.tcl.internal.ui.text.completion.TclContentAssistPreference;
import org.eclipse.dltk.tcl.internal.ui.text.completion.TclScriptCompletionProcessor;
import org.eclipse.dltk.tcl.ui.text.TclPartitions;
import org.eclipse.dltk.ui.text.AbstractScriptScanner;
import org.eclipse.dltk.ui.text.IColorManager;
import org.eclipse.dltk.ui.text.ScriptPresentationReconciler;
import org.eclipse.dltk.ui.text.ScriptSourceViewerConfiguration;
import org.eclipse.dltk.ui.text.completion.ContentAssistPreference;
import org.eclipse.dltk.ui.text.completion.ContentAssistProcessor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.AbstractInformationControlManager;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.information.IInformationPresenter;
import org.eclipse.jface.text.information.IInformationProvider;
import org.eclipse.jface.text.information.InformationPresenter;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.ui.texteditor.ITextEditor;

public class TclSourceViewerConfiguration
		extends ScriptSourceViewerConfiguration {

	private TclTextTools fTextTools;

	private TclCodeScanner fCodeScanner;
	private AbstractScriptScanner fStringScanner;
	private AbstractScriptScanner fCommentScanner;

	public TclSourceViewerConfiguration(IColorManager colorManager,
			IPreferenceStore preferenceStore, ITextEditor editor,
			String partitioning) {
		super(colorManager, preferenceStore, editor, partitioning);
	}

	@Override
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return TclPartitions.TCL_PARTITION_TYPES;
	}

	private IInformationControlCreator getHierarchyPresenterControlCreator(
			ISourceViewer sourceViewer) {
		return parent -> {
			int shellStyle = SWT.RESIZE;
			int treeStyle = SWT.V_SCROLL | SWT.H_SCROLL;
			return new TclHierarchyInformationControl(parent, shellStyle,
					treeStyle);
		};
	}

	@Override
	public IInformationPresenter getHierarchyPresenter(
			ScriptSourceViewer sourceViewer, boolean doCodeResolve) {
		// Do not create hierarchy presenter if there's no CU.
		if (getEditor() != null && getEditor().getEditorInput() != null
				&& EditorUtility.getEditorInputModelElement(getEditor(),
						true) == null)
			return null;

		InformationPresenter presenter = new InformationPresenter(
				getHierarchyPresenterControlCreator(sourceViewer));
		presenter.setDocumentPartitioning(
				getConfiguredDocumentPartitioning(sourceViewer));
		presenter.setAnchor(AbstractInformationControlManager.ANCHOR_GLOBAL);
		IInformationProvider provider = new ScriptElementProvider(getEditor(),
				doCodeResolve);
		presenter.setInformationProvider(provider,
				IDocument.DEFAULT_CONTENT_TYPE);

		presenter.setSizeConstraints(50, 20, true, false);
		return presenter;
	}

	@Override
	protected void initializeScanners() {
		Assert.isTrue(isNewSetup());

		// Creating scanners
		fCodeScanner = new TclCodeScanner(getColorManager(), fPreferenceStore);

		fStringScanner = new TclStringScanner(getColorManager(),
				fPreferenceStore);

		fCommentScanner = createCommentScanner(
				TclColorConstants.TCL_SINGLE_LINE_COMMENT,
				TclColorConstants.TCL_TODO_TAG);
	}

	/**
	 * @return <code>true</code> iff the new setup without text tools is in use.
	 */
	private boolean isNewSetup() {
		return fTextTools == null;
	}

	protected RuleBasedScanner getStringScanner() {
		return fStringScanner;
	}

	protected RuleBasedScanner getCommentScanner() {
		return fCommentScanner;
	}

	@Override
	public IPresentationReconciler getPresentationReconciler(
			ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new ScriptPresentationReconciler();
		reconciler.setDocumentPartitioning(
				getConfiguredDocumentPartitioning(sourceViewer));

		DefaultDamagerRepairer dr = new DefaultDamagerRepairer(
				this.fCodeScanner);
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

		dr = new DefaultDamagerRepairer(getStringScanner());
		reconciler.setDamager(dr, TclPartitions.TCL_STRING);
		reconciler.setRepairer(dr, TclPartitions.TCL_STRING);

		dr = new DefaultDamagerRepairer(getCommentScanner());
		reconciler.setDamager(dr, TclPartitions.TCL_COMMENT);
		reconciler.setRepairer(dr, TclPartitions.TCL_COMMENT);

		return reconciler;
	}

	/**
	 * Adapts the behavior of the contained components to the change encoded in
	 * the given event.
	 * <p>
	 * Clients are not allowed to call this method if the old setup with text
	 * tools is in use.
	 * </p>
	 *
	 * @param event
	 *            the event to which to adapt
	 * @see TclSourceViewerConfiguration#TclSourceViewerConfiguration(IColorManager,
	 *      IPreferenceStore, ITextEditor, String)
	 */
	@Override
	public void handlePropertyChangeEvent(PropertyChangeEvent event) {
		Assert.isTrue(isNewSetup());

		if (fCodeScanner.affectsBehavior(event)) {
			fCodeScanner.adaptToPreferenceChange(event);
		}

		if (fStringScanner.affectsBehavior(event)) {
			fStringScanner.adaptToPreferenceChange(event);
		}

		if (fCommentScanner.affectsBehavior(event)) {
			fCommentScanner.adaptToPreferenceChange(event);
		}
	}

	/**
	 * Determines whether the preference change encoded by the given event
	 * changes the behavior of one of its contained components.
	 *
	 * @param event
	 *            the event to be investigated
	 * @return <code>true</code> if event causes a behavioral change
	 *
	 */
	@Override
	public boolean affectsTextPresentation(PropertyChangeEvent event) {
		return fCodeScanner.affectsBehavior(event)
				|| fStringScanner.affectsBehavior(event)
				|| fCommentScanner.affectsBehavior(event);
	}

	@Override
	public IAutoEditStrategy[] getAutoEditStrategies(ISourceViewer sourceViewer,
			String contentType) {
		// return super.getAutoEditStrategies(sourceViewer, contentType);
		String partitioning = getConfiguredDocumentPartitioning(sourceViewer);
		return new IAutoEditStrategy[] {
				new TclAutoEditStrategy(fPreferenceStore, partitioning) };
	}

	@Override
	protected void alterContentAssistant(ContentAssistant assistant) {
		// IDocument.DEFAULT_CONTENT_TYPE
		IContentAssistProcessor scriptProcessor = new TclScriptCompletionProcessor(
				getEditor(), assistant, IDocument.DEFAULT_CONTENT_TYPE);
		assistant.setContentAssistProcessor(scriptProcessor,
				IDocument.DEFAULT_CONTENT_TYPE);

		// TclPartitions.TCL_COMMENT
		ContentAssistProcessor singleLineProcessor = new TclScriptCompletionProcessor(
				getEditor(), assistant, TclPartitions.TCL_COMMENT);
		assistant.setContentAssistProcessor(singleLineProcessor,
				TclPartitions.TCL_COMMENT);

		// TclPartitions.TCL_STRING
		ContentAssistProcessor stringProcessor = new TclScriptCompletionProcessor(
				getEditor(), assistant, TclPartitions.TCL_STRING);
		assistant.setContentAssistProcessor(stringProcessor,
				TclPartitions.TCL_STRING);
	}

	@Override
	protected ContentAssistPreference getContentAssistPreference() {
		return TclContentAssistPreference.getDefault();
	}

}
