package ch13;

import java.rmi.*;

/**
 * Similar to Learning Java, 3rd Edition, p.466-467.
 */
public class MyClient {

	public static void main(String[] args) throws RemoteException {
		new MyClient(args[0]);
	}

	public MyClient(String host) {
		try {
			RemoteServer server = (RemoteServer) Naming.lookup("rmi://" + host + "/NiftyServer");
			System.out.println(server.getDate());
			System.out.println(server.execute(new MyCalculation(2)));
		} catch (java.io.IOException e) {
			// I/O Error or bad URL
			System.out.println(e);
		} catch (NotBoundException e) {
			// NiftyServer isn't registered
			System.out.println(e);
		}
	}
}
