/*
 * Copyright (c) 2004 David Flanagan.  All rights reserved.
 * This code is from the book Java Examples in a Nutshell, 3nd Edition.
 * It is provided AS-IS, WITHOUT ANY WARRANTY either expressed or implied.
 * You may study, use, and modify it for any non-commercial purpose,
 * including teaching and use in open-source projects.
 * You may distribute it non-commercially as long as you retain this notice.
 * For a commercial use license, or to purchase the book, 
 * please visit http://www.davidflanagan.com/javaexamples3.
 */
package je3.ch11.gui;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 265-267. 
 */
public class BoxLayoutPane extends JPanelDemoable {

	private static final long serialVersionUID = 1835967559101331462L;

	public BoxLayoutPane() {
		// Use a BorderLayout layout manager to arrange various Box components.
		this.setLayout(new BorderLayout());

		/*
		 * Give the entire panel a margin by adding an empty border. We could also do
		 * this by overriding getInsets().
		 */
		this.setBorder(new EmptyBorder(10, 10, 10, 10));

		// Add a plain row of buttons along the top of the pane.
		Box topRow = Box.createHorizontalBox();
		for (int i = 0; i < 4; i++) {
			JButton jButton = new JButton("B" + i);
			jButton.setFont(new Font("serif", Font.BOLD, 12 + i * 2));
			topRow.add(jButton);
		}
		this.add(topRow, BorderLayout.NORTH);

		/*
		 * Add a plain column of buttons along the right edge. Use BoxLayout with a
		 * different kind of Swing container. Give the column a border; we can't do that
		 * with the Box class.
		 */
		JPanel rightColumn = new JPanel();
		rightColumn.setLayout(new BoxLayout(rightColumn, BoxLayout.Y_AXIS));
		rightColumn.setBorder(new TitledBorder(new EtchedBorder(), "Column"));
		for (int i = 0; i < 4; i++) {
			JButton jButton = new JButton("Button " + i);
			jButton.setFont(new Font("sanserif", Font.BOLD, 10 + i * 2));
			rightColumn.add(jButton);
		}
		// Add a column to the right of the panel.
		this.add(rightColumn, BorderLayout.EAST);

		/*
		 * Add a button box along the bottom of the panel. Use "Glue" to space the
		 * buttons evenly with stretchy space.
		 */
		Box buttonbox = Box.createHorizontalBox();

		buttonbox.add(Box.createHorizontalGlue());
		buttonbox.add(new JButton("Okay"));
		buttonbox.add(Box.createHorizontalGlue());
		buttonbox.add(new JButton("Cancel"));
		buttonbox.add(Box.createHorizontalGlue());
		buttonbox.add(new JButton("Help"));
		buttonbox.add(Box.createHorizontalGlue());

		this.add(buttonbox, BorderLayout.SOUTH);

		// Create a component to display in the center of the panel.
		JTextArea jTextArea = new JTextArea();
		String textAreaText = "";
		textAreaText += "This component has 12-pixel margins on left and top";
		textAreaText += " and has 72-pixel margins on right and bottom.";
		jTextArea.setText(textAreaText);
		jTextArea.setLineWrap(true);
		jTextArea.setWrapStyleWord(true);

		/*
		 * Use Box objects to give the JTextArea an unusual spacing. First, create a
		 * column with 3 children. The first and last children are rigid spaces. The
		 * middle child is the text area.
		 */
		Box fixedcol = Box.createVerticalBox();
		// 12 rigid pixels:
		fixedcol.add(Box.createVerticalStrut(12));
		// The JTextArea fills the in-between space:
		fixedcol.add(jTextArea);
		// 72 rigid pixels:
		fixedcol.add(Box.createVerticalStrut(72));

		/*
		 * Now create a row. Give it rigid spaces on the left and right, and put the
		 * column from above in the middle.
		 */
		Box fixedrow = Box.createHorizontalBox();
		fixedrow.add(Box.createHorizontalStrut(12));
		fixedrow.add(fixedcol);
		fixedrow.add(Box.createHorizontalStrut(72));

		// Now add the JTextArea in the column in the row to the panel.
		this.add(fixedrow, BorderLayout.CENTER);
	}

	public static class Demo {
		public static void main(String[] args) {
			launchInShowBean("je3.ch11.gui.BoxLayoutPane", 300, 200);
			System.out.println("Demo for BoxLayoutPane. The buttons are non-functional.");
		}
	}
}
