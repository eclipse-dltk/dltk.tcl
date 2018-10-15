/*******************************************************************************
 * Copyright (c) 2009, 2017 xored software, Inc and others.
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
package org.eclipse.dltk.tcl.parser.structure;

import static java.lang.Integer.toHexString;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.eclipse.dltk.compiler.env.IModuleSource;
import org.eclipse.dltk.compiler.util.Util;
import org.eclipse.dltk.core.ISourceElementParser;
import org.eclipse.dltk.core.caching.IStructureConstants;
import org.eclipse.dltk.tcl.core.tests.model.Activator;
import org.eclipse.dltk.tcl.internal.parser.TclSourceElementParser;
import org.eclipse.dltk.tcl.internal.structure.TclSourceElementParser2;
import org.eclipse.dltk.tcl.parser.TclParser;
import org.eclipse.dltk.tcl.parser.structure.Collector.Tag;
import org.eclipse.dltk.tcl.parser.tests.TestUtils;
import org.eclipse.dltk.utils.TextUtils;
import org.osgi.framework.Bundle;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class StructureParserTests extends TestCase {

	final URL resource;

	public StructureParserTests(URL resource) {
		super(resource != null ? resource.getPath() : "null"); //$NON-NLS-1$
		this.resource = resource;
	}

	/**
	 * @param collector
	 */
	private String dump(Collector collector) {
		List<String> lines = new ArrayList<>();
		final byte[] bytes = collector.getBytes();
		final List<Tag> tags = collector.tags;
		// lines.add(tags.toString());
		for (int i = 0; i < tags.size(); ++i) {
			Tag tag = tags.get(i);
			int end = i + 1 < tags.size() ? tags.get(i + 1).offset
					: bytes.length;
			StringBuilder sb = new StringBuilder();
			sb.append(describeTag(tag.tag));
			if (tag.context != null) {
				sb.append("(").append(tag.context).append(")");
			}
			sb.append(":");
			// sb.append(toHexString(tag.offset)).append("..").append(
			// toHexString(end)).append(":");
			for (int j = tag.offset; j < end; ++j) {
				sb.append(' ');
				final int b = bytes[j] & 0xFF;
				if (b < 0x10) {
					sb.append('0');
				}
				sb.append(toHexString(b));
			}
			lines.add(sb.toString());
		}
		return TextUtils.join(lines, Util.LINE_SEPARATOR);
	}

	/**
	 * @param tag
	 * @return
	 */
	private String describeTag(int tag) {
		for (Field field : IStructureConstants.class.getFields()) {
			if (Modifier.isPublic(field.getModifiers())
					&& Modifier.isStatic(field.getModifiers())
					&& Integer.TYPE.equals(field.getType())) {
				try {
					Integer value = (Integer) field.get(null);
					if (value.intValue() == tag) {
						return field.getName();
					}
				} catch (IllegalArgumentException e) {
					// ignore
				} catch (IllegalAccessException e) {
					// ignore
				}
			}
		}
		return "#" + Integer.toString(tag); //$NON-NLS-1$
	}

	@Override
	protected void runTest() throws Throwable {
		ParserInput input = new ParserInput(resource);
		Collector oldStructure = parse(input, new TclSourceElementParser());
		// TclSourceElementParser2.USE_NEW = true;
		Collector newStructure = parse(input, new TclSourceElementParser2() {
			@Override
			protected TclParser createParser() {
				return TestUtils.createParser();
			}
		});
		// assertEquals(oldStructure.tags, newStructure.tags);
		assertEquals(dump(oldStructure), dump(newStructure));
	}

	/**
	 * @param input
	 * @param parser
	 */
	private Collector parse(IModuleSource input, ISourceElementParser parser) {
		Collector collector = new Collector();
		parser.setRequestor(collector);
		parser.parseSourceModule(input);
		return collector;
	}

	public static Test suite() {
		return new StructureParserTests(null)
				.createSuite(Activator.getDefault().getBundle(), "/", "*.tcl");
	}

	public Test createSuite(Bundle bundle, String path, String pattern) {
		TestSuite suite = new TestSuite(getClass().getName());
		Enumeration<URL> e = bundle.findEntries(path, pattern, true);
		while (e.hasMoreElements()) {
			URL url = e.nextElement();
			if (!url.getPath().startsWith("/META-INF")) {
				suite.addTest(new StructureParserTests(url));
			}
		}
		return suite;
	}

}
