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
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;

import je3.ch02.classes.AbstractTokenizer;
import je3.ch02.classes.Tokenizer;
import je3.ch06.nio.ByteBufferTokenizer;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 161-162. ChannelTokenizer
 * is a subclass of {@link ByteBufferTokenizer}, which is in turn a subset of
 * {@link AbstractTokenizer}, so launching the Run Configuration for
 * "ChannelTokenizer" should test all three classes, at least partially.
 */
public class ChannelTokenizer extends ByteBufferTokenizer {
	static final int DEFAULT_BUFFER_SIZE = 32 * 1024;
	// Where the bytes come from:
	ReadableByteChannel channel;
	// Where we put those bytes:
	ByteBuffer buffer;
	// Whether there are any more:
	boolean hasMoreBytes;

	/*
	 * Construct a ChannelTokenizer to tokenize the specified channel, decoding its
	 * bytes using the specified charset.
	 */
	public ChannelTokenizer(ReadableByteChannel channel, Charset charset) {
		this(channel, charset, DEFAULT_BUFFER_SIZE, DEFAULT_BUFFER_SIZE);
	}

	/*
	 * Construct a ChannelTokenizer for the channel and charset, additionally
	 * specifying the character and byte buffer sizes to use.
	 */
	public ChannelTokenizer(ReadableByteChannel channel, Charset charset, int charBufferSize, int byteBufferSize) {
		// Superclass handles charset and size.
		super(charset, charBufferSize);
		// Remember the channel:
		this.channel = channel;
		// Assume there are some bytes in the channel:
		this.hasMoreBytes = true;
		// Allocate the buffer we'll use to store bytes:
		buffer = ByteBuffer.allocateDirect(byteBufferSize);
	}

	// Return false when we're at EOF and have returned all bytes.
	@Override
	protected boolean hasMoreBytes() {
		return hasMoreBytes;
	}

	// Refill the buffer and return it.
	@Override
	protected ByteBuffer getMoreBytes() throws IOException {
		// Clear the buffer; this prepares it to be filled:
		buffer.clear();
		// Read a chunk of bytes:
		int bytesRead = channel.read(buffer);
		// If we are at EOF, remember that for hasMoreBytes().
		if (bytesRead == -1) {
			hasMoreBytes = false;
		}
		// Prepare the buffer to be drained:
		buffer.flip();
		return buffer;
	}

	public static void main(String[] args) throws IOException {
		// Open file streams and get channels for them.
		FileInputStream fileInputStream = new FileInputStream(args[0]);
		ReadableByteChannel readableByteChannel = fileInputStream.getChannel();

		// Do the tokenizing.
		System.out.println("Tokenizing \"" + args[0] + "\" as a readable byte channel.");
		ChannelTokenizer channelTokenizer = new ChannelTokenizer(readableByteChannel, Charset.forName("UTF-8"));
		channelTokenizer.tokenizeWords(true).skipSpaces(true);
		while (channelTokenizer.next() != Tokenizer.EOF) {
			System.out.println(channelTokenizer.tokenText());
		}
		fileInputStream.close();
	}
}
