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
 * From Java Examples in a Nutshell, 3rd Edition, pp. 18. This program computes
 * and displays the factorial of a number specified on the command line. It
 * handles possible user input errors with try/catch.
 **/
public class FactComputer {
	public static void main(String[] args) {
		/*
		 * Try to compute a factorial. If something goes wrong, handle it in the catch
		 * clause below.
		 */
		try {
			int nInSeries = Integer.parseInt(args[0]);
			System.out.println(nInSeries + "! = " + Factorial4.factorial(nInSeries));
		}
		/*
		 * The user forgot to specify an argument. Thrown if args[0] is undefined.
		 */
		catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Sorry, you must specify an argument");
			System.out.println("Usage: java FactComputer <number>");
		}
		// The argument is not a number. This is thrown by Integer.parseInt().
		catch (NumberFormatException e) {
			System.out.println("Sorry, the argument you specify must be an integer");
		}
		// The argument is < 0. Thrown by Factorial4.factorial().
		catch (IllegalArgumentException e) {
			// Display the message sent by the factorial() method:
			System.out.println("Sorry, bad argument: " + e.getMessage());
		}
	}
}
