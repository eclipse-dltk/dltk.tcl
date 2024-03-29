/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.ui.text;

import java.util.Arrays;

import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.tcl.ui.text.TclPartitions;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.TextUtilities;
import org.eclipse.jface.text.TypedRegion;

public class TclDocumentScanner {

	/**
	 * Returned by all methods when the requested position could not be found,
	 * or if a {@link BadLocationException} was thrown while scanning.
	 */
	public static final int NOT_FOUND = -1;

	/**
	 * Special bound parameter that means either -1 (backward scanning) or
	 * <code>fDocument.getLength()</code> (forward scanning).
	 */
	public static final int UNBOUND = -2;

	/**
	 * Specifies the stop condition, upon which the <code>scanXXX</code> methods
	 * will decide whether to keep scanning or not. This interface may
	 * implemented by clients.
	 */
	private static abstract class StopCondition {
		/**
		 * Instructs the scanner to return the current position.
		 *
		 * @param ch
		 *            the char at the current position
		 * @param position
		 *            the current position
		 * @param forward
		 *            the iteration direction
		 * @return <code>true</code> if the stop condition is met.
		 */
		public abstract boolean stop(char ch, int position, boolean forward);

		/**
		 * Asks the condition to return the next position to query. The default
		 * is to return the next/previous position.
		 *
		 * @return the next position to scan
		 */
		public int nextPosition(int position, boolean forward) {
			return forward ? position + 1 : position - 1;
		}
	}

	/**
	 * Stops upon a non-whitespace (as defined by
	 * {@link Character#isWhitespace(char)}) character.
	 */
	private static class NonWhitespace extends StopCondition {
		@Override
		public boolean stop(char ch, int position, boolean forward) {
			return !Character.isWhitespace(ch);
		}
	}

	/**
	 * Stops upon a non-whitespace character in the default partition.
	 *
	 * @see NonWhitespace
	 */
	private final class NonWhitespaceDefaultPartition extends NonWhitespace {
		@Override
		public boolean stop(char ch, int position, boolean forward) {
			return super.stop(ch, position, true)
					&& isDefaultPartition(position);
		}

		@Override
		public int nextPosition(int position, boolean forward) {
			ITypedRegion partition = getPartition(position);
			if (fPartition.equals(partition.getType()))
				return super.nextPosition(position, forward);

			if (forward) {
				int end = partition.getOffset() + partition.getLength();
				if (position < end)
					return end;
			} else {
				int offset = partition.getOffset();
				if (position > offset)
					return offset - 1;
			}
			return super.nextPosition(position, forward);
		}
	}

	/**
	 * Stops upon a character in the default partition that matches the given
	 * character list.
	 */
	private final class CharacterMatch extends StopCondition {
		private final char[] fChars;

		/**
		 * Creates a new instance.
		 *
		 * @param ch
		 *            the single character to match
		 */
		public CharacterMatch(char ch) {
			this(new char[] { ch });
		}

		/**
		 * Creates a new instance.
		 *
		 * @param chars
		 *            the chars to match.
		 */
		public CharacterMatch(char[] chars) {
			Assert.isNotNull(chars);
			Assert.isTrue(chars.length > 0);
			fChars = chars;
			Arrays.sort(chars);
		}

		@Override
		public boolean stop(char ch, int position, boolean forward) {
			return Arrays.binarySearch(fChars, ch) >= 0
					&& isDefaultPartition(position);
		}

		@Override
		public int nextPosition(int position, boolean forward) {
			ITypedRegion partition = getPartition(position);
			if (fPartition.equals(partition.getType()))
				return super.nextPosition(position, forward);

			if (forward) {
				int end = partition.getOffset() + partition.getLength();
				if (position < end)
					return end;
			} else {
				int offset = partition.getOffset();
				if (position > offset)
					return offset - 1;
			}
			return super.nextPosition(position, forward);
		}
	}

	/** The document being scanned. */
	private final IDocument fDocument;

	/** The partitioning being used for scanning. */
	private final String fPartitioning;

