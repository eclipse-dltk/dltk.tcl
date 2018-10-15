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

package org.eclipse.dltk.tcl.validators.tests;

import junit.framework.TestCase;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.dltk.core.search.IDLTKSearchConstants;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.core.search.NopTypeNameRequestor;
import org.eclipse.dltk.core.search.SearchEngine;
import org.eclipse.dltk.core.search.SearchPattern;
import org.eclipse.dltk.tcl.core.TclLanguageToolkit;
import org.eclipse.dltk.tcl.internal.validators.TclCheckBuildParticipant;
import org.eclipse.dltk.tcl.parser.PerformanceMonitor;
import org.eclipse.dltk.tcl.parser.tests.TestUtils;
import org.osgi.framework.Bundle;

public class ChecksProjectTest extends TestCase {
	private IProgressMonitor monitor = new IProgressMonitor() {
		public void beginTask(String name, int totalWork) {
			System.out.println("Begin task:" + name);
		}

		public void done() {
		}

		public void internalWorked(double work) {
		}

		public boolean isCanceled() {
			// TODO Auto-generated method stub
			return false;
		}

		public void setCanceled(boolean value) {
			// TODO Auto-generated method stub

		}

		public void setTaskName(String name) {
		}

		public void subTask(String name) {
			System.out.println("Sub task:" + name);
		}

		public void worked(int work) {
			System.out.println("@");
		}
	};

	IProject project;

	public void setUp() throws Exception {
		TclCheckBuildParticipant.TESTING_DO_OPERATIONS = false;
		TclCheckBuildParticipant.TESTING_DO_CHECKS = false;
		String name = "test_project_name";
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		project = root.getProject(name);

		Bundle bundle = Platform
				.getBundle("org.eclipse.dltk.tcl.parser.tests.ats");
		TestCase.assertNotNull(bundle);
		TestUtils.exractZipInto(root.getLocation().append(name).toOSString(),
				bundle.getEntry("/ats.zip"), new String[] {});
		project.create(monitor);
		project.open(monitor);
	}

	public void testProjectOperations() throws Exception {
		PerformanceMonitor.getDefault().end("DLTK:Create project");
		PerformanceMonitor.getDefault().begin("DLTK:Wait for build and index");
		project.build(IncrementalProjectBuilder.FULL_BUILD, monitor);
		waitForAutoBuild();
		waitUntilIndexesReady();
		PerformanceMonitor.getDefault().end("DLTK:Wait for build and index");
		PerformanceMonitor.getDefault().begin("DLTK:Search methods");

		PerformanceMonitor.getDefault().print();
	}

	public static void waitForAutoBuild() {
		boolean wasInterrupted = false;
		do {
			try {
				IJobManager jobManager = Job.getJobManager();
				Job[] jobs = Job.getJobManager().find(
						ResourcesPlugin.FAMILY_AUTO_BUILD);
				jobManager.join(ResourcesPlugin.FAMILY_AUTO_BUILD, null);
				jobs = Job.getJobManager().find(
						ResourcesPlugin.FAMILY_AUTO_BUILD);
				for (int j = 0; j < jobs.length; j++) {
					System.out.println("#2" + jobs[j]);
				}
				wasInterrupted = false;
			} catch (OperationCanceledException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				wasInterrupted = true;
			}
		} while (wasInterrupted);
	}

	public static void waitUntilIndexesReady() {
		// dummy query for waiting until the indexes are ready
		SearchEngine engine = new SearchEngine();
		IDLTKSearchScope scope = SearchEngine
				.createWorkspaceScope(TclLanguageToolkit.getDefault());
		try {
			engine.searchAllTypeNames(null, "!@$#!@".toCharArray(),
					SearchPattern.R_PATTERN_MATCH
							| SearchPattern.R_CASE_SENSITIVE,
					IDLTKSearchConstants.TYPE, scope,
					new NopTypeNameRequestor(),
					IDLTKSearchConstants.WAIT_UNTIL_READY_TO_SEARCH, null);
		} catch (CoreException e) {
		}
	}
}
