package ch13;

import java.net.Socket;
import java.io.*;

// From p. 422.

public class DateAtHost extends java.util.Date {

	private static final long serialVersionUID = -8114102028149368476L;

	static int timePort = 37;
	// seconds from start of 20th century to Jan 1, 1970 00:00 GMT
	static final long offset = 2208988800L;

	public DateAtHost(String host) throws IOException {
		this(host, timePort);
	}

	public DateAtHost(String host, int port) throws IOException {
		Socket server = new Socket(host, port);
		DataInputStream din = new DataInputStream(server.getInputStream());
		int time = din.readInt();
		server.close();

		setTime((((1L << 32) + time) - offset) * 1000);
	}
}
