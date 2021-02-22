package ch12;

import java.io.*;
import java.util.*;

/**
 * From Learning Java, 3rd Edition, p. 416. Serializes a hash table and write it
 * to disk. (Run "Load" to deserialize the disk file and echo it to standard
 * out.) The input data and output file are both hard-coded in this example.
 */
public class Save {
	public static void main(String[] args) {
		Hashtable<String, Object> hashtable = new Hashtable<String, Object>();
		hashtable.put("string", "Gabriel Garcia Marquez");
		hashtable.put("int", 26);
		hashtable.put("double", Math.PI);

		try {
			String outputFilename = "output/hashtable.ser";
			FileOutputStream fileOut = new FileOutputStream(outputFilename);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(hashtable);
			out.close();
			System.out.println("Hashtable serialized to: " + outputFilename);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
