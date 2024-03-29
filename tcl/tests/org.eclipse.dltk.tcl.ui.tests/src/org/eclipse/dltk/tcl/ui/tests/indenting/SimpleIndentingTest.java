/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 
 *******************************************************************************/
package org.eclipse.dltk.tcl.ui.tests.indenting;

import java.io.IOException;

import junit.framework.TestCase;

import org.eclipse.dltk.tcl.internal.ui.text.TclAutoEditStrategy;
import org.eclipse.dltk.tcl.internal.ui.text.TclPartitionScanner;
import org.eclipse.dltk.tcl.ui.TclPreferenceConstants;
import org.eclipse.dltk.tcl.ui.tests.TclUITestsPlugin;
import org.eclipse.dltk.tcl.ui.text.TclPartitions;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.DocumentRewriteSession;
import org.eclipse.jface.text.DocumentRewriteSessionType;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.TextUtilities;
import org.eclipse.jface.text.rules.FastPartitioner;

@SuppressWarnings("nls")
public class SimpleIndentingTest extends TestCase {

	private final IPreferenceStore fStore = TclUITestsPlugin.getDefault()
			.getPreferenceStore();

	private TclAutoEditStrategy strategy;

	/**
	 * Installs a partitioner with <code>document</code>.
	 * 
	 * @param document
	 *            the document
	 */
	private static void installStuff(Document document) {
		String[] types = new String[] { TclPartitions.TCL_STRING,
				TclPartitions.TCL_COMMENT, IDocument.DEFAULT_CONTENT_TYPE };
		FastPartitioner partitioner = new FastPartitioner(
				new TclPartitionScanner(), types);
		partitioner.connect(document);
		document.setDocumentPartitioner(TclPartitions.TCL_PARTITIONING,
				partitioner);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		TclPreferenceConstants.initializeDefaultValues(fStore);
		strategy = new TclAutoEditStrategy(fStore,
				TclPartitions.TCL_PARTITIONING);
	}

	public void testParts() throws Exception {
		String text = "#comment one\n" + "	#another comment\n" + "\n"
				+ "proc foo{} {\n" + "	set a \"test\"\n" + "}\n"
				+ "#another comment";
		Document temp = new Document(text);
		installStuff(temp);

		String fPartitioning = TclPartitions.TCL_PARTITIONING;

		ITypedRegion region = TextUtilities.getPartition(temp, fPartitioning,
				1, true);
		assertEquals(TclPartitions.TCL_COMMENT, region.getType());
		region = TextUtilities.getPartition(temp, fPartitioning, 0, true);
		assertEquals(IDocument.DEFAULT_CONTENT_TYPE, region.getType());
		region = TextUtilities.getPartition(temp, fPartitioning, temp
				.getLineOffset(1) + 10, true);
		assertEquals(TclPartitions.TCL_COMMENT, region.getType());
		region = TextUtilities.getPartition(temp, fPartitioning, temp
				.getLineOffset(2), true);
		assertEquals(IDocument.DEFAULT_CONTENT_TYPE, region.getType());
		region = TextUtilities.getPartition(temp, fPartitioning, temp
				.getLineOffset(4) + 2, true);
		assertEquals(IDocument.DEFAULT_CONTENT_TYPE, region.getType());
		region = TextUtilities.getPartition(temp, fPartitioning, temp
				.getLineOffset(4) + 10, true);
		assertEquals(TclPartitions.TCL_STRING, region.getType());
	}

	public void testParts2() throws Exception {
		String text = "\"(\"\n" + "dflt";
		Document temp = new Document(text);

		installStuff(temp);

		String fPartitioning = TclPartitions.TCL_PARTITIONING;

		ITypedRegion region = TextUtilities.getPartition(temp, fPartitioning,
				1, true);
		assertEquals(TclPartitions.TCL_STRING, region.getType());
		region = TextUtilities.getPartition(temp, fPartitioning, 0, true);
		assertEquals(IDocument.DEFAULT_CONTENT_TYPE, region.getType());
		region = TextUtilities.getPartition(temp, fPartitioning, 2, true);
		assertEquals(TclPartitions.TCL_STRING, region.getType());
		region = TextUtilities.getPartition(temp, fPartitioning, 5, true);
		assertEquals(IDocument.DEFAULT_CONTENT_TYPE, region.getType());
	}

