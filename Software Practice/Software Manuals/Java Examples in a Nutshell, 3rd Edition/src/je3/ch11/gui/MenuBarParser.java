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

import java.util.List;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

/**
 * From Java Examples in a Nutshell, 3rd Edition, p. 334. Parse a JMenuBar from
 * a ResourceBundle. A menu bar is represented simply as a list of menu property
 * names. For example:
 * 
 * <pre>
 *     menubar: menu.file menu.edit menu.view menu.help
 * </pre>
 * 
 * For more examples, from an actual resource file, see:
 * 
 * <pre>
 *     WebBrowserResources.properties
 * </pre>
 * To test this load the WebBrowser example in this same package.
 */
public class MenuBarParser implements ResourceParser {
	static final Class<?>[] supportedTypes = new Class[] { JMenuBar.class };

	@Override
	public Class<?>[] getResourceTypes() {
		return supportedTypes;
	}

	@Override
	public Object parse(GUIResourceBundle bundle, String key, Class<?> type) throws java.util.MissingResourceException {
		// Get the value of the key as a list of strings.
		List<String> menuList = bundle.getStringList(key);

		// Create a JMenuBar
		JMenuBar jMenuBar = new JMenuBar();

		/*
		 * Create a JMenu for each of the menu property names, and add it to the bar.
		 */
		int nummenus = menuList.size();
		for (int i = 0; i < nummenus; i++) {
			jMenuBar.add((JMenu) bundle.getResource(menuList.get(i), JMenu.class));
		}

		return jMenuBar;
	}
}
