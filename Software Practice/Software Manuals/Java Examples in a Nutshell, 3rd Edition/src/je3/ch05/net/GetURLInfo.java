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

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 104-105. A class that
 * displays information about a URL.
 */
public class GetURLInfo {

	private static final String usage = "Usage: java GetURLInfo <url>";
	
	/** Use the URLConnection class to get info about the URL */
	public static void printinfo(URL url) throws IOException {
		// Get URLConnection from URL:
		URLConnection connection = url.openConnection();
		// Open a connection to URL:
		connection.connect();

		// Display some information about the URL contents.
		System.out.println("  Host: " + url.getHost());
		System.out.println("  Content Type: " + connection.getContentType());
		System.out.println("  Content Encoding: " + connection.getContentEncoding());
		System.out.println("  Content Length: " + connection.getContentLength());
		System.out.println("  Date: " + new Date(connection.getDate()));
		System.out.println("  Last Modified: " + new Date(connection.getLastModified()));
		System.out.println("  Expiration: " + new Date(connection.getExpiration()));

		// If it is an HTTP connection, display some additional information.
		if (connection instanceof HttpURLConnection) {
			HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
			System.out.println("  Request Method: " + httpURLConnection.getRequestMethod());
			System.out.println("  Response Message: " + httpURLConnection.getResponseMessage());
			System.out.println("  Response Code: " + httpURLConnection.getResponseCode());
		}
	}

	/** Create a URL, and call printinfo() to display information about it. */
	public static void main(String[] args) {
		try {
			printinfo(new URL(args[0]));
		} catch (Exception e) {
			System.err.println(e);
			System.err.println(usage);
		}
	}
}
