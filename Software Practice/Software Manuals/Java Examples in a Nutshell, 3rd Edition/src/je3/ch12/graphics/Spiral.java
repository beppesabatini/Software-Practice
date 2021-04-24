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

import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.Rectangle;
import java.awt.Shape;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 387-391. This Shape
 * implementation represents a spiral curve.
 */
public class Spiral implements Shape {
	// The center of the spiral:
	double centerX, centerY;
	// The spiral starting point:
	double startRadius, startAngle;
	// The spiral ending point:
	double endRadius, endAngle;
	// The bigger of the two radii:
	double outerRadius;
	// The angle direction is 1 if the angle increases, -1 otherwise:
	int angleDirection;

	/*
	 * It's hard to do contains() and intersects() tests on a spiral, so we do them
	 * on this circular approximation of the spiral. This is not an ideal solution,
	 * and is only a good approximation for "tight" spirals.
	 */
	Shape approximation;

	/**
	 * The Spiral() constructor. It takes arguments for the center of the shape, the
	 * start point, and the end point. The start and end points are specified in
	 * terms of angle and radius. The spiral curve is formed by varying the angle
	 * and radius smoothly between the two end points.
	 */
	public Spiral(double centerX, double centerY, double startRadius, double startAngle, double endRadius,
			double endAngle) {
		// Save the parameters that describe the spiral.
		this.centerX = centerX;
		this.centerY = centerY;
		this.startRadius = startRadius;
		this.startAngle = startAngle;
		this.endRadius = endRadius;
		this.endAngle = endAngle;

		// Figure out the maximum radius, and the spiral direction.
		this.outerRadius = Math.max(startRadius, endRadius);
		if (startAngle < endAngle) {
			angleDirection = 1;
		} else {
			angleDirection = -1;
		}

		if ((startRadius < 0) || (endRadius < 0)) {
			throw new IllegalArgumentException("Spiral radii must be >= 0");
		}

		/*
		 * Here approximate the inside of the spiral by using a circle of about the same
		 * size.
		 */
		double zeroX = centerX - outerRadius;
		double zeroY = centerY - outerRadius;
		double width = outerRadius * 2;
		double height = outerRadius * 2;
		approximation = new Ellipse2D.Double(zeroX, zeroY - outerRadius, width, height);
	}

	/**
	 * The bounding box of a Spiral is the same as the bounding box of the
	 * approximation circle (the previous), with the same center and the maximum
	 * radius.
	 */
	@Override
	public Rectangle getBounds() {
		int zeroX = (int) (centerX - outerRadius);
		int zeroY = (int) (centerY - outerRadius);
		int width = (int) outerRadius * 2;
		int height = (int) outerRadius * 2;
		return new Rectangle(zeroX, zeroY, width, height);
	}

	/** Same as getBounds(), but with floating-point coordinates. */
	@Override
	public Rectangle2D getBounds2D() {
		return new Rectangle2D.Double(centerX - outerRadius, centerY - outerRadius, outerRadius * 2, outerRadius * 2);
	}

	/**
	 * These methods use a circle approximation to determine whether a point or
	 * rectangle is inside the spiral. We could be more clever than this.
	 */
	public boolean contains(Point2D point2D) {
		return approximation.contains(point2D);
	}

	public boolean contains(Rectangle2D rectangle2D) {
		return approximation.contains(rectangle2D);
	}

	@Override
	public boolean contains(double x, double y) {
		return approximation.contains(x, y);
	}

	@Override
	public boolean contains(double x, double y, double w, double h) {
		return approximation.contains(x, y, w, h);
	}

	/**
	 * These methods determine whether the specified rectangle intersects the
	 * spiral. We use our circle approximation. The Shape interface explicitly
	 * allows approximations to be used for these methods.
	 */
	@Override
	public boolean intersects(double x, double y, double w, double h) {
		return approximation.intersects(x, y, w, h);
	}

	@Override
	public boolean intersects(Rectangle2D rectangle2D) {
		return approximation.intersects(rectangle2D);
	}

	/**
	 * This method is the heart of all Shape implementations. It returns a
	 * PathIterator that describes the shape in terms of the line and curve segments
	 * that comprise it. Our iterator implementation approximates the shape of the
	 * spiral using line segments only. We pass in a "flatness" argument that tells
	 * it how good the approximation must be. (smaller numbers mean a better
	 * approximation).
	 */
	@Override
	public PathIterator getPathIterator(AffineTransform affineTransform) {
		return new SpiralIterator(affineTransform, outerRadius / 500.0);
	}

