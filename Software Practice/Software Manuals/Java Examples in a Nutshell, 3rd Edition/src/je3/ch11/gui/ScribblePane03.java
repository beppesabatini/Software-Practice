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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JButton;
import javax.swing.JList;

/**
 * From Java Examples in a Nutshell, 3rd Edition, p. 281. This scribble
 * component includes a JButton to clear the screen, and a JList that lets the
 * user select a drawing color. It uses event listener objects to handle events
 * from those sub-components.
 */
public class ScribblePane03 extends ScribblePane02 {
	
	private static final long serialVersionUID = 1468797913129021698L;

	// These are colors the user can choose from:
	Color[] colors = new Color[] { Color.black, Color.red, Color.blue };
	// These are names for those colors:
	String[] colorNames = new String[] { "Black", "Red", "Blue" };

	// Add JButton and JList components to the panel.
	public ScribblePane03() {
		// An implicit super() call here invokes the superclass constructor.
		/*
		 * Add a "Clear" button to the panel. Handle button events with an action
		 * listener.
		 */
		JButton clear = new JButton("Clear");
		clear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				clear();
			}
		});
		this.add(clear);

		/*
		 * Add a JList to allow color choices. Handle list selection events with a
		 * ListSelectionListener.
		 */
		@SuppressWarnings({ "unchecked", "rawtypes" })
		final JList<String[]> colorList = new JList(colorNames);
		colorList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent listSelectionEvent) {
				setColor(colors[colorList.getSelectedIndex()]);
			}
		});
		this.add(colorList);
	}

	public static class Demo {
		public static void main(String[] args) {
			launchInShowBean("je3.ch11.gui.ScribblePane03", 300, 200);
			System.out.println("Demo for ScribblePane03. Not all menu items are functional");
		}
	}
}
