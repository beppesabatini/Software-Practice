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
package je3.ch05.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 127-139. This class is a
 * generic framework for a flexible, multi-threaded server. It listens on any
 * number of specified ports, and, when it receives a connection on a port,
 * passes input and output streams to a specified Service object which provides
 * the actual service. It can limit the number of concurrent connections, and
 * logs activity to a specified stream.
 * <p>
 * There is a Run Configuration for this program with the standard name,
 * matching the class name, "Server." In that Configuration, each bundled
 * service is associated with a port number:
 * 
 * <pre>
   • 2725: Control, an admin tool. This will prompt for the password (which is 
           "password"). If your password is accepted, enter "help" for 
           more commands.
   • 2726: Time. Displays the current time. 
   • 2727: Reverse. Prompts the user for input and displays that input reversed.
   • 2728: HTTPMirror. Echos back any HTTP Get requests it received.
   • 2729: Unique ID. Prints a unique ID to the console.
 * </pre>
 * 
 * To test these, launch the Run Configuration for Server and leave it running.
 * Then locate the Run Configuration for {@link GenericClient}, and edit it to
 * include the port number of the service you wish to test. Launch GenericClient
 * and follow the prompts. You can run GenericClient from the command line as
 * well of course.
 */
public class Server {

	private static final String usage = "Usage: java Server [-control <password> <port>] [<servicename> <port> ... ]";

	// private static final String DEBUG = "true";

	/**
	 * This is the main() method for running the server as a standalone program. The
	 * command-line arguments to the program should be pairs of service names and
	 * port numbers. For each pair, the program will dynamically load the named
	 * Service class, instantiate it, and tell the server to provide that Service on
	 * the specified port. The special -control argument should be followed by a
	 * password and port, and will start special server control service running on
	 * the specified port, protected by the specified password.
	 */
	public static void main(String[] args) {
		try {
			// Check number of arguments
			if (args.length < 2) {
				throw new IllegalArgumentException("Must specify a service");
			}

			/**
			 * Create a server object that has a limit of 10 concurrent connections, and
			 * logs to a Logger at the Level.INFO level. Prior to Java 1.4 we did this:
			 * <p>
			 * new Server(System.out, 10);
			 */
			Server server = new Server(Logger.getLogger(Server.class.getName()), Level.INFO, 10);

			// Now parse the argument list.
			int i = 0;
			while (i < args.length) {
				// Handle the -control argument.
				if (args[i].equals("-control")) {
					i++;
					String password = args[i++];
					int port = Integer.parseInt(args[i++]);
					// Add the control service:
					server.addService(new Control(server, password), port);
				} else {
					/*
					 * Otherwise, start a named service on the specified port. Dynamically load and
					 * instantiate a Service class.
					 */
					String serviceName = args[i++];
					Class<?> serviceClass = Class.forName(serviceName);
					Service service = (Service) serviceClass.getDeclaredConstructor().newInstance();
					int port = Integer.parseInt(args[i++]);
					server.addService(service, port);
				}
			}
			// Display a message if anything goes wrong.
		} catch (Exception exception) {
			System.err.println("Server: " + exception);
			System.err.println(usage);
			exception.printStackTrace(System.err);
			System.exit(1);
		}
	}

	/* These variables capture the state of the server. */
	// A Hashtable mapping port numbers to Listeners:
	Map<Integer, Listener> services;
	// The set of current connections:
	Set<Connection> connections;
	// The concurrent connection limit:
	int maxConnections;
	// The ThreadGroup for all our threads:
	ThreadGroup threadGroup;

	/*
	 * This class was originally written to send logging output to a stream. It has
	 * been retrofitted to also support the java.util.logging API of Java 1.4. You
	 * can use either, neither, or both.
	 */
	// Where we send our logging output:
	PrintWriter logStream;
	// A Java 1.4 logging destination:
	Logger logger;
	// The level at which to log messages:
	Level logLevel;

	/**
	 * This is the Server() constructor. It must be passed a stream to which to send
	 * log output (pass it null to send the output nowhere). The constructor also
	 * needs the limit on the number of concurrent connections.
	 */
	public Server(OutputStream logStream, int maxConnections) {
		this(maxConnections);
		setLogStream(logStream);
		log("Starting server");
	}

	/**
	 * This constructor added to support logging with the Java 1.4 Logger class.
	 */
	public Server(Logger logger, Level logLevel, int maxConnections) {
		this(maxConnections);
		setLogger(logger, logLevel);
		log("Starting server");
	}

