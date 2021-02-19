package ch19;

import javax.swing.*;

// From p. 664.

public class Flow extends JPanel {

	private static final long serialVersionUID = 6694085488341694308L;

	public Flow() {
		// FlowLayout is default layout manager for a JPanel
		add(new JButton("One"));
		add(new JButton("Two"));
		add(new JButton("Three"));
		add(new JButton("Four"));
		add(new JButton("Five"));
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Flow");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setSize(400, 75);
		frame.setLocation(200, 200);
		Flow flow = new Flow();
		frame.setContentPane(flow);
		frame.setVisible(true);
	}
}
