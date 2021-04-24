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
import java.awt.Component;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Font;
import java.awt.Point;

import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 312-315. This class is a
 * JTree subclass that displays the tree of AWT or Swing components that make up
 * a GUI.
 */
public class ComponentTree extends JTree {

	private static final long serialVersionUID = -131822905363273334L;

	/**
	 * All this constructor method has to do is set the TreeModel and
	 * TreeCellRenderer objects for the tree. It is these classes (defined below)
	 * that do all the real work.
	 */
	public ComponentTree(Component c) {
		super(new ComponentTreeModel(c));
		setCellRenderer(new ComponentCellRenderer(getCellRenderer()));
	}

	/**
	 * The TreeModel class puts hierarchical data in a form that the JTree can
	 * display. This implementation interprets the containment hierarchy of a
	 * Component for display by the ComponentTree class. Note that any kind of
	 * Object can be a node in the tree, as long as the TreeModel knows how to
	 * handle it.
	 */
	static class ComponentTreeModel implements TreeModel {
		// The root object of the tree.
		Component root;

		// Constructor: just remember the root object.
		public ComponentTreeModel(Component root) {
			this.root = root;
		}

		// Return the root of the tree.
		@Override
		public Object getRoot() {
			return root;
		}

		/*
		 * Is this node a leaf? (Leaf nodes are displayed differently by JTree.) Any
		 * node that isn't a container is a leaf, since they cannot have children. We
		 * also define containers with no children as leaves.
		 */
		@Override
		public boolean isLeaf(Object node) {
			if (!(node instanceof Container)) {
				return true;
			}
			Container c = (Container) node;
			return c.getComponentCount() == 0;
		}

		// How many children does this node have?
		@Override
		public int getChildCount(Object node) {
			if (node instanceof Container) {
				Container container = (Container) node;
				return container.getComponentCount();
			}
			return (0);
		}

		// Return the specified child of a parent node.
		@Override
		public Object getChild(Object parent, int index) {
			if (parent instanceof Container) {
				Container container = (Container) parent;
				return container.getComponent(index);
			}
			return null;
		}

		// Return the index of the child node in the parent node.
		@Override
		public int getIndexOfChild(Object parent, Object child) {
			if (!(parent instanceof Container)) {
				return -1;
			}
			Container container = (Container) parent;
			Component[] children = container.getComponents();
			if (children == null) {
				return -1;
			}
			for (int i = 0; i < children.length; i++) {
				if (children[i] == child) {
					return i;
				}
			}
			return -1;
		}

		/*
		 * This method is only required for editable trees, so it is not implemented
		 * here.
		 */
		@Override
		public void valueForPathChanged(TreePath path, Object newvalue) {
		}

		/*
		 * This TreeModel never fires any events (since it is not editable) so event
		 * listener registration methods are left unimplemented.
		 */
		@Override
		public void addTreeModelListener(TreeModelListener l) {
		}

		@Override
		public void removeTreeModelListener(TreeModelListener l) {
		}
	}

	/**
	 * A TreeCellRenderer displays each node of a tree. The default renderer
	 * displays arbitrary Object nodes by calling their toString() method. The
	 * Component.toString() method returns long strings with extraneous information.
	 * Therefore, we use this "wrapper" implementation of TreeCellRenderer to
	 * convert nodes from Component objects to useful String values before passing
	 * those String values on to the default renderer.
	 */
	static class ComponentCellRenderer implements TreeCellRenderer {
		// The renderer for which we are a wrapper.
		TreeCellRenderer renderer;

		// Constructor: just remember the renderer.
		public ComponentCellRenderer(TreeCellRenderer renderer) {
			this.renderer = renderer;
		}

		/*
		 * This is the only TreeCellRenderer method. Compute the string to display, and
		 * pass it to the wrapped renderer
		 */
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
				boolean leaf, int row, boolean hasFocus) {
			// Component type:
			String newvalue = value.getClass().getName();
			// Component name:
			String name = ((Component) value).getName();
			if (name != null) {
				newvalue += " (" + name + ")";
			}
			// Use the wrapped renderer object to do the real work.
			return renderer.getTreeCellRendererComponent(tree, newvalue, selected, expanded, leaf, row, hasFocus);
		}
	}

	/**
	 * This main() method demonstrates the use of the ComponentTree class: it puts a
	 * ComponentTree component in a Frame, and uses the ComponentTree to display its
	 * own GUI hierarchy. It also adds a TreeSelectionListener to display additional
	 * information about each component as it is selected.
	 */
	public static void main(String[] args) {
		// Create a frame for the demo, and handle window close requests.
		JFrame jFrame = new JFrame("ComponentTree Demo");
		jFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		/*
		 * Create a scroll pane and a "message line" and add them to the center and
		 * bottom of the frame.
		 */
		JScrollPane scrollpane = new JScrollPane();
		final JLabel msgline = new JLabel(" ");
		jFrame.getContentPane().add(scrollpane, BorderLayout.CENTER);
		jFrame.getContentPane().add(msgline, BorderLayout.SOUTH);

		/*
		 * Now create the ComponentTree object, specifying the frame as the component
		 * whose tree is to be displayed. Also set the tree's font.
		 */
		JTree tree = new ComponentTree(jFrame);
		tree.setFont(new Font("SansSerif", Font.BOLD, 12));

		// Only allow a single item in the tree to be selected at once.
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		/*
		 * Add an event listener for notifications when the tree selection state
		 * changes.
		 */
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				/*
				 * Tree selections are referred to by path. We only care about the last node in
				 * the path.
				 */
				TreePath path = e.getPath();
				Component component = (Component) path.getLastPathComponent();
				/*
				 * Now we know what component was selected, so display some information about it
				 * in the message line.
				 */
				if (component.isShowing()) {
					Point point = component.getLocationOnScreen();
					String message = "";
					message += "x: " + point.x + "  y: " + point.y;
					message += "  width: " + component.getWidth() + "  height: " + component.getHeight();
					msgline.setText(message);
				} else {
					msgline.setText("component is not showing");
				}
			}
		});

		// Now that we've set up the tree, add it to the scroll pane.
		scrollpane.setViewportView(tree);

		// Finally, set the size of the main window, and pop it up.
		jFrame.setSize(600, 400);
		jFrame.setVisible(true);
	}
}
