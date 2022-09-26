package websites.medium.blockchain;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class Wallet {

	public PrivateKey privateKey;
	public PublicKey publicKey;

	// Elliptic Curve with Digital Signature Algorithm
	public final static String SIGNATURE_ALGORITHM = "ECDSA";
	// Bouncy Castle
	public final static String SIGNATURE_PROVIDER = "BC";
	// Secure Hash Algorithm 1 Pseudo Random Number Generator
	private final static String SECURE_RANDOM_ALGORITHM = "SHA1PRNG";
	// 192-bit prime field Weierstrass curve
	private final static String ELLIPTIC_CURVE_NAME = "prime192v1";

	// Track the unspent coins owned by this wallet:
	public Map<String, TransactionOutput> localUnspentTransactionObjects = new HashMap<String, TransactionOutput>();

	public Wallet() {
		generateAuthenticationKeyPair();
	}

	public void generateAuthenticationKeyPair() {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance(SIGNATURE_ALGORITHM, SIGNATURE_PROVIDER);
			SecureRandom random = SecureRandom.getInstance(SECURE_RANDOM_ALGORITHM);
			ECGenParameterSpec curveSpecification = new ECGenParameterSpec(ELLIPTIC_CURVE_NAME);
			// Initialize the key generator and generate a KeyPair:
			keyGen.initialize(curveSpecification, random); // 256 bytes provides an acceptable security level
			KeyPair keyPair = keyGen.generateKeyPair();

			// Set the public and private keys from the keyPair:
			this.privateKey = keyPair.getPrivate();
			this.publicKey = keyPair.getPublic();
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	/*
	 * This method returns how much money we own and can spend, and also tracks (in
	 * a local Map) the unspent TransactionObjects owned by this Wallet.
	 */
	public float getBalance() {
		float unspentTotal = 0;
		localUnspentTransactionObjects = new HashMap<String, TransactionOutput>();

		Map<String, TransactionOutput> allUnspentCoinsInChain = BlockchainManager.unspentTransactionOutputs;

		for (Map.Entry<String, TransactionOutput> keyValuePair : allUnspentCoinsInChain.entrySet()) {
			TransactionOutput currentUnspentTransactionOutput = keyValuePair.getValue();
			// If this transaction belongs to me (if these coins belong to me):
			if (currentUnspentTransactionOutput.isMine(publicKey)) {
				// Add it to our local Map of unspent transactions:
				localUnspentTransactionObjects.put(currentUnspentTransactionOutput.id, currentUnspentTransactionOutput);
				unspentTotal += currentUnspentTransactionOutput.transactionAmount;
			}
		}
		return (unspentTotal);
	}

	// Generates and returns a new transaction spending coins from this wallet.
	public Transaction sendFunds(PublicKey coinsRecipient, float transactionAmount) {
		// See how much money we have and see if it's enough:
		if (this.getBalance() < transactionAmount) {
			System.out.println("Sorry, you do not have enough funds to send this transaction.");
			return null;
		}
		// Create array list of inputs
		List<TransactionInput> transactionInputs = new ArrayList<TransactionInput>();

		float availableToSend = 0;
		for (Map.Entry<String, TransactionOutput> keyValuePair : localUnspentTransactionObjects.entrySet()) {
			TransactionOutput localUnspentTransactionObject = keyValuePair.getValue();
			availableToSend += localUnspentTransactionObject.transactionAmount;
			transactionInputs.add(new TransactionInput(localUnspentTransactionObject.id));
			if (availableToSend > transactionAmount) {
				break;
			}
		}

		Transaction newTransaction = new Transaction(publicKey, coinsRecipient, transactionAmount, transactionInputs);
		newTransaction.generateSignature(privateKey);

		for (TransactionInput transactionInput : transactionInputs) {
			localUnspentTransactionObjects.remove(transactionInput.transactionOutputId);
		}
		return newTransaction;
	}

}
