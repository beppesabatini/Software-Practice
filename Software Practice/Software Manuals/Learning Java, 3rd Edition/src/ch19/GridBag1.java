package ch19;

import java.awt.*;
import javax.swing.*;

/**
 * From Learning Java, 3rd Edition, p. 674-675.
 */
public class GridBag1 extends JPanel {

	private static final long serialVersionUID = -438632096254633577L;
	GridBagConstraints constraints = new GridBagConstraints();

	public GridBag1() {
		setLayout(new GridBagLayout());
		int x, y; // for clarity
		x = 1;
		y = 0;
		addGB(new JButton("North"), x, y);
		addGB(new JButton("West"), x = 0, y = 1);
		addGB(new JButton("Center"), x = 1, y = 1);
		addGB(new JButton("East"), x = 2, y = 1);
		addGB(new JButton("South"), x = 1, y = 2);
	}

	void addGB(Component component, int x, int y) {
		constraints.gridx = x;
		constraints.gridy = y;
		add(component, constraints);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("GridBag1");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(225, 150);
		frame.setLocation(200, 200);
		frame.setContentPane(new GridBag1());
		frame.setVisible(true);
	}
}
