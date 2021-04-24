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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import je3.ch21.rmi.Mud.BadPassword;
import je3.ch21.rmi.Mud.NoSuchPlace;
import je3.ch21.rmi.Mud.PlaceAlreadyExists;
import je3.ch21.rmi.Mud.RemoteMudPlace;
import je3.ch21.rmi.Mud.RemoteMudServer;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 625-628. MUD stands for
 * Multi-User Domain. This class implements the RemoteMudServer interface. It
 * also defines a main() method, so you can run it as a standalone program that
 * will set up and initialize a MUD server.
 * <p>
 * Note that a MudServer maintains an entrance point to a MUD, but it is not the
 * MUD itself. Most of the interesting MUD functionality is defined by the
 * RemoteMudPlace interface and implemented by the RemotePlace class.
 * <p>
 * In addition to being a remote object, this class is also Serializable, so
 * that the state of the MUD can be saved to a file and later restored. Note
 * that the main() method defines two ways of starting a MUD: one is to start it
 * from scratch with a single initial place, and another is to restore an
 * existing MUD from a file.
 */
public class MudServer extends UnicastRemoteObject implements RemoteMudServer, Serializable {
	/** This constant is a version number for serialization. */
	static final long serialVersionUID = 7453281245880199453L;

	// The standard entrance to this MUD:
	MudPlace entrance;
	// The password required to dump() the state of the MUD:
	String password;
	// The name that this MUD is registered under:
	String mudName;
	// A mapping of place names to places in this MUD:
	Hashtable<String, RemoteMudPlace> places;

	/**
	 * Start a MUD from scratch, with the given name and password. Create an initial
	 * MudPlace object as the entrance, giving it the specified name and
	 * description.
	 */
	public MudServer(String mudName, String password, String placeName, String description) throws RemoteException {
		this.mudName = mudName;
		this.password = password;
		this.places = new Hashtable<String, RemoteMudPlace>();
		// Create the entrance place:
		try {
			this.entrance = new MudPlace(this, placeName, description);
		} catch (PlaceAlreadyExists placeAlreadyExists) {
			// This should never happen.
		}
	}

	/** For serialization only. Never call this constructor. */
	public MudServer() throws RemoteException {
	}

	/** This remote method returns the name of the MUD. */
	@Override
	public String getMudName() throws RemoteException {
		return mudName;
	}

	/** This remote method returns the entrance place of the MUD */
	@Override
	public RemoteMudPlace getEntrance() throws RemoteException {
		return entrance;
	}

	/**
	 * This remote method returns a RemoteMudPlace object for the named place. In
	 * this sense, a MudServer acts as like an RMI Registry object, returning remote
	 * objects looked up by name. It is simpler to do it this way than to use an
	 * actual Registry object. If the named place does not exist, it throws a
	 * NoSuchPlace exception.
	 */
	@Override
	public RemoteMudPlace getNamedPlace(String name) throws RemoteException, NoSuchPlace {
		RemoteMudPlace remoteMudPlace = places.get(name);
		if (remoteMudPlace == null) {
			throw new NoSuchPlace();
		}
		return remoteMudPlace;
	}

	/**
	 * Define a new placeName to place mapping in our hashtable. This is not a
	 * remote method. The MudPlace() constructor calls it to register the new place
	 * it is creating.
	 */
	public void setPlaceName(RemoteMudPlace remoteMudPlace, String placeName) throws PlaceAlreadyExists {
		if (places.containsKey(placeName)) {
			throw new PlaceAlreadyExists();
		}
		places.put(placeName, remoteMudPlace);
	}

	/**
	 * This remote method serializes and compresses the state of the MUD to a named
	 * file, if the specified password matches the one specified when the MUD was
	 * initially created. Note that the state of a MUD consists of all places in the
	 * MUD, with all things and exits in those places. The people in the MUD are not
	 * part of the state that is saved.
	 */
	@Override
	public void dump(String password, String fileName) throws RemoteException, BadPassword, IOException {
		if ((this.password != null) && this.password.equals(password) == false) {
			throw new BadPassword();
		}
		FileOutputStream fileOutputStream = new FileOutputStream(fileName);
		GZIPOutputStream gZipOutputStream = new GZIPOutputStream(fileOutputStream);
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(gZipOutputStream);
		objectOutputStream.writeObject(this);
		objectOutputStream.close();
	}

	/**
	 * This main() method defines the standalone program that starts up a MUD
	 * server. If invoked with a single argument, it treats that argument as the
	 * name of a file containing the serialized and compressed state of an existing
	 * MUD, and recreates it. Otherwise, it expects four command-line arguments: the
	 * name of the MUD, the password, the name of the entrance place for the MUD,
	 * and a description of that entrance place. Besides creating the MudServer
	 * object, this program sets an appropriate security manager, and uses the
	 * default rmiregistry to register the the MudServer under its given name.
	 * 
	 * <pre>
	* Parameters:
	*   String args[0] newMultiUserDomainName
	*   String args[1] password
	*   String args[2] newPlaceName // Will be created in the new MUD
	*   String args[3] newPlaceDescription // Should be people-readable
	 * </pre>
	 * 
	 * Test this by launching the Run Configuration "MudServer - Create MUD", or
	 * else "MudServer -- Reload MUD". Alternatively, you can navigate to your
	 * compiled output directory and run a command-line equivalent. You may have to
	 * add your output directory to your classpath. Leave the MudServer running.
	 * <p>
	 * Open one or more additional consoles, one for each user you wish to simulate.
	 * In each console, enter a command such as this:
	 * 
	 * <pre>
	 * java j3.ch21.rmi.MudClient localhost myNewMUD Lobby
	 * </pre>
	 * 
	 * ...where "myNewMUD" is the Multi-User Domain you just created or reloaded,
	 * and "Lobby" is your optional entry point to the MUD. Prompts will guide you
	 * through defining your character and navigating your new surroundings. Enter
	 * "help" for a list of commands. This program, by the way, is supposed to work,
	 * not only if you use "localhost," but also if you specify a remote host.
	 */
	public static void main(String[] args) {
		try {
			MudServer server;
			if (args.length == 1) {
				// Read the MUD state in from a file.
				FileInputStream fileInputStream = new FileInputStream(args[0]);
				ObjectInputStream objectInputStream = new ObjectInputStream(new GZIPInputStream(fileInputStream));
				server = (MudServer) objectInputStream.readObject();
				objectInputStream.close();
			}
			// Otherwise, create an initial MUD from scratch.
			else {
				server = new MudServer(args[0], args[1], args[2], args[3]);
			}

			Naming.rebind(Mud.mudPrefix + server.mudName, server);
		}
		// Display an error message if anything goes wrong.
		catch (Exception exception) {
			System.out.println(exception);
			exception.printStackTrace(System.err);
			String usage = "";
			usage += "Usage: java MudServer <savedMudFile>\n";
			usage += "   or: java MudServer <mudname> <password> <placeName> <description>";
			System.out.println(usage);
			System.exit(1);
		}
	}
}
