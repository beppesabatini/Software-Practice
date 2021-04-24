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
package je3.ch12.graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 405-409. This class
 * displays one or more GraphicsExample objects in a Swing JFrame and a
 * JTabbedPane.
 */
public class GraphicsExampleFrame extends JFrame {

	private static final long serialVersionUID = 4360172745958101714L;

	static final String CURRENT_PACKAGE = "je3.ch12.graphics.";

	public GraphicsExampleFrame(final GraphicsExample[] examples) {
		super("GraphicsExampleFrame");

		// Set up the frame:
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		// ...and, the tabbed pane:
		final JTabbedPane jTabbedPane = new JTabbedPane();
		contentPane.add(jTabbedPane, BorderLayout.CENTER);

		/*
		 * Add a menu bar.
		 */
		// Create the menu bar:
		JMenuBar jMenuBar = new JMenuBar();
		// Add it to the frame:
		this.setJMenuBar(jMenuBar);
		// Create a File menu:
		JMenu fileMenu = new JMenu("File");
		// Add to the menu bar:
		jMenuBar.add(fileMenu);
		// Create a Print menu item:
		JMenuItem printMenuItem = new JMenuItem("Print");
		// Add it to the menu:
		fileMenu.add(printMenuItem);
		// Create a Quit item:
		JMenuItem quitMenuItem = new JMenuItem("Quit");
		// Add it to the menu:
		fileMenu.add(quitMenuItem);

		// Tell the Print menu item what to do when selected:
		printMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				/*
				 * Get the currently displayed example, and call the print method (defined
				 * below).
				 */
				print(examples[jTabbedPane.getSelectedIndex()]);
			}
		});

		// Tell the Quit menu item what to do when selected.
		quitMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		// In addition to the Quit menu item, also handle window close events.
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		// Insert each of the example objects into the tabbed pane.
		for (int i = 0; i < examples.length; i++) {
			GraphicsExample e = examples[i];
			jTabbedPane.addTab(e.getName(), new GraphicsExamplePane(e));
		}
	}

	/**
	 * This inner class is a custom Swing component that displays a GraphicsExample
	 * object.
	 */
	public class GraphicsExamplePane extends JComponent {

		private static final long serialVersionUID = -6962237994341506519L;

		// The example to display:
		GraphicsExample example;
		// How much space it requires:
		Dimension size;

		public GraphicsExamplePane(GraphicsExample graphicsExample) {
			this.example = graphicsExample;
			size = new Dimension(graphicsExample.getWidth(), graphicsExample.getHeight());
		}

		/** Draw the component and the example it contains. */
		@Override
		public void paintComponent(Graphics graphics) {
			// Set the background to white:
			graphics.setColor(Color.white);
			graphics.fillRect(0, 0, size.width, size.height);
			// Set a default drawing color:
			graphics.setColor(Color.black);
			// Ask the example to draw itself:
			example.draw((Graphics2D) graphics, this);
		}

		// These methods specify how big the component must be.
		@Override
		public Dimension getPreferredSize() {
			return size;
		}

		@Override
		public Dimension getMinimumSize() {
			return size;
		}
	}

	/** This method is invoked by the Print menu item. */
	public void print(final GraphicsExample example) {
		// Start off by getting a printer job to do the printing.
		PrinterJob job = PrinterJob.getPrinterJob();
		/*
		 * Wrap the example in a Printable object (defined below) and tell the
		 * PrinterJob that we want to print it.
		 */
		job.setPrintable(new PrintableExample(example));

		// Display the print dialog to the user
		if (job.printDialog()) {
			// If they didn't cancel it, then tell the job to start printing
			try {
				job.print();
			} catch (PrinterException printerException) {
				System.out.println("Couldn't print: " + printerException.getMessage());
			}
		}
	}

	/**
	 * This inner class implements the Printable interface in order to print a
	 * GraphicsExample object.
	 */
	class PrintableExample implements Printable {
		// The example to print:
		GraphicsExample example;

		// The constructor. It only remembers the example.
		public PrintableExample(GraphicsExample graphicsExample) {
			this.example = graphicsExample;
		}

		/**
		 * This method is called by the PrinterJob to print the example.
		 */
		@Override
		public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
			// Tell the PrinterJob that there is only one page.
			if (pageIndex != 0) {
				return NO_SUCH_PAGE;
			}

			/*
			 * The PrinterJob supplies us a Graphics object to draw with. Anything drawn
			 * with this object will be sent to the printer. The Graphics object can safely
			 * be cast to a Graphics2D object.
			 */
			Graphics2D graphics2D = (Graphics2D) graphics;

			// Translate to skip the left and top margins.
			graphics2D.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

			/*
			 * Figure out how big the printable area is, and how big the example is.
			 */
			double pageWidth = pageFormat.getImageableWidth();
			double pageHeight = pageFormat.getImageableHeight();
			double exampleWidth = example.getWidth();
			double exampleHeight = example.getHeight();

			// Scale the example if needed.
			double scaleX = 1.0, scaleY = 1.0;
			if (exampleWidth > pageWidth) {
				scaleX = pageWidth / exampleWidth;
			}
			if (exampleHeight > pageHeight) {
				scaleY = pageHeight / exampleHeight;
			}
			double scaleFactor = Math.min(scaleX, scaleY);
			if (scaleFactor != 1) {
				graphics2D.scale(scaleFactor, scaleFactor);
			}

			/*
			 * Finally, call the draw() method of the example, passing in the Graphics2D
			 * object for the printer
			 */
			example.draw(graphics2D, GraphicsExampleFrame.this);

			// Tell the PrinterJob that we successfully printed the page.
			return PAGE_EXISTS;
		}
	}

	/**
	 * The main program. Use Java reflection to load and instantiate the specified
	 * GraphicsExample classes, then create a GraphicsExampleFrame to display them.
	 */
	public static void main(String[] args) {
		GraphicsExample[] examples = new GraphicsExample[args.length];

		// Loop through the command line arguments.
		for (int i = 0; i < args.length; i++) {
			// The class name of the requested example:
			String classname = args[i];

			// If no package is specified, assume it is in this package.
			if (classname.indexOf('.') == -1) {
				classname = CURRENT_PACKAGE + args[i];
			}
			// Try to instantiate the named GraphicsExample class.
			try {
				Class<?> exampleClass = Class.forName(classname);
				examples[i] = (GraphicsExample) exampleClass.getDeclaredConstructor().newInstance();
			} catch (ClassNotFoundException e) {
				// An unknown class:
				System.err.println("Couldn't find example: " + classname);
				System.exit(1);
			} catch (ClassCastException e) {
				// The wrong type of class:
				System.err.println("Class " + classname + " is not a GraphicsExample");
				System.exit(1);
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				String message = e.getLocalizedMessage();
				System.err.println(message);
				System.exit(1);
			} catch (Exception e) {
				// catch InstantiationException, IllegalAccessException
				// The class doesn't have a public constructor:
				System.err.println("Couldn't instantiate example: " + classname);
				e.printStackTrace();
				System.exit(1);
			}
		}

		// Now create a window in which to display the examples, and make it visible.
		GraphicsExampleFrame graphicsExampleFrame = new GraphicsExampleFrame(examples);
		graphicsExampleFrame.pack();
		graphicsExampleFrame.setVisible(true);
	}
}