	/** The partition to scan in. */
	private final String fPartition;

	/* internal scan state */

	/** the most recently read character. */
	private char fChar;

	/** the most recently read position. */
	private int fPos;

	/**
	 * The most recently used partition.
	 *
	 *
	 */
	private ITypedRegion fCachedPartition = new TypedRegion(-1, 0,
			"__no_partition_at_all"); //$NON-NLS-1$

	/* preset stop conditions */
	private final StopCondition fNonWSDefaultPart = new NonWhitespaceDefaultPartition();

	private final static StopCondition fNonWS = new NonWhitespace();

	/**
	 * Creates a new instance.
	 *
	 * @param document
	 *            the document to scan
	 * @param partitioning
	 *            the partitioning to use for scanning
	 * @param partition
	 *            the partition to scan in
	 */
	public TclDocumentScanner(IDocument document, String partitioning,
			String partition) {
		Assert.isLegal(document != null);
		Assert.isLegal(partitioning != null);
		Assert.isLegal(partition != null);
		fDocument = document;
		fPartitioning = partitioning;
		fPartition = partition;
	}

	/**
	 * Calls
	 * <code>this(document, IDocument, IDocument.DEFAULT_CONTENT_TYPE)</code>.
	 *
	 * @param document
	 *            the document to scan.
	 */
	public TclDocumentScanner(IDocument document) {
		this(document, TclPartitions.TCL_PARTITIONING,
				IDocument.DEFAULT_CONTENT_TYPE);
	}

	/**
	 * Returns the position of the closing peer character (forward search). Any
	 * scopes introduced by opening peers are skipped. All peers accounted for
	 * must reside in the default partition.
	 *
	 * <p>
	 * Note that <code>start</code> must not point to the opening peer, but to
	 * the first character being searched.
	 * </p>
	 *
	 * @param start
	 *            the start position
	 * @param openingPeer
	 *            the opening peer character (e.g. '{')
	 * @param closingPeer
	 *            the closing peer character (e.g. '}')
	 * @return the matching peer character position, or <code>NOT_FOUND</code>
	 */
	public int findClosingPeer(int start, final char openingPeer,
			final char closingPeer) {
		Assert.isLegal(start >= 0);

		try {
			int depth = 1;
			start -= 1;
			while (true) {
				start = scanForward(start + 1, UNBOUND, new CharacterMatch(
						new char[] { openingPeer, closingPeer }));
				if (start == NOT_FOUND)
					return NOT_FOUND;

				if (fDocument.getChar(start) == openingPeer)
					depth++;
				else
					depth--;

				if (depth == 0)
					return start;
			}

		} catch (BadLocationException e) {
			return NOT_FOUND;
		}
	}

	/**
	 * Returns the position of the opening peer character (backward search). Any
	 * scopes introduced by closing peers are skipped. All peers accounted for
	 * must reside in the default partition.
	 *
	 * <p>
	 * Note that <code>start</code> must not point to the closing peer, but to
	 * the first character being searched.
	 * </p>
	 *
	 * @param start
	 *            the start position
	 * @param openingPeer
	 *            the opening peer character (e.g. '{')
	 * @param closingPeer
	 *            the closing peer character (e.g. '}')
	 * @return the matching peer character position, or <code>NOT_FOUND</code>
	 */
	public int findOpeningPeer(int start, char openingPeer, char closingPeer) {
		Assert.isLegal(start < fDocument.getLength());

		try {
			int depth = 1;
			start += 1;
			while (true) {
				start = scanBackward(start - 1, UNBOUND, new CharacterMatch(
						new char[] { openingPeer, closingPeer }));
				if (start == NOT_FOUND)
					return NOT_FOUND;

				if (fDocument.getChar(start) == closingPeer)
					depth++;
				else
					depth--;

				if (depth == 0)
					return start;
			}

		} catch (BadLocationException e) {
			return NOT_FOUND;
		}
	}

