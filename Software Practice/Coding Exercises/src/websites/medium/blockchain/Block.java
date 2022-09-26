package websites.medium.blockchain;

import java.util.ArrayList;
import java.util.List;

/**
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
public class Block {

	private static final String DEBUG = "true";

	public String currentHash;
	public String previousHash;
	public String merkleRoot;
	public List<Transaction> transactions = new ArrayList<Transaction>();

	// Nonce is short for number used only once:
	private int nonce = 0;

	// Block Constructor.
	public Block(String previousHash) {
		this.previousHash = previousHash;
		this.currentHash = calculateHash();
	}

	/**
	 * Note that the hash value for a new block includes the Merkle root. This is a
	 * hash of all the hash values for the entire blockchain. This is what make a
	 * blockchain tamper-proof, or, more precisely, "tamper-evident." If anyone
	 * changes even one character in the chain, then every block downstream will
	 * have its hash value changed.
	 */
	public String calculateHash() {
		String hashSeed = "";
		hashSeed += this.previousHash;
		hashSeed += Integer.toString(this.nonce);
		hashSeed += this.merkleRoot;
		String calculatedhash = StringHasher.applySha256(hashSeed);
		return calculatedhash;
	}

	/**
	 * This function is a wrapper around the calculateHash() function, which
	 * deliberately makes it very difficult to come up with an acceptable hash
	 * value. The goal is to make it impossible for hackers to rebuild the entire
	 * blockchain, with (for example) themselves now owning all the money. Below, we
	 * keep recalculating and overwriting the currentHash value for this block,
	 * until we get a hash value with N leading zeros, where N equals the value of
	 * the "difficulty" argument.
	 * 
	 * @param difficulty Number of leading zeroes in an acceptable hash value
	 */
	public void mineBlock(int difficulty) {

		this.merkleRoot = StringHasher.getMerkleRoot(transactions);
		/*
		 * Create a String of zeroes N characters long, where N equals the difficulty
		 * argument. Do this by replacing every empty space in the empty "target" array
		 * with a character '0'.
		 */
		long startTime = System.currentTimeMillis();
		String target = new String(new char[difficulty]).replace('\0', '0');
		while (this.currentHash.substring(0, difficulty).equals(target) == false) {
			this.nonce++;
			this.currentHash = calculateHash();
		}
		long endTime = System.currentTimeMillis();

		String message01 = String.format("Block Mined!!! : %s", this.currentHash);
		System.out.println(message01);

		if (Boolean.valueOf(DEBUG) == true) {
			String mineTime = Long.toString(endTime - startTime);
			String message02 = String.format("Time to Mine: %s ms  Attempts: %d", mineTime, this.nonce);
			System.out.println(message02);
		}
	}

	// Add transactions to this block.
	public boolean addTransaction(Transaction transaction) {
		// Process the transaction and check if it is valid--unless the block is the
		// Genesis Block, in which case ignore it.
		if (transaction == null) {
			return (false);
		}
		if ((previousHash != "0")) { // The Genesis Block
			if ((transaction.processTransaction() != true)) {
				System.out.println("Sorry, your transaction failed to process. Discarded.");
				return (false);
			}
		}
		transactions.add(transaction);
		System.out.println("Success! Transaction successfully added to Block");
		return (true);
	}
}
