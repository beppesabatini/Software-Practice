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
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;
import java.awt.image.DirectColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.awt.Paint;
import java.awt.PaintContext;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 400-402. This is an
 * abstract Paint implementation that computes the color of each point to be
 * painted, by passing the coordinates of the point to the abstract methods
 * computeRed(), computeGreen(), computeBlue() and computeAlpha(). Alpha means
 * simply transparency. Subclasses must implement these three or four methods to
 * perform whatever type of painting is desired. Note that while this class
 * provides great flexibility, it is not very efficient.
 * <p/>
 * GenericPaint is an abstract class. Launching GenericPaint.Demo actually
 * launches Paints.Demo, the demo for the primary concrete subclass.
 * 
 */
public abstract class GenericPaint implements Paint {
	/** This is the main Paint method; all it does is return a PaintContext */
	@Override
	public PaintContext createContext(ColorModel colorModel, Rectangle deviceBounds, Rectangle2D userBounds,
			AffineTransform transform, RenderingHints hints) {
		return new GenericPaintContext(transform);
	}

	/** This paint class allows translucent painting */
	public int getTransparency() {
		return TRANSLUCENT;
	}

	/**
	 * These three methods return the red, green, blue, and alpha values of the
	 * pixel at appear at the specified user-space coordinates. The return value of
	 * each method should be between 0 and 255.
	 */
	public abstract int computeRed(double x, double y);

	public abstract int computeGreen(double x, double y);

	public abstract int computeBlue(double x, double y);

	public abstract int computeAlpha(double x, double y);

	/**
	 * The PaintContext class does all the work of painting
	 */
	class GenericPaintContext implements PaintContext {
		// The color model:
		ColorModel model;
		// For device-to-user transformation:
		Point2D origin, unitVectorX, unitVectorY;

		/**
		 * <pre>
		 * Our color model packs RGB and alpha values into a single integer. Here RGB
		 * means Red, Green, and Blue, while alpha means simply transparency or opacity.
		 * 
		 *                 bits     red       green        blue        alpha 
		 * DirectColorModel(32, 0x00ff0000, 0x0000ff00, 0x000000ff, 0xff000000);
		 * </pre>
		 */
		public GenericPaintContext(AffineTransform userToDevice) {
			model = new DirectColorModel(32, 0x00ff0000, 0x0000ff00, 0x000000ff, 0xff000000);
			/*
			 * The specified transform converts user to device pixels. We need to figure out
			 * the reverse transformation, so we can compute the user space coordinates of
			 * each device pixel.
			 */
			try {
				AffineTransform deviceToUser = userToDevice.createInverse();
				origin = deviceToUser.transform(new Point(0, 0), null);
				unitVectorX = deviceToUser.deltaTransform(new Point(1, 0), null);
				unitVectorY = deviceToUser.deltaTransform(new Point(0, 1), null);
			} catch (NoninvertibleTransformException e) {
				// If we can't invert the transform, just use device space.
				origin = new Point(0, 0);
				unitVectorX = new Point(1, 0);
				unitVectorY = new Point(0, 1);
			}
		}

		/** Return the color model used by this Paint implementation. */
		@Override
		public ColorModel getColorModel() {
			return model;
		}

		/**
		 * This is the main method of PaintContext. It must return a Raster that
		 * contains fill data for the specified rectangle. It creates a raster of the
		 * specified size, and loops through the device pixels. For each one, it
		 * converts the coordinates to user space, then calls the computeRed(),
		 * computeGreen() and computeBlue() methods to obtain the appropriate color for
		 * the device pixel.
		 */
		@Override
		public Raster getRaster(int x, int y, int width, int height) {
			WritableRaster raster = model.createCompatibleWritableRaster(width, height);
			int[] colorComponents = new int[4];
			// Loop through rows of raster:
			for (int j = 0; j < height; j++) {
				int deviceY = y + j;
				// Loop through columns:
				for (int i = 0; i < width; i++) {
					int deviceX = x + i;
					// Convert device coordinate to user-space coordinate.
					double userX = origin.getX() + deviceX * unitVectorX.getX() + deviceY * unitVectorY.getX();
					double userY = origin.getY() + deviceX * unitVectorX.getY() + deviceY * unitVectorY.getY();
					// Compute the color components of the pixel.
					colorComponents[0] = computeRed(userX, userY);
					colorComponents[1] = computeGreen(userX, userY);
					colorComponents[2] = computeBlue(userX, userY);
					colorComponents[3] = computeAlpha(userX, userY);
					// Set the color of the pixel.
					raster.setPixel(i, j, colorComponents);
				}
			}
			return raster;
		}

		/** Called when the PaintContext is no longer needed. */
		@Override
		public void dispose() {
		}
	}

	public static class Demo {
		public static void main(String[] args) {
			String[] graphicsExampleNames = { "je3.ch12.graphics.Paints" };
			GraphicsExampleFrame.main(graphicsExampleNames);
			System.out.println("Demo of Generic Paint via Paints.");
		}
	}
}
