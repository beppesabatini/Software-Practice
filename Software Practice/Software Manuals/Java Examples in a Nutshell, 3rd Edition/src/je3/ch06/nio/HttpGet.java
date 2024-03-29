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

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URI;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 163-166.
 */

public class HttpGet {

	private static String DEBUG = "true";

	public static void main(String[] args) {
		// The Channel for reading from the server:
		SocketChannel server = null;
		// The Stream to the destination file:
		FileOutputStream outputStream = null;
		// The Channel to write to the Stream:
		WritableByteChannel destination;

		try {

			// Parse the URL. Note we use the new java.net.URI, not URL here.
			URI uri = new URI(args[0]);

			// Now query and verify the various parts of the URI.
			String scheme = uri.getScheme();
			if (scheme == null || !scheme.equals("http")) {
				throw new IllegalArgumentException("Must use 'http:' protocol");
			}

			String hostname = uri.getHost();
			int port = uri.getPort();
			// Use default port if none specified:
			if (port == -1) {
				port = 80;
			}

			String path = uri.getRawPath();
			if (path == null || path.length() == 0) {
				path = "/";
			}

			String query = uri.getRawQuery();
			query = (query == null) ? "" : '?' + query;

			/**
			 * Combine the hostname and port into a single address object. Note
			 * {@link java.net.SocketAddress} and {@link InetSocketAddress} are new in Java
			 * 1.4.
			 */
			SocketAddress serverAddress = new InetSocketAddress(hostname, port);

			// Open a SocketChannel to the server.
			server = SocketChannel.open(serverAddress);

			// Put together the HTTP request we'll send to the server.
			String request = "";
			request += "GET " + path + query + " HTTP/1.1\r\n";
			// The hostname is required in HTTP 1.1.
			request += "Host: " + hostname + "\r\n";
			// Don't keep connection open.
			request += "Connection: close\r\n";
			request += "User-Agent: " + HttpGet.class.getName() + "\r\n";
			// A blank line indicates the end of the request headers.
			request += "\r\n";

			if (Boolean.valueOf(DEBUG) == true) {
				System.out.println("Server Request: " + request);
			}

			// Now wrap a CharBuffer around that request string.
			CharBuffer requestChars = CharBuffer.wrap(request);

			// Get a Charset object to encode the char buffer into bytes.
			Charset charset = Charset.forName("ISO-8859-1");

			// Use the charset to encode the request into a byte buffer.
			ByteBuffer requestBytes = charset.encode(requestChars);

			// Finally, we can send this HTTP request to the server.
			server.write(requestBytes);

			// Set up an output channel to which to send the output.
			if (args.length > 1) {
				// Either use a specified filename:
				outputStream = new FileOutputStream(args[1]);
				destination = outputStream.getChannel();
			} else {
				// Or, wrap a channel around standard out.
				destination = Channels.newChannel(System.out);
			}

			/*
			 * Allocate a 32 Kilobyte byte buffer for reading the response. Hopefully we'll
			 * get a low-level "direct" buffer.
			 */
			ByteBuffer data = ByteBuffer.allocateDirect(32 * 1024);

			// Have we discarded the HTTP response headers yet?
			boolean skippedHeaders = false;
			// The code sent by the server:
			int responseCode = -1;

			/*
			 * Now loop, reading data from the server channel, and writing it to the
			 * destination channel, until the server indicates that it has no more data.
			 */
			// Read data, and check for its end.
			while (server.read(data) != -1) {
				// Prepare to extract data from buffer:
				data.flip();

				/*
				 * All HTTP responses begin with a set of HTTP headers, which we need to
				 * discard. The headers end with the string "\r\n\r\n", which corresponds to the
				 * bytes 13, 10, 13, 10. If we haven't already skipped them, then do so now.
				 */
				if (!skippedHeaders) {
					/*
					 * First, though, read the HTTP response code. Assume that we get the complete
					 * first line of the response when the first read() call returns. Assume also
					 * that the first 9 bytes are the ASCII characters "HTTP/1.1 ", and that the
					 * response code is the ASCII characters in the following three bytes.
					 */
					if (responseCode == -1) {
						responseCode = 100 * (data.get(9) - '0') + 10 * (data.get(10) - '0') + 1 * (data.get(11) - '0');

						/*
						 * If there was an error, report it and quit. Note that we do not handle
						 * redirect responses.
						 */
						if (responseCode < 200 || responseCode >= 300) {
							System.err.println("HTTP Error: " + responseCode);
							System.exit(1);
						}
					}

					// Now skip the rest of the headers.
					try {
						for (;;) {
							if ((data.get() == 13) && (data.get() == 10) && (data.get() == 13) && (data.get() == 10)) {
								skippedHeaders = true;
								break;
							}
						}
					} catch (BufferUnderflowException e) {
						/*
						 * If we arrive here, it means we reached the end of the buffer and didn't find
						 * the end of the headers. There is a chance that the last 1, 2, or 3 bytes in
						 * the buffer were the beginning of the \r\n\r\n sequence, so back up a bit.
						 */
						data.position(data.position() - 3);
						// Now discard the headers we have read...
						data.compact();
						// ...and, go read more data from the server.
						continue;
					}
				}

				// Write the data out; drain the buffer fully.
				while (data.hasRemaining()) {
					destination.write(data);
				}

				/*
				 * Now that the buffer is drained, put it into fill mode in preparation for
				 * reading more data into it. The data.compact() function would also work here.
				 */
				data.clear();
			}
		} catch (Exception e) {
			// Report any errors that arise.
			if (Boolean.valueOf(DEBUG) == true) {
				e.printStackTrace(System.err);
			}
			System.err.println("Usage: java HttpGet <URL> [<filename>]");
		} finally {
			// Close the channels and output file stream, if necessary.
			try {
				if (server != null && server.isOpen()) {
					server.close();
				}
				if (outputStream != null) {
					outputStream.close();
				}
			} catch (IOException e) {
				// ignore
			}
			System.out.println("\nThe URL: " + args[0] + " has been downloaded to: " + args[1]);
		}
	}
}