	public void testParts3() throws Exception {
		String text = "#asdfasdfasdf \\\n" + "asdfasdfasdf\n"
				+ "asdf  ;  #sdfasdf\n" + "    #asdfasdf\n"
				+ "asdfasd #adfsad\n" + "#sdfasdf\n";
		Document temp = new Document(text);

		installStuff(temp);

		String fPartitioning = TclPartitions.TCL_PARTITIONING;

		ITypedRegion region = TextUtilities.getPartition(temp, fPartitioning,
				1, true);
		assertEquals(TclPartitions.TCL_COMMENT, region.getType());

		region = TextUtilities.getPartition(temp, fPartitioning, temp
				.getLineOffset(1) + 2, true);
		assertEquals(TclPartitions.TCL_COMMENT, region.getType());

		region = TextUtilities.getPartition(temp, fPartitioning, temp
				.getLineOffset(2) + 2, true);
		assertEquals(IDocument.DEFAULT_CONTENT_TYPE, region.getType());

		region = TextUtilities.getPartition(temp, fPartitioning, temp
				.getLineOffset(2) + 13, true);
		assertEquals(TclPartitions.TCL_COMMENT, region.getType());

		region = TextUtilities.getPartition(temp, fPartitioning, temp
				.getLineOffset(3) + 10, true);
		assertEquals(TclPartitions.TCL_COMMENT, region.getType());

		region = TextUtilities.getPartition(temp, fPartitioning, temp
				.getLineOffset(3) + 1, true);
		assertEquals(IDocument.DEFAULT_CONTENT_TYPE, region.getType());

		region = TextUtilities.getPartition(temp, fPartitioning, temp
				.getLineOffset(4) + 2, true);
		assertEquals(IDocument.DEFAULT_CONTENT_TYPE, region.getType());

		region = TextUtilities.getPartition(temp, fPartitioning, temp
				.getLineOffset(4) + 12, true);
		assertEquals(IDocument.DEFAULT_CONTENT_TYPE, region.getType());

		region = TextUtilities.getPartition(temp, fPartitioning, temp
				.getLineOffset(5) + 1, true);
		assertEquals(TclPartitions.TCL_COMMENT, region.getType());
	}

	public void testIndent00() throws Exception {
		String text = "#comment one\n" + "	#another comment\n" + "\n"
				+ "proc foo{} {\n" + "	set a \"test\"\n" + "}\n"
				+ "#another comment";
		Document temp = new Document(text);
		DocumentRewriteSession session = temp
				.startRewriteSession(DocumentRewriteSessionType.STRICTLY_SEQUENTIAL);
		installStuff(temp);

		int offset = temp.getLineOffset(3) + temp.getLineLength(3) - 1;

		DocumentCommand c = new DocCmd();
		c.caretOffset = -1;
		c.shiftsCaret = true;
		c.length = 0;
		c.offset = offset;
		c.text = "\n";

		strategy.customizeDocumentCommand(temp, c);

		// execute
		temp.replace(c.offset, c.length, c.text);
		int newOffset = c.offset + (c.text == null ? 0 : c.text.length());
		if (c.caretOffset != -1)
			newOffset = c.caretOffset;

		temp.stopRewriteSession(session);

		// check document
		String correct = "#comment one\n" + "	#another comment\n" + "\n"
				+ "proc foo{} {\n" + "	\n" + "	set a \"test\"\n" + "}\n"
				+ "#another comment";
		String result = temp.get();
		assertEquals(correct, result);

		// check caret position
		assertEquals(offset + 2, newOffset);
	}

