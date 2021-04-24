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

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.Naming;
import java.util.List;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 608-610. This class
 * contains nested classes and interfaces to demonstrate remote banking. The
 * nested Client object can do business with two different remote banks. To test
 * this:
 * 
 * <pre>
 *  • Kill any threads from previous test runs.
 *  • Verify that &lt;java_home>/rmiregistry is running as a background process, 
 *    or else launch it.
 *  • Set DEBUG to "true" in both Bank.java (this file) and also in 
 *    PersistentBankServer.java.
 *  • Run RemoteBankServer as a standalone program. It will register itself in 
 *    the registry (as "FirstRemote"). There is a Run Configuration to do this, if
 *    you are using Eclipse.
 *  • Run PersistentBankServer as a standalone program. This bank saves to a DB 
 *    (it's "persistent"), so ensure your DB is functional and running. This bank 
 *    will also register itself (as "SecondRemote"). There is also a Run Configuration 
 *    to do this. Don't kill this thread, leave it running.  
 *  • The Client can now bank remotely with both registered banks. There are two 
 *    Run Configurations in Eclipse to test this: 
 *      ▬ "Bank.Client invoking RemoteBankServer"
 *      ▬ "Bank.Client invoking PersistentBankServer"
 *    The difference between those two is a command-line System argument, which is stored
 *    in the Run Configuration. The tests will run the same test data for both versions,
 *    and output the same results to standard out.
 *  • If you want to start over, kill any surviving threads from the test. 
 * </pre>
 */
public class Bank {

	private static final String DEBUG = "true";

	/**
	 * This is the interface that defines the public methods of the bank server
	 * implementations.
	 */
	public interface RemoteBank extends Remote {
		/** Open a new account, with the specified name and password. */
		public void openAccount(String name, String password) throws RemoteException, BankingException;

		/** Close the named account */
		public FunnyMoney closeAccount(String name, String password) throws RemoteException, BankingException;

		/** Deposit money into the named account. */
		public void deposit(String name, String password, FunnyMoney money) throws RemoteException, BankingException;

		/** Withdraw the specified amount of money from the named account. */
		public FunnyMoney withdraw(String name, String password, int amount) throws RemoteException, BankingException;

		/** Return the amount of money in the named account. */
		public int getBalance(String name, String password) throws RemoteException, BankingException;

		/**
		 * Return a List of Strings that list the transaction history of the named
		 * account.
		 */
		public List<String> getTransactionHistory(String name, String password)
				throws RemoteException, BankingException;
	}

	/**
	 * This simple class represents a monetary amount. This implementation is really
	 * nothing more than a wrapper around an integer. It is a useful to demonstrate
	 * that RMI can accept arbitrary non-String objects as arguments, and return
	 * them as values, as long as they are Serializable. A more complete
	 * implementation of this FunnyMoney class might bear a serial number, a digital
	 * signature, and other security features to ensure that it is unique and
	 * non-forgeable.
	 */
	public static class FunnyMoney implements java.io.Serializable {

		private static final long serialVersionUID = 505902701322301568L;

		public int amount;

		public FunnyMoney(int amount) {
			this.amount = amount;
		}
	}

	/**
	 * This is a type of exception used to represent exceptional conditions related
	 * to banking, such as "Insufficient Funds" and "Invalid Password."
	 **/
	public static class BankingException extends Exception {

		private static final long serialVersionUID = 1230489207078131815L;

		public BankingException(String msg) {
			super(msg);
		}
	}

	/**
	 * This class is a simple standalone Client program that interacts with a
	 * RemoteBank server. It invokes different RemoteBank methods depending on its
	 * command-line arguments, and demonstrates interacting with a server using RMI.
	 */
	public static class Client {

		/**
		 * Figure out which RemoteBank to connect to by reading a system property,
		 * specified on the command line with a -D option to java. In Eclipse it can be
		 * defined in a Run Configuration under VM arguments:
		 * 
		 * <pre>
		   -DremoteBank=rmi:///FirstRemote
		 * </pre>
		 * 
		 * Or, if the RMI URL is not defined, use a default URL, the second argument for
		 * the getProperty() call (below).
		 * <p/>
		 * Note that by default, this client tries to connect to a server on the local
		 * machine. However, this is supposed to work even if a bank process on a
		 * different box is specified. For a different box, the rmi URL would look
		 * something like this:
		 * 
		 * <pre>
		   -DremoteBank=rmi://bank.trustme.com/TrustyBank
		 * </pre>
		 */
		static String remoteBankUrl = initRemoteBankUrl();

		static String initRemoteBankUrl() {
			remoteBankUrl = System.getProperty("remoteBank", "rmi:///FirstRemote");
			System.out.println("Bank.Client is using this remote bank: " + remoteBankUrl);
			System.out.println("");
			return (remoteBankUrl);
		}

		public static void main(String[] args) {
			if (Boolean.valueOf(DEBUG) == true) {
				runDebugCycle();
			} else {
				originalMain(args);
			}
		}

		/**
		 * This program only works in Eclipse for the time being, so we simulate six
		 * command-line calls to the old original main() function, for debugging
		 * purposes. These are the same six command-line calls described in the manual,
		 * on p. 607. We could also do this by restoring the old main() function, and
		 * defining six Run Configurations.
		 */
		private static void runDebugCycle() {
			String[] args00 = { "open", "david", "javanut" };
			String[] args01 = { "deposit", "david", "javanut", "1000" };
			String[] args02 = { "withdraw", "david", "javanut", "100" };
			String[] args03 = { "balance", "david", "javanut" };
			String[] args04 = { "history", "david", "javanut" };
			String[] args05 = { "close", "david", "javanut" };
			String[][] allArgs = { args00, args01, args02, args03, args04, args05 };

			for (int i = 0; i < allArgs.length; i++) {
				originalMain(allArgs[i]);
			}
		}

		private static void originalMain(String[] args) {

			try {
				/*
				 * Now look up that RemoteBank server using the Naming object, which contacts
				 * the rmiregistry server. Given the url, this call returns a RemoteBank object
				 * whose methods may be invoked remotely.
				 */
				RemoteBank remoteBank = (RemoteBank) Naming.lookup(Client.remoteBankUrl);

				// Convert the user's command to lower case.
				String command = args[0].toLowerCase();

				/* Now, go test the command against a bunch of possible options. */
				// Open an account:
				if (command.equals("open") || command.equals("openAccount")) {
					remoteBank.openAccount(args[1], args[2]);
					System.out.println("Account opened.");
				}
				// Close an account:
				else if (command.equals("close") || command.equals("closeAccount")) {
					FunnyMoney money = remoteBank.closeAccount(args[1], args[2]);
					// Note: our currency is denominated in wooden nickels.
					System.out.println(money.amount + " wooden nickels returned to you.");
					System.out.println("Thanks for banking with us.");
				}
				// Deposit money:
				else if (command.equals("deposit")) {
					FunnyMoney money = new FunnyMoney(Integer.parseInt(args[3]));
					remoteBank.deposit(args[1], args[2], money);
					System.out.println("Deposited " + money.amount + " wooden nickels.");
				}
				// Withdraw money:
				else if (command.equals("withdraw")) {
					FunnyMoney money = remoteBank.withdraw(args[1], args[2], Integer.parseInt(args[3]));
					System.out.println("Withdrew " + money.amount + " wooden nickels.");
				}
				// Check account balance:
				else if (command.equals("balance") || command.equals("getBalance")) {
					int amount = remoteBank.getBalance(args[1], args[2]);
					System.out.println("You have " + amount + " wooden nickels in the bank.");
				}
				// Get transaction history:
				else if (command.equals("history") || command.equals("getTransactionHistory")) {
					List<String> transactions = remoteBank.getTransactionHistory(args[1], args[2]);
					for (int i = 0; i < transactions.size(); i++) {
						System.out.println(transactions.get(i));
					}
				} else
					System.out.println("Unknown command");
			}
			// Catch and display RMI exceptions:
			catch (RemoteException remoteException) {
				System.err.println(remoteException);
				if (Boolean.valueOf(DEBUG) == true) {
					remoteException.printStackTrace(System.err);
				}
			}
			// Catch and display Banking related exceptions:
			catch (BankingException bankingException) {
				System.err.println(bankingException.getMessage());
				if (Boolean.valueOf(DEBUG) == true) {
					bankingException.printStackTrace(System.err);
				}
			}
			// Other exceptions are probably user syntax errors, so show usage.
			catch (Exception e) {
				System.err.println(e);
				String usage = "Usage: java [-DremoteBank=<url>] Bank$Client <command> <name> <password> [<amount>]";
				System.err.println(usage);
				System.err.println("where <command> is one of: open, close, deposit, withdraw, balance, history");
			}
		}
	}
}
