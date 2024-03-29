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
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import je3.ch11.gui.ItemChooser;
import je3.ch11.gui.ItemChooser.GUIControl;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 298-302. This is a JDialog
 * subclass that allows the user to select a font, in any style and size, from
 * the list of available fonts on the system.
 * <p/>
 * The dialog is modal. Display it with setVisible(true); that method does not
 * return until the user dismisses the dialog. When setVisible(true) returns,
 * call getSelectedFont() to obtain the user's selection. If the user clicked
 * the dialog's "Cancel" button, getSelectedFont() will return null.
 */
public class FontChooser extends JDialog {

	private static final long serialVersionUID = 7086066274251957842L;

	/*
	 * These fields define the component properties.
	 */
	// The name of the font family:
	String family;
	// The font style:
	int style;
	// The font size:
	int size;
	// The Font they correspond to:
	Font selectedFont;

	// This is the list of all font families on the system.
	String[] fontFamilies;

	// The various Swing components used in the dialog.
	ItemChooser families, styles, sizes;
	JTextArea preview;
	JButton okay, cancel;

	// The names to appear in the "Style" menu.
	static final String[] styleNames = new String[] { "Plain", "Italic", "Bold", "BoldItalic" };
	// The style values that correspond to those names
	static final Integer[] styleValues = new Integer[] { Font.PLAIN, Font.ITALIC, Font.BOLD, Font.BOLD + Font.ITALIC };
	// The size "names" to appear in the size menu
	static final String[] sizeNames = new String[] { "8", "10", "12", "14", "18", "20", "24", "28", "32", "40", "48",
			"56", "64", "72" };

	// This is the default preview string displayed in the dialog box.
	static String defaultPreviewString = initPreviewString();

	static String initPreviewString() {
		String defaultPreviewString = "";
		defaultPreviewString += "ABCDEFGHIJKLMNOPQRSTUVWXYZ\n";
		defaultPreviewString += "abcdefghijklmnopqrstuvwxyz\n";
		defaultPreviewString += "1234567890!@#$%^&*()_-=+[]{}<,.>\n";
		defaultPreviewString += "The quick brown fox jumps over the lazy dog";
		return (defaultPreviewString);
	}

	/** Create a font chooser dialog for the specified frame. */
	public FontChooser(Frame owner) {
		// Set dialog frame and title.
		super(owner, "Choose a Font");

		/*
		 * This dialog must be used as a modal dialog. In order to be used as a modeless
		 * dialog, it would have to fire a PropertyChangeEvent whenever the selected
		 * font changed, so that applications could be notified of the user's
		 * selections.
		 */
		setModal(true);

		// Figure out what fonts are available on the system.
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		fontFamilies = env.getAvailableFontFamilyNames();

		// Set initial values for the properties.
		family = fontFamilies[0];
		style = Font.PLAIN;
		size = 18;
		selectedFont = new Font(family, style, size);

		/*
		 * Create ItemChooser objects that allow the user to select font family, style,
		 * and size.
		 */
		families = new ItemChooser("Family", fontFamilies, null, 0, GUIControl.COMBOBOX);
		styles = new ItemChooser("Style", styleNames, styleValues, 0, GUIControl.COMBOBOX);
		sizes = new ItemChooser("Size", sizeNames, null, 4, GUIControl.COMBOBOX);

		// Now register event listeners to handle selections.
		families.addItemChooserListener(new ItemChooser.Listener() {
			public void itemChosen(ItemChooser.Event e) {
				setFontFamily((String) e.getSelectedValue());
			}
		});
		styles.addItemChooserListener(new ItemChooser.Listener() {
			public void itemChosen(ItemChooser.Event e) {
				setFontStyle((int) e.getSelectedValue());
			}
		});
		sizes.addItemChooserListener(new ItemChooser.Listener() {
			public void itemChosen(ItemChooser.Event e) {
				setFontSize(Integer.parseInt((String) e.getSelectedValue()));
			}
		});

		// Create a component to preview the font.
		preview = new JTextArea(defaultPreviewString, 5, 40);
		preview.setFont(selectedFont);

		// Create buttons to dismiss the dialog, and set handlers on them.
		okay = new JButton("Okay");
		cancel = new JButton("Cancel");
		okay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedFont = null;
				setVisible(false);
			}
		});

		// Put the ItemChoosers in a Box.
		Box choosersBox = Box.createHorizontalBox();
		choosersBox.add(Box.createHorizontalStrut(15));
		choosersBox.add(families);
		choosersBox.add(Box.createHorizontalStrut(15));
		choosersBox.add(styles);
		choosersBox.add(Box.createHorizontalStrut(15));
		choosersBox.add(sizes);
		choosersBox.add(Box.createHorizontalStrut(15));
		choosersBox.add(Box.createGlue());

		// Put the dismiss buttons in another box.
		Box buttonBox = Box.createHorizontalBox();
		buttonBox.add(Box.createGlue());
		buttonBox.add(okay);
		buttonBox.add(Box.createGlue());
		buttonBox.add(cancel);
		buttonBox.add(Box.createGlue());

		/*
		 * Put the choosers at the top, the buttons at the bottom, and the preview in
		 * the middle.
		 */
		Container contentPane = getContentPane();
		contentPane.add(new JScrollPane(preview), BorderLayout.CENTER);
		contentPane.add(choosersBox, BorderLayout.NORTH);
		contentPane.add(buttonBox, BorderLayout.SOUTH);

		// Set the dialog size based on the component size.
		pack();
	}

	/**
	 * Call this method after show() to obtain the user's selection. If the user
	 * used the "Cancel" button, this will return null.
	 */
	public Font getSelectedFont() {
		return selectedFont;
	}

	// These are other property getter methods.
	public String getFontFamily() {
		return family;
	}

	public int getFontStyle() {
		return style;
	}

	public int getFontSize() {
		return size;
	}

	/*
	 * The property setter methods are a little more complicated. Note that none of
	 * these setter methods update the corresponding ItemChooser components as they
	 * ought to.
	 */
	public void setFontFamily(String name) {
		family = name;
		changeFont();
	}

	public void setFontStyle(int style) {
		this.style = style;
		changeFont();
	}

	public void setFontSize(int size) {
		this.size = size;
		changeFont();
	}

	public void setSelectedFont(Font font) {
		selectedFont = font;
		family = font.getFamily();
		style = font.getStyle();
		size = font.getSize();
		preview.setFont(font);
	}

	// This method is called when the family, style, or size changes.
	protected void changeFont() {
		selectedFont = new Font(family, style, size);
		preview.setFont(selectedFont);
	}

	// Override this inherited method to prevent anyone from making us modeless.
	public boolean isModal() {
		return true;
	}

	/** This inner class demonstrates the use of FontChooser. */
	public static class Demo {
		public static void main(String[] args) {
			// Create some components and a FontChooser dialog
			final JFrame jFrame = new JFrame("demo");
			jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			final JButton jButton = new JButton("Push Me!");
			final FontChooser chooser = new FontChooser(jFrame);

			// Handle button clicks.
			jButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// Pop up the dialog:
					chooser.setVisible(true);
					// Get the user's selection:
					Font font = chooser.getSelectedFont();
					// If not cancelled, set the button font:
					if (font != null) {
						jButton.setFont(font);
					}
				}
			});

			// Display the demo:
			jFrame.getContentPane().add(jButton);
			jFrame.setSize(200, 100);
			jFrame.setVisible(true);
		}
	}
}
