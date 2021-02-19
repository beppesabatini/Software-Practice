package ch08;

import java.util.*;

// The point here is that an instance of instance of List can 
// be converted to an instance of Set without knowing the exact
// actual data type in the containers. Similar to p. 242.

public class Capture {
	// The first <T> here defines the name of the type variable, 
	// the stand-in for the generic type.
	static <T> Set<T> listToSet(List<T> list) {
		Set<T> set = new HashSet<T>();
		set.addAll(list);
		return set;
	}

	// Note <?> is a wildcard type, while <T> is a generic.
	// Generic methods are a little more powerful.
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {
		List list = new ArrayList();
		list.add(1);
		list.add(2);
		
		// Here, the wildcard List<?> is captured.
		Set<?> set = listToSet(list);
		System.out.println(set);
	}
}
