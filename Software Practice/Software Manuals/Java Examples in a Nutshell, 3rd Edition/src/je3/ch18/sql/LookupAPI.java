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

import java.io.FileInputStream;
import java.net.URL;
import java.sql.Connection;
// For the db-independent way to open a connection:
// import java.sql.DriverManager; 
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.sqlite.SQLiteConfig;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 542-546. This program uses
 * the database created by MakeAPIDB. It opens a connection to a database using
 * the same property file used by MakeAPIDB. Then it queries that database in
 * several interesting ways to obtain useful information about Java APIs. It can
 * be used to look up the fully-qualified name of a member, class, or package,
 * or it can be used to list the members of a class or package.
 * <p/>
 * Before you can run any of the SQL examples, there is a significant amount of
 * work required, to install or access a DB you can use, define properties to
 * access that DB, and define Run Configuration variables to tell your system
 * where your database and property files are located. If you can get through
 * all that (definitely possible), the SQL examples all have Run Configurations
 * checked in, which can all be launched with the one-click Demo method.
 */
public class LookupAPI {
	public static void main(String[] args) {
		// This is the JDBC connection to the database.
		Connection connection = null;
		try {
			/*
			 * Initialize some default values.
			 */
			// The name to look up:
			String target = null;
			// List members or lookup name?
			boolean list = false;
			// The file of db parameters:
			String dbPropertiesFile = "db.properties";

			// Parse the command-line arguments.
			for (int i = 0; i < args.length; i++) {
				if (args[i].equals("-l") || args[i].equals("-list")) {
					list = true;
				} else if (args[i].equals("-p") || args[i].equals("-propertiesFile")) {
					dbPropertiesFile = args[++i];
				} else if (target != null) {
					throw new IllegalArgumentException("Unexpected argument: " + args[i]);
				} else {
					target = args[i];
				}
			}
			if (target == null) {
				throw new IllegalArgumentException("No target specified");
			}

			/*
			 * Now determine the values needed to set up the database connection The program
			 * attempts to read a property file named "db.properties", or optionally
			 * specified with the -p argument. This property file may contain "driver",
			 * "database", "user", and "password" properties that specify the necessary
			 * values for connecting to the db. If the properties file does not exist, or
			 * does not contain the named properties, defaults will be used.
			 */
			// Empty properties:
			Properties dbProperties = new Properties();

			// Try to load properties:
			URL dbPropertiesFileUrl = LookupAPI.class.getResource(dbPropertiesFile);
			String dbPropertiesFilePath = dbPropertiesFileUrl.toString().substring(6).replace("%20", " ");
			try {
				dbProperties.load(new FileInputStream(dbPropertiesFilePath));
			} catch (Exception exception) {
				exception.printStackTrace(System.err);
			}

			// Read values from the Properties file.
			String driver = dbProperties.getProperty("jdbc.driver");
			String databaseUrl = dbProperties.getProperty("jdbc.url");
			// SQLite does not seem to require a username and password, at least not by
			// default.
//			String username = dbProperties.getProperty("jdbc.username", "");
//			String password = dbProperties.getProperty("jdbc.password", "");

			// The driver and database properties are mandatory.
			if (driver == null) {
				throw new IllegalArgumentException("No driver specified!");
			}
			if (databaseUrl == null) {
				throw new IllegalArgumentException("No database URL specified!");
			}

			// Load the database driver:
			Class.forName(driver);

			/*
			 * Promise the connection that we will not do any updates. This hint may improve
			 * efficiency.
			 */
			SQLiteConfig sqLiteConfig = new SQLiteConfig();
			sqLiteConfig.setReadOnly(true);

			// ...and, set up a connection to the specified database.
			// connection = DriverManager.getConnection(databaseUrl, user, password);
			connection = sqLiteConfig.createConnection(databaseUrl);

			// connection.setReadOnly(true);

			/*
			 * If the "-l" option was given, then list the members of the named package or
			 * class. Otherwise, lookup all matches for the specified member, class, or
			 * package.
			 */
			if (list == true) {
				list(connection, target);
			} else {
				lookup(connection, target);
			}
		}
		/*
		 * If anything goes wrong, print the exception and a usage message. If a
		 * SQLException is thrown, display the state message it includes.
		 */
		catch (Exception exception) {
			System.out.println(exception);
			if (exception instanceof SQLException) {
				SQLException sqlException = (SQLException) exception;
				String sqlState = sqlException.getSQLState();
				if (sqlState != null) {
					System.out.println(sqlState);
				}
			}
			System.out.println("Usage: java LookupAPI [-l] [-p <propfile>] " + "target");
		}
		// Always close the DB connection when we're done with it.
		finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (Exception exception) {
				exception.printStackTrace(System.err);
			}
		}
	}

	/**
	 * This method looks up all matches for the specified target string in the
	 * database. First, it prints the full name of any members by that name. Then it
	 * prints the full name of any classes by that name. Then it prints the name of
	 * any packages that contain the specified name.
	 */
	private static void lookup(Connection connection, String target) throws SQLException {
		// Create the Statement object we'll use to query the database.
		Statement statement = connection.createStatement();

		// Go find all class members with the specified name.
		String classMembersSQL = getClassMembersSQL(target);
		ResultSet resultSet = statement.executeQuery(classMembersSQL);

		// Loop through the results, and print them out (if there are any).
		while (resultSet.next()) {
			// Package name:
			String packageName = resultSet.getString(1);
			// Class name:
			String className = resultSet.getString(2);
			// Member name:
			String member = resultSet.getString(3);
			// Is the member a field?
			boolean isField = resultSet.getBoolean(4);
			// Display this match
			System.out.println(packageName + "." + className + "." + member + (isField ? "" : "()"));
		}

		// Now look for a class with the specified name.
		String classSQL = getClassSQL(target);
		resultSet = statement.executeQuery(classSQL);

		// Loop through the results and print them out.
		while (resultSet.next()) {
			System.out.println(resultSet.getString(1) + "." + resultSet.getString(2));
		}

		/*
		 * Finally, look for a package that matches a segment of of the name.
		 */
		String matchingPackageSQL = getMatchingPackageSQL(target);
		resultSet = statement.executeQuery(matchingPackageSQL);

		// Loop through the results and print them out
		while (resultSet.next()) {
			System.out.println(resultSet.getString(1));
		}

		// Finally, close the Statement objects
		statement.close();
	}

	/**
	 * This method looks for classes with the specified name, or packages that
	 * contain the specified name. For each class it finds, it displays all methods
	 * and fields of the class. For each package it finds, it displays all classes
	 * in the package.
	 */
	private static void list(Connection conn, String target) throws SQLException {
		// Create two Statement objects with which to query the database.
		Statement statement01 = conn.createStatement();
		Statement statement02 = conn.createStatement();

		// Look for a class with the given name:
		String packageAndClassSQL = getPackageAndClassSQL(target);
		ResultSet resultSet01 = statement01.executeQuery(packageAndClassSQL);

		// Loop through all matches
		while (resultSet01.next()) {
			String packageName = resultSet01.getString(1); // package name
			String className = resultSet01.getString(2); // class name
			// Print out the matching class name
			System.out.println("class " + packageName + "." + className + " {");

			// Now query all members of the class.
			String classMemberSQL = getClassMembersSQL(packageName, className);
			ResultSet resultSet02 = statement02.executeQuery(classMemberSQL);

			// Loop through the ordered list of all members, and print them out.
			while (resultSet02.next()) {
				String memberName = resultSet02.getString(1);
				int isField = resultSet02.getInt(2);
				System.out.println("  " + memberName + ((isField == 1) ? "" : "()"));
			}
			// End the class listing.
			System.out.println("}");
		}

		// Now go look for a package that matches the specified name.
		String packageSQL = getPackageSQL(target);
		resultSet01 = statement01.executeQuery(packageSQL);

		// Loop through any matching packages.
		while (resultSet01.next()) {
			// Display the name of the package.
			String packageName = resultSet01.getString(1);
			System.out.println("Package " + packageName + ": ");

			// Get a list of all classes and interfaces in the package.
			String packageClassSQL = getAllPackageClassesSQL(packageName);
			ResultSet resultSet02 = statement02.executeQuery(packageClassSQL);

			// Loop through the list and print them out.
			while (resultSet02.next()) {
				System.out.println("  " + resultSet02.getString(1));
			}
		}

		// Finally, close both Statement objects.
		statement01.close();
		statement02.close();
	}

	private static String getClassMembersSQL(String className) {
		// Go find all class members with the specified name.
		String query = "";
		query += "SELECT DISTINCT ";
		query += "  package.name, ";
		query += "  class.name, ";
		query += "  member.name, ";
		query += "  member.isField ";
		query += "FROM ";
		query += "  package, ";
		query += "  class, ";
		query += "  member ";
		query += "WHERE ";
		query += "    member.name = '" + className + "' ";
		query += "  AND ";
		query += "    member.classId = class.id ";
		query += "  AND ";
		query += "    class.packageId = package.id";

		return (query);
	}

	private static String getClassSQL(String className) {
		String query = "";
		query += "SELECT";
		query += "  package.name, ";
		query += "  class.name ";
		query += "FROM ";
		query += "  package, ";
		query += "  class ";
		query += "WHERE ";
		query += "    class.name = '" + className + "' ";
		query += "  AND ";
		query += "    class.packageId=package.id";

		return (query);
	}

	/*
	 * Look for a package that matches a segment of the name. Note the use of the
	 * SQL LIKE keyword and % wildcard characters.
	 */
	private static String getMatchingPackageSQL(String packageName) {
		String query = "";
		query += "SELECT ";
		query += "  name ";
		query += "FROM ";
		query += "  package ";
		query += "WHERE ";
		query += "    name =      '" + packageName + "' ";
		query += "  OR ";
		query += "    name LIKE '%." + packageName + ".%' ";
		query += "  OR ";
		query += "    name LIKE   '" + packageName + ".%' ";
		query += "  OR ";
		query += "    name LIKE '%." + packageName + "' ";

		return (query);
	}

	private static String getPackageAndClassSQL(String className) {
		String query = "";
		query += "SELECT ";
		query += "  package.name, ";
		query += "  class.name ";
		query += "FROM ";
		query += "  package, ";
		query += "  class ";
		query += "WHERE ";
		query += "    class.name = '" + className + "' ";
		query += "  AND ";
		query += "    class.packageId = package.id";

		return (query);
	}

	private static String getClassMembersSQL(String packageName, String className) {
		String query = "";
		query += "SELECT DISTINCT ";
		query += "  member.name, ";
		query += "  member.isField ";
		query += "FROM ";
		query += "  package, ";
		query += "  class, ";
		query += "  member ";
		query += "WHERE ";
		query += "    package.name = '" + packageName + "' ";
		query += "  AND ";
		query += "    class.name = '" + className + "' ";
		query += "  AND ";
		query += "    member.classId = class.id ";
		query += "  AND ";
		query += "    class.packageId = package.id ";
		query += "ORDER BY ";
		query += "  member.isField, ";
		query += "  member.name ";

		return (query);
	}

	private static String getPackageSQL(String packageName) {
		String query = "";
		query += "SELECT ";
		query += "  name ";
		query += "FROM ";
		query += "  package ";
		query += "WHERE ";
		query += "    name =      '" + packageName + "' ";
		query += "  OR ";
		query += "    name LIKE '%." + packageName + ".%' ";
		query += "  OR ";
		query += "    name LIKE   '" + packageName + ".%' ";
		query += "  OR ";
		query += "    name LIKE '%." + packageName + "' ";

		return (query);
	}

	private static String getAllPackageClassesSQL(String packageName) {
		String query = "";

		query += "SELECT ";
		query += "  class.name ";
		query += "FROM ";
		query += "  package, ";
		query += "  class ";
		query += "WHERE ";
		query += "    package.name='" + packageName + "' ";
		query += "  AND ";
		query += "    class.packageId=package.id ";
		query += "ORDER BY ";
		query += "  class.name ";

		return (query);
	}
}
