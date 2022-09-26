package websites.medium.blockchain;

import java.security.PublicKey;
import java.security.Security;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

// Download this jar file from https://www.bouncycastle.org/latest_releases.html
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * This is a little toy blockchain designed to illustrate the algorithm by which
 * a real blockchain works. This code comes mainly from this tutorial:
 * <ul>
 * <li><a href=
 * "https://medium.com/programmers-blockchain/create-simple-blockchain-java-tutorial-from-scratch-6eeed3cb03fa">Creating
 * Your First Blockchain with Java Part 1</a></li>
 * <li><a href=
 * "https://medium.com/programmers-blockchain/creating-your-first-blockchain-with-java-part-2-transactions-2cdac335e0ce">Creating
 * Your First Blockchain with Java Part 2</a></li>
 * </ul>
 * <p/>
 * Note that what's usually called "Bitcoin mining" would be better called "hash
 * racing." Hash values for the chain are, by design, very expensive
 * computationally. All members of the distributed peer-to-peer network complete
 * to see which can come up with a valid hash value first. The winner adds a new
 * block to the chain, and this new, longest version, is now the most up-to-date
 * blockchain.
 * <p/>
 * All the peers in the race have been helpful, so all now receive some payment.
 * The blockchain application now declares--by royal decree, by fiat--"I now
 * pronounce that some new money exists." The new digital coins are distributed
 * to the network, and the winner of the race gets a larger share. As long as
 * everyone believes the digital coin has value, and will keep its value, it is
 * a self-fulfilling prophecy.
 */
public class BlockchainManager {

	protected static final String DEBUG = "false";

	protected static List<Block> blockchain = new ArrayList<Block>();
	public static int difficulty = 5;

	/*
	 * A record of all the unspent money, throughout the entire blockchain. A real
	 * blockchain really does keep such a record, but of course it uses something
	 * fancier than a global HashMap.
	 */
	public static Map<String, TransactionOutput> unspentTransactionOutputs = new HashMap<String, TransactionOutput>();

	public static final float minimumTransaction = 0.1f;

	public static void main(String[] args) {

		if (Boolean.valueOf(DEBUG) == true) {
			System.out.println("In DEBUG mode");
		}
		// Add our blocks to the blockchain ArrayList.
		/*
		 * First, setup Bouncy Castle as a Security Provider. This will help by doing
		 * things like generating authorization keys.
		 */
		Security.addProvider(new BouncyCastleProvider());

		// Create three wallets:
		Wallet walletA = new Wallet();
		Wallet walletB = new Wallet();
		Wallet seedMoneyWallet = new Wallet();

		// Create the first item in the Blockchain: the coinbase transaction, which
		// sends 100 JingleCoins to walletA:
		Transaction coinbaseTransaction = new Transaction(seedMoneyWallet.publicKey, walletA.publicKey, 100f, null);
		// Manually sign the coinbase transaction:
		coinbaseTransaction.generateSignature(seedMoneyWallet.privateKey);
		// Manually set the transaction id:
		coinbaseTransaction.transactionId = "0";
		// Manually add the TransactionOutput:
		PublicKey firstRecipient = coinbaseTransaction.recipient;
		float firstAmount = coinbaseTransaction.transactionAmount;
		String firstID = coinbaseTransaction.transactionId;
		TransactionOutput firstTransactionOutput = new TransactionOutput(firstRecipient, firstAmount, firstID);
		coinbaseTransaction.transactionOutputs.add(firstTransactionOutput);
		// Store our first transaction in the (global) unspent money list:
		unspentTransactionOutputs.put(firstTransactionOutput.id, firstTransactionOutput);

		System.out.println("Creating and Mining the Genesis Block... ");
		Block genesisBlock = new Block("0");
		genesisBlock.addTransaction(coinbaseTransaction);
		addBlock(genesisBlock);

		// Now testing basic functionality;
		Block block1 = new Block(genesisBlock.currentHash);
		System.out.println("\nWalletA's balance is: " + walletA.getBalance());
		System.out.println("\nWalletA is attempting to send funds (40) to WalletB...");
		block1.addTransaction(walletA.sendFunds(walletB.publicKey, 40f));
		addBlock(block1);

		System.out.println("\nWalletA's balance is: " + walletA.getBalance());
		System.out.println("WalletB's balance is: " + walletB.getBalance());

		Block block2 = new Block(block1.currentHash);
		System.out.println("\nWalletA (intentionally) attempting to send more funds (1000) than it has...");
		block2.addTransaction(walletA.sendFunds(walletB.publicKey, 1000f));
		addBlock(block2);
		System.out.println("\nWalletA's balance is: " + walletA.getBalance());
		System.out.println("WalletB's balance is: " + walletB.getBalance());

		Block block3 = new Block(block2.currentHash);
		System.out.println("\nWalletB is attempting to send funds (20) to WalletA...");
		block3.addTransaction(walletB.sendFunds(walletA.publicKey, 20));
		System.out.println("\nWalletA's balance is: " + walletA.getBalance());
		System.out.println("WalletB's balance is: " + walletB.getBalance());

		isChainValid(coinbaseTransaction);
	}

