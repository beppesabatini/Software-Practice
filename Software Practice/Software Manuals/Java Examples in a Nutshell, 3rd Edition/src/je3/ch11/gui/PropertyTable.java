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

import java.awt.Dimension;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 301-311. This class is a
 * JTable subclass that displays a table of the JavaBeans properties of any
 * specified class.
 */
public class PropertyTable extends JTable {

	private static final long serialVersionUID = 7126584512565233853L;

	/**
	 * This main() method allows the PropertyTable class to be demonstrated as a
	 * standalone.
	 */
	public static void main(String[] args) {
		// Specify the name of the class as a command-line argument.
		Class<?> beanClass = null;
		try {
			// Use reflection to get the Class from the class name.
			beanClass = Class.forName(args[0]);
		} catch (Exception e) {
			// Report errors:
			System.out.println("Can't find specified class: " + e.getMessage());
			System.out.println("Usage: java PropertyTable <bean class name>");
			System.exit(0);
		}

		// Create a table to display the properties of the specified class.
		JTable table = new PropertyTable(beanClass);

		/*
		 * Then put the table in a scrolling window, put the scrolling window into a
		 * frame, and pop it all up on to the screen.
		 */
		JScrollPane scrollpane = new JScrollPane(table);
		JFrame jFrame = new JFrame("Properties of JavaBean: " + args[0]);
		jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		jFrame.getContentPane().add(scrollpane);
		jFrame.setSize(new Dimension(650, 650));
		jFrame.setVisible(true);
	}

	/**
	 * This constructor method specifies what data the table will display (the table
	 * model) and uses the TableColumnModel to customize the way that the table
	 * displays it. The hard work is done by the TableModel implementation below.
	 */
	public PropertyTable(Class<?> beanClass) {
		// Set the data model for this table
		try {
			setModel(new JavaBeanPropertyTableModel(beanClass));
		} catch (IntrospectionException e) {
			System.err.println("WARNING: can't introspect: " + beanClass);
		}

		// Tweak the appearance of the table by manipulating its column model.
		TableColumnModel columnModel = getColumnModel();

		// Set column widths
		columnModel.getColumn(0).setPreferredWidth(125);
		columnModel.getColumn(1).setPreferredWidth(200);
		columnModel.getColumn(2).setPreferredWidth(75);
		columnModel.getColumn(3).setPreferredWidth(50);

		// Right justify the text in the first column.
		TableColumn nameColumn = columnModel.getColumn(0);
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(SwingConstants.RIGHT);
		nameColumn.setCellRenderer(renderer);
	}

	/**
	 * This class implements TableModel, and represents JavaBeans property data in a
	 * way that the JTable component can display. If you've got some type of tabular
	 * data to display, implement a TableModel class to describe that data, and the
	 * JTable component will be able to display it.
	 */
	static class JavaBeanPropertyTableModel extends AbstractTableModel implements TableModel, Serializable {

		private static final long serialVersionUID = 7039490568035599244L;

		// The properties to display:
		PropertyDescriptor[] properties;

		/**
		 * The constructor: use the JavaBeans introspector mechanism to get information
		 * about all the properties of a bean. Once we've got this information, the
		 * other methods will interpret it for JTable.
		 **/
		public JavaBeanPropertyTableModel(Class<?> beanClass) throws java.beans.IntrospectionException {
			// Use the Introspector class to get "bean info" about the class.
			BeanInfo beaninfo = Introspector.getBeanInfo(beanClass);
			// Get the property descriptors from that BeanInfo class.
			properties = beaninfo.getPropertyDescriptors();

			/*
			 * Now do a case-insensitive sort by property name. The anonymous Comparator
			 * implementation specifies how to sort PropertyDescriptor objects by name.
			 */
			Arrays.sort(properties, new Comparator<PropertyDescriptor>() {
				public int compare(PropertyDescriptor a, PropertyDescriptor b) {
					return a.getName().compareToIgnoreCase(b.getName());
				}
			});
		}

		// These are the names of the columns represented by this TableModel.
		static final String[] columnNames = new String[] { "Name", "Type", "Access", "Bound" };

		// These are the types of the columns represented by this TableModel
		static final Class<?>[] columnTypes = new Class[] { String.class, Class.class, String.class, Boolean.class };

		// These simple methods return basic information about the table
		public int getColumnCount() {
			return columnNames.length;
		}

		public int getRowCount() {
			return properties.length;
		}

		public String getColumnName(int column) {
			return columnNames[column];
		}

		public Class<?> getColumnClass(int column) {
			return columnTypes[column];
		}

		/**
		 * This method returns the value that appears at the specified row and column of
		 * the table.
		 **/
		public Object getValueAt(int row, int column) {
			PropertyDescriptor prop = properties[row];
			switch (column) {
				case 0:
					return prop.getName();
				case 1:
					return prop.getPropertyType();
				case 2:
					return getAccessType(prop);
				case 3:
					return prop.isBound();
				default:
					return null;
			}
		}

		// A helper method called from getValueAt() above.
		String getAccessType(PropertyDescriptor prop) {
			java.lang.reflect.Method reader = prop.getReadMethod();
			java.lang.reflect.Method writer = prop.getWriteMethod();
			if ((reader != null) && (writer != null)) {
				return "Read/Write";
			} else if (reader != null) {
				return "Read-Only";
			} else if (writer != null) {
				return "Write-Only";
			}
			// This should never happen.
			else {
				return "No Access";
			}
		}
	}
}
