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
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.channels.FileChannel;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 150-152. BGrep: a regular
 * expression search utility, like Unix grep, but block-oriented instead of
 * line-oriented. For any match found, the filename and character position
 * within the file (note: not the line number) are printed, along with the text
 * that matched.
 * 
 * <pre>
 * Usage:
 *    java BGrep [-e &lt;encoding>] [-i] [-s] &lt;pattern> &lt;filename>...
 * 
 * Options:
 *   -e &lt;encoding> specifies and encoding. UTF-8 is the default
 *   -i enables case-insensitive matching.  Use -s also for non-ASCII text
 *   -s enables strict (but slower) processing of non-ASCII characters
 * </pre>
 * 
 * This program requires that each file to be searched must fit into main
 * memory. Thus it will not work with extremely large files. Also, the Run
 * Configuration is not working yet with Korean words such as 유리를.
 */
public class BGrep {
	public static void main(String[] args) {
		// Default to UTF-8 encoding:
		String encodingName = "UTF-8";
		// Start with the default regexp flags:
		int flags = Pattern.MULTILINE;

		try {
			// First, process any options:
			int nextArgument = 0;
			while (args[nextArgument].charAt(0) == '-') {
				String option = args[nextArgument++];
				if (option.equals("-e")) {
					encodingName = args[nextArgument++];
				} else if (option.equals("-i")) {
					// Use case-insensitive matching:
					flags |= Pattern.CASE_INSENSITIVE;
					// Strict Unicode processing:
				} else if (option.equals("-s")) {
					// Use case-insensitive Unicode:
					flags |= Pattern.UNICODE_CASE;
					// Allow different representations of the same character to match:
					flags |= Pattern.CANON_EQ;
				} else {
					System.err.println("Unknown option: " + option);
					usage();
				}
			}

			// Get the Charset for converting bytes to chars.
			Charset charset = Charset.forName(encodingName);

			/*
			 * The next argument is required to be a regular expression. Compile it into a
			 * Pattern object.
			 */
			Pattern pattern = Pattern.compile(args[nextArgument++], flags);

			// Require that at least one file is specified.
			if (nextArgument == args.length) {
				usage();
			}

			// Loop through each of the specified filenames.
			while (nextArgument < args.length) {
				String filename = args[nextArgument++];
				// This will hold the complete text of the file:
				CharBuffer decodedCharacters;
				try {
					// Handle per-file errors locally.
					// Open a FileChannel to the named file:
					FileInputStream stream = new FileInputStream(filename);
					FileChannel fileChannel = stream.getChannel();

					/*
					 * Memory-map the file into one big ByteBuffer. This is easy but may be somewhat
					 * inefficient for short files.
					 */
					ByteBuffer byteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());

					/*
					 * We can close the file once it is is mapped into memory. Closing the stream
					 * closes the channel, too.
					 */
					stream.close();

					// Decode the entire ByteBuffer into one big CharBuffer
					decodedCharacters = charset.decode(byteBuffer);
				} catch (IOException e) {
					// File not found, or another problem. Print error message...
					System.err.println(e);
					// ...and move on to the next file.
					continue;
				}

				/*
				 * This is the basic regular expression loop for finding all matches in a
				 * CharSequence. Note that CharBuffer implements CharSequence. A Matcher holds
				 * state for a given Pattern and text.
				 */
				Matcher matcher = pattern.matcher(decodedCharacters);
				while (matcher.find()) { // While there are more matches...
					// ...print out details of the match.

					String matchInfo = "";
					// File name:
					matchInfo += filename + ":";
					// Character position:
					matchInfo += matcher.start() + ": ";
					// Matching text:
					matchInfo += matcher.group();
					System.out.println(matchInfo);
				}
			}
		}
		// These are the things that can go wrong in the code above.
		catch (UnsupportedCharsetException e) {
			// Bad encoding name:
			System.err.println("Unknown encoding: " + encodingName);
		} catch (PatternSyntaxException e) {
			// Bad search pattern:
			System.err.println("Syntax error in search pattern:\n" + e.getMessage());
		} catch (ArrayIndexOutOfBoundsException e) {
			// Wrong number of arguments:
			usage();
		}
	}

	/** A utility method to display invocation syntax and exit. */
	public static void usage() {
		System.err.println("Usage: java BGrep [-e <encoding>] [-i] [-s] <pattern> <filename>...");
		System.exit(1);
	}
}
