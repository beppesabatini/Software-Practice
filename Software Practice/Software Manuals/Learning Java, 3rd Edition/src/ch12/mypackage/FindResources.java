package ch12.mypackage;

import java.net.URL;
import java.io.IOException;

// Examples of different ways the function can find files or other
// resources. Note this function doesn't seem to be able to find any 
// resource closer to the root than its package. It's pretty useless actually. 
// From p. 414.

public class FindResources {
	public static void main(String[] args) throws IOException {
		// String pwd = System.getProperty("user.dir");
		// String classpath = System.getProperty("java.class.path");

		// absolute starting from any place in the classpath 
		// (not from the root of the file system)
		URL url = FindResources.class.getResource("/ch09/Clock.html");
		System.out.println("1st version: " + url);
		// relative to the class location
		url = FindResources.class.getResource("../src/ch09/Clock.html");
		if(url == null) {
			System.out.println("2nd Version: Cannot find");
		}
		// another resource relative to the class location
		url = FindResources.class.getResource("..\\io\\rot13InputStream.class");
		if(url == null) {
			System.out.println("3rd Version: Cannot find");
		}
	}
}
