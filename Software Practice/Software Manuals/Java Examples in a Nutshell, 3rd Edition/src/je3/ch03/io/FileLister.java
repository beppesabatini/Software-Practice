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
package je3.ch03.io;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.List;
import java.awt.Panel;
import java.awt.TextField;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 71-75. This class creates
 * and displays a window containing a list of files and sub-directories in a
 * specified directory. Clicking on an entry in the list displays more
 * information about it. Double-clicking on an entry displays it, if a file, or
 * lists it if a directory. An optionally-specified FilenameFilter filters the
 * displayed list.
 */
public class FileLister extends Frame implements ActionListener, ItemListener {

	private static final long serialVersionUID = -7531813119538509155L;

	/*
	 * To display the directory contents. This is an AWT List, not a Java
	 * Collections List.
	 */
	private List list;
	// To display detail info in:
	private TextField details;
	// Holds the buttons:
	private Panel buttons;
	// The Up and Close buttons:
	private Button upButton, closeButton;
	// The directory currently listed:
	private File currentDirectory;
	// An optional filter for the directory:
	private FilenameFilter filter;
	// The directory contents:
	private String[] files;
	// To display dates and time correctly:
	private DateFormat dateFormatter = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);

	/**
	 * Constructor: create the GUI, and list the initial directory.
	 */
	public FileLister(String directory, FilenameFilter filter) {
		// Create the window:
		super("File Lister");
		// Save the filter, if any:
		this.filter = filter;

		// Destroy the window when the user requests it.
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				dispose();
			}
		});

		// Set up the list:
		list = new List(12, false);
		list.setFont(new Font("MonoSpaced", Font.PLAIN, 14));
		list.addActionListener(this);
		list.addItemListener(this);

		// Set up the details area:
		details = new TextField();
		details.setFont(new Font("MonoSpaced", Font.PLAIN, 12));
		details.setEditable(false);

		// Set up the button box:
		buttons = new Panel();
		buttons.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 5));
		buttons.setFont(new Font("SansSerif", Font.BOLD, 14));

		// Set up the two buttons:
		upButton = new Button("Up a Directory");
		closeButton = new Button("Close");
		upButton.addActionListener(this);
		closeButton.addActionListener(this);

		// Add buttons to button box:
		buttons.add(upButton);
		buttons.add(closeButton);

		// Add content to the window:
		this.add(list, BorderLayout.CENTER);
		this.add(details, BorderLayout.NORTH);
		this.add(buttons, BorderLayout.SOUTH);
		this.setSize(500, 350);

		// ...and, now list initial directory.
		listDirectory(directory);
	}

	/**
	 * This method uses the list() method to get all entries in a directory and then
	 * displays them in the List component.
	 */
	private void listDirectory(String directoryName) {
		// Convert the string to a File object, and check that the directory exists.
		File targetDirectory = new File(directoryName);
		if (targetDirectory.isDirectory() == false) {
			throw new IllegalArgumentException("FileLister: no such directory");
		}

		// Get the (filtered) directory entries
		files = targetDirectory.list(filter);

		// Sort the list of filenames.
		java.util.Arrays.sort(files);

		// Remove any old entries in the list, and add the new ones
		list.removeAll();
		list.add("[Up to Parent Directory]"); // A special case entry
		for (int i = 0; i < files.length; i++) {
			list.add(files[i]);
		}

		// Display directory name in the window title bar and in the details box.
		this.setTitle(directoryName);
		details.setText(directoryName);

		// Remember this directory for later.
		this.currentDirectory = targetDirectory;
	}

	/**
	 * This ItemListener method uses various File methods to obtain information
	 * about a file or directory. Then it displays that info.
	 **/
	@Override
	public void itemStateChanged(ItemEvent e) {
		// Subtract 1 for the "Up To Parent" entry:
		int index = list.getSelectedIndex() - 1;
		if (index < 0) {
			return;
		}
		// Get the selected entry:
		String fileName = files[index];
		// Convert to a parentDirectory/fileName combo:
		File file = new File(currentDirectory, fileName);
		// Confirm that the File exists:
		if (file.exists() == false) {
			throw new IllegalArgumentException("FileLister: " + "no such file or directory");
		}

		/*
		 * Get the details about the file or directory, and concatenate them to a
		 * string.
		 */
		String fileInfo = fileName;
		if (file.isDirectory()) {
			fileInfo += File.separator;
		}
		fileInfo += " " + file.length() + " bytes ";
		fileInfo += dateFormatter.format(new Date(file.lastModified()));
		if (file.canRead()) {
			fileInfo += " Read";
		}
		if (file.canWrite()) {
			fileInfo += " Write";
		}

		// And display the details string:
		details.setText(fileInfo);
	}

	/**
	 * This ActionListener method is invoked when the user double-clicks on an entry
	 * or clicks on one of the buttons. If they double-click on a file, create a
	 * FileViewer to display that file. If they double-click on a directory, call
	 * the listDirectory() method to display that directory
	 **/
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		if (actionEvent.getSource() == closeButton) {
			this.dispose();
		} else if (actionEvent.getSource() == upButton) {
			displayParentDirectory();
		}
		// Double click on an item:
		else if (actionEvent.getSource() == list) {
			// Check which item:
			int index = list.getSelectedIndex();
			// Handle the first item, "Up To Parent":
			if (index == 0) {
				displayParentDirectory();
			}
			// Otherwise, get filename:
			else {
				String name = files[index - 1];
				// Convert to a File:
				File file = new File(currentDirectory, name);
				String fullname = file.getAbsolutePath();
				// List the directory contents:
				if (file.isDirectory()) {
					listDirectory(fullname);
				}
				// Display file:
				else {
					new FileViewer(fullname).setVisible(true);
				}
			}
		}
	}

	/** A convenience method to display the contents of the parent directory. */
	private void displayParentDirectory() {
		String parent = currentDirectory.getParent();
		if (parent == null) {
			return;
		}
		listDirectory(parent);
	}

	/** A convenience method used by main() */
	private static void usage() {
		System.out.println("Usage: java FileLister [<directory_name>] " + "[-e <file_extension>]");
		System.exit(0);
	}

	/**
	 * A main() method so FileLister can be run standalone. Parse command line
	 * arguments and create the FileLister object. If an extension is specified,
	 * create a FilenameFilter for it. If no directory is specified, use the current
	 * directory.
	 */
	public static void main(String args[]) throws IOException {
		FileLister fileLister;
		// The filter, if any:
		FilenameFilter filenameFilter = null;
		// The specified directory, or the current directory:
		String directory = null;

		// Loop through the args array, parsing arguments.
		for (int i = 0; i < args.length; i++) {
			// Results can be constrained to a specified file extension:
			if (args[i].equals("-e") || args[i].equals("-extension")) {
				if (++i >= args.length) {
					// The command line is wrong:
					usage();
				}
				String suffix = args[i];
				filenameFilter = createFilenameFilter(suffix);
			} else {
				/*
				 * If a directory has already (seemingly) been specified, the command line is
				 * wrong, so fail:
				 */
				if (directory != null) {
					usage();
				} else {
					directory = args[i];
				}
			}
		}

		// If no directory is specified, use the current directory.
		if (directory == null) {
			directory = System.getProperty("user.dir");
		}
		// Create the FileLister object, with directory and filter specified.
		fileLister = new FileLister(directory, filenameFilter);
		// Arrange for the application to exit when the window is closed.
		fileLister.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		// Finally, pop the window up.
		fileLister.setVisible(true);
	}

	/**
	 * This function returns a simple FilenameFilter. FilenameFilter is just an
	 * interface, here with an anonymous object implementing the interface. It
	 * defines the accept() method required to determine whether a specified file
	 * should be displayed in the FileLister, or be filtered out. A file gets
	 * through the filter if its name ends with the specified extension, or if it is
	 * a directory.
	 */
	private static FilenameFilter createFilenameFilter(String suffix) {
		FilenameFilter filenameFilter = new FilenameFilter() {
			@Override
			public boolean accept(File directoryToFilter, String name) {
				if (name.endsWith(suffix)) {
					return true;
				} else {
					// Subdirectories get through the filter:
					return (new File(directoryToFilter, name)).isDirectory();
				}
			}
		};
		return (filenameFilter);
	}
}
