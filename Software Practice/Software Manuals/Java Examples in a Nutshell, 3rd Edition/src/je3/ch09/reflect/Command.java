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
package je3.ch09.reflect;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.swing.JFrame;

import je3.ch02.classes.Tokenizer;
import je3.ch02.classes.CharSequenceTokenizer;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 234-238. This class
 * represents a Method, the list of arguments to be passed to that method, and
 * the object on which the method is to be invoked.
 * <p>
 * The invoke() method invokes the method. The actionPerformed() method does the
 * same thing, allowing this class to implement ActionListener, and be used to
 * respond to ActionEvents generated in a GUI or elsewhere.
 * <p>
 * The static parse() method parses a string representation of a method and its
 * arguments.
 */
public class Command implements ActionListener, InvocationHandler {
	// The method to be invoked:
	Method method;
	// The object on which to invoke it:
	Object target;
	// The arguments to pass to the method:
	Object[] args;

	// An empty array. This is used for methods with no arguments at all.
	static final Object[] nullargs = new Object[] {};

	/** This constructor creates a Command object for a no-arg method */
	public Command(Object target, Method m) {
		this(target, m, nullargs);
	}

	/**
	 * This constructor creates a Command object for a method that takes the
	 * specified array of arguments. Note that the parse() method provides another
	 * way to create a Command object.
	 */
	public Command(Object target, Method method, Object[] args) {
		this.target = target;
		this.method = method;
		if (args == null) {
			args = nullargs;
		}
		this.args = args;
	}

	/**
	 * This construct specifies the method to call by name. It looks for a method of
	 * the target object with the specified name and specified number of arguments.
	 * It does not attempt to verify the types of the arguments, since the complex
	 * data object (Integer, Boolean, etc) in the arguments[] array could represent
	 * a reference or primitive arguments.
	 */
	public Command(Object target, String methodName, Object[] args) {
		this.target = target;
		if (args == null) {
			args = nullargs;
		}
		this.args = args;

		Method[] methods = target.getClass().getMethods();
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			if (method.getParameterTypes().length == args.length && method.getName().equals(methodName)) {
				this.method = method;
				break;
			}
		}

