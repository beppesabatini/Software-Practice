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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.cert.X509Certificate;

import javax.net.SocketFactory;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 112-115. This program
 * connects to a Web server and downloads the specified URL from it. It uses the
 * HTTP protocol directly, and can also handle HTTPS.
 */
public class HttpClient {

	private final static String usage = "Usage: java HttpClient <URL> [<filename>]";

	public static void main(String[] args) {

		// Check the arguments
		if (args.length < 1 || args.length > 2) {
			System.out.println(usage);
			throw new IllegalArgumentException("Wrong number of arguments");
		}

		// Get an output stream to which to write the URL contents.
		OutputStream toFile = null;
		try {
			toFile = new FileOutputStream(args[1]);
		} catch (FileNotFoundException fileNotFoundException) {
			System.out.println("Sorry, could not open output file: " + args[1]);
			System.err.println(fileNotFoundException);
		}

		/*
		 * Now use the URI class to parse the user-specified URL into its various parts.
		 * The java.net.URI class is new in Java 1.4; it is like URL, but has more
		 * powerful parsing, and does not have built-in networking capability.
		 */
		URI uri = null;

		try {
			uri = new URI(args[0]);
		} catch (URISyntaxException uriSyntaxException) {
			System.err.println("Sorry, bad syntax in URI: " + args[0]);
			uriSyntaxException.printStackTrace(System.err);
		}
		String protocol = uri.getScheme();
		String host = uri.getHost();
		int port = uri.getPort();
		String uriPath = uri.getRawPath();
		if (uriPath == null || uriPath.length() == 0) {
			uriPath = "/";
		}
		String query = uri.getRawQuery();
		if (query != null && query.length() > 0) {
			uriPath += "?" + query;
		}
		// The socket we'll use to communicate.
		Socket socket = null;
		;

		if (protocol.equals("http")) {
			socket = getHttpSocket(host, port);
		} else if (protocol.equals("https")) {
			socket = getHttpsSocket(host, port);
		} else {
			try {
				toFile.close();
			} catch (IOException ioException) {
				System.err.println(ioException);
				ioException.printStackTrace(System.err);
			}
			throw new IllegalArgumentException("URL must use http: or https: protocol");
		}

		/*
		 * We now have either a regular socket or an SSL socket. HTTP and HTTPS are the
		 * same from here on.
		 */

		try {
			sendGetRequest(socket, uriPath, host);
			readServerResponse(socket, toFile);

			// When the server closes the connection, we close our resources as well.
			socket.close();
			toFile.close();
			// Report any errors that arise:
		} catch (IOException exception) {
			exception.printStackTrace(System.err);
			System.err.println(usage);
		}
	}

	private static Socket getHttpSocket(String host, int port) {
		// This is a normal http protocol, create a normal socket.
		// Use the default http port 80.
		if (port == -1) {
			port = 80;
		}
		Socket socket = null;
		try {
			socket = new Socket(host, port);
		} catch (IOException ioException) {
			System.err.println("Sorry, could not open HTTP socket to host: " + host + " and port: " + port);
			ioException.printStackTrace(System.err);
		}
		return (socket);
	}

