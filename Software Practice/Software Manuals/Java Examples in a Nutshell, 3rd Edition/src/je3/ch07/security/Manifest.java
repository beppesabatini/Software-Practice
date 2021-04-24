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
package je3.ch07.security;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 196-202. This program
 * creates a manifest file for the specified file, or verifies an existing
 * manifest file. By default the manifest file is named MANIFEST, but the -m
 * option can be used to override this. The -v option specifies that the
 * manifest should be verified. Verification is also the default option if no
 * files are specified.
 * <p/>
 * To test this file, we generated a keyfile pair like so:
 * 
 * <pre>
 * &lt;java_home>\bin\keytool.exe -genkeypair -alias &lt;myAlias> -keysize 2048
 * </pre>
 * 
 * The keys are by default stored in the user's home directory and in the file
 * ".keystore". Then to generate your manifest file, launch the Run
 * Configuration named "Manifest Create", or else refer to that configuration to
 * use its command line values.
 * <p/>
 * The manual and the code discuss a manifest generated for a list of files, but
 * in fact you will have to revise the code a little if you want to make that
 * happen. The program only supports one file at a time for now.
 * <p/>
 * To verify the manifest you created, copy it from the output directory for
 * this project, over to the input directory. Then launch the Run Configuration
 * "Manifest Verify". Don't check in the manifest file, either into the input
 * directory or into the output directory; it can't be used by anyone without
 * your keystore.
 */
