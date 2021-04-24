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
package je3.ch20.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 577-580. This servlet
 * maintains an arbitrary set of counter variables and increments and displays
 * the value of one named counter each time it is invoked. It saves the state of
 * the counters to a disk file, so the counts are not lost when the server shuts
 * down. It is suitable for counting page hits, or any other type of event. It
 * is not typically invoked directly, but is included within other pages, using
 * JSP, SSI, or a RequestDispatcher.
 * <p/>
 * None of the examples in the servlet chapter, chapter 20, can be launched with
 * one click or with a Run Configuration. You must have access to a web server,
 * and in that web server you must install the WAR file made up of all the files
 * in this chapter. There is a build file to build that WAR file at the root of
 * this project, build.je3.ch20.servlet.war.xml. When all that is done, there is
 * a URL for the entry point to the WAR file:
 * <p/>
 * http://localhost:8080/FlanaganServlet1.0/
 * <p/>
 * That is a page of links which directly or indirectly test most of the servlet
 * chapter examples.
 */
public class Counter extends HttpServlet {

	private static final long serialVersionUID = -2741093154824997298L;

	private static Logger logger = Logger.getLogger(Counter.class.getName());

	// A hash table which maps counter names to counts.
	HashMap<String, Integer> counts = new HashMap<String, Integer>();
	// The file in which counts are saved:
	File countFile;
	// How often (in milliseconds) to save our state while running?
	long saveInterval;
	// When did we last save our state?
	long lastSaveTime;

	/*
	 * This method is called when the web server first instantiates this servlet. It
	 * reads initialization parameters (which are configured at deployment time in
	 * the web.xml file), and loads the initial state of the counter variables from
	 * a file.
	 */
	@Override
	public void init() throws ServletException {
		ServletConfig config = getServletConfig();
		try {
			// Get the save file.
			String countFileString = config.getInitParameter("countFile");
			countFile = new File(countFileString);
			// How often should we save our state while running?
			saveInterval = Integer.parseInt(config.getInitParameter("saveInterval"));
			// The state couldn't have changed before now.
			lastSaveTime = System.currentTimeMillis();
			// Now, read in the count data:
			loadState();
		} catch (Exception exception) {
			// If something goes wrong, wrap the exception and rethrow it.
			throw new ServletException("Can't initialize Counter servlet: " + exception.getMessage(), exception);
		}
	}

	/*
	 * This method is called when the web server stops the servlet (which happens
	 * when the web server is shutting down, or when the servlet is not in active
	 * use.) This method saves the counts to a file so they can be restored when the
	 * servlet is restarted.
	 */
	@Override
	public void destroy() {
		try {
			// Try to save the state:
			saveState();
		} catch (Exception exception) {
			// Ignore any problems: we did the best we could.
		}
	}

	/*
	 * These constants define the request parameter and attribute names that the
	 * servlet uses to find the name of the counter to increment.
	 */
	public static final String PARAMETER_NAME = "counter";
	public static final String ATTRIBUTE_NAME = "je3.ch20.servlet.Counter.counter";

	/**
	 * This method is called when the servlet is invoked. It looks for a request
	 * parameter named "counter", and uses its value as the name of the counter
	 * variable to increment. If it doesn't find the request parameter, then it uses
	 * the URL of the request as the name of the counter. This is useful when the
	 * servlet is mapped to a URL suffix. This method also checks how much time has
	 * elapsed since it last saved its state, and saves the state again if
	 * necessary. This prevents it from losing too much data if the server crashes
	 * or shuts down without calling the destroy() method.
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// Get the name of the counter as a request parameter.
		String counterName = request.getParameter(PARAMETER_NAME);

		/*
		 * If we didn't find it there, see if it was passed to us as a request
		 * attribute, which happens when the output of this servlet is included by
		 * another servlet.
		 */
		if (counterName == null) {
			counterName = (String) request.getAttribute(ATTRIBUTE_NAME);
		}

		// If it wasn't a parameter or attribute, use the request URL.
		if (counterName == null) {
			counterName = request.getRequestURI();
		}

		// What is the current count?
		Integer count;

