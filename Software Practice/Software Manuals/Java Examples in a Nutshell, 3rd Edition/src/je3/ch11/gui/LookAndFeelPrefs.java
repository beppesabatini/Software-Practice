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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 342-343. To test this,
 * launch any example using the ShowBean platform, such as the YesNoPanel.
 */
public class LookAndFeelPrefs extends JPanelDemoable {

	private static final long serialVersionUID = 2494884868128127596L;

	public static final String PREF_NAME = "preferredLookAndFeelClassName";

	/**
	 * Get the desired look and feel from a per-user preference. If the preferences
	 * doesn't exist or is unavailable, use the default look and feel. The
	 * preference is shared by all classes in the same package as prefsClass.
	 */
	public static void setPreferredLookAndFeel(Class<?> prefsClass) {
		Preferences prefs = Preferences.userNodeForPackage(prefsClass);
		String defaultLAF = UIManager.getSystemLookAndFeelClassName();
		String laf = prefs.get(PREF_NAME, defaultLAF);
		try {
			UIManager.setLookAndFeel(laf);
		} catch (Exception e) {
			/*
			 * ClassNotFound or InstantiationException An exception here is probably caused
			 * by a bogus preference. Ignore it silently; the user will make do with the
			 * default look-and-feel.
			 */
		}
	}

	/**
	 * Create a menu of radio buttons listing the available look-and-feels. When the
	 * user selects one, change the component hierarchy under the JFrame to the new
	 * look-and-feel, and store the new selection as the current preference for the
	 * package containing class c.
	 */
	public static JMenu createLookAndFeelMenu(final Class<?> prefsClass, final ActionListener listener) {
		// Create the menu:
		final JMenu plafmenu = new JMenu("Look and Feel");

		// Create an object used for radio button mutual exclusion.
		ButtonGroup radiogroup = new ButtonGroup();

		// Look up the available look and feels.
		UIManager.LookAndFeelInfo[] plafs = UIManager.getInstalledLookAndFeels();

		// Find out which one is currently used.
		String currentLAFName = UIManager.getLookAndFeel().getClass().getName();

		// Loop through the preferred look-and-feels, and add a menu item for each one.
		for (int i = 0; i < plafs.length; i++) {
			String plafName = plafs[i].getName();
			final String plafClassName = plafs[i].getClassName();

			// Create the menu item:
			final JMenuItem item = plafmenu.add(new JRadioButtonMenuItem(plafName));
			item.setSelected(plafClassName.equals(currentLAFName));

			// Tell the menu item what to do when it is selected.
			item.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					try {
						// Set the new look and feel:
						UIManager.setLookAndFeel(plafClassName);
					} catch (UnsupportedLookAndFeelException e) {
						/*
						 * Sometimes a Look-and-Feel is installed but not supported, as in the Windows
						 * look-and-feel on Linux platforms.
						 */
						String message = "The selected Look-and-Feel is not supported on this platform.";
						String title = "Unsupported Look And Feel";
						JOptionPane.showMessageDialog(plafmenu, message, title, JOptionPane.ERROR_MESSAGE);
						item.setEnabled(false);
					} catch (Exception e) {
						// ClassNotFound or Instantiation error. Should not happen.
						item.setEnabled(false);
					}

					// Make the selection persistent by storing it in preferences.
					Preferences preferences = Preferences.userNodeForPackage(prefsClass);
					preferences.put(PREF_NAME, plafClassName);

					/*
					 * Invoke the supplied action listener so the calling application can update its
					 * components to the new look-and-feel. Reuse the event that was passed here.
					 */
					listener.actionPerformed(event);
				}
			});

			// Only allow one menu item at a time to be selected.
			radiogroup.add(item);
		}

		return plafmenu;
	}

	public static class Demo {
		public static void main(String[] args) {
			System.out.print("Demo for LookAndFeelPrefs as used by YesNoPanel. ");
			System.out.println("Edit YesNoPanel through the Properties Menu.");
			System.out.println("The buttons are non-functional.");
			launchInShowBean("je3.ch15.beans.YesNoPanel", 400, 240);
		}
	}
}
