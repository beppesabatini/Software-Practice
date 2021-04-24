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

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;

/**
 * From Java Examples in a Nutshell, 3rd Edition, p. 262. 
 */
public class FlowLayoutPane extends JPanelDemoable {

	private static final long serialVersionUID = -6752537698623621856L;

	public FlowLayoutPane() {
		/*
		 * Use a FlowLayout layout manager. Left justify rows. Leave 10 pixels of
		 * horizontal and vertical space between components.
		 */
		this.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

		// Add some buttons to demonstrate the layout.
		String spaces = ""; 
		for (int i = 1; i <= 9; i++) {
			// Used to make the buttons different.
			this.add(new JButton("Button #" + i + spaces));
			spaces += " ";
		}

		// Give ourselves a default size.
		this.setPreferredSize(new Dimension(500, 200));
	}
	public static class Demo {
		public static void main(String[] args) {
			launchInShowBean("je3.ch11.gui.FlowLayoutPane", 500, 200);
			System.out.println("Demo for FlowLayoutPane. The buttons are non-functional.");
		}
	}
}
