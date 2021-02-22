package ch02;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * From Learning Java, 3rd Edition, p. 42-43. This version allows the user to
 * use the mouse and drag around the Hello World string.
 */
public class HelloJava2 {
	public static void main(String[] args) {
		// The frame variable is a "local variable."
		// Local variables don't get automatically initialized (to null).
		JFrame frame = new JFrame("HelloJava2");
		frame.add(new HelloComponent2("Hello Java!"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 300);
		frame.setVisible(true);
	}
}

class HelloComponent2 extends JComponent implements MouseMotionListener {
// JComponent extends Container extends Component extends Object
	private static final long serialVersionUID = 9010910788876912776L;

	// The next three are called "instance variables." They are duplicated
	// in each instance (or instantiation).
	String theMessage;
	// Coordinates of the message
	int messageX = 125, messageY = 95;

	public HelloComponent2(String message) {
		theMessage = message;
		// Calls a Component function
		addMouseMotionListener(this);
	}

	// Called from a JComponent function.
	public void paintComponent(Graphics g) {
		g.drawString(theMessage, messageX, messageY);
	}

	// Called from a Component function.
	public void mouseDragged(MouseEvent e) {
		// Save the mouse coordinates and paint the message.
		messageX = e.getX();
		messageY = e.getY();
		// Another Component function. This forks a thread.
		repaint();
	}

	public void mouseMoved(MouseEvent e) {
	}
}