		/*
		 * This block of code is synchronized because multiple requests may be running
		 * at the same time in different threads. Synchronization prevents them from
		 * updating the counts hashtable at the same time.
		 */
		synchronized (counts) {
			// Get the counter value from the hashtable:
			count = counts.get(counterName);

			// Increment the counter, or if it is new, log and start it at 1.
			if (count != null) {
				count = count.intValue() + 1;
			} else {
				/*
				 * If this is a counter we haven't used before, send a message to the log file,
				 * just so we can track what we're counting.
				 */
				log("Starting new counter: " + counterName);
				// Start counting at 1!
				count = 1;
			}

			// Store the incremented (or new) counter value into the hashtable.
			counts.put(counterName, count);

			/*
			 * Check whether saveInterval milliseconds have elapsed since we last saved our
			 * state. If so, save it again. This prevents us from losing more than
			 * saveInterval milliseconds of data, even if the server crashes unexpectedly.
			 */
			if (System.currentTimeMillis() - lastSaveTime > saveInterval) {
				saveState();
				lastSaveTime = System.currentTimeMillis();
			}
		} // End of synchronized block

		/*
		 * Finally, output the counter value. Since this servlet is usually included
		 * within the output of other servlets, we don't bother setting the content
		 * type.
		 */
		PrintWriter printWriter = response.getWriter();
		printWriter.print(count);
	}

	/*
	 * The doPost method just calls doGet, so that this servlet can be included in
	 * pages that are loaded with POST requests.
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doGet(request, response);
	}

	/*
	 * Save the state of the counters by serializing the hashtable to the file
	 * specified by the initialization parameter.
	 */
	private void saveState() throws IOException {
		// Open a file for writing:
		FileOutputStream fileOutputStream = new FileOutputStream(countFile);
		// Buffer the output:
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
		/*
		 * Note ObjectOutputStream serializes. It calls writeObject() to do so. Users
		 * can write their own writeObject() to assist with some special handling in the
		 * serialization.
		 */
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream);

		/*
		 * Save the hashtable to the stream. Note ObjectOutputStream serializes. It
		 * calls writeObject() to do so. Users can write their own writeObject()
		 * function, to assist with any special handling required in the serialization.
		 */
		objectOutputStream.writeObject(counts);
		// Always remember to close your files!
		objectOutputStream.close();
	}

	/*
	 * Load the initial state of the counters by deserializing a hashtable from the
	 * file specified by the initialization parameter. If the file doesn't exist
	 * yet, then start with an empty hashtable.
	 */
	private synchronized void loadState() throws IOException {
		if (countFile.exists() == false) {
			return;
		}

		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(countFile);
		} catch (FileNotFoundException fileNotFoundException) {
			String message = "";
			message += "Sorry, could not find the countFile for stored data.";
			message += "\n";
			message += "The countFile: " + countFile;
			logger.log(Level.SEVERE, message);
			return;
		}

		ObjectInputStream objectInputStream = null;
		try {
			objectInputStream = new ObjectInputStream(new BufferedInputStream(fileInputStream));
			@SuppressWarnings("unchecked")
			HashMap<String, Integer> input = (HashMap<String, Integer>) objectInputStream.readObject();
			counts = input;
		} catch (ClassNotFoundException classNotFoundException) {
			classNotFoundException.printStackTrace(System.err);
			String message = "";
			message += "Sorry, the countFile for stored data contains bad data";
			String exceptionMessage = classNotFoundException.getMessage();
			if (exceptionMessage != null) {
				message += "\n";
				message += "ClassNotFoundException message: " + exceptionMessage;
			}
			logger.log(Level.SEVERE, message);
			return;
		} catch (IOException ioException) {
			String message = "";
			message += "Sorry, could not open the countFile for stored data";
			String exceptionMessage = ioException.getMessage();
			if (exceptionMessage != null) {
				message += "\n";
				message += "IOException message: " + exceptionMessage;
				logger.log(Level.SEVERE, message);
				return;
			}
		} finally {
			try {
				if (objectInputStream != null) {
					objectInputStream.close();
				}
			} catch (IOException ioException) {
				ioException.printStackTrace(System.err);
			}
		}
	}
}
