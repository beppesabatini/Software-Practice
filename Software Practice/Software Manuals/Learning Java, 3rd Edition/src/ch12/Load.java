package ch12;

import java.io.*;
import java.util.*;

/**
 * From Learning Java, 3rd Edition, p. 416. Deserializes a Hashtable and prints
 * it to the console. The input and output locations are both hard-coded here.
 */
public class Load {
	public static void main(String[] args) {
		try {
			FileInputStream fileIn = new FileInputStream("output/hashtable.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);

			@SuppressWarnings("rawtypes")
			Hashtable h = (Hashtable) in.readObject();
			System.out.println(h.toString());
			in.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
