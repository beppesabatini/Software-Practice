package ch19;

import java.awt.*;
import javax.swing.*;

// From p. 671. 

public class GridBag5 extends JPanel {

	private static final long serialVersionUID = -8148169103874001652L;

	GridBagConstraints constraints = new GridBagConstraints();

	public GridBag5() {
		setLayout(new GridBagLayout());
		int x, y; // for clarity
		x = 1; y = 0;
		addGB(new JButton("North"), x, y);
		constraints.ipadx = 25; // add padding
		constraints.ipady = 25;
		addGB(new JButton("West"), x = 0, y = 1);
		constraints.ipadx = 0; // remove padding
		constraints.ipady = 0;
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
		JFrame frame = new JFrame("GridBag5");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setSize(250, 250);
		frame.setLocation(200, 200);
		frame.setContentPane(new GridBag5());
		frame.setVisible(true);
	}
}
