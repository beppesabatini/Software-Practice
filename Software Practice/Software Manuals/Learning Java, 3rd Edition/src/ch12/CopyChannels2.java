package ch12;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;

// This code does not appear in Learning Java ch. 12.

public class CopyChannels2 {
	public static void main(String[] args) throws Exception {
		String fromFileName = args[0];
		String toFileName = args[1];
		
		FileInputStream inStream = new FileInputStream(fromFileName);
		FileChannel in = inStream.getChannel();
		
		FileOutputStream outStream = new FileOutputStream(toFileName);
		FileChannel out = outStream.getChannel();

		// This line is the only difference between this class and the
		// earlier CopyChannel (without the "2"). 
		ByteBuffer buff = ByteBuffer.allocateDirect(32 * 1024);

		while (in.read(buff) > 0) {
			buff.flip();
			out.write(buff);
			buff.clear();
		}

		in.close();
		inStream.close();
		out.close();
		outStream.close();
	}
}
