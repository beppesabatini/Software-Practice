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
import java.io.FileReader;
import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 78-79. A simple
 * FilterReader that strips HTML tags (or anything between pairs of angle
 * brackets) out of a stream of characters. RemoveHtmlReader is a subclass of
 * the abstract FilterReader.
 */
public class RemoveHTMLReader extends FilterReader {
	/** A trivial constructor. Just initialize our superclass. */
	public RemoveHTMLReader(Reader reader) {
		super(reader);
	}

	/**
	 * This is the implementation of the no-op [read-only] read() method of
	 * FilterReader. It calls in.read() to get a buffer full of characters, then
	 * strips out the HTML tags. (The "in" value is a protected field of the
	 * superclass).
	 */
	@Override
	public int read(char[] buffer, int startingPoint, int length) throws IOException {
		// How many characters have been read:
		int numberCharactersRead = 0;
		/*
		 * Loop, because we might read a bunch of characters, then strip them all out,
		 * leaving us with zero characters to return.
		 */
		while (numberCharactersRead == 0) {
			// Read characters
			numberCharactersRead = in.read(buffer, startingPoint, length);
			// Check for EOF and handle it.
			if (numberCharactersRead == -1) {
				return -1;
			}

			/*
			 * Loop through the characters we read, stripping out HTML tags. Characters not
			 * in tags are copied over previous tags
			 */
			// Index of last non-HTML char:
			int last = startingPoint;
			// Used to remember whether we are "inside" a tag:
			boolean inTag = false;
			for (int i = startingPoint; i < startingPoint + numberCharactersRead; i++) {
				// If not in an HTML tag
				if (inTag == false) {
					// Check for tag start:
					if (buffer[i] == '<') {
						inTag = true;
					}
					// ...and copy the character:
					else {
						buffer[last++] = buffer[i];
					}
				}
				// check for end of tag
				else if (buffer[i] == '>') {
					inTag = false;
				}
			}
			/*
			 * Figure out how many characters remain. If it is more than zero characters the
			 * while loop will finish. Then return that number of characters.
			 */
			numberCharactersRead = last - startingPoint;
		}
		return numberCharactersRead;
	}

	/**
	 * This is another no-op [read-only] read() method we have to implement. We
	 * implement it in terms of the method above. Our superclass implements the
	 * remaining read() methods in terms of these two.
	 **/
	@Override
	public int read() throws IOException {
		char[] buffer = new char[1];
		int result = read(buffer, 0, 1);
		if (result == -1) {
			return -1;
		} else {
			return (int) buffer[0];
		}
	}

	/** This class defines a main() method to test the RemoveHTMLReader. */
	public static class Test {
		/** The test program: read a text file, strip the HTML, print it to console. */
		public static void main(String[] args) {
			try {
				if (args.length != 1) {
					throw new IllegalArgumentException("Wrong number of args");
				}
				// The FileReader is the basic Reader that returns a character stream.
				FileReader fileReader = new FileReader(args[0]);

				/*
				 * Create a stream to read lines of character strings from the file, and strip
				 * the tags from it.
				 */
				BufferedReader filteringFileReader = new BufferedReader(new RemoveHTMLReader(fileReader));
				// Read line by line, printing lines to the console:
				String line;
				while ((line = filteringFileReader.readLine()) != null) {
					System.out.println(line);
				}
				// Close the stream.
				filteringFileReader.close();
			} catch (Exception exception) {
				System.err.println(exception);
				System.err.println("Usage: java RemoveHTMLReader$Test" + " <filename>");
			}
		}
	}
}