	public void testIndent01() throws Exception {
		String text = "#comment one\n" + "	#another comment\n" + "\n"
				+ "proc foo{} ";
		Document temp = new Document(text);
		DocumentRewriteSession session = temp
				.startRewriteSession(DocumentRewriteSessionType.STRICTLY_SEQUENTIAL);
		installStuff(temp);

		int offset = temp.getLineOffset(3) + temp.getLineLength(3);

		DocumentCommand c = new DocCmd();
		c.caretOffset = -1;
		c.shiftsCaret = true;
		c.length = 0;
		c.offset = offset;
		c.text = "{";

		strategy.customizeDocumentCommand(temp, c);

		// execute
		temp.replace(c.offset, c.length, c.text);
		int newOffset = c.offset + (c.text == null ? 0 : c.text.length());
		if (c.caretOffset != -1)
			newOffset = c.caretOffset;

		temp.stopRewriteSession(session);

		// check document
		String correct = "#comment one\n" + "	#another comment\n" + "\n"
				+ "proc foo{} {}";
		String result = temp.get();
		assertEquals(correct, result);

		// check caret position
		assertEquals(temp.getLineOffset(3) + 12, newOffset);

		// next command
		session = temp
				.startRewriteSession(DocumentRewriteSessionType.STRICTLY_SEQUENTIAL);

		c = new DocCmd();
		c.caretOffset = -1;
		c.shiftsCaret = true;
		c.length = 0;
		c.offset = newOffset;
		c.text = "\n";

		strategy.customizeDocumentCommand(temp, c);

		// execute
		temp.replace(c.offset, c.length, c.text);
		newOffset = c.offset + (c.text == null ? 0 : c.text.length());
		if (c.caretOffset != -1)
			newOffset = c.caretOffset;

		temp.stopRewriteSession(session);

		// check document
		correct = "#comment one\n" + "	#another comment\n" + "\n"
				+ "proc foo{} {\n" + "	\n" + "}";
		result = temp.get();
		assertEquals(correct, result);

		// check caret position
		assertEquals(temp.getLineOffset(4) + 1, newOffset);
	}

	public void testIndent02() throws Exception {
		String text = "#comment (one";
		Document temp = new Document(text);
		DocumentRewriteSession session = temp
				.startRewriteSession(DocumentRewriteSessionType.STRICTLY_SEQUENTIAL);
		installStuff(temp);

		int offset = temp.getLineOffset(0) + temp.getLineLength(0);

		DocumentCommand c = new DocCmd();
		c.caretOffset = -1;
		c.shiftsCaret = true;
		c.length = 0;
		c.offset = offset;
		c.text = "\n";

		strategy.customizeDocumentCommand(temp, c);

		// execute
		temp.replace(c.offset, c.length, c.text);
		int newOffset = c.offset + (c.text == null ? 0 : c.text.length());
		if (c.caretOffset != -1)
			newOffset = c.caretOffset;

		temp.stopRewriteSession(session);

		// check document
		String correct = "#comment (one\n" + "";
		String result = temp.get();
		assertEquals(correct, result);

		// check caret position
		assertEquals(temp.getLineOffset(1), newOffset);
	}

	public void testIndent03() throws Exception {
		String text = "set a = \"asdfasdf(ssdf\"";
		Document temp = new Document(text);
		DocumentRewriteSession session = temp
				.startRewriteSession(DocumentRewriteSessionType.STRICTLY_SEQUENTIAL);
		installStuff(temp);

		int offset = temp.getLineOffset(0) + temp.getLineLength(0);

		DocumentCommand c = new DocCmd();
		c.caretOffset = -1;
		c.shiftsCaret = true;
		c.length = 0;
		c.offset = offset;
		c.text = "\n";

		strategy.customizeDocumentCommand(temp, c);

		// execute
		temp.replace(c.offset, c.length, c.text);
		int newOffset = c.offset + (c.text == null ? 0 : c.text.length());
		if (c.caretOffset != -1)
			newOffset = c.caretOffset;

		temp.stopRewriteSession(session);

		// check document
		String correct = "set a = \"asdfasdf(ssdf\"\n" + "";
		String result = temp.get();
		assertEquals(correct, result);

		// check caret position
		assertEquals(temp.getLineOffset(1), newOffset);
	}

