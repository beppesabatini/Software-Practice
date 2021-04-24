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
package je3.ch02.classes;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 25-26. This class
 * demonstrates how you might use the Rectangle class
 */
public class RectangleTest {
	public static void main(String[] args) {
		// Create Rectangle objects:
		Rectangle r1 = new Rectangle(1, 1, 4, 4);
		Rectangle r2 = new Rectangle(2, 3, 5, 6);
		// Invoke Rectangle methods:
		Rectangle union = r1.union(r2);
		Rectangle intersection = r2.intersection(r1);

		// Use Rectangle fields and invoke a method.
		if (union.isInside(r2.x1, r2.y1)) {
			System.out.println("(" + r2.x1 + "," + r2.y1 + ") is inside the union");
		}

		// These lines implicitly call the Rectangle.toString() method:
		System.out.println(r1 + " union " + r2 + " = " + union);
		System.out.println(r1 + " intersect " + r2 + " = " + intersection);
	}
}
