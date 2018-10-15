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
 *     xored software, Inc. - initial API and Implementation (Andrei Sobolev)
 *******************************************************************************/
package org.eclipse.dltk.tcl.parser.definitions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.eclipse.dltk.tcl.ast.TclCommand;
import org.eclipse.dltk.tcl.definitions.Command;
import org.eclipse.dltk.tcl.definitions.Namespace;
import org.eclipse.dltk.tcl.definitions.Scope;
import org.eclipse.dltk.tcl.parser.ISubstitutionManager;
import org.eclipse.emf.common.util.EList;

public class NamespaceScopeProcessor implements IScopeProcessor {
	/**
	 * Set of all available scopes
	 */
	private Set<Scope> scopes = new HashSet<>();
	/**
	 * Namespac'ed "command" to command map.
	 */
	private Map<String, Set<Command>> commands = new HashMap<>();
	/**
	 * Parsing command stack.
	 */
	private Stack<TclCommand> commandStack = new Stack<>();

	public NamespaceScopeProcessor() {
	}

	// Copy internal data.
	public NamespaceScopeProcessor(NamespaceScopeProcessor coreProcessor) {
		this.scopes = coreProcessor.scopes;
		this.commands = coreProcessor.commands;
		// this.scopes = new HashSet<Scope>(coreProcessor.scopes);
		// Map<String, Set<Command>> map = coreProcessor.commands;
		// for (Entry<String, Set<Command>> entry : map.entrySet()) {
		// commands
		// .put(entry.getKey(), new HashSet<Command>(entry.getValue()));
		// }
	}

	public void addScope(Scope scope) {
		if (scopes.add(scope)) {
			fillCommandsMap(scope, "");
		}
	}

	private void fillCommandsMap(Scope scope, String prefix) {
		String namePrefix = prefix;
		if (scope instanceof Namespace) {
			namePrefix = namePrefix + ((Namespace) scope).getName() + "::";
		}
		EList<Scope> children = scope.getChildren();
		for (Scope child : children) {
			fillCommandsMap(child, namePrefix);
		}
		if (scope instanceof Command) {
			Command command = (Command) scope;
			String key = namePrefix + command.getName();
			if (key.startsWith("::")) {
				key = key.substring(2);
			}
			Set<Command> commands = this.commands.get(key);
			if (commands == null) {
				commands = new HashSet<>();
			}
			commands.add(command);
			this.commands.put(key, commands);
		}
	}

	@Override
	public Command[] getCommandDefinition(String command) {
		if (command.startsWith("::")) {
			// This is top level command
			String commandName = command.substring(2);
			return this.getCommandsByName(commandName);
		}
		// Else try to look for namespace command.
		String[] namespacePrefix = calculatePrefixes(this.commandStack);
		for (int i = namespacePrefix.length; i >= 1; i--) {
			Command[] cmnd = this
					.getCommandsByName(namespacePrefix[i - 1] + command);
			if (cmnd != null && cmnd.length > 0) {
				return cmnd;
			}
		}
		return null;
	}

	private Command[] getCommandsByName(String commandName) {
		if (commandName.startsWith("::")) {
			commandName = commandName.substring(2);
		}
		Set<Command> list = commands.get(commandName);
		if (list == null) {
			return new Command[0];
		}
		return list.toArray(new Command[list.size()]);
	}

	private String[] calculatePrefixes(Stack<TclCommand> commands) {
		StringBuffer buffer = new StringBuffer();
		List<String> results = new ArrayList<>();
		results.add("");// Module root
		for (int i = 0; i < commands.size(); i++) {
			TclCommand command = commands.get(i);
			Command definition = command.getDefinition();
			if (definition != null
					&& definition.getName().equals("namespace")) {
				String name = BuiltinCommandUtils.getNamespaceEvalName(command,
						definition);
				if (name != null) {
					if (name.startsWith("::")) {
						buffer.setLength(0);
						name = name.substring(2);
						buffer.append(name + "::");
					} else {
						buffer.append(name + "::");
					}
					results.add(buffer.toString());
				}
			}
		}
		return results.toArray(new String[results.size()]);
	}

	@Override
	public void processCommand(TclCommand command) {
		commandStack.push(command);
	}

	@Override
	public void endProcessCommand() {
		commandStack.pop();
	}

	public NamespaceScopeProcessor copy() {
		NamespaceScopeProcessor processor = new NamespaceScopeProcessor();
		processor.commands = new HashMap<>(this.commands);
		processor.scopes = new HashSet<>(this.scopes);
		return processor;
	}

	@Override
	public String getQualifiedName(String command) {
		if (command.startsWith("::")) {
			// This is top level command
			String commandName = command.substring(2);
			return commandName;
		}
		// Else try to look for namespace command.
		String[] namespacePrefix = calculatePrefixes(this.commandStack);
		String result = command;
		for (int i = namespacePrefix.length; i >= 1; i--) {
			String name = namespacePrefix[i - 1] + command;
			Command[] cmnd = this.getCommandsByName(name);
			if (cmnd != null && cmnd.length > 0) {
				return name;
			}
			if (i == namespacePrefix.length) {
				result = name;
			}
		}
		return result;
	}

	@Override
	public ISubstitutionManager getSubstitutionManager() {
		return null;
	}

	public Command[] getCommands() {
		List<Command> commands = new ArrayList<>();
		Collection<Set<Command>> values = this.commands.values();
		for (Set<Command> set : values) {
			commands.addAll(set);
		}
		return commands.toArray(new Command[commands.size()]);
	}

	@Override
	public boolean checkCommandScope(Command command) {
		List<Command> scopes = command.getScope();
		if (scopes == null || scopes.size() == 0)
			return true;
		for (Command scope : scopes) {
			String name = scope.getName();
			for (TclCommand tclCommand : commandStack) {
				if (name.equals(tclCommand.getQualifiedName()))
					return true;
			}
		}
		return false;
	}
}
