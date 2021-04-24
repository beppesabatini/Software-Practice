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

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;

import je3.ch02.classes.Tokenizer;
import je3.ch02.classes.AbstractTokenizer;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 79-81. This Tokenizer
 * implementation extends AbstractTokenizer, to tokenize a stream of text read
 * from a java.io.Reader. It implements the createBuffer() and fillBuffer()
 * methods required by AbstractTokenizer. See that class for details on how
 * these methods must behave. Note that a buffer size may be selected, and that
 * this buffer size also determines the maximum token length. The Test class is
 * a simple test that tokenizes a file and uses the tokens to produce a copy of
 * the file.
 * <p/>
 * There is a Run Configuration for this file. The output to the console does
 * not tokenize contractions correctly. The output to a file
 * (output/simpleTokenizerTest.txt.copy) is correct.
 */
public class ReaderTokenizer extends AbstractTokenizer {
	Reader reader;

	// Create a ReaderTokenizer with a default buffer size of 16K characters.
	public ReaderTokenizer(Reader reader) {
		this(reader, 16 * 1024);
	}

	public ReaderTokenizer(Reader reader, int bufferSize) {
		// Remember the reader from which we read input:
		this.reader = reader;
		/*
		 * Tell our superclass about the selected buffer size. The superclass will pass
		 * this number to createBuffer().
		 */
		maximumTokenLength(bufferSize);
	}

	// Create a buffer to tokenize.
	@Override
	protected void createBuffer(int bufferSize) {
		// Make sure AbstractTokenizer only calls this method once.
		assert text == null;
		// The new buffer:
		this.text = new char[bufferSize];
		// How much text it contains:
		this.numChars = 0;
	}

	/**
	 * Fill or refill the buffer. See {@link AbstractTokenizer#fillBuffer}() for
	 * what this method must do.
	 */
	@Override
	protected boolean fillBuffer() throws IOException {
		// Make sure AbstractTokenizer is upholding its end of the bargain.
		assert (text != null && 0 <= tokenStart && tokenStart <= tokenEnd);
		assert (tokenEnd <= p && p <= numChars && numChars <= text.length);

		// First, shift already tokenized characters out of the buffer.
		if (tokenStart > 0) {
			// Shift array contents
			System.arraycopy(text, tokenStart, text, 0, numChars - tokenStart);
			// And update buffer indexes
			tokenEnd -= tokenStart;
			p -= tokenStart;
			numChars -= tokenStart;
			tokenStart = 0;
		}

		// Now try to read more characters into the buffer.
		int numberCharactersRead = reader.read(text, numChars, text.length - numChars);
		// If there are no more characters, return false.
		if (numberCharactersRead == -1)
			return false;
		// Otherwise, adjust the number of valid characters in the buffer.
		numChars += numberCharactersRead;
		return true;
	}

	/*
	 * This test class tokenizes a file, reporting the tokens to standard out, and
	 * creating a copy of the file, to demonstrate that every input character is
	 * accounted for (since spaces are not skipped).
	 */
	public static class Test {
		public static void main(String[] args) throws java.io.IOException {
			String inputFileName = args[0];
			if (inputFileName == null || "".equals(inputFileName)) {
				System.err.println("Usage: ReaderTokenizer <fileName>");
				System.exit(0);
			}

			Reader fileReader = new FileReader(inputFileName);

			String outputFileName = inputFileName;
			int lastSlash = inputFileName.lastIndexOf('/');
			if (lastSlash > 0 && lastSlash < inputFileName.length() - 1) {
				outputFileName = inputFileName.substring(lastSlash + 1);
			} else {
				lastSlash = inputFileName.lastIndexOf('\\');
				if (lastSlash > 0 && lastSlash < inputFileName.length() - 1) {
					outputFileName = inputFileName.substring(lastSlash + 1);
				}
			}

			PrintWriter printWriter = new PrintWriter(new FileWriter("output/" + outputFileName + ".copy"));
			ReaderTokenizer readerTokenizer = new ReaderTokenizer(fileReader);
			readerTokenizer.tokenizeWords(true).tokenizeNumbers(true).tokenizeSpaces(true);
			while (readerTokenizer.next() != Tokenizer.EOF) {
				switch (readerTokenizer.tokenType()) {
					case Tokenizer.EOF: {
						System.out.println("EOF");
						break;
					}
					case Tokenizer.WORD: {
						System.out.println("WORD: " + readerTokenizer.tokenText());
						break;
					}
					case Tokenizer.NUMBER: {
						System.out.println("NUMBER: " + readerTokenizer.tokenText());
						break;
					}
					case Tokenizer.SPACE: {
						System.out.println("SPACE");
						break;
					}
					default: {
						System.out.println((char) readerTokenizer.tokenType());
					}
				}
				// Copy the token to the file.
				printWriter.print(readerTokenizer.tokenText());
			}
			printWriter.close();
		}
	}
}
