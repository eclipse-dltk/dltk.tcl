/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.core.codeassist.completion;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.expressions.StringLiteral;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.codeassist.complete.CompletionNodeFound;
import org.eclipse.dltk.tcl.ast.TclStatement;
import org.eclipse.dltk.tcl.ast.expressions.TclBlockExpression;
import org.eclipse.dltk.tcl.ast.expressions.TclExecuteExpression;
import org.eclipse.dltk.tcl.core.TclKeywordsManager;
import org.eclipse.dltk.tcl.core.TclParseUtil;
import org.eclipse.dltk.tcl.core.ast.TclAdvancedExecuteExpression;
import org.eclipse.dltk.tcl.core.ast.TclPackageDeclaration;
import org.eclipse.dltk.tcl.core.extensions.ICompletionExtension;
import org.eclipse.dltk.tcl.internal.core.codeassist.TclASTUtil;
import org.eclipse.dltk.tcl.internal.core.codeassist.TclAssistParser;
import org.eclipse.dltk.tcl.internal.parser.OldTclParserUtils;

public class TclCompletionParser extends TclAssistParser {
	private static class TclEmptyCompleteStatement extends TclStatement {
		public TclEmptyCompleteStatement(List expressions) {
			super(expressions);
		}
	}

	private ICompletionExtension[] extensions;

	public TclCompletionParser(ICompletionExtension[] extensions) {
		this.extensions = extensions;
	}

	/**
	 * Called then element could not be found.
	 */
	@Override
	public void handleNotInElement(ASTNode node, int position) {
		if (node != null && node.sourceStart() <= position
				&& position <= node.sourceEnd()) {
			// this is empty module case
			if (node instanceof ModuleDeclaration) {
				ModuleDeclaration unit = (ModuleDeclaration) node;
				List exprs = new ArrayList();
				exprs.add(new SimpleReference(position, position, ""));
				TclEmptyCompleteStatement statement = new TclEmptyCompleteStatement(
						exprs);
				unit.addStatement(statement);
				this.parseBlockStatements(statement, unit, position);
			} else if (node instanceof MethodDeclaration) {
				// empty keyword like completion.
				MethodDeclaration method = (MethodDeclaration) node;
				List exprs = new ArrayList();
				exprs.add(new SimpleReference(position, position, ""));
				TclEmptyCompleteStatement statement = new TclEmptyCompleteStatement(
						exprs);
				method.getStatements().add(statement);
				this.parseBlockStatements(statement, method, position);
			} else if (node instanceof TypeDeclaration) {
				// empty keyword like completion.
				TypeDeclaration type = (TypeDeclaration) node;
				List exprs = new ArrayList();
				exprs.add(new SimpleReference(position, position, ""));
				TclEmptyCompleteStatement statement = new TclEmptyCompleteStatement(
						exprs);
				type.getStatements().add(statement);
				// this.assistNodeParent = node;
				this.parseBlockStatements(statement, type, position);
			} else if (node instanceof TclExecuteExpression) {
				// empty keyword like completion.
				List exprs = new ArrayList();
				exprs.add(new SimpleReference(position, position, ""));
				TclEmptyCompleteStatement statement = new TclEmptyCompleteStatement(
						exprs);
				this.parseBlockStatements(statement, node, position);
			}
		}
	}

