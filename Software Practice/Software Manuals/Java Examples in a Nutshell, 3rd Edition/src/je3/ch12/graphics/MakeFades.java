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

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Paint;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 385-387. This program
 * creates PNG images of the specified color that fade from fully opaque to
 * fully transparent. Images of this sort are useful in web design, where they
 * can be used as background images and combined with background colors to
 * produce two-color fades. (IE6 does not support PNG transparency).
 * <p>
 * Images are produced in three sizes and with and 8 directions. In this
 * implementation, the images are written into the "output" directory, and are
 * given names of the form: fade-to-color-speed-direction.png
 * 
 * <pre>
 *   color:      the color name specified on the command line
 *   speed:      slow (1024px), medium (512px), fast(256px)
 *   direction:  a compass point: N, E, S, W, NE, SE, SW, NW
 * </pre>
 * 
 * Invoke this program with the name of a color and three floating-point values
 * specifying the red, green, and blue components of the color. The red, green,
 * and blue values should be normalized to a value between 0 and 1, that is,
 * red/255, green/255, blue/255. For example:
 * 
 * <pre>
 * Usage: MakeFades purple 0.294f 0.0f 0.510f
 * </pre>
 */
public class MakeFades {
	// A fast fade is a small image, and a slow fade is a large image.
	public static final String[] sizeNames = { "fast", "medium", "slow" };
	public static final int[] sizes = { 256, 512, 1024 };

	// Direction names and coordinates
	public static final String[] directionNames = { //
			"N", "E", "S", "W", "NE", "SE", "SW", "NW" };
	public static float[][] directions = { //
			new float[] { 0f, 1f, 0f, 0f }, // North
			new float[] { 0f, 0f, 1f, 0f }, // East
			new float[] { 0f, 0f, 0f, 1f }, // South
			new float[] { 1f, 0f, 0f, 0f }, // West
			new float[] { 0f, 1f, 1f, 0f }, // Northeast
			new float[] { 0f, 0f, 1f, 1f }, // Southeast
			new float[] { 1f, 0f, 0f, 1f }, // Southwest
			new float[] { 1f, 1f, 0f, 0f }, // Northwest
	};
	private static String usageMessage00 = "Sorry, red, green, and blue values should be between one and zero.";
	private static String usageMessage01 = "Usage: MakeFades purple 0.294f 0.0f 0.510f";

	public static void main(String[] args) throws IOException, NumberFormatException {
		if (args.length < 4) {
			System.err.println();
			System.exit(1);

		}
		// Parse the command-line arguments:
		String colorname = args[0];

		float red = 0.0f;
		float green = 0.0f;
		float blue = 0.0f;
		try {
			red = Float.parseFloat(args[1]);
			green = Float.parseFloat(args[2]);
			blue = Float.parseFloat(args[3]);
		} catch (NumberFormatException e) {
			System.err.println(usageMessage00);
			System.err.println(usageMessage01);
			e.printStackTrace();
		}
		if (red > 1 || green > 1 || blue > 1) {
			System.err.println(usageMessage00);
			System.err.println(usageMessage01);
			throw new IllegalArgumentException(usageMessage01);
		}
		/*
		 * Create from and to colors based on those arguments.
		 */
		// From transparent (invisible):
		Color from = new Color(red, green, blue, 0.0f);
		// To opaque (solid):
		Color to = new Color(red, green, blue, 1.0f);

		// Loop through the sizes and directions, and create an image for each:
		for (int size = 0; size < sizes.length; size++) {
			for (int direction = 0; direction < directions.length; direction++) {
				// This is the size of the image:
				int currentSize = sizes[size];

				// Create a GradientPaint for this direction and size
				Paint paint = new GradientPaint(directions[direction][0] * currentSize, //
						directions[direction][1] * currentSize, //
						from, //
						directions[direction][2] * currentSize, //
						directions[direction][3] * currentSize, //
						to);

				// Start with a blank image that supports transparency.
				BufferedImage image = new BufferedImage(currentSize, currentSize, BufferedImage.TYPE_INT_ARGB);

				// Now use fill the image with our color gradient.
				Graphics2D graphics2D = image.createGraphics();
				graphics2D.setPaint(paint);
				graphics2D.fillRect(0, 0, currentSize, currentSize);

				// This is the name of the file to which we'll write the image.
				String fileName = "";
				fileName += "output/";
				fileName += "fade-to-";
				fileName += colorname + "-";
				fileName += sizeNames[size] + "-";
				fileName += directionNames[direction] + ".png";

				File file = new File(fileName);

				// Save the image in PNG format using the javax.imageio API.
				ImageIO.write(image, "png", file);

				// Show the user our progress by printing the filename.
				System.out.println(file);
			}
		}
	}
}
