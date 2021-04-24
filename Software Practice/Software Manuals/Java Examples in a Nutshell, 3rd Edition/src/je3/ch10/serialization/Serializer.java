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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.Serializable;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 242-244. This class
 * defines utility routines that use Java serialization.
 */
public class Serializer {
	/**
	 * Serialize the provided object (and any Serializable objects to which it
	 * refers) and store its serialized state in File outputFile.
	 */
	static void store(Serializable object, File outputFile) throws IOException {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(outputFile));
		// This method serializes an object graph or object tree.
		out.writeObject(object);
		out.close();
	}

	/**
	 * Deserialize the contents of File inputFile and return the resulting object or
	 * object tree.
	 */
	static Object load(File outputFile) throws IOException, ClassNotFoundException {
		// The class to be deserialized:
		ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(outputFile));
		// This method deserializes an object graph or object tree.
		Object deserializedObject = objectInputStream.readObject();
		objectInputStream.close();
		return (deserializedObject);
	}

	/**
	 * Use object serialization to make a "deep clone" of the object
	 * serializableObject. This method serializes serializableObject and all objects
	 * it refers to, and then deserializes that graph of objects, which means that
	 * everything is copied.
	 * <p>
	 * This differs from the clone() method of an object, which is usually
	 * implemented to produce a "shallow" clone, that copies only references to
	 * other objects, instead of deep-copying all referenced objects.
	 */
	static Object deepclone(final Serializable serializableObject) throws IOException, ClassNotFoundException {
		/*
		 * Create a connected pair of "piped" streams. We'll write bytes to one, and
		 * read them from the other.
		 */
		final PipedOutputStream pipeout = new PipedOutputStream();
		PipedInputStream pipein = new PipedInputStream(pipeout);

		/*
		 * Now define an independent thread to serialize the object and write its bytes
		 * to the PipedOutputStream.
		 */
		Thread writer = new Thread() {
			public void run() {
				ObjectOutputStream out = null;
				try {
					out = new ObjectOutputStream(pipeout);
					out.writeObject(serializableObject);
				} catch (IOException e) {
				} finally {
					try {
						out.close();
					} catch (Exception e) {
					}
				}
			}
		};
		// Make the thread start serializing and writing.
		writer.start();

		/*
		 * Meanwhile, in this thread, read and deserialize from the piped input stream.
		 * The resulting object is a deep clone of the original.
		 */
		ObjectInputStream objectInputStream = new ObjectInputStream(pipein);
		Object deserializedObjectTree = objectInputStream.readObject();
		objectInputStream.close();
		return deserializedObjectTree;
	}

	/**
	 * This is a simple serializable data structure that we use below for testing
	 * the methods above.
	 */
	public static class DataStructure implements Serializable {

		private static final long serialVersionUID = 2469871518723018039L;

		String message;
		int[] data;
		DataStructure next;

		public String toString() {
			String messageString = message;
			for (int i = 0; i < data.length; i++) {
				messageString += " " + data[i];
			}
			if (this.next != null) {
				messageString += "\n\t" + this.next.toString();
			}
			return messageString;
		}
	}

	/** This class defines a main() method for testing */
	public static class Test {
		public static void main(String[] args) throws IOException, ClassNotFoundException {
			// Create a simple object graph.
			DataStructure dataStructure = new DataStructure();
			dataStructure.message = "hello world";
			dataStructure.data = new int[] { 1, 2, 3, 4 };
			dataStructure.next = new DataStructure();
			dataStructure.next.message = "Nested structure";
			dataStructure.next.data = new int[] { 9, 8, 7 };

			// Display the original object graph.
			System.out.println("Original data structure: " + dataStructure);

			// Output it to a file.
			File outputFile = new File("output/datastructure.ser");
			System.out.println("Storing to a file...");
			Serializer.store(dataStructure, outputFile);

			// Read it back from the file, and display it again.
			dataStructure = (DataStructure) Serializer.load(outputFile);
			System.out.println("Read from the file: " + dataStructure);

			/*
			 * Create a deep clone and display that. After making the copy, modify the
			 * original to prove that the clone is "deep."
			 */
			DataStructure dataStructureClone = (DataStructure) Serializer.deepclone(dataStructure);
			System.out.println("Changing the original...");
			dataStructure.next.message = null;
			dataStructure.next.data = null;
			System.out.println("\nChanged! and the deep clone should not be affected: ");
			System.out.println("Deep clone: " + dataStructureClone);
		}
	}
}
