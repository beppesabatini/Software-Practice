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

import java.awt.Button;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.PrintJob;
import java.awt.Toolkit;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 423-431. A character
 * output stream that sends output to a printer. This has been heavily
 * refactored from the manual distribution, and still doesn't work too well.
 */
public class HardcopyWriter extends Writer {
	/*
	 * These are the instance variables for the class.
	 */
	// The PrintJob object in use:
	private PrintJob job;
	// Graphics object for the printed document:
	private Graphics formattedOutputDocument;
	// The name of the print job:
	private String jobName;
	// Point size of the font:
	private int fontSize;
	// Body font and header font:
	private Font bodyFont, headerFont;
	// Metrics for the body font:
	private FontMetrics bodyFontMetrics;
	// Metrics for the header font:
	private FontMetrics headerFontMetrics;
	// Upper-left corner inside margin:
	private int originX, originY;
	// Size (in pixels) inside margins:
	private int width, height;
	// Number of characters per line:
	private int charactersPerLine;
	// Number of lines per page:
	private int linesPerPage;
	// Current column and line position:
	private int characterPosition = 0, lineNumber = 0;
	// Current page number:
	private int currentPageNumber = 0;

	// A field to save state between invocations of the write() method.
	private boolean previousCharacterWasReturn = false;

	// A static variable that holds user preferences between print jobs.
	private static Properties printProperties = new Properties();

	/**
	 * This is the constructor this class and takes several arguments:
	 * 
	 * @param frame        Required for all printing in Java.
	 * @param jobname      Appears left justified at the top of each printed page.
	 * @param fontSize     Specified in points, as on-screen font sizes are.
	 * @param leftMargin   Specified in inches (or fractions of inches).
	 * @param rightMargin
	 * @param topMargin
	 * @param bottomMargin
	 * @throws HardcopyWriter.PrintCanceledException
	 */
	public HardcopyWriter(Frame frame, String jobName, int fontSize, double leftMargin, double rightMargin,
			double topMargin, double bottomMargin) throws HardcopyWriter.PrintCanceledException {
		/*
		 * Get the PrintJob object with which we'll do all the printing. The call is
		 * synchronized on the static printProperties object, which means that only one
		 * print dialog can be popped up at a time. If the user clicks Cancel in the
		 * print dialog, throw an exception.
		 */
		// Get Toolkit from Frame:
		Toolkit toolkit = frame.getToolkit();
		synchronized (printProperties) {
			this.job = toolkit.getPrintJob(frame, jobName, printProperties);
		}
		if (this.job == null) {
			throw new PrintCanceledException("User cancelled the print request");
		}

		this.fontSize = fontSize;
		// Size of the page (in pixels):
		Dimension pageSize = job.getPageDimension();
		// Page resolution in dots per inch:
		int pageDPI = job.getPageResolution();

		/*
		 * Compute coordinates of the upper-left corner of the page, that is, the
		 * coordinates of (leftMargin, topMargin). Also, compute the width and height
		 * inside of the margins.
		 */
		this.originX = (int) (leftMargin * pageDPI);
		this.originY = (int) (topMargin * pageDPI);
		this.width = pageSize.width - (int) ((leftMargin + rightMargin) * pageDPI);
		this.height = pageSize.height - (int) ((topMargin + bottomMargin) * pageDPI);

		// Get body font and font size.
		this.bodyFont = new Font("Monospaced", Font.PLAIN, this.fontSize);
		this.bodyFontMetrics = frame.getFontMetrics(bodyFont);
		int lineHeight = bodyFontMetrics.getHeight();
		// Assumes a monospaced font!
		int characterWidth = bodyFontMetrics.charWidth('0');

		// Now compute columns and lines will fit inside the margins.
		this.charactersPerLine = width / characterWidth;
		this.linesPerPage = height / lineHeight;

		// Get header font information.
		this.headerFont = new Font("SansSerif", Font.ITALIC, fontSize);
		// ...and, compute the baseline of page header: 1/8" above the top margin.
		this.headerFontMetrics = frame.getFontMetrics(headerFont);

		// Save the job name.
		this.jobName = jobName;
		// Save the font size.
		this.fontSize = fontSize;
	}

