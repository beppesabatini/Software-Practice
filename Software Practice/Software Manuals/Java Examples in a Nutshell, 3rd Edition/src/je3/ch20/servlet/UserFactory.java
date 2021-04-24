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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 588-590. A class for
 * creating new users in a database and retrieving existing users from the
 * database. Note that it assumes that a database table with the specified name
 * exists. You can create such a table with SQL like this:
 * 
 * <pre>
    CREATE TABLE subscribers (email VARCHAR(64) PRIMARY KEY, password VARCHAR(20), html BIT, digest BIT);
 * </pre>
 * 
 * You'll need more than just a database to test this example. None of the
 * examples in the servlet chapter, chapter 20, can be launched with one click
 * or with a Run Configuration. You must have access to a web server, and in
 * that web server you must install the WAR file made up of all the files in
 * this chapter. There is a build file to build that WAR file at the root of
 * this project, build.je3.ch20.servlet.war.xml. When all that is done, there is
 * a URL for the entry point to the WAR file:
 * <p/>
 * http://localhost:8080/FlanaganServlet1.0/
 * <p/>
 * That is a page of links which directly or indirectly test most of the servlet
 * chapter examples.
 */
public class UserFactory {
	// The connection to the database:
	Connection db;
	// The name of the database table we'll use:
	String tablename;

	/*
	 * These prepared statements are created in the constructor, and used in the
	 * insert(), select(), update() and delete() methods. PreparedStatements are
	 * used for security, so that the database cannot be attacked with user names
	 * and passwords that include SQL quotes.
	 */
	PreparedStatement insertUser, selectUser, updateUser, deleteUser;

	// Create a new UserFactory object backed by the specified DB table.
	public UserFactory(Connection db, String tableName) throws SQLException {
		this.db = db;
		this.tablename = tableName;

		/*
		 * Prepare the SQL statements we'll use later. Parameters will be substituted
		 * for the question marks in the methods below.
		 */
		String insertStatement = getInsertStatementSQL(tableName);
		insertUser = db.prepareStatement(insertStatement);
		selectUser = db.prepareStatement("SELECT * FROM " + tablename + " WHERE email = ? ");
		deleteUser = db.prepareStatement("DELETE FROM " + tablename + " WHERE email = ? ");
		updateUser = db.prepareStatement("UPDATE " + tablename + " SET html = ?,digest = ? WHERE email = ? ");
	}

	// Create a new User with the specified e-mail address and password.
	public User insert(String email, String password) throws UserAlreadyExists, SQLException {
		// Check whether the user already exists.
		selectUser.setString(1, email);
		ResultSet results = selectUser.executeQuery();
		if (results.next()) {
			throw new UserAlreadyExists(email);
		}

		// If not, create a new entry in the database.
		insertUser.setString(1, email);
		insertUser.setString(2, password);
		insertUser.executeUpdate();

		// ...and, return a matching User object to the caller.
		return new User(email, false, false);
	}

	// Look up the User object for the specified address and password.
	public User select(String email, String password) throws NoSuchUser, BadPassword, SQLException {
		// Look up the user:
		selectUser.setString(1, email);
		ResultSet results = selectUser.executeQuery();
		// Check that the user exists:
		if (!results.next()) {
			throw new NoSuchUser(email);
		}
		// Check that the password is correct.
		String pw = results.getString("password");
		if (!pw.equals(password)) {
			throw new BadPassword(email);
		}
		// Return a User object representing this user and their mail preferences.
		boolean html = results.getInt("html") == 1;
		boolean digest = results.getInt("digest") == 1;
		return new User(email, html, digest);
	}

	// Delete the specified User object from the database.
	public void delete(User user) throws SQLException {
		// Make sure we're not already deleted:
		if (user.deleted) {
			return;
		}
		// Delete the user from the database:
		deleteUser.setString(1, user.getEmailAddress());
		deleteUser.executeUpdate();
		// Don't allow an update() after a delete():
		user.deleted = true;
	}

	// Update the HTML and digest preferences of the specified User.
	public void update(User user) throws SQLException {
		// Don't allow updates to deleted users:
		if (user.deleted) {
			return;
		}
		// Update the database record to reflect new preferences:
		updateUser.setInt(1, user.getPrefersHTML() ? 1 : 0);
		updateUser.setInt(2, user.getPrefersDigests() ? 1 : 0);
		updateUser.setString(3, user.getEmailAddress());
		updateUser.executeUpdate();
	}

	// The following are custom exception types that we may throw.
	public static class UserAlreadyExists extends Exception {

		private static final long serialVersionUID = -8038681582038187858L;

		public UserAlreadyExists(String msg) {
			super(msg);
		}
	}

	public static class NoSuchUser extends Exception {

		private static final long serialVersionUID = -580067349422381870L;

		public NoSuchUser(String msg) {
			super(msg);
		}
	}

	public static class BadPassword extends Exception {

		private static final long serialVersionUID = -3582377627484620436L;

		public BadPassword(String msg) {
			super(msg);
		}
	}

	private String getInsertStatementSQL(String tableName) {
		String query = "";

		query += "INSERT ";
		query += "  INTO ";
		query += "" + tablename + " (email,";
		query += "                   password, ";
		query += "                   html, ";
		query += "                   digest) ";
		query += "  VALUES(?, ";
		query += "         ?, ";
		query += "         0, ";
		query += "         0) ";

		return (query);
	}

}
