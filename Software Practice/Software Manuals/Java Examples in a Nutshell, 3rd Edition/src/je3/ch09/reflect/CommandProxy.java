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
import java.awt.event.FocusListener;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 239-240. This class is an
 * InvocationHandler based on a Map of method names to Command objects. When the
 * invoke() method is called, the name of the method to be invoked is looked up
 * in the map, and the associated Command, if any is invoked. Arguments passed
 * to invoke() are always ignored.
 * <p>
 * Note that there is no public constructor for this class. Instead, there is a
 * static factory method for creating Proxy objects that use an instance of this
 * class. Pass the interface to be implemented and a Map of name/Command pairs.
 */
public class CommandProxy implements InvocationHandler {
	// This maps method names to Command objects that implement them.
	Map<String, Command> methodMap;

	// Private constructor:
	private CommandProxy(Map<String, Command> methodMap) {
		this.methodMap = methodMap;
	}

	/**
	 * This method implements InvocationHandler.invoke(), and invokes a Command, if
	 * any particular such command is associated with the name of Method method. It
	 * ignores arguments[] and always returns null.
	 */
	@Override
	public Object invoke(Object p, Method method, Object[] arguments) throws Throwable {
		String methodName = method.getName();
		Command command = (Command) methodMap.get(methodName);
		if (command != null) {
			command.invoke();
		}
		return null;
	}

	/**
	 * Return an object that implements the specified interface, using the
	 * name-to-Command map as the implementation of the interface methods.
	 * 
	 * @param interface To be implemented
	 * @param methodMap Maps from String to Command
	 * @return an anonymous Object that implements the interface
	 */
	public static Object create(Class<?> iface, Map<String, Command> methodMap) {
		InvocationHandler handler = new CommandProxy(methodMap);
		ClassLoader loader = handler.getClass().getClassLoader();
		return Proxy.newProxyInstance(loader, new Class[] { iface }, handler);
	}

	// This is a test class to demonstrate the use of CommandProxy.
	static class Test {
		public static void main(String[] args) throws java.io.IOException {
			// Set up a simple GUI.
			javax.swing.JFrame jFrame = new javax.swing.JFrame("Command Test");
			Dimension jFrameDimension = new Dimension(150, 100);
			jFrame.setPreferredSize(jFrameDimension);
			jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			javax.swing.JButton button = new javax.swing.JButton("Hello World");
			jFrame.getContentPane().add(button, java.awt.BorderLayout.CENTER);
			jFrame.pack();
			jFrame.setVisible(true);
			
			System.out.println("To run this demo: ");
			System.out.println("Try giving the button focus, and then taking focus away from the button.");
			System.out.println("Make sure you can still see the button after it loses focus.");

			// Set up the Map of method names to Command objects
			Map<String, Command> methodMap = new HashMap<String, Command>();
			methodMap.put("focusGained", Command.parse(button, "setText(\"Hello!\")"));
			methodMap.put("focusLost", Command.parse(button, "setText(\"Goodbye\")"));

			// Use CommandProxy.create() to create a proxy FocusListener.
			FocusListener focusListener = (FocusListener) CommandProxy.create(FocusListener.class, methodMap);

			// Use the synthetic FocusListener.
			button.addFocusListener(focusListener);
		}
	}
}