	/**
	 * This constructor supports no logging.
	 */
	public Server(int maxConnections) {
		threadGroup = new ThreadGroup(Server.class.getName());
		this.maxConnections = maxConnections;
		services = new HashMap<Integer, Listener>();
		connections = new HashSet<Connection>(maxConnections);
	}

	/**
	 * A public method to set the current logging stream. Pass the method null to
	 * turn logging off.
	 */
	public synchronized void setLogStream(OutputStream outputStream) {
		if (outputStream != null) {
			logStream = new PrintWriter(outputStream);
		} else {
			logStream = null;
		}
	}

	/**
	 * Set the current Logger and logging level. Pass the method null to turn
	 * logging off.
	 */
	public synchronized void setLogger(Logger logger, Level level) {
		this.logger = logger;
		this.logLevel = level;
	}

	/** Write the specified string to the log */
	protected synchronized void log(String loggedString) {
		if (logger != null) {
			logger.log(logLevel, loggedString);
		}
		if (logStream != null) {
			logStream.println("[" + new Date() + "] " + loggedString);
			logStream.flush();
		}
	}

	/** Write the specified object to the log */
	protected void log(Object object) {
		log(object.toString());
	}

	/**
	 * This method makes the server start providing a new service. It runs the
	 * specified Service object on the specified port.
	 */
	public synchronized void addService(Service service, int port) throws IOException {
		// The hashtable key:
		Integer key = port;
		// Check whether a service is already on that port.
		if (services.get(key) != null) {
			throw new IllegalArgumentException("Port " + port + " already in use.");
		}
		// Create a Listener object to listen for connections on the port.
		Listener listener = new Listener(threadGroup, port, service);
		// Store it in the hashtable.
		services.put(key, listener);
		// Log the new service starting.
		log("Starting service " + service.getClass().getName() + " on port " + port);
		// Start the listener running.
		listener.start();
	}

	/**
	 * This method makes the server stop providing a service on a specified port. It
	 * does not terminate any pending connections to that service, but merely causes
	 * the server to stop accepting new connections
	 */
	public synchronized void removeService(int port) {
		// The key for the hashtable:
		Integer key = port;
		// Look up the Listener object for the port in the hashtable:
		final Listener listener = services.get(key);
		if (listener == null) {
			return;
		}
		// Ask the listener to stop:
		listener.pleaseStop();
		// Remove it from the hashtable:
		services.remove(key);
		// ...and log it.
		log("Stopping service " + listener.service.getClass().getName() + " on port " + port);
	}

	/**
	 * This nested Thread subclass is a "Listener". It listens for connections on a
	 * specified port (using a ServerSocket), and when it gets a connection request,
	 * it calls the server's addConnection() method to accept (or reject) the
	 * connection. There is one Listener for each Service being provided by the
	 * Server.
	 */
	public class Listener extends Thread {
		// The socket to listen for connections:
		ServerSocket listenSocket;
		// The port we're listening on:
		int port;
		// The service to provide on that port:
		Service service;
		// Whether we've been asked to stop:
		volatile boolean stop = false;

		/**
		 * The Listener constructor creates a thread for itself in the ThreadGroup. It
		 * creates a ServerSocket to listen for connections on the specified port. It
		 * arranges for the ServerSocket to be interruptible, so that services can be
		 * removed from the server.
		 */
		public Listener(ThreadGroup group, int port, Service service) throws IOException {
			super(group, "Listener:" + port);
			listenSocket = new ServerSocket(port);
			// Give the new socket a non-zero timeout so accept() can be interrupted.
			listenSocket.setSoTimeout(5000);
			this.port = port;
			this.service = service;
		}

		/**
		 * This is the polite way to get a Listener to stop accepting connections.
		 */
		public void pleaseStop() {
			// Set the stop flag:
			this.stop = true;
			// Stop blocking in accept():
			this.interrupt();
			try {
				// Stop listening:
				listenSocket.close();
			} catch (IOException e) {
				// ignore
			}
		}

		/**
		 * A Listener is a Thread, and this is its body. Wait for connection requests,
		 * accept them, and pass the socket on to the addConnection method of the
		 * server.
		 */
		@Override
		public void run() {
			// Loop until we're asked to stop.
			while (!stop) {
				try {
					Socket client = listenSocket.accept();
					addConnection(client, service);
				} catch (InterruptedIOException interruptedIOException) {
					// Safe to ignore this.
				} catch (IOException ioException) {
					log(ioException);
				}
			}
		}
	}

