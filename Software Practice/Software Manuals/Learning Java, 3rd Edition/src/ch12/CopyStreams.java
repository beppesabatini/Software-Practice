package ch12;

import java.io.*;

/**
 * This code does not appear in Learning Java chap. 12.
 */
public class CopyStreams {
	public static void main(String[] args) throws Exception {
		if(args.length != 2) {
			System.out.println("Usage: CopyStreams <fromFileName> <toFileName>");
			System.exit(0);
		}
		String fromFileName = args[0];
		String toFileName = args[1];

		BufferedInputStream in = new BufferedInputStream(new FileInputStream(fromFileName));
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(toFileName));
		byte[] buff = new byte[32 * 1024];
		int len;
		while ((len = in.read(buff)) > 0) {
			out.write(buff, 0, len);
		}
		in.close();
		out.close();
	}
}
