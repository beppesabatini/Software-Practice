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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.ArrayIndexOutOfBoundsException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 226-228. A convenience
 * class that can display a localized exception message depending on the class
 * of the exception. It uses a MessageFormat, and passes five arguments that the
 * localized message may include:
 * 
 * <pre>
 *   {0}: the message included in the exception or error.
 *   {1}: the full class name of the exception or error.
 *   {2}: the file the exception occurred in
 *   {3}: a line number in that file.
 *   {4}: the current date and time.
 * </pre>
 * 
 * Messages are looked up in a ResourceBundle with the base name "Errors", using
 * a the full class name of the exception object as the resource name. If no
 * resource is found for a given exception class, the superclasses are checked.
 */
public class LocalizedError {

	/*
	 * In this example, the error properties file is saved in the package
	 * je3.ch08.i18n, and is called "Menus.properties". You may need to change this
	 * constant to match your local package structure.
	 */
	private static final String errorPropertiesFile = "je3.ch08.i18n.Errors";

	public static void display(Throwable error) {
		ResourceBundle bundle;
		// Try to get the resource bundle.
		// If none, print the error in a non-localized way.
		try {
			String bundleName = errorPropertiesFile;
			bundle = ResourceBundle.getBundle(bundleName);
		} catch (MissingResourceException e) {
			error.printStackTrace(System.err);
			return;
		}

		/*
		 * Look up a localized message resource in that bundle, using the class name of
		 * the error (or its superclasses) as the resource name. If no resource was
		 * found, display the error without localization.
		 */
		String message = null;
		Class<?> errorClass = error.getClass();
		while ((message == null) && (errorClass != Object.class)) {
			try {
				message = bundle.getString(errorClass.getName());
			} catch (MissingResourceException e) {
				errorClass = errorClass.getSuperclass();
			}
		}
		if (message == null) {
			error.printStackTrace(System.err);
			return;
		}

		/*
		 * Get the filename and line number for the exception. In Java 1.4, this is
		 * easy, but in prior releases, we had to try parsing the output
		 * Throwable.printStackTrace().
		 */
		StackTraceElement frame = error.getStackTrace()[0];
		String filename = frame.getFileName();
		int linenum = frame.getLineNumber();

		// Set up an array of arguments to use with the message.
		String errmsg = error.getMessage();
		Object lineNumArg = linenum < 0 ? "N/A" : linenum;
		Object[] args = { ((errmsg != null) ? errmsg : ""), error.getClass().getName(), filename, lineNumArg,
				new Date() };

		/*
		 * Finally, display the localized error message, using MessageFormat.format() to
		 * substitute the arguments into the message.
		 */
		System.err.println(MessageFormat.format(message, args));
	}

	/**
	 * This is a simple test program that demonstrates the display() method. You can
	 * use it to trigger and display a FileNotFoundException or an
	 * ArrayIndexOutOfBoundsException.
	 **/
	public static void main(String[] args) {
		FileReader in = null;
		try {
			in = new FileReader(args[0]);
		} catch(ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
			LocalizedError.display(arrayIndexOutOfBoundsException);
		} catch (FileNotFoundException fileNotFoundException) {
			LocalizedError.display(fileNotFoundException);
		} finally {
			if (in != null) {
				in = null;
			}
		}
	}
}
