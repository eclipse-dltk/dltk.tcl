/*******************************************************************************
 * Copyright (c) 2005, 2017 IBM Corporation and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.dltk.tcl.ui;

import org.eclipse.dltk.tcl.internal.ui.text.TclColorConstants;
import org.eclipse.dltk.ui.CodeFormatterConstants;
import org.eclipse.dltk.ui.PreferenceConstants;
import org.eclipse.dltk.ui.preferences.NewScriptProjectPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.RGB;

public class TclPreferenceConstants extends PreferenceConstants {

	/**
	 * A named preference that holds the color used to render keyword.
	 * <p>
	 * Value is of type <code>String</code>. A RGB color value encoded as a
	 * string using class <code>PreferenceConverter</code>
	 * </p>
	 *
	 * @see org.eclipse.jface.resource.StringConverter
	 * @see org.eclipse.jface.preference.PreferenceConverter
	 */
	public final static String EDITOR_KEYWORD_RETURN_COLOR = TclColorConstants.TCL_KEYWORD_RETURN;

	/**
	 * A named preference that controls whether kwyword are rendered in bold.
	 * <p>
	 * Value is of type <code>Boolean</code>. If <code>true</code> single line
	 * comments are rendered in bold. If <code>false</code> the are rendered
	 * using no font style attribute.
	 * </p>
	 */
	public final static String EDITOR_KEYWORD_RETURN_BOLD = TclColorConstants.TCL_KEYWORD_RETURN
			+ EDITOR_BOLD_SUFFIX;

	/**
	 * A named preference that controls whether keyword are rendered in italic.
	 * <p>
	 * Value is of type <code>Boolean</code>. If <code>true</code> single line
	 * comments are rendered in italic. If <code>false</code> the are rendered
	 * using no italic font style attribute.
	 * </p>
	 */
	public final static String EDITOR_KEYWORD_RETURN_ITALIC = TclColorConstants.TCL_KEYWORD_RETURN
			+ EDITOR_ITALIC_SUFFIX;

	/**
	 * A named preference that controls whether single line comments are
	 * rendered in strikethrough.
	 * <p>
	 * Value is of type <code>Boolean</code>. If <code>true</code> single line
	 * comments are rendered in strikethrough. If <code>false</code> the are
	 * rendered using no italic font style attribute.
	 * </p>
	 */
	public final static String EDITOR_KEYWORD_RETURN_STRIKETHROUGH = TclColorConstants.TCL_KEYWORD_RETURN
			+ EDITOR_STRIKETHROUGH_SUFFIX;

	/**
	 * A named preference that controls whether keyword are rendered in
	 * underline.
	 * <p>
	 * Value is of type <code>Boolean</code>. If <code>true</code> single line
	 * comments are rendered in underline. If <code>false</code> the are
	 * rendered using no italic font style attribute.
	 * </p>
	 *
	 *
	 */
	public final static String EDITOR_KEYWORD_RETURN_UNDERLINE = TclColorConstants.TCL_KEYWORD_RETURN
			+ EDITOR_UNDERLINE_SUFFIX;

	/**
	 * A named preference that holds the color used to render single line
	 * comments.
	 * <p>
	 * Value is of type <code>String</code>. A RGB color value encoded as a
	 * string using class <code>PreferenceConverter</code>
	 * </p>
	 *
	 * @see org.eclipse.jface.resource.StringConverter
	 * @see org.eclipse.jface.preference.PreferenceConverter
	 */
	public final static String EDITOR_SINGLE_LINE_COMMENT_COLOR = TclColorConstants.TCL_SINGLE_LINE_COMMENT;

	/**
	 * A named preference that controls whether single line comments are
	 * rendered in bold.
	 * <p>
	 * Value is of type <code>Boolean</code>. If <code>true</code> single line
	 * comments are rendered in bold. If <code>false</code> the are rendered
	 * using no font style attribute.
	 * </p>
	 */
	public final static String EDITOR_SINGLE_LINE_COMMENT_BOLD = TclColorConstants.TCL_SINGLE_LINE_COMMENT
			+ EDITOR_BOLD_SUFFIX;

	/**
	 * A named preference that controls whether single line comments are
	 * rendered in italic.
	 * <p>
	 * Value is of type <code>Boolean</code>. If <code>true</code> single line
	 * comments are rendered in italic. If <code>false</code> the are rendered
	 * using no italic font style attribute.
	 * </p>
	 */
	public final static String EDITOR_SINGLE_LINE_COMMENT_ITALIC = TclColorConstants.TCL_SINGLE_LINE_COMMENT
			+ EDITOR_ITALIC_SUFFIX;

	/**
	 * A named preference that controls whether single line comments are
	 * rendered in strikethrough.
	 * <p>
	 * Value is of type <code>Boolean</code>. If <code>true</code> single line
	 * comments are rendered in strikethrough. If <code>false</code> the are
	 * rendered using no italic font style attribute.
	 * </p>
	 */
	public final static String EDITOR_SINGLE_LINE_COMMENT_STRIKETHROUGH = TclColorConstants.TCL_SINGLE_LINE_COMMENT
			+ EDITOR_STRIKETHROUGH_SUFFIX;

	/**
	 * A named preference that controls whether single line comments are
	 * rendered in underline.
	 * <p>
	 * Value is of type <code>Boolean</code>. If <code>true</code> single line
	 * comments are rendered in underline. If <code>false</code> the are
	 * rendered using no italic font style attribute.
	 * </p>
	 *
	 *
	 */
	public final static String EDITOR_SINGLE_LINE_COMMENT_UNDERLINE = TclColorConstants.TCL_SINGLE_LINE_COMMENT
			+ EDITOR_UNDERLINE_SUFFIX;

	/*
	 * Key worlds
	 */
	/**
	 * A named preference that holds the color used to render keyword.
	 * <p>
	 * Value is of type <code>String</code>. A RGB color value encoded as a
	 * string using class <code>PreferenceConverter</code>
	 * </p>
	 *
	 * @see org.eclipse.jface.resource.StringConverter
	 * @see org.eclipse.jface.preference.PreferenceConverter
	 */
	public final static String EDITOR_KEYWORD_COLOR = TclColorConstants.TCL_KEYWORD;

	/**
	 * A named preference that controls whether kwyword are rendered in bold.
	 * <p>
	 * Value is of type <code>Boolean</code>. If <code>true</code> single line
	 * comments are rendered in bold. If <code>false</code> the are rendered
	 * using no font style attribute.
	 * </p>
	 */
	public final static String EDITOR_KEYWORD_BOLD = TclColorConstants.TCL_KEYWORD
			+ EDITOR_BOLD_SUFFIX;

	/**
	 * A named preference that controls whether keyword are rendered in italic.
	 * <p>
	 * Value is of type <code>Boolean</code>. If <code>true</code> single line
	 * comments are rendered in italic. If <code>false</code> the are rendered
	 * using no italic font style attribute.
	 * </p>
	 */
	public final static String EDITOR_KEYWORD_ITALIC = TclColorConstants.TCL_KEYWORD
			+ EDITOR_ITALIC_SUFFIX;

	/**
	 * A named preference that controls whether single line comments are
	 * rendered in strikethrough.
	 * <p>
	 * Value is of type <code>Boolean</code>. If <code>true</code> single line
	 * comments are rendered in strikethrough. If <code>false</code> the are
	 * rendered using no italic font style attribute.
	 * </p>
	 */
	public final static String EDITOR_KEYWORD_STRIKETHROUGH = TclColorConstants.TCL_KEYWORD
			+ EDITOR_STRIKETHROUGH_SUFFIX;

	/**
	 * A named preference that controls whether keyword are rendered in
	 * underline.
	 * <p>
	 * Value is of type <code>Boolean</code>. If <code>true</code> single line
	 * comments are rendered in underline. If <code>false</code> the are
	 * rendered using no italic font style attribute.
	 * </p>
	 *
	 *
	 */
	public final static String EDITOR_KEYWORD_UNDERLINE = TclColorConstants.TCL_KEYWORD
			+ EDITOR_UNDERLINE_SUFFIX;

	/*
	 * Numbers
	 */
	/**
	 * A named preference that holds the color used to render NUMBER.
	 * <p>
	 * Value is of type <code>String</code>. A RGB color value encoded as a
	 * string using class <code>PreferenceConverter</code>
	 * </p>
	 *
	 * @see org.eclipse.jface.resource.StringConverter
	 * @see org.eclipse.jface.preference.PreferenceConverter
	 */
	public final static String EDITOR_NUMBER_COLOR = TclColorConstants.TCL_NUMBER;

	/**
	 * A named preference that controls whether number are rendered in bold.
	 * <p>
	 * Value is of type <code>Boolean</code>. If <code>true</code> single line
	 * comments are rendered in bold. If <code>false</code> the are rendered
	 * using no font style attribute.
	 * </p>
	 */
	public final static String EDITOR_NUMBER_BOLD = TclColorConstants.TCL_NUMBER
			+ EDITOR_BOLD_SUFFIX;

	/**
	 * A named preference that controls whether NUMBER are rendered in italic.
	 * <p>
	 * Value is of type <code>Boolean</code>. If <code>true</code> single line
	 * comments are rendered in italic. If <code>false</code> the are rendered
	 * using no italic font style attribute.
	 * </p>
	 */
	public final static String EDITOR_NUMBER_ITALIC = TclColorConstants.TCL_NUMBER
			+ EDITOR_ITALIC_SUFFIX;

	/**
	 * A named preference that controls whether single line comments are
	 * rendered in strikethrough.
	 * <p>
	 * Value is of type <code>Boolean</code>. If <code>true</code> single line
	 * comments are rendered in strikethrough. If <code>false</code> the are
	 * rendered using no italic font style attribute.
	 * </p>
	 */
	public final static String EDITOR_NUMBER_STRIKETHROUGH = TclColorConstants.TCL_NUMBER
			+ EDITOR_STRIKETHROUGH_SUFFIX;

	/**
	 * A named preference that controls whether NUMBER are rendered in
	 * underline.
	 * <p>
	 * Value is of type <code>Boolean</code>. If <code>true</code> single line
	 * comments are rendered in underline. If <code>false</code> the are
	 * rendered using no italic font style attribute.
	 * </p>
	 *
	 *
	 */

	public final static String EDITOR_NUMBER_UNDERLINE = TclColorConstants.TCL_NUMBER
			+ EDITOR_UNDERLINE_SUFFIX;

	/*
	 * Variables
	 */
	/**
	 * A named preference that holds the color used to render VARIABLE.
	 * <p>
	 * Value is of type <code>String</code>. A RGB color value encoded as a
	 * string using class <code>PreferenceConverter</code>
	 * </p>
	 *
	 * @see org.eclipse.jface.resource.StringConverter
	 * @see org.eclipse.jface.preference.PreferenceConverter
	 */
	public final static String EDITOR_VARIABLE_COLOR = TclColorConstants.TCL_VARIABLE;

	/**
	 * A named preference that controls whether VARIABLE are rendered in bold.
	 * <p>
	 * Value is of type <code>Boolean</code>. If <code>true</code> single line
	 * comments are rendered in bold. If <code>false</code> the are rendered
	 * using no font style attribute.
	 * </p>
	 */
	public final static String EDITOR_VARIABLE_BOLD = TclColorConstants.TCL_VARIABLE
			+ EDITOR_BOLD_SUFFIX;

	/**
	 * A named preference that controls whether VARIABLE are rendered in italic.
	 * <p>
	 * Value is of type <code>Boolean</code>. If <code>true</code> single line
	 * comments are rendered in italic. If <code>false</code> the are rendered
	 * using no italic font style attribute.
	 * </p>
	 */
	public final static String EDITOR_VARIABLE_ITALIC = TclColorConstants.TCL_VARIABLE
			+ EDITOR_ITALIC_SUFFIX;

	/**
	 * A named preference that controls whether single line comments are
	 * rendered in strikethrough.
	 * <p>
	 * Value is of type <code>Boolean</code>. If <code>true</code> single line
	 * comments are rendered in strikethrough. If <code>false</code> the are
	 * rendered using no italic font style attribute.
	 * </p>
	 */
	public final static String EDITOR_VARIABLE_STRIKETHROUGH = TclColorConstants.TCL_VARIABLE
			+ EDITOR_STRIKETHROUGH_SUFFIX;

	/**
	 * A named preference that controls whether VARIABLE are rendered in
	 * underline.
	 * <p>
	 * Value is of type <code>Boolean</code>. If <code>true</code> single line
	 * comments are rendered in underline. If <code>false</code> the are
	 * rendered using no italic font style attribute.
	 * </p>
	 *
	 *
	 */

	public final static String EDITOR_VARIABLE_UNDERLINE = TclColorConstants.TCL_VARIABLE
			+ EDITOR_UNDERLINE_SUFFIX;

	/*
	 * Strings
	 */
	/**
	 * A named preference that holds the color used to render STRING.
	 * <p>
	 * Value is of type <code>String</code>. A RGB color value encoded as a
	 * string using class <code>PreferenceConverter</code>
	 * </p>
	 *
	 * @see org.eclipse.jface.resource.StringConverter
	 * @see org.eclipse.jface.preference.PreferenceConverter
	 */
	public final static String EDITOR_STRING_COLOR = TclColorConstants.TCL_STRING;

	/**
	 * A named preference that controls whether STRING are rendered in bold.
	 * <p>
	 * Value is of type <code>Boolean</code>. If <code>true</code> single line
	 * comments are rendered in bold. If <code>false</code> the are rendered
	 * using no font style attribute.
	 * </p>
	 */
	public final static String EDITOR_STRING_BOLD = TclColorConstants.TCL_STRING
			+ EDITOR_BOLD_SUFFIX;

	/**
	 * A named preference that controls whether STRING are rendered in italic.
	 * <p>
	 * Value is of type <code>Boolean</code>. If <code>true</code> single line
	 * comments are rendered in italic. If <code>false</code> the are rendered
	 * using no italic font style attribute.
	 * </p>
	 */
	public final static String EDITOR_STRING_ITALIC = TclColorConstants.TCL_STRING
			+ EDITOR_ITALIC_SUFFIX;

	/**
	 * A named preference that controls whether single line comments are
	 * rendered in strikethrough.
	 * <p>
	 * Value is of type <code>Boolean</code>. If <code>true</code> single line
	 * comments are rendered in strikethrough. If <code>false</code> the are
	 * rendered using no italic font style attribute.
	 * </p>
	 */
	public final static String EDITOR_STRING_STRIKETHROUGH = TclColorConstants.TCL_STRING
			+ EDITOR_STRIKETHROUGH_SUFFIX;

	/**
	 * A named preference that controls whether STRING are rendered in
	 * underline.
	 * <p>
	 * Value is of type <code>Boolean</code>. If <code>true</code> single line
	 * comments are rendered in underline. If <code>false</code> the are
	 * rendered using no italic font style attribute.
	 * </p>
	 *
	 *
	 */
	public final static String EDITOR_STRING_UNDERLINE = TclColorConstants.TCL_STRING
			+ EDITOR_UNDERLINE_SUFFIX;

	/**
	 * A named preference that controls whether the 'smart paste' feature is
	 * enabled. In simple mode (1) we just shift inserted block to correct
	 * common level. In full mode(2) we try to fully reindent all the code.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 *
	 */
	public final static String EDITOR_SMART_PASTE_MODE = "smartPasteMode"; //$NON-NLS-1$
	public final static int EDITOR_SMART_PASTE_MODE_SIMPLE = 1;
	public final static int EDITOR_SMART_PASTE_MODE_FULL = 2;

	/**
	 * A named preference that stores the value for imports folding for the
	 * default folding provider.
	 * <p>
	 * Value is of type <code>Boolean</code>.
	 * </p>
	 *
	 *
	 */
	public static final String EDITOR_FOLDING_IMPORTS = "editor_folding_default_imports"; //$NON-NLS-1$

	public static final String EDITOR_FOLDING_BLOCKS = "editor_folding_blocks"; //$NON-NLS-1$

	public static final int EDITOR_FOLDING_BLOCKS_OFF = 0;

	public static final int EDITOR_FOLDING_BLOCKS_INCLUDE = 1;

	public static final int EDITOR_FOLDING_BLOCKS_EXCLUDE = 2;

	public static final String EDITOR_FOLDING_INCLUDE_LIST = "editor_folding_include_list"; //$NON-NLS-1$

	public static final String EDITOR_FOLDING_EXCLUDE_LIST = "editor_folding_exclude_list"; //$NON-NLS-1$

	public static final String EDITOR_FOLDING_INIT_NAMESPACES = "editor_folding_init_namespaces"; //$NON-NLS-1$

	public static final String EDITOR_FOLDING_INIT_BLOCKS = "editor_folding_init_blocks"; //$NON-NLS-1$

	public static final String EDITOR_FOLDING_INIT_OTHER = "editor_folding_init_other_blocks"; //$NON-NLS-1$

	public static final String DOC_MAN_PAGES_LOCATIONS = "doc_man_pages_locations"; //$NON-NLS-1$

	public static final String CODEASSIST_FILTER_INTERNAL_API = "tcl_filter_internal_api"; //$NON-NLS-1$

	public final static String COMMENT_TASK_TAGS = TclColorConstants.TCL_TODO_TAG;

	public final static String COMMENT_TASK_TAGS_BOLD = COMMENT_TASK_TAGS
			+ EDITOR_BOLD_SUFFIX;

	public final static String EDITOR_CLASSES_COLOR = TclColorConstants.TCL_CLASS;
	public final static String EDITOR_CLASSES_COLOR_BOLD = EDITOR_CLASSES_COLOR
			+ EDITOR_BOLD_SUFFIX;

	public final static String EDITOR_PROCEDURES_COLOR = TclColorConstants.TCL_PROCEDURE;

	public final static String EDITOR_PROCEDURES_COLOR_BOLD = EDITOR_PROCEDURES_COLOR
			+ EDITOR_BOLD_SUFFIX;

	public final static String EDITOR_ARGUMENTS_COLOR = TclColorConstants.TCL_ARGUMENT;
	public final static String EDITOR_BASE_CLASS_COLOR = TclColorConstants.TCL_BASE_CLASS;

	public static void initializeDefaultValues(IPreferenceStore store) {
		PreferenceConstants.initializeDefaultValues(store);

		PreferenceConverter.setDefault(store,
				TclPreferenceConstants.EDITOR_SINGLE_LINE_COMMENT_COLOR,
				new RGB(63, 127, 95));
		PreferenceConverter.setDefault(store, COMMENT_TASK_TAGS,
				new RGB(127, 159, 191));
		PreferenceConverter.setDefault(store,
				TclPreferenceConstants.EDITOR_KEYWORD_COLOR,
				new RGB(127, 0, 85));
		PreferenceConverter.setDefault(store,
				TclPreferenceConstants.EDITOR_KEYWORD_RETURN_COLOR,
				new RGB(127, 0, 85));

		PreferenceConverter.setDefault(store,
				TclPreferenceConstants.EDITOR_STRING_COLOR,
				new RGB(42, 0, 255));
		PreferenceConverter.setDefault(store,
				TclPreferenceConstants.EDITOR_NUMBER_COLOR, new RGB(0, 0, 0));
		PreferenceConverter.setDefault(store,
				TclPreferenceConstants.EDITOR_VARIABLE_COLOR,
				new RGB(200, 0, 0));

		store.setDefault(TclPreferenceConstants.EDITOR_SINGLE_LINE_COMMENT_BOLD,
				false);
		store.setDefault(COMMENT_TASK_TAGS_BOLD, true);
		store.setDefault(
				TclPreferenceConstants.EDITOR_SINGLE_LINE_COMMENT_ITALIC,
				false);

		store.setDefault(TclPreferenceConstants.EDITOR_KEYWORD_BOLD, true);
		store.setDefault(TclPreferenceConstants.EDITOR_KEYWORD_ITALIC, false);
		store.setDefault(TclPreferenceConstants.EDITOR_KEYWORD_RETURN_BOLD,
				true);
		store.setDefault(TclPreferenceConstants.EDITOR_KEYWORD_RETURN_ITALIC,
				false);

		store.setDefault(PreferenceConstants.EDITOR_CLOSE_STRINGS, true);
		store.setDefault(PreferenceConstants.EDITOR_CLOSE_BRACKETS, true);
		store.setDefault(PreferenceConstants.EDITOR_CLOSE_BRACES, true);
		store.setDefault(PreferenceConstants.EDITOR_SMART_TAB, true);
		store.setDefault(TclPreferenceConstants.EDITOR_SMART_PASTE_MODE,
				TclPreferenceConstants.EDITOR_SMART_PASTE_MODE_SIMPLE);

		store.setDefault(PreferenceConstants.EDITOR_SMART_INDENT, true);
		store.setDefault(PreferenceConstants.EDITOR_TAB_WIDTH, 4);
		store.setDefault(PreferenceConstants.EDITOR_SMART_HOME_END, true);
		store.setDefault(PreferenceConstants.EDITOR_SUB_WORD_NAVIGATION, true);
		store.setDefault(PreferenceConstants.EDITOR_SYNC_OUTLINE_ON_CURSOR_MOVE,
				true);

		// folding
		store.setDefault(PreferenceConstants.EDITOR_FOLDING_ENABLED, true);
		store.setDefault(PreferenceConstants.EDITOR_COMMENTS_FOLDING_ENABLED,
				true);
		store.setDefault(TclPreferenceConstants.EDITOR_FOLDING_BLOCKS,
				TclPreferenceConstants.EDITOR_FOLDING_BLOCKS_EXCLUDE);
		store.setDefault(TclPreferenceConstants.EDITOR_FOLDING_INCLUDE_LIST,
				"proc,namespace,catch,switch,if,while,for,foreach"); //$NON-NLS-1$
		store.setDefault(TclPreferenceConstants.EDITOR_FOLDING_EXCLUDE_LIST,
				"variable,global"); //$NON-NLS-1$
		// store.setDefault(
		// TclPreferenceConstants.EDITOR_FOLDING_COMMENTS_WITH_NEWLINES,
		// true);
		store.setDefault(
				PreferenceConstants.EDITOR_FOLDING_INIT_HEADER_COMMENTS, true);
		store.setDefault(PreferenceConstants.EDITOR_FOLDING_LINES_LIMIT, 5);

		store.setDefault(CodeFormatterConstants.FORMATTER_TAB_CHAR,
				CodeFormatterConstants.MIXED);
		store.setDefault(CodeFormatterConstants.FORMATTER_TAB_SIZE, "8");
		store.setDefault(CodeFormatterConstants.FORMATTER_INDENTATION_SIZE,
				"4");

		// do more complicated stuff
		NewScriptProjectPreferencePage.initDefaults(store);

		store.setDefault(PreferenceConstants.APPEARANCE_COMPRESS_PACKAGE_NAMES,
				false);
		store.setDefault(PreferenceConstants.APPEARANCE_METHOD_RETURNTYPE,
				false);
		store.setDefault(PreferenceConstants.APPEARANCE_METHOD_TYPEPARAMETERS,
				true);
		store.setDefault(
				PreferenceConstants.APPEARANCE_PKG_NAME_PATTERN_FOR_PKG_VIEW,
				""); //$NON-NLS-1$

		store.setDefault(PreferenceConstants.SHOW_SOURCE_MODULE_CHILDREN, true);

		store.setDefault(PreferenceConstants.CODEASSIST_AUTOACTIVATION_TRIGGERS,
				":$"); //$NON-NLS-1$

		store.setDefault(CODEASSIST_FILTER_INTERNAL_API, true);
	}

	/**
	 * A preference that controls the selected formatter.
	 *
	 * @since 2.0
	 */
	public static final String FORMATTER_ID = "formatterId"; //$NON-NLS-1$
}
