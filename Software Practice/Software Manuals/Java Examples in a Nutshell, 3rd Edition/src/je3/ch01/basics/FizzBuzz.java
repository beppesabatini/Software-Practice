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
 * From Java Examples in a Nutshell, 3rd Edition, pp. 7-8. This program plays
 * the game "Fizzbuzz". It counts to 100, replacing each multiple of 5 with the
 * word "fizz", each multiple of 7 with the word "buzz", and each multiple of
 * both with the word "fizzbuzz". It uses the modulo operator (%) to determine
 * if a number is divisible by another.
 */
//Everything in Java is a class
public class FizzBuzz {
	// Every program must have main()
	public static void main(String[] args) {
		// count from 1 to 100
		for (int i = 1; i <= 100; i++) {
			// Is it a multiple of 5 & 7?
			if (((i % 5) == 0) && ((i % 7) == 0)) {
				System.out.print("fizzbuzz");
			} else
			// Is it a multiple of 5?
			if ((i % 5) == 0) {
				System.out.print("fizz");
			} else
			// Is it a multiple of 7?
			if ((i % 7) == 0) {
				System.out.print("buzz");
			} else {
				// Not a multiple of 5 or 7
				System.out.print(i);
			}
			System.out.print(" ");
		}
		System.out.println();
	}
}
