package ch12;

import java.io.*;
import java.util.zip.*;

// From p. 419.

public class GZip {
	public static int sChunk = 8192;

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: GZip <sourceFile>");
			return;
		}
		// create output stream
		String zipname = args[0] + ".gz";
		String outputName= "output\\" + zipname.substring(zipname.lastIndexOf('\\') + 1, zipname.length()); 
		
		
		GZIPOutputStream zipout;
		try {
			FileOutputStream out = new FileOutputStream(outputName);
			zipout = new GZIPOutputStream(out);
		} catch (IOException e) {
			System.out.println("Couldn't create " + zipname + ".");
			return;
		}
		byte[] buffer = new byte[sChunk];
		// compress the file
		try {
			FileInputStream in = new FileInputStream(args[0]);
			int length;
			while ((length = in.read(buffer, 0, sChunk)) != -1) {
				zipout.write(buffer, 0, length);
			}
			in.close();
			System.out.println("Zipped file complete at: " + outputName);
		} catch (IOException e) {
			System.out.println("Couldn't compress " + args[0] + ".");
		}
		try {
			zipout.close();
		} catch (IOException e) {
		}
	}
}
