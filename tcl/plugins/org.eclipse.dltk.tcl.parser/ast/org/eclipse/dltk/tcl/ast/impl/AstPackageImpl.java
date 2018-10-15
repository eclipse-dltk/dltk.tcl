/*******************************************************************************
 * Copyright (c) 2008 xored software, Inc.  
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
package org.eclipse.dltk.tcl.ast.impl;

import org.eclipse.dltk.tcl.ast.ArgumentMatch;
import org.eclipse.dltk.tcl.ast.AstFactory;
import org.eclipse.dltk.tcl.ast.AstPackage;
import org.eclipse.dltk.tcl.ast.ComplexString;
import org.eclipse.dltk.tcl.ast.ISubstitution;
import org.eclipse.dltk.tcl.ast.Node;
import org.eclipse.dltk.tcl.ast.Script;
import org.eclipse.dltk.tcl.ast.StringArgument;
import org.eclipse.dltk.tcl.ast.Substitution;
import org.eclipse.dltk.tcl.ast.TclArgument;
import org.eclipse.dltk.tcl.ast.TclArgumentList;
import org.eclipse.dltk.tcl.ast.TclCodeModel;
import org.eclipse.dltk.tcl.ast.TclCommand;
import org.eclipse.dltk.tcl.ast.TclModule;
import org.eclipse.dltk.tcl.ast.TclProblem;
import org.eclipse.dltk.tcl.ast.TclProblemModel;
import org.eclipse.dltk.tcl.ast.VariableReference;

import org.eclipse.dltk.tcl.definitions.DefinitionsPackage;

import org.eclipse.dltk.tcl.definitions.impl.DefinitionsPackageImpl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class AstPackageImpl extends EPackageImpl implements AstPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass scriptEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stringArgumentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass substitutionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tclArgumentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tclCommandEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tclArgumentListEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass argumentMatchEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass complexStringEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass variableReferenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iSubstitutionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tclModuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tclCodeModelEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tclProblemModelEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tclProblemEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.dltk.tcl.ast.AstPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private AstPackageImpl() {
		super(eNS_URI, AstFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link AstPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static AstPackage init() {
		if (isInited)
			return (AstPackage) EPackage.Registry.INSTANCE
					.getEPackage(AstPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredAstPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		AstPackageImpl theAstPackage = registeredAstPackage instanceof AstPackageImpl
				? (AstPackageImpl) registeredAstPackage
				: new AstPackageImpl();

		isInited = true;

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE
				.getEPackage(DefinitionsPackage.eNS_URI);
		DefinitionsPackageImpl theDefinitionsPackage = (DefinitionsPackageImpl) (registeredPackage instanceof DefinitionsPackageImpl
				? registeredPackage
				: DefinitionsPackage.eINSTANCE);

		// Create package meta-data objects
		theAstPackage.createPackageContents();
		theDefinitionsPackage.createPackageContents();

		// Initialize created meta-data
		theAstPackage.initializePackageContents();
		theDefinitionsPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theAstPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(AstPackage.eNS_URI, theAstPackage);
		return theAstPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNode() {
		return nodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNode_Start() {
		return (EAttribute) nodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNode_End() {
		return (EAttribute) nodeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getScript() {
		return scriptEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getScript_Commands() {
		return (EReference) scriptEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getScript_ContentStart() {
		return (EAttribute) scriptEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getScript_ContentEnd() {
		return (EAttribute) scriptEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStringArgument() {
		return stringArgumentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStringArgument_Value() {
		return (EAttribute) stringArgumentEClass.getEStructuralFeatures()
				.get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStringArgument_RawValue() {
		return (EAttribute) stringArgumentEClass.getEStructuralFeatures()
				.get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSubstitution() {
		return substitutionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSubstitution_Commands() {
		return (EReference) substitutionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTclArgument() {
		return tclArgumentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTclCommand() {
		return tclCommandEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTclCommand_Name() {
		return (EReference) tclCommandEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTclCommand_Arguments() {
		return (EReference) tclCommandEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTclCommand_Definition() {
		return (EReference) tclCommandEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTclCommand_Matches() {
		return (EReference) tclCommandEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTclCommand_QualifiedName() {
		return (EAttribute) tclCommandEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTclCommand_Matched() {
		return (EAttribute) tclCommandEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTclArgumentList() {
		return tclArgumentListEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTclArgumentList_Arguments() {
		return (EReference) tclArgumentListEClass.getEStructuralFeatures()
				.get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTclArgumentList_DefinitionArgument() {
		return (EReference) tclArgumentListEClass.getEStructuralFeatures()
				.get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTclArgumentList_Kind() {
		return (EAttribute) tclArgumentListEClass.getEStructuralFeatures()
				.get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getArgumentMatch() {
		return argumentMatchEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getArgumentMatch_Definition() {
		return (EReference) argumentMatchEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getArgumentMatch_Arguments() {
		return (EReference) argumentMatchEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getComplexString() {
		return complexStringEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getComplexString_Arguments() {
		return (EReference) complexStringEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getComplexString_Kind() {
		return (EAttribute) complexStringEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVariableReference() {
		return variableReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVariableReference_Name() {
		return (EAttribute) variableReferenceEClass.getEStructuralFeatures()
				.get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVariableReference_Index() {
		return (EReference) variableReferenceEClass.getEStructuralFeatures()
				.get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getISubstitution() {
		return iSubstitutionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTclModule() {
		return tclModuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTclModule_Statements() {
		return (EReference) tclModuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTclModule_Size() {
		return (EAttribute) tclModuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTclModule_CodeModel() {
		return (EReference) tclModuleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTclCodeModel() {
		return tclCodeModelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTclCodeModel_Delimeters() {
		return (EAttribute) tclCodeModelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTclCodeModel_LineOffsets() {
		return (EAttribute) tclCodeModelEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTclProblemModel() {
		return tclProblemModelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTclProblemModel_Problems() {
		return (EReference) tclProblemModelEClass.getEStructuralFeatures()
				.get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTclProblem() {
		return tclProblemEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTclProblem_Arguments() {
		return (EAttribute) tclProblemEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTclProblem_Id() {
		return (EAttribute) tclProblemEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTclProblem_Message() {
		return (EAttribute) tclProblemEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTclProblem_SourceStart() {
		return (EAttribute) tclProblemEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTclProblem_SourceEnd() {
		return (EAttribute) tclProblemEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTclProblem_Error() {
		return (EAttribute) tclProblemEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTclProblem_Warning() {
		return (EAttribute) tclProblemEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTclProblem_FileName() {
		return (EAttribute) tclProblemEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTclProblem_LineNumber() {
		return (EAttribute) tclProblemEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AstFactory getAstFactory() {
		return (AstFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		nodeEClass = createEClass(NODE);
		createEAttribute(nodeEClass, NODE__START);
		createEAttribute(nodeEClass, NODE__END);

		scriptEClass = createEClass(SCRIPT);
		createEReference(scriptEClass, SCRIPT__COMMANDS);
		createEAttribute(scriptEClass, SCRIPT__CONTENT_START);
		createEAttribute(scriptEClass, SCRIPT__CONTENT_END);

		stringArgumentEClass = createEClass(STRING_ARGUMENT);
		createEAttribute(stringArgumentEClass, STRING_ARGUMENT__VALUE);
		createEAttribute(stringArgumentEClass, STRING_ARGUMENT__RAW_VALUE);

		substitutionEClass = createEClass(SUBSTITUTION);
		createEReference(substitutionEClass, SUBSTITUTION__COMMANDS);

		tclArgumentEClass = createEClass(TCL_ARGUMENT);

		tclCommandEClass = createEClass(TCL_COMMAND);
		createEReference(tclCommandEClass, TCL_COMMAND__NAME);
		createEReference(tclCommandEClass, TCL_COMMAND__ARGUMENTS);
		createEReference(tclCommandEClass, TCL_COMMAND__DEFINITION);
		createEReference(tclCommandEClass, TCL_COMMAND__MATCHES);
		createEAttribute(tclCommandEClass, TCL_COMMAND__QUALIFIED_NAME);
		createEAttribute(tclCommandEClass, TCL_COMMAND__MATCHED);

		tclArgumentListEClass = createEClass(TCL_ARGUMENT_LIST);
		createEReference(tclArgumentListEClass, TCL_ARGUMENT_LIST__ARGUMENTS);
		createEReference(tclArgumentListEClass,
				TCL_ARGUMENT_LIST__DEFINITION_ARGUMENT);
		createEAttribute(tclArgumentListEClass, TCL_ARGUMENT_LIST__KIND);

		argumentMatchEClass = createEClass(ARGUMENT_MATCH);
		createEReference(argumentMatchEClass, ARGUMENT_MATCH__DEFINITION);
		createEReference(argumentMatchEClass, ARGUMENT_MATCH__ARGUMENTS);

		complexStringEClass = createEClass(COMPLEX_STRING);
		createEReference(complexStringEClass, COMPLEX_STRING__ARGUMENTS);
		createEAttribute(complexStringEClass, COMPLEX_STRING__KIND);

		variableReferenceEClass = createEClass(VARIABLE_REFERENCE);
		createEAttribute(variableReferenceEClass, VARIABLE_REFERENCE__NAME);
		createEReference(variableReferenceEClass, VARIABLE_REFERENCE__INDEX);

		iSubstitutionEClass = createEClass(ISUBSTITUTION);

		tclModuleEClass = createEClass(TCL_MODULE);
		createEReference(tclModuleEClass, TCL_MODULE__STATEMENTS);
		createEAttribute(tclModuleEClass, TCL_MODULE__SIZE);
		createEReference(tclModuleEClass, TCL_MODULE__CODE_MODEL);

		tclCodeModelEClass = createEClass(TCL_CODE_MODEL);
		createEAttribute(tclCodeModelEClass, TCL_CODE_MODEL__DELIMETERS);
		createEAttribute(tclCodeModelEClass, TCL_CODE_MODEL__LINE_OFFSETS);

		tclProblemModelEClass = createEClass(TCL_PROBLEM_MODEL);
		createEReference(tclProblemModelEClass, TCL_PROBLEM_MODEL__PROBLEMS);

		tclProblemEClass = createEClass(TCL_PROBLEM);
		createEAttribute(tclProblemEClass, TCL_PROBLEM__ARGUMENTS);
		createEAttribute(tclProblemEClass, TCL_PROBLEM__ID);
		createEAttribute(tclProblemEClass, TCL_PROBLEM__MESSAGE);
		createEAttribute(tclProblemEClass, TCL_PROBLEM__SOURCE_START);
		createEAttribute(tclProblemEClass, TCL_PROBLEM__SOURCE_END);
		createEAttribute(tclProblemEClass, TCL_PROBLEM__ERROR);
		createEAttribute(tclProblemEClass, TCL_PROBLEM__WARNING);
		createEAttribute(tclProblemEClass, TCL_PROBLEM__FILE_NAME);
		createEAttribute(tclProblemEClass, TCL_PROBLEM__LINE_NUMBER);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		DefinitionsPackage theDefinitionsPackage = (DefinitionsPackage) EPackage.Registry.INSTANCE
				.getEPackage(DefinitionsPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		scriptEClass.getESuperTypes().add(this.getTclArgument());
		stringArgumentEClass.getESuperTypes().add(this.getTclArgument());
		substitutionEClass.getESuperTypes().add(this.getTclArgument());
		substitutionEClass.getESuperTypes().add(this.getISubstitution());
		tclArgumentEClass.getESuperTypes().add(this.getNode());
		tclCommandEClass.getESuperTypes().add(this.getNode());
		tclArgumentListEClass.getESuperTypes().add(this.getTclArgument());
		complexStringEClass.getESuperTypes().add(this.getTclArgument());
		complexStringEClass.getESuperTypes().add(this.getISubstitution());
		variableReferenceEClass.getESuperTypes().add(this.getTclArgument());
		variableReferenceEClass.getESuperTypes().add(this.getISubstitution());

		// Initialize classes and features; add operations and parameters
		initEClass(nodeEClass, Node.class, "Node", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNode_Start(), ecorePackage.getEInt(), "start", null, //$NON-NLS-1$
				0, 1, Node.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNode_End(), ecorePackage.getEInt(), "end", null, 0, 1, //$NON-NLS-1$
				Node.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(scriptEClass, Script.class, "Script", !IS_ABSTRACT, //$NON-NLS-1$
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getScript_Commands(), this.getTclCommand(), null,
				"commands", null, 0, -1, Script.class, !IS_TRANSIENT, //$NON-NLS-1$
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getScript_ContentStart(), ecorePackage.getEInt(),
				"contentStart", null, 0, 1, Script.class, !IS_TRANSIENT, //$NON-NLS-1$
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getScript_ContentEnd(), ecorePackage.getEInt(),
				"contentEnd", null, 0, 1, Script.class, !IS_TRANSIENT, //$NON-NLS-1$
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(stringArgumentEClass, StringArgument.class, "StringArgument", //$NON-NLS-1$
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStringArgument_Value(), ecorePackage.getEString(),
				"value", null, 0, 1, StringArgument.class, !IS_TRANSIENT, //$NON-NLS-1$
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getStringArgument_RawValue(), ecorePackage.getEString(),
				"rawValue", null, 0, 1, StringArgument.class, IS_TRANSIENT, //$NON-NLS-1$
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(substitutionEClass, Substitution.class, "Substitution", //$NON-NLS-1$
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSubstitution_Commands(), this.getTclCommand(), null,
				"commands", null, 0, -1, Substitution.class, !IS_TRANSIENT, //$NON-NLS-1$
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tclArgumentEClass, TclArgument.class, "TclArgument", //$NON-NLS-1$
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(tclCommandEClass, TclCommand.class, "TclCommand", //$NON-NLS-1$
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTclCommand_Name(), this.getTclArgument(), null,
				"name", null, 0, 1, TclCommand.class, !IS_TRANSIENT, //$NON-NLS-1$
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTclCommand_Arguments(), this.getTclArgument(), null,
				"arguments", null, 0, -1, TclCommand.class, !IS_TRANSIENT, //$NON-NLS-1$
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTclCommand_Definition(),
				theDefinitionsPackage.getCommand(), null, "definition", null, 0, //$NON-NLS-1$
				1, TclCommand.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getTclCommand_Matches(), this.getArgumentMatch(), null,
				"matches", null, 0, -1, TclCommand.class, !IS_TRANSIENT, //$NON-NLS-1$
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTclCommand_QualifiedName(), ecorePackage.getEString(),
				"qualifiedName", null, 0, 1, TclCommand.class, !IS_TRANSIENT, //$NON-NLS-1$
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getTclCommand_Matched(), ecorePackage.getEBoolean(),
				"matched", null, 0, 1, TclCommand.class, !IS_TRANSIENT, //$NON-NLS-1$
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(tclArgumentListEClass, TclArgumentList.class,
				"TclArgumentList", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTclArgumentList_Arguments(), this.getTclArgument(),
				null, "arguments", null, 0, -1, TclArgumentList.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getTclArgumentList_DefinitionArgument(),
				theDefinitionsPackage.getComplexArgument(), null,
				"definitionArgument", null, 0, 1, TclArgumentList.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getTclArgumentList_Kind(), ecorePackage.getEInt(),
				"kind", null, 0, 1, TclArgumentList.class, !IS_TRANSIENT, //$NON-NLS-1$
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(argumentMatchEClass, ArgumentMatch.class, "ArgumentMatch", //$NON-NLS-1$
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getArgumentMatch_Definition(),
				theDefinitionsPackage.getArgument(), null, "definition", null, //$NON-NLS-1$
				0, 1, ArgumentMatch.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getArgumentMatch_Arguments(), this.getTclArgument(),
				null, "arguments", null, 0, -1, ArgumentMatch.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, !IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(complexStringEClass, ComplexString.class, "ComplexString", //$NON-NLS-1$
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getComplexString_Arguments(), this.getTclArgument(),
				null, "arguments", null, 0, -1, ComplexString.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getComplexString_Kind(), ecorePackage.getEInt(), "kind", //$NON-NLS-1$
				null, 0, 1, ComplexString.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(variableReferenceEClass, VariableReference.class,
				"VariableReference", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getVariableReference_Name(), ecorePackage.getEString(),
				"name", null, 0, 1, VariableReference.class, !IS_TRANSIENT, //$NON-NLS-1$
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getVariableReference_Index(), this.getTclArgument(),
				null, "index", null, 0, 1, VariableReference.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(iSubstitutionEClass, ISubstitution.class, "ISubstitution", //$NON-NLS-1$
				IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(tclModuleEClass, TclModule.class, "TclModule", !IS_ABSTRACT, //$NON-NLS-1$
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTclModule_Statements(), this.getTclCommand(), null,
				"statements", null, 0, -1, TclModule.class, !IS_TRANSIENT, //$NON-NLS-1$
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTclModule_Size(), ecorePackage.getEInt(), "size", //$NON-NLS-1$
				null, 0, 1, TclModule.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getTclModule_CodeModel(), this.getTclCodeModel(), null,
				"codeModel", null, 0, 1, TclModule.class, !IS_TRANSIENT, //$NON-NLS-1$
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tclCodeModelEClass, TclCodeModel.class, "TclCodeModel", //$NON-NLS-1$
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTclCodeModel_Delimeters(), ecorePackage.getEString(),
				"delimeters", null, 0, -1, TclCodeModel.class, !IS_TRANSIENT, //$NON-NLS-1$
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getTclCodeModel_LineOffsets(), ecorePackage.getEInt(),
				"lineOffsets", null, 0, -1, TclCodeModel.class, !IS_TRANSIENT, //$NON-NLS-1$
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(tclProblemModelEClass, TclProblemModel.class,
				"TclProblemModel", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTclProblemModel_Problems(), this.getTclProblem(),
				null, "problems", null, 0, -1, TclProblemModel.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(tclProblemEClass, TclProblem.class, "TclProblem", //$NON-NLS-1$
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTclProblem_Arguments(), ecorePackage.getEString(),
				"arguments", null, 0, -1, TclProblem.class, !IS_TRANSIENT, //$NON-NLS-1$
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getTclProblem_Id(), ecorePackage.getEInt(), "id", null, //$NON-NLS-1$
				0, 1, TclProblem.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getTclProblem_Message(), ecorePackage.getEString(),
				"message", null, 0, 1, TclProblem.class, !IS_TRANSIENT, //$NON-NLS-1$
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getTclProblem_SourceStart(), ecorePackage.getEInt(),
				"sourceStart", null, 0, 1, TclProblem.class, !IS_TRANSIENT, //$NON-NLS-1$
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getTclProblem_SourceEnd(), ecorePackage.getEInt(),
				"sourceEnd", null, 0, 1, TclProblem.class, !IS_TRANSIENT, //$NON-NLS-1$
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getTclProblem_Error(), ecorePackage.getEBoolean(),
				"error", null, 0, 1, TclProblem.class, !IS_TRANSIENT, //$NON-NLS-1$
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getTclProblem_Warning(), ecorePackage.getEBoolean(),
				"warning", null, 0, 1, TclProblem.class, !IS_TRANSIENT, //$NON-NLS-1$
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getTclProblem_FileName(), ecorePackage.getEString(),
				"fileName", null, 0, 1, TclProblem.class, !IS_TRANSIENT, //$NON-NLS-1$
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getTclProblem_LineNumber(), ecorePackage.getEInt(),
				"lineNumber", null, 0, 1, TclProblem.class, !IS_TRANSIENT, //$NON-NLS-1$
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //AstPackageImpl
