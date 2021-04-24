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
package je3.ch08.i18n;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 223-225. A convenience
 * class to automatically create localized menu panes.
 */
public class SimpleMenu {

	/*
	 * In this example, the menu properties files are saved in the package
	 * je3.ch08.i18n, and have names like "Menus.properties" and others. You will
	 * need to change this constant to match your local package structure.
	 */
	private static final String menuPropertiesFile = "je3.ch08.i18n.Menus";

	/** The convenience method that creates menu panes. */
	public static JMenu create(ResourceBundle bundle, String menuname, String[] itemnames, ActionListener listener) {
		// Get the menu title from the bundle. Use name as default label.
		String menulabel;
		try {
			menulabel = bundle.getString(menuname + ".label");
		} catch (MissingResourceException e) {
			menulabel = menuname;
		}

		// Create the menu pane.
		JMenu menu = new JMenu(menulabel);

		// For each named item in the menu:
		for (int i = 0; i < itemnames.length; i++) {
			// ...look up the label for the item, using the name as the default.
			String itemlabel;
			try {
				itemlabel = bundle.getString(menuname + "." + itemnames[i] + ".label");
			} catch (MissingResourceException exception) {
				itemlabel = itemnames[i];
			}

			JMenuItem item = new JMenuItem(itemlabel);

			// Look up an accelerator for the menu item.
			try {
				String acceleratorText = bundle.getString(menuname + "." + itemnames[i] + ".accelerator");
				item.setAccelerator(KeyStroke.getKeyStroke(acceleratorText));
			} catch (MissingResourceException exception) {
				// ignore
			}

			// Register an action listener and command for the item.
			if (listener != null) {
				item.addActionListener(listener);
				item.setActionCommand(itemnames[i]);
			}

			// Add the item to the menu.
			menu.add(item);
		}

		// Return the automatically created localized menu.
		return menu;
	}

	/** A simple test program for the above code */
	public static void main(String[] args) {
		// Get the locale: default, or specified on command-line
		Locale locale;
		if (args.length == 2) {
			locale = new Locale(args[0], args[1]);
		} else
			locale = Locale.getDefault();

		/*
		 * Get the resource bundle for that Locale. This will throw an (unchecked)
		 * MissingResourceException if no bundle is found.
		 */
		ResourceBundle bundle = ResourceBundle.getBundle(menuPropertiesFile, locale);

		// Create a simple GUI window with which to display the menu.
		// Display the locale in the window title.
		final JFrame jFrame = new JFrame("SimpleMenu: " + locale.getDisplayName(Locale.getDefault()));
		jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Create a menu bar.
		JMenuBar menubar = new JMenuBar();
		// Add the menu bar to window.
		jFrame.setJMenuBar(menubar);

		// Define an action listener that our menu will use.
		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				String actionCommand = actionEvent.getActionCommand();
				Component contentPane = jFrame.getContentPane();
				if (actionCommand.equals("red")) {
					contentPane.setBackground(Color.red);
				} else if (actionCommand.equals("green")) {
					contentPane.setBackground(Color.green);
				} else if (actionCommand.equals("blue")) {
					contentPane.setBackground(Color.blue);
				}
			}
		};

		/*
		 * Now create a menu using our convenience routine with the resource bundle and
		 * action listener we've created.
		 */
		JMenu menu = SimpleMenu.create(bundle, "colors", new String[] { "red", "green", "blue" }, listener);

		// Finally add the menu to the GUI, and pop it up.
		//
		// Add the menu to the menu bar:
		menubar.add(menu);
		// Set the window size:
		jFrame.setSize(300, 150);
		// Pop the window up:
		jFrame.setVisible(true);
	}
}