	/**
	 * This is the write() method of the stream. All Writer subclasses implement
	 * this. All other versions of write() are variants of this one.
	 */
	@Override
	public void write(char[] inputBuffer, int index, int length) {
		// For thread safety:
		synchronized (this.lock) {
			// Loop through all the characters passed to us.
			for (int i = index; i < index + length; i++) {
				// If we haven't begun a page (or a new page), do that now.
				if (this.formattedOutputDocument == null) {
					newpage();
				}

				/*
				 * If the character is a line terminator, then begin new line, unless it is a \n
				 * immediately after a \r.
				 */
				if (inputBuffer[i] == '\n') {
					if (previousCharacterWasReturn == false) {
						newline();
					}
					continue;
				}
				if (inputBuffer[i] == '\r') {
					newline();
					previousCharacterWasReturn = true;
					continue;
				} else {
					previousCharacterWasReturn = false;
				}

				// If the current character is some other non-printing character, ignore it.
				if (ignoreCharacter(inputBuffer[i])) {
					continue;
				}

				// If no more characters will fit on the line, start a new line.
				if (this.characterPosition >= this.charactersPerLine) {
					if (inputBuffer[i] != ' ') {
					}
					newline();
					// Also start a new page, if necessary:
					if (this.formattedOutputDocument == null) {
						newpage();
					}

				}

				/**
				 * Now print the character:
				 * <p>
				 * If it is a space, skip one space, without output.
				 * <p>
				 * If it is a tab, skip the necessary number of spaces.
				 * <p>
				 * Otherwise, print the character.
				 * <p>
				 * It is inefficient to draw only one character at a time, but because our
				 * FontMetrics don't match up exactly to what the printer uses, we need to
				 * position each character individually.
				 */
				if (Character.isSpaceChar(inputBuffer[i])) {
					this.characterPosition++;
				} else if (inputBuffer[i] == '\t') {
					this.characterPosition += 8 - (characterPosition % 8);
				} else {
					int lineHeight = bodyFontMetrics.getHeight();
					int lineAscent = bodyFontMetrics.getAscent();
					int characterWidth = bodyFontMetrics.charWidth('0');
					int targetX = this.originX + this.characterPosition * characterWidth;
					int targetY = this.originY + (lineNumber * lineHeight) + lineAscent;
					formattedOutputDocument.drawChars(inputBuffer, i, 1, targetX, targetY);
					this.characterPosition++;
				}
			}
		}
	}

	private boolean ignoreCharacter(char currentCharacter) {
		if (currentCharacter == '\t') {
			return (false);
		}

		if (Character.isSpaceChar(currentCharacter) == true) {
			return (false);
		}

		if (Character.isWhitespace(currentCharacter) == true) {
			return (true);
		}
		return (false);
	}

	/**
	 * This is the flush() method that all Writer subclasses must implement. There
	 * is no way to flush a PrintJob without prematurely printing the page, so we
	 * don't do anything.
	 */
	@Override
	public void flush() {
		/* do nothing */ }

	/**
	 * This is the close() method that all Writer subclasses must implement. Print
	 * the pending page (if any) and terminate the PrintJob.
	 */
	@Override
	public void close() {
		synchronized (this.lock) {
			// Send page to the printer:
			if (formattedOutputDocument != null) {
				formattedOutputDocument.dispose();
			}
			// Terminate the job:
			job.end();
		}
	}

	/**
	 * Set the font style. The argument should be one of the font style constants
	 * defined by the java.awt.Font class. All subsequent output will be in that
	 * style. This method relies on all styles of the Monospaced font having the
	 * same metrics.
	 */
	public void setFontStyle(int style) {
		synchronized (this.lock) {
			// Try to set a new font, but restore the current one if it fails.
			Font current = bodyFont;
			try {
				bodyFont = new Font("Monospaced", style, fontSize);
			} catch (Exception e) {
				bodyFont = current;
			}
			/*
			 * If a page is pending, set the new font. Otherwise newpage() will do so.
			 */
			if (formattedOutputDocument != null) {
				formattedOutputDocument.setFont(bodyFont);
			}
		}
	}

	/** End the current page. Subsequent output will be on a new page. */
	public void pageBreak() {
		synchronized (this.lock) {
			newpage();
		}
	}

	/** Return the number of columns of characters that fit on the page */
	public int getCharactersPerLine() {
		return this.charactersPerLine;
	}

	/** Return the number of lines that fit on a page */
	public int getLinesPerPage() {
		return this.linesPerPage;
	}

	/** This internal method begins a new line. */
	protected void newline() {
		// Reset character position to 0:
		this.characterPosition = 0;
		// Increment line number:
		this.lineNumber++;
		// If we've reached the end of page:
		if (this.lineNumber >= this.linesPerPage) {
			// Send the page to printer:
			formattedOutputDocument.dispose();
			// ...but don't start a new page yet.
			formattedOutputDocument = null;
		}
	}

