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
package je3.ch08.i18n;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 214-215. A program to
 * convert from one character encoding to another. To test this, launch the Run
 * Configuration "ConvertEncoding". It takes as input a checked-in test file
 * encoded in Windows-1252 (the old Microsoft Word character set). It converts
 * it into UTF-8.
 * <p/>
 * See <a href=
 * "https://docs.oracle.com/javase/8/docs/technotes/guides/intl/encoding.doc.html">Supported
 * Encodings</a> for other charsets and their official ("canonical") names to
 * use as arguments.
 * <p/>
 * Note that notepad-plus-plus can probably do all the character-set conversion
 * you need for general purposes, but this program gives you the functionality
 * to do it inside Java.
 */
public class ConvertEncoding {

	public static void usage() {
		String usage = "";
		usage += "Usage: java ConvertEncoding <options>\n";
		usage += "Options:\n\t-from <encoding>\n\t";
		usage += "-to <encoding>\n\t";
		usage += "-in <file>\n\t-out <file>";
		System.err.println(usage);
		System.exit(1);
	}

	public static void main(String[] args) {
		String inputEncoding = null, outputEncoding = null;
		String inputFile = null, outputFile = null;
		if (args.length == 0 || args.length % 2 != 0) {
			/*
			 * All flags require a value, so there should be an even number of arguments.
			 */
			usage();
		}
		for (int i = 0; i < args.length; i++) {
			// Parse command-line arguments.
			if (args[i].equals("-from")) {
				inputEncoding = args[++i];
			} else if (args[i].equals("-to")) {
				outputEncoding = args[++i];
			} else if (args[i].equals("-in")) {
				inputFile = args[++i];
			} else if (args[i].equals("-out")) {
				outputFile = args[++i];
			} else {
				usage();
			}
		}

		try {
			convert(inputFile, outputFile, inputEncoding, outputEncoding);
		} catch (Exception e) {
			// Defined at the end of this chapter.
			LocalizedError.display(e);
			System.exit(1);
		}
	}

	public static void convert(String inputFile, String outputFile, String inputEncoding, String outputEncoding)
			throws IOException, UnsupportedEncodingException {
		// Set up byte streams.
		InputStream inputStream;
		if (inputFile != null) {
			inputStream = new FileInputStream(inputFile);
		} else
			inputStream = System.in;
		OutputStream outputStream;
		if (outputFile != null) {
			outputStream = new FileOutputStream(outputFile);
		} else {
			outputStream = System.out;
		}

		// Use default encoding if no encoding is specified.
		if (inputEncoding == null) {
			inputEncoding = System.getProperty("file.encoding");
		}
		if (outputEncoding == null) {
			outputEncoding = System.getProperty("file.encoding");
		}

		// Set up character streams.
		Reader bufferedInputStreamReader = new BufferedReader(new InputStreamReader(inputStream, inputEncoding));
		Writer bufferedOutputStreamWriter = new BufferedWriter(new OutputStreamWriter(outputStream, outputEncoding));

		/*
		 * Copy characters from input to output. The InputStreamReader converts from the
		 * input encoding to Unicode, and the OutputStreamWriter converts from Unicode
		 * to the output encoding. Characters that cannot be represented in the output
		 * encoding are output as a question mark ('?').
		 */
		char[] charactersToConvert = new char[4096];
		int length;
		// Read a block of input.
		while ((length = bufferedInputStreamReader.read(charactersToConvert)) != -1) {
			// And write it out, now converted.
			bufferedOutputStreamWriter.write(charactersToConvert, 0, length);
		}
		System.out.println("Input file: \t\"" + inputFile + "\"");
		System.out.println("Converted to: \t\"" + outputFile + "\"");
		// Close the input.
		bufferedInputStreamReader.close();
		// Flush and close output.
		bufferedOutputStreamWriter.close();
	}
}
