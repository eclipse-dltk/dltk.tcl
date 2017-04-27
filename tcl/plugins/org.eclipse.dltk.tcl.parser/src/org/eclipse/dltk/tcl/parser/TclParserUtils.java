/*******************************************************************************
 * Copyright (c) 2008, 2017 xored software, Inc. and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Andrei Sobolev)
 *******************************************************************************/
package org.eclipse.dltk.tcl.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.dltk.tcl.ast.ArgumentMatch;
import org.eclipse.dltk.tcl.ast.AstFactory;
import org.eclipse.dltk.tcl.ast.ComplexString;
import org.eclipse.dltk.tcl.ast.Node;
import org.eclipse.dltk.tcl.ast.Script;
import org.eclipse.dltk.tcl.ast.StringArgument;
import org.eclipse.dltk.tcl.ast.Substitution;
import org.eclipse.dltk.tcl.ast.TclArgument;
import org.eclipse.dltk.tcl.ast.TclArgumentList;
import org.eclipse.dltk.tcl.ast.TclCommand;
import org.eclipse.dltk.tcl.ast.VariableReference;
import org.eclipse.dltk.tcl.definitions.Argument;
import org.eclipse.dltk.tcl.definitions.Command;
import org.eclipse.dltk.tcl.definitions.ComplexArgument;
import org.eclipse.dltk.tcl.definitions.Constant;
import org.eclipse.dltk.tcl.definitions.Group;
import org.eclipse.dltk.tcl.definitions.Switch;
import org.eclipse.dltk.tcl.definitions.TypedArgument;
import org.eclipse.dltk.tcl.internal.parser.raw.BracesSubstitution;
import org.eclipse.dltk.tcl.internal.parser.raw.CommandSubstitution;
import org.eclipse.dltk.tcl.internal.parser.raw.QuotesSubstitution;
import org.eclipse.dltk.tcl.internal.parser.raw.SimpleTclParser;
import org.eclipse.dltk.tcl.internal.parser.raw.TclParseException;
import org.eclipse.dltk.tcl.internal.parser.raw.TclScript;
import org.eclipse.dltk.tcl.internal.parser.raw.TclWord;
import org.eclipse.emf.common.util.EList;

public class TclParserUtils implements ITclParserOptions {

	public static final Pattern VERSION_PATTERN = Pattern
			.compile("([\\(\\[][^\\(\\)]*[\\)\\]])"); //$NON-NLS-1$
	public static final Pattern INTERVAL_PATTERN = Pattern
			.compile("([\\(\\[])(.*)[:;](.*)([\\)\\]])"); //$NON-NLS-1$
	public static final Pattern VALID_VERSION_PATTERN = Pattern.compile(
			"([\\(\\[](([0-9]+(\\.[0-9]+)*)|-)[;:](([0-9]+(\\.[0-9]+)*)|-)[\\)\\]]\\s*)*"); //$NON-NLS-1$

	public static boolean isVersionValid(String version) {
		if (version == null || version.length() == 0)
			return true;
		version = version.trim();
		Matcher matcher = VALID_VERSION_PATTERN.matcher(version);
		return matcher.matches();
	}

	public static boolean parseVersion(String version, String currentVersion) {

		if (version == null || version.length() == 0)
			return false;

		Matcher versionMatcher = VERSION_PATTERN.matcher(version);

		boolean isValid = false;

		while (versionMatcher.find()) {

			boolean isIntervalValid = true;

			String interval = versionMatcher.group(1);
			Matcher intervalMatcher = INTERVAL_PATTERN.matcher(interval);

			while (intervalMatcher.find()) {

				String lowerType = intervalMatcher.group(1);
				String lowerVersion = intervalMatcher.group(2);
				String upperVersion = intervalMatcher.group(3);
				String upperType = intervalMatcher.group(4);

				if (!lowerVersion.equals("-")) { //$NON-NLS-1$
					if (lowerType.equals("(") //$NON-NLS-1$
							&& compareVersions(currentVersion,
									lowerVersion) <= 0) {
						isIntervalValid = false;
						continue;
					}

					if (lowerType.equals("[") //$NON-NLS-1$
							&& compareVersions(currentVersion,
									lowerVersion) < 0) {
						isIntervalValid = false;
						continue;
					}
				}
				if (!upperVersion.equals("-")) { //$NON-NLS-1$
					if (upperType.equals(")") //$NON-NLS-1$
							&& compareVersions(currentVersion,
									upperVersion) >= 0) {
						isIntervalValid = false;
						continue;
					}

					if (upperType.equals("]") //$NON-NLS-1$
							&& compareVersions(currentVersion,
									upperVersion) > 0) {
						isIntervalValid = false;
						continue;
					}
				}
			}
			if (isIntervalValid) {
				isValid = true;
				break;
			}
		}
		return isValid;
	}

