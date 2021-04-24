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
package je3.ch10.serialization;

import java.io.Externalizable;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import je3.ch02.classes.IntList;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 248-249. This subclass of
 * IntList assumes that most of the integers it contains are less than 32,000.
 * It implements Externalizable so that it can define a compact serialization
 * format that takes advantage of this fact. The Externalizable interface
 * extends the Serializable interface.
 */
public class CompactIntList extends IntList implements Externalizable {
	/**
	 * This version number is here in case a later revision of this class wants to
	 * modify the externalization format, but still retain compatibility with
	 * externalized objects written by this version.
	 */
	static final byte version = 1;

	/**
	 * This method from the Externalizable interface is responsible for saving the
	 * complete state of the object to the specified stream. It can write anything
	 * it wants, as long as readExternal() can read it.
	 */
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		// Compact the list to its current size.
		trim();

		// Start with our version number.
		out.writeByte(version);
		// Output the number of array elements.
		out.writeInt(size);
		// Now loop through the array:
		for (int i = 0; i < size; i++) {
			// The array element to write:
			int n = data[i];
			if ((n <= Short.MAX_VALUE) && (n > Short.MIN_VALUE)) {
				/*
				 * If n fits in a short and is not Short.MIN_VALUE, then write it out as a
				 * short, saving ourselves two bytes.
				 */
				out.writeShort(n);
			} else {
				/*
				 * Otherwise write out the special value Short.MIN_VALUE to signal that the
				 * number does not fit in a short, and then output the number using a full 4
				 * bytes, for 6 bytes total.
				 */
				out.writeShort(Short.MIN_VALUE);
				out.writeInt(n);
			}
		}
	}

	/**
	 * This Externalizable method is responsible for completely restoring the state
	 * of the object. A no-arg constructor will be called to re-create the object,
	 * and this method must read the state written by writeExternal() to restore the
	 * object's state.
	 */
	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		// Start by reading and verifying the version number.
		byte v = in.readByte();
		if (v != version) {
			throw new IOException("CompactIntList: unknown version number");
		}

		// Read the number of array elements, and make the array that big.
		int newsize = in.readInt();
		// A protected method inherited from IntList:
		setCapacity(newsize);
		// Save this size.
		this.size = newsize;

		// Now read that many values from the stream.
		for (int i = 0; i < newsize; i++) {
			short n = in.readShort();
			if (n != Short.MIN_VALUE) {
				data[i] = n;
			} else {
				data[i] = in.readInt();
			}
		}
	}

	/**
	 * A main() method to prove that it works. The writeExternal() and
	 * readExternal() methods are both called indirectly from the
	 * Serializer.deepclone() method, from ch10, though it's hard to see that except
	 * in the debugger.
	 */
	public static void main(String[] args) throws Exception {
		CompactIntList compactIntList = new CompactIntList();
		for (int i = 0; i < 100; i++) {
			compactIntList.add((int) (Math.random() * 40000));
		}
		CompactIntList compactIntListClone = (CompactIntList) Serializer.deepclone(compactIntList);
		if (compactIntList.equals(compactIntListClone)) {
			System.out.println("Success! The compact integer list and its clone pass the equals() test.");
		} else {
			System.out.println("Sorry, the compact integer list and its clone do not pass the equals() test.");
		}
		
		String serializedListName = "output/compactIntList.ser";
		Serializer.store(compactIntList, new File(serializedListName));
		String status = "";
		status += "The original compact integer list has been serialized and output to: \"";
		status += serializedListName;
		status += "\".";
		System.out.println(status);
	}
}
