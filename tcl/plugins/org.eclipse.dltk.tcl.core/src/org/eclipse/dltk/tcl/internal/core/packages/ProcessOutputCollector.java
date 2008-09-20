/*******************************************************************************
 * Copyright (c) 2008 xored software, Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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

public class ProcessOutputCollector {

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
				e.printStackTrace();
			}
		}

	}

	private static class OutputReaderThread extends Thread {

		final InputStream stream;
		final String endOfStreamMarker;
		final List output = new ArrayList();

		/**
		 * @param stream
		 */
		public OutputReaderThread(InputStream stream, String endOfStreamMarker) {
			this.stream = stream;
			this.endOfStreamMarker = endOfStreamMarker;
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
						break;
					}
					output.add(line);
				}
			} catch (IOException e) {
				// ignore
			}
		}
	}

	public static List execute(Process process) {
		try {
			process.getOutputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		final InputStream errorStream = process.getErrorStream();
		new ErrorStreamReaderThread(errorStream).start();
		final InputStream inputStream = process.getInputStream();
		final OutputReaderThread output = new OutputReaderThread(inputStream,
				DLTKTclHelper.END_OF_STREAM);
		output.start();
		// TODO also we should check if process is terminated
		try {
			output.join(50000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			errorStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		process.destroy();
		return output.output;
	}
}