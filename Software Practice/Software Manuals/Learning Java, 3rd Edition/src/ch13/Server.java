package ch13;

import java.net.*;
import java.io.*;

/**
 * From Learning Java, 3rd Edition, p. 458-459.
 */
public class Server {
	public static void main(String argv[]) throws IOException {
		@SuppressWarnings("resource")
		ServerSocket ss = new ServerSocket(Integer.parseInt(argv[0]));
		while (true)
			new ServerConnection(ss.accept()).start();
	}
} // end of class Server

class ServerConnection extends Thread {
	Socket client;

	ServerConnection(Socket client) throws SocketException {
		this.client = client;
		setPriority(NORM_PRIORITY - 1);
	}

	public void run() {
		try {
			ObjectInputStream in = new ObjectInputStream(client.getInputStream());
			ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
			while (true) {
				out.writeObject(processRequest(in.readObject()));
				out.flush();
			}
		} catch (EOFException e3) { // Normal EOF
			try {
				client.close();
			} catch (IOException e) {
			}
		} catch (IOException e) {
			System.out.println("I/O error " + e); // I/O error
		} catch (ClassNotFoundException e2) {
			System.out.println(e2); // unknown type of request object
		}
	}

	private Object processRequest(Object request) {
		if (request instanceof DateRequest)
			return new java.util.Date();
		else if (request instanceof WorkRequest)
			return ((WorkRequest) request).execute();
		else
			return null;
	}
}
