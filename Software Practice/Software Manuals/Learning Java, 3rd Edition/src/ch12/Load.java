package ch12;

import java.io.*;
import java.util.*;

// Deserializes a hashtable and prints it to standard out. 
// The input and output locations are both hard-coded here. 
// From p. 416.

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
