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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 317-324. This class
 * implements a simple web browser using the HTML display capabilities of the
 * JEditorPane component.
 */
public class WebBrowser extends JFrame implements HyperlinkListener, PropertyChangeListener {

	private static final long serialVersionUID = 356003018049903004L;

	/**
	 * A simple main() method that allows the WebBrowser class to be used as a
	 * stand-alone application.
	 */
	public static void main(String[] args) throws IOException {
		// End the program when there are no more open browser windows.
		WebBrowser.setExitWhenLastWindowClosed(true);
		// Create a browser window:
		WebBrowser browser = new WebBrowser();
		// Set its size:
		browser.setSize(800, 600);
		// Make it visible:
		browser.setVisible(true);

		// Tell the browser what to display. This method is defined below.
		browser.displayPage((args.length > 0) ? args[0] : browser.getHome());
	}

	/*
	 * This class uses GUIResourceBundle to create its menu bar and toolbar. This
	 * static initializer performs one-time registration of the required
	 * ResourceParser classes.
	 */
	static {
		GUIResourceBundle.registerResourceParser(new MenuBarParser());
		GUIResourceBundle.registerResourceParser(new MenuParser());
		GUIResourceBundle.registerResourceParser(new ActionParser());
		GUIResourceBundle.registerResourceParser(new CommandParser());
		GUIResourceBundle.registerResourceParser(new ToolBarParser());
	}

	/*
	 * These are the Swing components that the browser uses.
	 */
	// Where the HTML is displayed:
	JEditorPane textPane;
	// Displays one-line messages:
	JLabel messageLine;
	// Displays and edits the current URL:
	JTextField urlField;
	// Allows the user to select a local file:
	JFileChooser fileChooser;

	/*
	 * These are Actions that are used in the menu bar and toolbar. We obtain
	 * explicit references to them from the GUIResourceBundle so we can enable and
	 * disable them.
	 */
	Action backAction, forwardAction;

	// These fields are used to maintain the browsing history of the window
	// The history list:
	List<URL> history = new ArrayList<URL>();
	// Current location in it:
	int currentHistoryPage = -1;
	// Trim the history list when over this size:
	public static final int MAX_HISTORY = 50;

	// These static fields control the behavior of the close() action.
	static int numBrowserWindows = 0;
	static boolean exitWhenLastWindowClosed = false;

	// This is where the "home()" method takes us. See also setHome().
	// A default value:
	String home = "http://www.google.com";

