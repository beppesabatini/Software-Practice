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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.JOptionPane;

import je3.ch11.gui.JPanelDemoable;
import utils.LearningJava3Utils;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 216-219. This class
 * extends JuliaSet1 and overrides the print() method to demonstrate the Java
 * 1.2 printing API. In this installation, users can test this class with the
 * JuliaSet run configuration. The printing functionality is too old, and no
 * longer works, but the fractals still look nice.
 */
public class JuliaSet2 extends JuliaSet1 {

	private static final long serialVersionUID = 2272813765868888344L;

	public JuliaSet2() {
		// Display a different set by default:
		this(.4, .4);
	}

	public JuliaSet2(double centerX, double centerY) {
		super(centerX, centerY);
	}

	/**
	 * This method demonstrates the Java 1.2 printing API. Test this class using the
	 * ShowBean program, as below. Substitute your own package names if they are
	 * different. In the current installation there is also a JuliaSet run
	 * configuration.
	 * 
	 * <pre>
	 je3.ch11.gui.ShowBean -width=400 -height=400 je3.ch13.print.JuliaSet2
	 * </pre>
	 */
	public void print() {
		/*
		 * Printing is not working yet for JuliaSet1 and JuliaSet2. Probably the old
		 * Java 1 and Java 2 classes are just too old.
		 */
		LearningJava3Utils.confirmContinueWithDisfunctional();

		// Java 1.1 used java.awt.PrintJob.
		// In Java 1.2 we use java.awt.print.PrinterJob.
		PrinterJob job = PrinterJob.getPrinterJob();

		// Alter the default page settings to request landscape mode.
		PageFormat page = job.defaultPage();
		// Landscape by default:
		page.setOrientation(PageFormat.LANDSCAPE);

		/*
		 * Tell the PrinterJob what Printable object we want to print.
		 * PrintableComponent is defined as an inner class below.
		 */
		String title = "Julia set for c={" + centerX + "," + centerY + "}";
		Printable printable = new PrintableComponent(this, title);
		job.setPrintable(printable, page);

		/*
		 * Call the printDialog() method to give the user a chance to alter the printing
		 * attributes or to cancel the printing request.
		 */
		if (job.printDialog()) {
			/*
			 * If we get here, then the user did not cancel the print job. So start
			 * printing, while displaying a dialog for errors.
			 */
			try {
				job.print();
			} catch (PrinterException e) {
				JOptionPane.showMessageDialog(this, e.toString(), "PrinterException", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	// This inner class implements the Printable interface for an AWT component.
	public static class PrintableComponent implements Printable {
		Component component;
		String title;

		public PrintableComponent(Component component, String title) {
			this.component = component;
			this.title = title;
		}

		/*
		 * This method should print the specified page number to the specified Graphics
		 * object, abiding by the specified page format. The printing system will call
		 * this method repeatedly to print all pages of the print job. If the page
		 * number is greater than the last page, it should return NO_SUCH_PAGE, to
		 * indicate that it is done. The printing system may call this method multiple
		 * times per page.
		 */
		public int print(Graphics graphics, PageFormat format, int pageNumber) {
			// This implementation is always a single page.
			if (pageNumber > 0) {
				return Printable.NO_SUCH_PAGE;
			}

			/*
			 * The Java 1.2 printing API passes us a Graphics object, but we can always cast
			 * it to a Graphics2D object.
			 */
			Graphics2D graphics2D = (Graphics2D) graphics;

			// Translate to accommodate the requested top and left margins.
			graphics2D.translate(format.getImageableX(), format.getImageableY());

			/*
			 * Figure out how big the drawing is, and how big the page (excluding margins)
			 * is.
			 */
			// Component size:
			Dimension size = component.getSize();
			double pageWidth = format.getImageableWidth();
			double pageHeight = format.getImageableHeight();

			// If the component is too wide or tall for the page, scale it down.
			if (size.width > pageWidth) {
				// How much to scale:
				double factor = pageWidth / size.width;
				// Adjust the coordinate system:
				graphics2D.scale(factor, factor);
				// Adjust the page size up:
				pageWidth /= factor;
				pageHeight /= factor;
			}
			// Do the same thing for height:
			if (size.height > pageHeight) {
				double factor = pageHeight / size.height;
				graphics2D.scale(factor, factor);
				pageWidth /= factor;
				pageHeight /= factor;
			}

			/*
			 * Now we know the component will fit on the page. Center it by translating as
			 * necessary.
			 */
			graphics2D.translate((pageWidth - size.width) / 2, (pageHeight - size.height) / 2);

			// Draw a line around the outside of the drawing area and label it.
			graphics2D.drawRect(-1, -1, size.width + 2, size.height + 2);
			graphics2D.drawString(title, 0, -15);

			/*
			 * Set a clipping region so the component can't draw outside of its own bounds.
			 */
			graphics2D.setClip(0, 0, size.width, size.height);

			/*
			 * Finally, print the component by calling its paint() method. This prints the
			 * background, border, and children as well. For swing components, if you don't
			 * want the background, border, and children, then call printComponent()
			 * instead.
			 */
			component.paint(graphics);

			// Tell the PrinterJob that the page number was valid.
			return Printable.PAGE_EXISTS;
		}
	}

	public static class Demo {
		public static void main(String[] args) {
			launchBeansInShowBean(JPanelDemoable.juliaSetBeans, 421, 490);
			System.out.println("Demo for three Julia Sets. Only JuliaSet3 actually prints.");
		}
	}
}