	private static Boolean isChainValid(Transaction coinbaseTransaction) {
		Block currentBlock;
		Block previousBlock;
		String hashLeadingZeroes = new String(new char[difficulty]).replace('\0', '0');

		/*
		 * This is a temporary working list of unspent transactions (usually unspent
		 * money) at a given block state.
		 */
		Map<String, TransactionOutput> localUnspentTransactionObjects = new HashMap<String, TransactionOutput>();

		String firstTransactionOutputId = coinbaseTransaction.transactionOutputs.get(0).id;
		TransactionOutput firstTransactionOutput = coinbaseTransaction.transactionOutputs.get(0);
		localUnspentTransactionObjects.put(firstTransactionOutputId, firstTransactionOutput);

		// Loop through each block in the blockchain, to check integrity:
		for (int i = 1; i < blockchain.size(); i++) {

			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i - 1);
			// Compare registered hash and calculated hash:
			if (currentBlock.currentHash.equals(currentBlock.calculateHash()) == false) {
				System.out.println("Sorry, block hashes do not match");
				return (false);
			}
			// Compare previous hash and registered previous hash:
			if (previousBlock.currentHash.equals(currentBlock.previousHash) == false) {
				System.out.println("Sorry, hashes for the previous block do not match");
				return false;
			}
			// Check if hash is well-formed, with N leading zeroes:
			if (currentBlock.currentHash.substring(0, difficulty).equals(hashLeadingZeroes) == false) {
				System.out.println("Sorry, this block hasn't been mined correctly");
				return false;
			}

			// Loop each transaction in the current Block:
			TransactionOutput currentUnspentTransactionOutput;
			for (int t = 0; t < currentBlock.transactions.size(); t++) {
				Transaction currentTransaction = currentBlock.transactions.get(t);

				if (currentTransaction.verifySignature() == false) {
					System.out.println("Sorry, the signature on transaction " + t + " is invalid");
					return false;
				}
				/*
				 * A transaction has to spend all the money it has; if he has change due, he
				 * sends it in a new output, back to himself.
				 */
				if (currentTransaction.getInputsValue() != currentTransaction.getOutputsValue()) {
					System.out.println("Sorry, the inputs are not equal to the outputs on transaction " + t);
					return false;
				}

				for (TransactionInput transactionInput : currentTransaction.transactionInputs) {
					String currentTransactionOutputId = transactionInput.transactionOutputId;
					currentUnspentTransactionOutput = localUnspentTransactionObjects.get(currentTransactionOutputId);

					if (currentUnspentTransactionOutput == null) {
						System.out.println("Sorry, the referenced output on transaction " + t + " is missing");
						return false;
					}

					if (transactionInput.unspentTransactionOutput.transactionAmount != currentUnspentTransactionOutput.transactionAmount) {
						System.out.println("Sorry, the referenced output on transaction " + t + " value is invalid");
						return false;
					}

					localUnspentTransactionObjects.remove(transactionInput.transactionOutputId);
				}

				for (TransactionOutput output : currentTransaction.transactionOutputs) {
					localUnspentTransactionObjects.put(output.id, output);
				}

				if (currentTransaction.transactionOutputs.get(0).recipient != currentTransaction.recipient) {
					System.out.println("Sorry, in transaction " + t + " output recipient is not who it should be");
					return false;
				}
				if (currentTransaction.transactionOutputs.get(1).recipient != currentTransaction.sender) {
					System.out.println("Sorry, in transaction " + t + " the sender is not correctly specified.");
					return false;
				}
			}
		}
		System.out.println("Success! Blockchain is valid.");
		return true;
	}

	public static void addBlock(Block newBlock) {
		newBlock.mineBlock(difficulty);
		blockchain.add(newBlock);
	}
}
