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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Vector;

import je3.ch21.rmi.Mud.AlreadyThere;
import je3.ch21.rmi.Mud.BadPassword;
import je3.ch21.rmi.Mud.ExitAlreadyExists;
import je3.ch21.rmi.Mud.MudException;
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
 * From Java Examples in a Nutshell, 3rd Edition, pp. 639-647. MUD stands for
 * Multi-User Domain. This class is a client program for the MUD. The main()
 * method sets up a connection to a RemoteMudServer, gets the initial
 * RemoteMudPlace object, and creates a MudPerson object to represent the user
 * in the MUD. Then it calls runMud() to put the person in the place, and begins
 * processing user commands. The getLine() and getMultiLine() methods are
 * convenience methods, used throughout, to get console input from the user.
 */
public class MudClient {
	/**
	 * The main program. It expects two or three arguments:
	 * 
	 * <pre>
	 *   args[0] the name of the host on which the MUD server is running
	 *   args[1] the name of the (pre-existing) MUD on that host
	 *   args[2] the name of a user-created place inside that MUD.
	 *           An optional entry point.
	 * </pre>
	 * 
	 * The program uses the Naming.lookup() method to obtain a RemoteMudServer
	 * object for the named MUD on the specified host. Then it uses the
	 * getEntrance() or getNamedPlace() method of RemoteMudServer to obtain the
	 * starting RemoteMudPlace object. It prompts the user for a their name and
	 * description, and creates a MudPerson object. Finally, it passes the person
	 * and the place to runMud() to begin interaction with the MUD.
	 */
	public static void main(String[] args) {
		try {
			// Each MUD is uniquely identified by a host and a MUD name:
			String hostName = args[0];
			String mudName = args[1];
			// Each place in a MUD has a unique name:
			String placeName = null;
			if (args.length > 2) {
				placeName = args[2];
			}

			/*
			 * Look up the RemoteMudServer object for the named MUD, using the default
			 * registry on the specified host. Note the use of the Mud.mudPrefix constant to
			 * help prevent naming conflicts in the registry.
			 */
			String lookupKey = "rmi://" + hostName + "/" + Mud.mudPrefix + mudName;
			RemoteMudServer server = (RemoteMudServer) Naming.lookup(lookupKey);

			/*
			 * If the user did not specify a place in the mud, use getEntrance() to get the
			 * initial place. Otherwise, call getNamedPlace() to find the initial place.
			 */
			RemoteMudPlace location = null;
			if (placeName == null) {
				location = server.getEntrance();
			} else {
				location = (RemoteMudPlace) server.getNamedPlace(placeName);
			}

			/*
			 * Greet the user and ask for their name and description. This relies on
			 * getLine() and getMultiLine() defined below.
			 */
			System.out.println("Welcome to " + mudName);
			String name = getLine("Enter your name: ");
			String description = getMultiLine("Please describe what people see when they look at you:");

			/*
			 * Define an output stream that the MudPerson object will use to display
			 * messages sent to it to the user. We'll use the console.
			 */
			PrintWriter printWriter = new PrintWriter(System.out);

			/*
			 * Create a MudPerson object to represent the user in the MUD. Use the specified
			 * name and description, and the output stream.
			 */
			MudPerson mudPerson = new MudPerson(name, description, printWriter);

			/*
			 * Lower this thread's priority one notch so that broadcast messages can appear
			 * even when we're blocking for I/O. This is necessary on the Linux platform,
			 * but may not be necessary on all platforms.
			 */
			int priority = Thread.currentThread().getPriority();
			Thread.currentThread().setPriority(priority - 1);

			/*
			 * Finally, put the MudPerson into the RemoteMudPlace, and start prompting the
			 * user for commands.
			 */
			runMud(location, mudPerson);
		}
		// If anything goes wrong, print a message and exit.
		catch (Exception exception) {
			System.out.println(exception);
			System.out.println("Usage: java MudClient <host> <multi-user-domain> [<mud-place>]");
			System.exit(1);
		}
	}

