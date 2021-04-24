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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 75-77. This class defines
 * two static methods for gzipping files and zipping directories. It also
 * provides a demonstration program as a nested class.
 */
public class Compress {
	/** Gzip the contents of the from file and save in the target file. */
	public static void gzipFile(String fromFile, String toFile) throws IOException {
		// Create stream of bytes to read from the source file.
		FileInputStream fileInputStream = new FileInputStream(fromFile);
		// Create stream to compress data and write it to the target file.
		GZIPOutputStream gzipOutputStream = new GZIPOutputStream(new FileOutputStream(toFile));
		// Copy bytes from one stream to the other.
		byte[] buffer = new byte[4096];
		int bytesRead;
		while ((bytesRead = fileInputStream.read(buffer)) != -1) {
			gzipOutputStream.write(buffer, 0, bytesRead);
		}
		// ...and, close the streams:
		fileInputStream.close();
		gzipOutputStream.close();
	}

	/** Zip the contents of the directory, and save it in the zip file. */
	public static void zipDirectory(String directory, String zipFile) throws IOException, IllegalArgumentException {
		// Check that the directory is a directory, and get its contents.
		File sourceDirectory = new File(directory);
		if (!sourceDirectory.isDirectory()) {
			throw new IllegalArgumentException("Compress: not a directory:  " + directory);
		}
		String[] entries = sourceDirectory.list();
		// Create a buffer for copying.
		byte[] buffer = new byte[4096];
		int bytesRead;

		// Create a stream to compress data and write it to the zip file.
		ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFile));

		// Loop through all entries in the directory.
		for (int i = 0; i < entries.length; i++) {
			File sourceFile = new File(sourceDirectory, entries[i]);
			// Don't zip sub-directories:
			if (sourceFile.isDirectory()) {
				continue;
			}
			// Stream to read bytes from a file:
			FileInputStream fileInputStream = new FileInputStream(sourceFile);
			// Make a ZipEntry:
			ZipEntry zipEntry = new ZipEntry(sourceFile.getPath());
			// Store entry:
			zipOutputStream.putNextEntry(zipEntry);
			// Copy bytes:
			while ((bytesRead = fileInputStream.read(buffer)) != -1) {
				zipOutputStream.write(buffer, 0, bytesRead);
			}
			// Close input stream
			fileInputStream.close();
		}
		// When we're done with the whole loop, close the output stream.
		zipOutputStream.close();
	}

	/**
	 * This nested class is a test program that demonstrates the use of the static
	 * methods defined above.
	 */
	public static class Test {
		/**
		 * Compress a specified file or directory. If no destination name is specified,
		 * append .gz to a file name or .zip to a directory name
		 **/
		public static void main(String args[]) throws IOException {
			// Check arguments:
			if ((args.length != 1) && (args.length != 2)) {
				System.err.println("Usage: java Compress$Test <from> [<to>]");
				System.exit(0);
			}
			String fromFile = args[0];
			fromFile = fromFile.replace("%20", " ");
			String toFile;
			File sourceFile = new File(fromFile);
			// Is it a file or a directory?
			boolean isDirectory = sourceFile.isDirectory();
			if (args.length == 2) {
				toFile = args[1];
			}
			// If the destination was not specified:
			else {
				// Use a .zip suffix:
				if (isDirectory == true) {
					toFile = fromFile + ".zip";
				}
				// ...or, a .gz suffix:
				else {
					toFile = fromFile + ".gz";
				}
			}

			// Make sure not to overwrite:
			if ((new File(toFile)).exists()) {
				System.err.println("Compress: won't overwrite existing file: " + toFile);
				System.exit(0);
			}

			// Finally, call one of the methods defined above to do the work.
			if (isDirectory == true) {
				Compress.zipDirectory(fromFile, toFile);
			} else {
				Compress.gzipFile(fromFile, toFile);
			}
		}
	}
}
