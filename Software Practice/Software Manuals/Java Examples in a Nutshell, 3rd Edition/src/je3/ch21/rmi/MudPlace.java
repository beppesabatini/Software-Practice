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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

import je3.ch21.rmi.Mud.AlreadyThere;
import je3.ch21.rmi.Mud.ExitAlreadyExists;
import je3.ch21.rmi.Mud.LinkFailed;
import je3.ch21.rmi.Mud.NoSuchExit;
import je3.ch21.rmi.Mud.NoSuchPlace;
import je3.ch21.rmi.Mud.NoSuchPerson;
import je3.ch21.rmi.Mud.NoSuchThing;
import je3.ch21.rmi.Mud.NotThere;
import je3.ch21.rmi.Mud.PlaceAlreadyExists;
import je3.ch21.rmi.Mud.RemoteMudPerson;
import je3.ch21.rmi.Mud.RemoteMudPlace;
import je3.ch21.rmi.Mud.RemoteMudServer;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 629-637. MUD stands for
 * Multi-User Domain. This class implements the RemoteMudPlace interface and
 * makes public a bunch of remote methods that are at the heart of the MUD. The
 * MudClient interacts primarily with these methods. See the comment for
 * RemoteMudPlace for an overview.
 * <p>
 * The MudPlace class is Serializable, so that places can be saved to disk along
 * with the MudServer that contains them. Note, however that the names and
 * people fields are marked "transient," so they are not serialized along with
 * the place. (Because, it wouldn't make sense to try to save RemoteMudPerson
 * objects, even if they could be serialized.)
 */
public class MudPlace extends UnicastRemoteObject implements RemoteMudPlace, Serializable {
	/** This constant is a version number for serialization. */
	static final long serialVersionUID = 5090967989223703026L;

	// Information about the place:
	String placeName, description;
	// Names of exits from this place:
	Vector<String> exits = new Vector<String>();
	// Where the exits go to:
	Vector<Object> destinations = new Vector<Object>();
	// Names of things in this place:
	Vector<String> things = new Vector<String>();
	// Descriptions of those things:
	Vector<String> descriptions = new Vector<String>();
	// Names of people in this place:
	transient Vector<String> names = new Vector<String>();
	// The RemoteMudPerson objects:
	transient Vector<RemoteMudPerson> people = new Vector<RemoteMudPerson>();
	// The server for this place:
	MudServer server;

	/** A no-arg constructor for deserialization only. Do not call it */
	public MudPlace() throws RemoteException {
		super();
	}

	/**
	 * This constructor creates a place, and calls a server method to register the
	 * object so that it will be accessible by name.
	 */
	public MudPlace(MudServer server, String placeName, String description) throws RemoteException, PlaceAlreadyExists {
		this.server = server;
		this.placeName = placeName;
		this.description = description;
		// Register the place:
		server.setPlaceName(this, placeName);
	}

	/** This remote method returns the name of this place */
	@Override
	public String getPlaceName() throws RemoteException {
		return placeName;
	}

	/** This remote method returns the description of this place. */
	@Override
	public String getDescription() throws RemoteException {
		return description;
	}

	/** This remote method returns a Vector of names of people in this place. */
	@Override
	public Vector<String> getNames() throws RemoteException {
		return names;
	}

	/** This remote method returns a Vector of names of items in this place */
	@Override
	public Vector<String> getThings() throws RemoteException {
		return things;
	}

	/** This remote method returns a Vector of names of exits from this place */
	@Override
	public Vector<String> getExits() throws RemoteException {
		return exits;
	}

	/**
	 * This remote method returns a RemoteMudPerson object corresponding to the
	 * specified name, or throws an exception if no such person is here.
	 */
	@Override
	public RemoteMudPerson getPerson(String name) throws RemoteException, NoSuchPerson {
		synchronized (names) {
			// What about when there are 2 of the same name?
			int i = names.indexOf(name);
			if (i == -1) {
				throw new NoSuchPerson();
			}
			return (RemoteMudPerson) people.elementAt(i);
		}
	}

	/**
	 * This remote method returns a description of the named item, or throws an
	 * exception if no such item is in this place.
	 */
	@Override
	public String examineThing(String name) throws RemoteException, NoSuchThing {
		synchronized (things) {
			int i = things.indexOf(name);
			if (i == -1) {
				throw new NoSuchThing();
			}
			return descriptions.elementAt(i);
		}
	}