		// If we didn't find a method, throw an exception.
		if (this.method == null) {
			throw new IllegalArgumentException("Unknown method " + methodName);
		}
	}

	/**
	 * Invoke the Command by calling the method on its target, and passing the
	 * arguments. See also actionPerformed(), which does not throw the checked
	 * exceptions that this method does.
	 */
	public void invoke() throws IllegalAccessException, InvocationTargetException {
		// Use reflection to invoke the method.
		method.invoke(target, args);
	}

	/**
	 * This method implements the ActionListener interface. It is like invoke(),
	 * except that it catches the exceptions thrown by that method, and rethrows
	 * them as unchecked RuntimeExceptions.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			// Call the invoke method.
			invoke();
		} catch (InvocationTargetException ex) {
			// A checked exception is here converted to an unchecked exception.
			// Note that we chain to the original exception.
			throw new RuntimeException(ex);
		} catch (IllegalAccessException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * This method implements the InvocationHandler interface, so that a Command
	 * object can be used with Proxy objects. Note that it simply calls the
	 * no-argument invoke() method, ignoring its arguments and returning null. This
	 * means that it is only useful for proxying interfaces that define a single
	 * no-arg void method.
	 */
	public Object invoke(Object p, Method m, Object[] a) throws Throwable {
		invoke();
		return null;
	}

	/**
	 * This static method creates a Command using the specified target object, and
	 * the specified string. The string should contain a method name, followed by an
	 * optional parenthesized comma-separated argument list, and a semicolon. The
	 * arguments may be boolean, integer or double literals, or double-quoted
	 * strings. The parser is lenient about missing commas, semicolons and quotes,
	 * but throws an IOException if it cannot parse the string.
	 */
	public static Command parse(Object target, String text) throws IOException {
		// The name of the method:
		String methodName;
		// Holds arguments as we parse them:
		ArrayList<Object> arguments = new ArrayList<>();
		// Holds argument types:
		ArrayList<Object> types = new ArrayList<>();

		Tokenizer tokenizer = new CharSequenceTokenizer(text);
		tokenizer.skipSpaces(true).tokenizeWords(true).tokenizeNumbers(true);
		tokenizer.quotes("\"'", "\"'");

		if (tokenizer.next() != Tokenizer.WORD) {
			throw new IOException("Missing method name for command");
		}
		methodName = tokenizer.tokenText();
		tokenizer.next();
		if (tokenizer.tokenType() == '(') {
			tokenizer.next();
			for (;;) { // Loop through all arguments
				int c = tokenizer.tokenType();
				if (c == ')') {
					// Consume closing parenthesis...
					tokenizer.next();
					// ...and break out of the list.
					break;
				}

				if (c == Tokenizer.WORD) {
					String word = tokenizer.tokenText();
					if (word.equals("true")) {
						arguments.add(Boolean.TRUE);
						types.add(boolean.class);
					} else if (word.equals("false")) {
						arguments.add(Boolean.FALSE);
						types.add(boolean.class);
					} else { // Treat unquoted identifiers as strings...
						arguments.add(word);
						types.add(String.class);
					}
				} else if (c == '"') {
					// A double-quoted string:
					arguments.add(tokenizer.tokenText());
					types.add(String.class);
				} else if (c == '\'') {
					// A single-quoted character:
					arguments.add(tokenizer.tokenText().charAt(0));
					types.add(char.class);
				} else if (c == Tokenizer.NUMBER) {
					// An integer:
					arguments.add(Integer.parseInt(tokenizer.tokenText()));
					types.add(int.class);
				} else {
					// Anything else is a syntax error.
					String errorMessage = "";
					errorMessage += "Unexpected token " + tokenizer.tokenText();
					errorMessage += " in argument list of " + methodName + "().";
					throw new IOException(errorMessage);
				}

				// Consume the token we just parsed, and then consume an optional comma.
				if (tokenizer.next() == ',') {
					tokenizer.next();
				}
			}
		}

		// Consume optional semicolon after method name or argument list.
		if (tokenizer.tokenType() == ';') {
			tokenizer.next();
		}

		/*
		 * We've parsed the argument list. Next, convert the lists of argument values
		 * and types to arrays.
		 */
		Object[] argValues = arguments.toArray();
		Class<?>[] argtypes = (Class[]) types.toArray(new Class[argValues.length]);

		/*
		 * At this point, we've got a method name, and arrays of argument values and
		 * types. Use reflection on the class of the target object to find a method with
		 * the given name and argument types. Throw an exception if we can't find the
		 * named method.
		 */
		Method method;
		try {
			method = target.getClass().getMethod(methodName, argtypes);
		} catch (Exception e) {
			throw new IOException("No such method found, or wrong argument " + "types: " + methodName);
		}

		/*
		 * Finally, create and return a Command object, using the target object passed
		 * to this method, the Method object we obtained above, and the array of
		 * argument values we parsed from the string.
		 */
		return new Command(target, method, argValues);
	}

	/**
	 * This simple program demonstrates how a Command object can be parsed from a
	 * string and used as an ActionListener object in a Swing application.
	 */
	static class Test {
		public static void main(String[] args) throws IOException {
			javax.swing.JFrame jFrame = new javax.swing.JFrame("Command Test");
			Dimension jFrameDimension = new Dimension(150, 100);
			jFrame.setPreferredSize(jFrameDimension);
			jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			javax.swing.JButton button01 = new javax.swing.JButton("Tick");
			javax.swing.JButton button02 = new javax.swing.JButton("Tock");
			javax.swing.JLabel label = new javax.swing.JLabel("Hello world");
			java.awt.Container pane = jFrame.getContentPane();

			pane.add(button01, java.awt.BorderLayout.WEST);
			pane.add(button02, java.awt.BorderLayout.EAST);
			pane.add(label, java.awt.BorderLayout.NORTH);

			// The second argument is just a string, but it gets parsed and run like a
			// method.
			button01.addActionListener(Command.parse(label, "setText(\"Tick Button Pressed\");"));
			button02.addActionListener(Command.parse(label, "setText(\"Tock Button Pressed\");"));

			jFrame.pack();
			jFrame.setVisible(true);
		}
	}
}