	/**
	 * This is the method that Listener objects call when they accept a connection
	 * from a client. It either creates a Connection object for the connection and
	 * adds it to the list of current connections, or, if the limit on connections
	 * has been reached, it closes the connection.
	 */
	protected synchronized void addConnection(Socket socket, Service service) {
		// If the connection limit has been reached:
		if (connections.size() >= maxConnections) {
			try {
				// Then tell the client it is being rejected.
				PrintWriter out = new PrintWriter(socket.getOutputStream());
				out.print("Connection refused; " + "the server is busy; please try again later.\r\n");
				out.flush();
				// ...and close the connection to the rejected client:
				socket.close();
				// ...and log it, of course.
				String message = "";
				message += "Connection refused to " + socket.getInetAddress().getHostAddress() + ":";
				message += socket.getPort() + ": max connections reached.";
				log(message);
			} catch (IOException ioException) {
				log(ioException);
			}
		} else {
			/**
			 * Otherwise, if the limit has not been reached, create a Connection thread to
			 * handle this connection.
			 */
			Connection connection = new Connection(socket, service);
			// Add it to the list of current connections:
			connections.add(connection);
			// Log this new connection:
			String connectionMessage = "";
			connectionMessage += "Connected to " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort();
			connectionMessage += " on port " + socket.getLocalPort();
			connectionMessage += " for service " + service.getClass().getName();
			log(connectionMessage);
			// ...and, start the Connection thread to provide the service.
			connection.start();
		}
	}

	/**
	 * A Connection thread calls this method just before it exits. It removes the
	 * specified Connection from the set of connections.
	 */
	protected synchronized void endConnection(Connection connection) {
		connections.remove(connection);
		String message = "";
		message += "Connection to " + connection.client.getInetAddress().getHostAddress() + ":";
		message += connection.client.getPort() + " closed.";
		log(message);
	}

	/** Change the current connection limit. */
	public synchronized void setMaxConnections(int max) {
		maxConnections = max;
	}

	/**
	 * This method displays status information about the server on the specified
	 * stream. It can be used for debugging, and is used by the Control service
	 * later in this example.
	 */
	public synchronized void displayStatus(PrintWriter out) {
		// Display a list of all Services that are being provided.
		Iterator<Integer> keys = services.keySet().iterator();
		while (keys.hasNext()) {
			Integer port = keys.next();
			Listener listener = services.get(port);
			out.print("SERVICE " + listener.service.getClass().getName() + " ON PORT " + port + "\r\n");
		}

		// Display the current connection limit.
		out.print("MAX CONNECTIONS: " + maxConnections + "\r\n");

		// Display a list of all current connections.
		Iterator<Connection> connectionDispenser = connections.iterator();
		while (connectionDispenser.hasNext()) {
			Connection connection = connectionDispenser.next();
			String status = "";
			status += "CONNECTED TO ";
			status += connection.client.getInetAddress().getHostAddress() + ":" + connection.client.getPort() + " ";
			status += "ON PORT " + connection.client.getLocalPort() + " ";
			status += "FOR SERVICE " + connection.service.getClass().getName();
			status += "\r\n";
			out.print(status);
		}
	}

	/**
	 * This class is a subclass of Thread that handles an individual connection
	 * between a client and a Service provided by this server. Because each such
	 * connection has a thread of its own, each Service can have multiple
	 * connections pending at once. Despite all the other threads in use, this is
	 * the key feature that makes this a multi-threaded server implementation.
	 */
	public class Connection extends Thread {
		// The socket through which to talk to the client.
		Socket client;
		// The service being provided to that client.
		Service service;

		/**
		 * This constructor merely saves some state and calls the superclass constructor
		 * to create a thread to handle the connection. Connection objects are created
		 * by Listener threads. These threads are part of the server's ThreadGroup, so
		 * all Connection threads are part of that group, too.
		 */
		public Connection(Socket client, Service service) {
			super("Server.Connection:" + client.getInetAddress().getHostAddress() + ":" + client.getPort());
			this.client = client;
			this.service = service;
		}

