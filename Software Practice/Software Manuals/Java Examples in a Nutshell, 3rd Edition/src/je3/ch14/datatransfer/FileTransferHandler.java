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
package je3.ch14.datatransfer;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.InputEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.TransferHandler;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 444-446. This
 * TransferHandler subclass wraps another TransferHandler and delegates most of
 * its operations to the wrapped handler. It adds the ability to to drop or
 * paste files using the predefined DataFlavor.javaFileListFlavor. When a file
 * list is pasted or dropped, the FileTransferHandler assumes the files are
 * text, reads them in order, concatenates their contents, and then passes the
 * resulting string to the wrapped handler for insertion.
 */
public class FileTransferHandler extends TransferHandler {

	private static final long serialVersionUID = -7754212644388506351L;

	// The handler that we wrap:
	TransferHandler wrappedHandler;
	// We use this array to test the wrapped handler:
	static DataFlavor[] stringFlavorArray = new DataFlavor[] { DataFlavor.stringFlavor };

	/** Pass an existing TransferHandler to this constructor. */
	public FileTransferHandler(TransferHandler wrappedHandler) {
		// Fail immediately on null:
		if (wrappedHandler == null) {
			throw new NullPointerException();
		}
		// Remember wrapped handler:
		this.wrappedHandler = wrappedHandler;
	}

	/**
	 * This method returns true if the TransferHandler knows how to work with one of
	 * the specified flavors. This implementation first checks the superclass, then
	 * checks for fileListFlavor support.
	 */
	@Override
	public boolean canImport(JComponent jComponent, DataFlavor[] flavors) {
		// If the wrapped handler can import it, we're done.
		if (wrappedHandler.canImport(jComponent, flavors)) {
			return true;
		}

		/*
		 * Otherwise, if the wrapped handler can handle string imports, then see if we
		 * are being offered a list of files that we can convert to a string.
		 */
		if (wrappedHandler.canImport(jComponent, stringFlavorArray)) {
			for (int i = 0; i < flavors.length; i++) {
				if (flavors[i].equals(DataFlavor.javaFileListFlavor)) {
					return true;
				}
			}
		}

		// Otherwise, we can't import any of the flavors.
		return false;
	}

	/**
	 * If the wrapped handler can import strings and the specified Transferable can
	 * provide its data as a List of File objects, then we read the files, and pass
	 * their contents as a string to the wrapped handler. Otherwise, we offer the
	 * Transferable to the wrapped handler to handle on its own.
	 */
	@Override
	public boolean importData(JComponent jComponent, Transferable transferable) {
		/*
		 * See if we're offered a java.util.List of java.io.File objects. We handle this
		 * case first because the Transferable is likely to also offer the filenames as
		 * strings, and we want to import the file contents, not their names!
		 */
		if (transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)
				&& wrappedHandler.canImport(jComponent, stringFlavorArray)) {
			try {
				@SuppressWarnings("unchecked")
				List<File> fileList = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);

				// Loop through the files to determine total size.
				int numberOFiles = fileList.size();
				int numberOfBytes = 0;
				for (int i = 0; i < numberOFiles; i++) {
					File file = fileList.get(i);
					numberOfBytes += (int) file.length();
				}

				// There will never be more characters than bytes in the files.
				// To hold file contents:
				char[] text = new char[numberOfBytes];
				// Current position in the text[] array:
				int position = 0;

				// Loop through the files again, reading their content as text.
				Reader reader = null;
				for (int i = 0; i < numberOFiles; i++) {
					File file = fileList.get(i);
					reader = new BufferedReader(new FileReader(file));
					position += reader.read(text, position, (int) file.length());
				}
				reader.close();

				/*
				 * Convert the character array to a string and wrap it in a pre-defined
				 * Transferable class for transferring strings
				 */
				StringSelection selection = new StringSelection(new String(text, 0, position));

				// Ask the wrapped handler to import the string.
				return wrappedHandler.importData(jComponent, selection);
			}
			// If anything goes wrong, just beep to tell the user.
			catch (UnsupportedFlavorException exception) {
				// Audible error:
				jComponent.getToolkit().beep();
				// Return failure code:
				return false;
			} catch (IOException exception) {
				// Audible error:
				jComponent.getToolkit().beep();
				// Return failure code:
				return false;
			}
		}

		// Otherwise let the wrapped class handle this transferable itself.
		return wrappedHandler.importData(jComponent, transferable);
	}

	/*
	 * The following methods just delegate to the wrapped TransferHandler.
	 */
	@Override
	public void exportAsDrag(JComponent jComponent, InputEvent inputEvent, int action) {
		wrappedHandler.exportAsDrag(jComponent, inputEvent, action);
	}

	@Override
	public void exportToClipboard(JComponent jComponent, Clipboard clip, int action) {
		wrappedHandler.exportToClipboard(jComponent, clip, action);
	}

	@Override
	public int getSourceActions(JComponent jComponent) {
		return wrappedHandler.getSourceActions(jComponent);
	}

	@Override
	public Icon getVisualRepresentation(Transferable transferable) {
		// This method is not currently (Java 1.4) used by Swing
		return wrappedHandler.getVisualRepresentation(transferable);
	}

	/**
	 * This class demonstrates the FileTransferHandler by installing it on a
	 * JTextArea component and providing a JFileChooser to drag and cut files.
	 */
	public static class Test {
		public static void main(String[] args) {
			/*
			 * Here's the text area. Note how we wrap our TransferHandler around the default
			 * handler returned by getTransferHandler()
			 */
			JTextArea jTextArea = new JTextArea();
			TransferHandler defaultHandler = jTextArea.getTransferHandler();
			jTextArea.setTransferHandler(new FileTransferHandler(defaultHandler));
			// Here's a JFileChooser, with dragging explicitly enabled.
			JFileChooser filechooser = new JFileChooser();
			filechooser.setDragEnabled(true);

			// Display them both in a window.
			JFrame jFrame = new JFrame("File Transfer Handler Test");
			jFrame.getContentPane().add(new JScrollPane(jTextArea), "Center");
			jFrame.getContentPane().add(filechooser, "South");
			jFrame.setSize(400, 600);
			jFrame.setVisible(true);
			jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
	}
}
