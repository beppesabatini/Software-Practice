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
import javax.swing.JButton;

/**
 * From Java Examples in a Nutshell, 3rd Edition, p. 264. 
 */
public class BorderLayoutPane extends JPanelDemoable {

	private static final long serialVersionUID = 3795432246342620978L;

	private static String[] constraints = initConstraints();

	private static String[] initConstraints() {
		String[] constraints = new String[5];
		
		constraints[0] = BorderLayout.NORTH;
		constraints[1] = BorderLayout.EAST;
		constraints[2] = BorderLayout.SOUTH;
		constraints[3] = BorderLayout.WEST;
		constraints[4] = BorderLayout.CENTER;
		
		return (constraints);
	}

	public BorderLayoutPane() {
		// Use a BorderLayout with 10-pixel margins between components.
		this.setLayout(new BorderLayout(10, 10));
		
		// Add children to the pane:
		for (int i = 0; i < 5; i++) {
			// Add a button to the pane constrained to the layout value it defines.
			this.add(new JButton(constraints[i]), constraints[i]); 
		}
	}

	public static class Demo {
		public static void main(String[] args) {
			launchInShowBean("je3.ch11.gui.BorderLayoutPane", 300, 200);
			System.out.println("Demo for BorderLayoutPane. The buttons are non-functional.");
		}
	}
}