	@Override
	public void parseBlockStatements(ASTNode node, ASTNode inNode,
			int position) {
		if (node instanceof TclStatement) {
			TclStatement statement = (TclStatement) node;
			List<ASTNode> expressions = statement.getExpressions();
			int len = expressions.size();
			boolean first = false;
			String completionToken = null;
			ASTNode completionNode = null;
			for (int i = 0; i < len; ++i) {
				ASTNode n = expressions.get(i);
				if (n.sourceStart() <= position && n.sourceEnd() >= position
						|| (node instanceof TclEmptyCompleteStatement)) {
					if (i == 0) {
						first = true;
					}
					completionNode = n;
				}
			}
			if (completionNode == null) {
				// TODO: Add inner completion here.
				if (len > 0) {
					// ASTNode firstNode = (ASTNode) expressions.get(0);
					// if (position > firstNode.sourceEnd()) {
					// // This could be variable completion.
					// boolean provideDollar =
					// !checkVariableWithoutDollarCompletion(
					// statement, position);
					// this.assistNodeParent = inNode;
					// SimpleReference ref = new SimpleReference(position,
					// position, "");
					// ASTNode nde = new CompletionOnVariable("", ref, node,
					// inNode, true, provideDollar);
					// throw new CompletionNodeFound(nde, null);
					// }
					String[] keywords = checkKeywords(completionToken, MODULE);
					ASTNode nde = new CompletionOnKeywordArgumentOrFunctionArgument(
							"", (TclStatement) node, keywords, position);
					this.assistNodeParent = inNode;
					throw new CompletionNodeFound(nde,
							null/*
								 * ((TypeDeclaration) inNode).scope
								 */);

				} else {
					completionToken = "";
				}
			} else if (completionNode instanceof SimpleReference) {
				int maxLen = position - completionNode.sourceStart();
				completionToken = ((SimpleReference) completionNode).getName();
				// We need to cut some sumbols if node is begger then position.
				if (completionToken.length() > maxLen && maxLen > 0) {
					completionToken = completionToken.substring(0, maxLen);
				}
			}
			if (completionNode instanceof StringLiteral) {
				int maxLen = position - completionNode.sourceStart();
				int pos = maxLen;
				SimpleReference tok = OldTclParserUtils
						.extractVariableFromString(
								(StringLiteral) completionNode, pos);
				if (tok != null) {
					this.assistNodeParent = inNode;
					ASTNode nde = new CompletionOnVariable(tok.getName(), tok,
							node, inNode, false);
					throw new CompletionNodeFound(nde, null);
				} else {
					this.assistNodeParent = inNode;
					SimpleReference ref = new SimpleReference(position,
							position, "");
					ASTNode nde = new CompletionOnVariable("", ref, node,
							inNode, true);
					throw new CompletionNodeFound(nde, null);
				}
			}
			if (completionNode instanceof TclExecuteExpression) {
				TclExecuteExpression expr = (TclExecuteExpression) completionNode;
				List exprs = expr.parseExpression();
				for (int i = 0; i < exprs.size(); ++i) {
					ASTNode n = (ASTNode) exprs.get(i);
					if (n.sourceStart() <= position
							&& n.sourceEnd() >= position) {
						parseBlockStatements(n, expr, position);
					}
				}
				handleNotInElement(expr, position);
			}
			if (completionNode instanceof TclAdvancedExecuteExpression) {
				TclAdvancedExecuteExpression expr = (TclAdvancedExecuteExpression) completionNode;
				List exprs = expr.getStatements();
				for (int i = 0; i < exprs.size(); ++i) {
					ASTNode n = (ASTNode) exprs.get(i);
					if (n.sourceStart() <= position
							&& n.sourceEnd() >= position) {
						parseBlockStatements(n, expr, position);
					}
				}
			}
			if (completionNode instanceof TclBlockExpression) {
				TclBlockExpression block = (TclBlockExpression) completionNode;

				List s = block.parseBlockSimple();
				if (s != null) {
					int slen = s.size();
					for (int u = 0; u < slen; ++u) {
						ASTNode n = (ASTNode) s.get(u);
						n.setStart(n.sourceStart() - block.sourceStart());
						n.setEnd(n.sourceEnd() - block.sourceStart());
						TclASTUtil.extendStatement(n, block.getBlock());
						n.setStart(n.sourceStart() + block.sourceStart());
						n.setEnd(n.sourceEnd() + block.sourceStart());
						if (n.sourceStart() <= position
								&& n.sourceEnd() >= position) {
							parseBlockStatements(n, inNode, position);
						}
					}
				}
				handleNotInElement(inNode, position);
			}

			if (completionToken != null && completionToken.startsWith("$")) {
				// Argument name completion...
				this.assistNodeParent = inNode;
				ASTNode nde = new CompletionOnVariable(completionToken,
						completionNode, node, inNode, false);
				throw new CompletionNodeFound(nde, null);
			} else {
				// This is keyword or function completion.
				if (inNode instanceof ModuleDeclaration
						&& completionNode != null && first) {
					String[] keywords = checkKeywords(completionToken, MODULE);
					ASTNode nde = new CompletionOnKeywordOrFunction(
							completionToken, completionNode, node, keywords);
					this.assistNodeParent = inNode;
					throw new CompletionNodeFound(nde,
							((ModuleDeclaration) inNode).scope);
				} else if (inNode instanceof MethodDeclaration
						&& completionNode != null && first) {
					String[] keywords = checkKeywords(completionToken,
							FUNCTION);
					ASTNode nde = new CompletionOnKeywordOrFunction(
							completionToken, completionNode,
							TclParseUtil.getScopeParent(getModule(), node),
							keywords);
					this.assistNodeParent = inNode;
					throw new CompletionNodeFound(nde,
							((MethodDeclaration) inNode).scope);
				} else if (inNode instanceof TypeDeclaration
						&& completionNode != null && first) {
					String[] keywords = checkKeywords(completionToken,
							NAMESPACE);
					ASTNode nde = new CompletionOnKeywordOrFunction(
							completionToken, completionNode, node, keywords);
					this.assistNodeParent = inNode;
					throw new CompletionNodeFound(nde,
							null/*
								 * ((TypeDeclaration) inNode).scope
								 */);
				} else if (inNode instanceof TclExecuteExpression
						&& completionNode != null && first) {
					String[] keywords = checkKeywords(completionToken,
							EXEC_EXPRESSION);
					ASTNode nde = new CompletionOnKeywordOrFunction(
							completionToken, completionNode, node, keywords);
					this.assistNodeParent = inNode;
					throw new CompletionNodeFound(nde,
							null/*
								 * ((TypeDeclaration) inNode).scope
								 */);
				} else {
					if (completionNode != null) {
						String[] keywords = checkKeywords(completionToken,
								MODULE);

						ASTNode nde = new CompletionOnKeywordArgumentOrFunctionArgument(
								completionToken, completionNode,
								(TclStatement) node, keywords);
						this.assistNodeParent = inNode;
						throw new CompletionNodeFound(nde,
								null/*
									 * ((TypeDeclaration ) inNode).scope
									 */);
					} else {
						String[] keywords = checkKeywords(completionToken,
								MODULE);
						if (completionToken == null) {
							completionToken = "";
						}
						ASTNode nde = new CompletionOnKeywordArgumentOrFunctionArgument(
								completionToken, (TclStatement) node, keywords,
								position);
						this.assistNodeParent = inNode;
						throw new CompletionNodeFound(nde,
								null/*
									 * ((TypeDeclaration ) inNode).scope
									 */);
					}
				}
			}
			// if (checkVariableWithoutDollarCompletion(statement, position)
			// && completionToken != null) {
			// this.assistNodeParent = inNode;
			// SimpleReference ref = new SimpleReference(completionNode
			// .sourceStart(), completionNode.sourceEnd(),
			// completionToken);
			// ASTNode nde = new CompletionOnVariable(completionToken, ref,
			// node, inNode, true);
			// throw new CompletionNodeFound(nde, null);
			// }

		} else if (node instanceof MethodDeclaration) {
			MethodDeclaration method = (MethodDeclaration) node;
			List statements = method.getStatements();
			boolean inStatement = false;
			if (statements != null) {
				int length = statements.size();
				for (int i = 0; i < length; i++) {
					ASTNode nde = (ASTNode) statements.get(i);
					if (nde.sourceStart() <= position
							&& nde.sourceEnd() >= position) {
						inStatement = true;
						parseBlockStatements(nde, method, position);
					}
				}
			}
			if (!inStatement) {
				this.handleNotInElement(method, position);
			}
		} else {
			visitElements(node, position);
		}
	}

