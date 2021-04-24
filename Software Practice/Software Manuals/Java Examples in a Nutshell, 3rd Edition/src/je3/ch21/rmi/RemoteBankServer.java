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

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import je3.ch21.rmi.Bank.RemoteBank;
import je3.ch21.rmi.Bank.BankingException;
import je3.ch21.rmi.Bank.FunnyMoney;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 611-614. This class
 * implements the remote methods defined by the RemoteBank interface. It has a
 * serious shortcoming, though: all account data is lost when the server goes
 * down. The next exercise will save the data to a DB. This RemoteBankServer
 * only runs in Eclipse for the time being.
 * <p/>
 * To test this:
 * 
 * <pre>
 *  •  cd &lt;project_home>\bin
 *  •  &lt;java_home>\rmic je3.ch21.rmi.RemoteBankServer 
 *     ▬ This generates RemoteBankServer_Stub.class in the same \bin directory as the original 
 *       RemoteBankServer.class file. The RMI process requires this stub, for reasons not clear.
 *  •  &lt;java_home>\rmiregistry
 *     ▬ Launch this background process. It maintains a map from local names to remote executables. 
 *       It's a little bit like a Domain Name Sever, but it's registering remotely accessible classes.
 *  •  java -DbankName=FirstRemote RemoteBankServer 
 *     ▬ The main() function in the file you are reading will register this bank as a possible
 *       remote executable. You can launch it with the Run Configuration named RemoteBankServer. 
 *  •  java -DremoteBank=rmi:///FirstRemote Bank.Client
 *     ▬ This instantiates and runs a Client object, in another file, which connects 
 *       (remotely) to the RemoteBankServer object defined below. This is supposed to work 
 *       even if the Client object and the RemoteBankServer object are on different boxes. 
 *       Users can launch this command line with the Run Configuration named 
 *       "Bank.Client invoking RemoteBankServer". It retrieves the remote bank from the 
 *       rmiregistry map, and simulates six command-line calls to RemoteBankServer (this file).
 * </pre>
 */
public class RemoteBankServer extends UnicastRemoteObject implements RemoteBank {

	private static final long serialVersionUID = 3195132114949569045L;

	/**
	 * This nested class stores data for a single account with the bank.
	 */
	class Account {
		// Account password:
		String password;
		// Account balance:
		int balance;
		// Account transaction history:
		List<String> transactions = new ArrayList<String>();

		Account(String password) {
			this.password = password;
			transactions.add("Account opened at " + new Date());
		}
	}

	/**
	 * This hashtable stores all open accounts, and maps from Account name to
	 * Account object. Methods that use this object will be synchronized, to prevent
	 * concurrent access by more than one thread.
	 */
	Map<String, Account> accounts = new HashMap<String, Account>();

	/**
	 * This constructor doesn't do anything, but because the superclass constructor
	 * throws an exception, the exception must be declared here
	 */
	public RemoteBankServer() throws RemoteException {
		super();
	}

	/**
	 * Open a bank account with the specified name and password. This method is
	 * synchronized to make it thread safe, since it manipulates the accounts
	 * hashtable. .
	 */
	@Override
	public synchronized void openAccount(String accountName, String password) throws RemoteException, BankingException {
		// Check if there is already an account under the specified name.
		if (accounts.get(accountName) != null) {
			throw new BankingException("Account already exists.");
		}
		// Otherwise, it doesn't exist, so create it.
		Account account = new Account(password);
		// ...and, register it
		accounts.put(accountName, account);
	}

	/**
	 * This internal method is not a remote method. Given a name and password it
	 * checks to see if an account with that name and password exists. If so, it
	 * returns the Account object. Otherwise, it throws an exception. This method is
	 * synchronized because it uses the accounts hashtable.
	 */
	synchronized Account verify(String accountName, String password) throws BankingException {
		Account account = accounts.get(accountName);
		if (account == null) {
			throw new BankingException("No such account");
		}
		if (!password.equals(account.password)) {
			throw new BankingException("Invalid password");
		}
		return account;
	}

	/**
	 * Close the named account. This method is synchronized to make it thread safe,
	 * since it manipulates the accounts hashtable.
	 */
	@Override
	public synchronized FunnyMoney closeAccount(String accountName, String password)
			throws RemoteException, BankingException {
		Account account;
		account = verify(accountName, password);
		accounts.remove(accountName);
		/*
		 * Before changing the balance or transactions of any account, we first have to
		 * obtain a lock on that account to be thread safe.
		 */
		synchronized (account) {
			int balance = account.balance;
			account.balance = 0;
			return new FunnyMoney(balance);
		}
	}

	/** Deposit the specified FunnyMoney to the named account. */
	@Override
	public void deposit(String accountName, String password, FunnyMoney money)
			throws RemoteException, BankingException {
		Account account = verify(accountName, password);
		synchronized (account) {
			account.balance += money.amount;
			account.transactions.add("Deposited " + money.amount + " on " + new Date());
		}
	}

	/** Withdraw the specified amount from the named account. */
	@Override
	public FunnyMoney withdraw(String accountName, String password, int amount)
			throws RemoteException, BankingException {
		Account account = verify(accountName, password);
		synchronized (account) {
			if (account.balance < amount) {
				throw new BankingException("Insufficient Funds");
			}
			account.balance -= amount;
			account.transactions.add("Withdrew " + amount + " on " + new Date());
			return new FunnyMoney(amount);
		}
	}

	/** Return the current balance in the named account. */
	@Override
	public int getBalance(String accountName, String password) throws RemoteException, BankingException {
		Account account = verify(accountName, password);
		synchronized (account) {
			return account.balance;
		}
	}

	/**
	 * Return a List of strings containing the transaction history for the named
	 * account.
	 */
	public List<String> getTransactionHistory(String accountName, String password)
			throws RemoteException, BankingException {
		Account account = verify(accountName, password);
		synchronized (account) {
			return account.transactions;
		}
	}

	/**
	 * The main program that runs this RemoteBankServer. Create a RemoteBankServer
	 * object, and store it with a name in the registry. Read a system property to
	 * determine the name, but use "FirstRemote" as the default name. This is all
	 * that is necessary to set up the service. RMI takes care of the rest.
	 */
	public static void main(String[] args) {
		try {
			// Create a bank server object:
			RemoteBankServer remoteBankServer = new RemoteBankServer();
			// Figure out what to name it:
			String bankName = System.getProperty("bankName", "FirstRemote");
			// Name it that:
			Naming.rebind(bankName, remoteBankServer);
			// Tell the world we're up and running:
			System.out.println("Good! " + bankName + " is open and ready for customers.");
		} catch (Exception exception) {
			System.err.println(exception);
			System.err.println("Usage: java [-DbankName=<name>] je3.ch21.rmi.RemoteBankServer");
			// Force exit because there may be RMI threads open:
			System.exit(1);
		}
	}
}