	/** This internal method begins a new page and prints the header. */
	protected void newpage() {
		// Begin the new page:
		this.formattedOutputDocument = job.getGraphics();
		// Reset line and char number:
		this.lineNumber = 0;
		this.characterPosition = 0;
		// Increment page number:
		this.currentPageNumber++;
		// Set the header font:
		this.formattedOutputDocument.setFont(headerFont);

		// Page resolution in dots per inch:
		int pageDPI = this.job.getPageResolution();
		int oneEighthInch = (int) (0.125 * pageDPI);
		// The font height is leading + ascent + descent.
		int fontHeight = headerFontMetrics.getHeight();
		// Baseline of the page header:
		int headerBaselineY = originY - oneEighthInch - fontHeight + headerFontMetrics.getAscent();

		// Print the job name, left-justified:
		this.formattedOutputDocument.drawString(jobName, originX, headerBaselineY);

		// Print the page number, centered:
		String pageNumberString = "- " + currentPageNumber + " -";
		int pageNumberStringWidth = headerFontMetrics.stringWidth(pageNumberString);
		int centeredPageNumberStringX = originX + (this.width - pageNumberStringWidth) / 2;
		formattedOutputDocument.drawString(pageNumberString, centeredPageNumberStringX, headerBaselineY);
		// Compute the date/time string to display in the page header, right-justified.
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT);
		dateFormat.setTimeZone(TimeZone.getDefault());
		String time = dateFormat.format(new Date());
		int timeStringWidth = headerFontMetrics.stringWidth(time);
		this.formattedOutputDocument.drawString(time, originX + width - timeStringWidth, headerBaselineY);

		// Draw a line beneath the header:
		int headerUnderlineY = headerBaselineY + headerFontMetrics.getDescent() + 1;
		this.formattedOutputDocument.drawLine(originX, headerUnderlineY, originX + width, headerUnderlineY);

