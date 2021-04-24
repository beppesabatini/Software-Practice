/*
 * Copyright (c) 2000 David Flanagan.  All rights reserved.
 * This code is from the book Java Examples in a Nutshell, 2nd Edition.
 * It is provided AS-IS, WITHOUT ANY WARRANTY either expressed or implied.
 * You may study, use, and modify it for any non-commercial purpose.
 * You may distribute it non-commercially as long as you retain this notice.
 * For a commercial use license, or to purchase the book (recommended),
 * visit http://www.davidflanagan.com/javaexamples2.
 */
package je3.ch11.gui;

import java.io.IOException;
import java.util.MissingResourceException;

/**
 * Referred to in Java Examples in a Nutshell, 3rd Edition, p. 324. This
 * subclass of MissingResourceException signals that a resource value was
 * present, but could not be properly parsed or otherwise converted to the
 * desired type.
 * <p/>
 * To test this, try mangling some properties in a *.properties file. Then
 * launch the example WebBrowser, which is in this same package, and rotate
 * through the different Themes. Don't check in the mangled properties file!
 */
public class MalformedResourceException extends MissingResourceException {

	private static final long serialVersionUID = -7151784510594154566L;

	public MalformedResourceException(String msg, String type, String key) {
		super(msg, type, key);
	}

	// Convenience constructors: automatically generate exception message.
	public MalformedResourceException(String type, String key) {
		super("Malformed " + type + " resource: " + key, type, key);
	}

	public MalformedResourceException(Class<?> type, String key) {
		this(type.getName(), key);
	}

	public static class Demo {
		public static void main(String[] args) {
			System.out.println("Demo of MalformedResourceException as used by WebBrowser.");
			String instructions = "";
			instructions += "Mangle some strings in WebBrowserResources.properties, ";
			instructions += "load the Browser, and rotate through the Themes.";
			System.out.println(instructions);
			System.out.println();
			try {
				WebBrowser.main(args);
			} catch (IOException ioException) {
				System.err.println("Not a MalformedResourceException: " + ioException.getMessage());
			} catch (MalformedResourceException malformedResourceException) {
				System.err.println(malformedResourceException.getMessage());
				malformedResourceException.printStackTrace(System.err);
				System.exit(1);
			}

		}
	}
}
