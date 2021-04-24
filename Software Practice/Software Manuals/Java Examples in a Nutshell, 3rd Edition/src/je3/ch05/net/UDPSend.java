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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 140-141. This class sends
 * the specified text or file as a datagram to the specified port of the
 * specified host. Not working yet with UTF-8.
 * <p/>
 * To test:
 *   • Launch the Run Configuration "UDPReceive"
 *   • Launch the Run Configuration "UDPSend"
 */
public class UDPSend {
	public static final String usage = usageString();

	private static String usageString() {
		String usage = "";
		usage += "Usage: java UDPSend <hostname> <port> <msg>...\n";
		usage += "   or: java UDPSend <hostname> <port> -f <file>";
		return (usage);
	}

	public static void main(String args[]) {
		FileInputStream fileInputStream = null;

		try {
			// Check the number of arguments:
			if (args.length < 3) {
				throw new IllegalArgumentException("Wrong number of arguments");
			}

			// Parse the arguments:
			String targetHost = args[0];
			int targetPort = Integer.parseInt(args[1]);

			/*
			 * Figure out the message to send. If the third argument is -f, then send the
			 * contents of the file specified as the fourth argument. Otherwise, concatenate
			 * the third and all remaining arguments and send that.
			 */
			byte[] messageBytes;
			if (args[2].equals("-f")) {
				File file = new File(args[3]);
				// Figure out how big the file is:
				int length = (int) file.length();
				// Create a buffer big enough:
				messageBytes = new byte[length];
				fileInputStream = new FileInputStream(file);
				int bytesRead = 0;
				int numberBytes;
				do { // Keep looping until we've read it all.
					numberBytes = fileInputStream.read(messageBytes, bytesRead, length - bytesRead);
					bytesRead += numberBytes;
				} while ((bytesRead < length) && (numberBytes != -1));
			} else { // Otherwise, just combine all the remaining arguments.
				String messageString = args[2];
				for (int i = 3; i < args.length; i++) {
					messageString += " " + args[i];
				}
				// Convert the message to bytes using UTF-8 encoding.
				messageBytes = messageString.getBytes("UTF-8");
			}

			// Get the Internet address of the specified host.
			InetAddress internetAddress = InetAddress.getByName(targetHost);

			// Initialize a Datagram packet with data and address.
			int length = messageBytes.length;
			DatagramPacket datagramPacket = new DatagramPacket(messageBytes, length, internetAddress, targetPort);

			// Create a Datagram socket, send the packet through it, close it.
			DatagramSocket datagramSocket = new DatagramSocket();
			datagramSocket.send(datagramPacket);
			datagramSocket.close();
		} catch (Exception exception) {
			System.err.println(exception);
			System.err.println(usage);

		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException exception) {
					System.err.println("Could not close FileInputStream");
				}
			}
		}
	}
}
