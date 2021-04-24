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
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.io.Externalizable;

import je3.ch11.gui.ScribblePane;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 392-396. This Shape
 * implementation represents a series of connected line segments. It is like a
 * Polygon, but is not closed. This class is used by the ScribblePane class from
 * the GUI chapter. It implements the Cloneable and Externalizable interfaces,
 * and thus the PolyLine can be used in the Drag-and-Drop examples in the Data
 * Transfer chapter.
 * <p/>
 * The PolyLine shape is used primarily by the ScribblePane class. When you
 * launch the Demo for PolyLine, you are actually launching the Demo for
 * ScribblePane, which should give PolyLine a good workout.
 */
public class PolyLine implements Shape, Cloneable, Externalizable {
	// The starting point of the PolyLine:
	float x0, y0;
	// How many line segments in this PolyLine:
	int numsegs;
	/**
	 * The x and y coordinates of the end point of each line segment are packed into
	 * a single array for simplicity: [x1,y1,x2,y2,...].
	 * <p>
	 * Note that these are relative to x0,y0.
	 */
	float[] coords;

	// Coordinates of our bounding box, relative to (x0, y0):
	float xmin = 0f, xmax = 0f, ymin = 0f, ymax = 0f;

	/*
	 * No arg constructor assumes an origin of (0,0). A no-arg constructor is
	 * required for the Externalizable interface
	 */
	public PolyLine() {
		this(0f, 0f);
	}

	// The constructor:
	public PolyLine(float x0, float y0) {
		// Record the starting point:
		setOrigin(x0, y0);
		// Note that we have no line segments, so far:
		numsegs = 0;
	}

	/** Set the origin of the PolyLine. Useful when moving it. */
	public void setOrigin(float x0, float y0) {
		this.x0 = x0;
		this.y0 = y0;
	}

	/** Add dx and dy to the origin */
	public void translate(float dx, float dy) {
		this.x0 += dx;
		this.y0 += dy;
	}

	/**
	 * Add a line segment to the PolyLine. Note that x and y are absolute
	 * coordinates, even though the implementation stores them relative to x0, y0;
	 */
	public void addSegment(float x, float y) {
		// Allocate or reallocate the coords[] array when necessary.
		if (coords == null) {
			coords = new float[32];
		}
		if (numsegs * 2 >= coords.length) {
			float[] newcoords = new float[coords.length * 2];
			System.arraycopy(coords, 0, newcoords, 0, coords.length);
			coords = newcoords;
		}

		// Convert from absolute to relative coordinates.
		x = x - x0;
		y = y - y0;

		// Store the data.
		coords[numsegs * 2] = x;
		coords[numsegs * 2 + 1] = y;
		numsegs++;

		// Enlarge the bounding box, if necessary.
		if (x > xmax) {
			xmax = x;
		} else if (x < xmin) {
			xmin = x;
		}
		if (y > ymax) {
			ymax = y;
		} else if (y < ymin) {
			ymin = y;
		}
	}

	/*------------------ The Shape Interface --------------------- */

	// Return floating-point bounding box.
	@Override
	public Rectangle2D getBounds2D() {
		return new Rectangle2D.Float(x0 + xmin, y0 + ymin, xmax - xmin, ymax - ymin);
	}

	// Return integer bounding box, rounded to outermost pixels.
	@Override
	public Rectangle getBounds() {
		int zeroX = (int) (x0 + xmin - 0.5f);
		int zeroY = (int) (y0 + ymin - 0.5f);
		int width = (int) (xmax - xmin + 0.5f);
		int height = (int) (xmax - xmin + 0.5f);

		return new Rectangle(zeroX, zeroY, width, height);
	}

	/*
	 * PolyLine shapes are open curves, with no interior. The Shape interface says
	 * that open curves should be implicitly closed for the purposes of insideness
	 * testing. For our purposes, however, we define PolyLine shapes to have no
	 * interior, and the contains() methods always return false.
	 */
	@Override
	public boolean contains(Point2D p) {
		return false;
	}

	@Override
	public boolean contains(Rectangle2D r) {
		return false;
	}

	@Override
	public boolean contains(double x, double y) {
		return false;
	}

	@Override
	public boolean contains(double x, double y, double w, double h) {
		return false;
	}

