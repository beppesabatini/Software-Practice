package ch22;

import javax.swing.*;
import java.awt.event.*;
import java.beans.EventHandler;

/**
 * From Learning Java, 3rd Edition, p. 782-783. A brief look at how reflection
 * can be used to create a generalized ActionListener, without having to specify
 * the type of the object which is listening. Thus it's reusable code for a
 * variety of class type listeners.
 */
public class DynamicHookup extends JFrame {

	private static final long serialVersionUID = -4557474777842164787L;

	JLabel label = new JLabel("Ready...", JLabel.CENTER);
	int count;

	public DynamicHookup() {
		JButton launchButton = new JButton("Launch!");
		getContentPane().add(launchButton, "South");
		getContentPane().add(label, "Center");
		ActionListener actionListener = EventHandler.create(ActionListener.class, this, "launchTheMissiles");
		launchButton.addActionListener(actionListener);
	}

	public void launchTheMissiles() {
		label.setText("Launched: " + count++);
	}

	public static void main(String[] args) {
		JFrame frame = new DynamicHookup();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(150, 150);
		frame.setVisible(true);
	}
}
