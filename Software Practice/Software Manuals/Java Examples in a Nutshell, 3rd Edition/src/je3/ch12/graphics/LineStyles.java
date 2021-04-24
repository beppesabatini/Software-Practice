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
import java.awt.geom.GeneralPath;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 369-371. A demonstration
 * of Java2D line styles.
 */
public class LineStyles implements GraphicsExample {
	// From GraphicsExample:
	@Override
	public String getName() {
		return "LineStyles";
	}

	// From GraphicsExample:
	@Override
	public int getWidth() {
		return 450;
	}

	// From GraphicsExample:
	@Override
	public int getHeight() {
		return 180;
	}

	// X coordinates of our shape:
	int[] xPoints = new int[] { 0, 50, 100 };
	// Y coordinates of our shape:
	int[] yPoints = new int[] { 75, 0, 75 };

	/*
	 * Here are three different line styles we will demonstrate. They are thick
	 * lines, with different cap and join styles.
	 */
	static Stroke[] linestyles = initLinestyles();

	static Stroke[] initLinestyles() {
		Stroke[] linestyles = new Stroke[3];
		linestyles[0] = new BasicStroke(25.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
		linestyles[1] = new BasicStroke(25.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER);
		linestyles[2] = new BasicStroke(25.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		return (linestyles);
	}

	/* Another line style--a 2 pixel-wide dot-dashed line. */
	float lineWidth = 2.0f;
	// The corner for a join is squared off.
	int capButt = BasicStroke.CAP_BUTT;
	// The edges for a join are beveled for a smooth fit.
	int joinBevel = BasicStroke.JOIN_BEVEL;
	// The limit to trim the meter join.
	float meterLimit = 1.0f;
	// The dash pattern for the lines: on 8, off 3, on 2, off 3.
	// Like so: -------- -- -------- -- -------- --
	float[] dashPattern = { 8.0f, 3.0f, 2.0f, 3.0f };
	// The starting point or initial phase for the rotation through the dash
	// pattern.
	float dashPhase = 0.0f;

	Stroke thindashed = new BasicStroke(lineWidth, capButt, joinBevel, meterLimit, dashPattern, dashPhase);

	// The labels to appear in the diagram, and the font to use to display them.
	Font font = new Font("Helvetica", Font.BOLD, 12);
	String[] capNames = new String[] { "Cap: Butt", "Cap: Square", "Cap: Round" };
	String[] joinNames = new String[] { "Join: Bevel", "Join: Miter", "Join: Round" };

	/** This method draws the example figure. */
	public void draw(Graphics2D g, Component c) {
		// Use anti-aliasing to avoid "jaggies" in the lines.
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Define the shape to draw.
		GeneralPath shape = new GeneralPath();
		// Start at point 0:
		shape.moveTo(xPoints[0], yPoints[0]);
		// Draw a line to point 1:
		shape.lineTo(xPoints[1], yPoints[1]);
		// ... and then on to point 2:
		shape.lineTo(xPoints[2], yPoints[2]);

		// Move the origin to the right and down, creating a margin.
		g.translate(20, 40);

		// Now loop, drawing our shape with each of the three defined line styles.
		for (int i = 0; i < linestyles.length; i++) {
			// Draw a gray line:
			g.setColor(Color.gray);
			// Select the line style to use:
			g.setStroke(linestyles[i]);
			// Draw the shape:
			g.draw(shape);
			// Now use black...
			g.setColor(Color.black);
			// ...and the thin dashed line...
			g.setStroke(thindashed);
			// ...and draw the shape again.
			g.draw(shape);

			/*
			 * Highlight the location of the vertexes of the shape by adding little squares
			 * to the ends of the dotted line segments. This accentuates the cap and join
			 * styles we're demonstrating.
			 */
			for (int j = 0; j < xPoints.length; j++) {
				g.fillRect(xPoints[j] - 2, yPoints[j] - 2, 5, 5);
			}
			// Label the cap style:
			g.drawString(capNames[i], 20, 105);
			// Label the join style:
			g.drawString(joinNames[i], 20, 120);

			// Move over to the right before looping again.
			g.translate(150, 0);
		}
	}

	public static class Demo {
		public static void main(String[] args) {
			String[] graphicsExampleNames = { "je3.ch12.graphics.LineStyles" };
			GraphicsExampleFrame.main(graphicsExampleNames);
			System.out.println("Demo of LineStyles.");
		}
	}
}
