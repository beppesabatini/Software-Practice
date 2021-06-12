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
package je3.ch16.applet;

import java.applet.Applet;
import java.awt.Graphics;

/**
 * From Java Examples in a Nutshell, 3rd Edition, p. 490. This applet just says
 * "Hello World".
 */
public class FirstApplet extends Applet {

	private static final long serialVersionUID = -8012235657946902647L;

	// This method displays the applet:
	@Override
	public void paint(Graphics graphics) {
		graphics.drawString("Hello World", 25, 50);
	}
}