package ch17;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * From Learning Java, 3rd Edition, p. 620-621. A demonstration of a Color
 * Chooser, here used to enable the user to change the background color.
 */
public class LocalColor {
	public static void main(String[] args) {
		final JFrame frame = new JFrame("LocalColor v1.0");
		final Container content = frame.getContentPane(); // unnecessary in 1.5+
		content.setLayout(new GridBagLayout());
		JButton button = new JButton("Change color...");
		content.add(button);

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color c = JColorChooser.showDialog(frame, "Choose a color", content.getBackground());
				if (c != null) {
					content.setBackground(c);
				}
			}
		});

		frame.setSize(200, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
