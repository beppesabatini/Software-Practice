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
package je3.ch18.sql;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 529-533. A general-purpose
 * SQL interpreter program. In the current installation the this program runs
 * against SQLite, a small document-based toy database for practicing SQL.
 * <p/>
 * Before you can run any of the SQL examples, there is a significant amount of
 * work required, to install or access a DB you can use, define properties to
 * access that DB, and define Run Configuration variables to tell your system
 * where your database and property files are located. If you can get through
 * all that (definitely possible), the SQL examples all have Run Configurations
 * checked in, which can all be launched with the one-click Demo method.
 */
public class ExecuteSQL {
	public static void main(String[] args) {
		// Our JDBC connection to the database server:
		Connection connection = null;
		try {
			String driver = null, url = null, user = "", password = "";

			// Parse all the command-line arguments.
			for (int n = 0; n < args.length; n++) {
				if (args[n].equals("-d")) {
					driver = args[++n];
				} else if (args[n].equals("-u")) {
					user = args[++n];
				} else if (args[n].equals("-p")) {
					password = args[++n];
				} else if (url == null) {
					url = args[n];
				} else
					throw new IllegalArgumentException("Unknown argument: " + args[n]);
			}

			// The only required argument is the database URL.
			if (url == null) {
				throw new IllegalArgumentException("No database specified");
			}
			/*
			 * If the user specified the class name for the DB driver, load that class
			 * dynamically. This gives the driver the opportunity to register itself with
			 * the DriverManager.
			 */
			if (driver != null) {
				Class.forName(driver);
			}

			/*
			 * Now open a connection the specified database, using the user-specified user
			 * name and password, if any. The driver manager will try all of the DB drivers
			 * it knows about, to try to parse the URL and connect to the DB server.
			 */
			connection = DriverManager.getConnection(url, user, password);

			// Now create the statement object we'll use to talk to the DB.
			Statement statement = connection.createStatement();

			// Get a stream to read from the console.
			BufferedReader inputStreamBufferedReader = new BufferedReader(new InputStreamReader(System.in));

			// Loop forever, reading the user's queries and executing them.
			while (true) {
				// Prompt the user:
				System.out.print("sql> ");
				// Make the prompt appear now:
				System.out.flush();
				// Get a line of input from user:
				String sql = inputStreamBufferedReader.readLine();

				// Quit when the user types "quit".
				if ((sql == null) || sql.equals("quit")) {
					break;
				}

				// Ignore blank lines:
				if (sql.length() == 0) {
					continue;
				}

				// Now, execute the user's line of SQL and display the results.
				try {
					/*
					 * We don't know if this is a query or some kind of update, so we use execute()
					 * instead of executeQuery() or executeUpdate() If the return value is true, it
					 * was a query, or else an update.
					 */
					boolean status = statement.execute(sql);

					/*
					 * Some complex SQL queries can return more than one set of results, so loop
					 * until there are no more results.
					 */
					do {
						if (status == true) { // It was a query and returns a ResultSet.
							// Get results:
							ResultSet resultSet = statement.getResultSet();
							// Display them:
							printResultsTable(resultSet, System.out);
						} else {
							/*
							 * If the SQL command that was executed was some kind of update rather than a
							 * query, then it doesn't return a ResultSet. Instead, we just print the number
							 * of rows that were affected.
							 */
							int numberUpdates = statement.getUpdateCount();
							System.out.println("Ok. " + numberUpdates + " rows affected.");
						}

						/*
						 * Now go see if there are even more results, and continue the results display
						 * loop if there are.
						 */
						status = statement.getMoreResults();
					} while (status != false || statement.getUpdateCount() != -1);
				}
				/*
				 * If a SQLException is thrown, display an error message. Note that
				 * SQLExceptions can have a general message and a DB-specific message returned
				 * by getSQLState().
				 */
				catch (SQLException sqlException) {
					String message = "";
					message += "SQLException: ";
					message += sqlException.getMessage();
					message += ": ";
					message += sqlException.getSQLState();
					System.err.println(message);
				}
				/*
				 * Each time through this loop, check to see if there were any warnings. Note
				 * that there can be a whole chain of warnings.
				 */
				finally { // Print out any warnings that occurred.
					SQLWarning warning;
					for (warning = connection.getWarnings(); warning != null; warning = warning.getNextWarning())
						System.err.println("WARNING: " + warning.getMessage() + ":" + warning.getSQLState());
				}
			}
		}
		/*
		 * Handle exceptions that occur during argument parsing, database connection
		 * setup, etc. For SQLExceptions, print the details.
		 */
		catch (Exception exception) {
			System.err.println(exception);
			if (exception instanceof SQLException) {
				System.err.println("SQL State: " + ((SQLException) exception).getSQLState());
			}
			System.err.println("Usage: java ExecuteSQL [-d <driver>] " + "[-u <user>] [-p <password>] <database URL>");
		}

		/*
		 * Be sure to always close the database connection when we exit, whether we exit
		 * because the user types 'quit' or because of an exception thrown while setting
		 * things up. Closing this connection also implicitly closes any open statements
		 * and result sets associated with it.
		 */
		finally {
			try {
				connection.close();
			} catch (Exception exception) {
				// Ignore it
			}
		}
	}

