package ch02;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * From Learning Java, 3rd Edition, pp. 58-59. In this version of HelloJava, the
 * text string blinks every 300 milliseconds. The user can still change the
 * string's color and drag it around.
 */
public class HelloJava4 {
	public static void main(String[] args) {
		JFrame frame = new JFrame("HelloJava4");
		frame.add(new HelloComponent4("Hello Java!"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 300);
		frame.setVisible(true);
	}
}

class HelloComponent4 extends JComponent implements MouseMotionListener, ActionListener, Runnable {

	private static final long serialVersionUID = 1941620542804144535L;

	String theMessage;
	int messageX = 115, messageY = 95; // Coordinates of the message

	JButton colorChangeButton;

	int colorIndex; // Current index into the someColors array (below).
	static Color[] someColors = { Color.black, Color.red, Color.green, Color.blue, Color.magenta };

	boolean blinkState;

	public HelloComponent4(String message) {
		theMessage = message;
		colorChangeButton = new JButton("Change Color");
		setLayout(new FlowLayout());
		add(colorChangeButton);
		colorChangeButton.addActionListener(this);
		addMouseMotionListener(this);
		Thread t = new Thread(this);
		t.start();
	}

	public void paintComponent(Graphics g) {
		g.setColor(blinkState ? getBackground() : currentColor());
		g.drawString(theMessage, messageX, messageY);
	}

	public void mouseDragged(MouseEvent e) {
		messageX = e.getX();
		messageY = e.getY();
		repaint();
	}

	public void mouseMoved(MouseEvent e) {
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == colorChangeButton) {
			changeColor();
		}
	}

	synchronized private void changeColor() {
		colorIndex = colorIndex + 1;
		// This function is now synchronized, to
		// prevent a different thread from accessing
		// the colorIndex before the out-of-bounds
		// handling is invoked.
		if (colorIndex == someColors.length) {
			colorIndex = 0;
		}
		setForeground(currentColor());
		repaint();
	}

	synchronized private Color currentColor() {
		return someColors[colorIndex];
	}

	public void run() {
		try {
			while (true) {
				blinkState = !blinkState; // Toggle blinkState.
				repaint(); // Show the change.
				// The sleep() function throws an InterruptedException if it
				// is interrupted by another thread, so a try/catch is needed.
				Thread.sleep(300);
			}
		} catch (InterruptedException ie) {
		}
	}
}
