package org.eclipse.dltk.itcl.internal.core;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.dltk.itcl.internal.core.classes.IncrTclClassesManager;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class IncrTCLPlugin extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.dltk.itcl.core";

	// The shared instance
	private static IncrTCLPlugin plugin;

	/**
	 * The constructor
	 */
	public IncrTCLPlugin() {
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		IncrTclClassesManager.getDefault().startup();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		IncrTclClassesManager.getDefault().shutdown();
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static IncrTCLPlugin getDefault() {
		return plugin;
	}

}