	/**
	 * Finds the smallest position in <code>fDocument</code> such that the
	 * position is &gt;= <code>position</code> and &lt; <code>bound</code> and
	 * <code>Character.isWhitespace(fDocument.getChar(pos))</code> evaluates to
	 * <code>false</code> and the position is in the default partition.
	 *
	 * @param position
	 *            the first character position in <code>fDocument</code> to be
	 *            considered
	 * @param bound
	 *            the first position in <code>fDocument</code> to not consider
	 *            any more, with <code>bound</code> &gt; <code>position</code>,
	 *            or <code>UNBOUND</code>
	 * @return the smallest position of a non-whitespace character in
	 *         [<code>position</code>, <code>bound</code>) that resides in a Tcl
	 *         partition, or <code>NOT_FOUND</code> if none can be found
	 */
	public int findNonWhitespaceForward(int position, int bound) {
		return scanForward(position, bound, fNonWSDefaultPart);
	}

	/**
	 * Finds the smallest position in <code>fDocument</code> such that the
	 * position is &gt;= <code>position</code> and &lt; <code>bound</code> and
	 * <code>Character.isWhitespace(fDocument.getChar(pos))</code> evaluates to
	 * <code>false</code>.
	 *
	 * @param position
	 *            the first character position in <code>fDocument</code> to be
	 *            considered
	 * @param bound
	 *            the first position in <code>fDocument</code> to not consider
	 *            any more, with <code>bound</code> &gt; <code>position</code>,
	 *            or <code>UNBOUND</code>
	 * @return the smallest position of a non-whitespace character in
	 *         [<code>position</code>, <code>bound</code>), or
	 *         <code>NOT_FOUND</code> if none can be found
	 */
	public int findNonWhitespaceForwardInAnyPartition(int position, int bound) {
		return scanForward(position, bound, fNonWS);
	}

	/**
	 * Finds the highest position in <code>fDocument</code> such that the
	 * position is &lt;= <code>position</code> and &gt; <code>bound</code> and
	 * <code>Character.isWhitespace(fDocument.getChar(pos))</code> evaluates to
	 * <code>false</code> and the position is in the default partition.
	 *
	 * @param position
	 *            the first character position in <code>fDocument</code> to be
	 *            considered
	 * @param bound
	 *            the first position in <code>fDocument</code> to not consider
	 *            any more, with <code>bound</code> &lt; <code>position</code>,
	 *            or <code>UNBOUND</code>
	 * @return the highest position of a non-whitespace character in
	 *         (<code>bound</code>, <code>position</code>] that resides in a Tcl
	 *         partition, or <code>NOT_FOUND</code> if none can be found
	 */
	public int findNonWhitespaceBackward(int position, int bound) {
		return scanBackward(position, bound, fNonWSDefaultPart);
	}

	/**
	 * Finds the lowest position <code>p</code> in <code>fDocument</code> such
	 * that <code>start</code> &lt;= p &lt; <code>bound</code> and
	 * <code>condition.stop(fDocument.getChar(p), p)</code> evaluates to
	 * <code>true</code>.
	 *
	 * @param start
	 *            the first character position in <code>fDocument</code> to be
	 *            considered
	 * @param bound
	 *            the first position in <code>fDocument</code> to not consider
	 *            any more, with <code>bound</code> &gt; <code>start</code>, or
	 *            <code>UNBOUND</code>
	 * @param condition
	 *            the <code>StopCondition</code> to check
	 * @return the lowest position in [<code>start</code>, <code>bound</code>)
	 *         for which <code>condition</code> holds, or <code>NOT_FOUND</code>
	 *         if none can be found
	 */
	public int scanForward(int start, int bound, StopCondition condition) {
		Assert.isLegal(start >= 0);

		if (bound == UNBOUND)
			bound = fDocument.getLength();

		Assert.isLegal(bound <= fDocument.getLength());

		try {
			fPos = start;
			while (fPos < bound) {

				fChar = fDocument.getChar(fPos);
				if (condition.stop(fChar, fPos, true))
					return fPos;

				fPos = condition.nextPosition(fPos, true);
			}
		} catch (BadLocationException e) {
		}
		return NOT_FOUND;
	}

