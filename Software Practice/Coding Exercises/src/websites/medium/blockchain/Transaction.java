package websites.medium.blockchain;

import java.util.ArrayList;
import java.util.List;
import java.security.PrivateKey;
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
public class Transaction {
	// This transactionId is also the hash of the transaction.
	public String transactionId;
	// This value is the sender's address, also called his public key.
	public PublicKey sender;
	// This value is the recipient's address, also called his public key.
	public PublicKey recipient;
	public float transactionAmount;
	// This is to prevent anybody else from spending funds in our wallet.
	public byte[] signature;

	public List<TransactionInput> transactionInputs = new ArrayList<TransactionInput>();
	public List<TransactionOutput> transactionOutputs = new ArrayList<TransactionOutput>();

	// A rough count of how many transactions have been generated.
	private static int sequence = 0;

	// Constructor:
	public Transaction(PublicKey from, PublicKey to, float transactionAmount,
			List<TransactionInput> transactionInputs) {
		this.sender = from;
		this.recipient = to;
		this.transactionAmount = transactionAmount;
		this.transactionInputs = transactionInputs;
	}

	// This calculates the transaction hash (which will be used as its unique ID).
	private String calculateHash() {
		/*
		 * Increase the sequence value, to avoid 2 identical transactions having the
		 * same hash.
		 */
		sequence++;

		String senderString = StringHasher.decodeStringFromKey(this.sender);
		String recipientString = StringHasher.decodeStringFromKey(this.recipient);
		String amountString = Float.toString(this.transactionAmount);
		String transactionSeed = senderString + recipientString + amountString + sequence;
		String transactionHash = StringHasher.applySha256(transactionSeed);
		return (transactionHash);
	}

	// Use this signature for all the data we don't wish to be tampered with.
	public void generateSignature(PrivateKey privateKey) {
		String signatureSeed = "";
		signatureSeed += StringHasher.decodeStringFromKey(this.sender);
		signatureSeed += StringHasher.decodeStringFromKey(this.recipient);
		signatureSeed += Float.toString(this.transactionAmount);
		this.signature = StringHasher.buildSignature(privateKey, signatureSeed);
	}

	// Confirm that a particular signature is authentic.
	public boolean verifySignature() {
		String signatureSeed = "";
		signatureSeed += StringHasher.decodeStringFromKey(this.sender);
		signatureSeed += StringHasher.decodeStringFromKey(this.recipient);
		signatureSeed += Float.toString(this.transactionAmount);
		return StringHasher.verifySignature(sender, signatureSeed, this.signature);
	}

	// Returns true if a new transaction could be created.
	public boolean processTransaction() {

		if (verifySignature() == false) {
			System.out.println("Sorry, your transaction signature failed to verify");
			return (false);
		}

		// Gather transaction inputs (make sure they are unspent):
		for (TransactionInput transactionInput : transactionInputs) {
			String parentID = transactionInput.transactionOutputId;
//			Map<String, TransactionOutput> unspentTransactionOutputs = BlockchainManager.getUnspentTransactionOutputs();
			transactionInput.unspentTransactionOutput = BlockchainManager.unspentTransactionOutputs.get(parentID);
		}

		// Check if transaction is valid:
		if (getInputsValue() < BlockchainManager.minimumTransaction) {
			System.out.println("Sorry, your transaction input is too small: " + getInputsValue());
			return (false);
		}

		// Now generate TransactionOutputs.
		// Get the value of the inputs, then the leftover change:
		float leftover = getInputsValue() - this.transactionAmount;
		transactionId = calculateHash();
		// Send the desired amount to the recipient:
		TransactionOutput forRecipient = new TransactionOutput(this.recipient, this.transactionAmount, transactionId);
		transactionOutputs.add(forRecipient);
		// Send the leftover 'change' back to sender:
		TransactionOutput forSender = new TransactionOutput(this.sender, leftover, transactionId);
		transactionOutputs.add(forSender);

		// Add outputs to the unspent list:
		for (TransactionOutput transactionOutput : transactionOutputs) {
			BlockchainManager.unspentTransactionOutputs.put(transactionOutput.id, transactionOutput);
		}

		/*
		 * Remove transaction inputs from unspent transaction output lists, because (of
		 * course) now they're spent:
		 */
		for (TransactionInput transactionInput : transactionInputs) {
			// If a Transaction can't be found, skip it:
			if (transactionInput.unspentTransactionOutput == null) {
				continue;
			}
			BlockchainManager.unspentTransactionOutputs.remove(transactionInput.unspentTransactionOutput.id);
		}

		return (true);
	}

	// Return sum of the input values (the unspent TransactionOutput values):
	public float getInputsValue() {
		float inputsTotal = 0f;
		for (TransactionInput transactionInput : transactionInputs) {
			// If a Transaction can't be found, skip it.
			if (transactionInput.unspentTransactionOutput == null) {
				continue;
			}
			inputsTotal += transactionInput.unspentTransactionOutput.transactionAmount;
		}
		return (inputsTotal);
	}

	// Return the sum of the outputs:
	public float getOutputsValue() {
		float outputsTotal = 0f;
		for (TransactionOutput transactionOutput : transactionOutputs) {
			outputsTotal += transactionOutput.transactionAmount;
		}
		return (outputsTotal);
	}

}
