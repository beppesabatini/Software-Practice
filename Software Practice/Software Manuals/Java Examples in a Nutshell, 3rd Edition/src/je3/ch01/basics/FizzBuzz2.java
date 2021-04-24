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
 * From Java Examples in a Nutshell, 3rd Edition, pp. xx-xx. This class is much like the FizzBuzz class, but uses a switch statement
 * instead of repeated if/else statements.
 */
public class FizzBuzz2 {
	public static void main(String[] args) {
		// count from 1 to 100
		for (int i = 1; i <= 100; i++) {
			// What's the remainder when divided by 35?
			switch (i % 35) {
				// For multiples of 35...
				case 0: {
					System.out.print("fizzbuzz ");
					break; // Don't forget this statement!
				}
				// If the remainder is any of these,
				// then the number is a multiple of 5.
				case 5:
				case 10:
				case 15:
				case 20:
				case 25:
				case 30: {
					System.out.print("fizz ");
					break;
				}
				// For any multiple of 7...
				case 7:
				case 14:
				case 21:
				case 28: {
					System.out.print("buzz ");
					break;
				}
				// For any other number...
				default: {
					System.out.print(i + " ");
					break;
				}
			}
		}
		System.out.println();
	}
}
