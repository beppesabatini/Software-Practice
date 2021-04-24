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
package je3.ch11.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.Graphics;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 278-280. A simple JPanel
 * subclass that uses event listeners to allow the user to scribble with the
 * mouse. Note that scribbles are not saved or redrawn.
 */
public class ScribblePane02 extends JPanelDemoable {

	private static final long serialVersionUID = 338736953370646144L;

	public ScribblePane02() {
		// Give the component a preferred size.
		setPreferredSize(new Dimension(450, 200));

		/*
		 * Register a mouse event handler defined as an anonymous subclass of
		 * MouseAdapter. Note the call to requestFocus(). This is required in order for
		 * the component to receive key events.
		 */
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent mouseEvent) {
				// Move to click position:
				moveto(mouseEvent.getX(), mouseEvent.getY());
				// Take keyboard focus:
				requestFocus();
			}
		});

		/*
		 * Register a mouse motion event handler defined as an anonymous subclass. By
		 * subclassing the abstract MouseMotionAdapter, rather than implementing
		 * MouseMotionListener, we only override the method we're interested in, and
		 * inherit a default (empty) implementation of the other method.
		 */
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				// Draw to mouse position:
				lineto(e.getX(), e.getY());
			}
		});

		/*
		 * Add a keyboard event handler to clear the screen on key 'C'. Again, this is a
		 * lot like an anonymous subclass of the abstract KeyAdapter class.
		 */
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_C)
					clear();
			}
		});
	}

	/** These are the coordinates of the the previous mouse position. */
	protected int last_x, last_y;

	/** Remember the specified point */
	public void moveto(int x, int y) {
		last_x = x;
		last_y = y;
	}

	/** Draw from the last point to this point, then remember new point */
	public void lineto(int x, int y) {
		// Get the object to draw with:
		Graphics graphics = getGraphics();
		// Tell it what color to use:
		graphics.setColor(color);
		// Tell it what to draw:
		graphics.drawLine(last_x, last_y, x, y);
		// Save the current point:
		moveto(x, y);
	}

	/**
	 * Clear the drawing area, using the component background color. This method
	 * works by requesting that the component be redrawn. Since this component does
	 * not have a paintComponent() method, nothing will be drawn. However, other
	 * parts of the component, such as borders or sub-components will be drawn
	 * correctly.
	 **/
	public void clear() {
		repaint();
	}

	/** This field holds the current drawing color property. */
	Color color = Color.black;

	/** This is the property "setter" method for the color property. */
	public void setColor(Color color) {
		this.color = color;
	}

	/** This is the property "getter" method for the color property. */
	public Color getColor() {
		return color;
	}

	public static class Demo {
		public static void main(String[] args) {
			launchInShowBean("je3.ch11.gui.ScribblePane02", 500, 300);
			System.out.println("Demo for ScribblePane02. Not all menu items are functional");
		}
	}
}