		/**
		 * This is the body of each and every Connection thread. All it does is pass the
		 * client input and output streams to the serve() method of the specified
		 * Service object. That method is responsible for reading from and writing to
		 * those streams to provide the actual service.
		 * <p/>
		 * Recall that the Service object has been passed from the Server.addService()
		 * method to a Listener object to the addConnection() method to this Connection
		 * object, and is now finally being used to provide the service. Note that just
		 * before this thread exits, it always calls the endConnection() method to
		 * remove itself from the set of connections.
		 */
		@Override
		public void run() {
			try {
				InputStream clientInputStream = client.getInputStream();
				OutputStream clientOutputStream = client.getOutputStream();
				service.serve(clientInputStream, clientOutputStream);
			} catch (IOException ioException) {
				log(ioException);
			} finally {
				endConnection(this);
			}
		}
	}

	/**
	 * Here is the Service interface of which we have seen so much. It defines only
	 * a single method which is invoked to provide the service. The serve() function
	 * will pass an input stream and an output stream to the client. The client
	 * should do whatever it wants with the streams, and should close them before
	 * returning.
	 * <p>
	 * All connections through the same port to this service share a single Service
	 * object. Thus, any state local to an individual connection must be stored in
	 * local variables within the serve() method. State that should be global to all
	 * connections on the same port should be stored in instance variables of the
	 * Service class. If the same Service is running on more than one port, there
	 * will typically be different Service instances for each port. Data that should
	 * be global to all connections on any port should be stored in static
	 * variables.
	 * <p>
	 * Note that implementations of this interface must have a no-argument
	 * constructor if they are to be dynamically instantiated by the main() method
	 * of the Server class.
	 */
	public interface Service {
		public void serve(InputStream in, OutputStream out) throws IOException;
	}

	/**
	 * A very simple service. It displays the current time on the server to the
	 * client, and closes the connection.
	 */
	public static class Time implements Service {
		@Override
		public void serve(InputStream clientInputStream, OutputStream clientOutputStream) throws IOException {
			PrintWriter clientOutputPrintWriter = new PrintWriter(clientOutputStream);
			clientOutputPrintWriter.print(new Date() + "\r\n");
			clientOutputPrintWriter.close();
			clientInputStream.close();
		}
	}

	/**
	 * This is another example service. It reads lines of input from the client, and
	 * sends them back, reversed. It also displays a welcome message and
	 * instructions, and closes the connection when the user enters a '.' on a line
	 * by itself.
	 */
	public static class Reverse implements Service {
		@Override
		public void serve(InputStream clientInputStream, OutputStream clientOutputStream) throws IOException {
			BufferedReader clientInputReader = new BufferedReader(new InputStreamReader(clientInputStream));
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(clientOutputStream);
			PrintWriter clientOutputWriter = new PrintWriter(new BufferedWriter(outputStreamWriter));
			clientOutputWriter.print("Welcome to the line reversal server.\r\n");
			clientOutputWriter.print("Enter lines.  End with a '.' on a line by itself.\r\n");
			for (;;) {
				clientOutputWriter.print("> ");
				clientOutputWriter.flush();
				String inputLine = clientInputReader.readLine();
				if ((inputLine == null) || inputLine.equals(".")) {
					break;
				}
				for (int j = inputLine.length() - 1; j >= 0; j--) {
					clientOutputWriter.print(inputLine.charAt(j));
				}
				clientOutputWriter.print("\r\n");
			}
			clientOutputWriter.close();
			clientInputReader.close();
		}
	}

	/**
	 * This service is an HTTP mirror, just like the {@link HttpMirror} class
	 * implemented earlier in this chapter. It echos back the client's HTTP request.
	 */
	public static class HTTPMirror implements Service {
		@Override
		public void serve(InputStream clientInputStream, OutputStream clientOutputStream) throws IOException {
			BufferedReader clientInputReader = new BufferedReader(new InputStreamReader(clientInputStream));
			PrintWriter clientOutputWriter = new PrintWriter(clientOutputStream);
			clientOutputWriter.print("HTTP/1.0 200\r\n");
			clientOutputWriter.print("Content-Type: text/plain\r\n\r\n");
			String line;
			while ((line = clientInputReader.readLine()) != null) {
				if (line.length() == 0)
					break;
				clientOutputWriter.print(line + "\r\n");
			}
			clientOutputWriter.close();
			clientInputReader.close();
		}
	}

