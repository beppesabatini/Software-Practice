package ch17;

import java.awt.*;
import javax.swing.*;

/**
 * From Learning Java, 3rd Edition, p. 610-611. Displays two photos in a
 * split-panel frame. Note the syntax in the Java code for accessing image files
 * on disk.
 */
public class SplitPaneFrame {
	public static void main(String[] args) {
		String fileOne = "src/ch17/Piazza di Spagna.jpg";
		String fileTwo = "src/ch17/L1-Light.jpg";
		if (args.length > 0)
			fileOne = args[0];
		if (args.length > 1)
			fileTwo = args[1];

		JFrame frame = new JFrame("SplitPaneFrame");

		JLabel leftImage = new JLabel(new ImageIcon(fileOne));
		Component left = new JScrollPane(leftImage);
		JLabel rightImage = new JLabel(new ImageIcon(fileTwo));
		Component right = new JScrollPane(rightImage);

		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right);
		split.setDividerLocation(100);
		frame.getContentPane().add(split);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 200);
		frame.setVisible(true);
	}
}