	private static Socket getHttpsSocket(String host, int port) {
		// For HTTPS we need to create a secure socket.
		if (port == -1) {
			port = 443;
		}
		SocketFactory socketFactory = SSLSocketFactory.getDefault();
		SSLSocket sslSocket = null;
		try {
			sslSocket = (SSLSocket) socketFactory.createSocket(host, port);
		} catch (UnknownHostException unknownHostException) {
			System.err.print("Sorry, unknown host: " + host);
			unknownHostException.printStackTrace(System.err);
		} catch (IOException ioException) {
			System.err.print(ioException.getMessage());
			ioException.printStackTrace(System.err);
		}

		// Get the server's certificate.
		SSLSession sslSession = sslSocket.getSession();
		X509Certificate certificate = null;
		try {
			certificate = (X509Certificate) sslSession.getPeerCertificates()[0];
		} catch (SSLPeerUnverifiedException e) {
			/*
			 * This means there was no certificate, or the certificate was not valid.
			 */
			String message = "Sorry, " + sslSession.getPeerHost() + " did not present a valid certificate.";
			System.err.println(message);
			System.exit(1);
		}

		// Print certificate details.
		String certificateDetails = "";
		certificateDetails += sslSession.getPeerHost() + " has presented a certificate belonging to:\t";
		certificateDetails += "[" + certificate.getSubjectDN() + "]\n";
		certificateDetails += "The certificate was issued by: \t" + "[" + certificate.getIssuerDN() + "]";
		System.out.println(certificateDetails);

		/*
		 * We could ask the user here to confirm that they trust the certificate owner
		 * and issuer before proceeding...
		 */
		return (sslSocket);
	}

	private static void sendGetRequest(Socket socket, String uriPath, String host) throws IOException {
		// Get output stream for the socket.
		PrintWriter toServer = new PrintWriter(socket.getOutputStream());

		/*
		 * Send the HTTP GET command to the Web server, specifying the file. We specify
		 * HTTP 1.0 instead of 1.1 because we don't know how to handle
		 * Transfer-Encoding: chunked in the response.
		 */
		String getRequest = "";
		getRequest += "GET " + uriPath + " HTTP/1.0";
		getRequest += "\r\n";
		getRequest += "Host: " + host;
		getRequest += "\r\n";
		getRequest += "Connection: close";
		getRequest += "\r\n";
		getRequest += "\r\n";

		toServer.print(getRequest);
		// Send it right now!
		toServer.flush();

		System.out.println();
		System.out.println("--- Request Headers: ---");
		System.out.print(getRequest);

	}
	
	private static void readServerResponse(Socket socket, OutputStream toFile) throws IOException {
		// Get input stream for the socket.
		InputStream fromServer = socket.getInputStream();

		byte[] buffer = new byte[8 * 1024];
		int bytesRead;

		/*
		 * Now read the HTTP headers the server returns, and print these to the console.
		 * Read from the server until we've got at least 4K bytes or until we get EOF.
		 * Assume that we'll find the end of the headers somewhere in the first 4K
		 * bytes.
		 */
		int numberOfBytes = 0;
		while (true) {
			bytesRead = fromServer.read(buffer, numberOfBytes, buffer.length - numberOfBytes);
			if (bytesRead == -1) {
				break;
			}
			numberOfBytes += bytesRead;
			if (numberOfBytes >= 4 * 1024) {
				break;
			}
		}

		/*
		 * Loop through the bytes, looking for the \r\n\r\n pattern (13, 10, 13, 10)
		 * that marks the end of the headers
		 */
		int i = 0;
		while (i <= numberOfBytes - 4) {
			if (buffer[i++] == 13 && buffer[i++] == 10 && buffer[i++] == 13 && buffer[i++] == 10) {
				break;
			}
		}
		// If we didn't find the end of the headers, abort.
		if (i > numberOfBytes - 4) {
			toFile.close();
			throw new IOException("The end of the headers was not found in the first " + numberOfBytes + " bytes.");
		}

		/*
		 * Now convert the headers to a Latin-1 string (omitting the final blank line,
		 * which is \r\n) and then print them out to the console.
		 */
		String resposneHeaders = new String(buffer, 0, i - 2, "ISO-8859-1");
		System.out.println();
		System.out.println("--- Response Headers: ---");
		System.out.print(resposneHeaders);

		// Any bytes we read after the headers get written to the file.
		toFile.write(buffer, i, numberOfBytes - i);

		// Now read the rest of the bytes and write to the file.
		while ((bytesRead = fromServer.read(buffer)) != -1) {
			toFile.write(buffer, 0, bytesRead);
		}
	}
}
