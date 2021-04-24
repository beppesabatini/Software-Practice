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

import java.applet.Applet;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.net.URL;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 355-358. An applet that
 * demonstrates most of the graphics primitives in java.awt.Graphics. In the
 * development installation, this is compiled with Java 8. To test or demo this,
 * switch Workspace to "Software Practice - Experimental".
 * 
 * <pre>
 * Remember that for most graphics the X and Y coordinates are numbered like so:
 * 
 *    0 - 1 - 2 - 3 - 4 - etc... [X axis]
 *    |
 *    1
 *    |
 *    2
 *    |
 *    3
 *    |
 *    4
 *    |
 *  etc...
 * [Y axis]
 * </pre>
 * 
 **/
@SuppressWarnings("deprecation") // This class actually compiles and runs correctly when Java 8 is used.
public class GraphicsSampler extends Applet {

	private static final long serialVersionUID = 6709741782676452652L;

	// The various colors we use:
	Color fill, outline, textcolor;
	// The font we use for text:
	Font font;
	// Information about font size:
	FontMetrics metrics;
	// Some images we draw with:
	Image image, background;

	public void start() {
		int width = 400;
		int height = 400;
		this.setSize(new Dimension(width, height));
	}

	/*
	 * This method is called when the applet is first created. It performs
	 * initialization, such as creating the resources (graphics attribute values)
	 * used by the paint() method.
	 */
	public void init() {
		/*
		 * Initialize color resources. Note the use of the Color() constructor, and the
		 * use of pre-defined color constants.
		 */
		// Equal values of red, green, and blue render as gray.
		fill = new Color(200, 200, 200);
		// Same as new Color(0, 0, 255):
		outline = Color.blue;
		// Same as new Color(255, 0, 0):
		textcolor = Color.red;

		// Create a font for use in the paint() method. Get its metrics, too.
		font = new Font("sansserif", Font.BOLD, 14);
		metrics = this.getFontMetrics(font);

		/*
		 * Load some Image objects for use in the paint() method. Note that the
		 * getResource() function searches for the resource starting from the classpath.
		 * This might not work everywhere, but it does work in Eclipse, which is all we
		 * need.
		 */
		URL tigerURL = GraphicsSampler.class.getResource("images/tiger.png");
		image = this.getImage(tigerURL);

		URL backgroundUrl = GraphicsSampler.class.getResource("images/background.png");
		background = this.getImage(backgroundUrl);

		// Set a property that tells the applet its background color.
		this.setBackground(Color.lightGray);
		this.setSize(new Dimension(400, 400));
	}

