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
package je3.ch06.nio;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import je3.ch02.classes.Tokenizer;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 160-161. This class
 * implements the Tokenizer interface for a FileChannel and Charset. It extends
 * {@link ByteBufferTokenizer}, and uses FileChannel#map() to memory-map the
 * contents of the file into a ByteBuffer.
 */
public class MappedFileTokenizer extends ByteBufferTokenizer {
	static final int DEFAULT_BUFFER_SIZE = 32 * 1024;
	// The file we want to tokenize:
	FileChannel channel;
	// What size chunks to map at at time:
	int byteBufferSize;
	// How big the file is:
	long fileSize;
	// Starting position of the next chunk:
	long filePosition;

	/*
	 * Construct a tokenizer for the specified FileChannel, assuming the file
	 * contains text encoded using the specified Charset.
	 */
	public MappedFileTokenizer(FileChannel channel, Charset charset) throws IOException {
		this(channel, charset, DEFAULT_BUFFER_SIZE, DEFAULT_BUFFER_SIZE);
	}

	/*
	 * Construct a tokenizer for the specified file and charset, additionally
	 * specifying the size of the byte and characters buffers to use.
	 */
	public MappedFileTokenizer(FileChannel channel, Charset charset, int charBufferSize, int byteBufferSize)
			throws IOException {
		// The superclass handles the charset and size.
		super(charset, charBufferSize);
		this.channel = channel;
		this.byteBufferSize = byteBufferSize;
		// Get the length of the file:
		fileSize = channel.size();
		// And start at the beginning:
		filePosition = 0;
	}

	// Return true if there are more bytes for us to return.
	@Override
	protected boolean hasMoreBytes() {
		return filePosition < fileSize;
	}

	// Read the next chunk of bytes and return them.
	@Override
	protected ByteBuffer getMoreBytes() throws IOException {
		/*
		 * Return byteBufferSize bytes, or the number remaining in the file if that is
		 * less.
		 */
		long length = byteBufferSize;
		if (filePosition + length > fileSize) {
			length = fileSize - filePosition;
		}

		// Memory map the bytes into a buffer.
		ByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, filePosition, length);
		// Store the position of the next chunk...
		filePosition += length;
		// ...and, return the memory-mapped buffer of bytes.
		return buffer;
	}

	public static void main(String[] args) throws IOException {
		// Open file streams and get channels for them.
		FileInputStream fileInputStream = new FileInputStream(args[0]);
		FileChannel fileInputChannel = fileInputStream.getChannel();

		// Do the tokenizing.
		System.out.println("Tokenizing \"" + args[0] + "\" as a readable FileChannel.");

		MappedFileTokenizer mappedFileTokenizer = new MappedFileTokenizer(fileInputChannel, Charset.forName("UTF-8"));
		mappedFileTokenizer.tokenizeWords(true).skipSpaces(true);

		while (mappedFileTokenizer.next() != Tokenizer.EOF) {
			System.out.println(mappedFileTokenizer.tokenText());
		}
		fileInputStream.close();
	}
}
