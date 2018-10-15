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
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.tcl.internal.core.packages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.tcl.core.TclPackagesManager;

public class ProcessOutputCollector {

	private static final int TIMEOUT = 50000;

	private static class ErrorStreamReaderThread extends Thread {

		final InputStream stream;

		/**
		 * @param stream
		 */
		public ErrorStreamReaderThread(InputStream stream) {
			this.stream = stream;
		}

		/*
		 * @see java.lang.Thread#run()
		 */
		public void run() {
			byte[] buffer = new byte[256];
			try {
				while (stream.read(buffer) != -1) {
					// ignore
				}
			} catch (IOException e) {
				// ignore
			}
		}

	}

	private static class OutputReaderThread extends Thread {

		final InputStream stream;
		final String endOfStreamMarker;
		final List<String> output = new ArrayList<String>();
		private IProgressMonitor monitor;
		private Process process;

		/**
		 * @param process
		 * @param stream
		 * @param monitor
		 */
		public OutputReaderThread(Process process, InputStream stream,
				String endOfStreamMarker, IProgressMonitor monitor) {
			this.process = process;
			this.stream = stream;
			this.endOfStreamMarker = endOfStreamMarker;
			this.monitor = monitor;
		}

		/*
		 * @see java.lang.Thread#run()
		 */
		public void run() {
			final BufferedReader input = new BufferedReader(
					new InputStreamReader(stream));
			try {
				while (true) {
					final String line = input.readLine();
					if (line == null) {
						break;
					}
					if (line.indexOf(endOfStreamMarker) >= 0) {
						output.add(line);
						break;
					}
					output.add(line);
					monitor.worked(1);
				}
			} catch (IOException e) {
				// ignore
			}
			// Wait for termination of process
			try {
				process.waitFor();
			} catch (InterruptedException e) {
				DLTKCore.error(e);
			}
		}
	}

	public static List<String> execute(Process process) {
		return execute(process, new NullProgressMonitor());
	}

	/**
	 * @since 2.0
	 */
	public static List<String> execute(Process process, IProgressMonitor monitor) {
		try {
			process.getOutputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		final InputStream errorStream = process.getErrorStream();
		new ErrorStreamReaderThread(errorStream).start();
		final InputStream inputStream = process.getInputStream();
		final OutputReaderThread output = new OutputReaderThread(process,
				inputStream, TclPackagesManager.END_OF_STREAM, monitor);
		output.start();
		// TODO also we should check if process is terminated
		try {
			output.join(TIMEOUT);
		} catch (InterruptedException e) {
			//
		}
		try {
			errorStream.close();
		} catch (IOException e) {
			//
		}
		try {
			inputStream.close();
		} catch (IOException e) {
			//
		}
		process.destroy();
		return output.output;
	}
}
