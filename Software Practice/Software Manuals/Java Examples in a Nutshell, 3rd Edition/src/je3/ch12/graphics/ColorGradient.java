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
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 361-362. An applet that
 * demonstrates the Color class. In the development installation, this is
 * compiled with Java 8. To test or demo this, switch Workspace to "Software
 * Practice - Experimental".
 */
@SuppressWarnings("deprecation") // This class actually compiles and runs correctly when Java 8 is used.
public class ColorGradient extends Applet {

	private static final long serialVersionUID = -4166873872527633023L;

	// Start and end color of the gradient:
	Color startColor, endColor;
	// The font we'll use:
	Font bigFont;

	/**
	 * Get the gradient start and end colors as applet parameter values, and parse
	 * them using Color.decode(). If they are malformed, use white.
	 */
	public void init() {
		try {
			startColor = Color.decode(getParameter("startColor"));
			endColor = Color.decode(getParameter("endColor"));
		} catch (NumberFormatException e) {
			startColor = endColor = Color.white;
		}
		bigFont = new Font("Helvetica", Font.BOLD, 72);
	}

	/** Draw the applet. The interesting code is in fillGradient() below */
	public void paint(Graphics g) {
		// Display the gradient:
		fillGradient(this, g, startColor, endColor);
		// Set the font:
		g.setFont(bigFont);
		// A light blue:
		g.setColor(new Color(100, 100, 200));
		// Now draw something interesting.
		g.drawString("Gradiant!", 100, 100);
	}

	/**
	 * Draw a color gradient from the top of the specified component to the bottom.
	 * Start with the start color and change smoothly to the end.
	 */
	public void fillGradient(Component c, Graphics g, Color start, Color end) {
		// How big is the component?
		Rectangle bounds = this.getBounds();
		/*
		 * Get the red, green, and blue components of the start and end colors as floats
		 * between 0.0 and 1.0. Note that the Color class also works with int values
		 * between 0 and 255
		 */
		float red01 = start.getRed() / 255.0f;
		float green01 = start.getGreen() / 255.0f;
		float blue01 = start.getBlue() / 255.0f;
		float red02 = end.getRed() / 255.0f;
		float green02 = end.getGreen() / 255.0f;
		float blue02 = end.getBlue() / 255.0f;
		// Figure out how much each component should change at each y value
		float deltaRed = (red02 - red01) / bounds.height;
		float deltaGreen = (green02 - green01) / bounds.height;
		float deltaBlue = (blue02 - blue01) / bounds.height;

		// Now loop once for each row of pixels in the component
		for (int y = 0; y < bounds.height; y++) {
			// Set the color of the row:
			g.setColor(new Color(red01, green01, blue01));
			// Draw the row:
			g.drawLine(0, y, bounds.width - 1, y);
			// Increment color components:
			red01 += deltaRed;
			green01 += deltaGreen;
			blue01 += deltaBlue;
		}
	}
}
