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

import java.awt.Component;
import java.awt.Graphics2D;

/**
 * From Java Examples in a Nutshell, 3rd Edition, p. 364. This interface defines
 * the methods that must be implemented by an object that is to be displayed by
 * the GraphicsExampleFrame object.
 */
public interface GraphicsExample {
	// Return the example name:
	public String getName();

	// Return its width:
	public int getWidth();

	// Return its height:
	public int getHeight();

	// Draw the example:
	public void draw(Graphics2D graphics, Component component);
}
