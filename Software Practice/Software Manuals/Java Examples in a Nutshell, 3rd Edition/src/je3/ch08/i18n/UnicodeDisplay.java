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

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Scrollbar;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollBar;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 210-213. This program
 * displays Unicode glyphs using user-specified fonts and font styles. Most
 * systems will not be able to display every symbol.
 */
public class UnicodeDisplay extends JFrame implements ActionListener {

	private static final long serialVersionUID = -4274749717527049891L;

	int page = 0;
	UnicodePanel unicodePanel;
	JScrollBar jScrollBar;
	String fontfamily = "Serif";
	int fontstyle = Font.PLAIN;

	/**
	 * This constructor creates the frame, menu bar, and scroll bar that work along
	 * with the UnicodePanel class, defined below
	 */
	public UnicodeDisplay(String name) {
		super(name);
		// Create the panel:
		unicodePanel = new UnicodePanel();
		// Initialize it:
		unicodePanel.setBase((char) (page * 0x100));
		// Center it:
		getContentPane().add(unicodePanel, "Center");

		// Create and set up a scroll bar, and put it on the right.
		jScrollBar = new JScrollBar(Scrollbar.VERTICAL, 0, 1, 0, 0xFF);
		jScrollBar.setUnitIncrement(1);
		jScrollBar.setBlockIncrement(0x10);
		jScrollBar.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent adjustmentEvent) {
				page = adjustmentEvent.getValue();
				unicodePanel.setBase((char) (page * 0x100));
			}
		});
		getContentPane().add(jScrollBar, "East");

		// Set things up so we respond to window close requests.
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});

		// Handle Page Up and Page Down and the up and down arrow keys.
		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent keyEvent) {
				int code = keyEvent.getKeyCode();
				int oldpage = page;
				if ((code == KeyEvent.VK_PAGE_UP) || (code == KeyEvent.VK_UP)) {
					if (keyEvent.isShiftDown()) {
						page -= 0x10; // hex16
					} else
						page -= 1;
					if (page < 0) {
						// If this takes us prior to the first page, round up to 0, the first page.
						page = 0;
					}
				} else if ((code == KeyEvent.VK_PAGE_DOWN) || (code == KeyEvent.VK_DOWN)) {
					if (keyEvent.isShiftDown()) {
						page += 0x10; // hex 16
					} else
						page += 1;
					if (page > 0xff) {
						// If this takes us past the last page, round down to 255, the last page.
						page = 0xff;
					}
				}
				if (page != oldpage) { // If anything has changed...
					// ...update the display...
					unicodePanel.setBase((char) (page * 0x100));
					// ...and update the scroll bar to match.
					jScrollBar.setValue(page);
				}
			}
		});

		// Set up a menu system to change fonts. Use a convenience method.
		JMenuBar menubar = new JMenuBar();
		this.setJMenuBar(menubar);
		menubar.add(makemenu("Font Family", new String[] { "Serif", "SansSerif", "Monospaced" }, this));
		menubar.add(makemenu("Font Style", new String[] { "Plain", "Italic", "Bold", "BoldItalic" }, this));
	}

	/** This method handles the items in the menu bars. */
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("Serif")) {
			fontfamily = "Serif";
		} else if (cmd.equals("SansSerif")) {
			fontfamily = "SansSerif";
		} else if (cmd.equals("Monospaced")) {
			fontfamily = "Monospaced";
		} else if (cmd.equals("Plain")) {
			fontstyle = Font.PLAIN;
		} else if (cmd.equals("Italic")) {
			fontstyle = Font.ITALIC;
		} else if (cmd.equals("Bold")) {
			fontstyle = Font.BOLD;
		} else if (cmd.equals("BoldItalic")) {
			fontstyle = Font.BOLD + Font.ITALIC;
		}
		unicodePanel.setFont(fontfamily, fontstyle);
	}

	/** A convenience method to create a Menu from an array of items. */
	private JMenu makemenu(String name, String[] itemnames, ActionListener listener) {
		JMenu jMenu = new JMenu(name);
		for (int i = 0; i < itemnames.length; i++) {
			JMenuItem item = new JMenuItem(itemnames[i]);
			item.addActionListener(listener);
			item.setActionCommand(itemnames[i]); // okay here, though
			jMenu.add(item);
		}
		return jMenu;
	}

	/** The main() program just create a window, packs it, and shows it. */
	public static void main(String[] args) {
		UnicodeDisplay unicodeDisplay = new UnicodeDisplay("Unicode Displayer");
		unicodeDisplay.pack();
		unicodeDisplay.setVisible(true);
	}

	/**
	 * This nested class is the one that displays one "page" of Unicode glyphs at a
	 * time. Each "page" is 256 characters, arranged into 16 rows of 16 columns
	 * each.
	 **/
	public static class UnicodePanel extends JComponent {

		private static final long serialVersionUID = 3680923828459254383L;

		// The "base" character is the starting point for the display.
		protected char base;
		protected Font font = new Font("Lucinda Sans Unicode", Font.PLAIN, 36);
		protected Font headingfont = new Font("monospaced", Font.BOLD, 36);
		static final int lineheight = 60;
		static final int charspacing = 40;
		static final int x0 = 130;
		static final int y0 = 80;

		/** Specify where to begin displaying, and then re-display. */
		public void setBase(char base) {
			this.base = base;
			repaint();
		}

		/** Set a new font name or style, and then re-display. */
		public void setFont(String family, int style) {
			this.font = new Font(family, style, 18);
			repaint();
		}

		/**
		 * The paintComponent() method actually draws the page of glyphs.
		 */
		public void paintComponent(Graphics g) {
			/*
			 * Start on a 16-character boundary. This bitwise "and" operation will round
			 * down the "base" value to a multiple of sixteen. (The single '&' operator
			 * invokes bitwise boolean "and".)
			 */
			int start = (int) base & 0xFFF0;

			// Draw the headings in a special font.
			g.setFont(headingfont);

			// Draw 0..F on top as column labels.
			for (int i = 0; i < 16; i++) {
				String s = Integer.toString(i, 16);
				g.drawString(s, x0 + i * charspacing, y0 - 50);
			}

			// Draw column down the left as row labels.
			for (int i = 0; i < 16; i++) {
				int j = start + i * 16;
				String s = Integer.toString(j, 16);
				g.drawString(s, 10, y0 + i * lineheight);
			}

			// Now draw the characters.
			g.setFont(font);
			char[] character = new char[1];
			for (int i = 0; i < 16; i++) {
				for (int j = 0; j < 16; j++) {
					character[0] = (char) (start + j * 16 + i);
					g.drawChars(character, 0, 1, x0 + i * charspacing, y0 + j * lineheight);
				}
			}
		}

		/** Custom components like this one should always have this method. */
		public Dimension getPreferredSize() {
			return new Dimension(x0 + 16 * charspacing, y0 + 16 * lineheight);
		}
	}
}
