package ch11;

import java.io.*;
// import java.net.*;

/**
 * This code does not appear in Learning Java chap. 11. The point of the example
 * is not clear. This reads the user's input (in green lettering) from the
 * console. After the user presses <Enter>, his input is echoed back to him.
 */
public class LogListener {
	public static void main(String[] args) throws IOException {

		// Listening on a socket:

		// int port = Integer.parseInt(args[0]);
		// ServerSocket server = new ServerSocket(port);
		// Socket client = server.accept();
		// BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

		// Listening to user input to a console:
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		String line;
		while ((line = in.readLine()) != null) {
			System.out.println(line);
		}
		// server.close();
	}
}
