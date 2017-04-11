/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.ui.text.completion;

import org.eclipse.dltk.core.CompletionProposal;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.tcl.core.TclNature;
import org.eclipse.dltk.ui.text.completion.IScriptCompletionProposal;
import org.eclipse.dltk.ui.text.completion.ScriptCompletionProposal;
import org.eclipse.dltk.ui.text.completion.ScriptCompletionProposalCollector;
import org.eclipse.swt.graphics.Image;

public class TclCompletionProposalCollector
		extends ScriptCompletionProposalCollector {

	protected final static char[] VAR_TRIGGER = new char[] { '\t', ' ', '=',
			';', '.' };

	@Override
	protected char[] getVarTrigger() {
		return VAR_TRIGGER;
	}

	public TclCompletionProposalCollector(ISourceModule module) {
		super(module);
	}

	@Override
	protected boolean isFiltered(CompletionProposal proposal) {
		if (isIgnored(proposal.getKind())) {
			return true;
		}
		if (true) {
			return false; // FIXME something is missing below
		}
		String name = proposal.getName();
		String completion = proposal.getCompletion();
		// Filter duplicates, by name and model element.
		CompletionProposal[] proposals = getRawCompletionProposals();
		for (int i = 0; i < proposals.length; i++) {
			String pName = proposals[i].getName();
			proposal.getCompletion();
			if (name.equals(pName)) {
				if (proposal.getModelElement() != null
						&& proposal.getModelElement()
								.equals(proposals[i].getModelElement())) {

				}
			}
		}
		return false;
	}

	/**
	 * @return
	 */
	private CompletionProposal[] getRawCompletionProposals() {
		// TODO Auto-generated method stub
		return new CompletionProposal[0];
	}

	// Specific proposals creation. May be use factory?
	@Override
	protected ScriptCompletionProposal createScriptCompletionProposal(
			String completion, int replaceStart, int length, Image image,
			String displayString, int i) {
		return new TclScriptCompletionProposal(displayString, replaceStart,
				length, image, displayString, i);
	}

	@Override
	protected ScriptCompletionProposal createScriptCompletionProposal(
			String completion, int replaceStart, int length, Image image,
			String displayString, int i, boolean isInDoc) {
		return new TclScriptCompletionProposal(displayString, replaceStart,
				length, image, displayString, i, isInDoc);
	}

	@Override
	protected ScriptCompletionProposal createOverrideCompletionProposal(
			IScriptProject scriptProject, ISourceModule compilationUnit,
			String name, String[] paramTypes, int start, int length,
			String displayName, String completionProposal) {
		return new TclOverrideCompletionProposal(scriptProject, compilationUnit,
				name, paramTypes, start, length, displayName,
				completionProposal);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.dltk.ui.text.completion.ScriptCompletionProposalCollector
	 * #createKeywordProposal(org.eclipse.dltk.core.CompletionProposal)
	 */
	@Override
	protected IScriptCompletionProposal createKeywordProposal(
			CompletionProposal proposal) {
		final ScriptCompletionProposal completionProposal = (ScriptCompletionProposal) super.createKeywordProposal(
				proposal);
		final String prefix;
		if (proposal.getExtraInfo() instanceof String) {
			prefix = (String) proposal.getExtraInfo();
		} else {
			prefix = completionProposal.getReplacementString();
		}
		completionProposal.setContextInformation(
				new TclKeywordLazyContextInformation(completionProposal, prefix,
						getSourceModule()));
		return completionProposal;
	}

	@Override
	protected String getNatureId() {
		return TclNature.NATURE_ID;
	}

	// protected IScriptCompletionProposal createKeywordProposal(
	// CompletionProposal proposal) {
	// String completion = String.valueOf(proposal.getCompletion());
	// int start = proposal.getReplaceStart();
	// int length = getLength(proposal);
	// String label = getLabelProvider().createSimpleLabel(proposal);
	// Image img = getImage(getLabelProvider().createMethodImageDescriptor(
	// proposal));
	// int relevance = computeRelevance(proposal);
	// return createScriptCompletionProposal(completion, start, length, img,
	// label, relevance);
	// }
}
