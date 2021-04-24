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
package je3.ch01.basics;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 20-21. This program reads
 * lines of text from the user, encodes them using the trivial "Rot13"
 * substitution cipher, and then prints out the encoded lines.
 */
public class Rot13Input {
	public static void main(String[] args) throws IOException {
		// Get set up to read lines of text from the user.
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		// Loop forever:
		for (;;) {
			// Print a prompt:
			System.out.print("> ");
			// Read a line:
			String line = in.readLine();
			// If EOF or "quit"...
			if ((line == null) || line.equals("quit")) {
				// ...break out of the loop.
				break;
			}
			// Use a StringBuffer: 
			StringBuffer buf = new StringBuffer(line);
			// For each character...
			for (int i = 0; i < buf.length(); i++) {
				// ..read, encode, and store.
				buf.setCharAt(i, rot13(buf.charAt(i)));
			}
			// Print encoded line:
			System.out.println(buf);
		}
	}

	/**
	 * This method performs the Rot13 substitution cipher. It "rotates" each letter
	 * 13 places through the alphabet. Since the Latin alphabet has 26 letters, this
	 * method both encodes and decodes.
	 */
	public static char rot13(char c) {
		// For uppercase letters:
		if ((c >= 'A') && (c <= 'Z')) {
			// Rotate forward 13:
			c += 13;
			if (c > 'Z') {
				// ...and subtract 26 if necessary:
				c -= 26;
			}
		}
		// Do the same for lowercase letters.
		if ((c >= 'a') && (c <= 'z')) {
			c += 13;
			if (c > 'z') {
				c -= 26;
			}
		}
		// Return the modified letter.
		return (c);
	}
}