	/**
	 * Finds the lowest position in <code>fDocument</code> such that the
	 * position is &gt;= <code>position</code> and &lt; <code>bound</code> and
	 * <code>fDocument.getChar(position) == ch</code> evaluates to
	 * <code>true</code> and the position is in the default partition.
	 *
	 * @param position
	 *            the first character position in <code>fDocument</code> to be
	 *            considered
	 * @param bound
	 *            the first position in <code>fDocument</code> to not consider
	 *            any more, with <code>bound</code> &gt; <code>position</code>,
	 *            or <code>UNBOUND</code>
	 * @param ch
	 *            the <code>char</code> to search for
	 * @return the lowest position of <code>ch</code> in (<code>bound</code>,
	 *         <code>position</code>] that resides in a Tcl partition, or
	 *         <code>NOT_FOUND</code> if none can be found
	 */
	public int scanForward(int position, int bound, char ch) {
		return scanForward(position, bound, new CharacterMatch(ch));
	}

	/**
	 * Finds the lowest position in <code>fDocument</code> such that the
	 * position is &gt;= <code>position</code> and &lt; <code>bound</code> and
	 * <code>fDocument.getChar(position) == ch</code> evaluates to
	 * <code>true</code> for at least one ch in <code>chars</code> and the
	 * position is in the default partition.
	 *
	 * @param position
	 *            the first character position in <code>fDocument</code> to be
	 *            considered
	 * @param bound
	 *            the first position in <code>fDocument</code> to not consider
	 *            any more, with <code>bound</code> &gt; <code>position</code>,
	 *            or <code>UNBOUND</code>
	 * @param chars
	 *            an array of <code>char</code> to search for
	 * @return the lowest position of a non-whitespace character in
	 *         [<code>position</code>, <code>bound</code>) that resides in a Tcl
	 *         partition, or <code>NOT_FOUND</code> if none can be found
	 */
	public int scanForward(int position, int bound, char[] chars) {
		return scanForward(position, bound, new CharacterMatch(chars));
	}

	/**
	 * Finds the highest position <code>p</code> in <code>fDocument</code> such
	 * that <code>bound</code> &lt; <code>p</code> &lt;= <code>start</code> and
	 * <code>condition.stop(fDocument.getChar(p), p)</code> evaluates to
	 * <code>true</code>.
	 *
	 * @param start
	 *            the first character position in <code>fDocument</code> to be
	 *            considered
	 * @param bound
	 *            the first position in <code>fDocument</code> to not consider
	 *            any more, with <code>bound</code> &lt; <code>start</code>, or
	 *            <code>UNBOUND</code>
	 * @param condition
	 *            the <code>StopCondition</code> to check
	 * @return the highest position in (<code>bound</code>, <code>start</code>
	 *         for which <code>condition</code> holds, or <code>NOT_FOUND</code>
	 *         if none can be found
	 */
	public int scanBackward(int start, int bound, StopCondition condition) {
		if (bound == UNBOUND)
			bound = -1;

		Assert.isLegal(bound >= -1);
		Assert.isLegal(start < fDocument.getLength());

		try {
			fPos = start;
			while (fPos > bound) {

				fChar = fDocument.getChar(fPos);
				if (condition.stop(fChar, fPos, false))
					return fPos;

				fPos = condition.nextPosition(fPos, false);
			}
		} catch (BadLocationException e) {
		}
		return NOT_FOUND;
	}

