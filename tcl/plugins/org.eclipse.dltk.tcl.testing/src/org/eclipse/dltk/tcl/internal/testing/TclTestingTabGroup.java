package org.eclipse.dltk.tcl.internal.testing;

import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.EnvironmentTab;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.dltk.debug.ui.launchConfigurations.ScriptArgumentsTab;
import org.eclipse.dltk.tcl.internal.debug.ui.interpreters.TclInterpreterTab;

public class TclTestingTabGroup extends AbstractLaunchConfigurationTabGroup {

	@Override
	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
		TclTestingMainLaunchConfigurationTab main = new TclTestingMainLaunchConfigurationTab(mode);
		ILaunchConfigurationTab[] tabs = new ILaunchConfigurationTab[] { main, new ScriptArgumentsTab(),
				new TclInterpreterTab(main), new EnvironmentTab(), new CommonTab() {
					@Override
					public void performApply(ILaunchConfigurationWorkingCopy configuration) {
						super.performApply(configuration);
						configuration.setAttribute(IDebugUIConstants.ATTR_CAPTURE_IN_CONSOLE, (String) null);
					}
				} };
		setTabs(tabs);
	}

}
