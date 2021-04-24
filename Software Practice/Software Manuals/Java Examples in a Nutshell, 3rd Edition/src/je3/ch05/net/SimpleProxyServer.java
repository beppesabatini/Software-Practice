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

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 124-126. This class
 * implements a simple single-threaded proxy server. A proxy server is one that
 * acts as a proxy for some other real server. When a client connects to a proxy
 * server, the proxy forwards the client's requests to the real server, and then
 * forwards the server's responses to to the client. To the client, the proxy
 * looks like the server. To the real server, the proxy looks like a client.
 * <p/>
 * One way to test this is by running two Run Configurations:
 * 
 * <pre>
 *   • SimpleProxyServer
 *     ▬ The proxy server class is launched, and stands by waiting for commands 
 *       for NIST, on port 2725. (NIST is the National Institute of Standards and 
 *       Technology.) 
 *   • Connect to SimpleProxyServer
 *     ▬ The "Connect" class requests the current time from the proxy, which relays the 
 *       request to NIST and passes back the response.
 * </pre>
 * 
 * The NIST server closes the connection after answering the simple request, so
 * both the proxy and the initial requester exit. A better test would keep
 * connections open between the proxy and its two communicators. We may be able
 * to do this by swapping out NIST for mySQL, or even the Server program in the
 * current package.
 */
public class SimpleProxyServer {

	private static final String usage = "Usage: java SimpleProxyServer <host> <remoteport> <localport>";
	private static final String DEBUG = "false";

	/** The main method parses arguments and passes them to runServer */
	public static void main(String[] args) throws IOException {
		try {
			// Check the number of arguments
			if (args.length != 3) {
				System.err.println(usage);
				throw new IllegalArgumentException("Wrong number of args.");
			}

			/*
			 * Get the command-line arguments: the host and port for which we are proxy, and
			 * the local port on which we listen for connections.
			 */
			String host = args[0];
			int remoteport = Integer.parseInt(args[1]);
			int localport = Integer.parseInt(args[2]);
			// Print a start-up message...
			System.out.println("Starting proxy for " + host + ":" + remoteport + " on port " + localport);
			// ...and start running the server. It never returns.
			runServer(host, remoteport, localport);
		} catch (Exception e) {
			System.err.println(e);
			System.err.println(usage);
		}
	}

	/**
	 * This method runs a single-threaded proxy server for host:remoteport on the
	 * specified local port. It never returns.
	 */
	public static void runServer(String host, int remoteport, int localport) throws IOException {
		// Create a ServerSocket to listen for connections.
		ServerSocket clientServerSocket = new ServerSocket(localport);

		/*
		 * Create buffers for client-to-server and server-to-client transfer. We make
		 * one final so it can be used in an anonymous class below. Note the assumptions
		 * about the volume of traffic in each direction.
		 */
		final byte[] request = new byte[1024];
		byte[] reply = new byte[4096];

		// This is a server that never returns, so enter an infinite loop.
		while (true) {
			// Variables to hold the sockets to the client and to the server.
			Socket clientSocket = null;
			Socket serverSocket = null;
			try {
				// Wait for a connection on the local port.
				clientSocket = clientServerSocket.accept();

				/*
				 * Get client streams. Make them final so they can be used in the anonymous
				 * thread below.
				 */
				final InputStream clientInputStream = clientSocket.getInputStream();
				final OutputStream clientOutputStream = clientSocket.getOutputStream();

				/*
				 * Make a connection to the real server. If we cannot connect to the server,
				 * send an error to the client, disconnect, and continue waiting for
				 * connections.
				 */
				try {
					serverSocket = new Socket(host, remoteport);
				} catch (IOException ioException) {
					PrintWriter clientOutputWriter = new PrintWriter(clientOutputStream);
					String message = "";
					message += "Proxy server cannot connect to " + host + ":" + remoteport;
					message += "\n";
					message += ioException;
					message += "\n";
					clientOutputWriter.print(message);
					clientOutputWriter.flush();
					clientSocket.close();
					continue;
				}

				// Get server streams.
				final InputStream serverInputStream = serverSocket.getInputStream();
				final OutputStream serverOutputStream = serverSocket.getOutputStream();

				/*
				 * Make a thread to read the client's requests and pass them to the server. We
				 * have to use a separate thread because requests and responses may be
				 * asynchronous.
				 */
				Thread thread = new Thread() {
					public void run() {
						int bytesRead;
						try {
							while ((bytesRead = clientInputStream.read(request)) != -1) {
								serverOutputStream.write(request, 0, bytesRead);
								serverOutputStream.flush();
							}
						} catch (SocketException socketException) {
							String message = socketException.getMessage();
							if (message != null && "Socket closed".equals(message)) {
								if (Boolean.valueOf(DEBUG) == true) {
									System.out.println("The socket is closed");
								}
								System.exit(0);
							}
							System.err.println(socketException);
							socketException.printStackTrace(System.err);
						} catch (IOException ioException) {
							System.err.println(ioException);
							ioException.printStackTrace(System.err);
						}

						/*
						 * The client closed the connection to us, so close our connection to the
						 * server. This will also cause the server-to-client loop in the main thread to
						 * exit.
						 */
						try {
							serverOutputStream.close();
						} catch (IOException ioException) {
							System.err.println(ioException);
							ioException.printStackTrace(System.err);
						}
					}
				};

				// Start the client-to-server request thread running.
				thread.start();

				/*
				 * Meanwhile, in the main thread, read the server's responses and pass them back
				 * to the client. This will be done in parallel with the client-to-server
				 * request thread above.
				 */
				int bytesRead;
				try {
					while ((bytesRead = serverInputStream.read(reply)) != -1) {
						clientOutputStream.write(reply, 0, bytesRead);
						clientOutputStream.flush();
					}
				} catch (IOException ioException) {
					System.err.println(ioException);
					ioException.printStackTrace(System.err);
				}

				/*
				 * The server closed its connection to us, so we close our connection to our
				 * client. This will make the other thread exit.
				 */
				clientOutputStream.close();
			} catch (IOException ioException) {
				System.err.println(ioException);
				ioException.printStackTrace(System.err);

				// Close the sockets no matter what happens.
			} finally {
				try {
					if (serverSocket != null) {
						serverSocket.close();
					}
					if (clientSocket != null) {
						clientSocket.close();
					}
					if (clientServerSocket != null) {
						clientServerSocket.close();
					}
					break;
				} catch (IOException ioException) {
					System.err.println(ioException);
					ioException.printStackTrace(System.err);
				}
			}
		}
	}
}
