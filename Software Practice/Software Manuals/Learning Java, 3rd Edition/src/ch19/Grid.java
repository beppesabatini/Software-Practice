package ch19;

import java.awt.*;
import javax.swing.*;

/**
 * From Learning Java, 3rd Edition, p. 665-666. An example of GridLayout.
 */
public class Grid extends JPanel {

	private static final long serialVersionUID = 9200628286876856501L;

	public Grid() {
		setLayout(new GridLayout(3, 2));
		add(new JButton("One"));
		add(new JButton("Two"));
		add(new JButton("Three"));
		add(new JButton("Four"));
		add(new JButton("Five"));
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Grid");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(200, 200);
		frame.setLocation(200, 200);
		frame.setContentPane(new Grid());
		frame.setVisible(true);
	}
}