	public void testIndent04() throws Exception {
		String text = "set c {a, b, \n" + "	d, e}";
		final Document temp = new Document(text);
		DocumentRewriteSession session = temp
				.startRewriteSession(DocumentRewriteSessionType.STRICTLY_SEQUENTIAL);
		installStuff(temp);
		DocumentCommand c = new DocCmd();
		c.caretOffset = -1;
		c.shiftsCaret = true;
		c.length = 0;
		c.offset = temp.getLineOffset(1) + temp.getLineLength(1);
		c.text = "\n";

		strategy.customizeDocumentCommand(temp, c);

		// execute
		temp.replace(c.offset, c.length, c.text);
		int newOffset = c.offset + (c.text == null ? 0 : c.text.length());
		if (c.caretOffset != -1)
			newOffset = c.caretOffset;

		temp.stopRewriteSession(session);

		// check document
		String correct = "set c {a, b, \n" + "	d, e}\n" + "";
		String result = temp.get();
		assertEquals(correct, result);

		// check caret position
		assertEquals(temp.getLineOffset(2), newOffset);
	}

	// public void testIndent05_pasting_simple() throws Exception {
	// final String text0 = TclUITestsPlugin.getDefault()
	// .getPluginFileContent("/tcls/expr-old.tcl");
	// Document temp = new Document("proc foo{} {\n\t\n}");
	// DocumentRewriteSession session = temp
	// .startRewriteSession(DocumentRewriteSessionType.STRICTLY_SEQUENTIAL);
	// installStuff(temp);
	//
	// DocumentCommand c = new DocumentCommand() {
	// {
	// this.doit = true;
	// this.caretOffset = -1;
	// this.shiftsCaret = true;
	// this.length = 0;
	// this.offset = "proc foo{} {\n\t".length();
	// this.text = text0;
	// }
	// };
	//
	// fStore.setValue(TclPreferenceConstants.EDITOR_SMART_PASTE_MODE, 1);
	// strategy.customizeDocumentCommand(temp, c);
	//
	// // execute
	// temp.replace(c.offset, c.length, c.text);
	//
	// temp.stopRewriteSession(session);
	//
	// // check document
	// assertEquals('i', temp.getChar(temp.getLineOffset(966) + 1));
	// assertEquals('f', temp.getChar(temp.getLineOffset(666) + 2));
	// assertEquals('S', temp.getChar(temp.getLineOffset(13) + 3));
	// }

	public void testIndent06_pasting_hard() throws Exception {
		final String text0 = TclUITestsPlugin.getDefault()
				.getPluginFileContent("/tcls/interp.tcl");
		Document temp = new Document("");
		DocumentRewriteSession session = temp
				.startRewriteSession(DocumentRewriteSessionType.STRICTLY_SEQUENTIAL);
		installStuff(temp);

		DocumentCommand c = new DocCmd();
		c.caretOffset = -1;
		c.shiftsCaret = true;
		c.length = 0;
		c.offset = 0;
		c.text = text0;

		fStore.setValue(TclPreferenceConstants.EDITOR_SMART_PASTE_MODE,
				TclPreferenceConstants.EDITOR_SMART_PASTE_MODE_FULL);
		strategy.customizeDocumentCommand(temp, c);

		// execute
		temp.replace(c.offset, c.length, c.text);

		temp.stopRewriteSession(session);

		// check document
		assertEquals('#', temp.getChar(0));
		// line numbers are starting at 0
		final IRegion line2944 = temp.getLineInformation(2944);
		assertEquals("\t" + "interp delete $i", temp.get(line2944.getOffset(),
				line2944.getLength()));
		final IRegion line1990 = temp.getLineInformation(1990);
		assertEquals("\t" + "a alias exec foo" + "\t\t"
				+ ";# Relies on exec being a string command!", temp.get(
				line1990.getOffset(), line1990.getLength()));
	}

	public void testIndent06_jumping() throws Exception {
		String text = "proc foo{} {\n" + "	proc doo {} {\n" + "		";
		final Document temp = new Document(text);
		DocumentRewriteSession session = temp
				.startRewriteSession(DocumentRewriteSessionType.STRICTLY_SEQUENTIAL);
		installStuff(temp);

		DocumentCommand c = new DocCmd();
		c.caretOffset = -1;
		c.shiftsCaret = true;
		c.length = 0;
		c.offset = temp.getLineOffset(2);
		c.text = "\t";

		strategy.customizeDocumentCommand(temp, c);

		// execute
		temp.replace(c.offset, c.length, c.text);
		int newOffset = c.offset + (c.text == null ? 0 : c.text.length());
		if (c.caretOffset != -1)
			newOffset = c.caretOffset;

		temp.stopRewriteSession(session);

		// check document
		String correct = "proc foo{} {\n" + "	proc doo {} {\n" + "		";
		String result = temp.get();
		assertEquals(correct, result);

		// check caret position
		assertEquals(temp.getLineOffset(2) + 2, newOffset);
	}

