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
 * From Java Examples in a Nutshell, 3rd Edition, pp. 15-16. This class computes
 * factorials and caches the results in a table for reuse. The number 20! is as
 * high as we can go using the long data type, so check the argument passed and
 * "throw an exception" if it is too big or too small.
 **/
public class Factorial3 {
	// Create an array to cache values 0! through 20!.
	static long[] table = new long[21];
	// A "static initializer": initialize the first value in the array.
	static {
		// The factorial of 0 is 1:
		table[0] = 1;
	}
	// Remember the highest initialized value in the array:
	static int last = 0;

	public static long factorial(int x) throws IllegalArgumentException {
		// Check if x is too big or too small. Throw an exception if so.
		if (x >= table.length) { // ".length" returns length of any array
			throw new IllegalArgumentException("Overflow; x is too large.");
		}
		if (x < 0) {
			throw new IllegalArgumentException("x must be non-negative.");
		}

		// Compute and cache any values that are not yet cached.
		while (last < x) {
			table[last + 1] = table[last] * (last + 1);
			last++;
		}
		// Now return the cached factorial of x.
		return (table[x]);
	}
}
