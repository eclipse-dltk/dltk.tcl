/**
 * Copyright (c) 2008 xored software, Inc.  
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0  
 * 
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 * 
 *
 * $Id: PackagePattern.java,v 1.2 2009/10/26 13:41:25 apanchenk Exp $
 */
package org.eclipse.dltk.tcl.activestatedebugger.preferences;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Package Pattern</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.dltk.tcl.activestatedebugger.preferences.PackagePattern#getPackageName <em>Package Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.dltk.tcl.activestatedebugger.preferences.PreferencesPackage#getPackagePattern()
 * @model
 * @generated
 * @since 2.0
 */
public interface PackagePattern extends Pattern {
	/**
	 * Returns the value of the '<em><b>Package Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Package Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Package Name</em>' attribute.
	 * @see #setPackageName(String)
	 * @see org.eclipse.dltk.tcl.activestatedebugger.preferences.PreferencesPackage#getPackagePattern_PackageName()
	 * @model
	 * @generated
	 */
	String getPackageName();

	/**
	 * Sets the value of the '{@link org.eclipse.dltk.tcl.activestatedebugger.preferences.PackagePattern#getPackageName <em>Package Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Package Name</em>' attribute.
	 * @see #getPackageName()
	 * @generated
	 */
	void setPackageName(String value);

} // PackagePattern
