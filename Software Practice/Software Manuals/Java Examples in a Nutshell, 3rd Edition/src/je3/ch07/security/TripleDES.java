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

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 203-206. This class
 * defines methods for encrypting and decrypting using the Triple DES algorithm
 * (Data Encryption Standard) and for generating, reading and writing Triple DES
 * keys. It also defines a main() method that allows these methods to be used
 * from the command line.
 * <p>
 * To test this file, first launch the Run Configuration "TripleDES". That will
 * prompt you with questions and generate a TripleDESKey encryption key for you.
 * You'll use this like the seed for a hash function. Then, navigate to the root
 * of this project, and enter on the command line something like this:
 * 
 * <pre>
 * java je3.ch07.security.TripleDES -encrypt output/TripleDESKey.des < input/testFile.txt > output/encrypted.txt
 * java je3.ch07.security.TripleDES -decrypt output/TripleDESKey.des < output/encrypted.txt > output/decrypted.txt
 * </pre>
 * 
 * The original file input/testFile.txt should match the final
 * output/decrypted.txt.
 */
public class TripleDES {

	// DESede means Data Encryption Standard encryption/decryption.
	// This is also called Triple DES.
	private static final String DATA_ENCRYPTION_STANDARD = "DESede";

	/**
	 * The program. The first argument must be -e, -d, or -g to encrypt, decrypt, or
	 * generate a key. The second argument is the name of a file from which the key
	 * is read or to which it is written for -g. The -e and -d arguments cause the
	 * program to read from standard input, and encrypt or decrypt to standard
	 * output.
	 */
	public static void main(String[] args) {
		try {
			/*
			 * Check to see whether there is a provider that can do TripleDES encryption. If
			 * not, explicitly install the SunJCE provider (Sun Java Cryptography
			 * Extension).
			 */
			Cipher cipher = null;
			try {
				cipher = Cipher.getInstance(DATA_ENCRYPTION_STANDARD);
			} catch (Exception e) {
				/*
				 * An exception here probably means the JCE provider (Java Cryptography
				 * Extension) hasn't been permanently installed on this system, which is done by
				 * listing it in the $JAVA_HOME/jre/lib/security/java.security file. Therefore,
				 * we have to install the JCE provider explicitly.
				 */
				System.err.println("Installing SunJCE provider.");
				// Uncomment out the next two lines if needed.
				// Provider sunJce = new com.sun.crypto.provider.SunJCE();
				// Security.addProvider(sunJce );
			} finally {
				if (cipher != null) {
					cipher = null;
				}
			}

			// This is from where we'll read the key, or to where we'll write it:
			File keyfile = new File(args[1]);

			// Now check the first argument, to see what we're going to do.
			if (args[0].equals("-g")) { // Generate a key
				System.out.print("Generating key. This may take some time...");
				System.out.flush();
				SecretKey key = generateKey();
				writeKey(key, keyfile);
				System.out.println("Done.");
				System.out.println("Secret key written to \"" + args[1] + "\". Protect that file carefully!");
			} else if (args[0].equals("-e") || args[0].equals("-encrypt")) {
				// Encrypt stdin to stdout.
				SecretKey key = readKey(keyfile);
				encrypt(key, System.in, System.out);
			} else if (args[0].equals("-d") || args[0].equals("-decrypt")) {
				// Decrypt stdin to stdout.
				SecretKey key = readKey(keyfile);
				decrypt(key, System.in, System.out);
			}
		} catch (Exception exception) {
			System.err.println(exception);
			System.err.println("Usage: java " + TripleDES.class.getName() + " -d|-e|-g <keyfile>");
		}
	}

	/** Generate a secret TripleDES encryption/decryption key. */
	public static SecretKey generateKey() throws NoSuchAlgorithmException {
		// Get a key generator for Triple DES (also referred to as DESede).
		KeyGenerator keygen = KeyGenerator.getInstance(DATA_ENCRYPTION_STANDARD);
		// Use it to generate a key.
		return keygen.generateKey();
	}

	/** Save the specified TripleDES SecretKey to the specified file. */
	public static void writeKey(SecretKey key, File outputKeyFile)
			throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		// Convert the secret key to an array of bytes like this.
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(DATA_ENCRYPTION_STANDARD);
		DESedeKeySpec keyspec = (DESedeKeySpec) keyfactory.getKeySpec(key, DESedeKeySpec.class);
		byte[] rawKey = keyspec.getKey();

		// Write the raw key to the file:
		FileOutputStream out = new FileOutputStream(outputKeyFile);
		out.write(rawKey);
		out.close();
	}

	/** Read a TripleDES secret key from the specified file */
	public static SecretKey readKey(File inputKeyFile)
			throws IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
		// Read the raw bytes from the keyfile.
		DataInputStream in = new DataInputStream(new FileInputStream(inputKeyFile));
		byte[] rawKey = new byte[(int) inputKeyFile.length()];
		in.readFully(rawKey);
		in.close();

		// Convert the raw bytes to a secret key like this:
		DESedeKeySpec keyspec = new DESedeKeySpec(rawKey);
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(DATA_ENCRYPTION_STANDARD);
		SecretKey key = keyfactory.generateSecret(keyspec);
		return key;
	}

	/**
	 * Use the specified TripleDES key to encrypt bytes from the input stream and
	 * write them to the output stream. This method uses CipherOutputStream to
	 * perform the encryption and write bytes at the same time.
	 **/
	public static void encrypt(SecretKey key, InputStream in, OutputStream out)
			throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IOException {
		// Create and initialize the encryption engine.
		Cipher cipher = Cipher.getInstance(DATA_ENCRYPTION_STANDARD);
		cipher.init(Cipher.ENCRYPT_MODE, key);

		// Create a special output stream to do the work for us.
		CipherOutputStream cipherOutputStream = new CipherOutputStream(out, cipher);

		// Read from the input and write to the encrypting output stream.
		byte[] buffer = new byte[2048];
		int bytesRead;
		while ((bytesRead = in.read(buffer)) != -1) {
			cipherOutputStream.write(buffer, 0, bytesRead);
		}
		cipherOutputStream.close();

		// For extra security, don't leave any plain text hanging around memory.
		java.util.Arrays.fill(buffer, (byte) 0);
	}

	/**
	 * Use the specified TripleDES key to decrypt bytes ready from the input stream
	 * and write them to the output stream. This method uses uses Cipher directly,
	 * to show how it can be done without CipherInputStream and CipherOutputStream.
	 **/
	public static void decrypt(SecretKey key, InputStream in, OutputStream out) throws NoSuchAlgorithmException,
			InvalidKeyException, IOException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException {
		// Create and initialize the decryption engine.
		Cipher cipher = Cipher.getInstance(DATA_ENCRYPTION_STANDARD);
		cipher.init(Cipher.DECRYPT_MODE, key);

		// Read bytes, decrypt, and write them out.
		byte[] buffer = new byte[2048];
		int bytesRead;
		while ((bytesRead = in.read(buffer)) != -1) {
			out.write(cipher.update(buffer, 0, bytesRead));
		}

		// Write out the final bunch of decrypted bytes
		out.write(cipher.doFinal());
		out.flush();
	}
}
