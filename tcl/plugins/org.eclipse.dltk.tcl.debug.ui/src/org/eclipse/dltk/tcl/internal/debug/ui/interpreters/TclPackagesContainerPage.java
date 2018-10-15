/*******************************************************************************
 * Copyright (c) 2016, 2018 xored software, Inc.  and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     xored software, Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.debug.ui.interpreters;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.internal.debug.ui.interpreters.AbstractInterpreterComboBlock;
import org.eclipse.dltk.internal.ui.wizards.IBuildpathContainerPage;
import org.eclipse.dltk.launching.IInterpreterInstall;
import org.eclipse.dltk.launching.InterpreterContainerHelper;
import org.eclipse.dltk.launching.ScriptRuntime;
import org.eclipse.dltk.tcl.core.TclPackagesManager;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.dltk.ui.wizards.IBuildpathContainerPageExtension;
import org.eclipse.dltk.ui.wizards.NewElementWizardPage;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ListDialog;

/**
 * FIXME Remove TclPackagesContainerPage class if not used anywhere
 */
public class TclPackagesContainerPage extends NewElementWizardPage
		implements IBuildpathContainerPage, IBuildpathContainerPageExtension {
	public class PackagesLabelProvider extends LabelProvider {

		@Override
		public Image getImage(Object element) {
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

	private Set<String> packages = new HashSet<>();
	private Set<String> autoPackages = new HashSet<>();

	private class PackagesContentProvider implements ITreeContentProvider {

		private final Object[] NONE_OBJECT = new Object[0];

		@Override
		public void dispose() {
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof Set) {
				return getElements(parentElement);
			}
			return NONE_OBJECT;
		}

		@Override
		public Object getParent(Object element) {
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			return false;
		}

		@Override
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof Set) {
				return packages.toArray();
			}
			return NONE_OBJECT;
		}
	}

	private IBuildpathEntry entry;

	private TreeViewer fElements;

	private IScriptProject scriptProject;

	public TclPackagesContainerPage() {
		super("Libraries");
	}

	protected AbstractInterpreterComboBlock getInterpreterBlock() {
		return new TclInterpreterComboBlock(null);
	}

	@Override
	public boolean finish() {
		return true;
	}

	@Override
	public IBuildpathEntry getSelection() {
		IBuildpathEntry createPackagesContainer = InterpreterContainerHelper.createPackagesContainer(this.packages,
				this.autoPackages,
				new Path(InterpreterContainerHelper.CONTAINER_PATH).append(this.scriptProject.getElementName()));
		return createPackagesContainer;
	}

	@Override
	public void setSelection(IBuildpathEntry containerEntry) {
		this.entry = containerEntry;
	}

	@Override
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		org.eclipse.swt.layout.GridLayout gridLayout = new org.eclipse.swt.layout.GridLayout(2, false);
		composite.setLayout(gridLayout);

		this.fElements = new TreeViewer(composite);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);

		this.fElements.getTree().setLayoutData(data);

		Composite buttons = new Composite(composite, SWT.NONE);
		GridData data2 = new GridData(SWT.FILL, SWT.FILL, false, false);
		buttons.setLayoutData(data2);

		GridLayout gridLayout2 = new GridLayout(1, true);
		buttons.setLayout(gridLayout2);

		Button add = new Button(buttons, SWT.PUSH);
		data2 = new GridData(SWT.FILL, SWT.DEFAULT, false, false);
		add.setLayoutData(data2);
		add.setText("Add");
		add.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				addPackage();
			}
		});
		Button addall = new Button(buttons, SWT.PUSH);
		data2 = new GridData(SWT.FILL, SWT.DEFAULT, false, false);
		addall.setLayoutData(data2);
		addall.setText("Add all");
		addall.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				addAllPackages();
			}
		});
		final Button remove = new Button(buttons, SWT.PUSH);
		remove.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				removePackage();
			}
		});
		remove.setText("Remove");
		remove.setLayoutData(data2);
		setControl(composite);

		setTitle("Packages");
		setMessage("Package dependencies list");
		this.setDescription("Package dependencies list");

		this.fElements.setContentProvider(new PackagesContentProvider());
		this.fElements.setLabelProvider(new PackagesLabelProvider());
		this.fElements.setInput(this.packages);
		this.fElements.addSelectionChangedListener(event -> {
			ISelection selection = event.getStructuredSelection();
			remove.setEnabled(!selection.isEmpty());
		});
		remove.setEnabled(false);
	}

	protected void removePackage() {
		IStructuredSelection sel = this.fElements.getStructuredSelection();
		boolean update = false;
		for (Iterator iterator = sel.iterator(); iterator.hasNext();) {
			String pkg = (String) iterator.next();
			boolean res = this.packages.remove(pkg);
			if (res) {
				update = res;
			}
		}
		if (update) {
			refreshView();
		}
	}

	private void refreshView() {
		PlatformUI.getWorkbench().getDisplay().asyncExec(() -> fElements.refresh());
	}

	protected void addPackage() {
		IInterpreterInstall install = null;
		try {
			install = ScriptRuntime.getInterpreterInstall(this.scriptProject);
		} catch (CoreException e) {
			if (DLTKCore.DEBUG) {
				e.printStackTrace();
			}
		}
		if (install != null) {
			Set<String> packages = TclPackagesManager.getPackageInfosAsString(install);
			final Set<String> names = new HashSet<>();
			names.addAll(packages);
			ListDialog dialog = new ListDialog(this.fElements.getControl().getShell());
			dialog.setContentProvider(new IStructuredContentProvider() {
				@Override
				public Object[] getElements(Object inputElement) {
					return names.toArray();
				}

				@Override
				public void dispose() {
				}

				@Override
				public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
				}
			});
			dialog.setLabelProvider(new PackagesLabelProvider());
			dialog.setInput(names);
			if (dialog.open() == ListDialog.OK) {
				Object[] result = dialog.getResult();
				for (int i = 0; i < result.length; i++) {
					String pkg = (String) result[i];
					this.packages.add(pkg);
				}
				refreshView();
			}
		} else {
			MessageBox box = new MessageBox(this.fElements.getControl().getShell(),
					SWT.OK | SWT.ICON_INFORMATION | SWT.APPLICATION_MODAL);
			box.setText("Packages");
			box.setText("Project interpreter could not be found...");
			box.open();
		}
	}

	protected void addAllPackages() {
		IInterpreterInstall install = null;
		try {
			install = ScriptRuntime.getInterpreterInstall(this.scriptProject);
		} catch (CoreException e) {
			if (DLTKCore.DEBUG) {
				e.printStackTrace();
			}
		}
		if (install != null) {
			Set<String> packages = TclPackagesManager.getPackageInfosAsString(install);
			this.packages.addAll(packages);

			refreshView();
		} else {
			MessageBox box = new MessageBox(this.fElements.getControl().getShell(),
					SWT.OK | SWT.ICON_INFORMATION | SWT.APPLICATION_MODAL);
			box.setText("Packages");
			box.setText("Project interpreter could not be found...");
			box.open();
		}
	}

	@Override
	public void initialize(IScriptProject project, IBuildpathEntry[] currentEntries) {
		this.scriptProject = project;
		Set<String> set = new HashSet<>();
		Set<String> autoSet = new HashSet<>();
		InterpreterContainerHelper.getInterpreterContainerDependencies(project, set, autoSet);
		this.packages.addAll(set);
		this.autoPackages.addAll(autoSet);
	}
}