	/**
	 * This method attempts to output the contents of a ResultSet in a textual
	 * table. It relies on the ResultSetMetaData class, but a fair bit of the code
	 * is simple string manipulation.
	 */
	static void printResultsTable(ResultSet resultSet, OutputStream output) throws SQLException {
		// Set up the output stream:
		PrintWriter printWriter = new PrintWriter(output);

		// Get some "meta data" (column names, etc.) about the results.
		ResultSetMetaData metadata = resultSet.getMetaData();

		/*
		 * Variables to hold important data about the table to be displayed.
		 */
		// How many columns:
		int numberColumns = metadata.getColumnCount();
		// The column labels:
		String[] labels = new String[numberColumns];
		// The width of each:
		int[] columnWidths = new int[numberColumns];
		// The start position of each:
		int[] startPositions = new int[numberColumns];
		// The total width of the table:
		int tableWidth;

		/*
		 * Figure out how wide the columns are, where each one begins, how wide each row
		 * of the table will be, etc.
		 */
		// For the initial '|':
		tableWidth = 1;
		// For each column:
		for (int i = 0; i < numberColumns; i++) {
			// Save its position:
			startPositions[i] = tableWidth;
			// Get its label:
			labels[i] = metadata.getColumnLabel(i + 1);
			/*
			 * Get the column width. If the db doesn't report one, guess 30 characters. Then
			 * check the length of the label, and use it if it is larger than the column
			 * width.
			 */
			int size = metadata.getColumnDisplaySize(i + 1);
			// Some drivers return -1...
			if (size == -1) {
				size = 30;
			}
			// Don't allow unreasonable sizes.
			if (size > 500) {
				size = 30;
			}
			int labelSize = labels[i].length();
			if (labelSize > size) {
				size = labelSize;
			}
			// Save the size of the column:
			columnWidths[i] = size + 1;
			// Increment the total size:
			tableWidth += columnWidths[i] + 2;
		}

		/*
		 * Create a horizontal divider line we use in the table. Also create a blank
		 * line that is the initial value of each line of the table.
		 */
		StringBuffer divider = new StringBuffer(tableWidth);
		StringBuffer blankLine = new StringBuffer(tableWidth);
		for (int i = 0; i < tableWidth; i++) {
			divider.insert(i, '-');
			blankLine.insert(i, " ");
		}
		// Put special marks in the divider line at the column positions.
		for (int i = 0; i < numberColumns; i++) {
			divider.setCharAt(startPositions[i] - 1, '+');
		}
		divider.setCharAt(tableWidth - 1, '+');

		// Begin the table output with a divider line:
		printWriter.println(divider);

		/*
		 * The next line of the table contains the column labels. Begin with a blank
		 * line, and put the column names and column divider characters "|" into it. The
		 * overwrite() function is defined below.
		 */
		StringBuffer line = new StringBuffer(blankLine.toString());
		line.setCharAt(0, '|');
		for (int i = 0; i < numberColumns; i++) {
			int position = startPositions[i] + 1 + (columnWidths[i] - labels[i].length()) / 2;
			overwrite(line, position, labels[i]);
			overwrite(line, startPositions[i] + columnWidths[i], " |");
		}

		// Then output the line of column labels and another divider.
		printWriter.println(line);
		printWriter.println(divider);

		/*
		 * Now, output the table data. Loop through the ResultSet, using the next()
		 * method to get the rows one at a time. Obtain the value of each column with
		 * getObject(), and output it, much as we did for the column labels above.
		 */
		while (resultSet.next()) {
			line = new StringBuffer(blankLine.toString());
			line.setCharAt(0, '|');
			for (int i = 0; i < numberColumns; i++) {
				Object value = resultSet.getObject(i + 1);
				if (value != null) {
					overwrite(line, startPositions[i] + 1, value.toString().trim());
				}
				overwrite(line, startPositions[i] + columnWidths[i], " |");
			}
			printWriter.println(line);
		}

		// Finally, end the table with one last divider line.
		printWriter.println(divider);
		printWriter.flush();
	}

	/** This utility method is used when printing the table of results */
	static void overwrite(StringBuffer stringBuffer, int position, String sourceString) {
		// String length:
		int stringLength = sourceString.length();
		// Buffer length:
		Integer bufferLength = stringBuffer.length();

		// Does it fit?
		if (position + stringLength > bufferLength) {
			stringLength = bufferLength - position;
		}
		// Copy the string into the buffer:
		for (int i = 0; i < stringLength; i++) {
			stringBuffer.setCharAt(position + i, sourceString.charAt(i));
		}
	}
}
