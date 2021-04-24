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

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 82-83. This class
 * represents a list of strings saved persistently to a file, along with an
 * index that allows random access to any string in the list. The static method
 * writeWords() creates such an indexed list in a file.
 * <p/>
 * The WordList class demonstrates the use of java.io.RandomAccessFile. Not a
 * stream at all, RandomAccessFile allows users to read and write arbitrary
 * bytes, text, and primitive data types from or to any specified location in a
 * file. RandomAccessFile is part of java.io, but it is compatible with
 * java.nio.
 */
public class WordList {
	// This is a simple test method.
	public static void main(String args[]) throws IOException {

		String outputFile = "output/words.data";
		// Write command line arguments to a WordList file named "words.data".
		writeWords(outputFile, args);

		// Now create a WordList based on that file:
		WordList list = new WordList(outputFile);
		// ...and iterate through the elements of the list backward.
		// This would be very inefficient to do with sequential-access streams.
		for (int i = list.size() - 1; i >= 0; i--) {
			System.out.println("Word position: " + i + " || Word: " + list.get(i));
		}
		// Tell the list we're done with it.
		list.close();
	}

	// This static method creates a WordList file.
	public static void writeWords(String filename, String[] words) throws IOException {
		/*
		 * Open the file for read/write access ("rw"). We only need to write, but have
		 * to request read access as well
		 */
		RandomAccessFile randomAccessFile = new RandomAccessFile(filename, "rw");

		// This array will hold the positions of each word in the file
		long wordPositions[] = new long[words.length];

		/*
		 * Reserve space at the start of the file for the wordPositions array and the
		 * length of that array. 4 bytes for length plus 8 bytes for each long value in
		 * the array.
		 */
		randomAccessFile.seek(4L + (8 * words.length));

		/*
		 * Now, loop through the words and write them out to the file, recording the
		 * start position of each word. Note that the text is written in the UTF-8
		 * encoding, which uses 1, 2, or 3 bytes per character, so we can't assume that
		 * the string length equals the string size on the disk. Also note that the
		 * writeUTF() method records the length of the string so it can be read by
		 * readUTF().
		 */
		for (int i = 0; i < words.length; i++) {
			// Record file position:
			wordPositions[i] = randomAccessFile.getFilePointer();
			// Write word:
			randomAccessFile.writeUTF(words[i]);
		}

		/* Now go back to the beginning of the file and write the positions. */
		// Start at the beginning:
		randomAccessFile.seek(0L);
		// Write the array length:
		randomAccessFile.writeInt(wordPositions.length);
		// Loop through the array:
		for (int i = 0; i < wordPositions.length; i++) {
			// Write the array element:
			randomAccessFile.writeLong(wordPositions[i]);
		}
		// Close the file when done:
		randomAccessFile.close();
	}

	/* These are the instance fields of the WordList class. */
	// The file to read words from:
	RandomAccessFile randomAccessFile;
	// The index that gives the position of each word:
	long[] positions;

	// Create a WordList object based on the named file.
	public WordList(String filename) throws IOException {
		// Open the random access file for read-only access.
		randomAccessFile = new RandomAccessFile(filename, "r");

		/* Now read the array of file positions from it. */
		// Read array length:
		int numberOfWords = randomAccessFile.readInt();
		// Allocate array:
		positions = new long[numberOfWords];
		// Read array contents:
		for (int i = 0; i < numberOfWords; i++) {
			positions[i] = randomAccessFile.readLong();
		}
	}

	// Call this method when the WordList is no longer needed.
	public void close() throws IOException {
		// Close file:
		if (randomAccessFile != null) {
			randomAccessFile.close();
		}
		// Remember that it is closed:
		randomAccessFile = null;
		positions = null;
	}

	// Return the number of words in the WordList.
	public int size() {
		// Make sure we haven't closed the file already:
		if (randomAccessFile == null) {
			throw new IllegalStateException("already closed");
		}
		return positions.length;
	}

	/*
	 * Return the string at the specified position in the WordList. This throws an
	 * IllegalStateException if it is already closed, and throws an
	 * ArrayIndexOutOfBounds if the index is negative or >= the size() of the
	 * entries.
	 */
	public String get(int index) throws IOException {
		// Make sure close() hasn't already been called.
		if (randomAccessFile == null) {
			throw new IllegalStateException("Already closed");
		}
		// Move to the word position in the file.
		randomAccessFile.seek(positions[index]);
		// Read and return the string at that position.
		return randomAccessFile.readUTF();
	}
}
