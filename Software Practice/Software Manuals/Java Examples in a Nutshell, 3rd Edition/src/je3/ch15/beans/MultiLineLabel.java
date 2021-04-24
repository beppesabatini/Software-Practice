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
package je3.ch15.beans;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.StringTokenizer;

import javax.accessibility.Accessible;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 458-461. A custom
 * component that displays multiple lines of text, with specified margins and
 * alignment.
 * 
 * <pre>
 * Usage: "The Three|Types of|User Input:"
 * </pre>
 * 
 * Note that lines must be separated with a pipe ('|') for the program to
 * recognize the line breaks. To test this, launch the Demo for the YesNoPanel,
 * click on the Properties menu item, and edit the messageText. For now, the
 * MultiLineLabel is not yet working well enough to be used outside of the
 * context of the {@link YesNoPanel}.
 */
public class MultiLineLabel extends JLabel implements SwingConstants, Accessible {

	private static final long serialVersionUID = 5524499596415374340L;

	/* User-specified properties: */

	/* The label, not broken into lines */
	private String label;
	// Left and right margins:
	private int marginWidth;
	// Top and bottom margins:
	private int marginHeight;
	// The alignment of the text:
	private Alignment alignment;

	/* Computed state values. */
	// The number of lines:
	private int numberLines;
	// The label, broken into lines:
	private String[] lines;
	// How wide each line is:
	private int[] lineWidths;
	// The width of the widest line:
	private int maximumWidth;
	// Total height of the font:
	private int lineHeight;
	// Font height above baseline:
	private int lineAscent;
	// Have the lines been measured?
	private boolean measured = false;

	/* Here are five versions of the constructor. */
	public MultiLineLabel(String label, int marginWidth, int marginHeight, Alignment alignment) {
		// Remember all the properties:
		this.label = label;
		this.marginWidth = marginWidth;
		this.marginHeight = marginHeight;
		this.alignment = alignment;
		// Break the label up into lines:
		newLabel();
	}

	public MultiLineLabel(String label, int marginWidth, int marginHeight) {
		this(label, marginWidth, marginHeight, Alignment.LEFT);
	}

	public MultiLineLabel(String label, Alignment alignment) {
		this(label, 10, 10, alignment);
	}

	public MultiLineLabel(String label) {
		this(label, 10, 10, Alignment.LEFT);
	}

	public MultiLineLabel() {
		this("");
	}

	/*
	 * Methods to set and query the various attributes of the component. Note that
	 * some query methods are inherited from the superclass.
	 */
	public void setLabel(String label) {
		this.label = label;
		// Break the label into lines:
		newLabel();
		// Request a redraw:
		repaint();
		// Note that we need to measure lines:
		measured = false;
		// Tell our containers about this:
		invalidate();
	}

	public void setAlignment(Alignment alignment) {
		this.alignment = alignment;
		repaint();
	}

	public void setMarginWidth(int marginWidth) {
		this.marginWidth = marginWidth;
		repaint();
	}

	public void setMarginHeight(int marginHeight) {
		this.marginHeight = marginHeight;
		repaint();
	}

	// Override this property setter method, because we need to remeasure.
	public void setFont(Font font) {
		// Tell our superclass about the new font:
		super.setFont(font);
		// Request a redraw:
		repaint();
		// Note that we need to remeasure lines:
		measured = false;
		// Tell our containers about new size:
		invalidate();
	}

	/* Property getter methods. */
	public String getLabel() {
		return this.label;
	}

	public Alignment getAlignment() {
		return this.alignment;
	}

	public int getMarginWidth() {
		return this.marginWidth;
	}

	public int getMarginHeight() {
		return this.marginHeight;
	}

	/**
	 * This method is called by a layout manager when it wants to know how big we'd
	 * like to be.
	 */
	public Dimension getPreferredSize() {
		if (measured == false) {
			measure();
		}
		return new Dimension(maximumWidth + 2 * marginWidth, numberLines * lineHeight + 2 * marginHeight);
	}

	/**
	 * This method is called when the layout manager wants to know the bare minimum
	 * amount of space we need to get by.
	 */
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	/**
	 * This method draws the component. Note that it handles the margins and the
	 * alignment, but that it doesn't have to worry about the color or font--the
	 * superclass takes care of setting those, in the Graphics object we've been
	 * passed.
	 */
	public void paintComponent(Graphics graphics) {
		int originX, originY;
		Dimension size = this.getSize();
		if (measured == false) {
			measure();
		}
		originY = lineAscent + (size.height - numberLines * lineHeight) / 2;
		for (int i = 0; i < numberLines; i++, originY += lineHeight) {
			if (this.alignment == Alignment.LEFT) {
				originX = marginWidth;
			} else if (this.alignment == Alignment.CENTER) {
				originX = (size.width - lineWidths[i]) / 2;
			} else {
				originX = size.width - marginWidth - lineWidths[i];
			}
			graphics.drawString(lines[i], originX, originY);
		}
	}

	/**
	 * This internal method breaks a specified label up into an array of lines. It
	 * uses the StringTokenizer utility class.
	 */
	private synchronized void newLabel() {
		StringTokenizer stringTokenizer = new StringTokenizer(label, "|");
		this.numberLines = stringTokenizer.countTokens();
		this.lines = new String[numberLines];
		this.lineWidths = new int[numberLines];
		for (int i = 0; i < numberLines; i++) {
			this.lines[i] = stringTokenizer.nextToken();
		}
	}

	/**
	 * This internal method figures out how the font is, and how wide each line of
	 * the label is, and how wide the widest line is.
	 */
	private synchronized void measure() {
		FontMetrics fontMetrics = this.getFontMetrics(this.getFont());
		this.lineHeight = fontMetrics.getHeight();
		this.lineAscent = fontMetrics.getAscent();
		this.maximumWidth = 0;
		for (int i = 0; i < numberLines; i++) {
			lineWidths[i] = fontMetrics.stringWidth(lines[i]);
			if (lineWidths[i] > maximumWidth) {
				maximumWidth = lineWidths[i];
			}
		}
		this.measured = true;
	}

	public static class Demo {
		public static void main(String[] args) {
			YesNoPanel.Demo.main(args);
			System.out.print("Demo for MultiLineLabel as used in the YesNoPanel. ");
		}
	}
}
