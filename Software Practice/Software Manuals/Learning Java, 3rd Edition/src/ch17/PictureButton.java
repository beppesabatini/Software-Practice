package ch17;

import java.awt.event.*;
import javax.swing.*;

/**
 * From Learning Java, 3rd Edition, p. 587-588. Displays an image in a frame,
 * which is configured as a button, and outputs to the console.
 */
public class PictureButton {
	public static void main(String[] args) {
		JFrame frame = new JFrame();

		Icon icon = new ImageIcon("src/ch17/rhino.gif");
		JButton button = new JButton(icon);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				System.out.println("Urp!");
			}
		});

		frame.getContentPane().add(button);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
