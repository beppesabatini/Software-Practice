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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.GridLayout;
import java.awt.Image;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import je3.ch15.beans.Bean;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 344-351. This class is a
 * program that uses reflection and JavaBeans introspection to create a set of
 * named components, set named properties on those components, and display them.
 * It allows the user to view the components using any installed look-and-feel.
 * <p/>
 * It is intended as a simple way to experiment with AWT and Swing components,
 * and to view a number of the other examples developed in this chapter. It also
 * demonstrates frames, menus, and the JTabbedPane component.
 */
public class ShowBean extends JFrame {

	private static final long serialVersionUID = 212930724524193081L;

	// The main program:
	public static void main(String[] args) {

		/*
		 * Set the look-and-feel for the application. LookAndFeelPrefs is defined
		 * elsewhere in this package.
		 */
		LookAndFeelPrefs.setPreferredLookAndFeel(ShowBean.class);

		// Process the command line arguments to get the components to display.
		Dimension jFrameDimension = new Dimension(550, 200);
		List<Bean> beans = parseArgs(args, jFrameDimension);

		// Create a jFrame.
		JFrame jFrame = new ShowBean(beans);
		jFrame.setPreferredSize(jFrameDimension);

		/*
		 * Handle window close requests by exiting the VM. Here we define an anonymous
		 * inner class satisfying WindowAdapter.
		 */
		jFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		// Make the frame visible on the screen.
		jFrame.setSize(jFrameDimension);
		jFrame.setVisible(true);

		/*
		 * The main() method exits now, but the Java VM keeps running, because all AWT
		 * programs automatically start an event-handling thread.
		 */
	}

	List<Bean> beans;
	JMenu propertyMenu, commandMenu;
	JTabbedPane pane;
	JFileChooser fileChooser = new JFileChooser();

