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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Date;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 153-154. The manual notes
 * that variants on this basic loop appear in most programs that use the New I/O
 * API.
 * <p/>
 * The channels appear to be a bit irregular in what they read and write
 * regarding the buffer, but the functions seem to have some internal
 * bookkeeping that is hidden from us, and which keeps track of any such
 * irregularity. So it seems.
 */
public class FileCopy3 {
	public static void main(String[] args) throws IOException {
		// Open file streams and get channels for them.
		FileInputStream fileInputStream = new FileInputStream(args[0]);
		ReadableByteChannel readableByteChannel = fileInputStream.getChannel();
		WritableByteChannel writeableByteChannel;
		if (args.length > 1) {
			writeableByteChannel = new FileOutputStream(args[1]).getChannel();
		} else {
			writeableByteChannel = Channels.newChannel(System.out);
		}

		// Do the copy.
		System.out.println("Copying \"" + args[0] + "\" through byte channels.");
		copy(readableByteChannel, writeableByteChannel);
		fileInputStream.close();
		if (args.length > 1 && "".equals(args[1]) == false) {
			File targetFile = new File(args[1]);
			if (targetFile.exists()) {
				System.out.println("Copied to \"" + args[1] + "\" on " + new Date(targetFile.lastModified()));
				System.out.println("File size: " + targetFile.length() + " bytes");
			}
		}
	}

	// Read all available bytes from one channel and copy them to the other.
	public static void copy(ReadableByteChannel readableByteChannelIn, WritableByteChannel readableByteChannelOut)
			throws IOException {
		// First, we need a buffer to hold blocks of copied bytes.
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(32 * 1024);
		/*
		 * Now loop until there are no more bytes to read (after the read() calls) and
		 * the buffer is empty (after the write() calls).
		 */
		while (readableByteChannelIn.read(byteBuffer) != -1 || byteBuffer.position() > 0) {
			/*
			 * The read() call leaves the buffer in "fill mode". To prepare to write bytes
			 * out of the buffer we have to put it in "drain mode" by flipping it: setting
			 * limit to position (where the read() ended) and position to zero (where the
			 * write() will start).
			 */
			byteBuffer.flip();

			/*
			 * Now write some or all of the bytes, from the buffer, out to the output
			 * channel.
			 */
			readableByteChannelOut.write(byteBuffer);

			/*
			 * Compact the buffer by discarding bytes that were written, and shifting any
			 * remaining bytes. This method also prepares the buffer for the next call to
			 * read() by setting the current position to first available byte (for writing),
			 * and the limit to the remaining buffer capacity.
			 */
			byteBuffer.compact();
		}
	}
}