	/**
	 * Finds the highest position in <code>fDocument</code> such that the
	 * position is &lt;= <code>position</code> and &gt; <code>bound</code> and
	 * <code>fDocument.getChar(position) == ch</code> evaluates to
	 * <code>true</code> for at least one ch in <code>chars</code> and the
	 * position is in the default partition.
	 *
	 * @param position
	 *            the first character position in <code>fDocument</code> to be
	 *            considered
	 * @param bound
	 *            the first position in <code>fDocument</code> to not consider
	 *            any more, with <code>bound</code> &lt; <code>position</code>,
	 *            or <code>UNBOUND</code>
	 * @param ch
	 *            the <code>char</code> to search for
	 * @return the highest position of one element in <code>chars</code> in
	 *         (<code>bound</code>, <code>position</code>] that resides in a Tcl
	 *         partition, or <code>NOT_FOUND</code> if none can be found
	 */
	public int scanBackward(int position, int bound, char ch) {
		return scanBackward(position, bound, new CharacterMatch(ch));
	}

	/**
	 * Finds the highest position in <code>fDocument</code> such that the
	 * position is &lt;= <code>position</code> and &gt; <code>bound</code> and
	 * <code>fDocument.getChar(position) == ch</code> evaluates to
	 * <code>true</code> for at least one ch in <code>chars</code> and the
	 * position is in the default partition.
	 *
	 * @param position
	 *            the first character position in <code>fDocument</code> to be
	 *            considered
	 * @param bound
	 *            the first position in <code>fDocument</code> to not consider
	 *            any more, with <code>bound</code> &lt; <code>position</code>,
	 *            or <code>UNBOUND</code>
	 * @param chars
	 *            an array of <code>char</code> to search for
	 * @return the highest position of one element in <code>chars</code> in
	 *         (<code>bound</code>, <code>position</code>] that resides in a Tcl
	 *         partition, or <code>NOT_FOUND</code> if none can be found
	 */
	public int scanBackward(int position, int bound, char[] chars) {
		return scanBackward(position, bound, new CharacterMatch(chars));
	}

	/**
	 * Checks whether <code>position</code> resides in a default (Tcl) partition
	 * of <code>fDocument</code>.
	 *
	 * @param position
	 *            the position to be checked
	 * @return <code>true</code> if <code>position</code> is in the default
	 *         partition of <code>fDocument</code>, <code>false</code> otherwise
	 */
	public boolean isDefaultPartition(int position) {
		return fPartition.equals(getPartition(position).getType());
	}

	/**
	 * Returns the partition at <code>position</code>.
	 *
	 * @param position
	 *            the position to get the partition for
	 * @return the partition at <code>position</code> or a dummy zero-length
	 *         partition if accessing the document fails
	 */
	private ITypedRegion getPartition(int position) {
		if (!contains(fCachedPartition, position)) {
			Assert.isTrue(position >= 0);
			Assert.isTrue(position <= fDocument.getLength());

			try {
				fCachedPartition = TextUtilities.getPartition(fDocument,
						fPartitioning, position, false);
			} catch (BadLocationException e) {
				fCachedPartition = new TypedRegion(position, 0,
						"__no_partition_at_all"); //$NON-NLS-1$
			}
		}

		return fCachedPartition;
	}

	/**
	 * Returns <code>true</code> if <code>region</code> contains
	 * <code>position</code>.
	 *
	 * @param region
	 *            a region
	 * @param position
	 *            an offset
	 * @return <code>true</code> if <code>region</code> contains
	 *         <code>position</code>
	 *
	 */
	private boolean contains(IRegion region, int position) {
		int offset = region.getOffset();
		return offset <= position && position < offset + region.getLength();
	}

	/**
	 * Computes the surrounding block around <code>offset</code>. The search is
	 * started at the beginning of <code>offset</code>, i.e. an opening brace at
	 * <code>offset</code> will not be part of the surrounding block, but a
	 * closing brace will.
	 *
	 * @param offset
	 *            the offset for which the surrounding block is computed
	 * @return a region describing the surrounding block, or <code>null</code>
	 *         if none can be found
	 */
	public IRegion findSurroundingBlock(int offset) {
		if (offset < 1 || offset >= fDocument.getLength())
			return null;

		int begin = findOpeningPeer(offset - 1, '{', '}');
		int end = findClosingPeer(offset, '{', '}');
		if (begin == NOT_FOUND || end == NOT_FOUND)
			return null;
		return new Region(begin, end + 1 - begin);
	}

}