	/**
	 * This method is the main loop of the MudClient. It places the person into the
	 * place (using the enter() method of RemoteMudPlace). Then it calls the look()
	 * method to describe the place to the user, and enters a command loop to prompt
	 * the user for a command and process the command.
	 */
	public static void runMud(RemoteMudPlace entrance, MudPerson mudPerson) throws RemoteException {
		// The current place:
		RemoteMudPlace location = entrance;
		// The person's name:
		String personName = mudPerson.getName();
		// The name of the current place:
		String placeName = null;
		// The name of the mud of that place:
		String mudName = null;

		try {
			// Enter the MUD:
			location.enter(mudPerson, personName, personName + " has entered the Multi-User Domain.");
			// Figure out where we are (for the prompt):
			mudName = location.getServer().getMudName();
			placeName = location.getPlaceName();
			// Describe the place to the user:
			look(location);
		} catch (Exception exception) {
			System.out.println(exception);
			System.exit(1);
		}

		/*
		 * Now that we've entered the MUD, begin a command loop to process the user's
		 * commands. Note that there is a huge block of catch statements at the bottom
		 * of the loop to handle all the things that could go wrong each time through
		 * the loop.
		 */
		// Loop until the user types "quit":
		for (;;) {
			try {
				/*
				 * Catch any exceptions that occur in the loop Pause just a bit before printing
				 * the prompt, to give output generated indirectly by the last command a chance
				 * to appear.
				 */
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
				}

				// Display a prompt, and get the user's input.
				String inputLine = getLine(mudName + '.' + placeName + "> ");

				/*
				 * Break the input into a command and an argument that consists of the rest of
				 * the line. Convert the command to lowercase.
				 */
				String command, argument;
				int index = inputLine.indexOf(' ');
				if (index == -1) {
					command = inputLine;
					argument = null;
				} else {
					command = inputLine.substring(0, index).toLowerCase();
					argument = inputLine.substring(index + 1);
				}
				if (argument == null) {
					argument = "";
				}

				/*
				 * Now go process the command. What follows is a huge repeated if/else statement
				 * covering each of the commands supported by this client. Many of these
				 * commands simply invoke one of the remote methods of the current
				 * RemoteMudPlace object. Some have to do a bit of additional processing.
				 */

				// LOOK: Describe the place and its items, people, and exits.
				if (command.equals("look")) {
					look(location);
				}
				// EXAMINE: Describe a named item.
				else if (command.equals("examine")) {
					String item = argument;
					System.out.println(location.examineThing(item));
				}
				// DESCRIBE: Describe a named person, probably not yourself.
				else if (command.equals("describe")) {
					String otherPersonsName = "The requested person";
					try {
						otherPersonsName = argument;
						RemoteMudPerson remoteMudPerson = location.getPerson(otherPersonsName);
						System.out.println(remoteMudPerson.getDescription());
					} catch (RemoteException remoteException) {
						String message = "";
						message += "Sorry, " + otherPersonsName;
						message += " is having technical difficulties. No description is available.";
						System.out.println(message);
					}
				}
				// GO: take a named exit.
				else if (command.equals("go")) {
					String exit = argument;
					location = location.go(mudPerson, exit);
					mudName = location.getServer().getMudName();
					placeName = location.getPlaceName();
					look(location);
				}
				// SAY: Say something to everyone.
				else if (command.equals("say")) {
					String announcement = argument;
					location.speak(mudPerson, announcement);
				}
				// TALK: Say something to one named person (probably not yourself).
				else if (command.equals("talk")) {
					String otherPersonsName = "";
					try {
						otherPersonsName = argument;
						RemoteMudPerson remoteMudPerson = location.getPerson(otherPersonsName);
						String message = getLine("What do you want to say?: ");
						remoteMudPerson.tell(personName + " says \"" + message + "\"");
					} catch (RemoteException remoteException) {
						String message = "";
						message = "Sorry, \"" + otherPersonsName + "\" is having technical difficulties.\n";
						message += "You two cannot talk at this time.";
						System.out.println(message);
					}
				}
				// CHANGE: Change my own description.
				else if (command.equals("change")) {
					mudPerson.setDescription(getMultiLine("Describe yourself for others: "));
				}
				// CREATE: Create a new item in this place
				else if (command.equals("create")) {
					String newItemName = argument;
					if (newItemName.length() == 0) {
						throw new IllegalArgumentException("Sorry, a name for the new item is required");
					}
					String newItemDescription = getMultiLine("Please describe the " + newItemName + ": ");
					location.createThing(mudPerson, newItemName, newItemDescription);
				}
				// DESTROY: Destroy a named item
				else if (command.equals("destroy")) {
					String itemName = argument;
					location.destroyThing(mudPerson, itemName);
				}
				// OPEN: Create a new place and connect this place to it
				// through the exit specified in the argument.
				else if (command.equals("open")) {
					String directionThere = argument;
					if (directionThere.length() == 0) {
						throw new IllegalArgumentException("Sorry, a direction or exit to the new place is required");
					}
					String newMudPlaceName = getLine("What is the name of your new place? ");
					String directionBack = getLine("What is the direction there back to here? ");
					String description = getMultiLine("Please describe " + newMudPlaceName + ":");

					location.createPlace(mudPerson, directionThere, directionBack, newMudPlaceName, description);
				}
				/*
				 * CLOSE: Close a named exit. Note: only closes an exit in one direction, and
				 * does not destroy the place to which it leads.
				 */
				else if (command.equals("close")) {
					String exitToClose = argument;
					if (exitToClose.length() == 0) {
						throw new IllegalArgumentException("Sorry, you must specify the exit you wish to close");
					}
					location.close(mudPerson, exitToClose);
				}
				/*
				 * LINK: Create a new exit that connects to an existing place that may be in
				 * another MUD, and even running on another host.
				 */
				else if (command.equals("link")) {
					String newExitName = argument;
					if (newExitName.length() == 0) {
						throw new IllegalArgumentException("Sorry, you must specify an existing place");
					}
					String host = getLine("To which computer host are you linking? ");
					String otherMudName = getLine("What is the name of the MUD on that computer? ");
					String otherPlaceName = getLine("What is the name of the \"place\" in that MUD? ");
					
					location.linkTo(mudPerson, newExitName, host, otherMudName, otherPlaceName);
					System.out.println("Please don't forget to make a link from there back to here!");
				}
				/*
				 * DUMP: Save the state of this MUD into the named file, if the password is
				 * correct.
				 */
				else if (command.equals("dump")) {
					String outputFileName = argument;
					if (outputFileName.length() == 0) {
						String message = "Sorry, you must specify the file where your MUD will be saved";
						throw new IllegalArgumentException(message);
					}
					String password = getLine("Password: ");
					outputFileName = "output/" + outputFileName + ".mud";
					location.getServer().dump(password, outputFileName);
				}
				// QUIT: Quit the game
				else if (command.equals("quit")) {
					try {
						location.exit(mudPerson, personName + " has quit.");
					} catch (Exception e) {

					}
					System.out.println("Bye.");
					System.out.flush();
					System.exit(0);
				}
				// HELP: Print out a big help message
				else if (command.equals("help")) {
					System.out.println(getHelpScreen());
				}
				// Otherwise, this is an unrecognized command.
				else {
					System.out.println("Sorry, we do not recognize that command.  Try entering 'help'.");
				}
			}
			// Handle the many possible types of MudException.
			catch (MudException mudException) {
				if (mudException instanceof NoSuchThing) {
					System.err.println("Sorry, there isn't any such item here.");
				} else if (mudException instanceof NoSuchPerson) {
					System.err.println("Sorry, there isn't anyone by that name here.");
				} else if (mudException instanceof NoSuchExit) {
					System.err.println("Sorry, there isn't an exit in that direction.");
				} else if (mudException instanceof NoSuchPlace) {
					System.err.println("Sorry, there isn't any such place.");
				} else if (mudException instanceof ExitAlreadyExists) {
					System.err.println("Sorry, there is already an exit in that direction.");
				} else if (mudException instanceof PlaceAlreadyExists) {
					System.err.println("Sorry, there is already a place with that name.");
				} else if (mudException instanceof LinkFailed) {
					System.err.println("Sorry, that exit is not functioning.");
				} else if (mudException instanceof BadPassword) {
					System.err.println("Sorry, invalid password.");
				} else if (mudException instanceof NotThere) {
					// Shouldn't happen
					System.err.println("Sorry, you can't do that when you're not there.");
				} else if (mudException instanceof AlreadyThere) {
					// Shouldn't happen
					System.err.println("Sorry, you can't go there; you're already there.");
				}
			}
			// Handle RMI exceptions:
			catch (RemoteException mudException) {
				System.err.println("The Multi-User Domain is having technical difficulties.");
				System.err.println("Perhaps the server has crashed:");
				System.err.println(mudException);
			}
			// Handle everything else that could go wrong.
			catch (Exception exception) {
				System.err.print("Sorry, an error in syntax or otherwise:");
				System.err.println(exception);
				System.err.println("Try using the 'help' command.");
			}
		}
	}

	/**
	 * This convenience method is used in several places in the runMud() method
	 * above. It displays the name and description of the current place (including
	 * the name of the MUDthe place is in), and also displays the list of items,
	 * people, and exits in the current place.
	 */
	public static void look(RemoteMudPlace remoteMudPlace) throws RemoteException, MudException {
		// MUD name:
		String mudName = remoteMudPlace.getServer().getMudName();
		// Place name:
		String placeName = remoteMudPlace.getPlaceName();
		// Place description:
		String description = remoteMudPlace.getDescription();
		// List of items here:
		Vector<String> items = remoteMudPlace.getThings();
		// List of people here:
		Vector<String> names = remoteMudPlace.getNames();
		// List of exits from here:
		Vector<String> exits = remoteMudPlace.getExits();

		// Print it all out:
		System.out.println("You are in: " + placeName + " of the Muti-User Domain: " + mudName);
		System.out.println(description);
		System.out.print("Items here: ");
		// Display list of items:
		for (int i = 0; i < items.size(); i++) {
			if (i > 0) {
				System.out.print(", ");
			}
			System.out.print(items.elementAt(i));
		}
		System.out.print("\nPeople here: ");
		// Display list of people:
		for (int i = 0; i < names.size(); i++) {
			if (i > 0) {
				System.out.print(", ");
			}
			System.out.print(names.elementAt(i));
		}
		System.out.print("\nExits are: ");
		// Display list of exits:
		for (int i = 0; i < exits.size(); i++) {
			if (i > 0) {
				System.out.print(", ");
			}
			System.out.print(exits.elementAt(i));
		}
		// Blank line:
		System.out.println();
		// Make it appear now!
		System.out.flush();
	}

	/** This static input stream reads lines from the console */
	static BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));

	/**
	 * A convenience method for prompting the user and getting a line of input. It
	 * guarantees that the line is not empty and strips off whitespace at the
	 * beginning and end of the line.
	 */
	public static String getLine(String prompt) {
		String inputLine = null;
		// Loop until a non-empty line is entered:
		do {
			try {
				// Display prompt:
				System.out.print(prompt);
				// Display it right away:
				System.out.flush();
				// Get a line of input:
				inputLine = consoleInput.readLine();
				// Strip off whitespace:
				if (inputLine != null) {
					inputLine = inputLine.trim();
				}
			} catch (Exception exception) {
				// Ignore any errors.
			}
		} while ((inputLine == null) || (inputLine.length() == 0));
		return inputLine;
	}

	/**
	 * A convenience method for getting multi-line input from the user. It prompts
	 * for the input, displays instructions, and guarantees that the input is not
	 * empty. It also allows the user to enter the name of a file from which text
	 * will be read.
	 */
	public static String getMultiLine(String prompt) {
		String text = "";
		// We'll break out of this loop when we get non-empty input.
		for (;;) {
			try {
				// The stream from which to read:
				BufferedReader bufferedReader = consoleInput;
				// Display the prompt:
				System.out.println(prompt);
				// Display some instructions:
				String instructions = "";
				instructions += "You can enter multiple lines.  ";
				instructions += "End with a '.' on a line by itself.\n";
				instructions += "Or enter a '<<' followed by a filename";

				System.out.println(instructions);
				// Make the prompt and instructions appear now.
				System.out.flush();
				// Read lines:
				String inputLine;
				// Until EOF (End Of File):)
				while ((inputLine = bufferedReader.readLine()) != null) {
					// Or, until we get a dot by itself:
					if (inputLine.equals(".")) {
						break;
					}
					/*
					 * Or, if a file is specified, start reading from it instead of from the
					 * console.
					 */
					if (inputLine.trim().startsWith("<<")) {
						String filename = inputLine.trim().substring(2).trim();
						FileReader fileReader = new FileReader(filename);
						bufferedReader = new BufferedReader(fileReader);
						// Don't count the << as part of the input.
						continue;
					} else {
						// Add the line to the collected input:
						text += inputLine + "\n";
					}
				}

				/*
				 * If we got at least one line, return it. Otherwise, chastise the user and go
				 * back to the prompt and the instructions.
				 */
				if (text.length() > 0) {
					return text;
				} else {
					System.out.println("Please enter at least one line.");
				}
			}
			/*
			 * If there were errors -- for example, an IO error in reading a file -- display
			 * the error, and loop again, displaying prompt and instructions.
			 */
			catch (Exception exception) {
				System.out.println(exception);
			}
		}
	}

	/** This is the usage string that explains the available commands */
	static private String getHelpScreen() {
		String help = "";
		help += "Commands are:\n";
		help += "look: Look around\n";
		help += "examine <item>: examine the named item in more detail\n";
		help += "describe <person>: describe the named person\n";
		help += "go <exit>: go though the named exit\n";
		help += "say <announcement>: make an announcement to everyone\n";
		help += "talk <person>: talk to one person -- will prompt for message\n";
		help += "change: change how you are described -- will prompt for description\n";
		help += "create <item>: create a new item -- will prompt for description \n";
		help += "destroy <item>: destroy an item\n";
		help += "open <exit>: create an exit and the place it leads to -- will prompt for input\n";
		help += "close <exit>: close an exit from this place\n";
		help += "link <existing-place>: create an exit to an existing place,\n";
		help += "     even if it's on a different computer -- will prompt for input.\n";
		help += "dump <filename>: save the MUD (but not the people) -- prompts for password\n";
		help += "quit: leave the Multi-User Domain\n";
		help += "help: display this message";

		return (help);
	}
}
