package ch07;

import java.util.*;
import java.lang.reflect.*;

// Quick introduction to accessing information about Generics.
// Similar to what appears on p. 208.

public class GenericReflect {

	public static void main(String[] args) {
		// This line appears in the List interface:
		// public interface List<E> extends Collection<E> { }

		TypeVariable<?>[] tv = List.class.getTypeParameters();
		System.out.println(tv[0].getName()); // E

		class StringList extends ArrayList<String> {

			private static final long serialVersionUID = -2307310008764359688L;

		}

		Type type = StringList.class.getGenericSuperclass();
		System.out.println(type); // java.util.ArrayList<java.lang.String>
		ParameterizedType pt = (ParameterizedType) type;
		System.out.println(pt.getActualTypeArguments()[0]);
		// class java.lang.String
	}
/*
 * Output: 
 * E
 * java.util.ArrayList<java.lang.String>
 * class java.lang.String
 */
}
