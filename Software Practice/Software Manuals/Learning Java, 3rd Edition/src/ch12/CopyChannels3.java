package ch12;

import java.io.*;
import java.nio.channels.*;

// This code does not appear in Learning Java ch. 12.

public class CopyChannels3 {
	public static void main(String[] args) throws Exception {
		String fromFileName = args[0];
		String toFileName = args[1];
		
		FileInputStream inStream = new FileInputStream(fromFileName);
		FileChannel in = inStream.getChannel();
		
		FileOutputStream outStream = new FileOutputStream(toFileName);
		FileChannel out = outStream.getChannel();

		in.transferTo(0, (int) in.size(), out);

		in.close();
		inStream.close();
		out.close();
		outStream.close();
	}
}
