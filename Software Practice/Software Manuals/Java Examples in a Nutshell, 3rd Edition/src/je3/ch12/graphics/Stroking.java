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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 371-372. A demonstration
 * of how Stroke objects work. These are Strokes as in pen strokes or brush
 * strokes.
 */
public class Stroking implements GraphicsExample {
	// Size of our example:
	static final int WIDTH = 725, HEIGHT = 250;

	// From GraphicsExample:
	@Override
	public String getName() {
		return "Stroking";
	}

	// From GraphicsExample:
	@Override
	public int getWidth() {
		return WIDTH;
	}

	// From GraphicsExample:
	@Override
	public int getHeight() {
		return HEIGHT;
	}

	/** Draw the example. */
	@Override
	public void draw(Graphics2D graphics2D, Component component) {
		// Create the shape we'll work with. See convenience method below.
		Shape pentagon = createRegularPolygon(5, 75);

		// Set up basic drawing attributes.
		// Draw in black:
		graphics2D.setColor(Color.black);
		// Use thin lines:
		graphics2D.setStroke(new BasicStroke(1.0f));
		// Basic small font:
		graphics2D.setFont(new Font("SansSerif", Font.PLAIN, 12));

		// Move to position:
		graphics2D.translate(100, 100);
		// Outline the shape:
		graphics2D.draw(pentagon);
		// Draw the caption:
		graphics2D.drawString("The shape", -30, 90);

		// Move over to the next shape:
		graphics2D.translate(175, 0);
		// Fill the shape:
		graphics2D.fill(pentagon);
		// Another caption:
		graphics2D.drawString("The filled shape", -50, 90);

		// Now use a Stroke object to create a "stroked shape" for our shape.
		BasicStroke wideline = new BasicStroke(10.0f);
		Shape outline = wideline.createStrokedShape(pentagon);

		// Move over to the next shape:
		graphics2D.translate(175, 0);
		// Draw the stroked shape:
		graphics2D.draw(outline);
		// Draw the caption:
		graphics2D.drawString("A Stroke creates", -50, 90);
		graphics2D.drawString("a new shape", -35, 105);

		// Move over:
		graphics2D.translate(175, 0);
		// Fill the stroked shape:
		graphics2D.fill(outline);
		// Draw the caption:
		graphics2D.drawString("Filling the new shape", -65, 90);
		graphics2D.drawString("outlines the old one", -65, 105);
	}

	/*
	 * A convenience method to define a regular polygon. This returns a shape that
	 * represents a regular polygon with the specified radius and number of sides,
	 * and which is centered at the origin.
	 */
	public Shape createRegularPolygon(int numsides, int radius) {
		Polygon polygon = new Polygon();
		// The angle between vertices:
		double angle = 2 * Math.PI / numsides;
		for (int i = 0; i < numsides; i++) {
			// Compute location of each vertex:
			polygon.addPoint((int) (radius * Math.sin(angle * i)), (int) (radius * -Math.cos(angle * i)));
		}
		return polygon;
	}

	public static class Demo {
		public static void main(String[] args) {
			String[] graphicsExampleNames = { "je3.ch12.graphics.Stroking" };
			GraphicsExampleFrame.main(graphicsExampleNames);
			System.out.println("Demo of Stroking.");
		}
	}
}
