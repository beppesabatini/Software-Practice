
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
// Note no package statement here.

import java.io.File;
import java.io.FileReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import je3.ch05.net.Server;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 192-193. This is a
 * demonstration service. It attempts to do things that may or may not be
 * allowed by the security policy and reports the results of its attempts to the
 * client.
 * <p/>
 * Java seems to no longer support the security.policy features quite exactly as
 * described in the manual. Not able to implement them or test them at this time.
 */
public class SecureService implements Server.Service {
	@Override
	public void serve(InputStream inputStream, OutputStream outputStream) throws IOException {
		PrintWriter printWriterOutput = new PrintWriter(outputStream);

		/*
		 * Try to install our own security manager. If we can do this, we can defeat any
		 * access control.
		 */
		printWriterOutput.println("Trying to create and install a security manager...");
		try {
			System.setSecurityManager(new SecurityManager());
			printWriterOutput.println("Success!");
		} catch (Exception exception) {
			printWriterOutput.println("Failed: " + exception);
		}

		/*
		 * Try to make the Server and the Java VM exit. This is a denial of service
		 * attack, and it should not succeed!
		 */
		printWriterOutput.println();
		printWriterOutput.println("Trying to exit...");
		try {
			System.exit(-1);
		} catch (Exception exception) {
			printWriterOutput.println("Failed: " + exception);
		}

		// The default system policy allows this property to be read.
		printWriterOutput.println();
		printWriterOutput.println("Attempting to find java version...");
		try {
			printWriterOutput.println(System.getProperty("java.version"));
		} catch (Exception exception) {
			printWriterOutput.println("Failed: " + exception);
		}

		// The default system policy does not allow this property to be read.
		printWriterOutput.println();
		printWriterOutput.println("Attempting to find home directory...");
		try {
			printWriterOutput.println(System.getProperty("user.home"));
		} catch (Exception exception) {
			printWriterOutput.println("Failed: " + exception);
		}

		// Our custom policy explicitly allows this property to be read.
		printWriterOutput.println();
		printWriterOutput.println("Attempting to read service.tmp property...");
		try {
			String tempDirectoryName = System.getProperty("service.tmp");
			printWriterOutput.println(tempDirectoryName);
			File tempDirectory = new File(tempDirectoryName);
			File file = new File(tempDirectory, "testfile");

			/*
			 * Check whether we've been given permission to write files to the tmpdir temp
			 * directory.
			 */
			printWriterOutput.println();
			printWriterOutput.println("Attempting to write a file in " + tempDirectoryName + "...");
			FileOutputStream fileOutputStream = null;
			try {
				fileOutputStream = new FileOutputStream(file);
				printWriterOutput.println("Opened file for writing: " + file);
			} catch (Exception e) {
				printWriterOutput.println("Failed: " + e);
			} finally {
				if (fileOutputStream != null) {
					fileOutputStream.close();
				}
			}

			/*
			 * Check whether we've been given permission to read files from the tmpdir temp
			 * directory.
			 */
			printWriterOutput.println();
			printWriterOutput.println("Attempting to read from " + tempDirectoryName + "...");
			FileReader fileReaderInput = null;
			try {
				fileReaderInput = new FileReader(file);
				printWriterOutput.println("Opened file for reading: " + file);
			} catch (Exception exception) {
				printWriterOutput.println("Failed: " + exception);
			} finally {
				if (fileReaderInput != null) {
					fileReaderInput.close();
				}
			}
		} catch (Exception exception) {
			printWriterOutput.println("Failed: " + exception);
		}

		// Close the Service sockets.
		printWriterOutput.close();
		if (inputStream != null) {
			inputStream.close();
		}
	}
}
