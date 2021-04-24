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
package je3.ch03.io;

import java.io.File;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 62-63. This class provides
 * a static method delete(), and a standalone program, that deletes a specified
 * file or directory.
 */
public class Delete {
	/**
	 * This is the main() method of the standalone program. After checking its
	 * arguments, it invokes the Delete.delete() method to do the deletion.
	 */
	public static void main(String[] args) {
		// Check command-line arguments:
		if (args.length != 1) {
			System.err.println("Usage: java Delete <file or directory>");
			System.exit(0);
		}
		// Call delete() and display any error messages it throws.
		try {
			delete(args[0]);
		} catch (IllegalArgumentException illegalArgumentException) {
			System.err.println(illegalArgumentException.getMessage());
		}
	}

	/**
	 * The static method that does the deletion. Invoked by main(), and designed for
	 * use by other programs as well. It first makes sure that the specified file or
	 * directory is deletable before attempting to delete it. If there is a problem,
	 * it throws an IllegalArgumentException.
	 */
	public static void delete(String filename) {
		// Create a File object to represent the filename.
		File targetFile = new File(filename);

		// Make sure the file or directory exists and isn't write protected.
		if (targetFile.exists() == false) {
			fail("Delete: no such file or directory: " + filename);
		}
		if (targetFile.canWrite() == false) {
			fail("Delete: write protected: " + filename);
		}
		// If it is a directory, make sure it is empty.
		if (targetFile.isDirectory()) {
			String[] files = targetFile.list();
			if (files.length > 0) {
				fail("Delete: directory not empty: " + filename);
			}
		}

		// If we passed all the tests, then attempt to delete it.
		boolean success = targetFile.delete();

		/*
		 * ...and, throw an exception if it didn't work for some (unknown) reason. For
		 * example, because of a bug with Java 1.1.1 on Linux, directory deletion always
		 * fails.
		 */
		if (success == false) {
			fail("Delete: deletion failed");
		}
	}

	/** A convenience method to throw an exception. */
	protected static void fail(String message) throws IllegalArgumentException {
		throw new IllegalArgumentException(message);
	}
}
