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

import java.awt.GridLayout;
import javax.swing.JButton;

/**
 * From Java Examples in a Nutshell, 3rd Edition, p. 263.  
 */
public class GridLayoutPane extends JPanelDemoable {

	private static final long serialVersionUID = -2306587826550999147L;

	public GridLayoutPane() {
		/*
		 * Layout components into a grid three columns wide, with the number of rows
		 * depending on the number of components. Leave 10 pixels of horizontal and
		 * vertical space between components.
		 */
		this.setLayout(new GridLayout(0, 3, 10, 10));
		// Add some components
		for (int i = 1; i <= 12; i++) {
			this.add(new JButton("Button #" + i));
		}
	}

	public static class Demo {
		public static void main(String[] args) {
			launchInShowBean("je3.ch11.gui.GridLayoutPane", 500, 300);
			System.out.println("Demo for GridLayoutPane. The buttons are non-functional.");
		}
	}
}
