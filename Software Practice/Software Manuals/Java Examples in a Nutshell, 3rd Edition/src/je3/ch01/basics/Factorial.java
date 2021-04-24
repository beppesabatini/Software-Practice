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
 * From Java Examples in a Nutshell, 3rd Edition, pp. 14. This class doesn't
 * define a main() method, so it isn't a program by itself. It does define a
 * useful method that we can use in other programs, though.
 */
public class Factorial {
	/** Compute and return x!, the factorial of x */
	public static int factorial(int x) {
		if (x < 0) {
			throw new IllegalArgumentException("x must be >= 0");
		}
		int factorial = 1;
		// Loop:
		for (int i = 2; i <= x; i++) {
			// Shorthand for: factorial = factorial * i
			factorial *= i;
		}
		return (factorial);
	}
}
