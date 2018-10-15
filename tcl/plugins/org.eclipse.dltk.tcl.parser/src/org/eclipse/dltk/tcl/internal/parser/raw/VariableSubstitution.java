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
package org.eclipse.dltk.tcl.internal.parser.raw;

public class VariableSubstitution extends TclElement implements ISubstitution {

	public static final int VAR_SIMPLE = 0;
	public static final int VAR_ARRAY = 1;
	public static final int VAR_NAME = 2;

	private String name;
	private TclWord index;
	private int kind;

	public VariableSubstitution() {
	}

	public TclWord getIndex() {
		return index;
	}

	public String getName() {
		return name;
	}

	public static boolean iAm(ICodeScanner scanner) {
		int c = scanner.read();
		if (c == ICodeScanner.EOF)
			return false;
		if (c != '$') {
			scanner.unread();
			return false;
		}
		int c2 = scanner.read();
		scanner.unread();
		if (c2 != ICodeScanner.EOF)
			scanner.unread();
		if (!TclTextUtils.isIdentifier(c2) && c2 != '(' && c2 != '{') {
			return false;
		}
		return true;
	}

	@Override
	public boolean readMe(ICodeScanner input, SimpleTclParser parser)
			throws TclParseException {
		if (!iAm(input))
			return false;
		setStart(input.getPosition());
		this.name = "";
		this.kind = VAR_SIMPLE;
		input.read();
		int c = input.read();
		if (c == '{') {
			this.kind = VAR_NAME;
			while (true) {
				c = input.read();
				if (c == ICodeScanner.EOF) {
					boolean cont = parser.handleError(new ErrorDescription(
							Messages.VariableSubstitution_BracesVariableName,
							getStart(), input.getPosition(),
							ErrorDescription.ERROR));
					if (!cont) {
						throw new TclParseException(
								Messages.VariableSubstitution_BracesVariableName,
								input.getPosition());
					} else {
						break;
					}
				}
				if (c == '}')
					break;
				this.name += (char) c;
			}
		} else {
			do {
				if (c == ICodeScanner.EOF) {
					break; // stop!
				}
				if (TclTextUtils.isIdentifier(c)) {
					this.name += (char) c;
					c = input.read();
				} else {
					if (c == '(') { // read index part
						this.kind = VAR_ARRAY;
						TclWord cvb = new TclWord();
						cvb.setStart(input.getPosition());
						int ch;
						while (true) {
							ISubstitution s = parser.getCVB(input);

							if (s != null) {
								s.readMe(input, parser);
								cvb.add(s);
								if (s instanceof TclElement) {
									cvb.setEnd(((TclElement) s).getEnd());
								}
							} else {
								ch = input.read();
								if (ch == ICodeScanner.EOF) {
									boolean cont = parser
											.handleError(new ErrorDescription(
													Messages.VariableSubstitution_VariableIndex,
													getStart(),
													input.getPosition(),
													ErrorDescription.ERROR));
									if (!cont)
										throw new TclParseException(
												Messages.VariableSubstitution_VariableIndex,
												getStart());
									else
										break; // stop!
								}

								if (ch == ')') {
									if (input.isEOF()) {
										cvb.setEnd(input.getPosition() - 1);
									} else {
										cvb.setEnd(input.getPosition() - 2);
									}
									break;
								} else {
									cvb.add((char) ch);
									cvb.setEnd(input.getPosition());
								}
							}
						}
						this.index = cvb;
						break;
					} else {
						input.unread();
						break;
					}
				}
			} while (true);
		}
		if (!input.isEOF()) {
			setEnd(input.getPosition() - 1);
		} else
			setEnd(input.getPosition());
		return true;
	}

	public int getKind() {
		return kind;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName()).append("["); //$NON-NLS-1$
		sb.append("$"); //$NON-NLS-1$
		if (kind == VAR_NAME) {
			sb.append("{"); //$NON-NLS-1$
		}
		sb.append(name);
		if (kind == VAR_NAME) {
			sb.append("}"); //$NON-NLS-1$
		}
		if (index != null) {
			sb.append("("); //$NON-NLS-1$
			sb.append(index);
			sb.append(")"); //$NON-NLS-1$
		}
		sb.append("]"); //$NON-NLS-1$
		return sb.toString();
	}
}
