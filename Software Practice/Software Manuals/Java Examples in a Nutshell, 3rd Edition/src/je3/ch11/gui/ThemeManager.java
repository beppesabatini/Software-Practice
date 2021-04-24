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
import java.awt.Font;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 338-341. This class reads
 * theme descriptions from a GUIResourceBundle, and uses them to specify colors
 * and fonts for the Metal look-and-feel. It also demonstrates an undocumented
 * feature for turning Swing notification sounds on and off.
 */
public class ThemeManager {
	// The frame to which themes are applied:
	JFrame frame;
	// Properties describing the themes:
	GUIResourceBundle resources;

	/**
	 * Build a ThemeManager for the frame and resource bundle. If there is a default
	 * theme specified, apply it to the frame.
	 */
	public ThemeManager(JFrame frame, GUIResourceBundle resources) {
		this.frame = frame;
		this.resources = resources;
		String defaultName = getDefaultThemeName();
		if (defaultName != null) {
			setTheme(defaultName);
		}
	}

	/** Look up the named theme, and apply it to the frame. */
	public void setTheme(String themeName) {
		// Look up the theme in the resource bundle
		Theme theme = new Theme(resources, themeName);

		// Make it the current theme
		MetalLookAndFeel.setCurrentTheme(theme);

		// Re-apply the Metal look-and-feel to install new theme.
		try {
			UIManager.setLookAndFeel(new MetalLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
		}

		// If the theme has an audio playlist, then set it.
		if (theme.playlist != null) {
			UIManager.put("AuditoryCues.playList", theme.playlist);
		}
		// Propagate the new look-and-feel across the entire component tree of the
		// frame.
		SwingUtilities.updateComponentTreeUI(frame);
	}

	/** Get the "display name" or label of the named theme */
	public String getDisplayName(String themeName) {
		return resources.getString(themeName + ".name", null);
	}

	/** Get the name of the default theme, or null */
	public String getDefaultThemeName() {
		return resources.getString("defaultTheme", null);
	}

	/**
	 * Get the list of all known theme names. The returned values are theme property
	 * names, not theme display names.
	 **/
	public String[] getAllThemeNames() {
		List<String> names = resources.getStringList("themelist");
		String[] allThemeNames = names.toArray(new String[names.size()]);
		return (allThemeNames);
	}

	/**
	 * Get a JMenu that lists all known themes by display name and installs any
	 * selected theme.
	 */
	public JMenu getThemeMenu() {
		String[] names = getAllThemeNames();
		String defaultName = getDefaultThemeName();
		JMenu menu = new JMenu("Themes");
		ButtonGroup buttongroup = new ButtonGroup();

		for (int i = 0; i < names.length; i++) {
			final String themeName = names[i];
			String displayName = getDisplayName(themeName);
			JMenuItem item = menu.add(new JRadioButtonMenuItem(displayName));
			buttongroup.add(item);

			if (themeName.equals(defaultName)) {
				item.setSelected(true);
			}
			item.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					setTheme(themeName);
				}
			});
		}
		return menu;
	}

	/**
	 * This class extends the DefaultMetalTheme class to return Color and Font
	 * values read from a GUIResourceBundle.
	 */
	public static class Theme extends DefaultMetalTheme {
		// These fields are the values returned by this Theme.
		String displayName;
		FontUIResource controlFont, menuFont, smallFont;
		FontUIResource systemFont, userFont, titleFont;
		ColorUIResource primary1, primary2, primary3;
		ColorUIResource secondary1, secondary2, secondary3;
		// Auditory cues:
		Object playlist;

		/**
		 * This constructor reads all the values it needs from the GUIResourceBundle. It
		 * uses intelligent defaults if properties are not specified.
		 */
		public Theme(GUIResourceBundle resources, String name) {
			// Get default font values from this theme:
			DefaultMetalTheme defaultTheme = new DefaultMetalTheme();

			// Look up the display name of the theme:
			displayName = resources.getString(name + ".name", null);

			// Look up the fonts for the theme:
			Font control = resources.getFont(name + ".controlFont", null);
			Font menu = resources.getFont(name + ".menuFont", null);
			Font small = resources.getFont(name + ".smallFont", null);
			Font system = resources.getFont(name + ".systemFont", null);
			Font user = resources.getFont(name + ".userFont", null);
			Font title = resources.getFont(name + ".titleFont", null);

			// Convert fonts to FontUIResource, or get the defaults.
			if (control != null) {
				controlFont = new FontUIResource(control);
			} else {
				controlFont = defaultTheme.getControlTextFont();
			}
			if (menu != null) {
				menuFont = new FontUIResource(menu);
			} else {
				menuFont = defaultTheme.getMenuTextFont();
			}
			if (small != null) {
				smallFont = new FontUIResource(small);
			} else {
				smallFont = defaultTheme.getSubTextFont();
			}
			if (system != null) {
				systemFont = new FontUIResource(system);
			} else {
				systemFont = defaultTheme.getSystemTextFont();
			}
			if (user != null) {
				userFont = new FontUIResource(user);
			} else
				userFont = defaultTheme.getUserTextFont();
			if (title != null) {
				titleFont = new FontUIResource(title);
			} else {
				titleFont = defaultTheme.getWindowTitleFont();
			}

			// Look up primary and secondary colors.
			Color primary = resources.getColor(name + ".primary", null);
			Color secondary = resources.getColor(name + ".secondary", null);

			// Derive all six colors from these two, using defaults if needed.
			if (primary != null) {
				primary1 = new ColorUIResource(primary);
			} else {
				primary1 = new ColorUIResource(102, 102, 153);
			}
			primary2 = new ColorUIResource(primary1.brighter());
			primary3 = new ColorUIResource(primary2.brighter());
			if (secondary != null) {
				secondary1 = new ColorUIResource(secondary);
			} else {
				secondary1 = new ColorUIResource(102, 102, 102);
			}
			secondary2 = new ColorUIResource(secondary1.brighter());
			secondary3 = new ColorUIResource(secondary2.brighter());

			/*
			 * Look up what type of sound is desired. This property should be one of the
			 * strings "all", "none", or "default". These map to undocumented UIManager
			 * properties. playlist is an array of strings, but we keep it as an opaque
			 * object.
			 */
			String sounds = resources.getString(name + ".sounds", "");
			if (sounds.equals("all")) {
				playlist = UIManager.get("AuditoryCues.allAuditoryCues");
			} else if (sounds.equals("none")) {
				playlist = UIManager.get("AuditoryCues.noAuditoryCues");
			} else if (sounds.equals("default")) {
				playlist = UIManager.get("AuditoryCues.defaultCueList");
			}
		}

		/*
		 * These methods override DefaultMetalTheme and return the property values we
		 * looked up and computed for this theme.
		 */
		@Override
		public String getName() {
			return displayName;
		}

		@Override
		public FontUIResource getControlTextFont() {
			return controlFont;
		}

		@Override
		public FontUIResource getSystemTextFont() {
			return systemFont;
		}

		@Override
		public FontUIResource getUserTextFont() {
			return userFont;
		}

		@Override
		public FontUIResource getMenuTextFont() {
			return menuFont;
		}

		@Override
		public FontUIResource getWindowTitleFont() {
			return titleFont;
		}

		@Override
		public FontUIResource getSubTextFont() {
			return smallFont;
		}

		@Override
		protected ColorUIResource getPrimary1() {
			return primary1;
		}

		@Override
		protected ColorUIResource getPrimary2() {
			return primary2;
		}

		@Override
		protected ColorUIResource getPrimary3() {
			return primary3;
		}

		@Override
		protected ColorUIResource getSecondary1() {
			return secondary1;
		}

		@Override
		protected ColorUIResource getSecondary2() {
			return secondary2;
		}

		@Override
		protected ColorUIResource getSecondary3() {
			return secondary3;
		}
	}

	public static class Demo {
		public static void main(String[] args) {
			Class<GUIResourceBundle> dummy01 = GUIResourceBundle.class;
			GUIResourceBundle guiResourceBundle = new GUIResourceBundle(dummy01, "je3.ch11.gui.WebBrowserResources");
			JFrame dummy02 = new JFrame();
			ThemeManager themeManager = new ThemeManager(dummy02, guiResourceBundle);

			String defaultThemeName = themeManager.getDefaultThemeName();
			System.out.println("Default Theme Name: " + defaultThemeName);
			String displayName = themeManager.getDisplayName(defaultThemeName);
			System.out.println("Display Name: " + displayName);
			String[] allThemeNames = themeManager.getAllThemeNames();
			System.out.println("All Theme Names: ");
			for (int i = 0; i < allThemeNames.length; i++) {
				System.out.println("  " + allThemeNames[i]);
			}
			System.out.println("All Themes: ");
			for (int i = 0; i < allThemeNames.length; i++) {
				new Theme(guiResourceBundle, allThemeNames[i]);
			}
		}
	}
}
