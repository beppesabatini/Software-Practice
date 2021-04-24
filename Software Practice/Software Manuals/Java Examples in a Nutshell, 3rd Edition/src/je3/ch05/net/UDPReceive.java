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

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 141-142. This program
 * waits to receive datagrams sent from the specified port. When it receives
 * one, it displays the sending host, and prints the contents of the datagram as
 * a string. Then it loops and waits again.
 * <p/>
 * To test:
 *   • Launch the Run Configuration "UDPReceive"
 *   • Launch the Run Configuration "UDPSend"
 */
public class UDPReceive {
	public static final String usage = "Usage: java UDPReceive <port>";
	private static DatagramSocket datagramSocket = null;

	public static void main(String args[]) {
		try {
			if (args.length != 1) {
				throw new IllegalArgumentException("Wrong number of args");
			}
			// Get the port from the command line.
			int receivingPort = Integer.parseInt(args[0]);

			// Create a socket to listen on the port.
			datagramSocket = new DatagramSocket(receivingPort);

			/*
			 * Create a buffer into which to read Datagrams. If anyone sends us a packet
			 * containing more than will fit into this buffer, the excess will simply be
			 * discarded!
			 */
			byte[] buffer = new byte[2048];

			// Create a packet to receive data into the buffer.
			DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
			System.out.println("Waiting to receive datagrams on port: " + receivingPort);

			// Now loop forever, waiting to receive packets and printing them.
			for (;;) {
				// Wait to receive a datagram
				datagramSocket.receive(datagramPacket);

				/*
				 * Decode the bytes of the packet to characters, using the UTF-8 encoding, and
				 * then display those characters.
				 */
				String message = new String(buffer, 0, datagramPacket.getLength(), "UTF-8");
				System.out.println(datagramPacket.getAddress().getHostName() + ": " + message);

				/*
				 * Reset the length of the packet before reusing it. Prior to Java 1.1, we'd
				 * just create a new packet each time.
				 */
				datagramPacket.setLength(buffer.length);
			}
		} catch (Exception exception) {
			System.err.println(exception);
			System.err.println(usage);
			datagramSocket.close();
		}
	}
}
