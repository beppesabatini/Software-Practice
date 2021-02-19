package ch07;

import java.util.HashMap;

// Demonstrates deep cloning. From p. 197. This is supposed to be an
// example of how deep cloning makes "equals()" return true; but
// as will be seen if we run this, the manual is wrong. The clone()
// behavior may have changed since the manual was written. 

public class DeepCloningSheep implements Cloneable {

	public HashMap<String, Sheep> flock = null;

	public DeepCloningSheep() {
		System.out.println("Initializing a new DeepCloningSheep");
		flock = new HashMap<String, Sheep>();

		flock.put("SheepOne", new Sheep());
		flock.put("SheepTwo", new Sheep());
	}

	@SuppressWarnings("unchecked")
	public DeepCloningSheep clone() {
		try {
			DeepCloningSheep clonedSheep = (DeepCloningSheep) super.clone();
			// Here we are "deep cloning" the flock associated with
			// the sheep being cloned.
			clonedSheep.flock = (HashMap<String, Sheep>) this.flock.clone();
			return (clonedSheep);
		} catch (CloneNotSupportedException e) {
			throw new Error("This should never happen!");
		}
	}
}

class DeepCloningInvoker {

	public static void main(String[] args) {
		DeepCloningSheep one = new DeepCloningSheep();
		DeepCloningSheep two = one.clone();
		if (one.equals(two)) {
			System.out.println("Equivalent Sheep - Deep");
		} else {
			System.out.println("Not Equivalent Sheep - Deep. The manual is wrong.");
		}
		if (one.flock.equals(two.flock)) {
			System.out.println("Equivalent flock - Deep");
		} else {
			System.out.println("Not Equivalent flock - Deep");
		}
	}
}
