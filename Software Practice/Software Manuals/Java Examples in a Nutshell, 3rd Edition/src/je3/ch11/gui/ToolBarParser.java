/*
 * Copyright (c) 2000 David Flanagan.  All rights reserved.
 * This code is from the book Java Examples in a Nutshell, 2nd Edition.
 * It is provided AS-IS, WITHOUT ANY WARRANTY either expressed or implied.
 * You may study, use, and modify it for any non-commercial purpose.
 * You may distribute it non-commercially as long as you retain this notice.
 * For a commercial use license, or to purchase the book (recommended),
 * visit http://www.davidflanagan.com/javaexamples2.
 */
package je3.ch11.gui;

import java.util.List;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JToolBar;

/**
 * Referred to in Java Examples in a Nutshell, 3rd Edition, p. 334. Parse a
 * JToolBar from a ResourceBundle. A toolbar is represented simply as a list of
 * action property names. For example:
 * 
 * <pre>
 *     toolbar: action.save, action.print, action.quit
 * </pre>
 * 
 * For more examples, from a an actual resource file, see:
 * 
 * <pre>
 * WebBrowserResources.properties
 * </pre>
 */
public class ToolBarParser implements ResourceParser {
	static final Class<?>[] supportedTypes = new Class[] { JToolBar.class };

	public Class<?>[] getResourceTypes() {
		return supportedTypes;
	}

	@Override
	public Object parse(GUIResourceBundle bundle, String key, Class<?> type) throws java.util.MissingResourceException {
		// Get the value of the key as a list of strings.
		List<String> toolList = bundle.getStringList(key);

		// Create a ToolBar:
		JToolBar jToolBar = new JToolBar();

		/*
		 * Create a JTool for each of the tool property names, and add it to the bar.
		 */
		int numtools = toolList.size();
		for (int i = 0; i < numtools; i++) {
			// Get the action name:
			String tool = toolList.get(i);
			// Get the Action object associated with that name:
			Action action = (Action) bundle.getResource(tool, Action.class);
			// Add the action to the toolbar, and get the JButton it creates.
			JButton button = jToolBar.add(action);
			// If the action contains a description, use it as the ToolTip.
			String tooltip = (String) action.getValue(Action.SHORT_DESCRIPTION);
			if (tooltip != null) {
				button.setToolTipText(tooltip);
			}
		}

		return jToolBar;
	}
}
