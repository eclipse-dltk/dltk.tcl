/**
 * <copyright>
 * </copyright>
 *
 * $Id: CheckerConfigImpl.java,v 1.3 2009/02/27 09:16:01 apanchenk Exp $
 */
package org.eclipse.dltk.tcl.tclchecker.model.configs.impl;

import org.eclipse.dltk.tcl.tclchecker.model.configs.CheckerConfig;
import org.eclipse.dltk.tcl.tclchecker.model.configs.CheckerMode;
import org.eclipse.dltk.tcl.tclchecker.model.configs.ConfigsPackage;
import org.eclipse.dltk.tcl.tclchecker.model.configs.MessageState;
import org.eclipse.dltk.validators.configs.impl.ValidatorConfigImpl;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Checker Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.dltk.tcl.tclchecker.model.configs.impl.CheckerConfigImpl#isSummary <em>Summary</em>}</li>
 *   <li>{@link org.eclipse.dltk.tcl.tclchecker.model.configs.impl.CheckerConfigImpl#getMode <em>Mode</em>}</li>
 *   <li>{@link org.eclipse.dltk.tcl.tclchecker.model.configs.impl.CheckerConfigImpl#getMessageStates <em>Message States</em>}</li>
 *   <li>{@link org.eclipse.dltk.tcl.tclchecker.model.configs.impl.CheckerConfigImpl#isUseTclVer <em>Use Tcl Ver</em>}</li>
 *   <li>{@link org.eclipse.dltk.tcl.tclchecker.model.configs.impl.CheckerConfigImpl#isIndividualMessageStates <em>Individual Message States</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CheckerConfigImpl extends ValidatorConfigImpl implements CheckerConfig {
	/**
	 * The default value of the '{@link #isSummary() <em>Summary</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSummary()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SUMMARY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isSummary() <em>Summary</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSummary()
	 * @generated
	 * @ordered
	 */
	protected boolean summary = SUMMARY_EDEFAULT;

	/**
	 * The default value of the '{@link #getMode() <em>Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMode()
	 * @generated
	 * @ordered
	 */
	protected static final CheckerMode MODE_EDEFAULT = CheckerMode.DEFAULT;

	/**
	 * The cached value of the '{@link #getMode() <em>Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMode()
	 * @generated
	 * @ordered
	 */
	protected CheckerMode mode = MODE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getMessageStates() <em>Message States</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMessageStates()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, MessageState> messageStates;

	/**
	 * The default value of the '{@link #isUseTclVer() <em>Use Tcl Ver</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUseTclVer()
	 * @generated
	 * @ordered
	 */
	protected static final boolean USE_TCL_VER_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isUseTclVer() <em>Use Tcl Ver</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUseTclVer()
	 * @generated
	 * @ordered
	 */
	protected boolean useTclVer = USE_TCL_VER_EDEFAULT;

	/**
	 * The default value of the '{@link #isIndividualMessageStates() <em>Individual Message States</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIndividualMessageStates()
	 * @generated
	 * @ordered
	 */
	protected static final boolean INDIVIDUAL_MESSAGE_STATES_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIndividualMessageStates() <em>Individual Message States</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIndividualMessageStates()
	 * @generated
	 * @ordered
	 */
	protected boolean individualMessageStates = INDIVIDUAL_MESSAGE_STATES_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CheckerConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ConfigsPackage.Literals.CHECKER_CONFIG;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isSummary() {
		return summary;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSummary(boolean newSummary) {
		boolean oldSummary = summary;
		summary = newSummary;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigsPackage.CHECKER_CONFIG__SUMMARY, oldSummary, summary));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CheckerMode getMode() {
		return mode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMode(CheckerMode newMode) {
		CheckerMode oldMode = mode;
		mode = newMode == null ? MODE_EDEFAULT : newMode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigsPackage.CHECKER_CONFIG__MODE, oldMode, mode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EMap<String, MessageState> getMessageStates() {
		if (messageStates == null) {
			messageStates = new EcoreEMap<String,MessageState>(ConfigsPackage.Literals.MESSAGE_STATE_MAP, MessageStateMapImpl.class, this, ConfigsPackage.CHECKER_CONFIG__MESSAGE_STATES);
		}
		return messageStates;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isUseTclVer() {
		return useTclVer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setUseTclVer(boolean newUseTclVer) {
		boolean oldUseTclVer = useTclVer;
		useTclVer = newUseTclVer;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigsPackage.CHECKER_CONFIG__USE_TCL_VER, oldUseTclVer, useTclVer));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isIndividualMessageStates() {
		return individualMessageStates;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIndividualMessageStates(boolean newIndividualMessageStates) {
		boolean oldIndividualMessageStates = individualMessageStates;
		individualMessageStates = newIndividualMessageStates;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigsPackage.CHECKER_CONFIG__INDIVIDUAL_MESSAGE_STATES, oldIndividualMessageStates, individualMessageStates));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ConfigsPackage.CHECKER_CONFIG__MESSAGE_STATES:
				return ((InternalEList<?>)getMessageStates()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ConfigsPackage.CHECKER_CONFIG__SUMMARY:
				return isSummary();
			case ConfigsPackage.CHECKER_CONFIG__MODE:
				return getMode();
			case ConfigsPackage.CHECKER_CONFIG__MESSAGE_STATES:
				if (coreType) return getMessageStates();
				else return getMessageStates().map();
			case ConfigsPackage.CHECKER_CONFIG__USE_TCL_VER:
				return isUseTclVer();
			case ConfigsPackage.CHECKER_CONFIG__INDIVIDUAL_MESSAGE_STATES:
				return isIndividualMessageStates();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ConfigsPackage.CHECKER_CONFIG__SUMMARY:
				setSummary((Boolean)newValue);
				return;
			case ConfigsPackage.CHECKER_CONFIG__MODE:
				setMode((CheckerMode)newValue);
				return;
			case ConfigsPackage.CHECKER_CONFIG__MESSAGE_STATES:
				((EStructuralFeature.Setting)getMessageStates()).set(newValue);
				return;
			case ConfigsPackage.CHECKER_CONFIG__USE_TCL_VER:
				setUseTclVer((Boolean)newValue);
				return;
			case ConfigsPackage.CHECKER_CONFIG__INDIVIDUAL_MESSAGE_STATES:
				setIndividualMessageStates((Boolean)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ConfigsPackage.CHECKER_CONFIG__SUMMARY:
				setSummary(SUMMARY_EDEFAULT);
				return;
			case ConfigsPackage.CHECKER_CONFIG__MODE:
				setMode(MODE_EDEFAULT);
				return;
			case ConfigsPackage.CHECKER_CONFIG__MESSAGE_STATES:
				getMessageStates().clear();
				return;
			case ConfigsPackage.CHECKER_CONFIG__USE_TCL_VER:
				setUseTclVer(USE_TCL_VER_EDEFAULT);
				return;
			case ConfigsPackage.CHECKER_CONFIG__INDIVIDUAL_MESSAGE_STATES:
				setIndividualMessageStates(INDIVIDUAL_MESSAGE_STATES_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ConfigsPackage.CHECKER_CONFIG__SUMMARY:
				return summary != SUMMARY_EDEFAULT;
			case ConfigsPackage.CHECKER_CONFIG__MODE:
				return mode != MODE_EDEFAULT;
			case ConfigsPackage.CHECKER_CONFIG__MESSAGE_STATES:
				return messageStates != null && !messageStates.isEmpty();
			case ConfigsPackage.CHECKER_CONFIG__USE_TCL_VER:
				return useTclVer != USE_TCL_VER_EDEFAULT;
			case ConfigsPackage.CHECKER_CONFIG__INDIVIDUAL_MESSAGE_STATES:
				return individualMessageStates != INDIVIDUAL_MESSAGE_STATES_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (summary: "); //$NON-NLS-1$
		result.append(summary);
		result.append(", mode: "); //$NON-NLS-1$
		result.append(mode);
		result.append(", useTclVer: "); //$NON-NLS-1$
		result.append(useTclVer);
		result.append(", individualMessageStates: "); //$NON-NLS-1$
		result.append(individualMessageStates);
		result.append(')');
		return result.toString();
	}

} //CheckerConfigImpl
