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
package org.eclipse.dltk.tcl.indexing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.dltk.core.IExternalSourceModule;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.tcl.ast.TclArgument;
import org.eclipse.dltk.tcl.ast.TclCommand;
import org.eclipse.dltk.tcl.core.TclPlugin;
import org.eclipse.dltk.tcl.core.packages.TclModuleInfo;
import org.eclipse.dltk.tcl.core.packages.TclPackagesFactory;
import org.eclipse.dltk.tcl.core.packages.TclSourceEntry;
import org.eclipse.dltk.tcl.definitions.Command;
import org.eclipse.dltk.tcl.internal.core.packages.TclPackageSourceModule;
import org.eclipse.dltk.tcl.parser.TclParserUtils;
import org.eclipse.dltk.tcl.parser.TclVisitor;
import org.eclipse.dltk.tcl.parser.printer.SimpleCodePrinter;
import org.eclipse.emf.common.util.EList;

public class PackageSourceCollector extends TclVisitor {

	public static final String PACKAGE = "package"; //$NON-NLS-1$
	static final String REQUIRE = "require"; //$NON-NLS-1$
	static final String PROVIDE = "provide"; //$NON-NLS-1$
	static final String IFNEEDED = "ifneeded"; //$NON-NLS-1$
	static final String EXACT = "-exact"; //$NON-NLS-1$

	static final String SOURCE = "source";//$NON-NLS-1$

	private final Map<IScriptProject, List<TclModuleInfo>> modules = new HashMap<IScriptProject, List<TclModuleInfo>>();
	private TclModuleInfo currentModuleInfo;

	private ISourceModule currentModule;

	public void process(List<TclCommand> declaration, ISourceModule module) {
		this.currentModule = module;
		try {
			currentModuleInfo = getCreateCurrentModuleInfo(module);
			currentModuleInfo.getProvided().clear();
			currentModuleInfo.getRequired().clear();
			currentModuleInfo.getSourced().clear();
			TclParserUtils.traverse(declaration, this);
		} catch (Exception e) {
			TclPlugin.error(e);
		}
	}

	public TclModuleInfo getCreateCurrentModuleInfo(ISourceModule module) {
		if (module != null) {
			String identifier = module.getHandleIdentifier();
			IScriptProject scriptProject = module.getScriptProject();
			List<TclModuleInfo> list = modules.get(scriptProject);
			if (list == null) {
				list = new ArrayList<TclModuleInfo>();
				modules.put(scriptProject, list);
			}
			TclModuleInfo result = null;
			// Remove all old required and provided packages.
			for (TclModuleInfo info : list) {
				if (info.getHandle().equals(identifier)) {
					result = info;
					break;
				}
			}
			if (result == null) {
				result = TclPackagesFactory.eINSTANCE.createTclModuleInfo();
				result.setHandle(identifier);
				result.setExternal(module instanceof IExternalSourceModule);
				list.add(result);
			}
			return result;
		}
		return TclPackagesFactory.eINSTANCE.createTclModuleInfo();
	}

	public boolean visit(TclCommand command) {
		Command definition = command.getDefinition();
		if (definition != null && PACKAGE.equals(definition.getName())) {
			processPackageCommand(command);
		}
		if (!(currentModule instanceof TclPackageSourceModule)) {
			if (definition != null && SOURCE.equals(definition.getName())) {
				processSourceCommand(command);
			}
		}
		return super.visit(command);
	}

	private TclArgument getStringArg(EList<TclArgument> args, int index) {
		if (index < args.size()) {
			return args.get(index);
		}
		return null;
	}

	private void processPackageCommand(TclCommand command) {
		EList<TclArgument> args = command.getArguments();
		TclArgument style = getStringArg(args, 0);
		if (style == null) {
			return;
		}
		final String keyword = SimpleCodePrinter.getArgumentString(style, style
				.getStart());
		if (REQUIRE.equalsIgnoreCase(keyword)) {
			TclArgument pkgName = getStringArg(args, 1);
			if (pkgName == null) {
				return;
			}
			String packageName = SimpleCodePrinter.getArgumentString(pkgName,
					pkgName.getStart());
			if (EXACT.equals(packageName)) {
				pkgName = getStringArg(args, 2);
				if (pkgName == null) {
					return;
				}
				packageName = SimpleCodePrinter.getArgumentString(pkgName,
						pkgName.getStart());
			}
			// if (TclPackagesManager.isValidPackageName(packageName)) {
			TclSourceEntry entry = TclPackagesFactory.eINSTANCE
					.createTclSourceEntry();
			entry.setValue(packageName);
			entry.setStart(pkgName.getStart());
			entry.setEnd(pkgName.getEnd());
			currentModuleInfo.getRequired().add(entry);
			// }
		} else if (/*
					 * IFNEEDED.equalsIgnoreCase(keyword) ||
					 */PROVIDE.equalsIgnoreCase(keyword)) {
			TclArgument pkgName = getStringArg(args, 1);
			if (pkgName == null) {
				return;
			}
			String pkg = SimpleCodePrinter.getArgumentString(pkgName, pkgName
					.getStart());
			// if (TclPackagesManager.isValidPackageName(pkg)) {
			// packagesProvided.add(new PackageInfo(pkg, null, currentModule));
			// }
			TclSourceEntry entry = TclPackagesFactory.eINSTANCE
					.createTclSourceEntry();
			entry.setValue(pkg);
			entry.setStart(pkgName.getStart());
			entry.setEnd(pkgName.getEnd());
			currentModuleInfo.getProvided().add(entry);
		}
	}

	private void processSourceCommand(TclCommand command) {
		EList<TclArgument> arguments = command.getArguments();
		for (TclArgument tclArgument : arguments) {
			TclSourceEntry entry = TclPackagesFactory.eINSTANCE
					.createTclSourceEntry();
			entry.setStart(tclArgument.getStart());
			entry.setEnd(tclArgument.getEnd());
			this.currentModuleInfo.getSourced().add(entry);
			entry.setValue(SimpleCodePrinter.getArgumentString(tclArgument,
					tclArgument.getStart()));
		}
	}

	/**
	 * @return the packagesProvided
	 */
	public Map<IScriptProject, List<TclModuleInfo>> getModules() {
		return modules;
	}

	/**
	 * For testing purposes only.
	 */
	public TclModuleInfo getCurrentModuleInfo() {
		return currentModuleInfo;
	}
}
