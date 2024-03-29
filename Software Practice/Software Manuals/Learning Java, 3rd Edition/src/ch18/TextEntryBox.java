package ch18;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * From Learning Java, 3rd Edition, p. 623-624. Text can be entered into a given
 * text area, either directly, or through a separate associated text field.
 */
public class TextEntryBox {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Text Entry Box");

		final JTextArea area = new JTextArea();
		area.setFont(new Font("Serif", Font.BOLD, 18));
		area.setText("Howdy!\n");
		final JTextField field = new JTextField();

		Container content = frame.getContentPane();
		content.add(new JScrollPane(area), BorderLayout.CENTER);
		content.add(field, BorderLayout.SOUTH);
		field.requestFocus();

		field.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				area.append(field.getText() + '\n');
				field.setText("");
			}
		});

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(200, 300);
		frame.setVisible(true);
	}
}
