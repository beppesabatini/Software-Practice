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
 * From Java Examples in a Nutshell, 3rd Edition, pp. 19. This program displays
 * factorials as the user enters values interactively.
 */
public class FactQuoter {
	public static void main(String[] args) throws IOException {
		// This is how we set things up to read lines of text from the user.
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		// Loop forever:
		for (;;) {
			// Display a prompt to the user:
			System.out.print("FactQuoter> ");
			// Read a line from the user:
			String line = in.readLine();
			// If we reach the end-of-file,
			// or if the user types "quit", then quit.
			if ((line == null) || line.equals("quit")) {
				break;
			}
			// Try to parse the line, and compute and print the factorial.
			try {
				int x = Integer.parseInt(line);
				System.out.println(x + "! = " + Factorial4.factorial(x));
			}
			// If anything goes wrong, display a generic error message.
			catch (Exception e) {
				System.out.println("Invalid Input");
			}
		}
	}
}
