package org.eclipse.dltk.tcl.internal.debug.ui.interpreters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.dltk.tcl.core.TclPackagesManager;
import org.eclipse.dltk.tcl.core.packages.TclInterpreterInfo;
import org.eclipse.dltk.tcl.core.packages.TclPackageInfo;
import org.eclipse.dltk.tcl.internal.ui.TclImages;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.dltk.ui.viewsupport.ImageDescriptorRegistry;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

/**
 * @since 2.0
 */
public class AvailablePackagesBlock {
	private static final Object[] PENDING_RESULT = new Object[0];
	private TclInterpreterInfo interpreter = null;
	private TreeViewer viewer;
	private AddTclInterpreterDialog dialog;

	private static class Node {
		String value;
		Object image;
	}

	private static class Library extends Node {

	}

	public AvailablePackagesBlock(AddTclInterpreterDialog dialog) {
		this.dialog = dialog;
	}

	public void createIn(Composite content) {
		viewer = new TreeViewer(content);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gd.heightHint = 50;
		viewer.getControl().setLayoutData(gd);
		viewer.setLabelProvider(new LabelProvider() {

			@Override
			public String getText(Object element) {
				if (element instanceof TclPackageInfo) {
					String name = ((TclPackageInfo) element).getName();
					String version = ((TclPackageInfo) element).getVersion();
					if (version != null && version.length() > 0) {
						return name + " (" + version + ")";
					} else {
						return name;
					}
				}
				if (element instanceof Node) {
					return ((Node) element).value;
				}
				return super.getText(element);
			}

			@Override
			public Image getImage(Object element) {
				if (element instanceof TclPackageInfo) {
					return DLTKPluginImages
							.get(DLTKPluginImages.IMG_OBJS_PACKAGE);
				}
				if (element instanceof Node) {
					Object image = ((Node) element).image;
					if (image instanceof Image) {
						return (Image) image;
					} else if (image instanceof ImageDescriptor) {
						return registry.get((ImageDescriptor) image);
					}
				}
				return registry.get(TclImages.DESC_OBJS_TCL);
			}

			@Override
			public void dispose() {
				super.dispose();
				registry.dispose();
			}

			final ImageDescriptorRegistry registry = new ImageDescriptorRegistry(
					false);

		});
		viewer.setContentProvider(new ITreeContentProvider() {
			public void inputChanged(Viewer viewer, Object oldInput,
					Object newInput) {
			}

			public void dispose() {
			}

			public Object[] getElements(Object inputElement) {
				if (inputElement instanceof TclInterpreterInfo) {
					TclInterpreterInfo info = (TclInterpreterInfo) inputElement;
					EList<TclPackageInfo> packages = info.getPackages();
					List<TclPackageInfo> sorted = new ArrayList<TclPackageInfo>();
					sorted.addAll(packages);
					Collections.sort(sorted, new Comparator<TclPackageInfo>() {
						public int compare(TclPackageInfo o1, TclPackageInfo o2) {
							return o1.getName().compareTo(o2.getName());
						}
					});
					return sorted.toArray();
				}
				return new Object[0];
			}

			public boolean hasChildren(Object element) {
				if (element instanceof TclPackageInfo) {
					return true;
				}
				return false;
			}

			public Object getParent(Object element) {
				return null;
			}

			public Object[] getChildren(final Object parentElement) {
				if (parentElement instanceof TclPackageInfo) {
					final TclPackageInfo info = (TclPackageInfo) parentElement;
					if (!info.isFetched()) {
						TclPackagesManager.getPackageInfo(
								AvailablePackagesBlock.this.dialog
										.getInterpreterStandin(), info
										.getName(), true,
								AvailablePackagesBlock.this.interpreter, null);
					}
					List<Node> result = new ArrayList<Node>();
					EList<String> sources = info.getSources();
					for (String source : sources) {
						Node nde = new Node();
						nde.value = source;
						nde.image = TclImages.DESC_OBJS_TCL;
						result.add(nde);
					}
					EList<String> libs = info.getLibraries();
					for (String source : libs) {
						Node nde = new Library();
						nde.value = source;
						nde.image = DLTKPluginImages
								.get(DLTKPluginImages.IMG_OBJS_LIBRARY);
						result.add(nde);
					}
					return result.toArray();
				}
				return new Object[0];
			}
		});
		viewer.setComparator(new ViewerComparator() {
			@Override
			public int category(Object element) {
				if (element instanceof Library) {
					return 1;
				}
				return super.category(element);
			}
		});
		// viewer.setInput(elements);
	}

	public void updatePackages(TclInterpreterInfo info) {
		this.interpreter = info;
		// Collections.sort(elements);
		viewer.getControl().getDisplay().asyncExec(new Runnable() {
			public void run() {
				viewer.setInput(interpreter);
				viewer.refresh();
			}
		});
	}
}