	/**
	 * This remote method moves the specified RemoteMudPerson from this place in the
	 * named direction (i.e. through the named exit) to whatever place is there. It
	 * throws exceptions if the specified person isn't in this place to begin with,
	 * or if they are already in the place through the exit or if the exit doesn't
	 * exist, or if the exit links to another MUD server and the server is not
	 * functioning.
	 */
	@Override
	public RemoteMudPlace go(RemoteMudPerson remoteMudPerson, String direction)
			throws RemoteException, NotThere, AlreadyThere, NoSuchExit, LinkFailed {
		// Make sure the direction is valid, and get destination if it is.
		Object destination;
		synchronized (exits) {
			int i = exits.indexOf(direction);
			if (i == -1) {
				throw new NoSuchExit();
			}
			destination = destinations.elementAt(i);
		}

		/*
		 * If destination is a string, it is a place on another server, so connect to
		 * that server. Otherwise, it is a place already on this server. Throw an
		 * exception if we can't connect to the server.
		 */
		RemoteMudPlace newRemoteMudPlace;
		if (destination instanceof String) {
			String destinationString = (String) destination;
			int position = destinationString.indexOf('@');
			String url = destinationString.substring(0, position);
			String placeName = destinationString.substring(position + 1);
			try {
				RemoteMudServer remoteMudServer = (RemoteMudServer) Naming.lookup(url);
				newRemoteMudPlace = remoteMudServer.getNamedPlace(placeName);
			} catch (Exception exception) {
				throw new LinkFailed();
			}
		}
		// If the destination is not a string, then it is a Place
		else {
			newRemoteMudPlace = (RemoteMudPlace) destination;
		}

		/*
		 * Make sure the person is here and get their name. Throw an exception if they
		 * are not here
		 */
		String name = verifyPresence(remoteMudPerson);

		// Move the person out of here, and tell everyone about it who remains.
		this.exit(remoteMudPerson, name + " has gone to " + direction);

		/*
		 * Put the person into the new place. Send a message to everyone already in that
		 * new place.
		 */
		String fromWhere;
		// Going to a local place:
		if (newRemoteMudPlace instanceof MudPlace) {
			fromWhere = placeName;
		} else {
			fromWhere = server.getMudName() + "." + placeName;
		}
		newRemoteMudPlace.enter(remoteMudPerson, name, name + " has arrived from: " + fromWhere);

		/*
		 * Return the new RemoteMudPlace object to the client, so they know where they
		 * are at now.
		 */
		return newRemoteMudPlace;
	}

	/**
	 * This remote method sends a message to everyone in the room. It is used to say
	 * things to everyone. It requires that the speaker be in this place.
	 */
	@Override
	public void speak(RemoteMudPerson speaker, String message) throws RemoteException, NotThere {
		String name = verifyPresence(speaker);
		tellEveryone(name + ":" + message);
	}

	/**
	 * This remote method sends a message to everyone in the room. It is used to do
	 * things that people can see. Requires that the actor be in this place.
	 */
	@Override
	public void act(RemoteMudPerson actor, String exit) throws RemoteException, NotThere {
		String name = verifyPresence(actor);
		String message = name + " is going to " + exit;
		tellEveryone(message);

		try {
			go(actor, exit);
		} catch (LinkFailed exception) {
			String status = name + " failed to go to " + exit;
			tellEveryone(status);
		} catch (NoSuchExit exception) {
			String status = name + " failed to go to " + exit + ", there is no such exit";
			tellEveryone(status);
		} catch (AlreadyThere exception) {
			String status = name + " can't go to " + exit + ", he is already there";
			tellEveryone(status);
		}

	}

	/**
	 * This remote method creates a new item in this room. It requires that the
	 * creator be in this room.
	 */
	@Override
	public void createThing(RemoteMudPerson creator, String name, String description)
			throws RemoteException, NotThere, AlreadyThere {
		// Make sure the creator is here.
		String creatorname = verifyPresence(creator);
		synchronized (things) {
			// Make sure there isn't already an item with this name.
			if (things.indexOf(name) != -1) {
				throw new AlreadyThere();
			}
			// Add the item name and descriptions to the appropriate lists.
			things.addElement(name);
			descriptions.addElement(description);
		}
		// Tell everyone about the new thing and its creator.
		tellEveryone(creatorname + " has created a " + name);
	}

