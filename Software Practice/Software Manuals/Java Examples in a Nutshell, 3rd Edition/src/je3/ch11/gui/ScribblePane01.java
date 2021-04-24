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

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 277-278. A simple JPanel
 * subclass that uses event listeners to allow the user to scribble with the
 * mouse. Note that scribbles are not saved or redrawn.
 */
public class ScribblePane01 extends JPanelDemoable implements MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 5422788664257081423L;

	// Previous mouse coordinates:
	protected int last_x, last_y;

	public ScribblePane01() {
		/*
		 * This component registers itself as an event listener for mouse events and
		 * mouse motion events.
		 */
		this.addMouseListener(this);
		this.addMouseMotionListener(this);

		// Give the component a preferred size:
		setPreferredSize(new Dimension(450, 200));
	}

	/*
	 * A method from the MouseListener interface. Invoked when the user presses a
	 * mouse button.
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		// Remember the coordinates of the click:
		last_x = e.getX();
		last_y = e.getY();
	}

	// A method from the MouseMotionListener interface. Invoked when the
	// user drags the mouse with a button pressed.
	@Override
	public void mouseDragged(MouseEvent e) {
		// Get the current mouse position:
		int x = e.getX();
		int y = e.getY();
		// Draw a line from the saved coordinates to the current position:
		this.getGraphics().drawLine(last_x, last_y, x, y);
		// Remember the current position:
		last_x = x;
		last_y = y;
	}

	// The other, unused methods of the MouseListener interface.
	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	// The other, unused, method of the MouseMotionListener interface.
	@Override
	public void mouseMoved(MouseEvent e) {
	}

	public static class Demo {
		public static void main(String[] args) {
			launchInShowBean("je3.ch11.gui.ScribblePane01", 500, 300);
			System.out.println("Demo for ScribblePane01. Not all menu items are functional.");
		}
	}
}
