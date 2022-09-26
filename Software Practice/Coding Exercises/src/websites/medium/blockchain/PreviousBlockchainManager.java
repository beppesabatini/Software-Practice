package websites.medium.blockchain;

import java.security.Security;
// Download this jar file from https://www.bouncycastle.org/latest_releases.html
import org.bouncycastle.jce.provider.BouncyCastleProvider;
// Download this jar file from https://repo1.maven.org/maven2/com/google/code/gson/gson/2.6.2/gson-2.6.2.jar
import com.google.gson.GsonBuilder;

/**
 * This is the old version of its parent class, BlockchainManager. It still does
 * some testing the parent class cannot do.
 * <p/>
 * This code comes mainly from this tutorial:
 * <ul>
 * <li><a href=
 * "https://medium.com/programmers-blockchain/create-simple-blockchain-java-tutorial-from-scratch-6eeed3cb03fa">Creating
 * Your First Blockchain with Java Part 1</a></li>
 * <li><a href=
 * "https://medium.com/programmers-blockchain/creating-your-first-blockchain-with-java-part-2-transactions-2cdac335e0ce">Creating
 * Your First Blockchain with Java Part 2</a></li>
 * </ul>
 */
public class PreviousBlockchainManager extends BlockchainManager {

	public static Wallet walletA;
	public static Wallet walletB;

	public static void main(String[] args) {

		if (Boolean.valueOf(DEBUG) == true) {
			System.out.println("In DEBUG mode");
		}

		initializeBlockchain();
		initializeTestWallets();
		testWalletTransaction();
	}

	private static void initializeBlockchain() {
		// Add three new Blocks to the blockchain ArrayList:
		blockchain.add(new Block("0"));
		System.out.println("Trying to mine the Genesis Block...");
		blockchain.get(0).mineBlock(difficulty);
		System.out.println();

		blockchain.add(new Block(blockchain.get(blockchain.size() - 1).currentHash));
		System.out.println("Trying to mine the Block #02...");
		blockchain.get(1).mineBlock(difficulty);
		System.out.println();

		blockchain.add(new Block(blockchain.get(blockchain.size() - 1).currentHash));
		System.out.println("Trying to mine the Block #03...");
		blockchain.get(2).mineBlock(difficulty);
		System.out.println();

		System.out.println("Blockchain is Valid: " + isChainValid());

		if (Boolean.valueOf(DEBUG) == true) {
			String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
			System.out.println("\nThe block chain: ");
			System.out.println(blockchainJson);
		}
	}

	private static void initializeTestWallets() {
		// Setup Bouncy Castle as a Security Provider
		Security.addProvider(new BouncyCastleProvider());
		// Create the new wallets
		walletA = new Wallet();
		walletB = new Wallet();
		// Test public and private keys
		System.out.println("Private and public keys:");
		System.out.println(StringHasher.decodeStringFromKey(walletA.privateKey));
		System.out.println(StringHasher.decodeStringFromKey(walletA.publicKey));
	}

	private static void testWalletTransaction() {
		// Create a test transaction from walletA to walletB
		Transaction transaction = new Transaction(walletA.publicKey, walletB.publicKey, 5, null);
		transaction.generateSignature(walletA.privateKey);
		// Verify the signature works, and verify it from the public key
		System.out.println("Is signature verified:");
		System.out.println(transaction.verifySignature());
	}

	private static Boolean isChainValid() {
		Block currentBlock;
		Block previousBlock;

		// Loop through blockchain to check hashes:
		for (int i = 1; i < blockchain.size(); i++) {
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i - 1);
			// Compare registered hash and calculated hash:
			if (currentBlock.currentHash.equals(currentBlock.calculateHash()) == false) {
				System.out.println("Current Hashes not equal");
				return false;
			}
			// Compare previous hash and registered previous hash:
			if (previousBlock.currentHash.equals(currentBlock.previousHash) == false) {
				System.out.println("Previous Hashes not equal");
				return false;
			}
		}
		return true;
	}
}
