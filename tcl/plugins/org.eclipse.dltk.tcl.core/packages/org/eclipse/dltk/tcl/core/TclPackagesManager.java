package org.eclipse.dltk.tcl.core;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.ProgressMonitoringJob;
import org.eclipse.dltk.core.RuntimePerformanceMonitor;
import org.eclipse.dltk.core.RuntimePerformanceMonitor.PerformanceNode;
import org.eclipse.dltk.core.environment.IDeployment;
import org.eclipse.dltk.core.environment.IEnvironment;
import org.eclipse.dltk.core.environment.IExecutionEnvironment;
import org.eclipse.dltk.core.environment.IFileHandle;
import org.eclipse.dltk.launching.EnvironmentVariable;
import org.eclipse.dltk.launching.IInterpreterInstall;
import org.eclipse.dltk.launching.IInterpreterInstallChangedListener;
import org.eclipse.dltk.launching.InterpreterConfig;
import org.eclipse.dltk.launching.InterpreterStandin;
import org.eclipse.dltk.launching.PropertyChangeEvent;
import org.eclipse.dltk.launching.ScriptLaunchUtil;
import org.eclipse.dltk.launching.ScriptRuntime;
import org.eclipse.dltk.tcl.core.packages.TclInterpreterInfo;
import org.eclipse.dltk.tcl.core.packages.TclModuleInfo;
import org.eclipse.dltk.tcl.core.packages.TclPackageInfo;
import org.eclipse.dltk.tcl.core.packages.TclPackagesFactory;
import org.eclipse.dltk.tcl.core.packages.TclPackagesPackage;
import org.eclipse.dltk.tcl.core.packages.TclProjectInfo;
import org.eclipse.dltk.tcl.core.packages.VariableMap;
import org.eclipse.dltk.tcl.core.packages.VariableValue;
import org.eclipse.dltk.tcl.internal.core.packages.ProcessOutputCollector;
import org.eclipse.emf.common.util.BasicEMap;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.osgi.util.NLS;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class TclPackagesManager {
	private static final String DLTK_TCL = "scripts/dltk.tcl"; //$NON-NLS-1$
	public static final String END_OF_STREAM = "DLTK-TCL-HELPER-9E7A168E-5EEF-4a46-A86D-0C82E90686E4-END-OF-STREAM"; //$NON-NLS-1$
	private static final String PKG_VERSION = "v20090505"; //$NON-NLS-1$

	private static Resource infos = null;
	private static final Map<String, Resource> projectInfos = new HashMap<String, Resource>();

	private static IInterpreterInstallChangedListener installChangedListener = null;
	private static Set<IInterpreterInstall> packageFetchingSet = new HashSet<IInterpreterInstall>();
	private static Set<IInterpreterInstall> sourcesFetchingSet = new HashSet<IInterpreterInstall>();

	public static List<TclPackageInfo> getPackageInfos(
			IInterpreterInstall install, Set<String> packageNames,
			boolean fetchIfRequired) {
		initialize();
		TclInterpreterInfo interpreterInfo = getTclInterpreter(install);
		List<TclPackageInfo> collection;
		synchronized (TclPackagesManager.class) {
			collection = Collections
					.unmodifiableList(getPackagesForInterpreter(packageNames,
							fetchIfRequired, interpreterInfo, install));
		}
		return collection;
	}

	public static List<TclPackageInfo> getPackageInfos(
			IInterpreterInstall install) {
		initialize();
		TclInterpreterInfo interpreterInfo = getTclInterpreter(install);
		List<TclPackageInfo> collection = null;
		synchronized (TclPackagesManager.class) {
			collection = Collections
					.unmodifiableList(new ArrayList<TclPackageInfo>(
							interpreterInfo.getPackages()));
		}
		return collection;
	}

	public static TclPackageInfo getPackageInfo(IInterpreterInstall install,
			String name, boolean fetchIfRequired) {
		initialize();
		TclInterpreterInfo interpreterInfo = getTclInterpreter(install);
		EList<TclPackageInfo> packages = interpreterInfo.getPackages();
		Set<TclPackageInfo> toFetch = new HashSet<TclPackageInfo>();
		TclPackageInfo result = null;

		synchronized (TclPackagesManager.class) {
			for (TclPackageInfo tclPackageInfo : packages) {
				if (name.equals(tclPackageInfo.getName())) {
					if (tclPackageInfo.isFetched() || !fetchIfRequired) {
						return tclPackageInfo;
					} else {
						toFetch.add(tclPackageInfo);
						fetchSources(toFetch, install, interpreterInfo, null);
						result = tclPackageInfo;
						break;
					}
				}
			}
		}
		return result;
	}

	public static Set<TclPackageInfo> getDependencies(
			IInterpreterInstall install, String name, boolean fetchIfRequired) {
		initialize();
		TclPackageInfo info = getPackageInfo(install, name, fetchIfRequired);
		if (info != null) {
			Set<TclPackageInfo> result = new HashSet<TclPackageInfo>();
			Set<TclPackageInfo> toFetch = new HashSet<TclPackageInfo>();
			processPackage(info, result, toFetch, fetchIfRequired);
			if (toFetch.size() > 0) {
				TclInterpreterInfo interpreter = getTclInterpreter(install);
				fetchSources(toFetch, install, interpreter, null);
				processPackage(info, result, toFetch, fetchIfRequired);
			}
			result.remove(info);
			return result;
		}
		return null;
	}

	private static TclInterpreterInfo getTclInterpreter(
			IInterpreterInstall install) {
		return getTclInterpreter(install, true);
	}

	/**
	 * @since 2.0
	 * @param install
	 * @return
	 */
	public static long getTclInterpreterFetchDate(IInterpreterInstall install) {
		TclInterpreterInfo interpreter = getTclInterpreter(install, true);
		if (interpreter != null) {
			return interpreter.getFetchedAt().getTime();
		}
		return 0;
	}

	private static TclInterpreterInfo getTclInterpreter(
			IInterpreterInstall install, boolean allowCreate) {
		TclInterpreterInfo interpreterInfo = null;
		String interpreterLocation = install.getInstallLocation().getPath()
				.toString();
		String environmentId = install.getInstallLocation().getEnvironmentId();
		synchronized (TclPackagesManager.class) {
			for (EObject eObject : infos.getContents()) {
				if (eObject instanceof TclInterpreterInfo) {
					TclInterpreterInfo info = (TclInterpreterInfo) eObject;
					String location = info.getInstallLocation();
					String name = info.getName();
					String env = info.getEnvironment();
					if (interpreterLocation.equals(location)
							&& install.getName().equals(name) && env != null
							&& env.equals(environmentId)) {
						interpreterInfo = info;
						break;
					}
				}
			}
			if (interpreterInfo == null) {
				if (!allowCreate) {
					return null;
				}
				interpreterInfo = TclPackagesFactory.eINSTANCE
						.createTclInterpreterInfo();
				interpreterInfo.setInstallLocation(interpreterLocation);
				interpreterInfo.setName(install.getName());
				interpreterInfo.setEnvironment(environmentId);
				interpreterInfo.setFetched(false);
				infos.getContents().add(interpreterInfo);
			}
		}
		fetchPackagesForInterpreter(install, interpreterInfo);

		return interpreterInfo;
	}

	private static long getPackagesRefreshInterval(IInterpreterInstall install) {
		if (install == null) {
			return 0;
		}
		final String refreshIntervalKey;
		final IEnvironment environment = install.getEnvironment();
		if (environment != null && environment.isLocal()) {
			refreshIntervalKey = TclCorePreferences.PACKAGES_REFRESH_INTERVAL_LOCAL;
		} else {
			refreshIntervalKey = TclCorePreferences.PACKAGES_REFRESH_INTERVAL_REMOTE;
		}
		return Platform.getPreferencesService().getLong(TclPlugin.PLUGIN_ID,
				refreshIntervalKey, 15 * 60 * 1000, null);
	}

	public static TclProjectInfo getTclProject(String name) {
		Resource resource = getProjectInfoResource(name);
		synchronized (resource) {
			TclProjectInfo info = null;
			for (EObject eObject : resource.getContents()) {
				if (eObject instanceof TclProjectInfo) {
					TclProjectInfo pinfo = (TclProjectInfo) eObject;
					String pname = pinfo.getName();
					if (name != null && name.equals(pname)) {
						info = pinfo;
					}
				}
			}
			if (info == null) {
				info = TclPackagesFactory.eINSTANCE.createTclProjectInfo();
				info.setName(name);
				resource.getContents().add(info);
			}
			return info;
		}
	}

	private static void fetchPackagesForInterpreter(
			IInterpreterInstall install, TclInterpreterInfo interpreterInfo) {
		synchronized (TclPackagesManager.class) {
			while (packageFetchingSet.contains(install)) {
				// White until it will be removed from fetches list
				try {
					TclPackagesManager.class.wait(50);
				} catch (InterruptedException e) {
					TclPlugin.error(e);
				}
			}
			if (!(!interpreterInfo.isFetched()
					|| interpreterInfo.getFetchedAt() == null || interpreterInfo
					.getFetchedAt().getTime()
					+ getPackagesRefreshInterval(install) < System
					.currentTimeMillis())) {
				return; // Info already fetched and ok
			}
			packageFetchingSet.add(install);
		}
		try {
			PerformanceNode p = RuntimePerformanceMonitor.begin();
			IExecutionEnvironment exeEnv = install.getExecEnvironment();
			if (exeEnv == null) {
				return;
			}
			ProgressMonitoringJob monitor = new ProgressMonitoringJob(
					Messages.TclInterpreterMessages_RetrieveListOfAvailablePackages
							+ " " + install.getName(), 100);
			try {
				List<String> content = deployExecute(exeEnv, install,
						new String[] { "get-pkgs" }, install //$NON-NLS-1$
								.getEnvironmentVariables(), monitor);
				synchronized (TclPackagesManager.class) {
					if (content != null) {
						/* Progress monitoring */monitor
								.subTask(Messages.TclInterpreterMessages_ProcessingPackagesInfo);
						processContent(content, false, true, interpreterInfo);
						/* Progress monitoring */monitor.worked(20);
						interpreterInfo.setFetched(true);
						interpreterInfo.setFetchedAt(new Date());
						monitor
								.subTask(Messages.TclInterpreterMessages_SavePackagesInfo);
						save();
						monitor.worked(10);
					}
				}
				String msg = Messages.TclInterpreterMessages_FetchInterpreterPackagesInfo;
				p.done("Tcl", msg, 0); //$NON-NLS-1$
			} finally {
				monitor.done();
			}
		} finally {
			synchronized (TclPackagesManager.class) {
				packageFetchingSet.remove(install);
				TclPackagesManager.class.notifyAll();
			}
		}
	}

	public static void save() {
		if (infos != null) {
			try {
				infos.save(null);
			} catch (IOException e) {
				if (DLTKCore.DEBUG) {
					// TclPlugin.error(e);
				}
			}
		}
		synchronized (projectInfos) {
			for (Map.Entry<String, Resource> entry : projectInfos.entrySet()) {
				try {
					entry.getValue().save(null);
				} catch (IOException e) {
					String msg = NLS.bind(Messages.TclInterpreterMessages_6,
							entry.getKey(), e.getMessage());
					TclPlugin.error(msg, e);
				}
			}
		}
	}

	private static String getXMLContent(List<String> content) {
		StringBuffer newList = new StringBuffer();
		if (content != null) {
			for (Iterator<String> iterator = content.iterator(); iterator
					.hasNext();) {
				String line = iterator.next();
				if (line.trim().startsWith("<")) { //$NON-NLS-1$
					newList.append(line).append("\n"); //$NON-NLS-1$
				}
			}
		}
		return newList.toString();
	}

	private static Document getDocument(String text) {
		try {
			DocumentBuilder parser = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			parser.setErrorHandler(new DefaultHandler());
			InputSource source = new InputSource(new StringReader(text));
			Document document = parser.parse(source);
			return document;
		} catch (IOException e) {
			if (DLTKCore.DEBUG) {
				e.printStackTrace();
			}
		} catch (ParserConfigurationException e) {
			if (DLTKCore.DEBUG) {
				e.printStackTrace();
			}
		} catch (SAXException e) {
			if (DLTKCore.DEBUG) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private static synchronized void processContent(List<String> content,
			boolean markAsFetched, boolean purgePackages,
			TclInterpreterInfo info) {
		String text = getXMLContent(content);
		Document document = getDocument(text);

		if (document != null) {
			final Set<String> processedPackages = new HashSet<String>();
			Element element = document.getDocumentElement();
			NodeList childNodes = element.getChildNodes();
			int len = childNodes.getLength();
			for (int i = 0; i < len; i++) {
				Node nde = childNodes.item(i);
				if (isElementName(nde, "path")) { //$NON-NLS-1$
					Element el = (Element) nde;
					NodeList elChilds = el.getChildNodes();
					for (int j = 0; j < elChilds.getLength(); j++) {
						Node pkgNde = elChilds.item(j);
						if (isElementName(pkgNde, "package")) { //$NON-NLS-1$
							Element pkgElement = (Element) pkgNde;
							String name = pkgElement.getAttribute("name"); //$NON-NLS-1$
							processedPackages.add(name);
							TclPackageInfo pkg = getCreatePackage(info, name);
							if (markAsFetched) {
								pkg.setFetched(markAsFetched);
							}
							populatePackage(pkg, pkgNde, info);
						}
					}
				}
			}
			if (purgePackages) {
				for (Iterator<TclPackageInfo> i = info.getPackages().iterator(); i
						.hasNext();) {
					TclPackageInfo packageInfo = i.next();
					if (!processedPackages.contains(packageInfo.getName())) {
						i.remove();
					}
				}
			}
		}
	}

	private static synchronized TclPackageInfo getCreatePackage(
			TclInterpreterInfo info, String name) {
		TclPackageInfo packageInfo = null;
		for (TclPackageInfo pkgInfo : info.getPackages()) {
			if (pkgInfo.getName().equals(name)) {
				packageInfo = pkgInfo;
				break;
			}
		}
		if (packageInfo == null) {
			packageInfo = TclPackagesFactory.eINSTANCE.createTclPackageInfo();
			packageInfo.setFetched(false);
			packageInfo.setName(name);
			info.getPackages().add(packageInfo);
		}
		return packageInfo;
	}

	private static void populatePackage(TclPackageInfo info, Node pkgNde,
			TclInterpreterInfo interpreterInfo) {
		Element pkg = (Element) pkgNde;

		info.setVersion(pkg.getAttribute("version")); //$NON-NLS-1$
		NodeList childs = pkg.getChildNodes();
		for (int i = 0; i < childs.getLength(); i++) {
			Node nde = childs.item(i);
			if (isElementName(nde, "source")) { //$NON-NLS-1$
				Element el = (Element) nde;
				String name = el.getAttribute("name"); //$NON-NLS-1$
				info.getSources().add(name);
			} else if (isElementName(nde, "require")) { //$NON-NLS-1$
				Element el = (Element) nde;
				String name = el.getAttribute("name"); //$NON-NLS-1$
				info.getDependencies().add(
						getCreatePackage(interpreterInfo, name));
			} else if (isElementName(nde, "load")) { //$NON-NLS-1$
				Element el = (Element) nde;
				String name = el.getAttribute("name"); //$NON-NLS-1$
				info.getLibraries().add(name);
			}
		}
	}

	private static List<TclPackageInfo> getPackagesForInterpreter(
			Set<String> packageName, boolean fetchIfRequired,
			TclInterpreterInfo interpreterInfo, IInterpreterInstall install) {
		Set<TclPackageInfo> result = new HashSet<TclPackageInfo>();
		Set<TclPackageInfo> toFetch = new HashSet<TclPackageInfo>();
		synchronized (TclPackagesManager.class) {
			for (TclPackageInfo tclPackageInfo : interpreterInfo.getPackages()) {
				if (packageName.contains(tclPackageInfo.getName())) {
					processPackage(tclPackageInfo, result, toFetch,
							fetchIfRequired);
				}
			}
		}
		fetchSources(toFetch, install, interpreterInfo, null);
		synchronized (TclPackagesManager.class) {
			for (TclPackageInfo tclPackageInfo : interpreterInfo.getPackages()) {
				if (packageName.contains(tclPackageInfo.getName())) {
					processPackage(tclPackageInfo, result, toFetch,
							fetchIfRequired);
				}
			}
		}
		return new ArrayList<TclPackageInfo>(result);
	}

	private static void processPackage(TclPackageInfo tclPackageInfo,
			Set<TclPackageInfo> result, Set<TclPackageInfo> toFetch,
			boolean fetchIfRequired) {
		if (tclPackageInfo.isFetched() || !fetchIfRequired) {
			result.add(tclPackageInfo);
		} else if (fetchIfRequired) {
			result.add(tclPackageInfo);
			toFetch.add(tclPackageInfo);
		}
		EList<TclPackageInfo> dependencies = tclPackageInfo.getDependencies();
		for (TclPackageInfo tclPackageInfo2 : dependencies) {
			if (!result.contains(tclPackageInfo2)) {
				processPackage(tclPackageInfo2, result, toFetch,
						fetchIfRequired);
			}
		}
	}

	private static void fetchSources(Set<TclPackageInfo> toFetch,
			IInterpreterInstall install, TclInterpreterInfo interpreterInfo,
			IProgressMonitor topMonitor) {
		synchronized (TclPackagesManager.class) {
			while (sourcesFetchingSet.contains(install)) {
				// White until it will be removed from fetches list
				try {
					TclPackagesManager.class.wait(50);
				} catch (InterruptedException e) {
					TclPlugin.error(e);
				}
			}
			// Check for consistency
			Set<TclPackageInfo> toRemove = new HashSet<TclPackageInfo>();
			for (TclPackageInfo tclPackageInfo : toFetch) {
				if (tclPackageInfo.isFetched()) {
					toRemove.add(tclPackageInfo);
				}
			}
			toFetch.removeAll(toRemove);
			if (toFetch.size() == 0) {
				return;
			}
			sourcesFetchingSet.add(install);
		}

		/* Progress monitoring */IProgressMonitor monitor = topMonitor;
		if (monitor == null) {
			monitor = new ProgressMonitoringJob(
					Messages.TclInterpreterMessages_RetrievePackageInformationSources
							+ " " + install.getName(), 100);
		}
		try {
			PerformanceNode p = RuntimePerformanceMonitor.begin();
			IExecutionEnvironment exeEnv = install.getExecEnvironment();
			if (exeEnv == null) {
				return;
			}
			String envName = install.getEnvironment().getName();
			/* Progress monitoring */monitor
					.subTask(Messages.TclInterpreterMessages_DeployingPackageInformationScript);
			IDeployment deployment = exeEnv.createDeployment();
			if (deployment == null) {
				return;
			}
			IFileHandle script = deploy(deployment);

			if (script == null) {
				deployment.dispose();
				return;
			}
			/* Progress monitoring */monitor.worked(10);

			IFileHandle workingDir = script.getParent();
			InterpreterConfig config = ScriptLaunchUtil
					.createInterpreterConfig(exeEnv, script, workingDir,
							install.getEnvironmentVariables());
			TclLibpathUtils.addTclLibPath(config,
					install.getLibraryLocations(), install.getEnvironment());
			StringBuffer buf = new StringBuffer();
			for (TclPackageInfo tclPackageInfo : toFetch) {
				buf.append(tclPackageInfo.getName()).append(" "); //$NON-NLS-1$
			}
			String names = buf.toString();
			ByteArrayInputStream bais = new ByteArrayInputStream(names
					.getBytes());
			/* Progress monitoring */monitor
					.subTask(Messages.TclInterpreterMessages_DeployingFileWithListOfPackages);
			IPath packagesPath = null;
			try {
				packagesPath = deployment.add(bais, "packages.txt"); //$NON-NLS-1$
			} catch (IOException e1) {
				if (DLTKCore.DEBUG) {
					e1.printStackTrace();
				}
				deployment.dispose();
				return;
			}
			/* Progress monitoring */monitor.worked(10);
			IFileHandle file = deployment.getFile(packagesPath);
			// For wish
			config.removeEnvVar("DISPLAY"); //$NON-NLS-1$
			String[] arguments = new String[] { "get-srcs", "-fpkgs", //$NON-NLS-1$ //$NON-NLS-2$
					file.toOSString() };

			config.addScriptArgs(arguments);

			/* Progress monitoring */monitor
					.subTask(Messages.TclInterpreterMessages_RunningPackageInfoScript);// MONITORING
			Process process = null;
			try {
				process = ScriptLaunchUtil.runScriptWithInterpreter(exeEnv,
						install.getInstallLocation().toOSString(), config);
			} catch (CoreException e) {
				if (DLTKCore.DEBUG) {
					e.printStackTrace();
				}
				deployment.dispose();
			}
			if (process == null) {
				deployment.dispose();
				return;
			}
			/* Progress monitoring */monitor.worked(10);
			List<String> output = ProcessOutputCollector.execute(process);
			/* Progress monitoring */monitor.worked(40);
			// deployment.dispose();
			/* Progress monitoring */monitor
					.subTask(Messages.TclInterpreterMessages_ProcessingPackagesInfo);
			processContent(output, true, false, interpreterInfo);
			/* Progress monitoring */monitor.worked(20);

			// Mark all toFetch as fetched.
			for (TclPackageInfo info : toFetch) {
				info.setFetched(true);
			}
			deployment.dispose();
			/* Progress monitoring */monitor
					.subTask(Messages.TclInterpreterMessages_SavePackagesInfo);
			save();
			/* Progress monitoring */monitor.worked(10);
			p
					.done(
							"Tcl", Messages.TclInterpreterMessages_FetchInterpreterSources, 0); //$NON-NLS-1$
		} finally {
			monitor.done();
			synchronized (TclPackagesManager.class) {
				sourcesFetchingSet.remove(install);
				TclPackagesManager.class.notifyAll();
			}
		}
	}

	private static URI getInfoLocation() {
		final IPath path = TclPlugin.getDefault().getStateLocation().append(
				"tclPackages_" + PKG_VERSION + ".info"); //$NON-NLS-2$ //$NON-NLS-2$
		return URI.createFileURI(path.toOSString());
	}

	private static URI getProjectLocation(String projectName) {
		final IPath path = TclPlugin.getDefault().getStateLocation().append(
				"project-" + projectName + ".info"); //$NON-NLS-1$ //$NON-NLS-2$
		return URI.createFileURI(path.toOSString());
	}

	private static boolean canLoad(URI location) {
		if (location.isFile()) {
			return new File(location.toFileString()).exists();
		} else {
			return true;
		}
	}

	private static class InterpreterInstallChangedListener implements
			IInterpreterInstallChangedListener {

		public void defaultInterpreterInstallChanged(
				IInterpreterInstall previous, IInterpreterInstall current) {
			// NOP
		}

		public void interpreterAdded(IInterpreterInstall Interpreter) {
			// TODO Auto-generated method stub
		}

		public void interpreterChanged(PropertyChangeEvent event) {
			if (IInterpreterInstallChangedListener.PROPERTY_EXTENSIONS
					.equals(event.getProperty())) {
				VariableMap oldVars = locateVariableMap(event.getOldValue());
				VariableMap newVars = locateVariableMap(event.getNewValue());
				if (oldVars != null || newVars != null) {
					if (oldVars != null && newVars != null) {
						if (!EcoreUtil.equals(oldVars, newVars)) {
							new RebuildProjectsJob((IInterpreterInstall) event
									.getSource()).schedule(500);
						}
					} else {
						new RebuildProjectsJob((IInterpreterInstall) event
								.getSource()).schedule(500);
					}
				}
			}
		}

		private VariableMap locateVariableMap(Object value) {
			if (value instanceof List<?>) {
				for (Object item : (List<?>) value) {
					if (item instanceof VariableMap) {
						return (VariableMap) item;
					}
				}
			}
			return null;
		}

		public void interpreterRemoved(IInterpreterInstall Interpreter) {
			// TODO Auto-generated method stub
		}

	}

	private synchronized static void initialize() {
		if (installChangedListener == null) {
			installChangedListener = new InterpreterInstallChangedListener();
			ScriptRuntime
					.addInterpreterInstallChangedListener(installChangedListener);
		}
		if (infos == null) {
			final URI location = getInfoLocation();
			infos = new XMIResourceImpl(location);
			try {
				if (canLoad(location)) {
					infos.load(null);
				}
			} catch (IOException e) {
				TclPlugin.error(e);
			}
		}
	}

	/**
	 * @param name
	 * @return
	 */
	private static Resource getProjectInfoResource(String projectName) {
		synchronized (projectInfos) {
			final Resource resource = projectInfos.get(projectName);
			if (resource != null) {
				return resource;
			}
		}
		final URI location = getProjectLocation(projectName);
		final Resource resource = new XMIResourceImpl(location);
		try {
			if (canLoad(location)) {
				resource.load(null);
			}
		} catch (IOException e) {
			TclPlugin.error(e);
		}
		synchronized (projectInfos) {
			final Resource r = projectInfos.get(projectName);
			if (r != null) {
				return r;
			} else {
				projectInfos.put(projectName, resource);
				return resource;
			}
		}
	}

	private static List<String> deployExecute(IExecutionEnvironment exeEnv,
			IInterpreterInstall install, String[] arguments,
			EnvironmentVariable[] env, ProgressMonitoringJob monitor) {
		// /* Progress monitoring */String envName = install.getEnvironment()
		// .getName();
		monitor
				.subTask(Messages.TclInterpreterMessages_DeployingPackageInformationScript);
		IDeployment deployment = exeEnv.createDeployment();
		if (deployment == null) {
			return null;
		}
		IFileHandle script = deploy(deployment);
		if (script == null) {
			deployment.dispose();
			return null;
		}
		/* Progress monitoring */monitor.worked(10);

		/* Progress monitoring */monitor
				.subTask(Messages.TclInterpreterMessages_RunningPackageInfoScript);
		IFileHandle workingDir = script.getParent();
		InterpreterConfig config = ScriptLaunchUtil.createInterpreterConfig(
				exeEnv, script, workingDir, env);
		TclLibpathUtils.addTclLibPath(config, install.getLibraryLocations(),
				install.getEnvironment());
		// For wish
		config.removeEnvVar("DISPLAY"); //$NON-NLS-1$

		if (arguments != null) {
			config.addScriptArgs(arguments);
		}

		Process process = null;
		try {
			process = ScriptLaunchUtil.runScriptWithInterpreter(exeEnv, install
					.getInstallLocation().toOSString(), config);
		} catch (CoreException e) {
			if (DLTKCore.DEBUG) {
				e.printStackTrace();
			}
		}
		if (process == null) {
			deployment.dispose();
			return null;
		}
		List<String> output = ProcessOutputCollector.execute(process);
		/* Progress monitoring */monitor.worked(70);
		deployment.dispose();
		return output;
	}

	private static IFileHandle deploy(IDeployment deployment) {
		IFileHandle script;
		try {
			IPath path = deployment.add(TclPlugin.getDefault().getBundle(),
					DLTK_TCL);
			script = deployment.getFile(path);
		} catch (IOException e) {
			if (DLTKCore.DEBUG) {
				e.printStackTrace();
			}
			return null;
		}
		return script;
	}

	private static boolean isElementName(Node nde, String name) {
		if (nde != null) {
			if (nde.getNodeType() == Node.ELEMENT_NODE) {
				if (name.equalsIgnoreCase(nde.getNodeName())) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isValidName(String packageName) {
		return packageName != null && packageName.length() != 0
				&& packageName.indexOf('$') == -1
				&& packageName.indexOf('[') == -1
				&& packageName.indexOf(']') == -1;
	}

	/**
	 * Return copy of all modules.
	 * 
	 * @param name
	 * @return
	 */
	public static synchronized List<TclModuleInfo> getProjectModules(String name) {
		TclProjectInfo info = getTclProject(name);
		List<TclModuleInfo> results = new ArrayList<TclModuleInfo>();

		EList<TclModuleInfo> modules = info.getModules();
		for (TclModuleInfo tclModuleInfo : modules) {
			TclModuleInfo copy = EcoreUtil.copy(tclModuleInfo);
			results.add(copy);
		}
		return Collections.unmodifiableList(results);
	}

	public static synchronized void setProjectModules(String name,
			List<TclModuleInfo> modules) {
		TclProjectInfo info = getTclProject(name);
		info.getModules().clear();
		info.getModules().addAll(modules);
		save();
	}

	public static synchronized void removeInterpreterInfo(
			IInterpreterInstall install) {
		initialize();
		final TclInterpreterInfo info = getTclInterpreter(install, false);
		if (info != null) {
			if (!info.getPackages().isEmpty() || info.isFetched()
					|| info.getFetchedAt() != null) {
				info.getPackages().clear();
				info.setFetched(false);
				info.setFetchedAt(null);
				save();
			}
		}
	}

	/**
	 * @since 2.0
	 */
	public static synchronized void markInterprterAsNotFetched(
			IInterpreterInstall install) {
		initialize();
		final TclInterpreterInfo info = getTclInterpreter(install, false);
		if (info != null) {
			info.setFetched(false);
			info.setFetchedAt(null);
			save();
		}
	}

	public static Set<String> getPackageInfosAsString(
			IInterpreterInstall install) {
		initialize();
		Set<String> result = new HashSet<String>();
		List<TclPackageInfo> list = getPackageInfos(install);
		for (TclPackageInfo tclPackageInfo : list) {
			result.add(tclPackageInfo.getName());
		}
		return result;
	}

	private static EMap<String, VariableValue> convertVariablesToEMap(
			Map<String, String> variables) {
		final EMap<String, VariableValue> result = new BasicEMap<String, VariableValue>();
		for (Map.Entry<String, String> entry : variables.entrySet()) {
			final VariableValue value = TclPackagesFactory.eINSTANCE
					.createVariableValue();
			value.setValue(entry.getValue());
			result.put(entry.getKey(), value);
		}
		return result;
	}

	private static Map<String, String> convertVariablesToMap(
			EMap<String, VariableValue> variables) {
		final Map<String, String> result = new HashMap<String, String>();
		for (Map.Entry<String, VariableValue> entry : variables.entrySet()) {
			result.put(entry.getKey(), entry.getValue().getValue());
		}
		return result;
	}

	/**
	 * @since 2.0
	 */
	public static Map<String, String> getVariables(IInterpreterInstall install) {
		return convertVariablesToMap(getVariablesEMap(install));
	}

	/**
	 * @since 2.0
	 */
	public static EMap<String, VariableValue> getVariablesEMap(
			IInterpreterInstall install) {
		final EObject variables = install
				.findExtension(TclPackagesPackage.Literals.VARIABLE_MAP);
		if (variables != null) {
			return ECollections.unmodifiableEMap(((VariableMap) variables)
					.getVariables());
		} else {
			return ECollections.emptyEMap();
		}
	}

	/**
	 * @since 2.0
	 */
	public static void setVariables(IInterpreterInstall install,
			Map<String, String> variables) {
		setVariables(install, convertVariablesToEMap(variables));
	}

	/**
	 * @since 2.0
	 */
	public static void setVariables(IInterpreterInstall install,
			EMap<String, VariableValue> variables) {
		if (variables != null && !variables.isEmpty()) {
			final VariableMap variableMap = TclPackagesFactory.eINSTANCE
					.createVariableMap();
			variableMap.getVariables().putAll(variables);
			install.replaceExtension(TclPackagesPackage.Literals.VARIABLE_MAP,
					variableMap);
		} else {
			install.replaceExtension(TclPackagesPackage.Literals.VARIABLE_MAP,
					null);
		}
	}

	/**
	 * @since 2.0
	 */
	public static Map<String, String> getVariables(String projectName) {
		return convertVariablesToMap(getVariablesEMap(projectName));
	}

	/**
	 * @since 2.0
	 */
	public static synchronized EMap<String, VariableValue> getVariablesEMap(
			String projectName) {
		TclProjectInfo projectInfo = getTclProject(projectName);
		return ECollections.unmodifiableEMap(projectInfo.getVariables());
	}

	/**
	 * @since 2.0
	 */
	public static void setVariables(String projectName,
			Map<String, String> variables) {
		setVariables(projectName, convertVariablesToEMap(variables));
	}

	/**
	 * @since 2.0
	 */
	public static synchronized void setVariables(String projectName,
			EMap<String, VariableValue> variables) {
		TclProjectInfo projectInfo = getTclProject(projectName);
		projectInfo.getVariables().clear();
		projectInfo.getVariables().putAll(variables);
		save();
	}

	/**
	 * @since 2.0
	 */
	public static void fillPackagesFromContent(List<String> content,
			TclInterpreterInfo info) {
		String text = getXMLContent(content);
		Document document = getDocument(text);
		Set<TclPackageInfo> packages = new HashSet<TclPackageInfo>();

		if (document != null) {
			Element element = document.getDocumentElement();
			NodeList childNodes = element.getChildNodes();
			int len = childNodes.getLength();
			for (int i = 0; i < len; i++) {
				Node nde = childNodes.item(i);
				if (isElementName(nde, "path")) { //$NON-NLS-1$
					Element el = (Element) nde;
					NodeList elChilds = el.getChildNodes();
					for (int j = 0; j < elChilds.getLength(); j++) {
						Node pkgNde = elChilds.item(j);
						if (isElementName(pkgNde, "package")) { //$NON-NLS-1$
							Element pkgElement = (Element) pkgNde;
							String name = pkgElement.getAttribute("name"); //$NON-NLS-1$

							TclPackageInfo pkg = getCreatePackage(info, name);
							populatePackage(pkg, pkgNde, info);
							packages.add(pkg);
						}
					}
				}
			}
		}
	}

	/**
	 * 
	 * Fetch only one package information to info
	 * 
	 * @since 2.0
	 */
	public static TclPackageInfo getPackageInfo(InterpreterStandin install,
			String name, boolean fetch, TclInterpreterInfo info,
			IProgressMonitor monitor) {
		EList<TclPackageInfo> packages = info.getPackages();
		Set<TclPackageInfo> toFetch = new HashSet<TclPackageInfo>();
		TclPackageInfo result = null;

		synchronized (TclPackagesManager.class) {
			for (TclPackageInfo tclPackageInfo : packages) {
				if (name.equals(tclPackageInfo.getName())) {
					if (tclPackageInfo.isFetched()) {
						return tclPackageInfo;
					} else {
						toFetch.add(tclPackageInfo);
						fetchSources(toFetch, install, info, monitor);
						result = tclPackageInfo;
						break;
					}
				}
			}
		}
		return result;
	}
}
