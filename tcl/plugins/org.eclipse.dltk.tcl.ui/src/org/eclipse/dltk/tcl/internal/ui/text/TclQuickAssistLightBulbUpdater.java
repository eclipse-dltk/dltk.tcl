/*******************************************************************************
 * Copyright (c) 2000, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.ui.text;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.tcl.core.TclNature;
import org.eclipse.dltk.tcl.internal.ui.TclUI;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.text.ScriptAnnotationUtils;
import org.eclipse.dltk.ui.text.ScriptCorrectionProcessorManager;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationAccessExtension;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.IAnnotationPresentation;
import org.eclipse.jface.text.source.ImageUtilities;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.texteditor.AnnotationPreference;
import org.eclipse.ui.texteditor.ITextEditor;

public class TclQuickAssistLightBulbUpdater {

	public static class AssistAnnotation extends Annotation
			implements IAnnotationPresentation {

		// XXX: To be fully correct this should be a non-static fields in
		// QuickAssistLightBulbUpdater
		private static final int LAYER;

		static {
			Annotation annotation = new Annotation(
					"org.eclipse.dltk.ui.warning", false, null); //$NON-NLS-1$
			AnnotationPreference preference = EditorsUI
					.getAnnotationPreferenceLookup()
					.getAnnotationPreference(annotation);
			if (preference != null)
				LAYER = preference.getPresentationLayer() - 1;
			else
				LAYER = IAnnotationAccessExtension.DEFAULT_LAYER;

		}

		private Image fImage;

		public AssistAnnotation() {
		}

		@Override
		public int getLayer() {
			return LAYER;
		}

		private Image getImage() {
			if (fImage == null) {
				fImage = DLTKPluginImages
						.get(DLTKPluginImages.IMG_OBJS_QUICK_ASSIST);
			}
			return fImage;
		}

		@Override
		public void paint(GC gc, Canvas canvas, Rectangle r) {
			ImageUtilities.drawImage(getImage(), gc, canvas, r, SWT.CENTER,
					SWT.TOP);
		}

	}

	private final Annotation fAnnotation;
	private boolean fIsAnnotationShown;
	private ITextEditor fEditor;

	private ISelectionChangedListener fListener;
	private IPropertyChangeListener fPropertyChangeListener;

	public TclQuickAssistLightBulbUpdater(ITextEditor part,
			ITextViewer viewer) {
		fEditor = part;
		fAnnotation = new AssistAnnotation();
		fIsAnnotationShown = false;
		fPropertyChangeListener = null;
	}

	public boolean isSetInPreferences() {
		return true;
	}

	private void installSelectionListener() {
		fListener = event -> {
			ISelection selection = event.getSelection();
			if (selection instanceof ITextSelection) {
				ITextSelection textSelection = (ITextSelection) selection;
				doSelectionChanged(textSelection.getOffset(),
						textSelection.getLength());
			}
		};
		fEditor.getSelectionProvider().addSelectionChangedListener(fListener);
	}

	private void uninstallSelectionListener() {
		if (fListener != null) {
			fEditor.getSelectionProvider()
					.removeSelectionChangedListener(this.fListener);
			fListener = null;
		}
		IAnnotationModel model = getAnnotationModel();
		if (model != null) {
			removeLightBulb(model);
		}
	}

	public void install() {
		if (isSetInPreferences()) {
			installSelectionListener();
		}
		if (fPropertyChangeListener == null) {
			fPropertyChangeListener = event -> doPropertyChanged(
					event.getProperty());
			TclUI.getDefault().getPreferenceStore()
					.addPropertyChangeListener(fPropertyChangeListener);
		}
	}

	public void uninstall() {
		uninstallSelectionListener();
		if (fPropertyChangeListener != null) {
			TclUI.getDefault().getPreferenceStore()
					.removePropertyChangeListener(fPropertyChangeListener);
			fPropertyChangeListener = null;
		}
	}

	protected void doPropertyChanged(String property) {
		// if
		// (property.equals(PreferenceConstants.EDITOR_QUICKASSIST_LIGHTBULB)) {
		// if (isSetInPreferences()) {
		// ISourceModule cu = getSourceModule();
		// if (cu != null) {
		// installSelectionListener();
		// Point point = fViewer.getSelectedRange();
		// CompilationUnit astRoot = SharedASTProvider.getAST(cu,
		// SharedASTProvider.WAIT_ACTIVE_ONLY, null);
		// if (astRoot != null) {
		// doSelectionChanged(point.x, point.y, astRoot);
		// }
		// }
		// } else {
		// uninstallSelectionListener();
		// }
		// }
	}

	private ISourceModule getSourceModule() {
		IModelElement elem = DLTKUIPlugin
				.getEditorInputModelElement(fEditor.getEditorInput());
		if (elem instanceof ISourceModule) {
			return (ISourceModule) elem;
		}
		return null;
	}

	private IAnnotationModel getAnnotationModel() {
		return DLTKUIPlugin.getDocumentProvider()
				.getAnnotationModel(fEditor.getEditorInput());
	}

	private IDocument getDocument() {
		return DLTKUIPlugin.getDocumentProvider()
				.getDocument(fEditor.getEditorInput());
	}

	private void doSelectionChanged(int offset, int length) {

		final IAnnotationModel model = getAnnotationModel();
		final ISourceModule cu = getSourceModule();
		if (model == null || cu == null) {
			return;
		}

		boolean hasQuickFix = hasQuickFixLightBulb(model, offset);
		if (hasQuickFix) {
			removeLightBulb(model);
			return; // there is already a quick fix light bulb at the new
			// location
		}

		calculateLightBulb(model, offset, length);
	}

	/*
	 * Needs to be called synchronized
	 */
	private void calculateLightBulb(IAnnotationModel model, int offset,
			int length) {
		boolean needsAnnotation = true;
		if (fIsAnnotationShown) {
			model.removeAnnotation(fAnnotation);
		}
		if (needsAnnotation) {
			model.addAnnotation(fAnnotation, new Position(offset, length));
		}
		fIsAnnotationShown = needsAnnotation;
	}

	private void removeLightBulb(IAnnotationModel model) {
		synchronized (this) {
			if (fIsAnnotationShown) {
				model.removeAnnotation(fAnnotation);
				fIsAnnotationShown = false;
			}
		}
	}

	/*
	 * Tests if there is already a quick fix light bulb on the current line
	 */
	private boolean hasQuickFixLightBulb(IAnnotationModel model, int offset) {
		try {
			IDocument document = getDocument();
			if (document == null) {
				return false;
			}

			// we access a document and annotation model from within a job
			// since these are only read accesses, we won't hurt anyone else if
			// this goes boink

			// may throw an IndexOutOfBoundsException upon concurrent document
			// modification
			int currLine = document.getLineOfOffset(offset);

			// this iterator is not protected, it may throw
			// ConcurrentModificationExceptions
			Iterator<Annotation> iter = model.getAnnotationIterator();
			while (iter.hasNext()) {
				Annotation annot = iter.next();
				if (ScriptAnnotationUtils.isQuickFixableType(annot)) {
					// may throw an IndexOutOfBoundsException upon concurrent
					// annotation model changes
					Position pos = model.getPosition(annot);
					if (pos != null) {
						// may throw an IndexOutOfBoundsException upon
						// concurrent document modification
						int startLine = document
								.getLineOfOffset(pos.getOffset());
						if (startLine == currLine && hasCorrections(annot)) {
							return true;
						}
					}
				}
			}
		} catch (BadLocationException e) {
			// ignore
		} catch (IndexOutOfBoundsException e) {
			// concurrent modification - too bad, ignore
		} catch (ConcurrentModificationException e) {
			// concurrent modification - too bad, ignore
		}
		return false;
	}

	private boolean hasCorrections(Annotation annot) {
		return ScriptCorrectionProcessorManager.canFix(TclNature.NATURE_ID,
				annot);
	}

}
