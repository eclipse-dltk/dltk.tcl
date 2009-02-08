/**
 * <copyright>
 * </copyright>
 *
 * $Id: ConfigsPackage.java,v 1.1 2009/02/05 18:41:37 apanchenk Exp $
 */
package org.eclipse.dltk.tcl.tclchecker.model.configs;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.dltk.tcl.tclchecker.model.configs.ConfigsFactory
 * @model kind="package"
 * @generated
 */
public interface ConfigsPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "configs"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/dltk/tcl/tclchecker/configs"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "configs"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ConfigsPackage eINSTANCE = org.eclipse.dltk.tcl.tclchecker.model.configs.impl.ConfigsPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.dltk.tcl.tclchecker.model.configs.impl.ConfigInstanceImpl <em>Config Instance</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.dltk.tcl.tclchecker.model.configs.impl.ConfigInstanceImpl
	 * @see org.eclipse.dltk.tcl.tclchecker.model.configs.impl.ConfigsPackageImpl#getConfigInstance()
	 * @generated
	 */
	int CONFIG_INSTANCE = 0;

	/**
	 * The feature id for the '<em><b>Summary</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIG_INSTANCE__SUMMARY = 0;

	/**
	 * The feature id for the '<em><b>Command Line Options</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIG_INSTANCE__COMMAND_LINE_OPTIONS = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIG_INSTANCE__NAME = 2;

	/**
	 * The feature id for the '<em><b>Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIG_INSTANCE__MODE = 3;

	/**
	 * The feature id for the '<em><b>Message States</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIG_INSTANCE__MESSAGE_STATES = 4;

	/**
	 * The number of structural features of the '<em>Config Instance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIG_INSTANCE_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link org.eclipse.dltk.tcl.tclchecker.model.configs.impl.MessageStateMapImpl <em>Message State Map</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.dltk.tcl.tclchecker.model.configs.impl.MessageStateMapImpl
	 * @see org.eclipse.dltk.tcl.tclchecker.model.configs.impl.ConfigsPackageImpl#getMessageStateMap()
	 * @generated
	 */
	int MESSAGE_STATE_MAP = 1;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MESSAGE_STATE_MAP__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MESSAGE_STATE_MAP__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Message State Map</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MESSAGE_STATE_MAP_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.dltk.tcl.tclchecker.model.configs.impl.CheckerInstanceImpl <em>Checker Instance</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.dltk.tcl.tclchecker.model.configs.impl.CheckerInstanceImpl
	 * @see org.eclipse.dltk.tcl.tclchecker.model.configs.impl.ConfigsPackageImpl#getCheckerInstance()
	 * @generated
	 */
	int CHECKER_INSTANCE = 2;

	/**
	 * The feature id for the '<em><b>Environment Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHECKER_INSTANCE__ENVIRONMENT_ID = 0;

	/**
	 * The feature id for the '<em><b>Executable Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHECKER_INSTANCE__EXECUTABLE_PATH = 1;

	/**
	 * The feature id for the '<em><b>Pcx Files</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHECKER_INSTANCE__PCX_FILES = 2;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHECKER_INSTANCE__VERSION = 3;

	/**
	 * The feature id for the '<em><b>Use Pcx Files</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHECKER_INSTANCE__USE_PCX_FILES = 4;

	/**
	 * The feature id for the '<em><b>Command Line Options</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHECKER_INSTANCE__COMMAND_LINE_OPTIONS = 5;

	/**
	 * The number of structural features of the '<em>Checker Instance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHECKER_INSTANCE_FEATURE_COUNT = 6;

	/**
	 * The meta object id for the '{@link org.eclipse.dltk.tcl.tclchecker.model.configs.CheckerMode <em>Checker Mode</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.dltk.tcl.tclchecker.model.configs.CheckerMode
	 * @see org.eclipse.dltk.tcl.tclchecker.model.configs.impl.ConfigsPackageImpl#getCheckerMode()
	 * @generated
	 */
	int CHECKER_MODE = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.dltk.tcl.tclchecker.model.configs.MessageState <em>Message State</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.dltk.tcl.tclchecker.model.configs.MessageState
	 * @see org.eclipse.dltk.tcl.tclchecker.model.configs.impl.ConfigsPackageImpl#getMessageState()
	 * @generated
	 */
	int MESSAGE_STATE = 4;

	/**
	 * The meta object id for the '{@link org.eclipse.dltk.tcl.tclchecker.model.configs.CheckerVersion <em>Checker Version</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.dltk.tcl.tclchecker.model.configs.CheckerVersion
	 * @see org.eclipse.dltk.tcl.tclchecker.model.configs.impl.ConfigsPackageImpl#getCheckerVersion()
	 * @generated
	 */
	int CHECKER_VERSION = 5;


	/**
	 * Returns the meta object for class '{@link org.eclipse.dltk.tcl.tclchecker.model.configs.ConfigInstance <em>Config Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Config Instance</em>'.
	 * @see org.eclipse.dltk.tcl.tclchecker.model.configs.ConfigInstance
	 * @generated
	 */
	EClass getConfigInstance();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.dltk.tcl.tclchecker.model.configs.ConfigInstance#isSummary <em>Summary</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Summary</em>'.
	 * @see org.eclipse.dltk.tcl.tclchecker.model.configs.ConfigInstance#isSummary()
	 * @see #getConfigInstance()
	 * @generated
	 */
	EAttribute getConfigInstance_Summary();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.dltk.tcl.tclchecker.model.configs.ConfigInstance#getCommandLineOptions <em>Command Line Options</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Command Line Options</em>'.
	 * @see org.eclipse.dltk.tcl.tclchecker.model.configs.ConfigInstance#getCommandLineOptions()
	 * @see #getConfigInstance()
	 * @generated
	 */
	EAttribute getConfigInstance_CommandLineOptions();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.dltk.tcl.tclchecker.model.configs.ConfigInstance#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.dltk.tcl.tclchecker.model.configs.ConfigInstance#getName()
	 * @see #getConfigInstance()
	 * @generated
	 */
	EAttribute getConfigInstance_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.dltk.tcl.tclchecker.model.configs.ConfigInstance#getMode <em>Mode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mode</em>'.
	 * @see org.eclipse.dltk.tcl.tclchecker.model.configs.ConfigInstance#getMode()
	 * @see #getConfigInstance()
	 * @generated
	 */
	EAttribute getConfigInstance_Mode();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.dltk.tcl.tclchecker.model.configs.ConfigInstance#getMessageStates <em>Message States</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Message States</em>'.
	 * @see org.eclipse.dltk.tcl.tclchecker.model.configs.ConfigInstance#getMessageStates()
	 * @see #getConfigInstance()
	 * @generated
	 */
	EReference getConfigInstance_MessageStates();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>Message State Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Message State Map</em>'.
	 * @see java.util.Map.Entry
	 * @model keyDataType="org.eclipse.emf.ecore.EString"
	 *        valueDataType="org.eclipse.dltk.tcl.tclchecker.model.configs.MessageState"
	 * @generated
	 */
	EClass getMessageStateMap();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getMessageStateMap()
	 * @generated
	 */
	EAttribute getMessageStateMap_Key();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getMessageStateMap()
	 * @generated
	 */
	EAttribute getMessageStateMap_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.dltk.tcl.tclchecker.model.configs.CheckerInstance <em>Checker Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Checker Instance</em>'.
	 * @see org.eclipse.dltk.tcl.tclchecker.model.configs.CheckerInstance
	 * @generated
	 */
	EClass getCheckerInstance();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.dltk.tcl.tclchecker.model.configs.CheckerInstance#getEnvironmentId <em>Environment Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Environment Id</em>'.
	 * @see org.eclipse.dltk.tcl.tclchecker.model.configs.CheckerInstance#getEnvironmentId()
	 * @see #getCheckerInstance()
	 * @generated
	 */
	EAttribute getCheckerInstance_EnvironmentId();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.dltk.tcl.tclchecker.model.configs.CheckerInstance#getExecutablePath <em>Executable Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Executable Path</em>'.
	 * @see org.eclipse.dltk.tcl.tclchecker.model.configs.CheckerInstance#getExecutablePath()
	 * @see #getCheckerInstance()
	 * @generated
	 */
	EAttribute getCheckerInstance_ExecutablePath();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.dltk.tcl.tclchecker.model.configs.CheckerInstance#getPcxFiles <em>Pcx Files</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Pcx Files</em>'.
	 * @see org.eclipse.dltk.tcl.tclchecker.model.configs.CheckerInstance#getPcxFiles()
	 * @see #getCheckerInstance()
	 * @generated
	 */
	EAttribute getCheckerInstance_PcxFiles();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.dltk.tcl.tclchecker.model.configs.CheckerInstance#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see org.eclipse.dltk.tcl.tclchecker.model.configs.CheckerInstance#getVersion()
	 * @see #getCheckerInstance()
	 * @generated
	 */
	EAttribute getCheckerInstance_Version();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.dltk.tcl.tclchecker.model.configs.CheckerInstance#isUsePcxFiles <em>Use Pcx Files</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Use Pcx Files</em>'.
	 * @see org.eclipse.dltk.tcl.tclchecker.model.configs.CheckerInstance#isUsePcxFiles()
	 * @see #getCheckerInstance()
	 * @generated
	 */
	EAttribute getCheckerInstance_UsePcxFiles();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.dltk.tcl.tclchecker.model.configs.CheckerInstance#getCommandLineOptions <em>Command Line Options</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Command Line Options</em>'.
	 * @see org.eclipse.dltk.tcl.tclchecker.model.configs.CheckerInstance#getCommandLineOptions()
	 * @see #getCheckerInstance()
	 * @generated
	 */
	EAttribute getCheckerInstance_CommandLineOptions();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.dltk.tcl.tclchecker.model.configs.CheckerMode <em>Checker Mode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Checker Mode</em>'.
	 * @see org.eclipse.dltk.tcl.tclchecker.model.configs.CheckerMode
	 * @generated
	 */
	EEnum getCheckerMode();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.dltk.tcl.tclchecker.model.configs.MessageState <em>Message State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Message State</em>'.
	 * @see org.eclipse.dltk.tcl.tclchecker.model.configs.MessageState
	 * @generated
	 */
	EEnum getMessageState();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.dltk.tcl.tclchecker.model.configs.CheckerVersion <em>Checker Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Checker Version</em>'.
	 * @see org.eclipse.dltk.tcl.tclchecker.model.configs.CheckerVersion
	 * @generated
	 */
	EEnum getCheckerVersion();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ConfigsFactory getConfigsFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.dltk.tcl.tclchecker.model.configs.impl.ConfigInstanceImpl <em>Config Instance</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.dltk.tcl.tclchecker.model.configs.impl.ConfigInstanceImpl
		 * @see org.eclipse.dltk.tcl.tclchecker.model.configs.impl.ConfigsPackageImpl#getConfigInstance()
		 * @generated
		 */
		EClass CONFIG_INSTANCE = eINSTANCE.getConfigInstance();

		/**
		 * The meta object literal for the '<em><b>Summary</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFIG_INSTANCE__SUMMARY = eINSTANCE.getConfigInstance_Summary();

		/**
		 * The meta object literal for the '<em><b>Command Line Options</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFIG_INSTANCE__COMMAND_LINE_OPTIONS = eINSTANCE.getConfigInstance_CommandLineOptions();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFIG_INSTANCE__NAME = eINSTANCE.getConfigInstance_Name();

		/**
		 * The meta object literal for the '<em><b>Mode</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFIG_INSTANCE__MODE = eINSTANCE.getConfigInstance_Mode();

		/**
		 * The meta object literal for the '<em><b>Message States</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONFIG_INSTANCE__MESSAGE_STATES = eINSTANCE.getConfigInstance_MessageStates();

		/**
		 * The meta object literal for the '{@link org.eclipse.dltk.tcl.tclchecker.model.configs.impl.MessageStateMapImpl <em>Message State Map</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.dltk.tcl.tclchecker.model.configs.impl.MessageStateMapImpl
		 * @see org.eclipse.dltk.tcl.tclchecker.model.configs.impl.ConfigsPackageImpl#getMessageStateMap()
		 * @generated
		 */
		EClass MESSAGE_STATE_MAP = eINSTANCE.getMessageStateMap();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MESSAGE_STATE_MAP__KEY = eINSTANCE.getMessageStateMap_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MESSAGE_STATE_MAP__VALUE = eINSTANCE.getMessageStateMap_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.dltk.tcl.tclchecker.model.configs.impl.CheckerInstanceImpl <em>Checker Instance</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.dltk.tcl.tclchecker.model.configs.impl.CheckerInstanceImpl
		 * @see org.eclipse.dltk.tcl.tclchecker.model.configs.impl.ConfigsPackageImpl#getCheckerInstance()
		 * @generated
		 */
		EClass CHECKER_INSTANCE = eINSTANCE.getCheckerInstance();

		/**
		 * The meta object literal for the '<em><b>Environment Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHECKER_INSTANCE__ENVIRONMENT_ID = eINSTANCE.getCheckerInstance_EnvironmentId();

		/**
		 * The meta object literal for the '<em><b>Executable Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHECKER_INSTANCE__EXECUTABLE_PATH = eINSTANCE.getCheckerInstance_ExecutablePath();

		/**
		 * The meta object literal for the '<em><b>Pcx Files</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHECKER_INSTANCE__PCX_FILES = eINSTANCE.getCheckerInstance_PcxFiles();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHECKER_INSTANCE__VERSION = eINSTANCE.getCheckerInstance_Version();

		/**
		 * The meta object literal for the '<em><b>Use Pcx Files</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHECKER_INSTANCE__USE_PCX_FILES = eINSTANCE.getCheckerInstance_UsePcxFiles();

		/**
		 * The meta object literal for the '<em><b>Command Line Options</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHECKER_INSTANCE__COMMAND_LINE_OPTIONS = eINSTANCE.getCheckerInstance_CommandLineOptions();

		/**
		 * The meta object literal for the '{@link org.eclipse.dltk.tcl.tclchecker.model.configs.CheckerMode <em>Checker Mode</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.dltk.tcl.tclchecker.model.configs.CheckerMode
		 * @see org.eclipse.dltk.tcl.tclchecker.model.configs.impl.ConfigsPackageImpl#getCheckerMode()
		 * @generated
		 */
		EEnum CHECKER_MODE = eINSTANCE.getCheckerMode();

		/**
		 * The meta object literal for the '{@link org.eclipse.dltk.tcl.tclchecker.model.configs.MessageState <em>Message State</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.dltk.tcl.tclchecker.model.configs.MessageState
		 * @see org.eclipse.dltk.tcl.tclchecker.model.configs.impl.ConfigsPackageImpl#getMessageState()
		 * @generated
		 */
		EEnum MESSAGE_STATE = eINSTANCE.getMessageState();

		/**
		 * The meta object literal for the '{@link org.eclipse.dltk.tcl.tclchecker.model.configs.CheckerVersion <em>Checker Version</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.dltk.tcl.tclchecker.model.configs.CheckerVersion
		 * @see org.eclipse.dltk.tcl.tclchecker.model.configs.impl.ConfigsPackageImpl#getCheckerVersion()
		 * @generated
		 */
		EEnum CHECKER_VERSION = eINSTANCE.getCheckerVersion();

	}

} //ConfigsPackage