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
package je3.ch06.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import je3.ch05.net.HttpClient;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 177-183. This class
 * manages asynchronous HTTP GET downloads and demonstrates non-blocking I/O
 * with SocketChannel and Selector, and also demonstrates logging with the
 * java.util.logging package. This example uses a number of inner classes and
 * interfaces.
 * <p/>
 * Call download() for each HTTP GET request you want to issue. You may
 * optionally pass a Listener object that will be notified when the download
 * terminates or encounters an exception. The download() function returns a
 * Download object which holds the downloaded bytes (including the HTTP headers)
 * and which allows you to poll the Status of the download. Call release() when
 * there are no more downloads.
 * <p/>
 * This program only supports HTTP, which is rarely used by itself any longer.
 * See {@link HttpClient} for a program which can download over HTTPS.
 */
public class HttpDownloadManager extends Thread {
	// An enumerated type. Values are returned by the Download.getStatus() function.
	public static class Status {
		// We haven't connected to the server yet.
		public static final Status UNCONNECTED = new Status("Unconnected");
		// We're connected to the server, sending a request or receiving a response.
		public static final Status CONNECTED = new Status("Connected");
		// A response has been received. HTTP error messages count as responses.
		public static final Status DONE = new Status("Done");
		// Something went wrong: a bad hostname, for example.
		public static final Status ERROR = new Status("Error");

		private final String name;

		private Status(String name) {
			this.name = name;
		}

		public String toString() {
			return name;
		}
	}

	// Everything you need to know about a pending download.
	public interface Download {
		// The hostname we're downloading from.
		public String getHost();

		// This defaults to port 80.
		public int getPort();

		// This includes the query string as well.
		public String getPath();

		// The status of the download.
		public Status getStatus();

		// The downloaded data, including its response headers.
		public byte[] getData();

		// We should only call this when the status is DONE.
		public int getHttpStatus();
	}

	// Implement this interface if you want to know when a download completes.
	public interface Listener {
		public void done(Download download);

		public void error(Download download, Throwable throwable);
	}

	// For managing multiple non-blocking I/O Channels.
	Selector selector;
	// A shared buffer for downloads.
	ByteBuffer buffer;
	// Downloads that don't have a Channel yet.
	List<Download> pendingDownloads;
	// Set this to true when the release() method is called.
	boolean released = false;
	// Logging output goes to this log.
	Logger log;

	// The HTTP protocol uses this character encoding.
	static final Charset LATIN1 = Charset.forName("ISO-8859-1");

	public HttpDownloadManager(Logger log) throws IOException {
		if (log == null) {
			log = Logger.getLogger(this.getClass().getName());
		}
		this.log = log;
		// Create a Selector.
		selector = Selector.open();
		// Allocate the main buffer.
		buffer = ByteBuffer.allocateDirect(64 * 1024);
		pendingDownloads = Collections.synchronizedList(new ArrayList<Download>());
		// The current class is subclass of thread; start it.
		this.start();
	}

	/*
	 * Ask the HttpDownloadManager to begin a download. Returns a Download object
	 * that can be used to poll the progress of the download. The optional Listener
	 * object will be notified when the download completes or aborts.
	 */
	public Download download(URI uri, Listener listener) throws IOException {
		if (released) {
			throw new IllegalStateException("Can't download() after release()");
		}

		// Get info from the URI.
		String scheme = uri.getScheme();
		if (scheme == null || !scheme.equals("http")) {
			throw new IllegalArgumentException("Must use 'http:' protocol");
		}
		String hostname = uri.getHost();
		int port = uri.getPort();
		// Use the default port if none is specified.
		if (port == -1) {
			port = 80;
		}
		String path = uri.getRawPath();
		if (path == null || path.length() == 0) {
			path = "/";
		}
		String query = uri.getRawQuery();
		if (query != null) {
			path += "?" + query;
		}

		// Create a Download object with the pieces of the URL.
		Download download = new DownloadImpl(hostname, port, path, listener);

		// Add it to the list of pending downloads. This is a synchronized list.
		pendingDownloads.add(download);

		/*
		 * Also ask the thread to stop blocking in the select() call, so that it will
		 * notice and process this new pending Download object.
		 */
		selector.wakeup();

		// Return the Download so that the caller can monitor it if desired.
		return download;
	}

	public void release() {
		// The thread will terminate when it notices this flag.
		released = true;
		// This will wake the thread up.
		try {
			selector.close();
		} catch (IOException e) {
			log.log(Level.SEVERE, "Error closing selector", e);
		}
	}

