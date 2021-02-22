package ch13;

import java.net.*;
import java.io.*;

import utils.LearningJava3Utils;

/**
 * From Learning Java, 3rd Edition, p. 459-460. This class is not working. Even
 * when the specified host is running, and the port number is not blocked, the
 * class is still not working. The error message is:
 * <p>
 * "I/O error java.io.StreamCorruptedException: invalid stream header: 48545450"
 */
public class Client {
	public static void main(String[] args) {

		LearningJava3Utils.confirmContinueWithDisfunctional();

		if (args.length != 2) {
			System.out.println("Usage: Client <hostname> <portNumber>");
		}
		try {
			Socket server = new Socket(args[0], Integer.parseInt(args[1]));
			ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(server.getInputStream());

			out.writeObject(new DateRequest());
			out.flush();
			System.out.println(in.readObject());

			out.writeObject(new MyCalculation(2));
			out.flush();
			System.out.println(in.readObject());

			server.close();
		} catch (IOException e) {
			System.out.println("I/O error " + e); // I/O error
		} catch (ClassNotFoundException e2) {
			System.out.println(e2); // unknown type of response object
		}
	}
}
