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

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 246-247. A simple class
 * that implements a growable array of ints, and knows how to serialize itself
 * as efficiently as a non-growable array.
 */
public class SerialIntList implements Serializable {

	private static final long serialVersionUID = -5357454909404269298L;

	/*
	 * These are the fields of this class. By default, the serialization mechanism
	 * would just write them out. But we've declared size to be transient, which
	 * means it will not be serialized. And we've provided writeObject() and
	 * readObject() methods below to customize the serialization process.
	 */
	// An array to store the numbers:
	protected int[] data = new int[8];
	// Index of next unused element of array:
	protected transient int size = 0;

	/** Return an element of the array */
	public int get(int index) {
		if (index >= size) {
			throw new ArrayIndexOutOfBoundsException(index);
		} else {
			return data[index];
		}
	}

	/** Add an int to the array, growing the array if necessary. */
	public void add(int x) {
		// Grow array if needed.
		if (data.length == size) {
			resize(data.length * 2);
		}
		// Store the int in it.
		data[size++] = x;
	}

	/** An internal method to change the allocated size of the array. */
	protected void resize(int newsize) {
		// Create a new array.
		int[] newdata = new int[newsize];
		// Copy array elements.
		System.arraycopy(data, 0, newdata, 0, size);
		// Replace old array.
		data = newdata;
	}

	/**
	 * Get rid of unused array elements before serializing the array. This may
	 * reduce the number of array elements to serialize. It also makes data.length
	 * the same as size, so there is no need to save the (transient) size field. The
	 * serialization mechanism will automatically call this method when serializing
	 * an object of this class. Note that this must be declared private.
	 */
	private void writeObject(ObjectOutputStream out) throws IOException {
		// Compact the array.
		if (data.length > size) {
			resize(size);
		}
		// Then write it out normally.
		out.defaultWriteObject();
	}

	/**
	 * Restore the transient size field after deserializing the array. The
	 * serialization mechanism automatically calls this method (specifically the
	 * ObjectInputStream calls it).
	 */
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		// Read the array normally.
		in.defaultReadObject();
		// Restore the transient field.
		size = data.length;
	}

	/**
	 * Does this object contain the same values as the object o? We override this
	 * Object method so we can test the class.
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof SerialIntList)) {
			return false;
		}
		SerialIntList that = (SerialIntList) o;
		if (this.size != that.size) {
			return false;
		}
		for (int i = 0; i < this.size; i++) {
			if (this.data[i] != that.data[i]) {
				return false;
			}
		}
		return true;
	}

	/** We must override the hashCode() method when we override equals(). */
	@Override
	public int hashCode() {
		// Non-zero, to hash [0] and [1] to distinct values.
		int code = 1;
		for (int i = 0; i < size; i++) {
			code = code * 997 + data[i]; // Ignore overflow
		}
		return code;
	}

	/**
	 * A main() method to prove that this works. The readObject() and writeObject()
	 * methods above are both called called and tested indirectly, from the
	 * Serializer.deepclone() method in ch10, though it's hard to see that except in
	 * the debugger.
	 */
	public static void main(String[] args) throws Exception {
		SerialIntList serialIntList = new SerialIntList();
		for (int i = 0; i < 100; i++) {
			serialIntList.add((int) (Math.random() * 40000));
		}
		SerialIntList serialIntListClone = (SerialIntList) Serializer.deepclone(serialIntList);
		if (serialIntList.equals(serialIntListClone)) {
			System.out.println("Success! The integer list and its clone pass the equals() test.");
		} else {
			System.out.println("Sorry, the integer list and its clone do not pass the equals() test.");
		}
		
		String serializedIntListName = "output/intList.ser";
		Serializer.store(serialIntList, new File(serializedIntListName));
		String status = "";
		status += "The original integer list has been serialized and output to: \"";
		status += serializedIntListName;
		status += "\".";
		System.out.println(status);
	}
}