	public class CompletionVisitor extends ASTVisitor {
		protected int position;
		protected ModuleDeclaration module;

		public CompletionVisitor(int position, ModuleDeclaration module) {
			this.position = position;
			this.module = module;
		}

		@Override
		public boolean visit(Statement s) throws Exception {
			if (s.sourceStart() <= position && s.sourceEnd() >= position) {
				for (int i = 0; i < extensions.length; i++) {
					extensions[i].visit(s, TclCompletionParser.this, position);
				}
				if (s instanceof TclStatement) {
					ASTNode inNode = TclParseUtil.getScopeParent(module, s);
					TclCompletionParser.this.parseBlockStatements(s, inNode,
							position);
				}
				if (s instanceof TclPackageDeclaration) {
					TclPackageDeclaration decl = (TclPackageDeclaration) s;
					if (decl.getNameStart() <= position
							&& position <= decl.getNameEnd()) {
						ASTNode inNode = TclParseUtil.getScopeParent(module, s);
						assistNodeParent = inNode;
						throw new CompletionNodeFound(decl,
								null/*
									 * ((TypeDeclaration ) inNode).scope
									 */);
					}
				}
			}
			return super.visit(s);
		}

		@Override
		public boolean visit(Expression s) throws Exception {
			if (s.sourceStart() <= position && s.sourceEnd() >= position) {
				if (s instanceof TclBlockExpression) {
					ASTNode inNode = TclParseUtil.getScopeParent(module, s);
					TclBlockExpression block = (TclBlockExpression) s;
					List ss = block.parseBlockSimple();
					if (ss != null) {
						int slen = ss.size();
						for (int u = 0; u < slen; ++u) {
							ASTNode n = (ASTNode) ss.get(u);
							if (n != null && n.sourceStart() <= position
									&& n.sourceEnd() >= position) {
								parseBlockStatements(n, inNode, position);
							}
						}
					}
				}
				for (int i = 0; i < extensions.length; i++) {
					extensions[i].visit(s, TclCompletionParser.this, position);
				}
			}
			return super.visit(s);
		}

