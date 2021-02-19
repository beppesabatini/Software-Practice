package ch12;

import java.io.*;
import java.util.zip.*;

// From p. 420-421.

public class GUnzip {
	public static int sChunk = 8192;

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: GUnzip <gzippedSourceFile>");
			return;
		}
		// create input stream
		String zipname, source;
		if (args[0].endsWith(".gz")) {
			zipname = args[0];
			source = args[0].substring(0, args[0].length() - 3);
		} else {
			zipname = args[0] + ".gz";
			source = args[0];
		}
		GZIPInputStream zipin;
		try {
			FileInputStream in = new FileInputStream(zipname);
			zipin = new GZIPInputStream(in);
		} catch (IOException e) {
			System.out.println("Couldn't open " + zipname + ".");
			return;
		}
		byte[] buffer = new byte[sChunk];
		// decompress the file
		try {
			String outputName= "output\\" + source.substring(zipname.lastIndexOf('\\') + 1, source.length()); 
			FileOutputStream out = new FileOutputStream(outputName);
			int length;
			while ((length = zipin.read(buffer, 0, sChunk)) != -1) {
				out.write(buffer, 0, length);
			}
			out.close();
			System.out.println("File unzipped at: " + outputName);
			} catch (IOException e) {
			System.out.println("Couldn't decompress " + args[0] + ".");
		}
		try {
			zipin.close();
		} catch (IOException e) {
		}
	}
}