	// Most initialization code is in the constructor instead of main().
	public ShowBean(final List<Bean> beans) {
		super("ShowBean");

		// Save the list of Bean objects.
		this.beans = beans;

		// Create a menu bar:
		JMenuBar menubar = new JMenuBar();
		// Tell the frame to display it:
		this.setJMenuBar(menubar);

		// Create and populate a File menu:
		JMenu filemenu = new JMenu("File");
		filemenu.setMnemonic('F');

		JMenuItem save = new JMenuItem("Save as...");
		save.setMnemonic('S');
		filemenu.add(save);

		JMenuItem serialize = new JMenuItem("Serialize as...");
		filemenu.add(serialize);

		JMenuItem quit = new JMenuItem("Quit");
		quit.setMnemonic('Q');
		filemenu.add(new JSeparator());
		filemenu.add(quit);

		menubar.add(filemenu);

		// Here are event handlers for the Save As and Quit menu items.
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveCurrentPane();
			}
		});

		serialize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				serializeCurrentPane();
			}
		});

		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		/*
		 * Set up a menu that allows the user to select the look-and-feel of the
		 * component from a list of installed look-and-feels. Remember the selected
		 * look-and-feel in a persistent Preferences node.
		 */
		JMenu plafmenu = LookAndFeelPrefs.createLookAndFeelMenu(ShowBean.class, new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				/*
				 * When the user selects a new look-and-feel, tell each component to change its
				 * look-and-feel.
				 */
				SwingUtilities.updateComponentTreeUI(ShowBean.this);
				// Then repack the frame to its new preferred size.
				ShowBean.this.pack();
			}
		});
		plafmenu.setMnemonic('L');
		// Add the menu to the menu bar.
		menubar.add(plafmenu);

		/*
		 * Create the Properties and Commands menus, but don't populate them. JMenuItems
		 * are added each time a new tab is selected.
		 */
		propertyMenu = new JMenu("Properties");
		propertyMenu.setMnemonic('P');
		menubar.add(propertyMenu);

		commandMenu = new JMenu("Commands");
		commandMenu.setMnemonic('C');
		menubar.add(commandMenu);

		/*
		 * Some components have many properties, so make the property menu three columns
		 * wide, so that we can see all of the entries.
		 */
		propertyMenu.getPopupMenu().setLayout(new GridLayout(0, 3));

		/*
		 * Create a JTabbedPane to display each of the components.
		 */
		pane = new JTabbedPane();
		pane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				populateMenus(pane.getSelectedIndex());
			}
		});

		// Now add each component as a tab of the tabbed pane.
		for (int i = 0; i < beans.size(); i++) {
			Bean b = (Bean) beans.get(i);
			Object o = b.getBean();
			Component component;
			if (o instanceof Component) {
				component = (Component) o;
			} else {
				component = new JLabel("This bean has no Component");
			}
			Image image = b.getIcon();
			Icon icon = null;
			if (image != null) {
				icon = new ImageIcon(image);
			}
			// Text for the tab, icon for the tab, contents of the tab, tool tip for the
			// tab.
			pane.addTab(b.getDisplayName(), icon, component, b.getShortDescription());
		}

		/*
		 * Add the tabbed pane to this frame. Note the call to getContentPane(). This is
		 * required for JFrame, but not for most Swing components.
		 */
		this.getContentPane().add(pane);

		// Make the frame as big as its children need.
		this.pack();
	}

	/**
	 * This static method loops through the command line arguments looking for the
	 * names of files that contain XML, encoded, or serialized components, or for
	 * class names of components to instantiate, or for name=value property settings
	 * for those components. It relies on the Bean class developed elsewhere.
	 */
	static List<Bean> parseArgs(String[] args, Dimension jFrameDimension) {
		// List of beans to return:
		List<Bean> beans = new ArrayList<Bean>();
		// The current bean:
		Bean bean = null;
		boolean expert = false;

		// Loop through all arguments:
		for (int i = 0; i < args.length; i++) {
			// Does it begin with a dash?
			if (args[i].charAt(0) == '-') {
				try {
					if (args[i].startsWith("-width=")) {
						int width = Integer.valueOf(args[i].substring(7));
						jFrameDimension.width = width;
						continue;
					}
					if (args[i].startsWith("-height=")) {
						int height = Integer.valueOf(args[i].substring(8));
						jFrameDimension.height = height;
						continue;
					}
					if (args[i].equals("-expert")) {
						expert = true;
					} else if (args[i].equals("-xml")) {
						// Read from XML
						if (++i >= args.length) {
							continue;
						}
						InputStream in = new FileInputStream(args[i]);
						bean = Bean.fromPersistentStream(in, expert);
						beans.add(bean);
						in.close();
					} else if (args[i].equals("-ser")) {
						// Deserialize a file:
						if (++i >= args.length) {
							continue;
						}
						ObjectInputStream in = new ObjectInputStream(new FileInputStream(args[i]));
						bean = Bean.fromSerializedStream(in, expert);
						beans.add(bean);
						in.close();
					} else {
						System.err.println("Unknown option: " + args[i]);
						continue;
					}
				} catch (Exception e) {
					// In case anything goes wrong:
					System.err.println(e);
					System.exit(1);
					continue;
				}
			} else if (args[i].indexOf('=') == -1) {
				/*
				 * It's a component name. If the argument does not contain an equal sign, then
				 * it is a component class name.
				 */
				try {
					bean = Bean.forClassName(args[i], expert);
					beans.add(bean);
				} catch (Exception e) {
					// If any step failed, print an error and exit.
					System.out.println("Can't load, instantiate, or introspect: " + args[i]);
					System.out.println(e);
					System.exit(1);
				}
			} else {
				/*
				 * The argument is a name=value property specification. Break it into name and
				 * value parts.
				 */
				int pos = args[i].indexOf('=');
				// Property name:
				String name = args[i].substring(0, pos);
				// Property value:
				String value = args[i].substring(pos + 1);

				// If we don't have a component to set this property on, skip!
				if (bean == null) {
					System.err.println("Property " + name + " specified before any bean.");
					continue;
				}

				// Now try to set the property.
				try {
					bean.setPropertyValue(name, value);
				} catch (Exception e) {
					/*
					 * Failure to set a property is not a fatal error; Just display the message and
					 * continue.
					 */
					System.out.println(e);
				}
			}
		}

		return beans;
	}

	/**
	 * Ask the user to select a filename, and then save the contents of the current
	 * tab to that file, using the JavaBeans persistence mechanism.
	 */
	public void saveCurrentPane() {
		int result = fileChooser.showSaveDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			try {
				File file = fileChooser.getSelectedFile();
				XMLEncoder encoder = new XMLEncoder(new FileOutputStream(file));
				encoder.writeObject(pane.getSelectedComponent());
				encoder.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, e, e.getClass().getName(), JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Ask the user to choose a filename, and then save the contents of the current
	 * pane to that file, using traditional object serialization.
	 */
	public void serializeCurrentPane() {
		int result = fileChooser.showSaveDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			try {
				File file = fileChooser.getSelectedFile();
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
				objectOutputStream.writeObject(pane.getSelectedComponent());
				objectOutputStream.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, e, e.getClass().getName(), JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Create JMenuItem objects in the Properties and Command menus. This method is
	 * called whenever a new tab is selected.
	 */
	void populateMenus(int index) {
		// First, delete the old menu contents:
		propertyMenu.removeAll();
		commandMenu.removeAll();

		// The Bean object for this tab:
		final Bean bean = beans.get(index);

		List<String> properties = bean.getPropertyNames();
		for (int i = 0; i < properties.size(); i++) {
			final String name = properties.get(i);
			// Create a menu item for the command:
			JMenuItem item = new JMenuItem(name);
			// If the property has a non-trivial description, make it a tool tip.
			String tip = bean.getPropertyDescription(name);
			if (tip != null && !tip.equals(name)) {
				item.setToolTipText(tip);
			}

			item.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					editProperty(bean, name);
				}
			});

			propertyMenu.add(item);
		}

		List<String> commands = bean.getCommandNames();
		for (int i = 0; i < commands.size(); i++) {
			final String name = commands.get(i);
			// Create a menu item for the command:
			JMenuItem jMenuItem = new JMenuItem(name);
			// If the command has a non-trivial description, make it a tool tip.
			String tip = bean.getCommandDescription(name);
			if (tip != null && !name.endsWith(tip)) {
				jMenuItem.setToolTipText(tip);
			}

			// Invoke the command when the item is selected
			jMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						bean.invokeCommand(name);
					} catch (Exception ex) {
						getToolkit().beep();
						String title = ex.getClass().getName();
						JOptionPane.showMessageDialog(ShowBean.this, ex, title, JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			// Add the item to the menu:
			commandMenu.add(jMenuItem);
		}
		if (commands.size() == 0) {
			commandMenu.add("No Commands Available");
		}
	}

	void editProperty(Bean bean, String name) {
		try {
			if (bean.isReadOnly(name)) {
				String value = bean.getPropertyValue(name);
				String title = "Read-only Property Value";
				JOptionPane.showMessageDialog(this, name + " = " + value, title, JOptionPane.PLAIN_MESSAGE);
			} else {
				Component editor = bean.getPropertyEditor(name);
				Object[] message = new Object[] { name, editor };
				JOptionPane.showMessageDialog(this, message, "Edit Property", JOptionPane.PLAIN_MESSAGE);
			}
		} catch (Exception e) {
			getToolkit().beep();
			JOptionPane.showMessageDialog(this, e, e.getClass().getName(), JOptionPane.ERROR_MESSAGE);
		}
	}
}