	// This method is called whenever the applet needs to be drawn or redrawn.
	public void paint(Graphics g) {
		// Specify the font we'll be using throughout.
		g.setFont(font);

		// Draw a background by tiling an image. The tile() function is defined below.
		tile(g, this, background);

		/*
		 * Draw a line.
		 */
		// Specify the drawing color:
		g.setColor(outline);
		// Draw a line from (25,10) to (150,80):
		g.drawLine(25, 10, 150, 80);
		// Draw some text. See the centerText() method below.
		centerText("drawLine()", null, g, textcolor, 25, 10, 150, 80);

		// Draw and fill an arc:
		g.setColor(fill);
		g.fillArc(225, 10, 150, 80, 90, 135);
		g.setColor(outline);
		g.drawArc(225, 10, 150, 80, 90, 135);
		centerText("fillArc()", "drawArc()", g, textcolor, 225, 10, 150, 80);

		// Draw and fill a rectangle:
		g.setColor(fill);
		g.fillRect(25, 110, 150, 80);
		g.setColor(outline);
		g.drawRect(25, 110, 150, 80);
		centerText("fillRect()", "drawRect()", g, textcolor, 25, 110, 150, 80);

		// Draw and fill a rounded rectangle:
		g.setColor(fill);
		g.fillRoundRect(225, 110, 150, 80, 20, 20);
		g.setColor(outline);
		g.drawRoundRect(225, 110, 150, 80, 20, 20);
		centerText("fillRoundRect()", "drawRoundRect()", g, textcolor, 225, 110, 150, 80);

		// Draw and fill an oval:
		g.setColor(fill);
		g.fillOval(25, 210, 150, 80);
		g.setColor(outline);
		g.drawOval(25, 210, 150, 80);
		centerText("fillOval()", "drawOval()", g, textcolor, 25, 210, 150, 80);

		// Define an octagon using arrays of X and Y coordinates:
		int numpoints = 8;
		int[] xpoints = new int[numpoints + 1];
		int[] ypoints = new int[numpoints + 1];
		for (int i = 0; i < numpoints; i++) {
			double angle = 2 * Math.PI * i / numpoints;
			xpoints[i] = (int) (300 + 75 * Math.cos(angle));
			ypoints[i] = (int) (250 - 40 * Math.sin(angle));
		}

		// Draw and fill the polygon:
		g.setColor(fill);
		g.fillPolygon(xpoints, ypoints, numpoints);
		g.setColor(outline);
		g.drawPolygon(xpoints, ypoints, numpoints);
		centerText("fillPolygon()", "drawPolygon()", g, textcolor, 225, 210, 150, 80);

		// Draw a 3D rectangle (clear an area for it first):
		g.setColor(fill);
		g.fillRect(20, 305, 160, 90);
		g.draw3DRect(25, 310, 150, 80, true);
		g.draw3DRect(26, 311, 148, 78, true);
		g.draw3DRect(27, 312, 146, 76, true);
		centerText("draw3DRect()", "x 3", g, textcolor, 25, 310, 150, 80);

		// Draw an image (centered within an area):
		int w = image.getWidth(this);
		int h = image.getHeight(this);
		g.drawImage(image, 225 + (150 - w) / 2, 310 + (80 - h) / 2, this);
		centerText("drawImage()", null, g, textcolor, 225, 310, 150, 80);
	}

	// A utility method to tile an image on the background of the component.
	protected void tile(Graphics g, Component c, Image i) {
		/*
		 * Use bounds() instead of getBounds() if you want compatibility with Java 1.0
		 * and old browsers like Netscape 3.
		 */
		// How big is the component?
		Rectangle r = c.getBounds();
		// How big is the image?
		int iw = i.getWidth(c);
		int ih = i.getHeight(c);
		if ((iw <= 0) || (ih <= 0)) {
			return;
		}
		// Loop horizontally:
		for (int x = 0; x < r.width; x += iw) {
			// Loop vertically:
			for (int y = 0; y < r.height; y += ih) {
				// Draw the image:
				g.drawImage(i, x, y, c);
			}
		}
	}

	/**
	 * A utility method to center two lines of text in a rectangle. This relies on
	 * the FontMetrics obtained in the init() method.
	 */
	protected void centerText(String string01, String string02, Graphics graphics, Color color, int rectangleX,
			int rectangleY, int rectangleWidth, int rectangleHeight) {
		// How tall is the font?
		int fontHeight = metrics.getHeight();
		// Where is the font baseline?
		int ascent = metrics.getAscent();
		int stringWidth01 = 0, stringWidth02 = 0;
		int string01X = 0, string02X = 0;
		int string01Y = 0, string02Y = 0;
		// How wide are the strings?
		stringWidth01 = metrics.stringWidth(string01);
		if (string02 != null) {
			stringWidth02 = metrics.stringWidth(string02);
		}
		// Center the strings horizontally:
		string01X = rectangleX + (rectangleWidth - stringWidth01) / 2;
		string02X = rectangleX + (rectangleWidth - stringWidth02) / 2;
		if (string02 == null) {
			// Center one string vertically:
			string01Y = rectangleY + (rectangleHeight - fontHeight) / 2 + ascent;
		} else {
			// Center two strings vertically:
			string01Y = rectangleY + (rectangleHeight - (int) (fontHeight * 2.2)) / 2 + ascent;
			string02Y = string01Y + (int) (fontHeight * 1.2);
		}
		// Set the color:
		graphics.setColor(color);
		// Draw the strings:
		graphics.drawString(string01, string01X, string01Y);
		if (string02 != null) {
			graphics.drawString(string02, string02X, string02Y);
		}
	}
}
