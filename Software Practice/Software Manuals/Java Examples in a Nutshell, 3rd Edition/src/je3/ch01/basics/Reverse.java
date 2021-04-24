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
 * From Java Examples in a Nutshell, 3rd Edition, p. 11. This program echos the
 * command-line arguments backwards.
 */
public class Reverse {
	public static void main(String[] args) {
		// Loop backwards through the array of arguments:
		for (int i = args.length - 1; i >= 0; i--) {
			// Loop backwards through the characters in each argument:
			for (int j = args[i].length() - 1; j >= 0; j--) {
				// Print out character j of argument i:
				System.out.print(args[i].charAt(j));
			}
			// Add a space at the end of each argument.
			System.out.print(" ");
		}
		// And terminate the line when we're done.
		System.out.println();
	}
}
