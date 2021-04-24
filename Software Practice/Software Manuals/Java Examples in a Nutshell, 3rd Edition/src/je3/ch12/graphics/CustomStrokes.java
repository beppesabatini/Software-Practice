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
import java.awt.font.GlyphVector;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 397-400. A demonstration
 * of writing custom Stroke classes. It is not possible to implement a one-click
 * demo for CustomStrokes at this time, so users should launch the Run
 * Configuration "CustomStrokes".
 */
public class CustomStrokes implements GraphicsExample {
	// Size of our example:
	static final int WIDTH = 750, HEIGHT = 200;

	// From GraphicsExample:
	@Override
	public String getName() {
		return "Custom Strokes";
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

	/*
	 * These are the various stroke objects we'll demonstrate.
	 */
	Stroke[] strokes = new Stroke[] {
			// The standard, predefined stroke (the outlined 'B'):
			new BasicStroke(4.0f),
			// A Stroke that does nothing (the solid 'B'):
			new NullStroke(),
			// A Stroke that strokes twice (the doubly outlined 'B'):
			new DoubleStroke(8.0f, 2.0f),
			// Shows the vertices & control points (the speckled 'B'):
			new ControlPointsStroke(2.0f),
			// Perturbs the shape before stroking (the shaky 'B'):
			new SloppyStroke(2.0f, 3.0f) };

	/** Draw the example. */
	@Override
	public void draw(Graphics2D graphics2D, Component c) {
		// Get a shape to work with. Here we'll use the letter 'B'.
		Font font = new Font("Serif", Font.BOLD, 200);
		GlyphVector glyphVector = font.createGlyphVector(graphics2D.getFontRenderContext(), "B");
		Shape shape = glyphVector.getOutline();

		// Set drawing attributes and starting position.
		graphics2D.setColor(Color.black);
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2D.translate(10, 175);

		// Draw the shape once with each stroke.
		for (int i = 0; i < strokes.length; i++) {
			// Set the stroke:
			graphics2D.setStroke(strokes[i]);
			// Draw the shape:
			graphics2D.draw(shape);
			// move to the right:
			graphics2D.translate(140, 0);
		}
	}
}

/**
 * This Stroke implementation does nothing. Its createStrokedShape() method
 * returns an unmodified shape. Thus, drawing a shape with this Stroke is the
 * same as filling that shape! This draws the second 'B', the solid one.
 */
class NullStroke implements Stroke {
	@Override
	public Shape createStrokedShape(Shape shape) {
		return shape;
	}
}

/**
 * This Stroke implementation applies a BasicStroke to a shape twice. If you
 * draw with this Stroke, then instead of outlining the shape, you're outlining
 * the outline of the shape. This draws the third 'B', the double-outlined
 * version.
 */
class DoubleStroke implements Stroke {
	// The two strokes to use:
	BasicStroke stroke1, stroke2;

	public DoubleStroke(float width1, float width2) {
		// Constructor arguments specify the line widths for the strokes.
		stroke1 = new BasicStroke(width1);
		stroke2 = new BasicStroke(width2);
	}

	@Override
	public Shape createStrokedShape(Shape shape) {
		// Use the first stroke to create an outline of the shape.
		Shape outline = stroke1.createStrokedShape(shape);
		/*
		 * Use the second stroke to create an outline of that outline. It is this
		 * outline of the outline that will be filled in.
		 */
		return stroke2.createStrokedShape(outline);
	}
}

/**
 * This Stroke implementation strokes the shape using a thin line, and also
 * displays the end points and Bezier curve control points of all the line and
 * curve segments that make up the shape. The radius argument to the constructor
 * specifies the size of the control point markers. Note the use of PathIterator
 * to break the shape down into its segments, and of GeneralPath to build up the
 * stroked shape. This draws the fourth 'B', the speckled one.
 */
class ControlPointsStroke implements Stroke {
	// How big the control point markers should be:
	float radius;

	public ControlPointsStroke(float radius) {
		this.radius = radius;
	}