	/** Create and initialize a new WebBrowser window */
	public WebBrowser() {
		// Chain to JFrame constructor:
		super("WebBrowser");

		// Create HTML window:
		textPane = new JEditorPane();
		// Don't allow the user to edit it:
		textPane.setEditable(false);

		/*
		 * Register action listeners. The first is to handle hyperlinks. The second is
		 * to receive property change notifications, which tell us when a document is
		 * done loading. This class implements these EventListener interfaces, and the
		 * methods are defined below.
		 */
		textPane.addHyperlinkListener(this);
		textPane.addPropertyChangeListener(this);

		// Put the text pane in a JScrollPane in the center of the window.
		this.getContentPane().add(new JScrollPane(textPane), BorderLayout.CENTER);

		// Now create a message line and place it at the bottom of the window.
		messageLine = new JLabel(" ");
		this.getContentPane().add(messageLine, BorderLayout.SOUTH);

		/*
		 * Read the file WebBrowserResources.properties (and any localized variants
		 * appropriate for the current Locale) to create a GUIResourceBundle from which
		 * we'll get our menu bar and toolbar.
		 */
		GUIResourceBundle resources = new GUIResourceBundle(this, "je3.ch11.gui." + "WebBrowserResources");

		// Read a menu bar from the resource bundle and display it.
		JMenuBar menubar = (JMenuBar) resources.getResource("menubar", JMenuBar.class);
		this.setJMenuBar(menubar);

		// Read a toolbar from the resource bundle. Don't display it yet.
		JToolBar toolbar = (JToolBar) resources.getResource("toolbar", JToolBar.class);

		/*
		 * Create a text field that the user can enter a URL in. Set up an action
		 * listener to respond to the ENTER key in that field
		 */
		urlField = new JTextField();
		urlField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayPage(urlField.getText());
			}
		});

		// Add the URL field and a label for it to the end of the toolbar.
		toolbar.add(new JLabel("         URL:"));
		toolbar.add(urlField);

		// And add the toolbar to the top of the window
		this.getContentPane().add(toolbar, BorderLayout.NORTH);

		/*
		 * Read cached copies of two Action objects from the resource bundle These
		 * actions are used by the menu bar and toolbar, and enabling and disabling them
		 * enables and disables the menu and toolbar items.
		 */
		backAction = (Action) resources.getResource("action.back", Action.class);
		forwardAction = (Action) resources.getResource("action.forward", Action.class);

		// Start off with both actions disabled.
		backAction.setEnabled(false);
		forwardAction.setEnabled(false);

		// Create a ThemeManager for this frame, and add a Theme menu to the menu bar.
		ThemeManager themes = new ThemeManager(this, resources);
		menubar.add(themes.getThemeMenu());

		// Keep track of how many web browser windows are open.
		WebBrowser.numBrowserWindows++;
	}

	/** Set the static property that controls the behavior of close(). */
	public static void setExitWhenLastWindowClosed(boolean b) {
		exitWhenLastWindowClosed = b;
	}

	/** These are accessor methods for the home property. */
	public void setHome(String home) {
		this.home = home;
	}

	public String getHome() {
		return home;
	}

	/**
	 * This internal method attempts to load and display the specified URL. It is
	 * called from various places throughout the class.
	 */
	boolean visit(URL url) {
		try {
			String href = url.toString();
			// Start animating. Animation is stopped in propertyChanged().
			startAnimation("Loading " + href + "...");
			// Load and display the URL:
			textPane.setPage(url);
			// Display URL in window title bar:
			this.setTitle(href);
			// Display URL in text input field:
			urlField.setText(href);
			// Return success:
			return true;
		} catch (IOException ex) {
			// If page loading fails:
			stopAnimation();
			messageLine.setText("Can't load page: " + ex.getMessage());
			// Return failure.
			return false;
		}
	}

	/**
	 * Ask the browser to display the specified URL, and put it in the history list.
	 */
	public void displayPage(URL url) {
		// Go to the specified url, and if we succeed:
		if (visit(url)) {
			// Add the url to the history list:
			history.add(url);
			int numberEntries = history.size();
			// Trim history when too large:
			if (numberEntries > MAX_HISTORY + 10) {
				history = history.subList(numberEntries - MAX_HISTORY, numberEntries);
				numberEntries = MAX_HISTORY;
			}
			// Set current history page:
			currentHistoryPage = numberEntries - 1;
			// If we can go back, then enable the Back action.
			if (currentHistoryPage > 0) {
				backAction.setEnabled(true);
			}
		}
	}

	/** Like displayPage(URL), but takes a string instead */
	public void displayPage(String href) {
		try {
			URL newUrl = new URL(href);
			displayPage(newUrl);
		} catch (MalformedURLException ex) {
			messageLine.setText("Bad URL: " + href);
		}
	}

	/** Allow the user to choose a local file, and display it. */
	public void openPage() {
		// Lazy creation: don't create the JFileChooser until it is needed.
		if (fileChooser == null) {
			fileChooser = new JFileChooser();
			// This javax.swing.filechooser.FileFilter displays only HTML files.
			FileFilter filter = new FileFilter() {
				public boolean accept(File f) {
					String fn = f.getName();
					if (fn.endsWith(".html") || fn.endsWith(".htm")) {
						return true;
					} else {
						return false;
					}
				}

				public String getDescription() {
					return "HTML Files";
				}
			};
			fileChooser.setFileFilter(filter);
			fileChooser.addChoosableFileFilter(filter);
		}

		// Ask the user to choose a file.
		int result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			// If they didn't click "Cancel", then try to display the file.
			File selectedFile = fileChooser.getSelectedFile();
			String url = "file:/" + selectedFile.getAbsolutePath();
			displayPage(url);
		}
	}

	/** Go back to the previously displayed page. */
	public void back() {
		// Go back, if we can:
		if (currentHistoryPage > 0) {
			visit((URL) history.get(--currentHistoryPage));
		}
		// Enable or disable actions as appropriate.
		backAction.setEnabled((currentHistoryPage > 0));
		forwardAction.setEnabled((currentHistoryPage < history.size() - 1));
	}

	/** Go forward to the next page in the history list. */
	public void forward() {
		// Go forward, if we can.
		if (currentHistoryPage < history.size() - 1) {
			visit((URL) history.get(++currentHistoryPage));
		}
		// Enable or disable actions as appropriate.
		backAction.setEnabled((currentHistoryPage > 0));
		forwardAction.setEnabled((currentHistoryPage < history.size() - 1));
	}

	/** Reload the current page in the history list. */
	public void reload() {
		if (currentHistoryPage != -1) {
			// We can't reload the current document, so display a blank page.
			textPane.setDocument(new javax.swing.text.html.HTMLDocument());
			// Now re-visit the current URL:
			visit(history.get(currentHistoryPage));
		}
	}

	/** Display the page specified by the "home" property */
	public void home() {
		displayPage(getHome());
	}

	/** Open a new browser window */
	public void newBrowser() {
		WebBrowser browser = new WebBrowser();
		browser.setSize(this.getWidth(), this.getHeight());
		browser.setVisible(true);
	}

	/**
	 * Close this browser window. If this was the only open window, and
	 * exitWhenLastBrowserClosed is true, then exit the VM.
	 */
	public void close() {
		// Hide the window:
		this.setVisible(false);
		// Destroy the window:
		this.dispose();
		// Synchronize for thread-safety:
		synchronized (WebBrowser.class) {
			// There is one window fewer now.
			WebBrowser.numBrowserWindows--;
			if ((numBrowserWindows == 0) && exitWhenLastWindowClosed) {
				// Exit if it was the last one.
				System.exit(0);
			}
		}
	}

	/**
	 * Exit the VM. If confirm is true, ask the user if they are sure. Note that
	 * showConfirmDialog() displays a dialog, waits for the user, and returns the
	 * user's response (i.e. the button the user selected).
	 */
	public void exit(boolean confirm) {
		if (confirm == true) {
			System.exit(0);
		}
		String message = "Are you sure you want to quit?";
		String title = "Really Quit?";
		int optionType = JOptionPane.YES_NO_OPTION;
		if (JOptionPane.showConfirmDialog(this, message, title, optionType) == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}

	/**
	 * This method implements HyperlinkListener. It is invoked when the user clicks
	 * on a hyperlink, or move the mouse onto or off of a link.
	 */
	@Override
	public void hyperlinkUpdate(HyperlinkEvent e) {
		// What happened?
		HyperlinkEvent.EventType type = e.getEventType();
		// Click!
		// Follow the link; display the new page.
		if (type == HyperlinkEvent.EventType.ACTIVATED) {
			displayPage(e.getURL());
		}
		// Mouse over!
		else if (type == HyperlinkEvent.EventType.ENTERED) {
			// When the mouse goes over a link, display it in the message line.
			messageLine.setText(e.getURL().toString());
		}
		// Mouse out!
		else if (type == HyperlinkEvent.EventType.EXITED) {
			// Clear the message line.
			messageLine.setText(" ");
		}
	}

	// The content of each state:
	static private final String[] animationStates = initAnimationStates();

	static String[] initAnimationStates() {
		String[] states = null;
		states = new String[] { "-", "\\", "|", "/", "-", "\\", "|", "/", ",", ".", "o", "0", "O", "#", "*", "+" };
		return (states);
	}

	/**
	 * This method implements java.beans.PropertyChangeListener. It is invoked
	 * whenever a bound property changes in the JEditorPane object. The property we
	 * are interested in is the "page" property, because it tells us when a page has
	 * finished loading.
	 */
	@Override
	public void propertyChange(PropertyChangeEvent e) {
		// If the page property changed:
		if (e.getPropertyName().equals("page"))
			// Then stop the loading animation.
			stopAnimation();
	}

	/**
	 * The fields and methods below implement a simple animation in the web browser
	 * message line; they are used to provide user feedback while web pages are
	 * loading.
	 */
	// The "loading..." message to display:
	String animationMessage;
	// What state of the animation are we in:
	int animationState = 0;

	/** This object calls the animate() method 8 times a second. */
	javax.swing.Timer animator = new javax.swing.Timer(125, new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			animate();
		}
	});

	/** Display the next frame. Called by the animator timer */
	void animate() {
		// Get the next state.
		String frame = animationStates[animationState++];
		// Update the message line.
		messageLine.setText(animationMessage + " " + frame);
		animationState = animationState % animationStates.length;
	}

	/** Start the animation. Called by the visit() method. */
	void startAnimation(String msg) {
		// Save the message to display.
		animationMessage = msg;
		// Start with state 0 of the animation.
		animationState = 0;
		// Tell the timer to start firing.
		animator.start();
	}

	/** Stop the animation. Called by the propertyChanged() method. */
	void stopAnimation() {
		// Tell the timer to stop firing events:
		animator.stop();
		// Clear the message line:
		messageLine.setText(" ");
	}
}
