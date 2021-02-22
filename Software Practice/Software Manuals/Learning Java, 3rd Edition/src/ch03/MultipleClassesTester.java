package ch03;

/**
 * Not from the Learning Java manual.
 */
public class MultipleClassesTester {

	public static void main(String[] args) {
		System.out.println("Testing behavior of multiple classes defined in one file.");
		System.out.println("Both \"final\" and \"abstract\" classes can be defined in a separate file."); 
		new TopLevelFinalClass(1);
		new TopLevelFinalClass();
		// new TopLevelAbstractClass(1); // won't compile
		// new TopLevelAbstractClass();  // won't compile
		new TopLevelAbstractClassImplementation();
	}
}