	/*
	 * The intersects methods simply test whether any of the line segments within a
	 * PolyLine intersects the given rectangle. Strictly speaking, the Shape
	 * interface requires us to also check whether the rectangle is entirely
	 * contained within the shape as well. But the contains() methods for this class
	 * always return false. <p> We might improve the efficiency of this method by
	 * first checking for intersection with the overall bounding box to rule out
	 * cases that aren't even close.
	 */
	@Override
	public boolean intersects(Rectangle2D r) {
		if (numsegs < 1) {
			return false;
		}
		float lastX = x0, lastY = y0;
		// Loop through the segments:
		for (int i = 0; i < numsegs; i++) {
			float x = coords[i * 2] + x0;
			float y = coords[i * 2 + 1] + y0;
			// See if this line segment intersects the rectangle.
			if (r.intersectsLine(x, y, lastX, lastY)) {
				return true;
			}
			// Otherwise move on to the next segment.
			lastX = x;
			lastY = y;
		}
		// No line segment intersected the rectangle.
		return false;
	}

	// This variant method is just defined in terms of the last.
	@Override
	public boolean intersects(double x, double y, double w, double h) {
		return intersects(new Rectangle2D.Double(x, y, w, h));
	}

	/*
	 * This is the key to the Shape interface; it tells Java2D how to draw the shape
	 * as a series of lines and curves. We use only lines.
	 */
	@Override
	public PathIterator getPathIterator(final AffineTransform transform) {
		return new PathIterator() {
			// Current segment:
			int curseg = -1;
			/*
			 * Copy the current segment for thread-safety, so we don't mess up if a segment
			 * is added while we're iterating.
			 */
			int numsegs = PolyLine.this.numsegs;

			public boolean isDone() {
				return curseg >= numsegs;
			}

			public void next() {
				curseg++;
			}

			// Get coordinates and type of the current segment as floats.
			public int currentSegment(float[] data) {
				int segtype;
				// The first time we're called:
				if (curseg == -1) {
					// Data is the origin point:
					data[0] = x0;
					data[1] = y0;
					// This will be returned as a moveto segment:
					segtype = SEG_MOVETO;
				} else {
					// Otherwise, the data is a segment end point.
					data[0] = x0 + coords[curseg * 2];
					data[1] = y0 + coords[curseg * 2 + 1];
					// Returned as a lineto segment:
					segtype = SEG_LINETO;
				}
				// If a transform was specified, transform point in place.
				if (transform != null) {
					transform.transform(data, 0, data, 0, 1);
				}
				return segtype;
			}

			// Same as last method, but use doubles.
			public int currentSegment(double[] data) {
				int segtype;
				// The first time we're called:
				if (curseg == -1) {
					// Data is the origin point:
					data[0] = x0;
					data[1] = y0;
					// This will be returned as a moveto segment:
					segtype = SEG_MOVETO;
				} else {
					// Otherwise, the data is a segment end point.
					data[0] = x0 + coords[curseg * 2];
					data[1] = y0 + coords[curseg * 2 + 1];
					// Returned as a lineto segment:
					segtype = SEG_LINETO;
				}
				if (transform != null) {
					transform.transform(data, 0, data, 0, 1);
				}
				return segtype;
			}

			// This only matters for closed shapes
			public int getWindingRule() {
				return WIND_NON_ZERO;
			}
		};
	}

	/*
	 * PolyLines never contain curves, so we can ignore the flatness limit and
	 * implement this method in terms of the one above.
	 */
	@Override
	public PathIterator getPathIterator(AffineTransform at, double flatness) {
		return getPathIterator(at);
	}

	/*------------------ Externalizable --------------------- */

	/**
	 * The following two methods implement the Externalizable interface. We use
	 * Externalizable instead of Serializable so that we have full control over the
	 * data format, and only write out the defined coordinates.
	 */
	@Override
	public void writeExternal(java.io.ObjectOutput out) throws java.io.IOException {
		out.writeFloat(x0);
		out.writeFloat(y0);
		out.writeInt(numsegs);
		for (int i = 0; i < numsegs * 2; i++) {
			out.writeFloat(coords[i]);
		}
	}

	@Override
	public void readExternal(java.io.ObjectInput in) throws java.io.IOException, ClassNotFoundException {
		this.x0 = in.readFloat();
		this.y0 = in.readFloat();
		this.numsegs = in.readInt();
		this.coords = new float[numsegs * 2];
		for (int i = 0; i < numsegs * 2; i++) {
			coords[i] = in.readFloat();
		}
	}

	/*------------------ Cloneable --------------------- */

	/**
	 * Override the Object.clone() method so that the array gets cloned, too.
	 */
	@Override
	public Object clone() {
		try {
			PolyLine copy = (PolyLine) super.clone();
			if (coords != null) {
				copy.coords = (float[]) this.coords.clone();
			}
			return copy;
		} catch (CloneNotSupportedException e) {
			// This should never happen.
			throw new AssertionError();
		}
	}

	public static class Demo {
		public static void main(String[] args) {
			ScribblePane.Demo.main(args);
			System.out.println("Demo of PolyLine as used in ScribblePane.");
		}
	}
}
