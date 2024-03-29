/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.ui.text.completion;

import org.eclipse.dltk.core.CompletionProposal;
import org.eclipse.dltk.tcl.internal.core.codeassist.ITclCompletionProposalTypes;
import org.eclipse.dltk.tcl.internal.ui.TclUI;
import org.eclipse.dltk.tcl.internal.ui.templates.TclTemplateCompletionProcessor;
import org.eclipse.dltk.tcl.ui.TclPreferenceConstants;
import org.eclipse.dltk.ui.text.completion.ScriptCompletionProposalCollector;
import org.eclipse.dltk.ui.text.completion.ScriptCompletionProposalComputer;
import org.eclipse.dltk.ui.text.completion.ScriptContentAssistInvocationContext;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.templates.TemplateCompletionProcessor;

public class TclTypeCompletionProposalComputer
		extends ScriptCompletionProposalComputer {

	@Override
	protected ScriptCompletionProposalCollector createCollector(
			ScriptContentAssistInvocationContext context) {
		ScriptCompletionProposalCollector collector = new TclCompletionProposalCollector(
				context.getSourceModule());

		collector.setIgnored(CompletionProposal.FIELD_REF, false);
		collector.setIgnored(CompletionProposal.KEYWORD, false);
		collector.setIgnored(CompletionProposal.PACKAGE_REF, false);
		collector.setIgnored(CompletionProposal.LABEL_REF, false);
		collector.setIgnored(CompletionProposal.LOCAL_VARIABLE_REF, false);
		collector.setIgnored(CompletionProposal.METHOD_DECLARATION, false);
		collector.setIgnored(CompletionProposal.METHOD_NAME_REFERENCE, false);
		collector.setIgnored(CompletionProposal.METHOD_REF, false);
		collector.setIgnored(CompletionProposal.POTENTIAL_METHOD_DECLARATION,
				false);
		collector.setIgnored(CompletionProposal.VARIABLE_DECLARATION, false);

		collector.setIgnored(CompletionProposal.TYPE_REF, false);

		IPreferenceStore preferenceStore = TclUI.getDefault()
				.getPreferenceStore();
		collector.setIgnored(ITclCompletionProposalTypes.FILTER_INTERNAL_API,
				preferenceStore.getBoolean(
						TclPreferenceConstants.CODEASSIST_FILTER_INTERNAL_API));

		return collector;
	}

	@Override
	protected TemplateCompletionProcessor createTemplateProposalComputer(
			ScriptContentAssistInvocationContext context) {
		return new TclTemplateCompletionProcessor(context);
	}
}
