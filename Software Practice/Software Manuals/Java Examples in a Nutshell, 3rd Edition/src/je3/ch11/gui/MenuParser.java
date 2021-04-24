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

import java.util.StringTokenizer;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 334-336. This class parses
 * a JMenu or JPopupMenu from textual descriptions found in a GUIResourceBundle.
 * The grammar is straightforward: the menu label followed by a colon and a list
 * of menu items. Menu items that begin with a '>' character are submenus. Menu
 * items that begin with a '-' character are separators. All other items are
 * action names.
 * <p>
 * For an example of a resource file see:
 * 
 * <pre>
 * WebBrowserResources.properties
 * </pre>
 */
public class MenuParser implements ResourceParser {
	// MenuParser handles two resource types.
	static final Class<?>[] supportedTypes = new Class<?>[] { JMenu.class, JPopupMenu.class };

	@Override
	public Class<?>[] getResourceTypes() {
		return supportedTypes;
	}

	@Override
	public Object parse(GUIResourceBundle bundle, String key, Class<?> type) throws java.util.MissingResourceException {
		// Get the string value of the key.
		String menudef = bundle.getString(key);

		// Break it up into words, ignoring whitespace, colons and commas.
		StringTokenizer st = new StringTokenizer(menudef, " \t:,");

		// The first word is the label of the menu.
		String menuLabel = st.nextToken();

		// Create either a JMenu or JPopupMenu.
		JMenu menu = null;
		JPopupMenu popup = null;
		if (type == JMenu.class) {
			menu = new JMenu(menuLabel);
		} else
			popup = new JPopupMenu(menuLabel);

		/*
		 * Then loop through the rest of the words, creating a JMenuItem for each one.
		 * Accumulate these items in a list.
		 */
		while (st.hasMoreTokens()) {
			// The next word:
			String item = st.nextToken();
			// This determines the type of the menu item:
			char firstchar = item.charAt(0);
			switch (firstchar) {
				/* For words beginning with a hyphen ('-'), add a separator to the menu. */
				case '-': {
					if (menu != null) {
						menu.addSeparator();
					} else {
						popup.addSeparator();
					}
					break;
				}
				/* Words beginning with a greater-than symbol ('>') are submenu names. */
				case '>': {
					// Strip off the > character, and recurse to parse the submenu.
					item = item.substring(1);
					// Parse a submenu and add it to the list of items:
					JMenu submenu = (JMenu) parse(bundle, item, JMenu.class);
					if (menu != null) {
						menu.add(submenu);
					} else {
						popup.add(submenu);
					}
					break;
				}
				case '!': // Words beginning with an exclamation mark ('!') are action names.
					// Strip off the ! character:
					item = item.substring(1);
					/*
					 * No "break;" symbol. Fall through to the next case (default). By default, all
					 * other words are taken as action names.
					 */
				default:
					// Look up the named action and add it to the menu.
					Action action = (Action) bundle.getResource(item, Action.class);
					if (menu != null) {
						menu.add(action);
					} else {
						popup.add(action);
					}
					break;
			}
		}

		// Finally, return the menu or the pop-up menu.
		if (menu != null) {
			return menu;
		} else {
			return popup;
		}
	}
}
