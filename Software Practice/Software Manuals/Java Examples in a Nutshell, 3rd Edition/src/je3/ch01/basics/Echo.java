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

/**
 * From Java Examples in a Nutshell, 3rd Edition, p. 11. This program prints out
 * all its command-line arguments.
 */
public class Echo {
	public static void main(String[] args) {
		// Initialize the loop variable:
		int i = 0;
		// Loop until the end of array:
		while (i < args.length) {
			// Print each argument out:
			System.out.print(args[i] + " ");
			// Increment the loop variable:
			i++;
		}
		// Terminate the line:
		System.out.println();
	}
}
