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

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Set;
import java.util.Iterator;

import javax.print.attribute.Attribute;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 172-175. A simple HTTP
 * server that displays information about all accessible printers on the
 * network.
 * <p/>
 * To test this, simply run the "main" function, or else invoke the
 * automatically-generated Run Configuration (which requires no arguments). Let
 * it keep running. Then, in a web browser, enter "localhost:8000" in the address
 * bar.
 */
public class PrintServiceWebInterface {
	public static void main(String[] args) throws IOException {

		// Get the character encoders and decoders we'll need.
		Charset charset = Charset.forName("ISO-8859-1");
		CharsetEncoder encoder = charset.newEncoder();

		// The HTTP headers we send back to the client are fixed.
		String headers = "";
		headers += "HTTP/1.1 200 OK\r\n";
		headers += "Content-type: text/html\r\n";
		headers += "Connection: close\r\n";
		headers += "\r\n";

		/*
		 * We'll use two buffers in our response. One holds the fixed headers, and the
		 * other holds the variable body of the response.
		 */
		ByteBuffer[] buffers = new ByteBuffer[2];
		buffers[0] = encoder.encode(CharBuffer.wrap(headers));
		ByteBuffer body = ByteBuffer.allocateDirect(16 * 1024);
		buffers[1] = body;

		// Find all available PrintService objects to be described.
		PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);

		/*
		 * All of the channels we use in this code will be in non-blocking mode. So we
		 * create a Selector object that will block while monitoring all of the
		 * channels, and will only stop blocking when one or more of the channels is
		 * ready for I/O of some sort.
		 */

		Selector selector = Selector.open();

		/*
		 * Create a new ServerSocketChannel, and bind it to port 8000. Note that we have
		 * to do this using the underlying ServerSocket.
		 */
		ServerSocketChannel server = ServerSocketChannel.open();
		server.socket().bind(new java.net.InetSocketAddress(8000));

		// Put the ServerSocketChannel into non-blocking mode
		server.configureBlocking(false);

		/*
		 * Now register the channel with the Selector. The SelectionKey represents the
		 * registration of this channel with this Selector.
		 */
		SelectionKey serverkey = server.register(selector, SelectionKey.OP_ACCEPT);

		for (;;) { // The main server loop. The server runs indefinitely.

			/*
			 * This call blocks until there is activity on one of the registered channels.
			 * This is the key method in non-blocking I/O.
			 */
			selector.select();

			/*
			 * Get a java.util.Set containing the SelectionKey objects for all channels that
			 * are ready for I/O.
			 */
			Set<SelectionKey> keys = selector.selectedKeys();

			// Use a java.util.Iterator to loop through the selected keys.
			for (Iterator<SelectionKey> keyDispenser = keys.iterator(); keyDispenser.hasNext();) {
				/*
				 * Get the next SelectionKey in the set, and then remove it from the set. It
				 * must be removed explicitly, or it will be returned again by the next call to
				 * select().
				 */
				SelectionKey key = (SelectionKey) keyDispenser.next();
				keyDispenser.remove();

				/*
				 * Check whether this key is the SelectionKey we got when we registered the
				 * ServerSocketChannel.
				 */
				if (key == serverkey) {
					// Activity on the ServerSocketChannel means a client is trying to connect to
					// the server.
					if (key.isAcceptable()) {
						/*
						 * Accept the client connection, and obtain a SocketChannel to communicate with
						 * the client.
						 */
						SocketChannel client = server.accept();

						// Make sure we actually got a connection.
						if (client == null) {
							continue;
						}

						// Put the client channel in non-blocking mode.
						client.configureBlocking(false);

						/*
						 * Now register the client channel with the Selector, specifying that we'd like
						 * to know when there is data ready to read on the channel.
						 */
						client.register(selector, SelectionKey.OP_READ);
					}
				} else {
					/*
					 * If the key we got from the Set of keys is not the ServerSocketChannel key,
					 * then it must be a key representing one of the client connections. Get the
					 * channel from the key.
					 */
					SocketChannel client = (SocketChannel) key.channel();
					/*
					 * If we got here, it should mean that there is data to be read from the
					 * channel. But, we double-check here.
					 */
					if (!key.isReadable()) {
						continue;
					}

					/*
					 * Now read bytes from the client. We assume that we get all the client's bytes
					 * in one read operation.
					 */
					client.read(body);

					/*
					 * The data we read should be some kind of HTTP GET request. We don't bother
					 * checking it however since there is only one page of data we know how to
					 * return.
					 */
					body.clear();

					// Build an HTML document as our response.
					// The body of the document contains PrintService details.
					String response = "";
					response += "  <html>";
					response += "    <head>";
					response += "      <title>PrinterStatus</title";
					response += "    </head>";
					response += "    <body>";
					response += "      <h1>Printer Status</h1>";
					for (int s = 0; s < services.length; s++) {
						PrintService service = services[s];
						response += "  <h2>";
						response += service.getName();
						response += "  </h2>";

						response += "  <table>";

						Attribute[] attrs = service.getAttributes().toArray();
						for (int a = 0; a < attrs.length; a++) {

							Attribute attr = attrs[a];
							response += "<tr>";
							response += "  <td>";
							response += attr.getName();
							response += "  </td>";
							response += "  <td>";
							response += attr;
							response += "  </td>";
							response += "</tr>";
						}
						response += "  </table>";
					}
					response += "    </body>";
					response += "  </html>";
					response += "\r\n";

					// Encode the response into the body ByteBuffer.
					encoder.reset();
					encoder.encode(CharBuffer.wrap(response), body, true);
					encoder.flush(body);

					// Prepare the body buffer to be drained.
					body.flip();
					// While there are bytes left to write:
					while (body.hasRemaining()) {
						// ...write both header and body buffers.
						client.write(buffers);
					}
					// Prepare header buffer for next write.
					buffers[0].flip();
					// Prepare body buffer for next read.
					body.clear();

					/*
					 * Once we've sent our response, we have no more interest in the client channel
					 * or its SelectionKey.
					 */
					// Close the channel.
					client.close();
					// Tell Selector to stop monitoring it.
					key.cancel();
				}
			}
		}
	}
}
