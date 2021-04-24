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

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 272-274. This
 * LayoutManager arranges the components into a column. Components are always
 * given their preferred size.
 * 
 * <pre>
 * When you create a ColumnLayout, you may specify four values:
 *   margin_height -- how much space to leave on top and bottom
 *   margin_width  -- how much space to leave on left and right
 *   spacing       -- how much vertical space to leave between items
 *   
 *   alignment: the horizontal position of the components:
 *   
 *      ColumnLayout.LEFT   -- left-justify the components
 *      ColumnLayout.CENTER -- horizontally center the components
 *      ColumnLayout.RIGHT  -- right-justify the components
 * </pre>
 * 
 * A developer never calls the methods of a ColumnLayout object. Just create one
 * and make it the layout manager for your container, by passing it to the
 * addLayout() method of the Container object.
 */
public class ColumnLayout extends JPanelDemoable implements LayoutManager2 {

	private static final long serialVersionUID = -7883845324903860024L;

	protected int margin_height;
	protected int margin_width;
	protected int spacing;
	protected int alignment;

	// Constants for the alignment argument to the constructor.
	public static final int LEFT = 0;
	public static final int CENTER = 1;
	public static final int RIGHT = 2;

	/**
	 * The constructor. See the JavaDoc above for the meanings of these arguments.
	 */
	public ColumnLayout(int margin_height, int margin_width, int spacing, int alignment) {
		this.margin_height = margin_height;
		this.margin_width = margin_width;
		this.spacing = spacing;
		this.alignment = alignment;
	}

	/**
	 * A default constructor that creates a ColumnLayout, using 5-pixel margin width
	 * and height, 5-pixel spacing, and left alignment.
	 */
	public ColumnLayout() {
		this(5, 5, 5, LEFT);
	}

	/**
	 * The method that actually performs the layout. This is called by the
	 * Container.
	 */
	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();
		Dimension parent_size = parent.getSize();
		Component child;
		int numberChildren = parent.getComponentCount();
		// The base X position:
		int x0 = insets.left + margin_width;
		int x;
		// Start at the top of the column:
		int y = insets.top + margin_height;

		// Loop through the children:
		for (int i = 0; i < numberChildren; i++) {
			// Get the child:
			child = parent.getComponent(i);
			// Skip hidden children
			if (child.isVisible() == false) {
				continue;
			}
			// How big is the child?
			Dimension pref = child.getPreferredSize();
			switch (alignment) {
				// Compute X coordinate:
				default:
				case LEFT: {
					x = x0;
					break;
				}
				case CENTER: {
					x = (parent_size.width - pref.width) / 2;
					break;
				}
				case RIGHT: {
					x = parent_size.width - insets.right - margin_width - pref.width;
					break;
				}
			}
			// Set the size and position of this child:
			child.setBounds(x, y, pref.width, pref.height);
			// Get Y position of the next child:
			y += pref.height + spacing;
		}
	}

	/** The Container calls this to find out how big the layout SHOULD be. */
	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return layoutSize(parent, 1);
	}

	/** The Container calls this to find out how big the layout MUST be. */
	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return layoutSize(parent, 2);
	}

	/** The Container calls this to find out how big the layout CAN be. */
	@Override
	public Dimension maximumLayoutSize(Container parent) {
		return layoutSize(parent, 3);
	}

	/*
	 * Compute the minimum, maximum, or preferred size of all the visible children.
	 */
	protected Dimension layoutSize(Container parent, int sizetype) {
		int numberChildren = parent.getComponentCount();
		Dimension size = new Dimension(0, 0);
		Insets insets = parent.getInsets();
		int numberVisibleChildren = 0;

		// Compute maximum width and total height of all visible children.
		for (int i = 0; i < numberChildren; i++) {
			Component child = parent.getComponent(i);
			Dimension dimension;
			if (child.isVisible() == false) {
				continue;
			}
			numberVisibleChildren++;
			if (sizetype == 1) {
				dimension = child.getPreferredSize();
			} else if (sizetype == 2) {
				dimension = child.getMinimumSize();
			} else {
				dimension = child.getMaximumSize();
			}
			if (dimension.width > size.width) {
				size.width = dimension.width;
			}
			size.height += dimension.height;
		}

		// Now add in margins and more.
		size.width += insets.left + insets.right + 2 * margin_width;
		size.height += insets.top + insets.bottom + 2 * margin_height;
		if (numberVisibleChildren > 1) {
			size.height += (numberVisibleChildren - 1) * spacing;
		}
		return size;
	}

	/*
	 * To satisfy the interfaces, define other LayoutManager and LayoutManager2
	 * methods which actually go unused by this class.
	 */
	@Override
	public void addLayoutComponent(String constraint, Component comp) {
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraint) {
	}

	@Override
	public void removeLayoutComponent(Component comp) {
	}

	@Override
	public void invalidateLayout(Container parent) {
	}

	@Override
	public float getLayoutAlignmentX(Container parent) {
		return 0.5f;
	}

	@Override
	public float getLayoutAlignmentY(Container parent) {
		return 0.5f;
	}

	public static class Demo {
		public static void main(String[] args) {
			launchInShowBean("je3.ch11.gui.ColumnLayoutPane", 300, 325);
			System.out.println("Demo for ColumnLayout as used by ColumnLayoutPane. The buttons are non-functional.");
		}
	}
}
