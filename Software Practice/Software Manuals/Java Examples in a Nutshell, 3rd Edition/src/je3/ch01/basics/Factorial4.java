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

// Import some classes we'll use in this example.
// Once we import a class, we don't have to type its full name.

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 17. This version of the
 * program uses arbitrary precision integers, so it does not have an upper-bound
 * on the values it can compute. It uses an ArrayList object to cache computed
 * values instead of a fixed-size array. An ArrayList is like an array, but can
 * grow to any size. The factorial() method is declared "synchronized" so that
 * it can be safely used in multi-threaded programs. Look up
 * java.math.BigInteger and java.util.ArrayList while studying this class. Prior
 * to Java 1.2, use Vector instead of ArrayList.
 */
public class Factorial4 {
	// Create the cache:
	protected static List<BigInteger> table = new ArrayList<BigInteger>(); 
	static { 
		// Initialize the first element of the cache with !0 = 1.
		table.add(BigInteger.valueOf(1));
	}

	/** The factorial() method, using BigIntegers cached in a ArrayList */
	public static synchronized BigInteger factorial(int x) {
		if (x < 0) {
			throw new IllegalArgumentException("x must be non-negative.");
		}
		for (int size = table.size(); size <= x; size++) {
			BigInteger lastfact = (BigInteger) table.get(size - 1);
			BigInteger nextfact = lastfact.multiply(BigInteger.valueOf(size));
			table.add(nextfact);
		}
		return (BigInteger) table.get(x);
	}

	/**
	 * A simple main() method that we can use as a standalone test program for our
	 * factorial() method.
	 **/
	public static void main(String[] args) {
		for (int i = 0; i <= 50; i++) {
			System.out.println(i + "! = " + factorial(i));
		}
	}
}