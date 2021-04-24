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
import java.nio.channels.Channel;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Set;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 168-170. A more robust
 * daytime service, that handles TCP and UDP connections, and provides exception
 * handling and error logging.
 * 
 * <pre>
 * To test this:
 *   • Launch the Run Configuration "DaytimeServer" (this file). Let it keep running.
 *   • Launch the Run Configuration "DaytimeClient".
 * </pre>
 * 
 * You should see the time printed to client's console. Now you know what time
 * it is! We are not able to test this with UDP connections yet.
 */
public class DaytimeServer {

	private static Logger logger = Logger.getLogger(DaytimeServer.class.getName());

	public static void main(String args[]) {

		try {

			/* Allow an alternative port for testing with non-root accounts. */
			// RFC867 specifies this port:
			int port = 13;
			if (args.length > 0) {
				port = Integer.parseInt(args[0]);
			}

			// The port we'll listen on:
			SocketAddress localPort = new InetSocketAddress(port);

			// Create and bind a TCP channel on which to listen for connections.
			ServerSocketChannel tcpServer = ServerSocketChannel.open();
			tcpServer.socket().bind(localPort);

			// Also create and bind a DatagramChannel to which to listen.
			DatagramChannel udpServer = DatagramChannel.open();
			udpServer.socket().bind(localPort);

			/*
			 * Specify non-blocking mode for both channels, since our Selector object will
			 * be doing the blocking for us.
			 */
			tcpServer.configureBlocking(false);
			udpServer.configureBlocking(false);

			/*
			 * The Selector object is what allows us to block while waiting for activity on
			 * either of the two channels.
			 */
			Selector selector = Selector.open();

			/*
			 * Register the channels with the selector, and specify what conditions (a
			 * connection ready to accept, a datagram ready to read) we'd like the Selector
			 * to wake up for. These methods return SelectionKey objects, which we don't
			 * need to retain in this example.
			 */
			tcpServer.register(selector, SelectionKey.OP_ACCEPT);
			udpServer.register(selector, SelectionKey.OP_READ);

			// Now loop indefinitely, processing client connections.
			runConnectionLoop(selector, tcpServer, udpServer);

		} catch (Exception e) {
			/*
			 * This is a startup error: there is no need to log it; just print a message and
			 * exit.
			 */
			System.err.println(e);
			System.exit(1);
		}
	}

	private static void runConnectionLoop(Selector selector, ServerSocketChannel tcpServer, DatagramChannel udpServer) {

		// Now loop indefinitely, processing client connections.
		for (;;) {
			try { // Handle per-connection problems below.
					// Wait for a client to connect:
				selector.select();

				loopThroughChannels(selector, tcpServer, udpServer);

			} catch (IOException e) {
				/*
				 * This is a (hopefully transient) problem with a single connection: we log the
				 * error, but continue running. We use our class name for the logger, so that a
				 * sysadmin can configure logging for this server independently of other
				 * programs.
				 */
				logger.log(Level.WARNING, "IOException in DaytimeServer", e);
			} catch (Throwable t) {
				/*
				 * If anything else goes wrong (out of memory, for example) then log the problem
				 * and exit.
				 */
				logger.log(Level.SEVERE, "FATAL error in DaytimeServer", t);
				System.exit(1);
			}
		}
	}

	private static void loopThroughChannels(Selector selector, ServerSocketChannel tcpServer, DatagramChannel udpServer)
			throws IOException {

		/*
		 * This is an empty byte buffer to receive empty datagrams with. If a datagram
		 * overflows the receive buffer size, the extra bytes are automatically
		 * discarded, so we don't have to worry about buffer overflow attacks here.
		 */
		ByteBuffer receiveBuffer = ByteBuffer.allocate(0);

		// Get an encoder for converting strings to bytes.
		CharsetEncoder encoder = Charset.forName("US-ASCII").newEncoder();
		/*
		 * If we get here, a client has probably connected, so put our response into a
		 * ByteBuffer.
		 */
		String date = new java.util.Date().toString() + "\r\n";
		ByteBuffer response = encoder.encode(CharBuffer.wrap(date));

		/*
		 * Get the SelectionKey objects for the channels that have activity on them.
		 * These are the keys returned by the register() methods above. They are
		 * returned in a java.util.Set.
		 */
		Set<SelectionKey> keys = selector.selectedKeys();

		// Iterate through the Set of keys.
		for (Iterator<SelectionKey> i = keys.iterator(); i.hasNext();) {
			// Get a key from the set, and remove it from the set
			SelectionKey key = i.next();
			i.remove();

			// Get the channel associated with the key.
			Channel channel = (Channel) key.channel();

			/*
			 * Now test the key and the channel to find out whether something happened on
			 * the TCP or UDP channel.
			 */
			if (key.isAcceptable() && channel == tcpServer) {
				// A client has attempted to connect via TCP. Accept the connection now.
				SocketChannel client = tcpServer.accept();
				/*
				 * If we accepted the connection successfully, then send our response back to
				 * the client.
				 */
				if (client != null) {
					// Send response:
					client.write(response);
					// Close connection:
					client.close();
				}
			} else if (key.isReadable() && channel == udpServer) {
				/*
				 * A UDP datagram is waiting. Receive it now, noting the address from which it
				 * was sent.
				 */
				SocketAddress clientAddress = udpServer.receive(receiveBuffer);
				/*
				 * If we got the datagram successfully, send the date and time in a response
				 * packet.
				 */
				if (clientAddress != null) {
					udpServer.send(response, clientAddress);
				}
			}
		}
	}
}