	public static int compareVersions(String v1, String v2) {
		String[] splited1 = v1.split("\\."); //$NON-NLS-1$
		String[] splited2 = v2.split("\\."); //$NON-NLS-1$

		int res = splited1.length - splited2.length;

		int min = (splited1.length < splited2.length) ? splited1.length
				: splited2.length;

		for (int i = 0; i < min; i++) {
			if (Integer.parseInt(splited1[i]) > Integer.parseInt(splited2[i]))
				return 1;
			if (Integer.parseInt(splited1[i]) < Integer.parseInt(splited2[i]))
				return -1;
		}
		return res;
	}

	public static List<TclArgument> parseCommandArguments(int offset,
			String content, List<Integer> blockArguments) {
		List<TclArgument> results = new ArrayList<>();
		try {
			AstFactory factory = AstFactory.eINSTANCE;
			SimpleTclParser parser = new SimpleTclParser();
			parser.setSkipComments(false);
			TclScript script;
			try {
				script = parser.parse(content);
			} catch (TclParseException e) {
				e.printStackTrace();
				return results;
			}
			List<org.eclipse.dltk.tcl.internal.parser.raw.TclCommand> commands = script
					.getCommands();
			for (int i = 0; i < commands.size(); i++) {
				org.eclipse.dltk.tcl.internal.parser.raw.TclCommand command = commands
						.get(i);
				for (final TclWord word : command.getWords()) {

					final TclArgument exp;
					Object o = word.getContents().get(0);
					if (o instanceof QuotesSubstitution) {
						QuotesSubstitution qs = (QuotesSubstitution) o;

						StringArgument literal = factory.createStringArgument();
						literal.setStart(offset + qs.getStart());
						literal.setEnd(offset + qs.getEnd() + 1);
						final String value = content.substring(word.getStart(),
								word.getEnd() + 1);
						literal.setValue(value);
						literal.setRawValue(value);
						exp = literal;
					} else if (o instanceof BracesSubstitution) {
						BracesSubstitution bs = (BracesSubstitution) o;

						StringArgument block = factory.createStringArgument();
						block.setStart(offset + bs.getStart());
						block.setEnd(offset + bs.getEnd() + 1);
						final String value = content.substring(word.getStart(),
								word.getEnd() + 1);
						block.setValue(value);
						block.setRawValue(value);
						exp = block;
					} else if (o instanceof CommandSubstitution
							&& word.getContents().size() == 1) {
						CommandSubstitution bs = (CommandSubstitution) o;

						StringArgument bl = factory.createStringArgument();
						bl.setStart(offset + bs.getStart());
						bl.setEnd(offset + bs.getEnd() + 1);
						final String value = content.substring(word.getStart(),
								word.getEnd() + 1);
						bl.setValue(value);
						bl.setRawValue(value);
						if (blockArguments != null) {
							blockArguments.add(results.size());
						}
						exp = bl;
					} else {
						StringArgument reference = factory
								.createStringArgument();
						reference.setStart(offset + word.getStart());
						reference.setEnd(offset + word.getEnd() + 1);
						final String value = content.substring(word.getStart(),
								word.getEnd() + 1);
						reference.setValue(value);
						reference.setRawValue(value);
						exp = reference;
					}
					results.add(exp);
				}
			}
			return results;
		} catch (StringIndexOutOfBoundsException bounds) {
			return results;
		}
	}

