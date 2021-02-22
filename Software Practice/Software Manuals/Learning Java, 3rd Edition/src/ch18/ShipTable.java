package ch18;

import javax.swing.*;
import javax.swing.table.*;

/**
 * From Learning Java, 3rd Edition, p. 647-648. A shipping schedule is
 * implemented as a JTable, displaying hard-coded data.
 */
public class ShipTable {
	public static class ShipTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 3017298952175160801L;

		private String[] headings = new String[] { "Number", "Hot?", "Origin", "Destination", "Ship Date", "Weight" };
		private Object[][] data = new Object[][] {
				{ "100420", Boolean.FALSE, "Des Moines IA", "Spokane WA", "02/06/2000", 450 },
				{ "202174", Boolean.TRUE, "Basking Ridge NJ", "Princeton NJ", "05/20/2000", 1250.0 },
				{ "450877", Boolean.TRUE, "St. Paul MN", "Austin TX", "03/20/2000", 1745.0 },
				{ "101891", Boolean.FALSE, "Boston MA", "Albany NY", "04/04/2000", 88.0 } };

		public int getRowCount() {
			return data.length;
		}

		public int getColumnCount() {
			return data[0].length;
		}

		public Object getValueAt(int row, int column) {
			return data[row][column];
		}

		public String getColumnName(int column) {
			return headings[column];
		}

		public Class<?> getColumnClass(int column) {
			return data[0][column].getClass();
		}
	}

	public static void main(String[] args) {
		// create the data model and the JTable
		TableModel model = new ShipTableModel();
		JTable table = new JTable(model);

		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		JFrame frame = new JFrame("ShipTable v1.0");
		frame.getContentPane().add(new JScrollPane(table));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 200);
		frame.setVisible(true);
	}
}