	/**
	 * Return a PathIterator that describes the shape in terms of line segments
	 * only, with an approximation quality specified by flatness.
	 */
	@Override
	public PathIterator getPathIterator(AffineTransform affineTransform, double flatness) {
		return new SpiralIterator(affineTransform, flatness);
	}

	/**
	 * This inner class is the PathIterator for our Spiral shape. For simplicity, it
	 * does not describe the spiral path in terms of Bezier curve segments, but
	 * simply approximates it with line segments. The flatness property specifies
	 * how far the approximation is allowed to deviate from the true curve.
	 */
	class SpiralIterator implements PathIterator {
		// How to transform generated coordinates:
		AffineTransform transform;
		// How close an approximation:
		double flatness;
		// Current angle:
		double angle = startAngle;
		// Current radius:
		double radius = startRadius;
		// Are we done yet?
		boolean done = false;

		/** A simple constructor. Just store the parameters into fields. */
		public SpiralIterator(AffineTransform transform, double flatness) {
			this.transform = transform;
			this.flatness = flatness;
		}

		/**
		 * All PathIterators have a "winding rule" that helps to specify what is the
		 * inside of a area and what is the outside. If you fill a spiral (which you're
		 * not supposed to do) the winding rule returned here yields better results than
		 * the alternative, WIND_EVEN_ODD.
		 */
		public int getWindingRule() {
			return WIND_NON_ZERO;
		}

		/** Returns true if the entire path has been iterated. */
		public boolean isDone() {
			return done;
		}

		/**
		 * Store the coordinates of the current segment of the path into the specified
		 * array, and return the type of the segment. Use trigonometry to compute the
		 * coordinates based on the current angle and radius. If this was the first
		 * point, return a MOVETO segment, otherwise return a LINETO segment. Also,
		 * check to see if we're done.
		 */
		public int currentSegment(float[] coords) {
			// Given the radius and the angle, compute the point coordinates.
			coords[0] = (float) (centerX + radius * Math.cos(angle));
			coords[1] = (float) (centerY - radius * Math.sin(angle));

			// If a transform was specified, use it on the coordinates.
			if (transform != null) {
				transform.transform(coords, 0, coords, 0, 1);
			}

			// If we've reached the end of the spiral remember that fact.
			if (angle == endAngle) {
				done = true;
			}

			// If this is the first point in the spiral, then move to it.
			if (angle == startAngle) {
				return SEG_MOVETO;
			}

			// Otherwise, draw a line from the previous point to this one.
			return SEG_LINETO;
		}

		/** This method is the same as above, except using double values. */
		public int currentSegment(double[] coords) {
			coords[0] = centerX + radius * Math.cos(angle);
			coords[1] = centerY - radius * Math.sin(angle);
			if (transform != null) {
				transform.transform(coords, 0, coords, 0, 1);
			}
			if (angle == endAngle) {
				done = true;
			}
			if (angle == startAngle) {
				return SEG_MOVETO;
			} else {
				return SEG_LINETO;
			}
		}

		/**
		 * Move on to the next segment of the path. Compute the angle and radius values
		 * for the next point in the spiral.
		 */
		public void next() {
			if (done) {
				return;
			}

			/**
			 * First, figure out how much to increment the angle. This depends on the
			 * required flatness, and also upon the current radius. When drawing a circle
			 * (which we'll use as our approximation) of radius r, we can maintain a
			 * flatness f by using angular increments given by this formula:
			 * 
			 * <pre>
			 * a = acos(2 * (flatness / radius) * (flatness / flatness) - 4 * (flatness / flatness) + 1)
			 * </pre>
			 * 
			 * Use this formula to figure out how much we can increment the angle for the
			 * next segment. Note that the formula does not work well for very small radii,
			 * so we special case those.
			 */
			double x = flatness / radius;
			if (Double.isNaN(x) || (x > .1)) {
				angle += Math.PI / 4 * angleDirection;
			} else {
				double y = 2 * x * x - 4 * x + 1;
				angle += Math.acos(y) * angleDirection;
			}

			// Check whether we've gone past the end of the spiral:
			if ((angle - endAngle) * angleDirection > 0) {
				angle = endAngle;
			}

			/*
			 * Now that we know the new angle, we can use interpolation to figure out what
			 * the corresponding radius is.
			 */
			double fractionComplete = (angle - startAngle) / (endAngle - startAngle);
			radius = startRadius + (endRadius - startRadius) * fractionComplete;
		}
	}
	public static class Demo {
		public static void main(String[] args) {
			String[] graphicsExampleNames = { "je3.ch12.graphics.Shapes" };
			GraphicsExampleFrame.main(graphicsExampleNames);
			System.out.println("Demo of Spiral via Shapes.");
		}
	}
}
