package ch02;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * From Learning Java, 3rd Edition, pp. 49-50. This version of "Hello Java" adds
 * a button to change the color of the string. Users can still mouse-drag around
 * the colorful string.
 */
public class HelloJava3 {
	public static void main(String[] args) {
		JFrame frame = new JFrame("HelloJava3");
		frame.add(new HelloComponent3("Hello Java!"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 300);
		frame.setVisible(true);
	}
}

class HelloComponent3 extends JComponent implements MouseMotionListener, ActionListener {

	private static final long serialVersionUID = 2770053230479337193L;

	String theMessage;
	int messageX = 115, messageY = 95; // Coordinates of the message

	JButton changeColorButton;

	int colorIndex; // The current index into the someColors[] array.
	static Color[] someColors = { Color.black, Color.red, Color.green, Color.blue, Color.magenta };

	public HelloComponent3(String message) {
		theMessage = message;
		changeColorButton = new JButton("Change Color");
		// Tells the component how to arrange components which are
		// added to it. A FlowLayout is a LayoutManager, one of the
		// standard ones.
		setLayout(new FlowLayout());
		add(changeColorButton);
		changeColorButton.addActionListener(this);
		addMouseMotionListener(this);
	}

	public void paintComponent(Graphics g) {
		g.drawString(theMessage, messageX, messageY);
	}

	// Implements a MouseMotionListener interface function.
	public void mouseDragged(MouseEvent e) {
		messageX = e.getX();
		messageY = e.getY();
		repaint();
	}

	// Implements a MouseMotionListener interface function.
	public void mouseMoved(MouseEvent e) {
	}

	public void actionPerformed(ActionEvent e) {
		// Did somebody push the change-color button?
		if (e.getSource() == changeColorButton) {
			changeColor();
		}
	}

	synchronized private void changeColor() {
		// Change the index to the next color, awkwardly.
		// This will rotate through the colors array.
		// colorIndex = colorIndex + 1;
		// The above is not thread safe; a different thread
		// might be running here before the out-of-bounds condition
		// is tested. That's why this function is synchronized.
		if (++colorIndex == someColors.length) {
			colorIndex = 0;
		}
		setForeground(currentColor()); // Use the new color.
		repaint(); // Paint again so we can see the change.
	}

	synchronized private Color currentColor() {
		return someColors[colorIndex];
	}
}