public class Manifest {
	public static void main(String[] args) throws Exception {
		/*
		 * Set the default values of the command-line arguments.
		 */
		// Verify a manifest, or create one?
		boolean verify = false;
		// Manifest file name:
		String manifestFileName = "MANIFEST";
		// Algorithm for message digests, also called the hashing algorithm:
		String digestAlgorithm = "MD5";
		// The signer. There is no default signature.
		String signerName = null;
		// Digital Signature Algorithm:
		String signatureAlgorithm = "DSA";
		// Private keys are password-protected.
		String password = null;
		// Where are keys stored? (The default is the user's home directory.)
		File keystoreFile = null;
		// What kind of keystore?
		String keystoreType = null;
		// How to access the keystore:
		String keystorePassword = null;
		// The files to digest:
		List<String> fileList = new ArrayList<String>();

		// Parse the command-line arguments, overriding the defaults above.
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-v") || args[i].equals("-verify")) {
				verify = true;
			} else if (args[i].equals("-m") || args[i].equals("-manifestFile")) {
				manifestFileName = args[++i];
			} else if ((args[i].equals("-da") || args[i].equals("digestAlgorithm")) && verify == false) {
				digestAlgorithm = args[++i];
			} else if ((args[i].equals("-s") || args[i].equals("-signerName")) && verify == false) {
				signerName = args[++i];
			} else if ((args[i].equals("-sa") || args[i].equals("-signatureAlgorithm")) && verify == false) {
				signatureAlgorithm = args[++i];
			} else if (args[i].equals("-p") || args[i].contentEquals("-password")) {
				password = args[++i];
			} else if (args[i].equals("-keystore")) {
				keystoreFile = new File(args[++i]);
			} else if (args[i].equals("-keystoreType")) {
				keystoreType = args[++i];
			} else if (args[i].equals("-keystorePassword")) {
				keystorePassword = args[++i];
			} else if (verify == false) {
				// This program is only supporting one file at a time for now.
				fileList.add(args[i]);
			} else {
				throw new IllegalArgumentException(args[i]);
			}
		}

		// If certain arguments weren't supplied, get default values.
		if (keystoreFile == null) {
			/*
			 * The keytool utility in the JDK, and the program you are reading, both default
			 * to storing the keystore in the user's home directory.
			 */
			File homeDirectory = new File(System.getProperty("user.home"));
			keystoreFile = new File(homeDirectory, ".keystore");
		}
		if (keystoreType == null) {
			keystoreType = KeyStore.getDefaultType();
		}
		if (keystorePassword == null) {
			keystorePassword = password;
		}

		if (verify == false && signerName != null && password == null) {
			System.out.println("Use -p to specify a password.");
			return;
		}

		/*
		 * Get the keystore we'll use for signing or verifying signatures. If no
		 * password was provided, then assume we won't be dealing with signatures, and
		 * skip the keystore.
		 */
		KeyStore keystore = null;
		if (keystorePassword != null) {
			keystore = KeyStore.getInstance(keystoreType);
			InputStream inputStream = new BufferedInputStream(new FileInputStream(keystoreFile));
			keystore.load(inputStream, keystorePassword.toCharArray());
		}

		/*
		 * If -v was specified, or else if no files to process were specified, verify a
		 * manifest. Otherwise, create a new manifest for the specified files.
		 */
		if (verify == true || (fileList.size() == 0)) {
			verify(manifestFileName, keystore);
		} else {
			create(manifestFileName, digestAlgorithm, signerName, signatureAlgorithm, keystore, password, fileList);
		}
	}

	/**
	 * This method creates a manifest file with the specified name, for the
	 * specified vector of files, using the named message digest algorithm. If
	 * signerName is non-null, it adds a digital signature to the manifest, using
	 * the named signature algorithm. This method can throw a bunch of exceptions.
	 */
	public static void create(String manifestFileName, String digestAlgorithm, String signerName,
			String signatureAlgorithm, KeyStore keystore, String password, List<String> filelist)
			throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, KeyStoreException,
			UnrecoverableKeyException, IOException {
		/*
		 * For computing a signature, we have to process the files in a fixed,
		 * repeatable order, so sort them alphabetically.
		 */
		Collections.sort(filelist);
		int numberFiles = filelist.size();

		Properties manifest = new Properties();
		Properties metadata = new Properties();
		MessageDigest messageDigest = MessageDigest.getInstance(digestAlgorithm);
		Signature signature = null;
		byte[] digest;

		// If a signer name was specified, then prepare to sign the manifest.
		if (signerName != null) {
			// Get a Signature object:
			signature = Signature.getInstance(signatureAlgorithm);

			// Look up the private key of the signer from the keystore:
			PrivateKey key = (PrivateKey) keystore.getKey(signerName, password.toCharArray());

			// Now prepare to create a signature for the specified signer:
			signature.initSign(key);
		}

		// Now, loop through the files, in a well-known alphabetical order:
		System.out.print("Computing message digests");
		for (int i = 0; i < numberFiles; i++) {
			String filename = filelist.get(i);
			// Compute the digest for each, and skip files that don't exist.
			try {
				digest = getFileDigest(filename, messageDigest);
			} catch (IOException ioException) {
				System.err.println("\nSkipping " + filename + ": " + ioException);
				continue;
			}

			/*
			 * If we're computing a signature, use the bytes of the filename and of the
			 * digest as part of the data to sign.
			 */
			if (signature != null) {
				signature.update(filename.getBytes());
				signature.update(digest);
			}
			// Store the filename and the encoded digest bytes in the manifest.
			manifest.put(filename, hexEncode(digest));
			System.out.print('.');
			System.out.flush();
		}

		// If a signer was specified, compute signature for the manifest.
		byte[] signaturebytes = null;
		if (signature != null) {
			System.out.print("Done\nComputing digital signature...");
			System.out.flush();

			/*
			 * Compute the digital signature by encrypting a message digest of all the bytes
			 * passed to the update() method using the private key of the signer. This is a
			 * time consuming operation.
			 */
			signaturebytes = signature.sign();
		}

		// Tell the user what comes next:
		System.out.print("Done\nWriting manifest to: \"" + manifestFileName + "\" ...");
		System.out.flush();

		/*
		 * Store some metadata about this manifest, including the name of the message
		 * digest algorithm it uses.
		 */
		metadata.put("__META.DIGESTALGORITHM", digestAlgorithm);
		// If we're signing the manifest, store some more metadata.
		if (signerName != null) {
			// Store the name of the signer.
			metadata.put("__META.SIGNER", signerName);
			// Store the name of the algorithm...
			metadata.put("__META.SIGNATUREALGORITHM", signatureAlgorithm);
			// ...and generate the signature, encode it, and store it.
			metadata.put("__META.SIGNATURE", hexEncode(signaturebytes));
		}

		// Now, save the manifest data and the metadata to the manifest file.
		FileOutputStream fileOutputStream = new FileOutputStream(manifestFileName);
		manifest.store(fileOutputStream, "Manifest message digests");
		metadata.store(fileOutputStream, "Manifest metadata");
		System.out.println("Done.");
	}

	/**
	 * This method verifies the digital signature of the named manifest file, if it
	 * has one. If that verification succeeds, it verifies the message digest of
	 * each file in fileList that is also named in the manifest. This method can
	 * throw a bunch of exceptions.
	 */
	public static void verify(String manifestFile, KeyStore keystore)
			throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, KeyStoreException, IOException {
		Properties manifest = new Properties();
		manifest.load(new FileInputStream(manifestFile));
		String digestAlgorithm = manifest.getProperty("__META.DIGESTALGORITHM");
		String signerName = manifest.getProperty("__META.SIGNER");
		String signatureAlgorithm = manifest.getProperty("__META.SIGNATUREALGORITHM");
		String hexSignature = manifest.getProperty("__META.SIGNATURE");

		// Get a list of filenames in the manifest.
		List<String> fileNames = new ArrayList<String>();
		@SuppressWarnings("unchecked")
		Enumeration<String> propertyNames = (Enumeration<String>) manifest.propertyNames();
		while (propertyNames.hasMoreElements()) {
			String nextPropertyName = propertyNames.nextElement();
			if (nextPropertyName.startsWith("__META") == false) {
				fileNames.add(nextPropertyName);
			}
		}
		int numberOfFiles = fileNames.size();

		// If we've got a signature but no keystore, warn the user.
		if (signerName != null && keystore == null) {
			System.out.println("Sorry, can't verify digital signature without a keystore.");
		}
		/*
		 * If the manifest contained metadata about a digital signature, then verify
		 * that signature first.
		 */
		if (signerName != null && keystore != null) {
			System.out.print("Verifying digital signature...");
			System.out.flush();

			/*
			 * To verify the signature, we must process the files in exactly the same order
			 * we did when we created the signature. We guarantee this order by sorting the
			 * filenames.
			 */
			Collections.sort(fileNames);

			/*
			 * Create a Signature object with which to do signature verification. Initialize
			 * it with the signer's public key from the keystore.
			 */
			Signature signature = Signature.getInstance(signatureAlgorithm);
			PublicKey publicKey = keystore.getCertificate(signerName).getPublicKey();
			signature.initVerify(publicKey);

			/*
			 * Now loop through these files in their known sorted order. For each one, send
			 * the bytes of the filename and of the digest to the signature object, for use
			 * in computing the signature. It is important that this be done in exactly the
			 * same order when verifying the signature, as it was done when creating the
			 * signature.
			 */
			for (int i = 0; i < numberOfFiles; i++) {
				String filename = fileNames.get(i);
				signature.update(filename.getBytes());
				signature.update(hexDecode(manifest.getProperty(filename)));
			}

			/*
			 * Now decode the signature read from the manifest file and pass it to the
			 * verify() method of the signature object. If the signature is not verified,
			 * print an error message and exit.
			 */
			if (signature.verify(hexDecode(hexSignature)) == false) {
				System.out.println("\nSorry, the manifest has an invalid signature.");
				System.exit(0);
			}

			// Tell the user we're done with this lengthy computation.
			System.out.println("Verified.");
		}

		// Tell the user we're starting the next phase of verification.
		System.out.println("Verifying file message digests.");
		System.out.flush();

		// Get a MessageDigest object to compute digests.
		MessageDigest messageDigest = MessageDigest.getInstance(digestAlgorithm);
		// Loop through all files.
		for (int i = 0; i < numberOfFiles; i++) {
			String filename = fileNames.get(i);
			// Look up the encoded digest from the manifest file.
			String hexDigest = manifest.getProperty(filename);
			// Compute the digest for the file.
			byte[] digest;
			try {
				digest = getFileDigest(filename, messageDigest);
			} catch (IOException e) {
				System.out.println("\nSkipping " + filename + ": " + e);
				continue;
			}

			/*
			 * Encode the computed digest, and compare it to the encoded digest from the
			 * manifest. If they are not equal, print an error message.
			 */
			if (hexDigest.equals(hexEncode(digest)) == false) {
				System.out.println("\nFile '" + filename + "' failed verification.");
			}

			/*
			 * Send one dot of output for each file we process. Since computing message
			 * digests takes some time, this lets the user know that the program is
			 * functioning and making progress
			 */
			System.out.print(".");
			System.out.flush();
		}
		// ...and, tell the user we're done with verification.
		System.out.println("\nDone.");
	}

	/**
	 * This convenience method is used by both create() and verify(). It reads the
	 * contents of a named file, and computes a message digest for it, using the
	 * specified MessageDigest object.
	 */
	public static byte[] getFileDigest(String filename, MessageDigest messageDigest) throws IOException {
		// Make sure there is nothing left behind in the MessageDigest:
		messageDigest.reset();

		// Create a stream to read from the file and compute the digest.
		DigestInputStream digestInputStream = new DigestInputStream(new FileInputStream(filename), messageDigest);
		/*
		 * Read to the end of the file, discarding everything we read. The
		 * DigestInputStream automatically passes all the bytes read to the update()
		 * method of the MessageDigest.
		 */
		while (digestInputStream.read(buffer) != -1) {
			/* do nothing */ ;
		}
		digestInputStream.close();

		// Finally, compute and return the digest value.
		return messageDigest.digest();
	}

	/** This static buffer is used by getFileDigest() above */
	public static byte[] buffer = new byte[4096];

	/** This array is used to convert from bytes to hexadecimal numbers */
	static final char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * A convenience method to convert an array of bytes to a String. We do this
	 * simply by converting each byte to two hexadecimal digits. Something like Base
	 * 64 encoding is more compact, but harder to encode.
	 */
	public static String hexEncode(byte[] bytes) {
		StringBuffer stringBuffer = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			byte currentByte = bytes[i];
			stringBuffer.append(digits[(currentByte & 0xf0) >> 4]);
			stringBuffer.append(digits[currentByte & 0x0f]);
		}
		return stringBuffer.toString();
	}

	/**
	 * A convenience method to convert in the other direction, from a string of
	 * hexadecimal digits to an array of bytes.
	 */
	public static byte[] hexDecode(String hexString) throws IllegalArgumentException {
		try {
			int length = hexString.length();
			byte[] byteArray = new byte[length / 2];
			for (int i = 0; i < byteArray.length; i++) {

				// Digit 1
				int digit1 = hexString.charAt(i * 2);
				if ((digit1 >= '0') && (digit1 <= '9')) {
					digit1 -= '0';
				} else if ((digit1 >= 'a') && (digit1 <= 'f')) {
					digit1 -= 'a' - 10;
				}
				// Digit 2
				int digit2 = hexString.charAt(i * 2 + 1);
				if ((digit2 >= '0') && (digit2 <= '9')) {
					digit2 -= '0';
				} else if ((digit2 >= 'a') && (digit2 <= 'f')) {
					digit2 -= 'a' - 10;
				}
				byteArray[i] = (byte) ((digit1 << 4) + digit2);
			}
			return byteArray;
		} catch (Exception e) {
			throw new IllegalArgumentException("hexDecode(): invalid input");
		}
	}
}
