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

import java.io.IOException;
import java.util.List;

import org.eclipse.dltk.console.IScriptConsoleShell;
import org.eclipse.dltk.console.ScriptConsoleCompletionProposal;
import org.eclipse.dltk.console.ui.IScriptConsoleViewer;
import org.eclipse.dltk.console.ui.ScriptConsoleCompletionProcessor;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.TextPresentation;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationPresenter;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.eclipse.swt.graphics.Image;

public class TclConsoleCompletionProcessor extends ScriptConsoleCompletionProcessor {

	protected static class Validator implements IContextInformationValidator, IContextInformationPresenter {

		protected int installOffset;

		@Override
		public boolean isContextInformationValid(int offset) {
			return Math.abs(installOffset - offset) < 5;
		}

		@Override
		public void install(IContextInformation info, ITextViewer viewer, int offset) {
			installOffset = offset;
		}

		@Override
		public boolean updatePresentation(int documentPosition, TextPresentation presentation) {
			return false;
		}
	}

	protected IProposalDecorator tclDecorator = new IProposalDecorator() {
		@Override
		public String formatProposal(ScriptConsoleCompletionProposal c) {
			return c.getDisplay();
		}

		@Override
		public Image getImage(ScriptConsoleCompletionProposal c) {
			String type = c.getType();
			if (type.equals("var")) {
				return DLTKPluginImages.get(DLTKPluginImages.IMG_OBJS_LOCAL_VARIABLE);
			} else if (type.equals("proc")) {
				return DLTKPluginImages.get(DLTKPluginImages.IMG_METHOD_PUBLIC);
			} else if (type.equals("command")) {
				return DLTKPluginImages.get(DLTKPluginImages.IMG_METHOD_PRIVATE);
			} else if (type.equals("func")) {
				return DLTKPluginImages.get(DLTKPluginImages.IMG_OBJS_FIELD);
			}

			return null;
		}
	};

	private IContextInformationValidator validator;

	public TclConsoleCompletionProcessor(IScriptConsoleShell interpreterShell) {
		super(interpreterShell);
	}

	@Override
	public char[] getCompletionProposalAutoActivationCharacters() {
		return new char[] { '$' };
	}

	@Override
	protected ICompletionProposal[] computeCompletionProposalsImpl(IScriptConsoleViewer viewer, int offset) {

		try {
			String commandLine = viewer.getCommandLine();
			int cursorPosition = offset - viewer.getCommandLineOffset();

			List list = getInterpreterShell().getCompletions(commandLine, cursorPosition);

			List<CompletionProposal> proposals = createProposalsFromString(list, offset, tclDecorator);

			return proposals.toArray(new ICompletionProposal[proposals.size()]);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new ICompletionProposal[] {};
	}

	@Override
	protected IContextInformation[] computeContextInformationImpl(ITextViewer viewer, int offset) {
		return null;
	}

	@Override
	public IContextInformationValidator getContextInformationValidator() {
		if (validator == null) {
			validator = new Validator();
		}

		return validator;
	}
}
