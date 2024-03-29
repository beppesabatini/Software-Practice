package ch07;

import java.lang.reflect.*;

/**
 * From Learning Java, 3rd Edition, p. 205
 */
class Invoke {
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Usage: <class name> <method name>");
			System.exit(1);
		}
		try {
			Class<?> c = Class.forName(args[0]);
			Method m = c.getMethod(args[1]);
			Object ret = m.invoke(null);
			String message = "Invoked static method: " + args[1] + " of class: " + args[0] + " with no args\nResults: " + ret;
			System.out.println(message);
		} catch (ClassNotFoundException e) {
			System.out.println(e);
			// Class.forName( ) can't find the class
		} catch (NoSuchMethodException e2) {
			System.out.println(e2);
			// that method doesn't exist
		} catch (IllegalAccessException e3) {
			System.out.println(e3);
			// we don't have permission to invoke that method
		} catch (InvocationTargetException e) {
			System.out.println(e);
			// an exception ocurred while invoking that method
			System.out.println("Method threw an: " + e.getTargetException());
		}
	}
}
