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
 * From Java Examples in a Nutshell, 3rd Edition, p. 10. This program prints out
 * the first 20 numbers in the Fibonacci sequence. Each term is formed by adding
 * together the previous two terms in the sequence, starting with the terms 1
 * and 1.
 */
public class Fibonacci {
	public static void main(String[] args) {
		// Initialize variables:
		int n0 = 1, n1 = 1, n2;
		// Print the first and second terms of the series.
		System.out.print(n0 + " " + n1 + " ");

		// Loop for the next 18 terms.
		for (int i = 0; i < 18; i++) {
			// The next term is the sum of the previous two:
			n2 = n1 + n0;
			// Print it out
			System.out.print(n2 + " ");
			// The first number previous becomes the second previous:
			n0 = n1;
			// ...and, the current number becomes the previous.
			n1 = n2;
		}
		// Terminate the line:
		System.out.println(); 
	}
}