	/**
	 * Remove an item from this room. Throws exceptions if the person who removes it
	 * isn't himself in the room, or if there is no such item here.
	 */
	@Override
	public void destroyThing(RemoteMudPerson destroyer, String item) throws RemoteException, NotThere, NoSuchThing {
		// Verify that the destroyer is here:
		String name = verifyPresence(destroyer);
		synchronized (things) {
			// Verify that there is a item by that name in this room:
			int index = things.indexOf(item);
			if (index == -1) {
				throw new NoSuchThing();
			}
			// ...and, remove its name and description from the lists.
			things.removeElementAt(index);
			descriptions.removeElementAt(index);
		}
		// Let everyone know of the demise of this item.
		tellEveryone(name + " has destroyed the " + item);
	}

	/**
	 * Create a new place in this MUD, with the specified name an description. The
	 * new place is accessible from this place through the specified exit, and this
	 * place is accessible from the new place through the specified entrance. The
	 * creator must be in this place in order to create a exit from this place.
	 */
	@Override
	public void createPlace(RemoteMudPerson creator, String exit, String entrance, String name, String description)
			throws RemoteException, NotThere, ExitAlreadyExists, PlaceAlreadyExists {
		// Verify that the creator is actually here in this place
		String creatorname = verifyPresence(creator);
		// Only one client may change exits at a time:
		synchronized (exits) {
			// Check that the exit doesn't already exist.
			if (exits.indexOf(exit) != -1) {
				throw new ExitAlreadyExists();
			}
			// Create the new place, registering its name with the server
			MudPlace destination = new MudPlace(server, name, description);
			// Link from there back to here:
			destination.exits.addElement(entrance);
			destination.destinations.addElement(this);
			// ...and, link from here to there.
			exits.addElement(exit);
			destinations.addElement(destination);
		}
		// Let everyone know about the new exit, and the new place beyond.
		tellEveryone(creatorname + " has created a new place: " + exit);
	}

	/**
	 * Create a new exit from this mud, linked to a named place in a named MUD on a
	 * named host (this can also be used to link to a named place in the current
	 * MUD, of course). Because of the possibilities of deadlock, this method only
	 * links from here to there; it does not create a return exit from there to
	 * here. That must be done with a separate call.
	 */
	@Override
	public void linkTo(RemoteMudPerson linker, String exit, String hostname, String mudName, String placeName)
			throws RemoteException, NotThere, ExitAlreadyExists, NoSuchPlace {
		// Verify that the linker is actually here:
		String name = verifyPresence(linker);

		/*
		 * Check that the link target actually exists. Throw NoSuchPlace if not. Note
		 * that NoSuchPlace may also mean "NoSuchMud" or "MudNotResponding".
		 */
		String url = "rmi://" + hostname + '/' + Mud.mudPrefix + mudName;
		try {
			RemoteMudServer remoteMudServer = (RemoteMudServer) Naming.lookup(url);
			// RemoteMudPlace destination = remoteMudServer.getNamedPlace(placeName);
			remoteMudServer.getNamedPlace(placeName);
		} catch (Exception exception) {
			throw new NoSuchPlace();
		}

		synchronized (exits) {
			// Check that the exit doesn't already exist.
			if (exits.indexOf(exit) != -1) {
				throw new ExitAlreadyExists();
			}
			// Add the exit, to the list of exit names:
			exits.addElement(exit);
			/*
			 * ...and, add the destination to the list of destinations. Note that the
			 * destination is stored as a string rather than as a RemoteMudPlace. This is
			 * because if the remote server goes down then comes back up again, a
			 * RemoteMudPlace is not valid, but the string still is.
			 */
			destinations.addElement(url + '@' + placeName);
		}
		// Let everyone know about the new exit and where it leads.
		String message = "";
		message += name + " has linked " + exit;
		message += " to '" + placeName + "' ";
		message += "in MUD '" + mudName + "' ";
		message += "on host " + hostname;
		tellEveryone(message);
	}

	/**
	 * Close an exit that leads out of this place. It does not close the return exit
	 * from there back to here. Note that this method does not destroy the place
	 * that the exit leads to. In the current implementation, there is no way to
	 * destroy a place.
	 */
	@Override
	public void close(RemoteMudPerson remoteMudPerson, String exit) throws RemoteException, NotThere, NoSuchExit {
		// Check that the person closing the exit is actually here.
		String name = verifyPresence(remoteMudPerson);
		synchronized (exits) {
			// Check that the exit exists:
			int index = exits.indexOf(exit);
			if (index == -1) {
				throw new NoSuchExit();
			}
			// Remove it and its destination from the lists:
			exits.removeElementAt(index);
			destinations.removeElementAt(index);
		}
		// Let everyone know that the exit doesn't exist anymore.
		tellEveryone(name + " has closed exit " + exit);
	}

