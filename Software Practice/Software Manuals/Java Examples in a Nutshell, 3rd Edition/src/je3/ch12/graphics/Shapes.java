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
import java.awt.geom.Arc2D;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 365-367. A demonstration
 * of Java2D shapes.
 */
public class Shapes implements GraphicsExample {
	// Size of our example:
	static final int WIDTH = 725, HEIGHT = 250;

	// From GraphicsExample:
	@Override
	public String getName() {
		return "Shapes";
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

	Shape[] shapes = new Shape[] {
			// A straight line segment:
			new Line2D.Float(0, 0, 100, 100),
			// A quadratic Bezier curve, with two end points and one control point:
			new QuadCurve2D.Float(0, 0, 80, 15, 100, 100),
			// A cubic Bezier curve--two end points and two control points.
			new CubicCurve2D.Float(0, 0, 80, 15, 10, 90, 100, 100),
			// A 120 degree portion of an ellipse:
			new Arc2D.Float(-30, 0, 100, 100, 60, -120, Arc2D.OPEN),
			// A 120 degree portion of an ellipse, closed with a chord:
			new Arc2D.Float(-30, 0, 100, 100, 60, -120, Arc2D.CHORD),
			// A 120 degree pie slice of an ellipse:
			new Arc2D.Float(-30, 0, 100, 100, 60, -120, Arc2D.PIE),
			// An ellipse:
			new Ellipse2D.Float(0, 20, 100, 60),
			// A rectangle:
			new Rectangle2D.Float(0, 20, 100, 60),
			// A rectangle with rounded corners:
			new RoundRectangle2D.Float(0, 20, 100, 60, 15, 15),
			// A triangle:
			new Polygon(new int[] { 0, 0, 100 }, new int[] { 20, 80, 80 }, 3),
			// A random polygon, initialized in code below.
			null,
			// A spiral--an instance of a custom Shape implementation:
			new Spiral(50, 50, 5, 0, 50, 4 * Math.PI), };

	{ // Initialize the null shape above as a Polygon with random points.
		Polygon p = new Polygon();
		for (int i = 0; i < 10; i++) {
			p.addPoint((int) (100 * Math.random()), (int) (100 * Math.random()));
		}
		shapes[10] = p;
	}

	/* These are the labels for each of the shapes. */
	String[] labels = new String[] { //
			"Line2D", //
			"QuadCurve2D", //
			"CubicCurve2D", //
			"Arc2D (OPEN)", //
			"Arc2D (CHORD)", //
			"Arc2D (PIE)", //
			"Ellipse2D", //
			"Rectangle2D", //
			"RoundRectangle2D", //
			"Polygon", //
			"Polygon (random)", //
			"Spiral" //
	};

	/** Draw the example. */
	@Override
	public void draw(Graphics2D graphics2D, Component c) {
		/*
		 * Set basic drawing attributes.
		 */
		// Select the font:
		graphics2D.setFont(new Font("SansSerif", Font.PLAIN, 10));
		// The line is two pixels wide:
		graphics2D.setStroke(new BasicStroke(2.0f));
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// Set the margins by starting at X 10, Y 10:
		graphics2D.translate(10, 10);

		// Loop through each shape:
		for (int i = 0; i < shapes.length; i++) {
			// Set a color:
			graphics2D.setColor(Color.yellow);
			// Fill the shapes with it:
			graphics2D.fill(shapes[i]);
			// Switch to black:
			graphics2D.setColor(Color.black);
			// Outline the shape with it:
			graphics2D.draw(shapes[i]);
			// Label the shape:
			graphics2D.drawString(labels[i], 0, 110);
			// Move over for next shape:
			graphics2D.translate(120, 0);
			// Move down a row after 6 shapes:
			if ((i + 1) % 6 == 0) {
				graphics2D.translate(-6 * 120, 120);
			}
		}
	}

	public static class Demo {
		public static void main(String[] args) {
			String[] graphicsExampleNames = { "je3.ch12.graphics.Shapes" };
			GraphicsExampleFrame.main(graphicsExampleNames);
			System.out.println("Demo of Shapes.");
		}
	}
}
