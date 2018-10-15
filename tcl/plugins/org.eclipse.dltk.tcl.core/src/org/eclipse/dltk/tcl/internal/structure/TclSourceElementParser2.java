/*******************************************************************************
 * Copyright (c) 2009 xored software, Inc.  
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
package org.eclipse.dltk.tcl.internal.structure;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.dltk.compiler.ISourceElementRequestor;
import org.eclipse.dltk.compiler.SourceElementRequestorMode;
import org.eclipse.dltk.compiler.env.IModuleSource;
import org.eclipse.dltk.compiler.problem.IProblemReporter;
import org.eclipse.dltk.compiler.problem.ProblemSeverity;
import org.eclipse.dltk.core.ISourceElementParser;
import org.eclipse.dltk.core.builder.ISourceLineTracker;
import org.eclipse.dltk.tcl.ast.TclCommand;
import org.eclipse.dltk.tcl.ast.TclModule;
import org.eclipse.dltk.tcl.core.TclPlugin;
import org.eclipse.dltk.tcl.internal.parser.NewTclSourceParser;
import org.eclipse.dltk.tcl.internal.parser.TclSourceElementParser;
import org.eclipse.dltk.tcl.parser.ITclErrorReporter;
import org.eclipse.dltk.tcl.parser.TclErrorCollector;
import org.eclipse.dltk.tcl.parser.TclParser;
import org.eclipse.dltk.tcl.parser.TclParserUtils;
import org.eclipse.dltk.tcl.parser.TclVisitor;
import org.eclipse.dltk.tcl.parser.definitions.DefinitionManager;
import org.eclipse.dltk.tcl.parser.definitions.NamespaceScopeProcessor;
import org.eclipse.dltk.tcl.structure.ITclModelBuildContext;
import org.eclipse.dltk.tcl.structure.ITclModelBuilder;
import org.eclipse.dltk.tcl.structure.ITclModelBuilderDetector;
import org.eclipse.dltk.tcl.structure.TclModelProblem;
import org.eclipse.dltk.tcl.structure.TclProcessorUtil;

public class TclSourceElementParser2 extends TclSourceElementParser implements
		ISourceElementParser {

	private class TclModelBuilderVisitor extends TclVisitor {

		private final TclModelBuildContext context;

		/**
		 * @param context
		 */
		private TclModelBuilderVisitor(TclModelBuildContext context) {
			this.context = context;
		}

		@Override
		public boolean visit(TclCommand command) {
			final String commandName = TclProcessorUtil.asString(command
					.getName());
			// context.resetAttributes();
			for (ITclModelBuilderDetector detector : detectors) {
				final String builderId = detector.detect(commandName, command,
						context);
				if (builderId != null) {
					final ITclModelBuilder builder = getBuilder(builderId, true);
					if (builder != null) {
						try {
							return builder.process(command, context);
						} catch (TclModelProblem e) {
							// TODO Auto-generated catch block
							return false;
						}
					}
					return true;
				}
			}
			final ITclModelBuilder builder = getBuilder(commandName, false);
			if (builder != null) {
				try {
					return builder.process(command, context);
				} catch (TclModelProblem e) {
					// TODO Auto-generated catch block
					return false;
				}
			}
			return true;
		}

		@Override
		public void endVisit(TclCommand command) {
			context.leave(command);
		}
	}

	public static final boolean USE_NEW = true;

	@Override
	public void parseSourceModule(IModuleSource module) {
		final ISourceElementRequestor requestor = getRequestor();
		if (USE_NEW && SourceElementRequestorMode.STRUCTURE.matches(requestor)) {
			initDetectors();
			final IProblemReporter reporter = getProblemReporter();
			// TODO load from disk cache
			// TODO load from memory cache
			TclErrorCollector collector = (reporter != null) ? new TclErrorCollector()
					: null;
			final String source = module.getSourceContents();
			final TclModelBuildContext context = new TclModelBuildContext(this,
					requestor, collector);
			requestor.enterModule();
			//
			final TclParser newParser = createParser();
			final NamespaceScopeProcessor coreProcessor = DefinitionManager
					.getInstance().createProcessor();
			TclModule tclModule = newParser.parseModule(source,
					context.getErrorReporter(), coreProcessor);
			traverse(tclModule.getStatements(), context);
			//
			requestor.exitModule(source.length());
			if (collector != null) {
				final ISourceLineTracker tracker = NewTclSourceParser
						.createLineTracker(tclModule);
				collector.reportAll(reporter, tracker);
			}
		} else {
			super.parseSourceModule(module);
		}
	}

	public List<TclCommand> parse(String source, int offset) {
		initDetectors();
		ITclModelBuildContext context = new TclModelBuildContext(this,
				getRequestor(), new ITclErrorReporter() {
					public void report(int code, String message,
							String[] extraMessage, int start, int end,
							ProblemSeverity kind) {
						// empty
					}
				});
		final TclParser newParser = createParser();
		newParser.setGlobalOffset(offset);
		final NamespaceScopeProcessor coreProcessor = DefinitionManager
				.getInstance().createProcessor();
		List<TclCommand> commands = newParser.parse(source,
				context.getErrorReporter(), coreProcessor);
		traverse(commands, (TclModelBuildContext) context);
		return commands;
	}

	protected TclParser createParser() {
		return new TclParser();
	}

	protected void traverse(List<TclCommand> commands,
			TclModelBuildContext context) {
		TclParserUtils.traverse(commands, new TclModelBuilderVisitor(context));
	}

	private void initDetectors() {
		if (detectors == null) {
			detectors = ModelBuilderManager.getInstance().getDetectors();
		}
	}

	protected ITclModelBuilderDetector[] detectors = null;

	private Map<String, ITclModelBuilder> builders = new HashMap<String, ITclModelBuilder>();

	private static final ITclModelBuilder NULL_BUILDER = new ITclModelBuilder() {
		public boolean process(TclCommand command, ITclModelBuildContext context) {
			return true;
		}
	};

	private static final Set<String> LOGGED_BUILDERS = Collections
			.synchronizedSet(new HashSet<String>());

	protected ITclModelBuilder getBuilder(String id, boolean logMissingAsError) {
		ITclModelBuilder builder = builders.get(id);
		if (builder == null) {
			builder = ModelBuilderManager.getInstance().getModelBuilder(id);
			if (builder == null) {
				if (logMissingAsError && LOGGED_BUILDERS.add(id)) {
					TclPlugin.error("Tcl Model Builder '" + id
							+ "' is not found");
				}
				builder = NULL_BUILDER;
			}
			builders.put(id, builder);
		}
		return builder != NULL_BUILDER ? builder : null;
	}

}