	/**
	 * This service demonstrates how to maintain state across connections by saving
	 * it in instance variables, and using synchronized access to those variables.
	 * It maintains a count of how many clients have connected, and tells each
	 * client what number it is.
	 */
	public static class UniqueID implements Service {
		public int id = 0;

		public synchronized int nextId() {
			return id++;
		}

		@Override
		public void serve(InputStream clientInputStream, OutputStream clientOutputStream) throws IOException {
			PrintWriter clientOutputWriter = new PrintWriter(clientOutputStream);
			clientOutputWriter.print("You are client #: " + nextId() + "\r\n");
			clientOutputWriter.close();
			clientInputStream.close();
		}
	}

	/**
	 * This is a non-trivial service. It implements a command-based protocol that
	 * gives password-protected runtime control over the operation of the server.
	 * See the main() method of the Server class to see how this service is started.
	 * <p>
	 * The recognized commands are:
	 * 
	 * <pre>
	 *   password: give password; authorization is required for most commands
	 *   add:      dynamically add a named service on a specified port
	 *   remove:   dynamically remove the service running on a specified port
	 *   max:      change the current maximum connection limit.
	 *   status:   display current services, connections, and connection limit
	 *   help:     display a help message
	 *   quit:     disconnect
	 * </pre>
	 * 
	 * This service displays a prompt, and sends all of its output to the user in
	 * capital letters. Only one client is allowed to connect to this service at a
	 * time.
	 */
	public static class Control implements Service {
		// The server we control:
		Server server;
		// The password we require:
		String password;
		// Whether a client is already connected:
		boolean connected = false;

		/**
		 * Create a new Control service. It will control the specified Server object,
		 * and will require the specified password for authorization Note that this
		 * Service does not have a no argument constructor, which means that it cannot
		 * be dynamically instantiated and added as the other, generic services above
		 * can be.
		 */
		public Control(Server server, String password) {
			this.server = server;
			this.password = password;
		}

		// Has the user has given the password yet?
		static boolean authorized = false;

		/**
		 * This is the serve method that provides the service. It reads a line the
		 * client, and uses java.util.StringTokenizer to parse it into commands and
		 * arguments. It does various things depending on the command.
		 */
		@Override
		public void serve(InputStream clientInputStream, OutputStream clientOutputStream) throws IOException {
			// Setup the streams
			BufferedReader clientInputReader = new BufferedReader(new InputStreamReader(clientInputStream));
			PrintWriter clientOutputWriter = new PrintWriter(clientOutputStream);
			Control.authorized = false;
			// For reading client input lines:
			String inputLine;

			/*
			 * If there is already a client connected to this service, display a message to
			 * this client and close the connection. We use a synchronized block to prevent
			 * a race condition.
			 */
			synchronized (this) {
				if (connected) {
					clientOutputWriter.print("ONLY ONE CONTROL CONNECTION ALLOWED.\r\n");
					clientOutputWriter.close();
					return;
				} else {
					connected = true;
				}
			}

			// This is the main loop: read a command, parse it, and handle it.
			for (;;) { // Start an endless loop.
				// Display a prompt:
				clientOutputWriter.print("> ");
				// Make it appear right away:
				clientOutputWriter.flush();
				// Get the user's input:
				inputLine = clientInputReader.readLine();
				// Quit if we get EOF:
				if (inputLine == null) {
					break;
				}
				try {
					boolean complete = processCommands(password, clientOutputWriter, inputLine, server);
					if (complete == true) {
						break;
					}
				} catch (Exception exception) {
					/*
					 * If an exception occurred during the command, print an error message, then
					 * output details of the exception.
					 */
					clientOutputWriter.print("ERROR WHILE PARSING OR EXECUTING COMMAND:\r\n" + exception + "\r\n");
				}
			}
			/*
			 * Finally, when the command loop ends, close the streams and set our connected
			 * flag to false so that other clients can now connect.
			 */
			connected = false;
			clientOutputWriter.close();
			clientInputReader.close();
		}
	}

