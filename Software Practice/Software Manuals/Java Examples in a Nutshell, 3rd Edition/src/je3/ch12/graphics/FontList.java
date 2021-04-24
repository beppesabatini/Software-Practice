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
package je3.ch12.graphics;

import java.applet.Applet;
import java.awt.Font;
import java.awt.Graphics;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 359-360. An applet that
 * displays the standard fonts and styles available in Java 1.1 (before Swing
 * was added). In the development installation, this is compiled with Java 8. To
 * test or demo this, switch Workspace to "Software Practice - Experimental".
 */
@SuppressWarnings("deprecation") // This class actually compiles and runs correctly when Java 8 is used.
public class FontList extends Applet {

	private static final long serialVersionUID = -4561808936275412100L;

	public void start() {
		this.setSize(300, 250);
	}

	/*
	 * The available font families. Java is actually confusing the concept of a font
	 * (such as Arial or Helvetica) with the concept of a font family (such as
	 * family members Helvetica, Helvetica Bold, Helvetica Italic, and others).
	 */
	String[] families = { //
			"Serif", // This displays as "TimesRoman" in many Java contexts.
			"SansSerif", // This displays as "Helvetica" in many Java contexts.
			"Monospaced" }; // This displays as "Courier" in many Java contexts.

	// The available font styles and names for each one:
	int[] styles = { Font.PLAIN, Font.ITALIC, Font.BOLD, Font.ITALIC + Font.BOLD };

	String[] stylenames = { "Plain", "Italic", "Bold", "Bold Italic" };

	// Draw the applet.
	public void paint(Graphics g) {
		// For each font family:
		for (int f = 0; f < families.length; f++) {
			// For each font style:
			for (int s = 0; s < styles.length; s++) {
				// Create a font:
				Font font = new Font(families[f], styles[s], 18);
				// Set the font:
				g.setFont(font);
				// Create the display name:
				String name = families[f] + " " + stylenames[s];
				// Draw the display name:
				g.drawString(name, 20, (f * 4 + s + 1) * 20);
			}
		}
	}
}
