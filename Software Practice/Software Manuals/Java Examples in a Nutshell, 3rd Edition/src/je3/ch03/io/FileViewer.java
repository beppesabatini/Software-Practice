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

import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 67-70. This class creates
 * and displays a window containing a TextArea, in which the contents of a text
 * file are displayed.
 */
public class FileViewer extends Frame implements ActionListener {

	private static final long serialVersionUID = -584890913148502563L;

	// The default directory to display in the FileDialog:
	private String directory;
	// The area in which to display the file contents:
	private TextArea textArea;

	/** Convenience constructor: the file viewer starts out blank. */
	public FileViewer() {
		this(null, null);
	}

	/** Convenience constructor: to display a file from the current directory. */
	public FileViewer(String fileName) {
		this(null, fileName);
	}

	/**
	 * The real constructor. Create a FileViewer object to display the specified
	 * file from the specified directory.
	 **/
	public FileViewer(String directory, String fileName) {
		// Create the frame:
		super();

		// Destroy the window when the user requests it.
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});

		// Create a TextArea to display the contents of the file.
		textArea = new TextArea("", 24, 80);
		textArea.setFont(new Font("MonoSpaced", Font.PLAIN, 12));
		textArea.setEditable(false);
		this.add("Center", textArea);

		// Create a bottom panel to hold a couple of buttons.
		Panel panel = new Panel();
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));
		this.add(panel, "South");

		// Create the buttons and arrange to handle button clicks.
		Font font = new Font("SansSerif", Font.BOLD, 14);
		Button openFileButton = new Button("Open File");
		Button closeButton = new Button("Close");
		openFileButton.addActionListener(this);
		openFileButton.setActionCommand("open");
		openFileButton.setFont(font);
		closeButton.addActionListener(this);
		closeButton.setActionCommand("close");
		closeButton.setFont(font);
		panel.add(openFileButton);
		panel.add(closeButton);

		this.pack();

		/*
		 * Figure out the directory, from the filename or current directory, if
		 * necessary.
		 */
		if (directory == null) {
			File file;
			// Test for full file name:
			if ((fileName != null) && (file = new File(fileName)).isAbsolute()) {
				directory = file.getParent();
				fileName = file.getName();
			} else {
				directory = System.getProperty("user.dir");
			}
		}

		// Remember the directory, for FileDialog.
		this.directory = directory;
		// Now load and display the file.
		setFile(directory, fileName);
	}

	/**
	 * Load and display the specified file from the specified directory
	 */
	private void setFile(String directory, String filename) {
		if ((filename == null) || (filename.length() == 0))
			return;
		File file;
		FileReader inputFileReader = null;
		/*
		 * Read and display the file contents. Since we're reading text, we use a
		 * FileReader (a character input stream)instead of a FileInputStream (a byte
		 * input stream).
		 */
		try {
			// Create a file object:
			file = new File(directory, filename);
			// ...and, a character input stream to read it.
			inputFileReader = new FileReader(file);
			// Read 4K characters at a time:
			char[] buffer = new char[4096];
			// How many characters to read each time:
			int length;
			// Clear the text area:
			textArea.setText("");
			// Read a batch of chars:
			while ((length = inputFileReader.read(buffer)) != -1) {
				// Convert to a string
				String bufferToString = new String(buffer, 0, length);
				// And display them:
				textArea.append(bufferToString);
			}
			// Set the window title:
			this.setTitle("FileViewer: " + filename);
			// Go to the start of the file:
			textArea.setCaretPosition(0);
		}
		// Display messages if something goes wrong.
		catch (IOException ioException) {
			textArea.setText(ioException.getClass().getName() + ": " + ioException.getMessage());
			this.setTitle("FileViewer: " + filename + ": I/O Exception");
		}
		// Always be sure to close the input stream!
		finally {
			try {
				if (inputFileReader != null) {
					inputFileReader.close();
				}
			} catch (IOException ioException) {
				// This is safe to ignore.
			}
		}
	}

	/**
	 * Handle button clicks
	 */
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		String command = actionEvent.getActionCommand();
		// If user clicked the "Open File" button:
		if (command.equals("open")) {
			// Create a file dialog box to prompt for a new, different file to display.
			FileDialog fileDialog = new FileDialog(this, "Open File", FileDialog.LOAD);
			/*
			 * Set the default directory. This works because the fileDialog blocks, and this
			 * code won't be run until after the user clicks the second "Open File" button.
			 */
			fileDialog.setDirectory(directory);

			// Display the dialog and wait for the user's response.
			fileDialog.setVisible(true);

			// Remember the new default directory:
			directory = fileDialog.getDirectory();
			// Load and display the selection:
			setFile(directory, fileDialog.getFile());
			// Get rid of the dialog box:
			fileDialog.dispose();
		}
		// If the user clicked the "Close" button:
		else if (command.equals("close")) {
			// Then close the window:
			this.dispose();
		}
	}

	/**
	 * The FileViewer can be used by other classes, or it can be used as a
	 * standalone with this main() method.
	 **/
	static public void main(String[] args) throws IOException {
		// Create a FileViewer object:
		Frame frame = new FileViewer((args.length == 1) ? args[0] : null);
		// Arrange to exit when the FileViewer window closes.
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		// And pop the window up:
		frame.setVisible(true);
	}
}
