package ch19;

import java.awt.*;
import javax.swing.*;

/**
 * From p. 668. Another demonstration of BorderLayout, now with each JButton in
 * its own panel.
 */
public class Border2 extends JPanel {

	private static final long serialVersionUID = -6852060248207982333L;

	public Border2() {
		setLayout(new BorderLayout());
		JPanel p = new JPanel();
		p.add(new JButton("North"));
		add(p, BorderLayout.NORTH);
		p = new JPanel();
		p.add(new JButton("South"));
		add(p, BorderLayout.SOUTH);
		p = new JPanel();
		p.add(new JButton("East"));
		add(p, BorderLayout.EAST);
		p = new JPanel();
		p.add(new JButton("West"));
		add(p, BorderLayout.WEST);
		p = new JPanel();
		p.add(new JButton("Center"));
		add(p, BorderLayout.CENTER);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Border2");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(225, 150);
		frame.setLocation(200, 200);
		frame.setContentPane(new Border2());
		frame.setVisible(true);
	}
}
