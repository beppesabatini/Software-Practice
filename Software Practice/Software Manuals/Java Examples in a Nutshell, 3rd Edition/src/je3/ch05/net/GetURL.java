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

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 103-104. This simple
 * program uses the URL class and its openStream() method to download the
 * contents of a URL and copy them to a file or to the console.
 * <p>
 * This class can only handle the HTTP protocol, which is seldom used by itself
 * any longer. For HTTPS, see (despite the name) the class HttpClient.
 */
public class GetURL {

	private static final String usage = "Usage: java GetURL <URL> [<filename>]";
	
	public static void main(String[] args) {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			// Check the arguments
			if ((args.length != 1) && (args.length != 2)) {
				throw new IllegalArgumentException("Wrong number of args");
			}

			// Now set up the streams.
			// First, create the URL object.
			URL url = new URL(args[0]);
			// Open a stream to it.
			inputStream = url.openStream();
			if (args.length == 2) {
				// Get an appropriate output stream.
				outputStream = new FileOutputStream(args[1]);
			} else {
				outputStream = System.out;
			}

			// Now copy bytes from the URL to the output stream.
			byte[] buffer = new byte[4096];
			int bytesRead;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
			System.out.println("URL saved to: " + args[1]);
		}
		// On exceptions, print error message and usage message.
		catch (Exception exception) {
			System.err.println(exception);
			System.err.println(usage);
		} finally { // Always close the streams, no matter what.
			try {
				inputStream.close();
				outputStream.close();
			} catch (Exception exception) {
			}
		}
	}
}
