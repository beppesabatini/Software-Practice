package websites.medium.blockchain;

import java.security.Key;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;
import java.util.Base64.Encoder;
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
public class StringHasher {

	private static final String DEBUG = "false";

	/**
	 * Applies Sha256 (Secure Hash Algorithm 256) to a string, and returns the
	 * result. This is a hashing function, not an encryption function. There is no
	 * way to reverse it. This method comes mainly from
	 * <a href="https://www.baeldung.com/sha-256-hashing-java">Baeldung</a>.
	 */
	public static String applySha256(String input) {
		try {
			MessageDigest hasher = MessageDigest.getInstance("SHA-256");

			byte[] hashedInput = hasher.digest(input.getBytes("UTF-8"));
			// This will contain the hashed input as a hexadecimal string (base 16).
			StringBuilder hexStringBuilder = new StringBuilder();
			for (int i = 0; i < hashedInput.length; i++) {
				// Bytes don't get cast to ints the way you might expect, so cast them this way
				// instead:
				int hashedInputInt = 0xff & hashedInput[i];
				String hexIntegerString = Integer.toHexString(hashedInputInt);
				if (hexIntegerString.length() == 1) {
					hexStringBuilder.append('0');
				}
				hexStringBuilder.append(hexIntegerString);
			}
			String hashedOutput = hexStringBuilder.toString();
			if (Boolean.valueOf(DEBUG) == true) {
				System.out.println("Hashed Output: " + hashedOutput);
			}
			return (hashedOutput);

		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	public static String decodeStringFromKey(Key key) {
		/*
		 * Base64 is similar to a serialization process. It takes binary values and
		 * flattens them into an ASCII string. It is reversible.
		 */
		Encoder encoderDecoder = Base64.getEncoder();
		byte[] encodedValue = key.getEncoded();
		String decodedString = encoderDecoder.encodeToString(encodedValue);
		return (decodedString);
	}

	public static byte[] buildSignature(PrivateKey privateKey, String signatureSeedString) {
		byte[] output = new byte[0];
		try {
			Signature signatureManager = Signature.getInstance(Wallet.SIGNATURE_ALGORITHM, Wallet.ALGORITHM_PROVIDER);
			signatureManager.initSign(privateKey);
			byte[] signatureSeedBytes = signatureSeedString.getBytes();
			signatureManager.update(signatureSeedBytes);
			byte[] finalSignature = signatureManager.sign();
			output = finalSignature;
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
		return output;
	}

	public static boolean verifySignature(PublicKey publicKey, String signatureSeedString, byte[] signature) {
		boolean isValid = false;
		try {
			Signature signatureManager = Signature.getInstance(Wallet.SIGNATURE_ALGORITHM, Wallet.ALGORITHM_PROVIDER);
			signatureManager.initVerify(publicKey);
			signatureManager.update(signatureSeedString.getBytes());
			isValid = signatureManager.verify(signature);
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
		return (isValid);
	}

	/*
	 * Takes an array of transactions, and returns a Merkle root. A Merkle root is
	 * (something like) a hash of all the hash values in the transactions.
	 */
	public static String getMerkleRoot(List<Transaction> transactions) {
		int transactionCount = transactions.size();
		List<String> previousTreeLayer = new ArrayList<String>();
		for (Transaction transaction : transactions) {
			previousTreeLayer.add(transaction.transactionId);
		}
		List<String> treeLayer = previousTreeLayer;
		while (transactionCount > 1) {
			treeLayer = new ArrayList<String>();
			for (int i = 1; i < previousTreeLayer.size(); i++) {
				treeLayer.add(applySha256(previousTreeLayer.get(i - 1) + previousTreeLayer.get(i)));
			}
			transactionCount = treeLayer.size();
			previousTreeLayer = treeLayer;
		}
		String merkleRoot = (treeLayer.size() == 1) ? treeLayer.get(0) : "";
		return (merkleRoot);
	}
}
