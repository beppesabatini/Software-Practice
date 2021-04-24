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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 268-269. 
 */
public class GridBagLayoutPane extends JPanelDemoable {

	private static final long serialVersionUID = -4911024868928310708L;

	public GridBagLayoutPane() {
		// Create and specify a layout manager.
		this.setLayout(new GridBagLayout());

		// Create a constraints object, and specify some default values.
		GridBagConstraints constraints = new GridBagConstraints();
		// Components grow in both dimensions.
		constraints.fill = GridBagConstraints.BOTH;
		// 5-pixel margins on all sides.
		constraints.insets = new Insets(5, 5, 5, 5);

		/*
		 * Create and add a bunch of buttons, specifying different grid position, and
		 * size for each. Give the first button a resize weight of 1.0 and all others a
		 * weight of 0.0. The first button will get all the extra space.
		 */
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 4;
		constraints.gridheight = 4;
		constraints.weightx = constraints.weighty = 1.0;
		this.add(new JButton("Button #1"), constraints);

		constraints.gridx = 4;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.weightx = constraints.weighty = 0.0;
		this.add(new JButton("Button #2"), constraints);

		constraints.gridx = 4;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(new JButton("Button #3"), constraints);

		constraints.gridx = 4;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.gridheight = 2;
		this.add(new JButton("Button #4"), constraints);

		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(new JButton("Button #5"), constraints);

		constraints.gridx = 2;
		constraints.gridy = 4;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(new JButton("Button #6"), constraints);

		constraints.gridx = 3;
		constraints.gridy = 4;
		constraints.gridwidth = 2;
		constraints.gridheight = 1;
		this.add(new JButton("Button #7"), constraints);

		constraints.gridx = 1;
		constraints.gridy = 5;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(new JButton("Button #8"), constraints);

		constraints.gridx = 3;
		constraints.gridy = 5;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		this.add(new JButton("Button #9"), constraints);
	}

	public static class Demo {
		public static void main(String[] args) {
			launchInShowBean("je3.ch11.gui.GridBagLayoutPane", 500, 300);
			System.out.println("Demo for GridBagLayoutPane. The buttons are non-functional.");
		}
	}
}
