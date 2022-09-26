package websites.medium.blockchain;

import java.security.PublicKey;

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
public class TransactionOutput {
	public String id;
	// The new owner of these coins:
	public PublicKey recipient;
	// The amount of digital coins involved:
	public float transactionAmount;
	// The id of the transaction this output was created in:
	public String parentTransactionId;

	// Constructor
	public TransactionOutput(PublicKey recipient, float transactionAmount, String parentTransactionId) {
		this.recipient = recipient;
		this.transactionAmount = transactionAmount;
		this.parentTransactionId = parentTransactionId;
		String recipientString = StringHasher.decodeStringFromKey(recipient);
		String transactionAmountString = Float.toString(transactionAmount);
		String idSeedString = recipientString + transactionAmountString + parentTransactionId;
		this.id = StringHasher.applySha256(idSeedString);
	}

	// Check if coin belongs to you:
	public boolean isMine(PublicKey publicKey) {
		return (publicKey == recipient);
	}
}
