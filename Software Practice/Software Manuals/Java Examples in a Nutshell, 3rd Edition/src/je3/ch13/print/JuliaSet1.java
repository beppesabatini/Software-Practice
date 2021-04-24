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
package je3.ch13.print;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.JobAttributes;
import java.awt.PageAttributes;
import java.awt.PrintJob;
import java.awt.Toolkit;

import je3.ch11.gui.JPanelDemoable;
import utils.LearningJava3Utils;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 413-415. This class is a
 * Swing component that computes and displays a fractal image known as a "Julia
 * set." The print() method demonstrates printing with the Java 1.1 printing
 * API, and is the main point of the example. As it happens though, the printing
 * functionality is so old it no longer works. At least he fractals still look
 * nice.
 */
public class JuliaSet1 extends JPanelDemoable {

	private static final long serialVersionUID = 3827679489524379411L;
	/*
	 * These constants are hard-coded for simplicity.
	 */
	// Region of complex plane:
	double xUpperLeft = -1.5;
	double yUpperLeft = -1.5;
	double xLowerRight = 1.5;
	double yLowerRight = 1.5;
	// Mapped to these pixels:
	int width = 400, height = 400;
	// This complex constant defines the set we display:
	double centerX, centerY;
	// The image we compute:
	BufferedImage image;

	/*
	 * We compute values between 0 and 63 for each point in the complex plane. This
	 * array holds the color values for each of those values.
	 */
	static int[] colors;
	// Static initializer for the colors[] array:
	static {
		colors = new int[64];
		for (int i = 0; i < colors.length; i++) {
			// Gray scale:
			// colors[63-i] = (i*4 << 16) + (i*4 << 8) + i*4;
			// Crazy Technicolor:
			colors[63 - i] = (i * 4) ^ ((i * 3) << 6) ^ ((i * 7) << 13);
		}
	}

	// The ShowBean platform requires such a constructor with no arguments, so here
	// we go.
	public JuliaSet1() {
		// This calls the constructor with two arguments with default values.
		this((double) -1.0, (double) 0.0);
	}

	/*
	 * This constructor specifies the {centerX, centerY} constants. For simplicity,
	 * the other constants remain hardcoded.
	 */
	public JuliaSet1(double centerX, double centerY) {
		this.centerX = centerX;
		this.centerY = centerY;
		// Not working yet; it's not clear why.
		setPreferredSize(new Dimension(width, height));

		computeImage();
	}

	// This method computes a color value for each pixel of the image.
	void computeImage() {
		// Create the image
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		// Now loop through the pixels.
		int i, j;
		double xWalker, yWalker;
		double deltaX = (xLowerRight - xUpperLeft) / width;
		double deltaY = (yLowerRight - yUpperLeft) / height;
		for (j = 0, yWalker = yUpperLeft; j < height; j++, yWalker += deltaY) {
			for (i = 0, xWalker = xUpperLeft; i < width; i++, xWalker += deltaX) {
				/*
				 * For each pixel, call testPoint() to determine a value. Then map that value to
				 * a color and set it in the image. If testPoint() returns 0, the point is part
				 * of the Julia set and is displayed in black. If it returns 63, the point is
				 * displayed in white. Values in-between are displayed in "crazy Technicolor."
				 */
				image.setRGB(i, j, colors[testPoint(xWalker, yWalker)]);
			}
		}
	}

	/*
	 * This is the key method for computing Julia sets. For each point z in the
	 * complex plane, we repeatedly compute z = z*z + c using complex arithmetic. We
	 * stop iterating when the magnitude of z exceeds 2, or after 64 iterations. We
	 * return the number of iterations-1.
	 */
	public int testPoint(double zx, double zy) {
		for (int i = 0; i < colors.length; i++) {
			// Compute z = z * z + c;
			double newX = zx * zx - zy * zy + centerX;
			double newY = 2 * zx * zy + centerY;
			zx = newX;
			zy = newY;
			// Check the magnitude of z and return the iteration number.
			if (zx * zx + zy * zy > 4) {
				return i;
			}
		}
		return colors.length - 1;
	}

	/*
	 * This method overrides JComponent to display the Julia set. Just scale the
	 * image to fit and draw it.
	 */
	public void paintComponent(Graphics g) {
		g.drawImage(image, 0, 0, width, height, this);
	}

	/*
	 * This method demonstrates the Java 1.1 java.awt.PrintJob printing API. Display
	 * the Julia set with ShowBean and use the Command menu to invoke this print
	 * command.
	 */
	/**
	 * This method demonstrates the Java 1.1 printing API (which fails, as of this
	 * writing). It also demonstrates the JobAttributes and PageAttributes classes
	 * added in Java 1.3. Test this class using the ShowBean program, as below.
	 * Substitute your own package names if they are different.
	 * 
	 * <pre>
	 je3.ch11.gui.ShowBean -width=400 -height=400 je3.ch13.print.JuliaSet1
	 * </pre>
	 */

	public void print() {
		/*
		 * Printing is not working yet. Probably the old Java 1 and Java 2 classes are
		 * just too old.
		 */
		LearningJava3Utils.confirmContinueWithDisfunctional();

		/*
		 * Create some attributes objects. This is Java 1.3 stuff. In Java 1.1, we'd use
		 * a java.util.Preferences object instead.
		 */
		JobAttributes jobAttributes = new JobAttributes();
		PageAttributes pageAttributes = new PageAttributes();

		// Set some example attributes: monochrome, landscape mode.
		pageAttributes.setColor(PageAttributes.ColorType.MONOCHROME);
		pageAttributes.setOrientationRequested(PageAttributes.OrientationRequestedType.LANDSCAPE);
		// Print to file by default.
		jobAttributes.setDestination(JobAttributes.DestinationType.FILE);
		jobAttributes.setFileName("juliaset.ps");

		// Look up the Frame that holds this component.
		Component frame = this;
		while (!(frame instanceof Frame)) {
			frame = frame.getParent();
		}

		/*
		 * Get a PrintJob object with which to print the Julia set. The getPrintJob()
		 * method displays a print dialog, and allows the user to override and modify
		 * the default JobAttributes and PageAttributes
		 */
		Toolkit toolkit = this.getToolkit();
		PrintJob job = toolkit.getPrintJob((Frame) frame, "JuliaSet1", jobAttributes, pageAttributes);

		// We get a null PrintJob if the user clicked cancel.
		if (job == null) {
			return;
		}

		/*
		 * Get a Graphics object from the PrintJob. We print simply by drawing to this
		 * Graphics object.
		 */
		Graphics g = job.getGraphics();

		/*
		 * Center the image on the page.
		 */
		// How big is the page?
		Dimension pagesize = job.getPageDimension();
		// How big is the image?
		Dimension panesize = this.getSize();
		// Center the image:
		g.translate((pagesize.width - panesize.width) / 2, (pagesize.height - panesize.height) / 2);

		// Draw a box around the Julia Set and label it.
		g.drawRect(-1, -1, panesize.width + 2, panesize.height + 2);
		g.drawString("Julia Set for c={" + centerX + "," + centerY + "}", 0, -15);

		// Set a clipping region:
		g.setClip(0, 0, panesize.width, panesize.height);

		// Now print the component by calling its paint method.
		this.paint(g);

		/*
		 * Finally, tell the printer we're done with the page. No output will be
		 * generated if we don't call dispose() here.
		 */
		g.dispose();
	}

	public static class Demo {
		public static void main(String[] args) {
			launchBeansInShowBean(JPanelDemoable.juliaSetBeans, 421, 490);
			System.out.println("Demo for three Julia Sets. Only JuliaSet3 actually prints.");
		}
	}
}
