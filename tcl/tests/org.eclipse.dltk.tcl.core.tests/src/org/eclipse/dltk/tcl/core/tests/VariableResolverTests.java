/*******************************************************************************
 * Copyright (c) 2009, 2017 xored software, Inc. and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.tcl.core.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.dltk.tcl.internal.core.packages.TclVariableResolver;
import org.eclipse.dltk.tcl.internal.core.packages.TclVariableResolver.SimpleVariableRegistry;
import org.junit.Test;

public class VariableResolverTests {

	private static TclVariableResolver createResolver() {
		return new TclVariableResolver(new SimpleVariableRegistry(
				Collections.<String, Object> emptyMap()));
	}

	private static TclVariableResolver createResolver(String key,
			Object value) {
		return new TclVariableResolver(new SimpleVariableRegistry(
				Collections.singletonMap(key, value)));
	}

	@Test
	public void testNop() {
		TclVariableResolver resolver = createResolver();
		assertEquals(("a"), resolver.resolve("a"));
		assertNull(resolver.resolve("$a"));
		assertEquals(("${a"), resolver.resolve("${a"));
		assertNull(resolver.resolve("${a}"));
		assertNull(resolver.resolve("$a(alfa)"));
	}

	@Test
	public void testSingle() {
		TclVariableResolver resolver = createResolver("name", "NAME");
		assertEquals(("NAME"), resolver.resolve("$name"));
		assertEquals(("NAME"), resolver.resolve("${name}"));
	}

	@Test
	public void testSingleMixed() {
		TclVariableResolver resolver = createResolver("name", "NAME");
		assertEquals(("/NAME/"), resolver.resolve("/$name/"));
		assertEquals(("/NAME/"), resolver.resolve("/${name}/"));
	}

	@Test
	public void testComplex() {
		TclVariableResolver resolver = createResolver("name", "VERY_VERY_BIG");
		assertEquals(("zaa/VERY_VERY_BIG/bbb"),
				resolver.resolve("zaa/$name/bbb"));
	}

	@Test
	public void testSmaller() {
		TclVariableResolver resolver = createResolver("longVariable", "small");
		assertEquals(("zaa/small/bbb"),
				resolver.resolve("zaa/$longVariable/bbb"));
	}

	@Test
	public void testEnvironmentResolve() {
		Map<String, String> envValues = new HashMap<>();
		Map<String, Object> variables = new HashMap<>();
		envValues.put("mytest", "myvalue");
		variables.put("env", envValues);
		variables.put("gamma", "mytest");
		variables.put("env(HOME)", "/home/dltk");

		TclVariableResolver resolver = new TclVariableResolver(
				new SimpleVariableRegistry(variables));
		assertEquals("myvalue", resolver.resolve("$env(mytest)"));
		assertEquals("myvalue", resolver.resolve("$env($gamma)"));
		assertEquals("/home/dltk", resolver.resolve("$env(HOME)"));
		assertNull(resolver.resolve("$env($alpha)"));
	}

	@Test
	public void testNestedExpressions() {
		Map<String, Object> variables = new HashMap<>();
		variables.put("a", "$b");
		variables.put("b", "YES");
		TclVariableResolver resolver = new TclVariableResolver(
				new SimpleVariableRegistry(variables));
		assertEquals("YES", resolver.resolve("$a"));
	}

	@Test
	public void testNestedCycle() {
		Map<String, Object> variables = new HashMap<>();
		variables.put("a", "$b");
		variables.put("b", "$a");
		TclVariableResolver resolver = new TclVariableResolver(
				new SimpleVariableRegistry(variables));
		assertNull(resolver.resolve("$a"));
	}

}
