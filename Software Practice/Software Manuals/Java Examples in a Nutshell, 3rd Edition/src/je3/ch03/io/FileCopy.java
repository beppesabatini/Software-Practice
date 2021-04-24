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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 64-66. This class is a
 * standalone program to copy a file, and also defines a static copy() method
 * that other programs can use to copy files.
 */
public class FileCopy {
	/** The main() method of the standalone program. This calls copy(). */
	public static void main(String[] args) {
		// Check arguments:
		if (args.length != 2) {
			System.err.println("Usage: java FileCopy <source> <destination>");
		} else {
			// Call copy() to do the copy; display any error messages.
			try {
				copy(args[0], args[1]);
			} catch (IOException ioException) {
				System.err.println(ioException.getMessage());
			}
		}
	}

	/**
	 * The static method that actually performs the file copy. Before copying the
	 * file, however, it performs a lot of tests to make sure everything is as it
	 * should be.
	 */
	public static void copy(String fromName, String toName) throws IOException {
		// Get File objects from the String arguments:
		File fromFile = new File(fromName);
		File toFile = new File(toName);

		/*
		 * First make sure the source file exists, that it is a file, and is readable.
		 * These tests are also performed by the FileInputStream constructor, which
		 * throws a FileNotFoundException if they fail.
		 */
		if (fromFile.exists() == false) {
			abort("No such source file: " + fromName);
		}
		if (fromFile.isFile() == false) {
			abort("can't copy directory: " + fromName);
		}
		if (fromFile.canRead() == false) {
			abort("source file is unreadable: " + fromName);
		}

		/*
		 * If the destination is a directory, use the source file name as the
		 * destination file name.
		 */
		if (toFile.isDirectory()) {
			// The new File object specifies both the parent directory and the child target.
			toFile = new File(toFile, fromFile.getName());
		}

		/*
		 * If the destination exists, make sure it is a writable file, and ask before
		 * overwriting it. If the destination doesn't exist, make sure the directory
		 * exists and is writable.
		 */
		if (toFile.exists()) {
			if (toFile.canWrite() == false) {
				abort("The destination file is unwriteable: " + toName);
			}
			// Ask whether to overwrite it.
			System.out.print("Overwrite existing file " + toFile.getName() + "? (Y/N): ");
			System.out.flush();
			// Get the user's response.
			BufferedReader bufferedInputStreamReader = new BufferedReader(new InputStreamReader(System.in));
			String response = bufferedInputStreamReader.readLine();
			// Check the response. If not a Yes, abort the copy.
			if (response.equals("Y") == false && response.equals("y") == false) {
				abort("The existing file was not overwritten.");
			}
		} else {
			/*
			 * If file doesn't exist, check if a directory contains it, and is writable. If
			 * getParent() returns null, then the directory is the current directory, so
			 * look up the user.dir system property to find out what that current directory
			 * is.
			 */
			// The destination directory:
			String parentName = toFile.getParent();
			// If none, use the current directory:
			if (parentName == null) {
				parentName = System.getProperty("user.dir");
			}
			// Convert the property to a file object.
			File directory = new File(parentName);
			if (directory.exists() == false) {
				abort("The destination directory doesn't exist: " + parentName);
			}
			if (directory.isFile()) {
				abort("The destination is not a directory: " + parentName);
			}
			if (directory.canWrite() == false) {
				abort("The destination directory is unwriteable: " + parentName);
			}
		}
		/*
		 * If we've gotten this far, then everything is okay. So we copy the file, a
		 * buffer of bytes at a time.
		 */
		// Stream of bytes which will be read from a File:
		FileInputStream fromFileInputStream = null;
		// Stream of bytes which will be written to a File:
		FileOutputStream toFileOutputStream = null;
		try {
			// Create input stream:
			fromFileInputStream = new FileInputStream(fromFile);
			// Create output stream:
			toFileOutputStream = new FileOutputStream(toFile);
			// Define a buffer to hold file contents:
			byte[] buffer = new byte[4096];
			// How many bytes are in buffer at any time:
			int bytesRead;

			/*
			 * Read a chunk of bytes into the buffer, then write them out, looping until we
			 * reach the end of the file (which is when read() returns -1). Note the
			 * combination of assignment and comparison in this while loop. This is a common
			 * I/O programming idiom.
			 * 
			 */
			// Read until EOF (end of file):
			while ((bytesRead = fromFileInputStream.read(buffer)) != -1) {
				toFileOutputStream.write(buffer, 0, bytesRead);
			}
		}
		// Always close the streams, even if exceptions were thrown.
		finally {
			if (fromFileInputStream != null) {
				try {
					fromFileInputStream.close();
				} catch (IOException ioException) {
					// Ignore
				}
			}
			if (toFileOutputStream != null) {
				try {
					toFileOutputStream.close();
				} catch (IOException ioException) {
					// Ignore
				}
			}
		}
	}

	/** A convenience method to throw an exception */
	private static void abort(String message) throws IOException {
		throw new IOException("FileCopy: " + message);
	}
}
