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
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 107-109. A simple network
 * client that establishes a network connection to a specified port on a
 * specified host, send an optional message across the connection, reads the
 * response from the server and exits. A suitable client for simple network
 * services like the daytime or finger. The basic Run Configuration asks NIST
 * for the current time. A variant connects with {@link SimpleProxyServer}.
 */
public class Connect {

	private static final String usage = "Usage: Connect <hostname> <port> message...";

	public static void main(String[] args) {
		try { // Handle exceptions below.
				// Get our command-line arguments.
			String hostname = args[0];
			int port = Integer.parseInt(args[1]);
			String message = "";
			if (args.length > 2) {
				for (int i = 2; i < args.length; i++) {
					message += args[i] + " ";
				}
			}

			// Create a Socket connected to the specified host and port.
			Socket socket = new Socket(hostname, port);

			// Get the socket output stream and wrap a PrintWriter around it.
			PrintWriter outputStreamPrintWriter = new PrintWriter(socket.getOutputStream());

			// Sent the specified message through the socket to the server.
			outputStreamPrintWriter.print(message + "\r\n");
			outputStreamPrintWriter.flush(); // Send it now.

			/*
			 * Get an input stream from the socket and wrap a BufferedReader around it, so
			 * we can read lines of text from the server.
			 */
			BufferedReader bufferedInputStreamReader = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));

			/*
			 * Before we start reading the server's response, tell the socket that we don't
			 * want to wait more than 3 seconds.
			 */
			socket.setSoTimeout(3000);

			/*
			 * Now read lines from the server until the server closes the connection (and we
			 * get a null return indicating EOF), or until the server is silent for 3
			 * seconds.
			 */
			try {
				String line;
				// If we get a line:
				while ((line = bufferedInputStreamReader.readLine()) != null) {
					// ...then print it out.
					System.out.println(line);
				}
			} catch (SocketTimeoutException e) {
				// We end up here if readLine() times out.
				System.err.println("Timeout; no response from server.");
			}

			// Close the output stream:
			outputStreamPrintWriter.close();
			// Close the input stream:
			bufferedInputStreamReader.close();
			// Close the socket:
			socket.close();
		} catch (IOException ioException) {
			// Handle IO and network exceptions here:
			System.err.println(ioException);
			// Bad port number:
		} catch (NumberFormatException numberFormatException) {
			System.err.println("You must specify the port as a number");
			// Wrong number of arguments:
		} catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
			System.err.println(usage);
		}
	}
}
