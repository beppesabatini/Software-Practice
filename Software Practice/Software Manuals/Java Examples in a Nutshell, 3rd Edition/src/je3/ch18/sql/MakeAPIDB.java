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
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 537-541. This class is a
 * standalone program that reads a list of classes and builds a database of
 * packages, classes, and class fields and methods.
 * <p/>
 * Before you can run any of the SQL examples, there is a significant amount of
 * work required, to install or access a DB you can use, define properties to
 * access that DB, and define Run Configuration variables to tell your system
 * where your database and property files are located. If you can get through
 * all that (definitely possible), the SQL examples all have Run Configurations
 * checked in, which can all be launched with the one-click Demo method.
 */
public class MakeAPIDB {
	public static void main(String args[]) {
		// The connection to the database:
		Connection connection = null;

		// Read the classes to index from a file specified by args[0].
		URL inputFileUrl = MakeAPIDB.class.getResource(args[0]);
		String inputFilePath = inputFileUrl.toString().substring(6).replace("%20", " ");
		BufferedReader bufferedFileReader;
		try {
			bufferedFileReader = new BufferedReader(new FileReader(inputFilePath));
			ArrayList<String> classNames = new ArrayList<String>();

			String name;
			while ((name = bufferedFileReader.readLine()) != null) {
				classNames.add(name);
			}

			/*
			 * Now determine the values needed to set up the database connection. The
			 * program attempts to read a property file named "APIDB.properties", or
			 * optionally specified by args[1]. This property file (if any) may contain
			 * "driver", "database", "user", and "password" properties that specify the
			 * necessary values for connecting to the DB. If the properties file does not
			 * exist, or does not contain the named properties, defaults will be used.
			 */
			// Empty properties:
			Properties properties = new Properties();

			URL propertyFileUrl = MakeAPIDB.class.getResource(args[1]);
			String propertyFilePath = propertyFileUrl.toString().substring(6).replace("%20", " ");
			try {
				// Try to load properties:
				properties.load(new FileInputStream(propertyFilePath));
			} catch (IOException exception01) {
				try {
					properties.load(new FileInputStream("APIDB.properties"));
				} catch (IOException exception02) {
					exception02.printStackTrace(System.err);
				}
			}

			// Read values from the properties file:
			String driver = properties.getProperty("jdbc.driver");
			String databaseUrl = properties.getProperty("jdbc.url");
			String user = properties.getProperty("jdbc.username", "");
			String password = properties.getProperty("jdbc.password", "");

			// The driver and database properties are mandatory.
			if (driver == null) {
				bufferedFileReader.close();
				throw new IllegalArgumentException("No driver specified!");
			}
			if (databaseUrl == null) {
				bufferedFileReader.close();
				throw new IllegalArgumentException("No database specified!");
			}

			// Load the driver. It registers itself with DriverManager.
			Class.forName(driver);

			// Set up a connection to the specified database.
			connection = DriverManager.getConnection(databaseUrl, user, password);
			DatabaseMetaData metadata = connection.getMetaData();

			String[] apiTables = { "package", "class", "member" };
			Map<String, Boolean> tablesExist = getTablesStatus(apiTables, metadata);
			connection.close();

			/*
			 * Create three new tables for our data. The package table contains a package ID
			 * and a package name. The class table contains a class ID, a package ID, and a
			 * name. The member table contains a class ID, a member name, and a bit that
			 * indicates whether the class member is a field or a method.
			 */
			connection = DriverManager.getConnection(databaseUrl, user, password);
			Statement statement = connection.createStatement();

			for (int i = 0; i < apiTables.length; i++) {
				if (tablesExist.get(apiTables[i]) == true) {
					String currentSql = "DROP TABLE " + apiTables[i];
					statement.executeUpdate(currentSql);
				}
			}

			statement.executeUpdate("CREATE TABLE package (id INT, name VARCHAR(80))");
			statement.executeUpdate("CREATE TABLE class   (id INT, packageId INT, name VARCHAR(48))");
			statement.executeUpdate("CREATE TABLE member  (classId INT, name VARCHAR(48), isField BIT)");

			/*
			 * Prepare some statements that will be used to insert records into these three
			 * tables.
			 */
			insertPackage = connection.prepareStatement("INSERT INTO package VALUES(?,?)");
			insertClass = connection.prepareStatement("INSERT INTO class VALUES(?,?,?)");
			insertMember = connection.prepareStatement("INSERT INTO member VALUES(?,?,?)");

			/*
			 * Now loop through the list of classes and use reflection to store them all in
			 * the tables.
			 */
			int numberClasses = classNames.size();
			for (int i = 0; i < numberClasses; i++) {
				try {
					storeClass(classNames.get(i));
				} catch (ClassNotFoundException e) {
					System.out.println("WARNING: class not found: " + classNames.get(i) + "; SKIPPING");
				}
			}
		} catch (Exception exception) {
			exception.printStackTrace(System.err);
			if (exception instanceof SQLException) {
				SQLException sqlException = (SQLException) exception;
				String sqlState = sqlException.getSQLState();
				if (sqlState != null) {
					System.err.println("SQLState: " + sqlException.getSQLState());
				}
			}
			System.err.println("Usage: java MakeAPIDB " + "<classListfile> <propertiesFile>");
		}
		// When we're done, close the connection to the database.
		finally {
			try {
				connection.close();
			} catch (Exception exception) {
				exception.printStackTrace(System.err);
			}
		}
	}