	/**
	 * Remove a person from this place. If there is a message, send it to everyone
	 * who is left in this place. If the specified person is not here this method
	 * does nothing and does not throw an exception. This method is called by go(),
	 * and the client should call it when the user quits. The client should not
	 * allow the user to invoke it directly, however.
	 */
	@Override
	public void exit(RemoteMudPerson remoteMudPerson, String message) throws RemoteException {
		synchronized (names) {
			int index = people.indexOf(remoteMudPerson);
			if (index == -1) {
				return;
			}
			names.removeElementAt(index);
			people.removeElementAt(index);
		}
		if (message != null) {
			tellEveryone(message);
		}
	}

	/**
	 * This method puts a person into this place, assigning them the specified name,
	 * and displaying a message to anyone else who is in that place. This method is
	 * called by go(), and the client should call it to initially place a person
	 * into the MUD. Once the person is in the MUD, however, the client should
	 * restrict them to using go() and should not allow them to call this method
	 * directly. If there have been networking problems, a client might call this
	 * method to restore a person to this place, in case they've been bumped out. (A
	 * person will be bumped out of a place if the server tries to send a message to
	 * them and gets a RemoteException.)
	 */
	@Override
	public void enter(RemoteMudPerson remoteMudPerson, String name, String message)
			throws RemoteException, AlreadyThere {
		// Send the message to everyone who is already here.
		if (message != null) {
			tellEveryone(message);
		}

		// Add the person to this place.
		synchronized (names) {
			if (people.indexOf(remoteMudPerson) != -1) {
				throw new AlreadyThere();
			}
			names.addElement(name);
			people.addElement(remoteMudPerson);
		}
	}

	/**
	 * This final remote method returns the server object for the MUD in which this
	 * place exists. The client should not allow the user to invoke this method.
	 */
	@Override
	public RemoteMudServer getServer() throws RemoteException {
		return this.server;
	}

	/**
	 * Create and start a thread that sends out a message everyone in this place. If
	 * it gets a RemoteException talking to a person, it silently removes that
	 * person from this place. This is not a remote method, but is used internally
	 * by a number of remote methods.
	 */
	protected void tellEveryone(final String message) {
		// If there is no one here, don't bother sending the message!
		if (people.size() == 0) {
			return;
		}
		/*
		 * Make a copy of the people here now. The message is sent asynchronously and
		 * the list of people in the room may change before the message is sent to
		 * everyone.
		 */

		@SuppressWarnings("unchecked")
		final Vector<RemoteMudPerson> recipients = (Vector<RemoteMudPerson>) people.clone();
		/*
		 * Create and start a thread to send the message, using an anonymous class. We
		 * do this because sending the message to everyone in this place might take some
		 * time, (particularly on a slow or flaky network) and we don't want to wait.
		 */
		new Thread() {
			public void run() {
				// Loop through the recipients
				for (int i = 0; i < recipients.size(); i++) {
					RemoteMudPerson person = recipients.elementAt(i);
					// Try to send the message to each one.
					try {
						person.tell(message);
					}
					/*
					 * If it fails, assume that that person's client or network has failed, and
					 * silently remove them from this place.
					 */
					catch (RemoteException remoteException01) {
						try {
							MudPlace.this.exit(person, null);
						} catch (RemoteException remoteException02) {
						}
					}
				}
			}
		}.start();
	}

	/**
	 * This convenience method checks whether the specified person is here. If so,
	 * it returns their name. If not it throws a NotThere exception
	 **/
	protected String verifyPresence(RemoteMudPerson remoteMudObject) throws NotThere {
		int index = people.indexOf(remoteMudObject);
		if (index == -1) {
			throw new NotThere();
		} else {
			return (String) names.elementAt(index);
		}
	}

	/**
	 * This method is used for custom deserialization. Since the vectors of people
	 * and of their names are transient, they are not serialized with the rest of
	 * this place. Therefore, when the place is deserialized, those vectors have to
	 * be recreated (as empty).
	 **/
	private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
		// Read most of the object as normal:
		objectInputStream.defaultReadObject();
		// Then recreate the names vector:
		names = new Vector<String>();
		// ...and, recreate the people vector:
		people = new Vector<RemoteMudPerson>();
	}

}