	public void testIndent06_autoclose() throws Exception {
		String text = "proc foo{} {\n" + "	proc doo {} { set b 5\n" + "		\n"
				+ "#}";
		final Document temp = new Document(text);
		DocumentRewriteSession session = temp
				.startRewriteSession(DocumentRewriteSessionType.STRICTLY_SEQUENTIAL);
		installStuff(temp);

		DocumentCommand c = new DocCmd();
		c.caretOffset = -1;
		c.shiftsCaret = true;
		c.length = 0;
		c.offset = temp.getLineOffset(1) + 15;
		c.text = "\n";

		strategy.customizeDocumentCommand(temp, c);

		// execute
		temp.replace(c.offset, c.length, c.text);
		int newOffset = c.offset + (c.text == null ? 0 : c.text.length());
		if (c.caretOffset != -1)
			newOffset = c.caretOffset;

		temp.stopRewriteSession(session);

		// check document
		String correct = "proc foo{} {\n" + "	proc doo {} { \n" + "		set b 5\n"
				+ "	}\n" + "		\n" + "#}";
		String result = temp.get();
		assertEquals(correct, result);

		// check caret position
		assertEquals(temp.getLineOffset(2) + 2, newOffset);
	}

	public void testAutoCloseWithNewLine() throws Exception {
		IDocument doc = new Document("{}");
		DocCmd cmd = new DocCmd(1, 0, "\n");
		strategy.customizeDocumentCommand(doc, cmd);
		assertEquals("\n\t\n", cmd.text);
	}

	public void testAutoCloseWithNewLine2() throws Exception {
		IDocument doc = new Document("()");
		DocCmd cmd = new DocCmd(1, 0, "\n");
		strategy.customizeDocumentCommand(doc, cmd);
		assertEquals("\n \n", cmd.text);
	}

	public void testAutoCloseWithNewLine3() throws Exception {
		IDocument doc = new Document("(");
		DocCmd cmd = new DocCmd(1, 0, "\n");
		strategy.customizeDocumentCommand(doc, cmd);
		assertEquals("\n \n)", cmd.text);
	}

	public void testAutoCloseWithNewLine4() throws Exception {
		IDocument doc = new Document("{");
		DocCmd cmd = new DocCmd(1, 0, "\n");
		strategy.customizeDocumentCommand(doc, cmd);
		assertEquals("\n\t\n}", cmd.text);
	}

	public void testAutoCloseCommon() throws Exception {
		DocCmd cmd = new DocCmd(0, 0, "{");
		strategy.customizeDocumentCommand(new Document(""), cmd);
		assertEquals("{}", cmd.text);
		assertEquals(1, cmd.caretOffset);

		cmd = new DocCmd(0, 0, "(");
		strategy.customizeDocumentCommand(new Document(""), cmd);
		assertEquals("()", cmd.text);
		assertEquals(1, cmd.caretOffset);

		cmd = new DocCmd(0, 0, "[");
		strategy.customizeDocumentCommand(new Document(""), cmd);
		assertEquals("[]", cmd.text);
		assertEquals(1, cmd.caretOffset);

		// FIXME double quotes are now handled by TclBracketInserter
		// cmd = new DocCmd(0, 0, "\"");
		// strategy.customizeDocumentCommand(new Document("\""), cmd);
		// assertEquals("", cmd.text);

		cmd = new DocCmd(0, 0, "(");
		strategy.customizeDocumentCommand(new Document("("), cmd);
		assertEquals("(", cmd.text);

		cmd = new DocCmd(1, 0, ")");
		strategy.customizeDocumentCommand(new Document("()"), cmd);
		assertEquals("", cmd.text);

		cmd = new DocCmd(1, 0, "\"");
		strategy.customizeDocumentCommand(new Document("\"\""), cmd);
		assertEquals("\"", cmd.text);
	}

