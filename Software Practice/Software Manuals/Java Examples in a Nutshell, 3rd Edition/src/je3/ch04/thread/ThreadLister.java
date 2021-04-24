/*
 * Copyright (c) 2004 David Flanagan.  All rights reserved.
 * This code is from the book Java Examples in a Nutshell, 3nd Edition.
 * It is provided AS-IS, WITHOUT ANY WARRANTY either expressed or implied.
 * You may study, use, and modify it for any non-commercial purpose,
 * including teaching and use in open-source projects.
 * You may distribute it non-commercially as long as you retain this notice.
 * For a commercial use license, or to purchase the book, 
 * please visit http://www.davidflanagan.com/javaexamples3.
 */
package je3.ch04.thread;

import java.awt.BorderLayout;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 91-92. This class contains
 * a useful static method for listing all threads and thread groups in the VM.
 * It also has a simple main() method so it can be run as a standalone program.
 */
public class ThreadLister {
	/** Display information about a thread. */
	private static void printThreadInfo(PrintWriter printWriter, Thread thread, String indent) {
		if (thread == null) {
			return;
		}
		String message = "";
		message += indent;
		message += "Thread: " + thread.getName();
		message += " | Priority: " + thread.getPriority();
		if (thread.isDaemon() == true) {
			message += " | Daemon";
		}
		if (thread.isAlive() == false) {
			message += " | Not Alive";
		}
		printWriter.println(message);
	}

	/** Display info about a thread group and its threads and groups */
	private static void printGroupInfo(PrintWriter printWriter, ThreadGroup threadGroup, String indent) {
		if (threadGroup == null) {
			return;
		}
		int numberThreads = threadGroup.activeCount();
		int numberGroups = threadGroup.activeGroupCount();
		Thread[] threads = new Thread[numberThreads];
		ThreadGroup[] groups = new ThreadGroup[numberGroups];

		threadGroup.enumerate(threads, false);
		threadGroup.enumerate(groups, false);

		String message = "";
		message += indent;
		message += "Thread Group: " + threadGroup.getName();
		message += " | Max Priority: " + threadGroup.getMaxPriority();
		if (threadGroup.isDaemon() == true) {
			message += "  | Daemon";
		}
		printWriter.println(message);

		for (int i = 0; i < numberThreads; i++) {
			printThreadInfo(printWriter, threads[i], indent + "    ");
		}
		for (int i = 0; i < numberGroups; i++) {
			printGroupInfo(printWriter, groups[i], indent + "    ");
		}
	}

	/** Find the root thread group and list it recursively */
	public static void listAllThreads(PrintWriter out) {
		ThreadGroup currentThreadGroup;
		ThreadGroup rootThreadGroup;
		ThreadGroup parent;

		// Get the current thread group:
		currentThreadGroup = Thread.currentThread().getThreadGroup();

		// Now go find the root thread group:
		rootThreadGroup = currentThreadGroup;
		parent = rootThreadGroup.getParent();
		while (parent != null) {
			rootThreadGroup = parent;
			parent = parent.getParent();
		}

		// And list it, recursively:
		printGroupInfo(out, rootThreadGroup, "");
	}

	/**
	 * The main() method create a simple graphical user interface to display the
	 * threads in. This allows us to see the "event dispatch thread" used by AWT and
	 * Swing.
	 */
	public static void main(String[] args) {
		// Create a simple Swing GUI"
		JFrame jFrame = new JFrame("ThreadLister Demo");
		jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JTextArea jTextArea = new JTextArea();
		jFrame.getContentPane().add(new JScrollPane(jTextArea), BorderLayout.CENTER);
		jFrame.setSize(500, 400);
		jFrame.setVisible(true);

		/*  Get the ThreadLister listing as a string using a StringWriter stream. */
		// To capture the listing:
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		// List threads to stream:
		ThreadLister.listAllThreads(printWriter);
		printWriter.close();
		// Get listing as a string:
		String threadListing = stringWriter.toString();

		// Finally, display the thread listing in the GUI:
		jTextArea.setText(threadListing);
	}
}
