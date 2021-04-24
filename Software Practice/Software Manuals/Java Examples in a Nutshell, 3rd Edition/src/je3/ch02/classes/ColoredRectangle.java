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

import java.awt.Color;
import java.awt.Graphics;

/**
 * From Java Examples in a Nutshell, 3rd Edition, p. 28. This class subclasses
 * DrawableRectangle and adds colors to the rectangle it draws.
 */
public class ColoredRectangle extends DrawableRectangle {
	// These are new fields defined by this class.
	// x1, y1, x2, and y2 are inherited from our super-superclass, Rectangle.
	protected Color border, fill;

	/**
	 * This constructor uses super() to invoke the superclass constructor, and also
	 * does some initialization of its own.
	 **/
	public ColoredRectangle(int x1, int y1, int x2, int y2, Color border, Color fill) {
		super(x1, y1, x2, y2);
		this.border = border;
		this.fill = fill;
	}

	/**
	 * This method overrides the draw() method of our superclass so that it can make
	 * use of the colors that have been specified.
	 **/
	public void draw(Graphics g) {
		g.setColor(fill);
		g.fillRect(x1, y1, (x2 - x1), (y2 - y1));
		g.setColor(border);
		g.drawRect(x1, y1, (x2 - x1), (y2 - y1));
	}
}