		@Override
		public boolean endvisit(Expression s) throws Exception {
			if (s instanceof Block && s.sourceStart() <= position
					&& s.sourceEnd() >= position) {
				// We are in block, and no in node completion are done.
				String[] keywords = checkKeywords("", MODULE);
				ASTNode inNode = TclParseUtil.getScopeParent(module, s);
				ASTNode nde = new CompletionOnKeywordOrFunction("", inNode, s,
						keywords);
				assistNodeParent = inNode;
				throw new CompletionNodeFound(nde, null);
			}
			return super.endvisit(s);
		}
	};

	protected CompletionVisitor createCompletionVisitor(int position) {
		return new CompletionVisitor(position, this.getModule());
	}

	private void visitElements(ASTNode node, int position) {
		if (!(node instanceof TclStatement)) {
			CompletionVisitor visitor = createCompletionVisitor(position);
			try {
				node.traverse(visitor);
			} catch (CompletionNodeFound e) {
				throw e;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private boolean checkVariableWithoutDollarCompletion(TclStatement statement,
			int position) {
		// TODO: Add more compecated check.
		Expression e = statement.getAt(0);
		if (e instanceof SimpleReference) {
			SimpleReference ref = (SimpleReference) e;
			String name = ref.getName();
			if (name.equals("set")) {
				return true;
			}
		}
		return false;
	}

	public String[] checkKeywords(String completionToken, int type) {
		String[] keywords = TclKeywordsManager.getKeywords(type);
		// TODO: Possible require cases.
		if (type == MODULE || type == FUNCTION || type == NAMESPACE
				|| type == EXEC_EXPRESSION) {
			// Suppose we can handle all keywords.
			String[] kw = new String[keywords.length];
			for (int i = 0; i < keywords.length; ++i) {
				kw[i] = keywords[i];
			}
			return kw;
		}
		return null;
	}

	public void setAssistNodeParent(ASTNode prevParent) {
		this.assistNodeParent = prevParent;
	}
}
