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

import java.awt.Font;
import javax.swing.JButton;

/**
 * From Java Examples in a Nutshell, 3rd Edition, p. 274.
 */
public class ColumnLayoutPane extends JPanelDemoable {

	private static final long serialVersionUID = 478889861703847961L;

	public ColumnLayoutPane() {
		// Specify a ColumnLayout LayoutManager, with right alignment.
		this.setLayout(new ColumnLayout(5, 5, 10, ColumnLayout.RIGHT));

		// Create some buttons, and set their sizes and positions explicitly.
		for (int i = 0; i < 6; i++) {
			int pointsize = 8 + i * 2;
			JButton jButton = new JButton("Point size " + pointsize);
			jButton.setFont(new Font("helvetica", Font.BOLD, pointsize));
			this.add(jButton);
		}
	}

	public static class Demo {
		public static void main(String[] args) {
			launchInShowBean("je3.ch11.gui.ColumnLayoutPane", 300, 325);
			System.out.println("Demo for ColumnLayoutPane. The buttons are non-functional.");
		}
	}
}
