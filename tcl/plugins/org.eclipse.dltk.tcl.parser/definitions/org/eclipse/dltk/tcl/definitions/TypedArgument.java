/*******************************************************************************
 * Copyright (c) 2008 xored software, Inc.  
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html  
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Andrei Sobolev)
 *******************************************************************************/
package org.eclipse.dltk.tcl.definitions;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Typed Argument</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.dltk.tcl.definitions.TypedArgument#getType <em>Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.dltk.tcl.definitions.DefinitionsPackage#getTypedArgument()
 * @model
 * @generated
 */
public interface TypedArgument extends Argument {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.dltk.tcl.definitions.ArgumentType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see org.eclipse.dltk.tcl.definitions.ArgumentType
	 * @see #setType(ArgumentType)
	 * @see org.eclipse.dltk.tcl.definitions.DefinitionsPackage#getTypedArgument_Type()
	 * @model
	 * @generated
	 */
	ArgumentType getType();

	/**
	 * Sets the value of the '{@link org.eclipse.dltk.tcl.definitions.TypedArgument#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see org.eclipse.dltk.tcl.definitions.ArgumentType
	 * @see #getType()
	 * @generated
	 */
	void setType(ArgumentType value);

} // TypedArgument
