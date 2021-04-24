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
package je3.ch11.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 304-308. This class
 * defines three static methods useful for coping with unrecoverable exceptions
 * and errors.
 * <p>
 * getHTMLDetails() returns an HTML-formatted stack trace suitable for display.
 * <p>
 * reportThrowable() serializes the exception and POSTs it to a web server
 * through a java.net.URLConnection. This function probably can be tested only
 * by communicating with a web server which is running the war file (je3.war)
 * built in chapter 20.
 * <p>
 * displayThrowable() displays the exception's message in a dialog box that
 * includes buttons that invoke getHTMLDetails() and reportThrowable().
 * <p>
 * This example demonstrates: StackTraceElement, chained exceptions, Swing
 * dialogs with JOptionPane, object serialization and URLConnection.
 */
public class ErrorHandler {
	/**
	 * Display details about throwable in a simple modal dialog. Title is the title
	 * of the dialog box. If submissionURL is not null, allow the user to report the
	 * exception to that URL. Component is the "owner" of the dialog, and may be
	 * null for non-graphical applications.
	 */
	public static void displayThrowable(final Throwable throwable, String title, final String submissionURL,
			Component component) {
		// Get throwable class name minus the package name.
		String className = throwable.getClass().getName();
		className = className.substring(className.lastIndexOf('.') + 1);

		// Basic error message is className plus exception message, if any.
		String msg = throwable.getMessage();
		final String basicMessage = className + ((msg != null) ? (": " + msg) : "");

		/*
		 * Here is a JLabel to display the message. We create the component explicitly
		 * so we can manipulate it with the buttons. Note the "final" declaration.
		 */
		final JLabel messageLabel = new JLabel(basicMessage);

		/*
		 * Here are buttons for the dialog. They are declared "final" for use in the
		 * event listeners below. The "Send Report" button is only enabled if we have a
		 * URL to which to send a bug report.
		 */
		final JButton detailsButton = new JButton("Show Details");
		final JButton reportButton = new JButton("Send Report");
		reportButton.setEnabled(submissionURL != null);

		/*
		 * Our dialog will display a JOptionPane. Note that we don't have to create the
		 * "Exit" button ourselves; JOptionPane will create it for us, and will cause it
		 * to close the dialog.
		 */
		JOptionPane pane = new JOptionPane(messageLabel, JOptionPane.ERROR_MESSAGE, JOptionPane.YES_NO_OPTION, null,
				new Object[] { detailsButton, reportButton, "Exit" });

		// This is the dialog box containing the pane.
		final JDialog dialog = pane.createDialog(component, title);

		// Add an event handler for the Details button.
		detailsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				// Show or hide error details based on state of button.
				String label = detailsButton.getText();
				if (label.startsWith("Show")) {
					// JLabel can display simple HTML text
					messageLabel.setText(getHTMLDetails(throwable));
					detailsButton.setText("Hide Details");
					// Make dialog resize to fit the details.
					dialog.pack();
				} else {
					messageLabel.setText(basicMessage);
					detailsButton.setText("Show Details");
					dialog.pack();
				}
			}
		});

		// Event handler for the "Report" button.
		reportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					// Report the error, get response. See below.
					String response = reportThrowable(throwable, submissionURL);
					// Tell the user about the report:
					messageLabel.setText("<html>Error reported to:<pre>" + submissionURL + "</pre>Server responds:<p>"
							+ response + "</html>");
					dialog.pack(); // Resize dialog to fit new message.
					// Don't allow it to be reported again
					reportButton.setText("Error Reported");
					reportButton.setEnabled(false);
				} catch (IOException e) {
					// If error reporting fails:
					messageLabel.setText("Error not reported: " + e);
					dialog.pack();
				}
			}
		});

		/*
		 * Display the dialog modally. This method will return only when the user clicks
		 * the "Exit" button of the JOptionPane.
		 */
		dialog.setVisible(true);
	}

	/**
	 * Return an HTML-formatted stack trace for the specified Throwable, including
	 * any exceptions chained to the exception. Note the use of the Java 1.4
	 * StackTraceElement to get stack details. The returned string begins with
	 * "<html>" and is therefore suitable for display in Swing components such as
	 * JLabel.
	 */
	public static String getHTMLDetails(Throwable throwable) {
		if (throwable == null) {
			return ("<html></html");
		}

		/**
		 * Start with the specified throwable, and loop through the chain of causality
		 * for the throwable.
		 * <p>
		 * Get the stack trace and output each frame. Be careful not to repeat stack
		 * frames that were already reported for the exception that this one caused.
		 */
		// Initial value:
		int lengthOfLastTrace = 1;

		String details = "";
		/**/ details += "<html>";
		details += "      <body>";
		while (throwable != null) {
			details += "    <b>";
			details += "  " + throwable.getClass().getName();
			details += "    </b>: ";
			details += "    \"" + throwable.getMessage() + "\"";
			details += "    <ul>";
			StackTraceElement[] stack = throwable.getStackTrace();
			for (int i = stack.length - lengthOfLastTrace; i >= 0; i--) {
				details += "  <li>";
				details += "    in " + stack[i].getClassName() + ".";
				details += "    <b>";
				details += "  " + stack[i].getMethodName();
				details += "  </b> at ";
				details += "    <tt>";
				details += "  " + stack[i].getFileName() + ":" + stack[i].getLineNumber();
				details += "    </tt>";
				details += "  </li>";
			}
			details += "    </ul>";
			throwable = throwable.getCause();
			if (throwable != null) {
				details += "<i>";
				details += "  Caused by: ";
				details += "</i>";
				lengthOfLastTrace = stack.length;
			}
		}
		details += "      </body>";
		details += "    </html>";
		return (details);
	}

	/**
	 * Serialize the specified Throwable, and use an HttpURLConnection to POST it to
	 * the specified URL. Return the response of the web server.
	 */
	public static String reportThrowable(Throwable throwable, String submissionURL) throws IOException {
		// Parse the URL:
		URL url = new URL(submissionURL);
		// Open the unconnected Connection:
		URLConnection connection = url.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		// Tell the server what kind of data we're sending.
		connection.addRequestProperty("Content-type", "application/x-java-serialized-object");

		/*
		 * This code might work for other URL protocols, but it is intended for HTTP. We
		 * use a POST request to send data with the request.
		 */
		if (connection instanceof HttpURLConnection) {
			((HttpURLConnection) connection).setRequestMethod("POST");
		}

		// Now connect to the server:
		connection.connect();

		/*
		 * Get a stream to write to the server from the URLConnection. Wrap an
		 * ObjectOutputStream around it and serialize the Throwable.
		 */
		ObjectOutputStream out = new ObjectOutputStream(connection.getOutputStream());
		out.writeObject(throwable);
		out.close();

		/*
		 * Now get the response from the URLConnection. We expect it to be an
		 * InputStream from which we read the server's response.
		 */
		Object response = connection.getContent();
		StringBuffer message = new StringBuffer();
		if (response instanceof InputStream) {
			BufferedReader in = new BufferedReader(new InputStreamReader((InputStream) response));
			String line;
			while ((line = in.readLine()) != null) {
				message.append(line);
			}
		}
		return message.toString();
	}

	// A test program to demonstrate the class:
	public static class Test {
		public static void main(String[] args) {
			String url = (args.length > 0) ? args[0] : null;
			try {
				foo();
			} catch (Throwable error) {
				ErrorHandler.displayThrowable(error, "Fatal Error", url, null);
				System.exit(1);
			}
		}

		// These methods purposely throw an exception.
		public static void foo() {
			bar(null);
		}

		public static void bar(Object object) {
			try {
				ExceptionThrower(object);
			} catch (NullPointerException exception) {
				/*
				 * Catch the null pointer exception, and throw a new exception that has the NPE
				 * specified as its cause.
				 */
				throw (IllegalArgumentException) new IllegalArgumentException("null argument").initCause(exception);
			}
		}

		public static boolean ExceptionThrower(Object object) {
			// Throws a NullPointerException if the object is null.
			Class<?> currentClass = object.getClass();
			if (currentClass == null) {
				return (false);
			}
			return (true);
		}
	}
}
