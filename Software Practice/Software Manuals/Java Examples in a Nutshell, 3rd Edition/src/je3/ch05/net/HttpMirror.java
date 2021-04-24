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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 122-123. This program is a
 * very simple Web server. When it receives a HTTP request it sends the request
 * back as the reply. This can be of interest when you want to see just what a
 * Web client is requesting, or what data is being sent when a form is
 * submitted, for example.
 * <p/>
 * This seems to be in effect a packet sniffer that can only sniff GET requests.
 * It works correctly if a user opens a browser and enters, for example,
 * localhost:2724/nonExistentFile.html.
 * <p/>
 * The same functionality is implemented with different code in the
 * {@link Server} program in this package. 
 */
public class HttpMirror {

	private static final String usage = "Usage: java HttpMirror <port>";

	public static void main(String args[]) {
		ServerSocket serverSocket = null;
		try {
			// Get the port to listen on.
			int port = Integer.parseInt(args[0]);
			// Create a ServerSocket to listen on that port.
			serverSocket = new ServerSocket(port);
			System.out.println("HttpMirror is listening on port: " + port);
			// Now enter an infinite loop, waiting for connections and handling them.
			for (;;) {
				/*
				 * Wait for a client to connect. The method will block; when it returns the
				 * socket will be connected to the client.
				 */
				Socket clientSocket = serverSocket.accept();

				// Get input and output streams to talk to the client.
				InputStream inputStream = clientSocket.getInputStream();
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
				BufferedReader clientInputReader = new BufferedReader(inputStreamReader);
				PrintWriter clientOutputWriter = new PrintWriter(clientSocket.getOutputStream());

				// Start sending our reply, using the HTTP 1.1 protocol.
				// Version and status code:
				clientOutputWriter.print("HTTP/1.1 200 \r\n");
				// The type of data:
				clientOutputWriter.print("Content-Type: text/plain\r\n");
				// This will close the stream:
				clientOutputWriter.print("Connection: close\r\n");
				// This will end the headers:
				clientOutputWriter.print("\r\n");

				/*
				 * Now, read the HTTP request from the client, and send it right back to the
				 * client as part of the body of our response. The client doesn't disconnect, so
				 * we never get an EOF. It does sends an empty line at the end of the headers,
				 * though. So when we see the empty line, we stop reading. This means we don't
				 * mirror the contents of POST requests, for example. Note that the readLine()
				 * method works with Unix, Windows, and Mac line terminators.
				 */
				String line;
				while ((line = clientInputReader.readLine()) != null) {
					if (line.length() == 0) {
						break;
					}
					clientOutputWriter.print(line + "\r\n");
				}

				/*
				 * Close socket, breaking the connection to the client, and closing the input
				 * and output streams.
				 */
				// Flush and close the output stream:
				clientOutputWriter.close();
				// Close the input stream:
				clientInputReader.close();
				// Close the socket itself:
				clientSocket.close();
			} // Now loop again, waiting for the next connection.
		}
		// If anything goes wrong, print an error message.
		catch (IOException ioException01) {
			try {
				serverSocket.close();
			} catch (IOException ioException02) {
				System.err.println("Could not close ServerSocket");
				ioException02.printStackTrace(System.err);
			}
			System.err.println(ioException01);
			System.err.println(usage);
		}
	}
}
