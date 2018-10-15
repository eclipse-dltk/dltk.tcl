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

package org.eclipse.dltk.tcl.parser.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.tcl.ast.StringArgument;
import org.eclipse.dltk.tcl.ast.TclArgument;
import org.eclipse.dltk.tcl.ast.TclCommand;
import org.eclipse.dltk.tcl.definitions.ArgumentType;
import org.eclipse.dltk.tcl.definitions.Command;
import org.eclipse.dltk.tcl.definitions.DefinitionsFactory;
import org.eclipse.dltk.tcl.definitions.Group;
import org.eclipse.dltk.tcl.definitions.Namespace;
import org.eclipse.dltk.tcl.definitions.Scope;
import org.eclipse.dltk.tcl.definitions.Switch;
import org.eclipse.dltk.tcl.definitions.TypedArgument;
import org.eclipse.dltk.tcl.parser.TclParser;
import org.eclipse.dltk.tcl.parser.TclParserUtils;
import org.eclipse.dltk.tcl.parser.TclVisitor;
import org.eclipse.dltk.tcl.parser.definitions.DefinitionLoader;
import org.eclipse.dltk.tcl.parser.definitions.NamespaceScopeProcessor;
import org.eclipse.dltk.tcl.parser.internal.tests.Activator;
import org.junit.Test;

public class NamespaceScopeProcessorTests {

	private Namespace createNamespace(DefinitionsFactory factory, Scope scope,
			String name) {
		Namespace namespaceA = factory.createNamespace();
		namespaceA.setName(name);
		scope.getChildren().add(namespaceA);
		return namespaceA;
	}

	private Command commandToNamespace(DefinitionsFactory factory, Scope scope,
			String name) {
		Command command = factory.createCommand();
		command.setName(name);
		scope.getChildren().add(command);
		return command;
	}

	private static Command createNamespaceCommand(DefinitionsFactory factory) {
		Command command = factory.createCommand();
		command.setName("namespace");

		Switch kind = factory.createSwitch();
		Group evalGroup = factory.createGroup();
		evalGroup.setConstant("eval");
		// Eval group
		TypedArgument namespaceEvalName = factory.createTypedArgument();
		namespaceEvalName.setLowerBound(1);
		namespaceEvalName.setUpperBound(1);
		namespaceEvalName.setName("namespaceName");
		namespaceEvalName.setType(ArgumentType.NAMESPACE);
		evalGroup.getArguments().add(namespaceEvalName);

		TypedArgument namespaceEvalScripts = factory.createTypedArgument();
		namespaceEvalScripts.setLowerBound(1);
		namespaceEvalScripts.setUpperBound(-1);
		namespaceEvalScripts.setName("scripts");
		namespaceEvalScripts.setType(ArgumentType.SCRIPT);
		evalGroup.getArguments().add(namespaceEvalScripts);

		kind.getGroups().add(evalGroup);
		command.getArguments().add(kind);
		return command;
	}

	@Test
	public void test001() throws Exception {
		DefinitionsFactory factory = DefinitionsFactory.eINSTANCE;

		Scope scope = factory.createScope();

		Namespace namespaceA = createNamespace(factory, scope, "a");
		Namespace namespaceB = createNamespace(factory, scope, "b");
		Namespace namespaceC = createNamespace(factory, scope, "c");

		Namespace namespaceD = createNamespace(factory, namespaceC, "d");

		Command aC = commandToNamespace(factory, namespaceA, "alfa");
		Command bC = commandToNamespace(factory, namespaceB, "alfa");
		Command cC = commandToNamespace(factory, namespaceC, "alfa");
		Command c = commandToNamespace(factory, scope, "alfa");
		Command dC = commandToNamespace(factory, namespaceD, "alfa");

		String content = TestUtils.getContents(Activator.getDefault()
				.getBundle().getEntry("/scripts/namespace001.tcl"));
		NamespaceScopeProcessor processor = new NamespaceScopeProcessor();
		processor.addScope(createNamespaceCommand(factory));
		processor.addScope(scope);
		TclParser parser = TestUtils.createParser();
		TestTclParserErrorReporter reporter = new TestTclParserErrorReporter();
		List<TclCommand> module = parser.parse(content, reporter, processor);
		final List<Command> alfaDefinitions = new ArrayList<>();
		TclParserUtils.traverse(module, new TclVisitor() {
			@Override
			public boolean visit(TclCommand cmnd) {
				TclArgument name = cmnd.getName();
				StringArgument nameArg = (StringArgument) name;
				if (nameArg.getValue().endsWith("alfa")) {
					Command definition = cmnd.getDefinition();
					if (definition != null) {
						alfaDefinitions.add(definition);
					} else {
						alfaDefinitions.add(null);
					}
				}
				return true;
			}
		});
		assertEquals(7, alfaDefinitions.size());
		for (int i = 0; i < alfaDefinitions.size(); i++) {
			assertNotNull(alfaDefinitions.get(i));
		}
		assertEquals(c, alfaDefinitions.get(0));
		assertEquals(aC, alfaDefinitions.get(1));
		assertEquals(bC, alfaDefinitions.get(2));
		assertEquals(cC, alfaDefinitions.get(3));
		assertEquals(dC, alfaDefinitions.get(4));
		assertEquals(c, alfaDefinitions.get(5));
		assertEquals(c, alfaDefinitions.get(6));
	}

	@Test
	public void test002() throws Exception {
		String content = TestUtils.getContents(Activator.getDefault()
				.getBundle().getEntry("/scripts/namespace002.tcl"));
		NamespaceScopeProcessor processor = new NamespaceScopeProcessor();
		Scope scope = DefinitionLoader.loadDefinitions(new URL(
				"platform:///plugin/org.eclipse.dltk.tcl.tcllib/definitions/builtin.xml"));
		assertNotNull(scope);
		processor.addScope(scope);
		Command[] patternsDefs = processor
				.getCommandDefinition("platform::patterns");
		assertEquals(1, patternsDefs.length);
		Command pattern = patternsDefs[0];
		TclParser parser = TestUtils.createParser();
		TestTclParserErrorReporter reporter = new TestTclParserErrorReporter();
		List<TclCommand> module = parser.parse(content, reporter, processor);
		final List<Command> definitions = new ArrayList<>();
		TclParserUtils.traverse(module, new TclVisitor() {
			@Override
			public boolean visit(TclCommand cmnd) {
				TclArgument name = cmnd.getName();

				StringArgument nameArg = (StringArgument) name;
				if (nameArg.getValue().endsWith("patterns")) {
					Command definition = cmnd.getDefinition();
					if (definition != null) {
						definitions.add(definition);
					} else {
						definitions.add(null);
					}
				}
				return true;
			}
		});
		assertEquals(22, definitions.size());
		for (int i = 0; i < definitions.size(); i++) {
			assertNotNull(definitions.get(i));
			assertEquals(pattern, definitions.get(i));
		}
	}
}
