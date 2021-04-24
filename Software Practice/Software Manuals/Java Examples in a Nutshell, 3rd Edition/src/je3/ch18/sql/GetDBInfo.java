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

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 534-536. This class uses
 * the DatabaseMetaData class to obtain information about the database, the JDBC
 * driver, and the tables in the database, or about the columns of a named
 * table.
 * <p/>
 * Before you can run any of the SQL examples, there is a significant amount of
 * work required, to install or access a DB you can use, define properties to
 * access that DB, and define Run Configuration variables to tell your system
 * where your database and property files are located. If you can get through
 * all that (definitely possible), the SQL examples all have Run Configurations
 * checked in, which can all be launched with the one-click Demo method.
 */
public class GetDBInfo {
	public static void main(String[] args) {
		/* The JDBC connection to the database server: */
		Connection connection = null;
		try {
			/*
			 * Look for the properties file db.properties in in the same directory as this
			 * program. It will contain default values for the various parameters needed to
			 * connect to a database.
			 */
			Properties properties = new Properties();

			try {
				properties.load(GetDBInfo.class.getResourceAsStream("db.properties"));
			} catch (Exception exception) {
				exception.printStackTrace(System.err);
			}

			/* Get default values from the properties file. */
			// Driver class name:
			String driver = properties.getProperty("jdbc.driver");
			// JDBC URL for server:
			String server = properties.getProperty("jdbc.server", "");
			// DB user name:
			String user = properties.getProperty("jdbc.username", "");
			// DB password:
			String password = properties.getProperty("jdbc.password", "");

			/* These variables don't have defaults. */
			// The DB name (appended to server URL):
			String database = null;
			// The optional name of a table in the DB:
			String table = null;

			/* Parse the command-line arguments to override the default values above. */
			for (int i = 0; i < args.length; i++) {
				// -d <driver>
				if (args[i].equals("-d")) {
					driver = args[++i];
				}
				// -s <server>
				else if (args[i].equals("-s")) {
					server = args[++i];
				}
				// -u <user>
				else if (args[i].equals("-u")) {
					user = args[++i];
				} else if (args[i].equals("-p")) {
					password = args[++i];
				}
				// <DB name>
				else if (database == null) {
					database = args[i];
				}
				// <database table>
				else if (table == null) {
					table = args[i];
				} else
					throw new IllegalArgumentException("Unknown argument: " + args[i]);
			}

			/*
			 * Make sure that at least a server or a database were specified. If not, we
			 * have no idea what to connect to, and cannot continue.
			 */
			if ((server.length() == 0) && (database.length() == 0)) {
				throw new IllegalArgumentException("No database specified.");
			}

			// Load the db driver, if any was specified.
			if (driver != null) {
				Class.forName(driver);
			}

			/*
			 * Now attempt to open a connection to the specified database on the specified
			 * server, using the specified name and password.
			 */
			connection = DriverManager.getConnection(server + database, user, password);

			/*
			 * Get the DatabaseMetaData object for the connection. This is the object that
			 * will return us all the data we're interested in here.
			 */
			DatabaseMetaData metadata = connection.getMetaData();

			// Display information about the server, the driver, etc.
			String dbms = "";
			dbms += "DBMS: " + metadata.getDatabaseProductName();
			dbms += " " + metadata.getDatabaseProductVersion();
			System.out.println(dbms);
			System.out.println("JDBC Driver: " + metadata.getDriverName() + " " + metadata.getDriverVersion());
			System.out.println("Database: " + metadata.getURL());
			String userName = metadata.getUserName();
			if (userName == null) {
				userName = user;
			}
			System.out.println("User Name: " + userName);

			/*
			 * Now, if the user did not specify a table, then display a list of all tables
			 * defined in the named database. Note that tables are returned in a ResultSet,
			 * just like query results are.
			 */
			if (table == null) {
				System.out.println("Tables:");
				ResultSet resultSet = metadata.getTables("", "", "%", null);
				while (resultSet.next()) {
					System.out.println("\t" + resultSet.getString(3));
				}
			}

			/*
			 * Otherwise, list all the columns of the specified table. Again, information
			 * about the columns is returned in a ResultSet.
			 */
			else {
				System.out.println("Columns of " + table + ": ");
				ResultSet resultSet = metadata.getColumns("", "", table, "%");
				while (resultSet.next()) {
					System.out.println("\t" + resultSet.getString(4) + " : " + resultSet.getString(6));
				}
			}
		}
		// Print an error message if anything goes wrong.
		catch (Exception exception) {
			exception.printStackTrace(System.err);
			if (exception instanceof SQLException) {
				System.err.println(((SQLException) exception).getSQLState());
			}
			String usage = "Usage: java GetDBInfo [-d <driver>] [-s <dbserver>] [-u <username>] [-p <password>] <dbname>";
			System.err.println(usage);
		}
		// Always remember to close the Connection object when we're done!
		finally {
			try {
				connection.close();
			} catch (Exception exception) {
				exception.printStackTrace(System.err);
			}
		}
	}
}
