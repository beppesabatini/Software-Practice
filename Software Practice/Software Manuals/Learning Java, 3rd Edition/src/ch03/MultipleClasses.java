package ch03;

public class MultipleClasses {
	static public void main(String[] args) {
		System.out.println("Hello World");
		System.out.println("Defining multiple classes in one file.");
		System.out.println("A (second) top level public class won't compile.");
		System.out.println("A top level private class won't compile.");

		new TopLevelFinalClass(1);
		new TopLevelFinalClass();
		// new TopLevelAbstractClass(1); // Won't compile, can't instantiate an abstract
		// new TopLevelAbstractClass(); // Won't compile, can't instantiate an abstract
	};
}

/*
 * These next two won't compile, only "final" and "abstract" class visibility
 * modifiers are permitted at the top level outside a public class.
 * 
 * public class TopLevelPublicClass {
 * 
 * }
 * 
 * private class TopLevelPrivateClass {
 * 
 * }
 */

final class TopLevelFinalClass {
	public TopLevelFinalClass(int a) {
		System.out.println("A top-level \"final\" class will compile and run with a public initializer.");
	}

	protected TopLevelFinalClass() {
		System.out.println("A top-level \"final\" class will compile and run with a protected initializer.");
	}
}

abstract class TopLevelAbstractClass {
	public TopLevelAbstractClass(int a) {
		System.out.println("A top-level \"abstract\" class won't compile and run with a public initializer.");
	}

	protected TopLevelAbstractClass() {
		System.out.println("A top-level \"abstract\" class won't compile and run with a protected initializer.");
	}
}

class TopLevelAbstractClassImplementation extends TopLevelAbstractClass {
	TopLevelAbstractClassImplementation() {
		System.out.println("A top level implementation of an abstract class will compile and run.");
	}
}