	/**
	 * The PathIterator specifies line segments in an array of six floats, in which
	 * the segments are specified by one, two or three points. This is how the array
	 * is interpreted:
	 * 
	 * <pre>
	 * 0) x1
	 * 1) y1
	 * 2) x2 [when needed for quadratic curves]
	 * 3) y2 [when needed for quadratic curves]
	 * 4) x3 [when needed for quadratic or cubic curves]
	 * 5) y3 [when needed for quadratic or cubic curves]]
	 * </pre>
	 * 
	 * The types of the segments, on which the switch statements below switch, is
	 * also stored.
	 */
	@Override
	public Shape createStrokedShape(Shape shape) {
		/*
		 * Start off by stroking the shape with a thin line. Store the resulting shape
		 * in a GeneralPath object so we can add to it.
		 */
		GeneralPath strokedShape = new GeneralPath(new BasicStroke(1.0f).createStrokedShape(shape));

		/*
		 * Use a PathIterator object to iterate through each of the line and curve
		 * segments of the shape. For each one, mark the end point and control points
		 * (if any) by adding a rectangle to the GeneralPath.
		 */
		float[] coordinates = new float[6];
		for (PathIterator pathIterator = shape.getPathIterator(null); !pathIterator.isDone(); pathIterator.next()) {
			int segmentType = pathIterator.currentSegment(coordinates);
			switch (segmentType) {
				case PathIterator.SEG_CUBICTO:
					/*
					 * A cubic (power of three) parametric curve. Control falls through to the next
					 * case, there is no "break" statement:
					 */
					markPoint(strokedShape, coordinates[4], coordinates[5]);
				case PathIterator.SEG_QUADTO:
					/*
					 * A quadratic (power of two) parametric curve. Control falls through to the
					 * next case, there is no "break" statement:
					 */
					markPoint(strokedShape, coordinates[2], coordinates[3]);
				case PathIterator.SEG_MOVETO:
				case PathIterator.SEG_LINETO:
					/*
					 * Move to this point, or draw a line to it. Control falls through to the next
					 * case, there is no "break" statement:
					 */
					markPoint(strokedShape, coordinates[0], coordinates[1]);
				case PathIterator.SEG_CLOSE:
					break;
			}
		}

		return strokedShape;
	}

	/** Add a small square centered at (x,y) to the specified path. */
	void markPoint(GeneralPath path, float x, float y) {
		// Begin a new sub-path:
		path.moveTo(x - radius, y - radius);
		// Add a line segment to it:
		path.lineTo(x + radius, y - radius);
		// Add a second line segment:
		path.lineTo(x + radius, y + radius);
		// And a third:
		path.lineTo(x - radius, y + radius);
		// Go back to last moveTo position:
		path.closePath();
	}
}

/**
 * This Stroke implementation randomly perturbs the line and curve segments that
 * make up a Shape, and then strokes that perturbed shape. It uses PathIterator
 * to loop through the Shape and GeneralPath to build up the modified shape.
 * Finally, it uses a BasicStroke to stroke the modified shape. The result is a
 * "sloppy" looking shape. This draws the fifth 'B', the wobbly-looking one.
 */
class SloppyStroke implements Stroke {
	BasicStroke stroke;
	float sloppiness;

	public SloppyStroke(float width, float sloppiness) {
		// Used to stroke modified shape:
		this.stroke = new BasicStroke(width);
		// How sloppy should we be?
		this.sloppiness = sloppiness;
	}

	@Override
	public Shape createStrokedShape(Shape shape) {
		// Start with an empty shape:
		GeneralPath newshape = new GeneralPath();

		/*
		 * Iterate through the specified shape, perturb its coordinates, and use them to
		 * build up the new shape.
		 */
		//
		float[] coordinates = new float[6];
		for (PathIterator pathIterator = shape.getPathIterator(null); !pathIterator.isDone(); pathIterator.next()) {
			int type = pathIterator.currentSegment(coordinates);
			switch (type) {
				case PathIterator.SEG_MOVETO:
					// Move from the current position to this new position without drawing.
					perturb(coordinates, 2);
					newshape.moveTo(coordinates[0], coordinates[1]);
					break;
				case PathIterator.SEG_LINETO:
					// Draw a line from the current position to this new point.
					perturb(coordinates, 2);
					newshape.lineTo(coordinates[0], coordinates[1]);
					break;
				case PathIterator.SEG_QUADTO:
					/*
					 * Draw a quadratic curve (power of 2), inferred starting from the current
					 * point, and curving from the first to the second coordinates specified.
					 */
					perturb(coordinates, 4);
					newshape.quadTo(coordinates[0], coordinates[1], coordinates[2], coordinates[3]);
					break;
				case PathIterator.SEG_CUBICTO:
					/*
					 * Draw a cubic curve (power of three), inferred starting from the current
					 * point, and continuing through the first, second, and third points specified,
					 */
					perturb(coordinates, 6);
					newshape.curveTo(coordinates[0], coordinates[1], coordinates[2], coordinates[3], coordinates[4],
							coordinates[5]);
					break;
				case PathIterator.SEG_CLOSE:
					newshape.closePath();
					break;
			}
		}

		// Finally, draw the perturbed shape and return the result:
		return stroke.createStrokedShape(newshape);
	}

	/*
	 * Randomly modify the specified number of coordinates, by an amount specified
	 * by the sloppiness field.
	 */
	void perturb(float[] coordinates, int numberCoordinates) {
		for (int i = 0; i < numberCoordinates; i++) {
			coordinates[i] += (float) ((Math.random() * 2 - 1.0) * sloppiness);
		}
	}
}
