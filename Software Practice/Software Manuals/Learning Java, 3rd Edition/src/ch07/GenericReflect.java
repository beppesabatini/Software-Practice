package ch07;

import java.util.*;
import java.lang.reflect.*;

/**
 * Similar to what appears on p. 208. A quick introduction to accessing
 * information about Generics.
 */
public class GenericReflect {

	public static void main(String[] args) {
		// This line appears in the List interface:
		// public interface List<E> extends Collection<E> { }

		TypeVariable<?>[] tv = List.class.getTypeParameters();
		System.out.println(tv[0].getName()); // Outputs: E

		class StringList extends ArrayList<String> {
			// Dummy class to verify how the compiler behaves.
			private static final long serialVersionUID = -2307310008764359688L;
		}

		Type type = StringList.class.getGenericSuperclass();
		System.out.println(type); // Outputs: java.util.ArrayList<java.lang.String>
		ParameterizedType pt = (ParameterizedType) type;
		System.out.println(pt.getActualTypeArguments()[0]); // Outputs: class java.lang.String
	}
}

//	 The output will look like this: 
//	 E 
//	 java.util.ArrayList<java.lang.String> 
//	 class java.lang.String