	public void run() {
		log.info("HttpDownloadManager thread starting.");

		// The download thread runs until release() is called.
		while (!released) {
			// The thread blocks here, waiting for something to happen.
			try {
				selector.select();
			} catch (IOException e) {
				// We should never get here.
				log.log(Level.SEVERE, "Error in select()", e);
				return;
			}

			// If release() was called, the thread should exit.
			if (released) {
				break;
			}

			// If any new Download objects are pending, deal with them first.
			if (pendingDownloads.isEmpty() == false) {
				/*
				 * Although pendingDownloads is a synchronized list, we still need to use a
				 * synchronized block to iterate through its elements to prevent a concurrent
				 * call to download().
				 */
				synchronized (pendingDownloads) {
					Iterator<Download> iter = pendingDownloads.iterator();
					while (iter.hasNext()) {
						// Get the pending download object from the list...
						DownloadImpl download = (DownloadImpl) iter.next();
						// ...and remove it.
						iter.remove();

						/*
						 * Now begin an asynchronous connection to the specified host and port. We don't
						 * block while waiting to connect.
						 */
						SelectionKey key = null;
						SocketChannel channel = null;
						try {
							// Open an unconnected channel.
							channel = SocketChannel.open();
							// Put it in non-blocking mode.
							channel.configureBlocking(false);
							/*
							 * Register it with the selector, specifying that we want to know when it is
							 * ready to connect, and when it is ready to read.
							 */
							key = channel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_CONNECT, download);
							// Create the web server address.
							SocketAddress address = new InetSocketAddress(download.host, download.port);
							// Ask the channel to start connecting.
							// Note that we don't send the HTTP request yet.
							// We'll do that when the connection completes.
							channel.connect(address);
						} catch (Exception e) {
							handleError(download, channel, key, e);
						}
					}
				}
			}

			// Now get the set of keys that are ready for connecting or reading.
			Set<SelectionKey> keys = selector.selectedKeys();
			// A potential bug workaround. This test currently is not needed.
			if (keys == null) {
				continue;
			}
			// Loop through the keys in the set.
			for (Iterator<SelectionKey> i = keys.iterator(); i.hasNext();) {
				SelectionKey key = i.next();
				// Remove the key from the set before handling.
				i.remove();

				// Get the Download object we attached to the key.
				DownloadImpl download = (DownloadImpl) key.attachment();
				// Get the channel associated with the key.
				SocketChannel channel = (SocketChannel) key.channel();

				try {
					if (key.isConnectable()) {
						/*
						 * If the channel is ready to connect, complete the connection and then send the
						 * HTTP GET request to it.
						 */
						if (channel.finishConnect()) {
							download.status = Status.CONNECTED;
							// This is the HTTP request we are sending.
							String request = "";
							request += "GET " + download.path + " HTTP/1.1\r\n";
							request += "Host: " + download.host + "\r\n";
							request += "Connection: close\r\n";
							request += "\r\n";
							// Wrap the request in a CharBuffer, and encode it to form a ByteBuffer.
							ByteBuffer requestBytes = LATIN1.encode(CharBuffer.wrap(request));
							/*
							 * Send the request to the server. If the bytes aren't all written in one call,
							 * we busy loop!
							 */
							while (requestBytes.hasRemaining()) {
								channel.write(requestBytes);
							}

							log.info("Sent HTTP request: " + download.host + ":" + download.port + ": " + request);
						}
					}

					if (key.isReadable()) {
						/*
						 * If the key indicates that there is data to be read, then read it and store it
						 * in the Download object.
						 */
						int numbytes = channel.read(buffer);

						/*
						 * If we read some bytes, store them; otherwise the download is complete and we
						 * need to note this.
						 */
						if (numbytes != -1) {
							// Prepare to drain the buffer.
							buffer.flip();
							// Store the data.
							download.addData(buffer);
							// Prepare for another read.
							buffer.clear();
							log.info("Read " + numbytes + " bytes from " + download.host + ":" + download.port);
						} else {
							// If there are no more bytes to read, we're done with the key.
							key.cancel();
							// And, done with the channel.
							channel.close();
							download.status = Status.DONE;
							// Notify the listener.
							if (download.listener != null) {
								download.listener.done(download);
							}
							log.info("Download from " + download.host + ":" + download.port + "is complete");
						}
					}
				} catch (Exception e) {
					handleError(download, channel, key, e);
				}
			}
		}
		log.info("HttpDownloadManager thread exiting.");
	}

	/*
	 * This is the error handling code used by the run() method: Set the status,
	 * close the Channel, cancel the key, log the error, and notify the listener.
	 */
	void handleError(DownloadImpl download, SocketChannel channel, SelectionKey key, Throwable throwable) {
		download.status = Status.ERROR;
		try {
			if (channel != null) {
				channel.close();
			}
		} catch (IOException e) {
			// ignore
		}
		if (key != null) {
			key.cancel();
		}
		String warning = "";
		warning += "Error connecting to or downloading from " + download.host + ":" + download.port;
		log.log(Level.WARNING, warning, throwable);
		if (download.listener != null)
			download.listener.error(download, throwable);
	}

	// This is the Download implementation we use internally.
	static class DownloadImpl implements Download {
		// Final fields are immutable for thread safety.
		final String host;
		final int port;
		final String path;
		final Listener listener;
		// Volatile fields may be changed concurrently.
		volatile Status status;
		volatile byte[] data = new byte[0];

		DownloadImpl(String host, int port, String path, Listener listener) {
			this.host = host;
			this.port = port;
			this.path = path;
			this.listener = listener;
			// Set the initial status.
			this.status = Status.UNCONNECTED;
		}

		// These are the basic getter methods.
		@Override
		public String getHost() {
			return host;
		}

		@Override
		public int getPort() {
			return port;
		}

		@Override
		public String getPath() {
			return path;
		}

		@Override
		public Status getStatus() {
			return status;
		}

		@Override
		public byte[] getData() {
			return data;
		}

		/**
		 * Return the HTTP status code for the download. This throws an
		 * IllegalStateException if the status is not Status.DONE.
		 */
		@Override
		public int getHttpStatus() {
			if (status != Status.DONE) {
				throw new IllegalStateException();
			}
			// In HTTP 1.1, the return code is in ASCII bytes 10-12..
			int returnValue = 0;
			returnValue += (data[9] - '0') * 100;
			returnValue += (data[10] - '0') * 10;
			returnValue += (data[11] - '0') * 1;
			return (returnValue);
		}

		/*
		 * Used internally when we read more data. This should use a larger buffer to
		 * prevent frequent re-allocation.
		 */
		void addData(ByteBuffer buffer) {
			// This function is only called during a download, so the status should be
			// "connected."
			assert status == Status.CONNECTED;
			// The number of existing bytes:
			int oldlen = data.length;
			// The number of new bytes:
			int numbytes = buffer.remaining();
			int newlen = oldlen + numbytes;
			// Create a new array.
			byte[] newdata = new byte[newlen];
			// Copy the old bytes.
			System.arraycopy(data, 0, newdata, 0, oldlen);
			// Copy the new bytes.
			buffer.get(newdata, oldlen, numbytes);
			// Save a pointer to the new array.
			data = newdata;
		}
	}

	// This Test class demonstrates a simple use of HttpDownloadManager.
	public static class Test {
		static int completedDownloads = 0;

		public static void main(String args[]) throws IOException, URISyntaxException {
			// With a -v argument, our logger will display lots of messages.
			final boolean verbose = args[0].equals("-v");
			int firstarg = 0;
			Logger logger = Logger.getLogger(Test.class.getName());

			if (verbose) {
				firstarg = 1;
				// This is the most detailed level of log output.
				logger.setLevel(Level.INFO);
			} else {
				// This is the routine level of log output.
				logger.setLevel(Level.WARNING);
			}

			// How many URLs are on the command line?
			final int numDownloads = args.length - firstarg;
			// Create the download manager.
			final HttpDownloadManager downloadManager = new HttpDownloadManager(logger);
			/*
			 * Now loop through URLs and call download() for each one, passing a listener
			 * object to receive notifications
			 */
			for (int i = firstarg; i < args.length; i++) {
				URI uri = new URI(args[i]);

				downloadManager.download(uri, new Listener() {
					// Define an anonymous class which implements the Listener interface.
					public void done(Download d) {
						System.err.println("DONE: " + d.getHost() + ": " + d.getHttpStatus());
						// If all downloads are complete, we're done with the HttpDownloadManager
						// thread.

						String dataString = new String(d.getData(), StandardCharsets.UTF_8);
						System.out.println(dataString);

						if (++completedDownloads == numDownloads) {
							downloadManager.release();
						}
					}

					public void error(Download d, Throwable t) {
						System.err.println(d.getHost() + ": " + t);
						if (++completedDownloads == numDownloads) {
							downloadManager.release();
						}
					}
				});
			}
		}
	}
}