	public static <T> void traverse(List<T> nodes, TclVisitor visitor) {
		for (int i = 0; i < nodes.size(); i++) {
			Node nde = (Node) nodes.get(i);
			if (nde instanceof Script) {
				Script script = (Script) nde;
				if (visitor.visit(script)) {
					traverse(script.getCommands(), visitor);
					visitor.endVisit(script);
				}
			} else if (nde instanceof Substitution) {
				Substitution substitution = (Substitution) nde;
				if (visitor.visit(substitution)) {
					traverse(substitution.getCommands(), visitor);
					visitor.endVisit(substitution);
				}
			} else if (nde instanceof TclCommand) {
				TclCommand command = (TclCommand) nde;
				if (visitor.visit(command)) {
					traverse(Collections.singletonList(command.getName()),
							visitor);
					traverse(command.getArguments(), visitor);
					visitor.endVisit(command);
				}
			} else if (nde instanceof StringArgument) {
				StringArgument argument = (StringArgument) nde;
				if (visitor.visit(argument)) {
					visitor.endVisit(argument);
				}
			} else if (nde instanceof TclArgumentList) {
				TclArgumentList list = (TclArgumentList) nde;
				if (visitor.visit(list)) {
					traverse(list.getArguments(), visitor);
					visitor.endVisit(list);
				}
			} else if (nde instanceof ComplexString) {
				ComplexString list = (ComplexString) nde;
				if (visitor.visit(list)) {
					traverse(list.getArguments(), visitor);
					visitor.endVisit(list);
				}
			} else if (nde instanceof VariableReference) {
				VariableReference list = (VariableReference) nde;
				if (visitor.visit(list)) {
					TclArgument index = list.getIndex();
					if (index != null) {
						traverse(Collections.singletonList(index), visitor);
					}
					visitor.endVisit(list);
				}
			}
		}
	}

	public static String getSynopsis(Command command) {
		if (command == null)
			return null;
		List<StringBuilder> list = new ArrayList<>();
		StringBuilder synopsis = new StringBuilder();
		String name = command.getName();
		if (name != null && name.length() != 0) {
			list.add(new StringBuilder(name));
		} else {
			// TODO error : bad definition
		}
		for (int i = 0; i < command.getArguments().size(); i++) {
			Argument arg = command.getArguments().get(i);
			list = concatSynopsises(list, getSynopsisArgInfo(arg, i));
		}
		boolean first = true;
		for (StringBuilder str : list) {
			if (!first)
				synopsis.append("\n"); //$NON-NLS-1$
			else
				first = false;
			synopsis.append(str.toString());
		}
		return synopsis.toString();
	}

	static final int SUBCOMMAND = 0;
	static final int OPTIONS = 1;
	static final int MODE = 2;
	static final int REGULAR = 3;

