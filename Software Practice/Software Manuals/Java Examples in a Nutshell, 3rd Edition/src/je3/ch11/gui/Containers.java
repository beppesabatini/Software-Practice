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

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 259-260. A component
 * subclass that demonstrates nested containers and components. It creates the
 * hierarchy shown below, and uses different colors to distinguish the different
 * nesting levels of the containers.
 * 
 * <pre>
 * Containers---panel1----button1
 *        |       |---panel2----button2
 *        |       |        |----panel3----button3
 *        |       |------panel4----button4
 *        |                   |----button5
 *        |---button6
 * </pre>
 */
public class Containers extends JPanelDemoable {

	private static final long serialVersionUID = 6897284921379013555L;

	public Containers() {
		// This component is white:
		this.setBackground(Color.white);
		this.setFont(new Font("Dialog", Font.BOLD, 24));

		JPanel jPanel01 = new JPanel();
		// jPanel01 is darker:
		jPanel01.setBackground(new Color(200, 200, 200));
		// jPanel01 is contained by this component:
		this.add(jPanel01);
		// Button 1 is contained in jPanel01:
		jPanel01.add(new JButton("#1"));

		JPanel jPanel02 = new JPanel();
		// jPanel02 is darker than jPanel01:
		jPanel02.setBackground(new Color(150, 150, 150));
		// jPanel02 is contained in jPanel01:
		jPanel01.add(jPanel02);
		// Button 2 is contained in jPanel02:
		jPanel02.add(new JButton("#2"));

		JPanel jPanel03 = new JPanel();
		// jPanel03 is darker than jPanel02:
		jPanel03.setBackground(new Color(100, 100, 100));
		// jPanel03 is contained in jPanel02:
		jPanel02.add(jPanel03);
		// Button 3 is contained in jPanel03:
		jPanel03.add(new JButton("#3"));

		JPanel jPanel04 = new JPanel();
		// jPanel04 is darker than jPanel01:
		jPanel04.setBackground(new Color(150, 150, 150));
		// jPanel04 is contained in jPanel01:
		jPanel01.add(jPanel04);
		// Button4 is contained in jPanel04:
		jPanel04.add(new JButton("#4"));
		// Button5 is also contained in jPanel04:
		jPanel04.add(new JButton("#5"));

		// Button6 is contained in this component:
		this.add(new JButton("#6"));
	}

	public static class Demo {
		public static void main(String[] args) {
			launchInShowBean("je3.ch11.gui.Containers", 400, 180);
			System.out.println("Demo for Containers. The buttons are non-functional.");
		}
	}
}
