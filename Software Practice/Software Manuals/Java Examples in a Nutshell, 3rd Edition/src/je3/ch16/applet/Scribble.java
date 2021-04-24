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
package je3.ch16.applet;

import java.applet.Applet;
import java.awt.Button;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 499-500. Deprecated!! This
 * applet lets the user scribble with the mouse. Note this demonstrates the Java
 * 1.0 event model, which is deprecated. All but one of the methods below
 * override functions in Component. This deprecated event model is included here
 * for historical purposes.
 */
public class Scribble extends Applet {

	private static final long serialVersionUID = -6457891859891866524L;

	// Remember last mouse coordinates:
	private int lastMouseX, lastMouseY;
	private Button eraseButton;

	/** Initialize the erase button, ask for keyboard focus. */
	@Override
	public void init() {
		eraseButton = new Button("Erase");
		this.add(eraseButton);
		// Set background color for the scribble:
		this.setBackground(Color.white);
		// Ask for keyboard focus so that we get the key events:
		this.requestFocus();
	}

	/** Respond to mouse clicks. */
	@Override
	public boolean mouseDown(Event event, int mouseClickX, int mouseClickY) {
		// Remember where the click was:
		lastMouseX = mouseClickX;
		lastMouseY = mouseClickY;
		return true;
	}

	/** Respond to mouse drags. */
	@Override
	public boolean mouseDrag(Event event, int mouseDragX, int mouseDragY) {
		Graphics g = getGraphics();
		// Draw from last position to here:
		g.drawLine(lastMouseX, lastMouseY, mouseDragX, mouseDragY);
		// And remember the new last position:
		lastMouseX = mouseDragX;
		lastMouseY = mouseDragY;
		return true;
	}

	/** Respond to key presses: Erase drawing when the user types 'e'. */
	@Override
	public boolean keyDown(Event event, int key) {
		if ((event.id == Event.KEY_PRESS) && (key == 'e')) {
			Graphics graphics = getGraphics();
			graphics.setColor(this.getBackground());
			graphics.fillRect(0, 0, getBounds().width, getBounds().height);
			return true;
		} else
			return false;
	}

	/**
	 * Respond to Button clicks: erase drawing when user clicks button. Note that
	 * (because of the @Override annotation) the compiler will complain if you
	 * delete the unused dummy argument. This is a good thing; you've ensured you
	 * are overriding a function you needed to override.
	 */
	@Override
	public boolean action(Event event, Object dummy) {
		if (event.target == eraseButton) {
			Graphics graphics = getGraphics();
			graphics.setColor(this.getBackground());
			graphics.fillRect(0, 0, getBounds().width, getBounds().height);
			return true;
		} else
			return false;
	}
}
