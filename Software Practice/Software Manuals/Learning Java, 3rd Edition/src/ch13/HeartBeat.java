package ch13;

import java.net.*;
import java.io.*;

/**
 * From Learning Java, 3rd Edition, p. 452. Java no longer supports applets.
 */
@SuppressWarnings("deprecation")
public class HeartBeat extends java.applet.Applet {

	private static final long serialVersionUID = 1044219712417400097L;

	String myHost;
	int myPort;

	public void init() {
		myHost = getCodeBase().getHost();
		myPort = Integer.parseInt(getParameter("myPort"));
	}

	private void sendMessage(String message) {
		try {
			byte[] data = message.getBytes("UTF-8");
			InetAddress addr = InetAddress.getByName(myHost);
			DatagramPacket pack = new DatagramPacket(data, data.length, addr, myPort);
			DatagramSocket ds = new DatagramSocket();
			ds.send(pack);
			ds.close();
		} catch (IOException e) {
			System.out.println(e); // Error creating socket
		}
	}

	public void start() {
		sendMessage("Arrived");
	}

	public void stop() {
		sendMessage("Departed");
	}
}