		// Set the basic monospaced font for the rest of the page.
		this.formattedOutputDocument.setFont(bodyFont);
	}

	/**
	 * This is the exception class that the HardcopyWriter constructor throws when
	 * the user clicks "Cancel" in the print dialog box.
	 */
	public static class PrintCanceledException extends Exception {

		private static final long serialVersionUID = -6046240639401397047L;

		public PrintCanceledException(String message) {
			super(message);
		}
	}

	/**
	 * A program that prints the specified file using HardcopyWriter.
	 */
	public static class PrintFile {
		public static void main(String[] args) {
			try {
				if (args.length != 1) {
					throw new IllegalArgumentException("Sorry, wrong number of arguments.");
				}
				FileReader fileReader = new FileReader(args[0]);
				HardcopyWriter hardcopyWriter = null;
				Frame printFileFrame = new Frame("PrintFile: " + args[0]);
				printFileFrame.setSize(200, 50);
				printFileFrame.setVisible(true);
				try {
					hardcopyWriter = new HardcopyWriter(printFileFrame, args[0], 10, .5, .5, .5, .5);
				} catch (HardcopyWriter.PrintCanceledException e) {
					System.exit(0);
				}
				printFileFrame.setVisible(false);
				char[] buffer = new char[4096];
				int numberCharacters;
				while ((numberCharacters = fileReader.read(buffer)) != -1) {
					hardcopyWriter.write(buffer, 0, numberCharacters);
				}
				fileReader.close();
				hardcopyWriter.close();
			} catch (Exception exception) {
				System.err.println(exception);
				System.err.println("Usage: " + "java HardcopyWriter$PrintFile <filename>");
				System.exit(1);
			}
			System.exit(0);
		}
	}

	/**
	 * A program that prints a demo page using HardcopyWriter.
	 **/
	public static class Demo extends Frame implements ActionListener {

		private static final long serialVersionUID = -4867788108840540991L;

		/** The main method of the program. Create a test window. */
		public static void main(String[] args) {
			Frame demoFrame = new Demo();
			demoFrame.setVisible(true);
		}

		// Buttons used in this program:
		protected Button printButton;
		protected Button quitButton;

		/** The constructor for the test program's window. */
		public Demo() {
			// Call frame constructor:
			super("HardcopyWriter Test");
			// Add a panel to the frame:
			Panel panel = new Panel();
			// Center it:
			this.add(panel, "Center");
			// Set a default font:
			panel.setFont(new Font("SansSerif", Font.BOLD, 18));
			// Create a Print button:
			printButton = new Button("Print Test Page");
			// Create a Quit button:
			quitButton = new Button("Quit");
			// Specify that we'll handle button presses:
			printButton.addActionListener(this);
			quitButton.addActionListener(this);
			// Add the buttons to panel:
			panel.add(printButton);
			panel.add(quitButton);
			// Set the frame size:
			this.pack();
		}

		/** Handle the button presses */
		public void actionPerformed(ActionEvent actionEvent) {
			Object actionEventSource = actionEvent.getSource();
			if (actionEventSource == quitButton) {
				System.exit(0);
			} else if (actionEventSource == printButton) {
				printDemoPage();
			}
		}

		/** Print the demo page. */
		public void printDemoPage() {
			// Create a HardcopyWriter, using a 10 point font and 3/4" margins.
			HardcopyWriter hardcopyWriter;
			try {
				hardcopyWriter = new HardcopyWriter(this, "Demo Page", 10, .75, .75, .75, .75);
			} catch (HardcopyWriter.PrintCanceledException e) {
				return;
			}

			// Send output to it through a PrintWriter stream.
			PrintWriter printWriter = new PrintWriter(hardcopyWriter);

			// Figure out the size of the page.
			int linesPerPage = hardcopyWriter.getLinesPerPage();
			int charactersPerLine = hardcopyWriter.getCharactersPerLine();

			// Mark the upper left and upper-right corners.
			// Upper-left corner:
			printWriter.print("+");
			// Space over to the right:
			for (int i = 0; i < charactersPerLine - 2; i++) {
				printWriter.print(" ");
			}
			// Upper-right corner:
			printWriter.print("+");

			// Display a title:
			hardcopyWriter.setFontStyle(Font.BOLD + Font.ITALIC);

			String demoTitle = "Hardcopy Writer Demo Page";
			StringBuilder centeredTitlePadding = new StringBuilder();
			int paddingWidth = (charactersPerLine - demoTitle.length()) / 2;
			for (int i = 0; i < paddingWidth; i++) {
				centeredTitlePadding.append(" ");
			}
			String centeredTitle = "\n" + centeredTitlePadding + demoTitle + "\n";
			printWriter.println(centeredTitle);

			// Demonstrate font styles:
			hardcopyWriter.setFontStyle(Font.BOLD);
			printWriter.println("Font Styles:");
			int[] styles = { Font.PLAIN, Font.BOLD, Font.ITALIC, Font.ITALIC + Font.BOLD };
			for (int i = 0; i < styles.length; i++) {
				hardcopyWriter.setFontStyle(styles[i]);
				String characters = "";
				characters += "ABCDEFGHIJKLMNOPQRSTUVWXYZ\n";
				characters += "abcdefghijklmnopqrstuvwxyz\n";
				characters += "1234567890!@#$%^&*()[]{}<>,.?:;+-=/\\`'\"_~|";
				printWriter.println(characters);
				printWriter.println();
			}

			// Demonstrate tab stops:
			hardcopyWriter.setFontStyle(Font.BOLD);
			printWriter.println("Tab Stops:");
			hardcopyWriter.setFontStyle(Font.PLAIN);
			String characters = "";
			characters += "          1         2         3         4         5\n";
			characters += "012345678901234567890123456789012345678901234567890\n";
			characters += "^\t^\t^\t^\t^\t^\t^";
			printWriter.println(characters);
			printWriter.println("\n");

			// Output some information about page dimensions and resolution.
			Dimension pageSize = hardcopyWriter.job.getPageDimension();
			int pageDPI = hardcopyWriter.job.getPageResolution();

			hardcopyWriter.setFontStyle(Font.BOLD);
			printWriter.println("Dimensions:");
			hardcopyWriter.setFontStyle(Font.PLAIN);
			String dimensions = "";
			dimensions += "\t";
			dimensions += "Resolution: " + pageDPI + " dots per inch\n";
			dimensions += "\t";
			dimensions += "Page width (pixels): " + pageSize.width + "\n";
			dimensions += "\t";
			dimensions += "Page height (pixels): " + pageSize.height + "\n";
			dimensions += "\t";
			dimensions += "Width inside margins (pixels): " + hardcopyWriter.width + "\n";
			dimensions += "\t";
			dimensions += "Height inside margins (pixels): " + hardcopyWriter.height + "\n";
			dimensions += "\t";
			dimensions += "Characters per line: " + charactersPerLine + "\n";
			dimensions += "\t";
			dimensions += "Lines per page: " + linesPerPage;
			printWriter.println(dimensions);

			// Pad with new lines down to the bottom of the page.
			for (int i = 0; i < linesPerPage - 34; i++) {
				printWriter.println();
			}

			// ...and, mark the lower left and lower right:
			// Lower-left:
			printWriter.print("+");
			// Space over to the right:
			for (int i = 0; i < charactersPerLine - 2; i++) {
				printWriter.print(" ");
			}
			// Lower-right:
			printWriter.print("+");

			// Close the output stream, forcing the page to be printed.
			printWriter.close();
		}
	}
}
