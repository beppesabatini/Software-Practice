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
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 367-369. A demonstration
 * of Java2D transformations.
 */
public class Transforms implements GraphicsExample {
	// From GraphicsExample:
	@Override
	public String getName() {
		return "Transforms";
	}

	// From GraphicsExample:
	@Override
	public int getWidth() {
		return 750;
	}

	// From GraphicsExample:
	@Override
	public int getHeight() {
		return 250;
	}

	// The shape to draw:
	Shape shape;
	// The ways to transform it:
	AffineTransform[] transforms;
	// Labels for each transform:
	String[] transformLabels;

	/**
	 * This constructor sets up the Shape and AffineTransform objects we need.
	 */
	public Transforms() {
		// Create a shape to draw:
		GeneralPath path = new GeneralPath();
		path.append(new Line2D.Float(0.0f, 0.0f, 0.0f, 100.0f), false);
		path.append(new Line2D.Float(-10.0f, 50.0f, 10.0f, 50.0f), false);
		path.append(new Polygon(new int[] { -5, 0, 5 }, new int[] { 5, 0, 5 }, 3), false);
		// Remember this shape:
		this.shape = path;

		// Set up some transforms to alter the shape.
		this.transforms = new AffineTransform[6];
		// 1) Tthe identity transform:
		transforms[0] = new AffineTransform();
		// 2) A scale transform: 3/4 size
		transforms[1] = AffineTransform.getScaleInstance(0.75, 0.75);
		// 3) A shearing transform:
		transforms[2] = AffineTransform.getShearInstance(-0.4, 0.0);
		// 4) A 30 degree clockwise rotation about the origin of the shape:
		transforms[3] = AffineTransform.getRotateInstance(Math.PI * 2 / 12);
		// 5) A 180 degree rotation about the midpoint of the shape:
		transforms[4] = AffineTransform.getRotateInstance(Math.PI, 0.0, 50.0);
		// 6) A combination transform:
		transforms[5] = AffineTransform.getScaleInstance(0.5, 1.5);
		transforms[5].shear(0.0, 0.4);
		// For 90 degrees:
		transforms[5].rotate(Math.PI / 2, 0.0, 50.0);

		// Define names for the transforms:
		transformLabels = new String[] { "identity", "scale", "shear", "rotate", "rotate", "combo" };
	}

	/** Draw the defined shape and label, using each transform */
	@Override
	public void draw(Graphics2D g, Component c) {
		// Define basic drawing attributes
		g.setColor(Color.black);
		// Lines two pixels wide:
		g.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL));
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Now draw each shape once using the transforms we've defined.
		for (int i = 0; i < transforms.length; i++) {
			// Save the current state:
			AffineTransform save = g.getTransform();
			// Move the starting point:
			g.translate(i * 125 + 50, 50);
			// Apply a transform defined above:
			g.transform(transforms[i]);
			// Draw the shape:
			g.draw(shape);
			// Draw the label:
			g.drawString(transformLabels[i], -25, 125);
			// Draw the containing box:
			g.drawRect(-40, -10, 80, 150);
			// Restore the shape to its original saved state:
			g.setTransform(save);
		}
	}

	public static class Demo {
		public static void main(String[] args) {
			String[] graphicsExampleNames = { "je3.ch12.graphics.Transforms" };
			GraphicsExampleFrame.main(graphicsExampleNames);
			System.out.println("Demo of Transforms.");
		}
	}
}
