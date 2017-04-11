/**
 * <copyright>
 * </copyright>
 *
 * $Id: ManpagesPackageImpl.java,v 1.1 2009/12/31 09:18:23 apanchenk Exp $
 */
package org.eclipse.dltk.tcl.ui.manpages.impl;

import java.util.Map;

import org.eclipse.dltk.tcl.ui.manpages.Documentation;
import org.eclipse.dltk.tcl.ui.manpages.InterpreterDocumentation;
import org.eclipse.dltk.tcl.ui.manpages.ManPageFolder;
import org.eclipse.dltk.tcl.ui.manpages.ManpagesFactory;
import org.eclipse.dltk.tcl.ui.manpages.ManpagesPackage;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!--
 * end-user-doc -->
 * 
 * @generated
 */
public class ManpagesPackageImpl extends EPackageImpl
		implements ManpagesPackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass documentationEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass manPageFolderEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass stringToStringEntryEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass interpreterDocumentationEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the
	 * package package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static factory
	 * method {@link #init init()}, which also performs initialization of the
	 * package, or returns the registered package, if one already exists. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.dltk.tcl.ui.manpages.ManpagesPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ManpagesPackageImpl() {
		super(eNS_URI, ManpagesFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model,
	 * and for any others upon which it depends.
	 *
	 * <p>
	 * This method is used to initialize {@link ManpagesPackage#eINSTANCE} when
	 * that field is accessed. Clients should not invoke it directly. Instead,
	 * they should simply access that field to obtain the package. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ManpagesPackage init() {
		if (isInited)
			return (ManpagesPackage) EPackage.Registry.INSTANCE
					.getEPackage(ManpagesPackage.eNS_URI);

		// Obtain or create and register package
		ManpagesPackageImpl theManpagesPackage = (ManpagesPackageImpl) (EPackage.Registry.INSTANCE
				.get(eNS_URI) instanceof ManpagesPackageImpl
						? EPackage.Registry.INSTANCE.get(eNS_URI)
						: new ManpagesPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theManpagesPackage.createPackageContents();

		// Initialize created meta-data
		theManpagesPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theManpagesPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ManpagesPackage.eNS_URI,
				theManpagesPackage);
		return theManpagesPackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getDocumentation() {
		return documentationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getDocumentation_Name() {
		return (EAttribute) documentationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getDocumentation_Folders() {
		return (EReference) documentationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getDocumentation_Default() {
		return (EAttribute) documentationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getDocumentation_Id() {
		return (EAttribute) documentationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getManPageFolder() {
		return manPageFolderEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getManPageFolder_Path() {
		return (EAttribute) manPageFolderEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getManPageFolder_Keywords() {
		return (EReference) manPageFolderEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getStringToStringEntry() {
		return stringToStringEntryEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getStringToStringEntry_Key() {
		return (EAttribute) stringToStringEntryEClass.getEStructuralFeatures()
				.get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getStringToStringEntry_Value() {
		return (EAttribute) stringToStringEntryEClass.getEStructuralFeatures()
				.get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getInterpreterDocumentation() {
		return interpreterDocumentationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getInterpreterDocumentation_DocumentationId() {
		return (EAttribute) interpreterDocumentationEClass
				.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ManpagesFactory getManpagesFactory() {
		return (ManpagesFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package. This method is guarded to
	 * have no affect on any invocation but its first. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		documentationEClass = createEClass(DOCUMENTATION);
		createEAttribute(documentationEClass, DOCUMENTATION__NAME);
		createEReference(documentationEClass, DOCUMENTATION__FOLDERS);
		createEAttribute(documentationEClass, DOCUMENTATION__DEFAULT);
		createEAttribute(documentationEClass, DOCUMENTATION__ID);

		manPageFolderEClass = createEClass(MAN_PAGE_FOLDER);
		createEAttribute(manPageFolderEClass, MAN_PAGE_FOLDER__PATH);
		createEReference(manPageFolderEClass, MAN_PAGE_FOLDER__KEYWORDS);

		stringToStringEntryEClass = createEClass(STRING_TO_STRING_ENTRY);
		createEAttribute(stringToStringEntryEClass,
				STRING_TO_STRING_ENTRY__KEY);
		createEAttribute(stringToStringEntryEClass,
				STRING_TO_STRING_ENTRY__VALUE);

		interpreterDocumentationEClass = createEClass(
				INTERPRETER_DOCUMENTATION);
		createEAttribute(interpreterDocumentationEClass,
				INTERPRETER_DOCUMENTATION__DOCUMENTATION_ID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model. This
	 * method is guarded to have no affect on any invocation but its first. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
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

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(documentationEClass, Documentation.class, "Documentation", //$NON-NLS-1$
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDocumentation_Name(), ecorePackage.getEString(),
				"name", null, 0, 1, Documentation.class, !IS_TRANSIENT, //$NON-NLS-1$
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentation_Folders(), this.getManPageFolder(),
				null, "folders", null, 0, -1, Documentation.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getDocumentation_Default(), ecorePackage.getEBoolean(),
				"default", null, 0, 1, Documentation.class, !IS_TRANSIENT, //$NON-NLS-1$
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentation_Id(), ecorePackage.getEString(), "id", //$NON-NLS-1$
				null, 0, 1, Documentation.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		EOperation op = addEOperation(documentationEClass,
				this.getManPageFolder(), "findFolder", 0, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "path", 0, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);

		initEClass(manPageFolderEClass, ManPageFolder.class, "ManPageFolder", //$NON-NLS-1$
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getManPageFolder_Path(), ecorePackage.getEString(),
				"path", null, 0, 1, ManPageFolder.class, !IS_TRANSIENT, //$NON-NLS-1$
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getManPageFolder_Keywords(),
				this.getStringToStringEntry(), null, "keywords", null, 0, -1, //$NON-NLS-1$
				ManPageFolder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		op = addEOperation(manPageFolderEClass, null, "addPage", 0, 1, //$NON-NLS-1$
				IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "keyword", 0, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "file", 0, 1, IS_UNIQUE, //$NON-NLS-1$
				IS_ORDERED);

		addEOperation(manPageFolderEClass, ecorePackage.getEBoolean(), "verify", //$NON-NLS-1$
				0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(stringToStringEntryEClass, Map.Entry.class,
				"StringToStringEntry", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				!IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStringToStringEntry_Key(), ecorePackage.getEString(),
				"key", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, //$NON-NLS-1$
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getStringToStringEntry_Value(),
				ecorePackage.getEString(), "value", null, 0, 1, Map.Entry.class, //$NON-NLS-1$
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(interpreterDocumentationEClass,
				InterpreterDocumentation.class, "InterpreterDocumentation", //$NON-NLS-1$
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getInterpreterDocumentation_DocumentationId(),
				ecorePackage.getEString(), "documentationId", null, 0, 1, //$NON-NLS-1$
				InterpreterDocumentation.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} // ManpagesPackageImpl
