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
import java.awt.color.ColorSpace;
import java.awt.Component;
import java.awt.Font;
import java.awt.geom.AffineTransform;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ByteLookupTable;
import java.awt.image.ColorConvertOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.LookupOp;
import java.awt.image.RescaleOp;
import java.io.FileNotFoundException;
import java.net.URL;

import javax.swing.ImageIcon;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 382-384. A demonstration
 * of various image processing filters.
 */
public class ImageOps implements GraphicsExample {
	// Size of our examples:
	static final int WIDTH = 600, HEIGHT = 675;

	// From GraphicsExample:
	@Override
	public String getName() {
		return "Image Processing";
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

	Image image;

	/** This constructor loads the image we will manipulate. */
	public ImageOps() throws FileNotFoundException {
		String imageName = "images/je3.cover.gif";
		// String imageName = "images/s-l1600.jpg";
		URL imageUrl = null;
		imageUrl = CompositeEffects.class.getResource(imageName);
		if (imageUrl == null) {
			String message = "Sorry, in CompositeEffects(), could not locate resource: " + imageName;
			System.err.println(message);
			throw new FileNotFoundException(message);
		}
		image = new ImageIcon(imageUrl).getImage();
	}

	// These are the labels we'll display for each of the filtered images.
	static String[] filterNames = initFilterNames();

	static String[] initFilterNames() {
		String[] filterNames = new String[12];
		filterNames[0] = "Original";
		filterNames[1] = "Gray Scale";
		filterNames[2] = "Negative";
		filterNames[3] = "Brighten (linear)";
		filterNames[4] = "Brighten (sqrt)";
		filterNames[5] = "Threshold";
		filterNames[6] = "Blur";
		filterNames[7] = "Sharpen";
		filterNames[8] = "Edge Detect";
		filterNames[9] = "Mirror";
		filterNames[10] = "Rotate (center)";
		filterNames[11] = "Rotate (lower left)";
		return (filterNames);
	}

	// These arrays of bytes are used by the LookupImageOp image filters below.
	static byte[] brightenTable = new byte[256];
	static byte[] thresholdTable = new byte[256];
	static { // Initialize the arrays
		for (int i = 0; i < 256; i++) {
			brightenTable[i] = (byte) (Math.sqrt(i / 255.0) * 255);
			thresholdTable[i] = (byte) ((i < 225) ? 0 : i);
		}
	}

	// This AffineTransform is used by one of the image filters below.
	static AffineTransform mirrorTransform;
	static {
		// Create and initialize the AffineTransform:
		mirrorTransform = AffineTransform.getTranslateInstance(127, 0);
		// Flip horizontally:
		mirrorTransform.scale(-1.0, 1.0);
	}

	static float[] blurFloats = new float[] { .1111f, .1111f, .1111f, .1111f, .1111f, .1111f, .1111f, .1111f, .1111f, };
	static Kernel blurMatrix = new Kernel(3, 3, blurFloats);

	static float[] sharpenFloats = new float[] { 0.0f, -0.75f, 0.0f, -0.75f, 4.0f, -0.75f, 0.0f, -0.75f, 0.0f };
	static Kernel sharpenMatrix = new Kernel(3, 3, sharpenFloats);

	static float[] edgeDetectFloats = new float[] { 0.0f, -0.75f, 0.0f, -0.75f, 3.0f, -0.75f, 0.0f, -0.75f, 0.0f };
	static Kernel edgeDetectMatrix = new Kernel(3, 3, edgeDetectFloats);

	static int nearestNeighbor = AffineTransformOp.TYPE_NEAREST_NEIGHBOR;
	static AffineTransform rotate180degrees = AffineTransform.getRotateInstance(Math.PI, 64, 95);
	static AffineTransform rotate15degrees = AffineTransform.getRotateInstance(Math.PI / 12, 0, 190);

	/*
	 * The following BufferedImageOp image filter objects perform different types of
	 * image processing operations.
	 */
	static BufferedImageOp[] filters = new BufferedImageOp[] {
			// 1) No filter here. We'll display the original image.
			null,
			// 2) Convert to gray scale color space:
			new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null),
			// 3) Negative image. Multiply each color value by -1.0 and add 255:
			new RescaleOp(-1.0f, 255f, null),
			// 4) Brighten, using a linear formula that increases all color values:
			new RescaleOp(1.25f, 0, null),
			// 5) Brighten using the lookup table defined above:
			new LookupOp(new ByteLookupTable(0, brightenTable), null),
			// 6) Threshold using the lookup table defined above:
			new LookupOp(new ByteLookupTable(0, thresholdTable), null),
			// 7) Blur by "convolving" (rolling together) the image with a matrix:
			new ConvolveOp(blurMatrix),
			// 8) Sharpen by using a different matrix:
			new ConvolveOp(sharpenMatrix),
			// 9) Edge detect using yet another matrix:
			new ConvolveOp(edgeDetectMatrix),
			// 10) Compute a mirror image using the transform defined above:
			new AffineTransformOp(mirrorTransform, AffineTransformOp.TYPE_BILINEAR),
			// 11) Rotate the image 180 degrees about its center point:
			new AffineTransformOp(rotate180degrees, nearestNeighbor),
			// 12) Rotate the image 15 degrees about the bottom left:
			new AffineTransformOp(rotate15degrees, nearestNeighbor), };

	/** Draw the example. */
	@Override
	public void draw(Graphics2D graphics2D, Component component) {
		/*
		 * Create a BufferedImage big enough to hold the Image loaded in the
		 * constructor. Then copy that image into the new BufferedImage object so that
		 * we can process it.
		 */
		int width = image.getWidth(component);
		int height = image.getHeight(component);
		int integerRGB = BufferedImage.TYPE_INT_RGB;
		BufferedImage bufferedImage = new BufferedImage(width, height, integerRGB);
		Graphics2D bufferedImageGraphics = bufferedImage.createGraphics();
		// Copy the image:
		bufferedImageGraphics.drawImage(image, 0, 0, component);

		// Set some default graphics attributes. Use 12pt bold text:
		graphics2D.setFont(new Font("SansSerif", Font.BOLD, 12));
		// Draw in green:
		graphics2D.setColor(Color.green);
		// Start drawing at x 10, y 10, giving us top and left margins:
		graphics2D.translate(10, 10);

		// Loop through the filters
		for (int i = 0; i < filters.length; i++) {
			/*
			 * If the filter is null, draw the original image, otherwise, draw the image as
			 * processed by the filter.
			 */
			if (filters[i] == null) {
				graphics2D.drawImage(bufferedImage, 0, 0, component);
			} else {
				graphics2D.drawImage(filters[i].filter(bufferedImage, null), 0, 0, component);
			}
			// Label the image:
			graphics2D.drawString(filterNames[i], 0, 205);
			// Move to the next image:
			graphics2D.translate(137, 0);
			// Move down a row after four images:
			if ((i + 1) % 4 == 0) {
				graphics2D.translate(-137 * 4, 215);
			}
		}
	}

	public static class Demo {
		public static void main(String[] args) {
			String[] graphicsExampleNames = { "je3.ch12.graphics.ImageOps" };
			GraphicsExampleFrame.main(graphicsExampleNames);
			System.out.println("Demo of ImageOps.");
		}
	}
}
