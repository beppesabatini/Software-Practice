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
package je3.ch05.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 106-107. This program
 * sends e-mail using a mailto: URL.
 */
public class SendMail {

	static private final String usage = "Usage: java SendMail [<mailhost>]";
	
	public static void main(String[] args) {
		try {
			// If the user specified a mail host, tell the system about it.
			if (args.length >= 1) {
				System.getProperties().put("mail.host", args[0]);
			}

			// A Reader stream to read from the console:
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

			// Ask the user for the from, to, and subject lines.
			System.out.print("From: ");
			String from = in.readLine();
			System.out.print("To: ");
			String to = in.readLine();
			System.out.print("Subject: ");
			String subject = in.readLine();

			// Establish a network connection for sending mail.
			// Create a mailto: URL
			URL url = new URL("mailto:" + to);
			// Create its URLConnection:
			URLConnection connection = url.openConnection();
			// Specify no input from it:
			connection.setDoInput(false);
			// Specify we'll do output:
			connection.setDoOutput(true);
			// Tell the user we're connecting:
			System.out.println("Connecting...");
			// Tell them right now:
			System.out.flush();
			// Connect to mail host:
			connection.connect();
			// Get output stream to host:
			PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(connection.getOutputStream()));

			/* We're talking to the SMTP server now. */
			// Write out mail headers. Don't let users fake the From address.
			String fromHeader = "";
			fromHeader += "From: \"" + from + "\" <" + System.getProperty("user.name");
			fromHeader += "@" + InetAddress.getLocalHost().getHostName() + ">\r\n";
			printWriter.print(fromHeader);
			printWriter.print("To: " + to + "\r\n");
			printWriter.print("Subject: " + subject + "\r\n");
			// A blank line to end the list of headers:
			printWriter.print("\r\n"); 

			// Now ask the user to enter the body of the message.
			System.out.println("Enter the message. " + "End with a '.' on a line by itself.");
			// Read message line by line and send it out.
			String line;
			for (;;) {
				line = in.readLine();
				if ((line == null) || line.equals(".")) {
					break;
				}
				printWriter.print(line + "\r\n");
			}

			// Close (and flush) the stream to terminate the message.
			printWriter.close();
			// Tell the user it was successfully sent.
			System.out.println("Message sent.");
			// Handle any exceptions, print error message:
		} catch (Exception exception) { 
			System.err.println(exception);
			System.err.println(usage);
		}
	}
}
