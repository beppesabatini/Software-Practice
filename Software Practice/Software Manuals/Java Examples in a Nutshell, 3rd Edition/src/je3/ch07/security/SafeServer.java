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
package je3.ch07.security;

import java.net.URL;
import je3.ch05.net.Server;
import utils.LearningJava3Utils;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 189-190. This class is a
 * program that uses the Server class defined in Chapter 5. Server would load
 * arbitrary "Service" classes to provide services. This class is an alternative
 * program to start up a Server in a similar way. The difference is that this
 * one uses a SecurityManager and a ClassLoader to prevent the Service classes
 * from doing anything damaging or malicious on the local system. This allows us
 * to safely run Service classes that come from untrusted sources.
 * <p/>
 * Java seems to no longer support the security.policy features quite exactly as
 * described in the manual. Not able to implement them or test them at this time.
 */
public class SafeServer {
	public static void main(String[] args) {

		LearningJava3Utils.confirmContinueWithDisfunctional();
		
		/*
		 * Install a Security manager, if the user didn't already install one with the
		 * -Djava.security.manager argument.
		 */
		if (System.getSecurityManager() == null) {
			System.out.println("Establishing a security manager");
			System.setSecurityManager(new SecurityManager());
		}

		// Create a Server object.
		Server server = new Server(null, 5);

		ClassLoader classLoader = null;

		try {
			/*
			 * Create the ClassLoader that we'll use to load Service classes. The classes
			 * should be stored in the JAR file or the directory specified as a URL by the
			 * first command-line argument
			 */
			URL serviceURL = new URL(args[0]);
			classLoader = new java.net.URLClassLoader(new URL[] { serviceURL });

			/*
			 * Parse the argument list, which should contain Service name/port pairs. For
			 * each pair, load the named Service using the class loader, then instantiate it
			 * with newInstance(), then tell the server to start running it.
			 */
			int i = 1;
			while (i < args.length) {
				// Dynamically load the Service class using the class loader.
				Class<?> serviceClass = classLoader.loadClass(args[i++]);
				// Dynamically instantiate the class.
				Server.Service service = (Server.Service) serviceClass.getDeclaredConstructor().newInstance();
				// Parse the port number.
				int port = Integer.parseInt(args[i++]);
				// Run the service.
				server.addService(service, port);
			}
		} catch (Exception e) {
			// Display a message if anything goes wrong.
			System.err.println(e);
			String usage = "";
			usage += "Usage: java " + SafeServer.class.getName() + " <url> <servicename> <port>\n";
			usage += "\t[<servicename> <port> ... ]";
			System.err.println(usage);
			System.exit(1);
		} finally {
			classLoader = null;
		}
	}
}
