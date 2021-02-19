package ch18;

import javax.swing.*;

/**
 * From p. 643-644. Displays a table with values hard-coded in the cells.
 */
public class DullShipTable {
	public static void main(String[] args) {
		// create some tabular data
		String[] headings = new String[] { "Number", "Hot?", "Origin", "Destination", "Ship Date", "Weight" };
		Object[][] data = new Object[][] {
				{ "100420", Boolean.FALSE, "Des Moines IA", "Spokane WA", "02/06/2000", 450.0 },
				{ "202174", Boolean.TRUE, "Basking Ridge NJ", "Princeton NJ", "05/20/2000", 1250.0 },
				{ "450877", Boolean.TRUE, "St. Paul MN", "Austin TX", "03/20/2000", 1745.0 },
				{ "101891", Boolean.FALSE, "Boston MA", "Albany NY", "04/04/2000", 88.0 } };

		// create the data model and the JTable
		JTable table = new JTable(data, headings);

		JFrame frame = new JFrame("DullShipTable v1.0");
		frame.getContentPane().add(new JScrollPane(table));

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 200);
		frame.setVisible(true);
	}
}
