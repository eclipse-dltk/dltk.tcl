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
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.parser;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.compiler.task.ITodoTaskPreferences;
import org.eclipse.dltk.compiler.task.TodoTaskPreferencesOnPreferenceLookupDelegate;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.builder.AbstractTodoTaskBuildParticipantType;
import org.eclipse.dltk.core.builder.IBuildParticipant;
import org.eclipse.dltk.tcl.ast.expressions.TclBlockExpression;
import org.eclipse.dltk.tcl.core.TclPlugin;

public class TclTodoParserType extends AbstractTodoTaskBuildParticipantType {

	protected ITodoTaskPreferences getPreferences(IScriptProject project) {
		return new TodoTaskPreferencesOnPreferenceLookupDelegate(
				TclPlugin.PLUGIN_ID, project);
	}

	protected IBuildParticipant getBuildParticipant(
			ITodoTaskPreferences preferences) {
		return new TclTodoTaskAstParser(preferences);
	}

	private static class TclTodoTaskAstParser extends TodoTaskBuildParticipant
			implements IBuildParticipant {

		public TclTodoTaskAstParser(ITodoTaskPreferences preferences) {
			super(preferences);
		}

		protected boolean isSimpleNode(ASTNode node) {
			if (node instanceof TclBlockExpression) {
				return true;
			}
			return super.isSimpleNode(node);
		}

	}

}
