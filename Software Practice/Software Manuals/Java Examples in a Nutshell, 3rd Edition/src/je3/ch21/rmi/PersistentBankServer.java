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
package je3.ch21.rmi;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import je3.ch18.sql.SqlDialect;
import je3.ch21.rmi.Bank.RemoteBank;
import je3.ch21.rmi.Bank.BankingException;
import je3.ch21.rmi.Bank.FunnyMoney;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 615-621. This class is
 * another implementation of the RemoteBank interface. It uses a database
 * connection as its back end, so that client data isn't lost if the server goes
 * down. Note that it takes the database connection out of "auto commit" mode
 * and explicitly calls commit() and rollback() to ensure that updates happen
 * atomically.
 * <p/>
 * Before running PersistentBankServer for the first time, you will need to
 * create another table:
 * 
 * <pre>
    BEGIN TRANSACTION;
    CREATE TABLE accounts (name VARCHAR(20), password VARCHAR(20), balance INT);
    COMMIT;
 * </pre>
 * 
 * To test this class (which is not easy), see the instructions in {@link Bank}.java.
 */
public class PersistentBankServer extends UnicastRemoteObject implements RemoteBank {

	private static final long serialVersionUID = -4290747346047730053L;

	private static final String DEBUG = "true";

	// The connection to the database that stores account info.
	Connection connection;

	/** The constructor. Just store away the database connection object. */
	public PersistentBankServer(Connection connection) throws RemoteException {
		this.connection = connection;
	}