	private static boolean processCommands(String password, PrintWriter clientOutputWriter, String inputLine,
			Server server) throws Exception {

		StringTokenizer stringTokenizer = new StringTokenizer(inputLine);
		// If input was empty:
		if (stringTokenizer.hasMoreTokens() == false) {
			// Processing is complete.
			return true;
		}
		// Get first word of the input and convert it to lower case.
		String command = stringTokenizer.nextToken().toLowerCase();
		/*
		 * Now compare to each of the possible commands, doing the appropriate thing for
		 * each command.
		 */

		switch (command) {
			case "password": {
				passwordCommand(stringTokenizer, password, clientOutputWriter);
				break;
			}
			case "add": {
				addCommand(stringTokenizer, clientOutputWriter, server);
				break;
			}
			case "remove": {
				removeCommand(stringTokenizer, clientOutputWriter, server);
				break;
			}
			case "max": {
				maxCommand(clientOutputWriter, stringTokenizer, server);
				break;
			}
			case "status": {
				statusCommand(clientOutputWriter, server);
				break;
			}
			case "help":
			case "commands":
				helpCommand(clientOutputWriter);
				break;
			case "quit": {
				// Processing is complete.
				return (true);
			}
			default: {
				clientOutputWriter.print("UNRECOGNIZED COMMAND\r\n");
				break;
			}
		}
		// More commands to come.
		return (false);
	}

	private static void passwordCommand(StringTokenizer tokenizer, String password, PrintWriter clientOutputWriter) {
		// Get the next word:
		String nextToken = tokenizer.nextToken();
		// Is it the password?
		if (nextToken.equals(password)) {
			clientOutputWriter.print("OK\r\n");
			Control.authorized = true;
		} else {
			Control.authorized = false;
			clientOutputWriter.print("INVALID PASSWORD\r\n");
		}
	}

	private static void addCommand(StringTokenizer stringTokenizer, PrintWriter clientOutputWriter, Server server)
			throws Exception {
		// If adding a service has been requested:
		// Check whether password has been given.
		if (Control.authorized == false) {
			clientOutputWriter.print("PASSWORD REQUIRED\r\n");
		} else {
			/*
			 * Get the name of the service and try to dynamically load and instantiate it.
			 * Exceptions will be handled below.
			 */
			String serviceName = stringTokenizer.nextToken();
			Class<?> serviceClass = Class.forName(serviceName);
			Service service;
			try {
				service = (Service) serviceClass.getDeclaredConstructor().newInstance();
			} catch (NoSuchMethodError e) {
				throw new IllegalArgumentException("Service must have a no-argument constructor");
			}
			int port = Integer.parseInt(stringTokenizer.nextToken());
			// If no exceptions occurred, add the service.
			server.addService(service, port);
			clientOutputWriter.print("SERVICE ADDED\r\n");
		}
	}

	private static void removeCommand(StringTokenizer stringTokenizer, PrintWriter clientOutputWriter, Server server) {
		// If removing a service has been requested:
		if (Control.authorized == false) {
			clientOutputWriter.print("PASSWORD REQUIRED\r\n");
		} else {
			int port = Integer.parseInt(stringTokenizer.nextToken());
			server.removeService(port);
			clientOutputWriter.print("SERVICE REMOVED\r\n");
		}
	}

	private static void maxCommand(PrintWriter clientOutputWriter, StringTokenizer stringTokenizer, Server server) {
		// If setting a maximum connection limit has been requested:
		if (Control.authorized == false) {
			clientOutputWriter.print("PASSWORD REQUIRED\r\n");
		} else {
			int max = Integer.parseInt(stringTokenizer.nextToken());
			server.setMaxConnections(max);
			clientOutputWriter.print("MAX CONNECTIONS CHANGED\r\n");
		}
	}

	private static void statusCommand(PrintWriter clientOutputWriter, Server server) {
		// If displaying the status has been requested:
		if (Control.authorized == false) {
			clientOutputWriter.print("PASSWORD REQUIRED\r\n");
		} else {
			server.displayStatus(clientOutputWriter);
		}
	}

	private static void helpCommand(PrintWriter clientOutputWriter) {
		// Display command syntax. A password is not required to see this.

		String commands = "";
		commands += "COMMANDS:";
		commands += "\r\n";
		commands += "\t";
		commands += "password <password>";
		commands += "\r\n";
		commands += "\t";
		commands += "add <service> <port>";
		commands += "\r\n";
		commands += "\t";
		commands += "remove <port>";
		commands += "\r\n";
		commands += "\t";
		commands += "max <max-connections>";
		commands += "\r\n";
		commands += "\t";
		commands += "status";
		commands += "\r\n";
		commands += "\t";
		commands += "help";
		commands += "\r\n";
		commands += "\t";
		commands += "quit";
		commands += "\r\n";

		clientOutputWriter.print(commands);
	}
}