	static boolean tableExists(DatabaseMetaData metadata, String tableName) {

		ResultSet tables = null;
		try {
			tables = metadata.getTables("", "", tableName, null);
			boolean oneExists = tables.next();

			return (oneExists);
		} catch (SQLException sqlException) {
			String sqlState = sqlException.getSQLState();
			if (sqlState != null) {
				System.err.println("SQLState: " + sqlException.getSQLState());
			}
			sqlException.printStackTrace(System.err);
			return (false);
		}
	}

	static Map<String, Boolean> getTablesStatus(String[] apiTables, DatabaseMetaData metadata) {
		Map<String, Boolean> tablesExist = new HashMap<String, Boolean>();
		for (int i = 0; i < apiTables.length; i++) {
			boolean tableExists = tableExists(metadata, apiTables[i]);
			tablesExist.put(apiTables[i], tableExists);
		}
		return (tablesExist);
	}

	/**
	 * This hash table records the mapping between package names and package ID.
	 * This is the only one we need to store temporarily. The others are stored in
	 * the DB, and don't have to be looked up by this program.
	 */
	static Map<String, Integer> packageToId = new HashMap<String, Integer>();

	// Counters for the package and class identifier columns
	static int packageId = 0, classId = 0;

	/*
	 * Some prepared SQL statements for use in inserting new values into the tables.
	 * Initialized in main() above.
	 */
	static PreparedStatement insertPackage;
	static PreparedStatement insertClass;
	static PreparedStatement insertMember;

	/**
	 * Given a fully-qualified class name, this method stores the package name in
	 * the package table (if it is not already there), stores the class name in the
	 * class table, and then uses the Java Reflection API to look up all methods and
	 * fields of the class, and stores those in the member table.
	 */
	public static void storeClass(String name) throws SQLException, ClassNotFoundException {
		String packageName;
		String className;

		// Dynamically load the class.
		Class<?> namedClass = Class.forName(name);

		// Display output so the user knows that the program is progressing.
		System.out.println("Storing data for: " + name);

		// Figure out the package name and the class name.
		int position = name.lastIndexOf('.');
		if (position == -1) {
			packageName = "";
			className = name;
		} else {
			packageName = name.substring(0, position);
			className = name.substring(position + 1);
		}

		/*
		 * Figure out what the package ID is. If there is one, then this package has
		 * already been stored in the database. Otherwise, assign an ID, and store it
		 * and the package name in the ID.
		 */
		Integer newPackageId;
		// Check hashtable:
		newPackageId = packageToId.get(packageName);
		if (newPackageId == null) {
			// Assign an id:
			newPackageId = ++packageId;
			// Store the package name and the package ID together:
			packageToId.put(packageName, newPackageId);
			// Set arguments for the prepared statement:
			insertPackage.setInt(1, packageId);
			insertPackage.setString(2, packageName);
			// Insert into the package DB:
			insertPackage.executeUpdate();
		}

		/*
		 * Now, store the class name in the class table of the database. This record
		 * includes the package ID, so that the class is linked to the package that
		 * contains it. To store the class, we set arguments to the PreparedStatement,
		 * then execute that statement.
		 */
		// Set class identifier:
		insertClass.setInt(1, ++classId);
		// Set package identifier:
		insertClass.setInt(2, newPackageId.intValue());
		// Set class name:
		insertClass.setString(3, className);
		// Insert the class record:
		insertClass.executeUpdate();

		/*
		 * Now, get a list of all non-private methods of the class, and insert those
		 * into the "members" table of the database. Each record includes the class ID
		 * of the containing class, and also a value that indicates that these are
		 * methods, not fields.
		 */
		// Get a list of methods:
		Method[] methods = namedClass.getDeclaredMethods();
		// For all non-private methods:
		for (int i = 0; i < methods.length; i++) {
			if (Modifier.isPrivate(methods[i].getModifiers())) {
				continue;
			}
			// Set the class ID:
			insertMember.setInt(1, classId);
			// Set method name:
			insertMember.setString(2, methods[i].getName());
			// Mark it as not a field:
			insertMember.setBoolean(3, false);
			// Insert into db:
			insertMember.executeUpdate();
		}

		/*
		 * Do the same thing for the non-private fields of the class.
		 */
		// Get a list of fields:
		Field[] fields = namedClass.getDeclaredFields();
		// For each non-private field:
		for (int i = 0; i < fields.length; i++) {
			if (Modifier.isPrivate(fields[i].getModifiers())) {
				continue;
			}
			// Set the class ID:
			insertMember.setInt(1, classId);
			// Set the field name:
			insertMember.setString(2, fields[i].getName());
			// Mark it as a field:
			insertMember.setBoolean(3, true);
			// Insert the record:
			insertMember.executeUpdate();
		}
	}
}
