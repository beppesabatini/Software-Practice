package ch13;

import java.net.*;
import java.io.*;

// Seems to talk to HeartBeat, which uses applets, which are no 
// longer supported. From p. 453.

public class Pulse {
	public static void main(String[] argv) throws IOException {

		@SuppressWarnings("resource")
		DatagramSocket s = new DatagramSocket(Integer.parseInt(argv[0]));

		while (true) {
			DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
			s.receive(packet);
			System.out.println("packet length = " + packet.getData().length);
			String message = new String(packet.getData(), "UTF-8");
			System.out.println("Heartbeat from: " + packet.getAddress().getHostName() + " - " + message);
		}
	}
}
