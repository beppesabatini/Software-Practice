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

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 231-233. A program that
 * displays a class synopsis for the named class.
 */
public class ShowClass {
	/** The main method. Print info about the named class. */
	public static void main(String[] args) throws ClassNotFoundException {
		Class<?> c = Class.forName(args[0]);
		print_class(c);
	}

	/**
	 * Display the modifiers, name, superclass and interfaces of a class or
	 * interface. Then go and list all constructors, fields, and methods.
	 */
	public static void print_class(Class<?> currentClass) {
		// Print modifiers, type (class or interface), name and superclass.
		if (currentClass.isInterface()) {
			// The modifiers will include the "interface" keyword here...
			System.out.print(Modifier.toString(currentClass.getModifiers()) + " " + typename(currentClass));
		} else if (currentClass.getSuperclass() != null) {
			String superclass = "";
			superclass += Modifier.toString(currentClass.getModifiers()) + " class " + typename(currentClass);
			superclass += " extends " + typename(currentClass.getSuperclass());
			System.out.print(superclass);
		} else {
			System.out.print(Modifier.toString(currentClass.getModifiers()) + " class " + typename(currentClass));
		}

		// Print interfaces or super-interfaces of the class or interface.
		Class<?>[] interfaces = currentClass.getInterfaces();
		if ((interfaces != null) && (interfaces.length > 0)) {
			if (currentClass.isInterface()) {
				System.out.print(" extends ");
			} else {
				System.out.print(" implements ");
			}
			for (int i = 0; i < interfaces.length; i++) {
				if (i > 0) {
					System.out.print(", ");
				}
				System.out.print(typename(interfaces[i]));
			}
		}

		System.out.println(" {"); // Begin class member listing.

		// Now look up and display the members of the class.
		System.out.println("  --- Constructors ---");
		Constructor<?>[] constructors = currentClass.getDeclaredConstructors();
		for (int i = 0; i < constructors.length; i++) {
			// Display constructors.
			print_method_or_constructor(constructors[i]);
		}
		System.out.println("  --- Fields ---");
		// Look up fields.
		Field[] fields = currentClass.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			// Display them.
			print_field(fields[i]);
		}

		System.out.println("  --- Methods ---");
		// Look up methods.
		Method[] methods = currentClass.getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			// Display them.
			print_method_or_constructor(methods[i]);
		}

		System.out.println("}"); // End class member listing.
	}

	/** Return the name of an interface or primitive type, handling arrays. */
	public static String typename(Class<?> typeName) {
		String brackets = "";
		while (typeName.isArray()) {
			brackets += "[]";
			typeName = typeName.getComponentType();
		}
		String name = typeName.getName();
		int position = name.lastIndexOf('.');
		if (position != -1) {
			name = name.substring(position + 1);
		}
		return name + brackets;
	}

	/** Return a string version of modifiers, handling spaces nicely. */
	public static String modifiers(int m) {
		if (m == 0) {
			return "";
		} else {
			return Modifier.toString(m) + " ";
		}
	}

	/** Print the modifiers, type, and name of a field. */
	public static void print_field(Field field) {
		String fieldInfo = "";
		fieldInfo += "  " + modifiers(field.getModifiers()) + typename(field.getType());
		fieldInfo += " " + field.getName() + ";";
		System.out.println(fieldInfo);
	}

	/**
	 * Print the modifiers, return type, name, parameter types and exception type of
	 * a method or constructor. Note the use of the Member interface to allow this
	 * method to work with both Method and Constructor objects
	 */
	public static void print_method_or_constructor(Member member) {
		Class<?> returntype = null;
		Class<?> parameters[];
		Class<?> exceptions[];
		if (member instanceof Method) {
			Method method = (Method) member;
			returntype = method.getReturnType();
			parameters = method.getParameterTypes();
			exceptions = method.getExceptionTypes();
			String methodInfo = "";
			methodInfo += "  " + modifiers(member.getModifiers()) + typename(returntype);
			methodInfo += " " + member.getName() + "(";
			System.out.print(methodInfo);
		} else {
			Constructor<?> constructor = (Constructor<?>) member;
			parameters = constructor.getParameterTypes();
			exceptions = constructor.getExceptionTypes();
			System.out.print("  " + modifiers(member.getModifiers()) + typename(constructor.getDeclaringClass()) + "(");
		}

		for (int i = 0; i < parameters.length; i++) {
			if (i > 0) {
				System.out.print(", ");
			}
			System.out.print(typename(parameters[i]));
		}
		System.out.print(")");
		if (exceptions.length > 0)
			System.out.print(" throws ");
		for (int i = 0; i < exceptions.length; i++) {
			if (i > 0) {
				System.out.print(", ");
			}
			System.out.print(typename(exceptions[i]));
		}
		System.out.println(";");
	}
}
