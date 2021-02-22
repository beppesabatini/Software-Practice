package ch08;

/**
 * This code doesn't seem to appear in the Learning Java manual. It also doen't
 * appear to demonstrate anything meaningful. Type inference is discussed.
 */
class Base {
}

class Sub1 extends Base implements Runnable {
	public void run() {
	}
}

class Sub2 extends Base implements Runnable {
	public void run() {
	}
}

public class TypeInference {
	static <T extends Base> T infer(T t1, T t2) {
		return null;
	}

	public static void main(String[] args) {
		Base base = infer(new Sub1(), new Sub2());
		// Note (in code): Eclipse 3.1 says this is an error, but it's not.
		// (from BFS): "Base" is the nearest common supertype, so that's
		// the return type that should be "inferred," but Base is still
		// a subtype of runnable so this is a legal assignment.
		Runnable runnable = infer(new Sub1(), new Sub2());

		if (base instanceof Runnable && runnable instanceof Runnable) {
			System.out.println("No error here!");
		}
	}

}
