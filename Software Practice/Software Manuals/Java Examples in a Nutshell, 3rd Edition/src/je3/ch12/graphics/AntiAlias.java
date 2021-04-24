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
import java.awt.geom.AffineTransform;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.RenderingHints;

/**
 * From Java Examples in a Nutshell, 3rd Edition, p. 377. A demonstration of
 * anti-aliasing.
 */
public class AntiAlias implements GraphicsExample {
	// Size of our example:
	static final int WIDTH = 650, HEIGHT = 350;

	// From GraphicsExample:
	@Override
	public String getName() {
		return "AntiAliasing";
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

	/** Draw the example. */
	@Override
	public void draw(Graphics2D graphics2D, Component component) {
		// Create an off-screen image:
		BufferedImage image = new BufferedImage(65, 35, BufferedImage.TYPE_INT_RGB);
		// Get its Graphics for drawing:
		Graphics2D imageGraphics = image.createGraphics();

		/*
		 * Set the background to a gradient fill. The varying color of the background
		 * helps to demonstrate the anti-aliasing effect.
		 */
		imageGraphics.setPaint(new GradientPaint(0, 0, Color.black, 65, 35, Color.white));
		imageGraphics.fillRect(0, 0, 65, 35);

		/*
		 * Set drawing attributes for the foreground. Most importantly, turn on
		 * anti-aliasing.
		 */
		// Two-pixel lines:
		imageGraphics.setStroke(new BasicStroke(2.0f));
		// An 18-point font:
		imageGraphics.setFont(new Font("Serif", Font.BOLD, 18));
		// Turn on anti-aliasing:
		imageGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Now draw pure blue text and a pure red oval.
		imageGraphics.setColor(Color.blue);
		imageGraphics.drawString("Java", 9, 22);
		imageGraphics.setColor(Color.red);
		imageGraphics.drawOval(1, 1, 62, 32);

		/*
		 * Finally, scale the image by a factor of 10 and display it in the window. This
		 * will allow us to see the anti-aliased pixels.
		 */
		graphics2D.drawImage(image, AffineTransform.getScaleInstance(10, 10), component);

		// Draw the image one more time at its original size, for comparison.
		graphics2D.drawImage(image, 0, 0, component);
	}
	
	public static class Demo {
		public static void main(String[] args) {
			String[] graphicsExampleNames = { "je3.ch12.graphics.AntiAlias" };
			GraphicsExampleFrame.main(graphicsExampleNames);
			System.out.println("Demo of AntiAlias.");
		}
	}
}