	public void testAutoCloseSmart() throws Exception {
		DocCmd cmd = new DocCmd(0, 0, "(");
		strategy.customizeDocumentCommand(new Document(")"), cmd);
		assertEquals("(", cmd.text);

		cmd = new DocCmd(0, 0, "{");
		strategy.customizeDocumentCommand(new Document("\n\thi!!!\n}"), cmd);
		assertEquals("{", cmd.text);
	}

	public void testNewLine0() throws Exception {
		DocCmd cmd = new DocCmd("proc foo { ".length(), 0, "\n");
		strategy.customizeDocumentCommand(new Document("proc foo { doo"), cmd);
		assertEquals("\n\tdoo\n}", cmd.text);
	}

	public void testNewLine1() throws Exception {
		DocCmd cmd = new DocCmd("proc foo { ".length(), 0, "\n");
		strategy.customizeDocumentCommand(new Document("proc foo { doo"), cmd);
		assertEquals("\n\tdoo\n}", cmd.text);
	}

	public void testCloseBrace() throws Exception {
		String text = "\tproc foo {\n\t\tdoo\n\t\t";
		DocCmd cmd = new DocCmd(text.length(), 0, "}");
		strategy.customizeDocumentCommand(new Document(text), cmd);
		assertEquals("\t}", cmd.text);
		assertEquals(text.length() - 2, cmd.offset);
	}

	public void testSmartPaste2() throws Exception {
		Document d = new Document("proc foo{} {\n" + "\t\n");
		DocCmd cmd = new DocCmd("proc foo{} {\n\t".length(), 0, "		set a 5\n"
				+ "		set b 6\n" + "	}\n");
		fStore.setValue(TclPreferenceConstants.EDITOR_SMART_PASTE_MODE,
				TclPreferenceConstants.EDITOR_SMART_PASTE_MODE_SIMPLE);
		strategy.customizeDocumentCommand(d, cmd);
		// execute
		d.replace(cmd.offset, cmd.length, cmd.text);
		assertEquals("proc foo{} {\n" + "	set a 5\n" + "	set b 6\n" + "}\n\n",
				d.get());

	}

	public void testSmartPaste289072() throws IOException, BadLocationException {
		fStore.setValue(TclPreferenceConstants.EDITOR_SMART_PASTE_MODE,
				TclPreferenceConstants.EDITOR_SMART_PASTE_MODE_FULL);
		String content = TclUITestsPlugin.getDefault().getPluginFileContent(
				"/tcls/bug289072.tcl");
		DocumentCommand c = new DocCmd();
		c.caretOffset = -1;
		c.shiftsCaret = true;
		c.length = 0;
		c.offset = content.indexOf("[]") + 1;
		c.text = "fakeproc";
		String expected = content.replaceAll("\\[\\]", "\\[" + c.text + "\\]");

		Document doc = new Document(content);

		strategy.customizeDocumentCommand(doc, c);
		doc.replace(c.offset, c.length, c.text);
		assertEquals(expected, doc.get());
	}

	public void testSmartPaste289072a() throws IOException,
			BadLocationException {
		fStore.setValue(TclPreferenceConstants.EDITOR_SMART_PASTE_MODE,
				TclPreferenceConstants.EDITOR_SMART_PASTE_MODE_FULL);
		String content = TclUITestsPlugin.getDefault().getPluginFileContent(
				"/tcls/bug289072.tcl");
		Document doc = new Document(content);

		DocumentCommand c = new DocCmd();
		c.caretOffset = -1;
		c.shiftsCaret = true;
		c.length = 0;
		c.offset = doc.getLineOffset(doc.getLineOfOffset(content
				.indexOf("set x")) + 1);
		c.text = "fakeproc\n";
		String expected = content.substring(0, c.offset) + ("\t\t" + c.text)
				+ content.substring(c.offset);
		strategy.customizeDocumentCommand(doc, c);
		doc.replace(c.offset, c.length, c.text);
		assertEquals(expected, doc.get());
	}

}
