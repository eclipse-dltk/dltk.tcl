package org.eclipse.dltk.tcl.internal.ui.text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IExternalSourceModule;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.internal.environment.LocalEnvironment;
import org.eclipse.dltk.internal.core.ModelManager;
import org.eclipse.dltk.launching.IInterpreterInstall;
import org.eclipse.dltk.launching.InterpreterContainerHelper;
import org.eclipse.dltk.launching.ScriptRuntime;
import org.eclipse.dltk.tcl.core.TclNature;
import org.eclipse.dltk.tcl.core.TclPackagesManager;
import org.eclipse.dltk.tcl.core.packages.TclModuleInfo;
import org.eclipse.dltk.tcl.core.packages.TclPackagesFactory;
import org.eclipse.dltk.tcl.core.packages.TclProjectInfo;
import org.eclipse.dltk.tcl.core.packages.UserCorrection;
import org.eclipse.dltk.tcl.internal.ui.TclUI;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.dltk.ui.editor.IScriptAnnotation;
import org.eclipse.dltk.ui.text.IAnnotationResolution;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ListDialog;

final class TclRequirePackageCorrectionMarkerResolution
		implements IMarkerResolution, IAnnotationResolution {
	private String pkgName;
	private IScriptProject project;
	private ISourceModule module;

	public TclRequirePackageCorrectionMarkerResolution(String pkgName,
			IScriptProject scriptProject, ISourceModule module) {
		this.pkgName = pkgName;
		this.project = scriptProject;
		this.module = module;
	}

	@Override
	public String getLabel() {
		return Messages.TclRequirePackageCorrectionMarkerResolution_SpecifyPackagesResolutionLabel;
	}

	public class PackagesLabelProvider extends LabelProvider {
		private IInterpreterInstall install;

		public PackagesLabelProvider(IInterpreterInstall install) {
			this.install = install;
			if (install == null) {
				install = ScriptRuntime.getDefaultInterpreterInstall(
						TclNature.NATURE_ID, LocalEnvironment.getInstance());
			}
		}

		@Override
		public Image getImage(Object element) {
			if (element instanceof String) {
				String packageName = (String) element;
				if (install != null) {
					final Set<String> names = TclPackagesManager
							.getPackageInfosAsString(install);

					if (!names.contains(packageName)) {
						return DLTKPluginImages
								.get(DLTKPluginImages.IMG_OBJS_ERROR);
					}
				}
			}

			return DLTKPluginImages.get(DLTKPluginImages.IMG_OBJS_PACKAGE);
		}

		@Override
		public String getText(Object element) {
			if (element instanceof String) {
				return (String) element;
			}
			return super.getText(element);
		}

	}

	private boolean resolve() {

		final IInterpreterInstall install;
		try {
			install = ScriptRuntime.getInterpreterInstall(project);
			if (install != null) {
				final Set<String> pnames = new HashSet<>();
				final Set<String> pAutoNames = new HashSet<>();
				InterpreterContainerHelper.getInterpreterContainerDependencies(
						project, pnames, pAutoNames);

				Set<String> packages = TclPackagesManager
						.getPackageInfosAsString(install);
				final List<String> names = new ArrayList<>();
				names.addAll(packages);
				Collections.sort(names, String.CASE_INSENSITIVE_ORDER);

				ListDialog dialog = new ListDialog(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell()) {
					@Override
					protected int getTableStyle() {
						return SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL
								| SWT.BORDER;
					}
				};
				dialog.setTitle(
						Messages.TclRequirePackageCorrectionMarkerResolution_SpecifyPackagesTitle);
				dialog.setContentProvider(new IStructuredContentProvider() {
					@Override
					public Object[] getElements(Object inputElement) {
						return names.toArray();
					}

					@Override
					public void dispose() {
					}

					@Override
					public void inputChanged(Viewer viewer, Object oldInput,
							Object newInput) {
					}
				});
				dialog.setLabelProvider(new PackagesLabelProvider(install));
				dialog.setInput(names);
				Set<String> pkgs = new HashSet<>();
				if (dialog.open() == ListDialog.OK) {
					TclProjectInfo info = TclPackagesManager
							.getTclProject(this.project.getElementName());
					TclModuleInfo moduleInfo = info
							.findModule(module.getHandleIdentifier());
					if (moduleInfo == null) {
						moduleInfo = TclPackagesFactory.eINSTANCE
								.createTclModuleInfo();
						moduleInfo.setHandle(module.getHandleIdentifier());
						moduleInfo.setExternal(
								module instanceof IExternalSourceModule);
						info.getModules().add(moduleInfo);
					}
					Object[] result = dialog.getResult();
					UserCorrection correction = TclPackagesFactory.eINSTANCE
							.createUserCorrection();
					correction.setOriginalValue(pkgName);
					moduleInfo.getPackageCorrections().add(correction);
					for (int i = 0; i < result.length; i++) {
						String pkg = (String) result[i];
						pkgs.add(pkg);
						correction.getUserValue().add(pkg);
					}
					TclPackagesManager.save();
				} else {
					return false;
				}
				if (pnames.addAll(pkgs)) {
					InterpreterContainerHelper
							.setInterpreterContainerDependencies(project,
									pnames, pAutoNames);
				} else {
					ModelManager.getModelManager().getDeltaProcessor()
							.checkExternalChanges(
									new IModelElement[] { project },
									new NullProgressMonitor());
				}
				return true;
			}
		} catch (CoreException e) {
			TclUI.error("require package resolve error", e); //$NON-NLS-1$
		}
		return false;
	}

	@Override
	public void run(final IMarker marker) {
		resolve();
	}

	@Override
	public void run(IScriptAnnotation annotation, IDocument document) {
		if (resolve()) {
			ISourceModule module = annotation.getSourceModule();
			try {
				module.reconcile(true, null, new NullProgressMonitor());
			} catch (ModelException e) {
				if (DLTKCore.DEBUG) {
					e.printStackTrace();
				}
			}
		}
	}
}