	private static List<StringBuilder> getSynopsisArgInfo(Argument arg,
			int pos) {
		List<StringBuilder> list = new ArrayList<>();
		if (arg instanceof Constant) {
			list.add(new StringBuilder(((Constant) arg).getName()));
		} else if (arg instanceof TypedArgument) {
			list.add(new StringBuilder(((TypedArgument) arg).getName()));
		} else if (arg instanceof Group) {
			String constant = ((Group) arg).getConstant();
			if (constant != null && constant.length() != 0)
				list.add(new StringBuilder(constant));
			for (Argument sub : ((Group) arg).getArguments()) {
				list = concatSynopsises(list, getSynopsisArgInfo(sub, pos + 1));
			}
		} else if (arg instanceof ComplexArgument) {
			for (Argument sub : ((ComplexArgument) arg).getArguments()) {
				list = concatSynopsises(list, getSynopsisArgInfo(sub, pos + 1));
			}
			for (StringBuilder sub : list) {
				sub.insert(0, "{"); //$NON-NLS-1$
				sub.append("}"); //$NON-NLS-1$
			}
		} else if (arg instanceof Switch) {
			int type = REGULAR;
			String constant = null;
			if (((Switch) arg).getGroups() != null) {
				constant = ((Switch) arg).getGroups().get(0).getConstant();
				if (constant != null && constant.length() != 0) {
					if (constant.startsWith("-")) { //$NON-NLS-1$
						type = OPTIONS;
					} else {
						if (pos == 0)
							type = SUBCOMMAND;
						else
							type = MODE;
					}
				}
			} else {
				// TODO bad definition
			}
			switch (type) {
			case REGULAR:
				if (arg.getLowerBound() == 0) {
					list.add(new StringBuilder());
				}
			case SUBCOMMAND:
				for (Group group : ((Switch) arg).getGroups()) {
					list.addAll(getSynopsisArgInfo(group, pos + 1));
				}
				return list;
			case OPTIONS:
			case MODE:
				boolean first = true;
				StringBuilder options = new StringBuilder();
				options.append("<"); //$NON-NLS-1$
				for (Group group : ((Switch) arg).getGroups()) {
					for (StringBuilder str : getSynopsisArgInfo(group,
							pos + 1)) {
						if (!first)
							options.append("|"); //$NON-NLS-1$
						else
							first = false;
						options.append(str);
					}
				}
				options.append(">"); //$NON-NLS-1$
				list.add(options);
			}
		}
		for (StringBuilder res : list) {
			if (arg.getUpperBound() == 0) {
				// TODO error : bad definition
			} else if (arg.getUpperBound() == -1) {
				if (arg.getLowerBound() == 0) {
					res.append(" ..."); //$NON-NLS-1$
					res.insert(0, "?"); //$NON-NLS-1$
					res.append("?"); //$NON-NLS-1$
				} else {
					String value = res.toString();
					for (int i = 0; i < arg.getUpperBound() - 1; i++)
						res.append(" ").append(value); //$NON-NLS-1$
					res.append(" ?").append(value).append(" ...?"); //$NON-NLS-1$ //$NON-NLS-2$
				}
			} else if (arg.getUpperBound() == 1) {
				if (arg.getLowerBound() == 0) {
					res.insert(0, "?"); //$NON-NLS-1$
					res.append("?"); //$NON-NLS-1$
				}
			} else {
				String value = res.toString();
				for (int i = 0; i < arg.getUpperBound(); i++)
					res.append(" ").append(value); //$NON-NLS-1$
			}
		}
		return list;
	}

	private static List<StringBuilder> concatSynopsises(
			List<StringBuilder> prefixes, List<StringBuilder> ss) {
		List<StringBuilder> newList = new ArrayList<>();
		if (prefixes.size() == 0)
			return ss;
		for (StringBuilder prefix : prefixes) {
			for (StringBuilder s : ss) {
				newList.add(new StringBuilder(prefix.length() + s.length() + 1)
						.append(prefix).append(" ").append(s)); //$NON-NLS-1$
			}
		}
		return newList;
	}

	/*
	 * static DefinitionsFactory factory = DefinitionsFactory.eINSTANCE; public
	 * Command getDefinition(String synopsis) { Command command =
	 * factory.createCommand(); StringTokenizer tokenizer = new
	 * StringTokenizer(synopsis); boolean first = true; while
	 * (tokenizer.hasMoreTokens()){ String token = tokenizer.nextToken(" "); if
	 * (first) { command.setName(token); first = false; } else { Argument arg =
	 * null; if (token.startsWith("?")){ if (token.endsWith("?")){ arg =
	 * factory.createTypedArgument(); arg.setLowerBound(0);
	 * ((TypedArgument)arg).setName(token.substring(1,token.length()-1)); } else
	 * { arg = factory.createGroup(); boolean isOver = false; while
	 * (tokenizer.hasMoreTokens() && !isOver){ String subToken =
	 * tokenizer.nextToken(" "); if (subToken.endsWith("?")){ subToken =
	 * subToken.substring(0,subToken.length()-1); if (subToken.equals("...")){
	 * arg.setUpperBound(-1); break; } } } } } command.getArguments().add(arg);
	 * }
	 *
	 * } return command; }
	 */
	/**
	 * Return empty list if not matched.
	 *
	 * @param command
	 * @param name
	 * @return
	 */
	public static TclArgument[] getTypedMatch(TclCommand command, String name) {
		EList<ArgumentMatch> matches = command.getMatches();
		List<TclArgument> results = new ArrayList<>();
		for (ArgumentMatch argumentMatch : matches) {
			Argument definition = argumentMatch.getDefinition();
			if (definition instanceof TypedArgument) {
				TypedArgument arg = (TypedArgument) definition;
				if (name.equals(arg.getName())) {
					results.addAll(argumentMatch.getArguments());
				}
			}
		}
		return results.toArray(new TclArgument[results.size()]);
	}
}
