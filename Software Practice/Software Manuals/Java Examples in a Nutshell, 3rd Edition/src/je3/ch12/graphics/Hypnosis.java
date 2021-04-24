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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 403-405. A Swing component
 * that smoothly animates a spiral in a hypnotic way.
 */
public class Hypnosis extends JComponent implements ActionListener {

	private static final long serialVersionUID = -457634239130422523L;

	// The center of the spiral:
	double centerX, centerY;
	// The inner and outer radii of the spiral:
	double radiusInner, radiusOuter;
	// The start and end angles of the spiral:
	double angleStart, angleEnd;
	// How much the angle changes with each state:
	double deltaA;
	// The trajectory of the center:
	double deltaX, deltaY;
	// How wide the lines are:
	float linewidth;
	// The object that triggers the animation:
	Timer timer;
	// The image we use for double-buffering:
	BufferedImage buffer;
	// Graphics2D object for drawing into the buffer:
	Graphics2D offScreenGraphics2D;

	public static class HypnosisStartState {
		public double centerX;
		public double centerY;
		public double radiusInner;
		public double radiusOuter;
		public double angleStart;
		public double angleEnd;
		public float linewidth;
		public int delay;
		public double deltaA;
		public double deltaX;
		public double deltaY;
	}

	public Hypnosis(HypnosisStartState initialize) {
		this.centerX = initialize.centerX;
		this.centerY = initialize.centerY;
		this.radiusInner = initialize.radiusInner;
		this.radiusOuter = initialize.radiusOuter;
		this.angleStart = initialize.angleStart;
		this.angleEnd = initialize.angleEnd;
		this.linewidth = initialize.linewidth;
		this.deltaA = initialize.deltaA;
		this.deltaX = initialize.deltaX;
		this.deltaY = initialize.deltaY;

		// Set up a timer to call actionPerformed() every delay milliseconds.
		timer = new Timer(initialize.delay, this);

		// Create a buffer for double-buffering.
		int imageWidth = (int) (2 * radiusOuter + linewidth);
		int imageHeight = (int) (2 * radiusOuter + linewidth);
		int imageType = BufferedImage.TYPE_INT_RGB;
		buffer = new BufferedImage(imageWidth, imageHeight, imageType);

		/*
		 * Create a Graphics object for the buffer, and set the line width and request
		 * anti-aliasing when drawing with it.
		 */
		offScreenGraphics2D = buffer.createGraphics();
		offScreenGraphics2D.setStroke(new BasicStroke(linewidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		offScreenGraphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}

	// Start and stop the animation by starting and stopping the timer.
	public void start() {
		timer.start();
	}

	public void stop() {
		timer.stop();
	}

	/**
	 * Swing calls this method to ask the component to redraw itself. This method
	 * uses double-buffering to make the animation smoother. Swing does
	 * double-buffering automatically, so this may not actually make much
	 * difference, but it is important to understand the technique.
	 */
	public void paintComponent(Graphics graphics) {
		// Clear the background of the off-screen image.
		offScreenGraphics2D.setColor(getBackground());
		offScreenGraphics2D.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());

		// Now draw a black spiral into the off-screen image.
		offScreenGraphics2D.setColor(Color.black);
		double spiralCenterX = radiusOuter + linewidth / 2;
		double spiralCenterY = radiusOuter + linewidth / 2;
		Spiral newSpiral = new Spiral(spiralCenterX, spiralCenterY, radiusInner, angleStart, radiusOuter, angleEnd);
		offScreenGraphics2D.draw(newSpiral);

		// Now copy that off-screen image onto the screen.
		graphics.drawImage(buffer, (int) (centerX - radiusOuter), (int) (centerY - radiusOuter), this);
	}

	/**
	 * This method implements the ActionListener interface. Our Timer object calls
	 * this method periodically. It updates the position and angles of the spiral
	 * and requests a redraw. Instead of redrawing the entire component, however,
	 * this method requests a redraw only for the area that has changed.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		/*
		 * Ask to have the old bounding box of the spiral redrawn. Nothing else has
		 * anything drawn in it, so it doesn't need a redraw.
		 */
		int oldRepaintX = (int) (centerX - radiusOuter - linewidth);
		int oldRepaintY = (int) (centerY - radiusOuter - linewidth);
		int oldRepaintWidth = (int) (2 * (radiusOuter + linewidth));
		int oldRepaintHeight = (int) (2 * (radiusOuter + linewidth));
		repaint(oldRepaintX, oldRepaintY, oldRepaintWidth, oldRepaintHeight);

		// Now animate: update the position and angles of the spiral.

		// Bounce if we've hit an edge.
		Rectangle bounds = getBounds();
		if ((centerX - radiusOuter + deltaX < 0) || (centerX + radiusOuter + deltaX > bounds.width)) {
			deltaX = -deltaX;
		}
		if ((centerY - radiusOuter + deltaY < 0) || (centerY + radiusOuter + deltaY > bounds.height)) {
			deltaY = -deltaY;
		}

		// Move the center of the spiral:
		centerX += deltaX;
		centerY += deltaY;

		// Increment the start and end angles:
		angleStart += deltaA;
		angleEnd += deltaA;
		// Don't let them get too big:
		if (angleStart > 2 * Math.PI) {
			angleStart -= 2 * Math.PI;
			angleEnd -= 2 * Math.PI;
		}

		/*
		 * Now ask to have the new bounding box of the spiral redrawn. This rectangle
		 * will be intersected with the redraw rectangle requested above, and only the
		 * combined region will be redrawn.
		 */
		int newRepaintX = (int) (centerX - radiusOuter - linewidth);
		int newRepaintY = (int) (centerY - radiusOuter - linewidth);
		int newRepaintWidth = (int) (2 * (radiusOuter + linewidth));
		int newRepaintHeight = (int) (2 * (radiusOuter + linewidth));
		repaint(newRepaintX, newRepaintY, newRepaintWidth, newRepaintHeight);
	}

	/** Tell Swing not to double-buffer for us, since we do our own */
	public boolean isDoubleBuffered() {
		return false;
	}

	/**
	 * This is a main() method for testing the component.
	 */
	public static void main(String[] args) {
		JFrame jFrame = new JFrame("Hypnosis");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		HypnosisStartState initialize = new Hypnosis.HypnosisStartState();
		initialize.centerX = 200;
		initialize.centerY = 200;
		initialize.radiusInner = 10;
		initialize.radiusOuter = 100;
		initialize.angleStart = 0;
		initialize.angleEnd = 5 * Math.PI;
		initialize.linewidth = 10;
		initialize.delay = 100;
		initialize.deltaA = 2 * Math.PI / 15;
		initialize.deltaX = 3;
		initialize.deltaY = 5;

		Hypnosis hypnosis = new Hypnosis(initialize);
		jFrame.getContentPane().add(hypnosis, BorderLayout.CENTER);
		jFrame.setSize(400, 400);
		jFrame.setVisible(true);
		hypnosis.start();
	}
}
