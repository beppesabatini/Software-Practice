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
 * From Java Examples in a Nutshell, 3rd Edition, pp. 22-23. This program
 * computes prime numbers using the Sieve of Eratosthenes algorithm: rule out
 * multiples of all lower prime numbers, and anything remaining is a prime. It
 * prints out the largest prime number less than or equal to the supplied
 * command-line argument.
 */
public class Sieve {
	public static void main(String[] args) {
		/*
		 * We will compute all primes less than the value specified on the command line,
		 * or, if no argument was provided, compute all primes less than 100 million.
		 */
		// Assign a default value:
		int max = 1000000000;
		// Parse user-supplied arg:
		try {
			max = Integer.parseInt(args[0]);
		} catch (Exception e) {
			// Silently ignore exceptions.
		}

		// Create an array that specifies whether each number is prime or not.
		boolean[] isPrime = new boolean[max + 1];

		// Assume that all numbers are primes, until proven otherwise.
		for (int i = 0; i <= max; i++) {
			isPrime[i] = true;
		}

		// However, we know that 0 and 1 are not primes. Make a note of it.
		isPrime[0] = isPrime[1] = false;

		/*
		 * To compute all primes less than max, we need to rule out multiples of all
		 * integers less than the square root of max.
		 */
		int n = (int) Math.ceil(Math.sqrt(max)); // See java.lang.Math class

		/*
		 * Now, for each integer i from 0 to n: <p> If i is a prime, then none of its
		 * multiples are primes, so indicate this in the array. If i is not a prime,
		 * then its multiples have already been ruled out by one of the prime factors of
		 * i, so we can skip this case.
		 */
		for (int i = 0; i <= n; i++) {
			// If i is a prime,
			if (isPrime[i]) {
				// Loop through multiples:
				for (int j = 2 * i; j <= max; j = j + i) {
					// Any multiples can't be a prime:
					isPrime[j] = false;
				}
			}
		}

		// Now go look for the largest prime:
		int largest;
		for (largest = max; isPrime[largest] == false; largest--) {
			// Empty loop body:
			;
		}

		// Output the result:
		System.out.println("The largest prime less than or equal to " + max + " is " + largest);
	}
}
