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
package je3.ch13.print;

import java.awt.print.Printable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Chromaticity;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.SimpleDoc;
import javax.print.StreamPrintService;
import javax.print.StreamPrintServiceFactory;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import je3.ch11.gui.JPanelDemoable;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 420-422. This class
 * extends JuliaSet2, and its print() and save() methods demonstrate the Java
 * 1.4 printing API. The three different JuliaSet classes use three different
 * versions of the Java Printing API (1.1, 1.2, and 1.4), and this is the only
 * one that still prints.
 */
public class JuliaSet3 extends JuliaSet2 {

	private static final long serialVersionUID = -1005983840870116478L;

	public JuliaSet3() {
		super(-.7, -.25);
	}

	/*
	 * This method overrides JuliaSet2.print(), and demonstrates the
	 * javax.print.printing API.
	 */
	@Override
	public void print() {
		// Get a list of all printers that can handle Printable objects.
		DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
		PrintService[] services = PrintServiceLookup.lookupPrintServices(flavor, null);

		// Set some define printing attributes
		PrintRequestAttributeSet printAttributes = new HashPrintRequestAttributeSet();
		// Landscape mode (sideways):
		printAttributes.add(OrientationRequested.LANDSCAPE);
		// Print in mono. Actually it still prints in color for some reason.
		printAttributes.add(Chromaticity.MONOCHROME);

		/*
		 * Display a dialog that allows the user to select one of the available printers
		 * and to edit the default attributes
		 */
		PrintService service = ServiceUI.printDialog(null, 100, 100, services, null, null, printAttributes);

		// If the user canceled, don't do anything.
		if (service == null) {
			return;
		}

		// Now call a method defined below to finish the printing.
		printToService(service, printAttributes);
	}

	/*
	 * This method is like print() above, but prints to a PostScript file instead of
	 * printing to a printer.
	 */
	public void save() throws IOException {
		// Find a factory object for printing Printable objects to PostScript.
		DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
		String format = "application/postscript";
		StreamPrintServiceFactory factory = null;
		factory = StreamPrintServiceFactory.lookupStreamPrintServiceFactories(flavor, format)[0];

		// Ask the user to select a file and open the selected file.
		JFileChooser chooser = new JFileChooser();
		if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		File f = chooser.getSelectedFile();
		FileOutputStream out = new FileOutputStream(f);

		// Obtain a PrintService that prints to that file.
		StreamPrintService service = factory.getPrintService(out);

		// Do the printing with the method below.
		printToService(service, null);

		// ...and, close the output file.
		out.close();
	}

	/*
	 * Print the Julia set to the specified PrintService, using the specified
	 * attributes.
	 */
	public void printToService(PrintService service, PrintRequestAttributeSet printAttributes) {
		/*
		 * Wrap ourselves in the PrintableComponent class defined by JuliaSet2. The
		 * title below is displayed in the printed version but not in the pop-up GUI
		 * version.
		 * 
		 */
		String title = "Julia set for c={" + centerX + "," + centerY + "}";
		Printable printable = new PrintableComponent(this, title);

		// Now create a Doc that encapsulates the Printable object and its type.
		DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
		Doc doc = new SimpleDoc(printable, flavor, null);

		// Java 1.1 uses PrintJob.
		// Java 1.2 uses PrinterJob.
		// Java 1.4 uses DocPrintJob. Create one from the service.
		DocPrintJob job = service.createPrintJob();

		// Set up a dialog box to monitor printing status.
		final JOptionPane pane = new JOptionPane("Printing...", JOptionPane.PLAIN_MESSAGE);
		JDialog dialog = pane.createDialog(this, "Print Status");
		// This listener object updates the dialog as the status changes.
		job.addPrintJobListener(new PrintJobAdapter() {
			@Override
			public void printJobCompleted(PrintJobEvent e) {
				pane.setMessage("Printing complete.");
			}

			@Override
			public void printDataTransferCompleted(PrintJobEvent e) {
				pane.setMessage("Document transfered to printer.");
			}

			@Override
			public void printJobRequiresAttention(PrintJobEvent e) {
				pane.setMessage("Check printer: out of paper?");
			}

			@Override
			public void printJobFailed(PrintJobEvent e) {
				pane.setMessage("Print job failed");
			}
		});

		// Show the dialog, non-modal.
		dialog.setModal(false);
		dialog.setVisible(true);

		// Now print the Doc to the DocPrintJob.
		try {
			job.print(doc, printAttributes);
		} catch (PrintException e) {
			// Display any errors to the dialog box.
			pane.setMessage(e.toString());
		}
	}

	public static class Demo {
		public static void main(String[] args) {
			launchBeansInShowBean(JPanelDemoable.juliaSetBeans, 421, 490);
			System.out.println("Demo for three Julia Sets. Only JuliaSet3 actually prints.");
		}
	}
}
