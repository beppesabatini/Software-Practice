package ch13;

import java.rmi.*;

// From p. 472-473. 

public class MyClientAsync extends java.rmi.server.UnicastRemoteObject implements WorkListener {

	private static final long serialVersionUID = -8872393680041914164L;

	public MyClientAsync(String host) throws RemoteException {
		try {
			RemoteServer server = (RemoteServer) Naming.lookup("rmi://" + host + "/NiftyServer");

			server.asyncExecute(new MyCalculation(100), this);
			System.out.println("call done...");
		} catch (java.io.IOException e) {
			System.out.println(e);
			// I/O Error or bad URL
		} catch (NotBoundException e) {
			System.out.println(e);
			// NiftyServer isn't registered
		}
	}

	public void workCompleted(WorkRequest request, Object result) throws RemoteException {
		System.out.println("Async result: " + result);
	}

	public static void main(String[] args) throws RemoteException {
		new MyClientAsync(args[0]);
	}

}
