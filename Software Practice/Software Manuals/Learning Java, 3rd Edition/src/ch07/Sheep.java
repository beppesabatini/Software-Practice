package ch07;

/**
 * From Learning Java, 3rd Edition, p. 196. Demonstrates cloning.
 */
public class Sheep implements Cloneable {

	// Overrides the clone() function in the superclass, which is "Object."
	public Sheep clone() {
		try {
			return (Sheep) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new Error("This should never happen!");
		}
	}
}

class CloningInvoker {

	public static void main(String[] args) {
		Sheep one = new Sheep();
		Sheep two = one.clone();
		if (two.equals(one)) {
			System.out.println("Equivalent");
		} else {
			System.out.println("Not Equivalent");
		}
	}
}
