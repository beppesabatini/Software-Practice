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

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 285-291. This class is a
 * Swing component that presents a choice to the user. It allows the choice to
 * be presented in a JList, in a JComboBox, or with a bordered group of
 * JRadioButton components. Additionally, it displays the name of the choice
 * with a JLabel.
 * <p>
 * ItemChooser allows an arbitrary value to be associated with each possible
 * choice. Note that this component only allows one item to be selected at a
 * time. Multiple selections are not supported.
 */
public class ItemChooser extends JPanel {

	private static final long serialVersionUID = -5636752875781043925L;

	/*
	 * These fields hold property values for this component.
	 */
	// The overall name of the choice:
	String name;
	// The text for each choice option:
	String[] labels;
	// Arbitrary values associated with each option:
	Object[] values;
	// The selected choice:
	int selection;
	// How the choice is presented:
	GUIControl guiControl;

	/* These are the legal values for the presentation fields. */
	public enum GUIControl {
		LIST, COMBOBOX, RADIOBUTTONS;
	}

	/*
	 * These components are used for each of the three possible presentations.
	 */
	// One type of presentation:
	JList<?> list;
	// A combo box or drop-down, allows only one choice:
	JComboBox<String[]> combobox;
	// A set of radio buttons, allows only one choice:
	JRadioButton[] radiobuttons;

	// The list of objects that are interested in our state.
	ArrayList<Listener> listeners = new ArrayList<Listener>();

