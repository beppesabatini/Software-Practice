package ch17;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import utils.LearningJava3Utils;

/**
 * Not in the manual. Includes three buttons which don't do anything, and a
 * right-click (context menu) which is supposed to change the background color
 * (not the button color), but instead,only throws a stack trace. 
 */
public class PopUpColorMenu2 implements ActionListener {
	Component selectedComponent;

	public PopUpColorMenu2() {
		JFrame frame = new JFrame("PopUpColorMenu v2.0");

		JPanel panel = new JPanel();
		JButton button = new JButton("Uno");
		button.setInheritsPopupMenu(true);
		panel.add(button);
		button = new JButton("Due");
		button.setInheritsPopupMenu(true);
		panel.add(button);
		button = new JButton("Tre");
		button.setInheritsPopupMenu(true);
		panel.add(button);

		final JPopupMenu colorMenu = new JPopupMenu("Color");
		colorMenu.add(makeMenuItem("Red"));
		colorMenu.add(makeMenuItem("Green"));
		colorMenu.add(makeMenuItem("Blue"));
		panel.setComponentPopupMenu(colorMenu);
		panel.setBackground(Color.BLUE);

		frame.add(BorderLayout.CENTER, panel);
		frame.setSize(200, 75);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		String color = e.getActionCommand();
		if (color.equals("Red")) {
			selectedComponent.setBackground(Color.red);
		} else if (color.equals("Green")) {
			selectedComponent.setBackground(Color.green);
		} else if (color.equals("Blue")) {
			selectedComponent.setBackground(Color.blue);
		}
	}

	private JMenuItem makeMenuItem(String label) {
		JMenuItem item = new JMenuItem(label);
		item.addActionListener(this);
		return item;
	}

	public static void main(String[] args) {
		LearningJava3Utils.confirmContinueWithDisfunctional();
		new PopUpColorMenu2();
	}
}
