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
package je3.ch06.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 166-167. A simple TCP
 * server for the daytime service. See RFC867 for details. This implementation
 * lacks meaningful exception handling, and cannot handle UDP connections.
 * <p/>
 * The only way to test this seems to be to launch the Run Configuration
 * "SimpleDaytimeServer", and then use the Connect program to ping it from the
 * command line. Don't spend too much time on this, though, the (non-simple)
 * {@link DaytimeServer} does the same thing and works better.
 * 
 * <pre>
 * java je3.ch05.net.Connect localhost 2725
 * </pre>
 */
public class SimpleDaytimeServer {

	private static String DEBUG = "true";

	public static void main(String args[]) throws java.io.IOException {
		/*
		 * RFC867 specifies port 13 for this service. On Unix platforms, you need to be
		 * running as root to use that port, so we allow this service to use other ports
		 * for testing.
		 */
		int port = 13;
		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		}

		// Create a channel on which to listen for connections.
		ServerSocketChannel server = ServerSocketChannel.open();

		/*
		 * Bind the channel to a local port. Note that we do this by obtaining the
		 * underlying java.net.ServerSocket and binding that socket.
		 */
		server.socket().bind(new InetSocketAddress(port));

		// Get an encoder for converting strings to bytes.
		CharsetEncoder encoder = Charset.forName("US-ASCII").newEncoder();

		for (;;) { // Loop indefinitely, processing client connections.
			// Wait for a client to connect.
			SocketChannel client = server.accept();

			// Build response string, wrap, and encode to bytes.
			String date = new java.util.Date().toString() + "\r\n";
			ByteBuffer response = encoder.encode(CharBuffer.wrap(date));

			// Send the response to the client and disconnect.
			client.write(response);
			if (Boolean.valueOf(DEBUG) == true) {
				System.out.println(response);
				// System.out.println(date);
			}
			client.close();
		}
	}
}