	/** Open an account: */
	@Override
	public synchronized void openAccount(String accountName, String password) throws RemoteException, BankingException {

		if (Boolean.valueOf(DEBUG) == true) {
			cleanUpDebugData(connection);
		}

		// First, check if there is already an account with that name.
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM accounts WHERE name = '" + accountName + "'");
			if (resultSet.next()) {
				throw new BankingException("Sorry, account name \"" + accountName + "\" has been taken already.");
			}

			/*
			 * Since that account doesn't exist, go ahead and create it. Also, create a
			 * table for the transaction history of this account and insert an initial
			 * transaction into it.
			 */
			statement = connection.createStatement();
			statement.executeUpdate("INSERT INTO accounts VALUES ('" + accountName + "', '" + password + "', 0)");
			/*
			 * Please note, in real life you would NOT create a new table for every
			 * customer!! You would use an account_history table with a customerId column.
			 */
			statement.executeUpdate("CREATE TABLE " + accountName + "_history (msg VARCHAR(80))");
			String insertHistorySQL = getInsertHistoryAfterOpenAccountSQL(accountName);
			statement.executeUpdate(insertHistorySQL);

			// No matter what happens, don't forget to close the DB Statement.
			statement.close();

			/*
			 * ...and, if we've been successful so far, commit these updates, ending the
			 * atomic transaction. All the methods below also use this atomic transaction
			 * commit/rollback scheme.
			 */
			connection.commit();
		} catch (SQLException sqlException01) {
			/*
			 * If an exception was thrown, "rollback" the prior updates, removing them from
			 * the database. This also ends the atomic transaction.
			 */
			try {
				connection.rollback();
			} catch (Exception sqlException02) {
				sqlException02.printStackTrace(System.err);
			}
			// Pass the SQLException on in the body of a BankingException.
			throw new BankingException(buildSQLExceptionMessage(sqlException01));
		}
	}

	/**
	 * This convenience method checks whether the name and password match an
	 * existing account. If so, it returns the balance in that account. If not, it
	 * throws an exception. Note that this method does not call commit() or
	 * rollback(), so its query is part of a larger transaction.
	 */
	public int verify(String accountName, String password) throws BankingException, SQLException {
		Statement statement = null;
		try {
			statement = connection.createStatement();
			String balanceSQL = getSelectBalanceAfterVerifySQL(accountName, password);
			ResultSet resultSet = statement.executeQuery(balanceSQL);
			if (resultSet.next() == false) {
				throw new BankingException("Sorry, bad account name or password");
			}
			return resultSet.getInt(1);
		} finally {
			try {
				statement.close();
			} catch (Exception exception) {
				exception.printStackTrace(System.err);
			}
		}
	}

	/** Close a named account. */
	@Override
	public synchronized FunnyMoney closeAccount(String accountName, String password)
			throws RemoteException, BankingException {
		int balance = 0;
		Statement statement = null;
		try {
			balance = verify(accountName, password);
			statement = connection.createStatement();
			// Delete the account from the accounts table.
			String deleteSQL = getDeleteAfterCloseAccountSQL(accountName, password);
			statement.executeUpdate(deleteSQL);
			// ...and, drop the transaction history table for this account.
			statement.executeUpdate("DROP TABLE " + accountName + "_history ");
			connection.commit();
		} catch (SQLException sqlException01) {
			try {
				connection.rollback();
			} catch (Exception sqlException02) {
				sqlException02.printStackTrace(System.err);
			}
			throw new BankingException(buildSQLExceptionMessage(sqlException01));
		} finally {
			try {
				statement.close();
			} catch (SQLException sqlException) {
				sqlException.printStackTrace(System.err);
			}
		}

		// Lastly, return whatever balance remained in the account.
		return new FunnyMoney(balance);
	}

	/** Deposit the specified money into the named account. */
	@Override
	public synchronized void deposit(String accountName, String password, FunnyMoney money)
			throws RemoteException, BankingException {
		int balance = 0;
		Statement statement = null;
		try {
			balance = verify(accountName, password);
			statement = connection.createStatement();
			// Update the balance:
			String updateSQL = getUpdateAfterDepositSQL(accountName, password, balance, money);
			statement.executeUpdate(updateSQL);
			// Add a row to the transaction history:
			String insertHistorySQL = getInsertUserHistoryAfterDepositSQL(accountName, money);
			statement.executeUpdate(insertHistorySQL);
			connection.commit();
		} catch (SQLException sqlException01) {
			try {
				connection.rollback();
			} catch (Exception sqlException02) {
				sqlException02.printStackTrace(System.err);
			}
			throw new BankingException(buildSQLExceptionMessage(sqlException01));
		} finally {
			try {
				statement.close();
			} catch (SQLException sqlException) {
				sqlException.printStackTrace(System.err);
			}
		}
	}

	/** Withdraw the specified amount from the named account. */
	@Override
	public synchronized FunnyMoney withdraw(String accountName, String password, int amount)
			throws RemoteException, BankingException {
		int balance = 0;
		Statement statement = null;
		try {
			balance = verify(accountName, password);
			if (balance < amount) {
				throw new BankingException("Insufficient Funds");
			}
			statement = connection.createStatement();

			// Update the account balance
			String updateSQL = getUpdateAfterWithdrawalSQL(balance, amount, accountName, password);
			statement.executeUpdate(updateSQL);

			// Add a row to the transaction history:
			String insertSQL = getInsertAfterWithdrawalSQL(accountName, amount);
			statement.executeUpdate(insertSQL);
			connection.commit();
		} catch (SQLException sqlException01) {
			try {
				connection.rollback();
			} catch (SQLException sqlException02) {
				sqlException02.printStackTrace(System.err);
			}
			throw new BankingException(buildSQLExceptionMessage(sqlException01));
		} finally {
			try {
				statement.close();
			} catch (SQLException sqlException) {
				sqlException.printStackTrace(System.err);
			}
		}

		return new FunnyMoney(amount);
	}

	/** Return the balance of the specified account. */
	@Override
	public synchronized int getBalance(String accountName, String password) throws RemoteException, BankingException {
		int balance;
		try {
			// Get the balance:
			balance = verify(accountName, password);
			// Commit the transaction:
			connection.commit();
		} catch (SQLException sqlException01) {
			try {
				connection.rollback();
			} catch (SQLException sqlException02) {
				sqlException02.printStackTrace(System.err);
			}
			throw new BankingException(buildSQLExceptionMessage(sqlException01));
		}
		// Return the balance:
		return balance;
	}

	/** Get the transaction history of the named account. */
	@Override
	public synchronized List<String> getTransactionHistory(String accountName, String password)
			throws RemoteException, BankingException {
		Statement statement = null;
		List<String> list = new ArrayList<String>();
		try {
			/*
			 * Call verify to check the password, even though we don't care what the current
			 * balance is.
			 */
			verify(accountName, password);
			statement = connection.createStatement();
			// Request everything out of the history table.
			// Get the results of the query, and put them in a List.
			ResultSet resultSet = statement.executeQuery("SELECT * from " + accountName + "_history");
			while (resultSet.next()) {
				list.add(resultSet.getString(1));
			}
			// Commit the transaction:
			connection.commit();
		} catch (SQLException sqlException01) {
			try {
				connection.rollback();
			} catch (Exception sqlException02) {
				sqlException02.printStackTrace(System.err);
			}
			throw new BankingException(buildSQLExceptionMessage(sqlException01));
		} finally {
			try {
				statement.close();
			} catch (SQLException sqlException) {
				sqlException.printStackTrace(System.err);
			}
		}
		// Return the Vector of transaction history.
		return list;
	}

	/**
	 * This main() method is the standalone program that figures out what database
	 * to connect to with what driver, connects to the database, creates a
	 * PersistentBankServer object, and registers it with the registry, making it
	 * available for client use.
	 */
	public static void main(String[] args) {

		/**
		 * Create a new Properties object. If there is a <db.properties> file specified
		 * on the command line, initialize the database properties from that. Readers
		 * may want to provide a command-line filepath to the old db.properties file
		 * from chapter 18. In this development installation, the full path to the old
		 * db.properties file is specified in the run configuration
		 * "PeristentBankServer". Define the variable to match your local environment.
		 * 
		 * <pre>
		 * ${java_examples_nutshell_project}\src\je3\ch18\sql\db.properties
		 * </pre>
		 */
		String dbPropertiesFilepath = "";
		if (args.length > 0 && args[0].length() > 0) {
			dbPropertiesFilepath = args[0];
			dbPropertiesFilepath = dbPropertiesFilepath.replace("%20", " ");
		} else {
			/*
			 * If no DB properties file has been specified, either on the command line or in
			 * a run configuration, then by default the system will look for a local file
			 * called "db.properties". The system will look for a local properties file by
			 * searching through the class path and finally the \bin directory. This will
			 * only find files inside the chapter 21/rmi directory.
			 */
			URL dbPropertiesUrl = PersistentBankServer.class.getResource("db.properties");
			dbPropertiesFilepath = dbPropertiesUrl.toString().substring(6).replace("%20", " ");
		}

		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(dbPropertiesFilepath);
		} catch (FileNotFoundException fileNotFoundException) {
			System.err.println("Sorry, could not locate file: " + dbPropertiesFilepath);
			fileNotFoundException.printStackTrace(System.err);
		}

		Properties properties = new Properties();
		try {
			properties.load(fileInputStream);
		} catch (IOException ioException) {
			System.err.println("Sorry, could not load DB properties from: " + dbPropertiesFilepath);
			ioException.printStackTrace(System.err);
		}

		/*
		 * The db.properties file (or else the file specified on the command line) must
		 * contain the properties "driver" and "database", and may optionally contain
		 * the properties "user" and "password". The DB property-file names, and the
		 * property names inside them, vary a little between examples. Make sure they
		 * appear below as they appear in the file you are opening.
		 */
		String driver = properties.getProperty("jdbc.driver");
		String databaseUrl = properties.getProperty("jdbc.url");
		String username = properties.getProperty("jdbc.username", "");
		String password = properties.getProperty("jdbc.password", "");
		SqlDialect sqlDialect = null;

		if (databaseUrl != null && "".equals(databaseUrl) == false) {
			String[] urlElements = databaseUrl.split(":");
			sqlDialect = SqlDialect.fromUrlName(urlElements[1]);
		}

		try {
			// Load the database driver class:
			Class.forName(driver);
		} catch (ClassNotFoundException classNotFoundException) {
			System.out.println(System.err);
		}

		Connection connection = null;
		try {
			// Connect to the database that stores our accounts.
			connection = DriverManager.getConnection(databaseUrl, username, password);
			/*
			 * Configure the database to allow multiple queries and updates to be grouped
			 * into atomic transactions.
			 */
			connection.setAutoCommit(false);
			if (sqlDialect == SqlDialect.MYSQL) {
				connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace(System.err);
		}

		PersistentBankServer persistentBankServer = null;
		/*
		 * Read a system property to figure out how to name this server. Use
		 * "SecondRemote" as the default. The bankName variable is defined on the java
		 * command line with a -DbankName=<bank> argument. In a Run Configuration it
		 * goes under VM arguments.
		 */
		String bankName = System.getProperty("bankName", "SecondRemote");

		try {
			// Create a server object that uses our database connection.
			persistentBankServer = new PersistentBankServer(connection);
		} catch (RemoteException remoteException) {
			remoteException.printStackTrace(System.err);
		}

		try {
			// Register the server with the name.
			Naming.rebind(bankName, persistentBankServer);

		} catch (Exception exception) {
			System.err.println(exception);
			if (exception instanceof RemoteException) {
				exception.printStackTrace(System.err);
			}
			String usage = "Usage: java [-DbankName=<name>] je3.ch21.rmi.PersistentBankServer [<db.properties.file>]";
			System.err.println(usage);
			System.exit(1);
		}

		// ...and, tell everyone that we're up and running.
		System.out.println("Good! " + bankName + " is open and ready for customers.");
	}

	/**
	 * Clean up any old data from past debugging cycles, to start fresh.
	 */
	private void cleanUpDebugData(Connection connection) {
		/**
		 * This will close the "david" account and drop the "david_history" table. These
		 * have to match the test data in Bank.java.
		 */
		String accountName = "david";
		String password = "javanut";
		try {
			Statement statement = connection.createStatement();
			// Delete the account from the accounts table...
			String deleteSQL = getDeleteAfterCloseAccountSQL(accountName, password);
			statement.executeUpdate(deleteSQL);
			// ...and, drop the transaction history table for this account.
			statement.executeUpdate("DROP TABLE " + accountName + "_history ");
			connection.commit();
		} catch (SQLException sqlException01) {
			// If there is no old data to wipe out, then there's nothing to worry about.  
			// System.err.println(buildSQLExceptionMessage(sqlException01));
			// sqlException01.printStackTrace(System.err);
			try {
				connection.rollback();
			} catch (SQLException sqlException02) {
				System.err.println(buildSQLExceptionMessage(sqlException02));
				sqlException02.printStackTrace(System.err);
			}
		}
	}

	private static String buildSQLExceptionMessage(SQLException sqlException) {
		String message = "";
		message += "SQLException: " + sqlException.getMessage();
		String sqlState = sqlException.getSQLState();
		if (sqlState == null) {
			return (message);
		}
		message += "\n";
		message += "SQLState: " + sqlState;

		return (message);
	}

	private String getInsertHistoryAfterOpenAccountSQL(String accountName) {
		String historyTableName = accountName + "_history ";

		String query = "";
		query += "  INSERT INTO ";
		query += "" + historyTableName;
		query += "  VALUES ('Account opened at " + new Date() + "') ";

		return (query);
	}

	private String getSelectBalanceAfterVerifySQL(String accountName, String password) {
		String query = "";

		query += "SELECT ";
		query += "  balance ";
		query += "FROM ";
		query += "  accounts ";
		query += "WHERE ";
		query += "    name = '" + accountName + "' ";
		query += "  AND ";
		query += "    password = '" + password + "' ";

		return (query);
	}

	private String getUpdateAfterDepositSQL(String accountName, String password, int balance, FunnyMoney money) {

		String query = "";
		query += "UPDATE ";
		query += "  accounts ";
		query += "SET ";
		query += "  balance = " + (balance + money.amount) + " ";
		query += "WHERE ";
		query += "    name = '" + accountName + "' ";
		query += "  AND ";
		query += "    password = '" + password + "' ";

		return (query);
	}

	private String getInsertUserHistoryAfterDepositSQL(String accountName, FunnyMoney money) {

		String query = "";
		query += "  INSERT INTO ";
		query += "" + accountName + "_history";
		query += "  VALUES ('Deposited " + money.amount + " at " + new Date() + "') ";

		return (query);
	}

	private String getUpdateAfterWithdrawalSQL(int balance, int amount, String accountName, String password) {

		String query = "";
		query += "UPDATE ";
		query += "  accounts ";
		query += "SET ";
		query += "  balance = " + (balance - amount) + " ";
		query += "WHERE ";
		query += "    name = '" + accountName + "' ";
		query += "  AND ";
		query += "    password = '" + password + "'";

		return (query);
	}

	private String getInsertAfterWithdrawalSQL(String accountName, int amount) {

		String query = "";
		query += "  INSERT INTO ";
		query += "" + accountName + "_history ";
		query += "  VALUES ( 'Withdrew " + amount + " at " + new Date() + "' ) ";

		return (query);
	}

	private String getDeleteAfterCloseAccountSQL(String accountName, String password) {

		String query = "";
		query += "DELETE FROM ";
		query += "  accounts ";
		query += "WHERE ";
		query += "    name = '" + accountName + "' ";
		query += "  AND ";
		query += "    password = '" + password + "' ";

		return (query);
	}
}
