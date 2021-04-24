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
import java.io.Reader;
import java.net.Socket;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 110-112. This program
 * connects to a server at a specified host and port.
 * <p/>
 * The best way to test this class is to use it as the client for the program
 * named Server in this same package. Configure the client either in its own Run
 * Configuration file (named "GenericClient"), or else launch it from the
 * command line with the appropriate arguments. See the {@link Server} JavaDoc
 * for ideas on how to test both programs together. (Note Server provides
 * ASCII-based services, not web pages.)
 */
public class GenericClient {

	/*
	 * Here <hostname> and <port> refer to the server to which the client is
	 * connecting.
	 */
	public static final String usage = "Usage: java GenericClient <hostname> <port>";

	public static void main(String[] args) throws IOException {
		try {
			// Check the number of arguments:
			if (args.length != 2) {
				throw new IllegalArgumentException("Wrong number of args");
			}

			// Parse the host and port specifications.
			String host = args[0];
			int port = Integer.parseInt(args[1]);

			// Connect to the specified host and port.
			Socket socket = new Socket(host, port);

			/*
			 * Set up streams for reading from and writing to the server. The from_server
			 * stream is final for use in the inner class below.
			 */
			final Reader fromServer = new InputStreamReader(socket.getInputStream());
			PrintWriter toServer = new PrintWriter(socket.getOutputStream());

			// Set up streams for reading from and writing to the console.
			BufferedReader fromUser = new BufferedReader(new InputStreamReader(System.in));
			/*
			 * The toUser stream is final, for use in the anonymous class below. Pass true
			 * for auto-flush on println().
			 */
			final PrintWriter toUser = new PrintWriter(System.out, true);

			// Tell the user that we've connected.
			toUser.println("Connected to " + socket.getInetAddress() + ":" + socket.getPort());

			/*
			 * Create a thread that gets output from the server and displays it to the user.
			 * We use a separate thread for this, so that we can receive asynchronous
			 * output.
			 */
			Thread thread = new Thread() {
				public void run() {
					char[] buffer = new char[1024];
					int charactersRead;
					try {
						/*
						 * Read characters from the server until the stream closes, and write them to
						 * the console.
						 */
						while ((charactersRead = fromServer.read(buffer)) != -1) {
							toUser.write(buffer, 0, charactersRead);
							toUser.flush();
						}
					} catch (IOException ioException) {
						toUser.println(ioException);
					}

					/*
					 * When the server closes the connection, the loop above will end. Tell the user
					 * what happened, and call System.exit(), causing the main thread to exit along
					 * with this one.
					 */
					toUser.println("Connection closed by server.");
					System.exit(0);
				}
			};

			// Now start the server-to-user thread.
			thread.start();

			// In parallel, read the user's input and pass it on to the server.
			String inputLine;
			while ((inputLine = fromUser.readLine()) != null) {
				toServer.print(inputLine + "\r\n");
				toServer.flush();
			}

			/*
			 * If the user types a Ctrl-D (Unix) or Ctrl-Z (Windows) to end their input,
			 * we'll get an EOF, and the while() loop above will exit. When this happens, we
			 * stop the server-to-user thread and close the socket.
			 */

			socket.close();
			toUser.println("Connection closed by client.");
			System.exit(0);
		}
		// If anything goes wrong, print an error message.
		catch (Exception exception) {
			System.err.println(exception);
			System.err.println(usage);
			exception.printStackTrace(System.err);
		}
	}
}
