package ch16;

import javax.swing.*;

/**
 * From Learning Java, 3rd Edition, p. 566. An attempt to show the difference
 * between a JFrame and a JWindow.
 */
public class TopLevelWindows {
	public static void main(String[] args) {
		JFrame f = new JFrame("The Frame");
		f.setSize(300, 300);
		f.setLocation(100, 100);

		JWindow w = new JWindow();
		w.setSize(300, 300);
		w.setLocation(500, 100);

		f.setVisible(true);
		w.setVisible(true);
	}
}
