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
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Date;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 149-150. This program
 * copies the file named in its first argument, to the file named in its second
 * argument, or to standard output if there is no second argument.
 * <p/>
 * This example demonstrates a special bulk-transfer method that is unique to
 * the FileChannel class: the transferTo() method copies the entire contents of
 * the file to the specified channel without the need for any explicitly
 * allocated ByteBuffer objects. The manual notes that the method is
 * particularly useful for web servers and other applications that transfer file
 * contents.
 */
public class FileCopy2 {
	public static void main(String[] args) {
		// Streams to the two files. These are closed in the "finally" block.
		FileInputStream fileInputStream = null;
		FileOutputStream fileOutputStream = null;
		try {
			// Open a stream to for the input file and get a channel from it.
			fileInputStream = new FileInputStream(args[0]);
			FileChannel fileInputChannel = fileInputStream.getChannel();

			// Now get the output channel.
			WritableByteChannel writableByteChannelOut;
			// If there is a second filename...
			if (args.length > 1) {
				// ...open the file stream...
				fileOutputStream = new FileOutputStream(args[1]);
				// ...and get its channel.
				writableByteChannelOut = fileOutputStream.getChannel();
			} else {
				/*
				 * There is no destination filename. Write to standard out instead.
				 */
				// Wrap standard out in a stream.
				writableByteChannelOut = Channels.newChannel(System.out);
			}

			// Query the size of the input file.
			long numbytes = fileInputChannel.size();

			/**
			 * Bulk-transfer all bytes from one channel to the other. This is a special
			 * feature of FileChannel channels. See also FileChannel.transferFrom().
			 */
			System.out.println("Copying \"" + args[0] + "\" through a FileChannel");
			fileInputChannel.transferTo(0, numbytes, writableByteChannelOut);
		} catch (IOException e) {
			/*
			 * IOExceptions usually have useful informative messages. Display the message if
			 * anything goes wrong.
			 */
			System.out.println(e);
		} finally {
			/*
			 * Always close input and output streams. Doing this closes the channels
			 * associated with them as well.
			 */
			try {
				if (fileInputStream != null) {
					fileInputStream.close();
				}
				if (fileOutputStream != null) {
					fileOutputStream.close();
				}
			} catch (IOException e) {
			}
		}
		if (args.length > 1 && "".equals(args[1]) == false) {
			File targetFile = new File(args[1]);
			if (targetFile.exists()) {
				System.out.println("Copied to \"" + args[1] + "\" on " + new Date(targetFile.lastModified()));
			}
		}
	}
}
