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
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

public class ChannelToWriter {
	/**
	 * From Java Examples in a Nutshell, 3rd Edition, pp. 156-157. Read bytes from
	 * the specified channel, decode them using the specified Charset, and write the
	 * resulting characters to the specified writer.
	 */
	public static void copy(ReadableByteChannel readableByteChannel, Writer writer, Charset charset)
			throws IOException {
		// Get and configure the CharsetDecoder we'll use.
		CharsetDecoder decoder = charset.newDecoder();
		decoder.onMalformedInput(CodingErrorAction.IGNORE);
		decoder.onUnmappableCharacter(CodingErrorAction.IGNORE);

		// Get the buffers we'll use, and the backing array for the CharBuffer.
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(2 * 1024);
		CharBuffer charBuffer = CharBuffer.allocate(2 * 1024);
		char[] charBufferArray = charBuffer.array();

		// Read from channel, into the ByteBuffer, until EOF:
		while (readableByteChannel.read(byteBuffer) != -1) {
			// Switch to drain mode for decoding:
			byteBuffer.flip();
			/*
			 * Decode the byte buffer into the char buffer. Pass false to indicate that
			 * we're not done.
			 */
			decoder.decode(byteBuffer, charBuffer, false);

			/*
			 * Put the char buffer into drain mode, and write its contents to the Writer,
			 * reading them from the backing array.
			 */
			charBuffer.flip();
			writer.write(charBufferArray, charBuffer.position(), charBuffer.remaining());

			/*
			 * Discard all bytes we decoded, and put the byte buffer back into fill mode.
			 * Since all characters were output, clear that buffer.
			 */
			// Discard decoded bytes:
			byteBuffer.compact();
			// Clear the character buffer:
			charBuffer.clear();
		}

		/*
		 * At this point there may still be some bytes in the buffer to decode. So, put
		 * the buffer into drain mode, call decode() a final time, and finish with a
		 * flush().
		 */
		byteBuffer.flip();
		// True means this is the final call to decode().
		decoder.decode(byteBuffer, charBuffer, true);
		// Flush any buffered chars:
		decoder.flush(charBuffer);
		// Write these final chars (if any) to the writer:
		charBuffer.flip();
		writer.write(charBufferArray, charBuffer.position(), charBuffer.remaining());
		writer.flush();
	}

	// A test method: copy a UTF-8 file to standard out.
	public static void main(String[] args) throws IOException {
		FileInputStream fileInputStream = new FileInputStream(args[0]);
		FileChannel fileChannel = fileInputStream.getChannel();
		/*
		 * The JavaDoc calls the OutputStreamWriter a
		 * "bridge from character streams to byte streams."
		 */
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(System.out);
		Charset utf8 = Charset.forName("UTF-8");
		ChannelToWriter.copy(fileChannel, outputStreamWriter, utf8);
		fileInputStream.close();
		fileChannel.close();
		outputStreamWriter.close();
	}
}
