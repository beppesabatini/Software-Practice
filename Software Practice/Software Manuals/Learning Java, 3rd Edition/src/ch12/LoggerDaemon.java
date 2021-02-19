package ch12;

import java.io.*;

// From p. 401.

class LoggerDaemon extends Thread {
	
	// No main function in this class. From p. 401.

	PipedReader in = new PipedReader();

	LoggerDaemon() {
		start();
	}

	public void run() {
		BufferedReader bin = new BufferedReader(in);
		@SuppressWarnings("unused")
		String s;

		try {
			while ((s = bin.readLine()) != null) {
				// process line of data
				// ...
			}
		} catch (IOException e) {
		}
	}

	PrintWriter getWriter() throws IOException {
		return new PrintWriter(new PipedWriter(in));
	}
}