	// The constructor method sets everything up.
	public ItemChooser(String name, String[] labels, Object[] values, int defaultSelection, GUIControl presentation) {
		// Copy the constructor arguments to instance fields.
		this.name = name;
		this.labels = labels;
		this.values = values;
		this.selection = defaultSelection;
		this.guiControl = presentation;

		// If no values were supplied, use the labels.
		if (values == null) {
			this.values = labels;
		}

		// Now, create content and event handlers based on presentation type
		switch (presentation) {
			case LIST: {
				initList();
				break;
			}
			case COMBOBOX: {
				initComboBox();
				break;
			}
			case RADIOBUTTONS: {
				initRadioButtons();
				break;
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	// Initialization for JList presentation.
	void initList() {
		// Create the list:
		list = new JList(labels);
		// Set initial state:
		list.setSelectedIndex(selection);

		// Handle state changes.
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				ItemChooser.this.select(list.getSelectedIndex());
			}
		});

		/* Lay out list and name label vertically. */
		// Vertical:
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		// Display choice name:
		this.add(new JLabel(name));
		// Add the JList:
		this.add(new JScrollPane(list));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	// Initialization for JComboBox presentation
	void initComboBox() {
		// Create the combo box:
		combobox = new JComboBox(labels);
		// Set initial state:
		combobox.setSelectedIndex(selection);

		// Handle changes to the state
		combobox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				ItemChooser.this.select(combobox.getSelectedIndex());
			}
		});

		// Lay out combo box and name label horizontally.
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.add(new JLabel(name));
		this.add(combobox);
	}

	// Initialization for JRadioButton presentation
	void initRadioButtons() {
		/* Create an array of mutually exclusive radio buttons. */
		// The array:
		radiobuttons = new JRadioButton[labels.length];
		// Used for exclusion:
		ButtonGroup radioButtonGroup = new ButtonGroup();
		// A shared listener:
		ChangeListener listener = new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JRadioButton b = (JRadioButton) e.getSource();
				if (b.isSelected()) {
					/*
					 * If we received this event because a button was selected, then loop through
					 * the list of buttons to figure out the index of the selected one.
					 */
					for (int i = 0; i < radiobuttons.length; i++) {
						if (radiobuttons[i] == b) {
							ItemChooser.this.select(i);
							return;
						}
					}
				}
			}
		};

		// Display the choice name in a border around the buttons.
		this.setBorder(new TitledBorder(new EtchedBorder(), name));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		/*
		 * Create the buttons, add them to the button group, and specify the event
		 * listener for each one.
		 */
		for (int i = 0; i < labels.length; i++) {
			radiobuttons[i] = new JRadioButton(labels[i]);
			if (i == selection) {
				radiobuttons[i].setSelected(true);
			}
			radiobuttons[i].addChangeListener(listener);
			radioButtonGroup.add(radiobuttons[i]);
			this.add(radiobuttons[i]);
		}
	}

	/*
	 * These simple property accessor methods just return field values These are
	 * read-only properties. The values are set by the constructor and may not be
	 * changed.
	 */
	public String getName() {
		return name;
	}

	public GUIControl getGUIControl() {
		return guiControl;
	}

	public String[] getLabels() {
		return labels;
	}

	public Object[] getValues() {
		return values;
	}

	/** Return the index of the currently selected item. */
	public int getSelectedIndex() {
		return selection;
	}

	/** Return the object associated with the currently selected item */
	public Object getSelectedValue() {
		return values[selection];
	}

	/**
	 * Set the selected item by specifying its index. Calling this method changes
	 * the on-screen display, but does not generate events.
	 **/
	public void setSelectedIndex(int selection) {
		switch (guiControl) {
			case LIST:
				list.setSelectedIndex(selection);
				break;
			case COMBOBOX:
				combobox.setSelectedIndex(selection);
				break;
			case RADIOBUTTONS:
				radiobuttons[selection].setSelected(true);
				break;
		}
		this.selection = selection;
	}

	/**
	 * This internal method is called when the selection changes. It stores the new
	 * selected index, and fires events to any registered listeners. The event
	 * listeners registered on the JList, JComboBox, or JRadioButtons all call this
	 * method.
	 */
	protected void select(int selection) {
		// Store the new selected index:
		this.selection = selection;
		// If there are any listeners registered:
		if (!listeners.isEmpty()) {
			// Create an event object to describe the selection.
			ItemChooser.Event e = new ItemChooser.Event(this, selection, values[selection]);
			// Loop through the listeners using an Iterator.
			for (Iterator<Listener> i = listeners.iterator(); i.hasNext();) {
				ItemChooser.Listener listener = i.next();
				// Notify each listener of the selection.
				listener.itemChosen(e);
			}
		}
	}

	// These methods are for event listener registration and deregistration.
	public void addItemChooserListener(ItemChooser.Listener listener) {
		listeners.add(listener);
	}

	public void removeItemChooserListener(ItemChooser.Listener listener) {
		listeners.remove(listener);
	}

	/**
	 * This inner class defines the event type generated by ItemChooser objects. The
	 * inner class name is Event, so the full name is ItemChooser.Event
	 */
	public static class Event extends java.util.EventObject {

		private static final long serialVersionUID = 3753625274217341259L;

		// Index of the selected item:
		int selectedIndex;
		// The value associated with it:
		Object selectedValue;

		public Event(ItemChooser source, int selectedIndex, Object selectedValue) {
			super(source);
			this.selectedIndex = selectedIndex;
			this.selectedValue = selectedValue;
		}

		public ItemChooser getItemChooser() {
			return (ItemChooser) getSource();
		}

		public int getSelectedIndex() {
			return selectedIndex;
		}

		public Object getSelectedValue() {
			return selectedValue;
		}
	}

	/**
	 * This inner interface must be implemented by any object that wants to be
	 * notified when the current selection in a ItemChooser component changes.
	 */
	public interface Listener extends java.util.EventListener {
		public void itemChosen(ItemChooser.Event e);
	}

	/**
	 * This inner class is a simple demonstration of the ItemChooser component. It
	 * uses command-line arguments as ItemChooser labels and values.
	 */
	public static class Demo {

		public static void main(String[] args) {

			// Create a window:
			final JFrame jFrame = new JFrame("ItemChooser Demo");

			ItemChooser itemChooser01 = new ItemChooser("Choice #1", args, null, 0, GUIControl.LIST);
			ItemChooser itemChooser02 = new ItemChooser("Choice #2", args, null, 0, GUIControl.COMBOBOX);
			ItemChooser itemChooser03 = new ItemChooser("Choice #3", args, null, 0, GUIControl.RADIOBUTTONS);

			ActionListener reportActionListener = new ActionListener() {
				public void actionPerformed(ActionEvent actionEvent) {
					/*
					 * Note the use of multi-line italic HTML text with the JOptionPane message
					 * dialog box.
					 */
					String msg = "";
					msg += "<html>";
					msg += "  <body>";
					msg += "    <i>";
					msg += "  " + itemChooser01.getName() + ": " + itemChooser01.getSelectedValue();
					msg += "      <br/>";
					msg += "  " + itemChooser02.getName() + ": " + itemChooser02.getSelectedValue();
					msg += "      <br/>";
					msg += "  " + itemChooser03.getName() + ": " + itemChooser03.getSelectedValue();
					msg += "    </i>";
					msg += "  </body>";
					msg += "</html>";

					JOptionPane.showMessageDialog(jFrame, msg);
				}
			};

			jFrame.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});

			// A "message line" in which to display results.
			final JLabel msgline = new JLabel(" ");

			// Create a panel holding three ItemChooser components.
			JPanel chooserPanel = new JPanel();

			// An event listener that displays changes on the message line.
			ItemChooser.Listener listener = new ItemChooser.Listener() {
				public void itemChosen(ItemChooser.Event event) {
					String messageLine = "";
					messageLine += event.getItemChooser().getName() + ": ";
					messageLine += event.getSelectedIndex() + ": ";
					messageLine += event.getSelectedValue();
					msgline.setText(messageLine);
				}
			};
			itemChooser01.addItemChooserListener(listener);
			itemChooser02.addItemChooserListener(listener);
			itemChooser03.addItemChooserListener(listener);

			/*
			 * Instead of tracking every change with a ItemChooser.Listener, applications
			 * can also just query the current state when they need it. Here's a button that
			 * does that.
			 */
			JButton reportJButton = new JButton("Report");
			reportJButton.addActionListener(reportActionListener);

			// Add the 3 ItemChooser objects, and the Button to the panel.
			chooserPanel.add(itemChooser01);
			chooserPanel.add(itemChooser02);
			chooserPanel.add(itemChooser03);
			chooserPanel.add(reportJButton);

			// Add the panel and the message line to the window.
			Container contentPane = jFrame.getContentPane();
			contentPane.add(chooserPanel, BorderLayout.CENTER);
			contentPane.add(msgline, BorderLayout.SOUTH);

			// Set the window size and pop it up.
			jFrame.pack();
			jFrame.setVisible(true);
			System.out.println("Demo for ItemChooser.");
		}
	}
}
