package ch17;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * From p. 593-594. One specified list of options is displayed as both a combo
 * box (a drop-down selector) and a scroll pane. Only the input to the scroll
 * pane will be displayed.
 */
public class Lister {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Lister v1.0");

		// Create a combo box
		String[] items = { "uno", "due", "tre", "quattro", "cinque", "sei", "sette", "otto", "nove", "deici", "undici",
				"dodici" };
		JComboBox<String> comboBox = new JComboBox<String>(items);
		comboBox.setEditable(true);

		// Create a list with the same data model.
		final JList<String> list = new JList<String>(comboBox.getModel());

		// Create a button; when it's pressed, print out
		// the selection in the list.
		JButton button = new JButton("Per favore");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				@SuppressWarnings("deprecation")
				Object[] selection = list.getSelectedValues();
				System.out.println("-----");
				for (Object s : selection)
					System.out.println(s);
			}
		});

		// Put the controls in the content pane.
		Container c = frame.getContentPane(); // unnecessary in 1.5+
		JPanel comboPanel = new JPanel();
		comboPanel.add(comboBox);
		c.add(comboPanel, BorderLayout.NORTH);
		c.add(new JScrollPane(list), BorderLayout.CENTER);
		c.add(button, BorderLayout.SOUTH);

		frame.setSize(200, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
