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
package je3.ch21.rmi;

import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import je3.ch21.rmi.Mud.RemoteMudPerson;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 637-638. MUD stands for
 * Multi-User Domain. This is the simplest of the remote objects that we
 * implement for the MUD. It maintains only a little bit of state, and has only
 * two exported methods.
 */
public class MudPerson extends UnicastRemoteObject implements RemoteMudPerson {

	private static final long serialVersionUID = -2975135752951802250L;

	// The name of the person:
	String name;
	// The person's description:
	String description;
	// Where to send messages we receive:
	PrintWriter tellStream;

	public MudPerson(String name, String description, PrintWriter printWriter) throws RemoteException {
		this.name = name;
		this.description = description;
		tellStream = printWriter;
	}

	/** Return the person's name. Not a remote method . */
	public String getName() {
		return name;
	}

	/** Set the person's name. Not a remote method. */
	public void setName(String name) {
		this.name = name;
	}

	/** Set the person's description. Not a remote method. */
	public void setDescription(String description) {
		this.description = description;
	}

	/** Set the stream that messages to us should be written to. Not remote. */
	public void setTellStream(PrintWriter printWriter) {
		this.tellStream = printWriter;
	}

	/** A remote method that returns this person's description */
	@Override
	public String getDescription() throws RemoteException {
		return description;
	}

	/**
	 * A remote method that delivers a message to the person. That is, it delivers a
	 * message to the user controlling the "person."
	 */
	@Override
	public void tell(String message) throws RemoteException {
		tellStream.println(message);
		tellStream.flush();
	}
}
