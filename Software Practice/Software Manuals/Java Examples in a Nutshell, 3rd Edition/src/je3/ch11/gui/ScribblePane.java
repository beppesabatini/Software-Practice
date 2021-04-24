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

import java.awt.AWTEvent;
import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.List;
import java.util.ArrayList;

import je3.ch12.graphics.PolyLine;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 282-284. This custom
 * component allows the user to scribble, and retains the scribbles so that they
 * can be redrawn when needed. It uses the PolyLine custom Shape implementation
 * defined in chapter 12, and demonstrates event handling with low-level event
 * processing methods.
 */
public class ScribblePane extends JPanelDemoable {

	private static final long serialVersionUID = -1474559319582058397L;
	// The PolyLines that comprise this scribble:
	List<PolyLine> lines;
	// The PolyLine currently being drawn:
	PolyLine currentLine;
	// How to draw the lines:
	Stroke stroke;

	public ScribblePane() {
		// We need a default size:
		setPreferredSize(new Dimension(450, 200));
		// Initialize a list of lines:
		lines = new ArrayList<PolyLine>();
		// Lines are 3 pixels wide:
		stroke = new BasicStroke(3.0f);

		/*
		 * Register interest in mouse button and mouse motion events, so that
		 * processMouseEvent() and processMouseMotionEvent() will be invoked, even if no
		 * event listeners are registered.
		 */
		enableEvents(AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK);
	}

	/** We override this method to draw ourselves. */
	public void paintComponent(Graphics graphics) {
		// Let the superclass do its painting first.
		super.paintComponent(graphics);

		/*
		 * Make a copy of the Graphics context, so we can modify it. We cast it at the
		 * same time, so we can use Java2D graphics.
		 */
		Graphics2D graphics2D = (Graphics2D) (graphics.create());

		// Our superclass doesn't paint the background, so do this ourselves.
		graphics2D.setColor(getBackground());
		graphics2D.fillRect(0, 0, getWidth(), getHeight());

		// Set the line width and color to use for the foreground.
		graphics2D.setStroke(stroke);
		graphics2D.setColor(this.getForeground());

		// Now loop through the PolyLine shapes and draw them all.
		int numlines = lines.size();
		for (int i = 0; i < numlines; i++) {
			graphics2D.draw(lines.get(i));
		}
	}

	/**
	 * Erase all lines and repaint. This method is for the convenience of programs
	 * that use this component.
	 */
	public void clear() {
		lines.clear();
		repaint();
	}

	/**
	 * We override this method to receive notification of mouse button events. See
	 * also the enableEvents() call in the constructor method.
	 */
	public void processMouseEvent(MouseEvent mouseEvent) {
		// If the type and button are correct, then process it.
		if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
			if (mouseEvent.getID() == MouseEvent.MOUSE_PRESSED) {
				// Start a new line on mouse down
				currentLine = new PolyLine(mouseEvent.getX(), mouseEvent.getY());
				lines.add(currentLine);
				mouseEvent.consume();
			} else if (mouseEvent.getID() == MouseEvent.MOUSE_RELEASED) {
				// End the line on mouse up
				currentLine = null;
				mouseEvent.consume();
			}
		}

		// The superclass method dispatches to registered event listeners.
		super.processMouseEvent(mouseEvent);
	}

	/**
	 * We override this method to receive notification of mouse motion events.
	 */
	public void processMouseMotionEvent(MouseEvent mouseEvent) {
		// If we're dragging the mouse and a line exists, add a line segment.
		if (mouseEvent.getID() == MouseEvent.MOUSE_DRAGGED && currentLine != null) {
			currentLine.addSegment(mouseEvent.getX(), mouseEvent.getY());
			mouseEvent.consume();

			/*
			 * Redraw the whole component. We could optimize this by passing the bounding
			 * box of the region that needs redrawing to the repaint() method. Don't forget
			 * to take line width into account, however.
			 */
			repaint();
		}
		super.processMouseMotionEvent(mouseEvent);
	}

	public static class Demo {
		public static void main(String[] args) {
			launchInShowBean("je3.ch11.gui.ScribblePane", 500, 300);
			System.out.println("Demo for ScribblePane. Not all menu items are functional.");
		}
	}
}
