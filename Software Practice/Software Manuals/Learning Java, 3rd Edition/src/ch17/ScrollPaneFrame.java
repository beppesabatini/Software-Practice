package ch17;

import javax.swing.*;
import java.io.IOException;

/**
 * From p. 609-610. Displays a photo with a scroll frame.
 */
public class ScrollPaneFrame {
	public static void main(String[] args) {

		try {
			String current = new java.io.File(".").getCanonicalPath();
			System.out.println("Current dir:" + current);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String filename = "src/ch17/Piazza di Spagna.jpg";
		if (args.length > 0) {
			filename = args[0];
		}

		JFrame frame = new JFrame("ScrollPaneFrame v1.0");
		JLabel image = new JLabel(new ImageIcon(filename));
		frame.getContentPane().add(new JScrollPane(image));

		frame.setSize(300, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
