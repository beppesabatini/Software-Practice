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
import javax.swing.JButton;

/**
 * From Java Examples in a Nutshell, 3rd Edition, p. 270. 
 */
public class NullLayoutPane extends JPanelDemoable {

	private static final long serialVersionUID = 1928289417262905004L;

	public NullLayoutPane() {
		/*
		 * Get rid of the default layout manager. We'll arrange the components
		 * ourselves.
		 */
		this.setLayout(null);

		// Create some buttons and set their sizes and positions explicitly.
		for (int i = 1; i <= 9; i++) {
			JButton jButton = new JButton("Button #" + i);
			// Use "reshape()" for this in Java 1.0.
			jButton.setBounds(i * 30, i * 20, 125, 30);
			this.add(jButton);
		}
	}

	public static class Demo {
		public static void main(String[] args) {
 			ShowBean showBean = launchInShowBean("je3.ch11.gui.NullLayoutPane", 450, 325);
			Dimension dimension = new Dimension(450, 325);
			showBean.setPreferredSize(dimension);
			showBean.pack();
			System.out.println("Demo for NullLayoutPane. The buttons are non-functional.");
		}
	}
}
