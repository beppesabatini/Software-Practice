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
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 362-363. An applet that
 * displays a simple animation. In the development installation, this is
 * compiled with Java 8. To test or demo this, switch Workspace to "Software
 * Practice - Experimental".
 */
@SuppressWarnings("deprecation") // This class actually compiles and runs correctly when Java 8 is used.
public class BouncingCircle extends Applet implements Runnable {
	private static final long serialVersionUID = 4841796496825813739L;

	// Position and radius of the circle:
	int x = 150, y = 50, radius = 50;
	// Trajectory of the circle:
	int deltaX = 11, deltaY = 7;
	// The thread that performs the animation:
	Thread animator;
	// A flag to ask the thread to stop:
	volatile boolean pleaseStop;

	/** This method simply draws the circle at its current position */
	public void paint(Graphics g) {
		g.setColor(Color.red);
		g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
	}

	/**
	 * This method moves (and bounces) the circle and then requests a redraw. The
	 * animator thread calls this method periodically.
	 */
	public void animate() {
		// Bounce if we've hit an edge.
		Rectangle bounds = getBounds();
		if ((x - radius + deltaX < 0) || (x + radius + deltaX > bounds.width)) {
			deltaX = -deltaX;
		}
		if ((y - radius + deltaY < 0) || (y + radius + deltaY > bounds.height)) {
			deltaY = -deltaY;
		}

		// Move the circle.
		x += deltaX;
		y += deltaY;

		/*
		 * Ask the browser to call our paint() method to draw the circle at its new
		 * position.
		 */
		repaint();
	}

	/**
	 * This method is from the Runnable interface. It is the body of the thread that
	 * performs the animation. The thread itself is created and started in the
	 * start() method.
	 */
	@Override
	public void run() {
		// Loop until we're asked to stop:
		while (!pleaseStop) {
			// Update and request redraw:
			animate();
			// Wait 100 milliseconds:
			try {
				Thread.sleep(100);
			}
			// Ignore interruptions:
			catch (InterruptedException e) {
			}
		}
	}

	/** Start animating when the browser starts the applet */
	public void start() {
		// Create a thread:
		animator = new Thread(this);
		// Don't ask it to stop yet:
		pleaseStop = false;
		// Start the thread:
		animator.start();
		/*
		 * The thread that called start now returns to its caller. Meanwhile, the new
		 * animator thread has called the run() method.
		 */
	}

	/** Stop animating when the browser stops the applet */
	public void stop() {
		// Set the flag that causes the run() method to end.
		pleaseStop = true;
	}
}
