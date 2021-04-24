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
import java.awt.geom.AffineTransform;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.TexturePaint;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 373-376. A demonstration
 * of Java2D custom paints and transformations.
 */
public class Paints implements GraphicsExample {
	// Size of our example:
	static final int WIDTH = 800, HEIGHT = 375;

	// From GraphicsExample:
	@Override
	public String getName() {
		return "Paints";
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

	/** Draw the example */
	@Override
	public void draw(Graphics2D graphics2D, Component component) {
		/*
		 * Paint the entire background using a GradientPaint. The background color
		 * varies diagonally from deep red to pale blue.
		 */
		graphics2D.setPaint(new GradientPaint(0, 0, new Color(150, 0, 0), WIDTH, HEIGHT, new Color(200, 200, 255)));
		// Fill the background:
		graphics2D.fillRect(0, 0, WIDTH, HEIGHT);

		/**
		 * Use a different GradientPaint to draw the border box. This paint fades back
		 * and forth between deep opaque green and transparent green.
		 * <p>
		 * Note: the 4th argument to the Color() constructor specifies color opacity; 0
		 * through 255 specify values in a range from totally transparent to totally
		 * solid (or opaque). The Color() constructor refers to the transparency/opacity
		 * as the "alpha" channel.
		 */
		graphics2D.setPaint(new GradientPaint(0, 0, new Color(0, 150, 0), 20, 20, new Color(0, 150, 0, 0), true));
		// Use wide lines:
		graphics2D.setStroke(new BasicStroke(15));
		// Now draw the box:
		graphics2D.drawRect(25, 25, WIDTH - 50, HEIGHT - 50);

		/*
		 * The glyphs of fonts can be used as Shape objects, which enables us to use
		 * Java2D techniques with letters Just as we would with any other shape. Here we
		 * get some letter shapes to draw.
		 */
		// A basic font:
		Font font = new Font("Serif", Font.BOLD, 10);
		// A scaled-up version:
		Font bigfont = font.deriveFont(AffineTransform.getScaleInstance(30.0, 30.0));
		GlyphVector glyphVector = bigfont.createGlyphVector(graphics2D.getFontRenderContext(), "JAV");
		// Shape of the letter 'J':
		Shape jShape = glyphVector.getGlyphOutline(0);
		// Shape of the letter 'A':
		Shape aShape = glyphVector.getGlyphOutline(1);
		// Shape of the letter 'V':
		Shape vshape = glyphVector.getGlyphOutline(2);

		// We're going to outline the letters with a 5-pixel wide line.
		graphics2D.setStroke(new BasicStroke(5.0f));

		/*
		 * We're going to fake shadows for the letters using the following Paint and
		 * AffineTransform objects.
		 */
		// Specify translucent black:
		Paint shadowPaint = new Color(0, 0, 0, 100);
		// Shear to the right:
		AffineTransform shadowTransform = AffineTransform.getShearInstance(-1.0, 0.0);
		// Scale the height by one-half:
		shadowTransform.scale(1.0, 0.5);

		// Move to the baseline of our first letter, 'J'.
		graphics2D.translate(65, 270);

		// Draw the shadow of the J shape:
		graphics2D.setPaint(shadowPaint);
		// Compensate for the descender of the J:
		graphics2D.translate(15, 20);
		// Transform the 'J' into the shape of its shadow, and fill it:
		graphics2D.fill(shadowTransform.createTransformedShape(jShape));
		// Undo the translation above"
		graphics2D.translate(-15, -20);

		/* Now fill the 'J' shape with a solid (and opaque) color. */
		// Fill with solid, opaque blue:
		graphics2D.setPaint(Color.blue);
		// Fill the shape:
		graphics2D.fill(jShape);
		// Switch to solid black:
		graphics2D.setPaint(Color.black);
		// ...and, draw the outline of the J:
		graphics2D.draw(jShape);

		/*
		 * Now draw the A shadow.
		 */
		// Move to the right:
		graphics2D.translate(-20, 0);
		// Set the shadow color:
		graphics2D.setPaint(shadowPaint);
		// Draw the shadow:
		graphics2D.fill(shadowTransform.createTransformedShape(aShape));

		/*
		 * Draw the first 'A' shape using a solid transparent color.
		 */
		// Transparent green, for paint:
		graphics2D.setPaint(new Color(0, 255, 0, 125));
		// Fill the shape:
		graphics2D.fill(aShape);
		// Switch to solid black:
		graphics2D.setPaint(Color.black);
		// Draw the outline:
		graphics2D.draw(aShape);

		/*
		 * Draw the shadow of the letter 'V'.
		 */
		graphics2D.translate(-60, 0);
		graphics2D.setPaint(shadowPaint);
		graphics2D.fill(shadowTransform.createTransformedShape(vshape));

		/*
		 * We're going to fill the next letter using a TexturePaint, which repeatedly
		 * tiles an image. The first step is to obtain the image. We could load it from
		 * an image file, but here we create it ourselves by drawing a into an
		 * off-screen image. Note that we use a GradientPaint to fill the off-screen
		 * image, so the fill pattern combines features of both Paint classes.
		 */
		// Create a new tile image:
		BufferedImage tile = new BufferedImage(50, 50, BufferedImage.TYPE_INT_RGB);
		// Get its Graphics for drawing:
		Graphics2D tileGraphics = tile.createGraphics();
		// Fill tile background with pink:
		tileGraphics.setColor(Color.pink);
		tileGraphics.fillRect(0, 0, 50, 50);
		// Now using diagonal gradient, shifting from green to gray:
		tileGraphics.setPaint(new GradientPaint(40, 0, Color.green, 0, 40, Color.gray));
		// Draw a circle with this gradient:
		tileGraphics.fillOval(5, 5, 40, 40);

		/*
		 * Use this new tile to create a TexturePaint and fill the letter 'V'.
		 */
		graphics2D.setPaint(new TexturePaint(tile, new Rectangle(0, 0, 50, 50)));
		// Fill in the 'V' letter shape:
		graphics2D.fill(vshape);
		// Switch to solid black:
		graphics2D.setPaint(Color.black);
		// Draw the outline of the letter:
		graphics2D.draw(vshape);

		/* Move to the right and draw the shadow of the final A. */
		graphics2D.translate(365, 0);
		graphics2D.setPaint(shadowPaint);
		graphics2D.fill(shadowTransform.createTransformedShape(aShape));

		/*
		 * For the last letter, use a custom Paint class to fill with a complex
		 * mathematically defined pattern. The color is recalculated for every pixel.
		 * The abstract GenericPaint class is defined later in the chapter.
		 */
		graphics2D.setPaint(new GenericPaint() {
			@Override
			public int computeRed(double x, double y) {
				return 128;
			}

			@Override
			public int computeGreen(double x, double y) {
				return (int) ((Math.sin(x / 7) + Math.cos(y / 5) + 2) / 4 * 255);
			}

			@Override
			public int computeBlue(double x, double y) {
				return ((int) (x * y)) % 256;
			}

			@Override
			public int computeAlpha(double x, double y) {
				return ((int) x % 25 * 8 + 50) + ((int) y % 25 * 8 + 50);
			}
		});
		// Fill the second letter 'A':
		graphics2D.fill(aShape);
		// Revert to solid black:
		graphics2D.setPaint(Color.black);
		// Draw the outline of the second 'A':
		graphics2D.draw(aShape);
	}

	public static class Demo {
		public static void main(String[] args) {
			String[] graphicsExampleNames = { "je3.ch12.graphics.Paints" };
			GraphicsExampleFrame.main(graphicsExampleNames);
			System.out.println("Demo of Paints.");
		}
	}
}
