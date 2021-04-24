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

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.awt.Shape;
import java.io.FileNotFoundException;
import java.net.URL;

import javax.swing.ImageIcon;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 378-381.
 */
public class CompositeEffects implements GraphicsExample {
	// The image we'll be displaying, and its size:
	Image cover;
	static final int COVERWIDTH = 127, COVERHEIGHT = 190;

	/** This constructor loads the cover image. */
	public CompositeEffects() throws FileNotFoundException {
		String imageName = "images/je3.cover.gif";
		URL imageUrl = null;
		imageUrl = CompositeEffects.class.getResource(imageName);
		if (imageUrl == null) {
			String message = "Sorry, in CompositeEffects(), could not locate resource: " + imageName;
			System.err.println(message);
			throw new FileNotFoundException(message);
		}
		cover = new ImageIcon(imageUrl).getImage();
	}

	// These are basic GraphicsExample methods.
	@Override
	public String getName() {
		return "Composite Effects";
	}

	@Override
	public int getWidth() {
		// The total width for six images:
		return 6 * COVERWIDTH + 70;
	}

	@Override
	public int getHeight() {
		return COVERHEIGHT + 50;
	}

	/** Draw the example. */
	@Override
	public void draw(Graphics2D graphics2D, Component component) {
		// fill the background:
		graphics2D.setPaint(new Color(175, 175, 175));
		graphics2D.fillRect(0, 0, getWidth(), getHeight());

		// Set text attributes:
		graphics2D.setColor(Color.black);
		graphics2D.setFont(new Font("SansSerif", Font.BOLD, 12));

		// Draw the unmodified image:
		graphics2D.translate(10, 10);
		graphics2D.drawImage(cover, 0, 0, component);
		graphics2D.drawString("Original Image", 0, COVERHEIGHT + 15);

		/*
		 * Draw the cover again, using AlphaComposite to make the opaque colors of the
		 * image 50% translucent.
		 */
		graphics2D.translate(COVERWIDTH + 10, 0);
		graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		graphics2D.drawImage(cover, 0, 0, component);

		/*
		 * Restore the pre-defined default Composite for the screen, so opaque colors
		 * stay opaque.
		 */
		graphics2D.setComposite(AlphaComposite.SrcOver);
		// Label the effect:
		graphics2D.drawString("Original Image,", 0, COVERHEIGHT + 15);
		graphics2D.drawString("Half Transparent", 0, COVERHEIGHT + 30);

		/*
		 * Now get an off-screen image to work with. In order to achieve certain
		 * compositing effects, the drawing surface must support transparency. On-screen
		 * drawing surfaces cannot, so we have to do the compositing in an off-screen
		 * image that is specially created to have an "alpha channel", then copy the
		 * final result to the screen.
		 */
		BufferedImage offscreen = new BufferedImage(COVERWIDTH, COVERHEIGHT, BufferedImage.TYPE_INT_ARGB);

		/*
		 * First, fill the image with a color gradient background that varies
		 * left-to-right from opaque to transparent yellow.
		 */
		Graphics2D offScreenGraphics = offscreen.createGraphics();
		offScreenGraphics.setPaint(new GradientPaint(0, 0, Color.yellow, COVERWIDTH, 0, new Color(255, 255, 0, 0)));
		offScreenGraphics.fillRect(0, 0, COVERWIDTH, COVERHEIGHT);

		/*
		 * Now copy the cover image on top of this, but use the DstOver rule which draws
		 * it "underneath" the existing pixels, and allows the image to show depending
		 * on the transparency of those pixels.
		 */
		offScreenGraphics.setComposite(AlphaComposite.DstOver);
		offScreenGraphics.drawImage(cover, 0, 0, component);

		/*
		 * ...and, display this composited image on the screen. Note that the image is
		 * opaque, and that none of the screen background shows through.
		 */
		graphics2D.translate(COVERWIDTH + 10, 0);
		graphics2D.drawImage(offscreen, 0, 0, component);
		graphics2D.drawString("Yellow Gradient over", 0, COVERHEIGHT + 15);
		graphics2D.drawString("Opaque Original", 0, COVERHEIGHT + 30);

		/*
		 * Now start over, and do a new effect with the off-screen image. First, fill
		 * the off-screen image with a new color gradient. We don't care about the
		 * colors themselves; we just want the translucency of the background to vary.
		 * We use opaque black to transparent black. Note that since we've already used
		 * this off-screen image, we set the composite to Src, so we can fill the image
		 * and ignore anything that is already there.
		 */
		offScreenGraphics.setComposite(AlphaComposite.Src);
		offScreenGraphics
				.setPaint(new GradientPaint(0, 0, Color.black, COVERWIDTH, COVERHEIGHT, new Color(0, 0, 0, 0)));
		offScreenGraphics.fillRect(0, 0, COVERWIDTH, COVERHEIGHT);

		/*
		 * Now set the compositing type to SrcIn, so colors come from the source, but
		 * the translucency comes from the destination.
		 */
		offScreenGraphics.setComposite(AlphaComposite.SrcIn);

		// Draw our loaded image into the off-screen image, compositing it.
		offScreenGraphics.drawImage(cover, 0, 0, component);

		/*
		 * ...and then, copy our off-screen image on to the screen. Note that the image
		 * is translucent, and some of the background shows through.
		 */
		graphics2D.translate(COVERWIDTH + 10, 0);
		graphics2D.drawImage(offscreen, 0, 0, component);
		graphics2D.drawString("Original turning ", 0, COVERHEIGHT + 15);
		graphics2D.drawString("Invisible", 0, COVERHEIGHT + 30);

		/*
		 * If we do the same thing but use SrcOut, then the resulting image will have
		 * the inverted translucency values of the destination.
		 */
		offScreenGraphics.setComposite(AlphaComposite.Src);
		offScreenGraphics
				.setPaint(new GradientPaint(0, 0, Color.black, COVERWIDTH, COVERHEIGHT, new Color(0, 0, 0, 0)));
		offScreenGraphics.fillRect(0, 0, COVERWIDTH, COVERHEIGHT);
		offScreenGraphics.setComposite(AlphaComposite.SrcOut);
		offScreenGraphics.drawImage(cover, 0, 0, component);
		graphics2D.translate(COVERWIDTH + 10, 0);
		graphics2D.drawImage(offscreen, 0, 0, component);
		graphics2D.drawString("Invisibility ", 0, COVERHEIGHT + 15);
		graphics2D.drawString("Wearing Off", 0, COVERHEIGHT + 30);

		/*
		 * Here's a cool effect; it has nothing to do with compositing, but uses an
		 * arbitrary shape to clip the image. It uses Area to combine shapes into more
		 * complicated ones.
		 */
		graphics2D.translate(COVERWIDTH + 10, 0);
		// Save the current clipping region:
		Shape savedClip = graphics2D.getClip();
		// Create a shape to use as the new clipping region. Begin with an ellipse:
		Area clip = new Area(new Ellipse2D.Float(0, 0, COVERWIDTH, COVERHEIGHT));
		// Intersect with a rectangle, truncating the ellipse:
		clip.intersect(new Area(new Rectangle(5, 5, COVERWIDTH - 10, COVERHEIGHT - 10)));
		// Then, subtract an ellipse from the bottom of the truncated ellipse:
		clip.subtract(new Area(new Ellipse2D.Float(COVERWIDTH / 2 - 40, COVERHEIGHT - 20, 80, 40)));
		// Use the resulting shape as the new clipping region.
		graphics2D.clip(clip);
		// Then, draw the image through this clipping region:
		graphics2D.drawImage(cover, 0, 0, component);
		// Restore the old clipping region, so we can label the effect.
		graphics2D.setClip(savedClip);
		graphics2D.drawString("Clipping Against", 0, COVERHEIGHT + 15);
		graphics2D.drawString("Ellipse Shapes", 0, COVERHEIGHT + 30);

	}

	public static class Demo {
		public static void main(String[] args) {
			String[] graphicsExampleNames = { "je3.ch12.graphics.CompositeEffects" };
			GraphicsExampleFrame.main(graphicsExampleNames);
			System.out.println("Demo of CompositeEffects.");
		}
	}
}
