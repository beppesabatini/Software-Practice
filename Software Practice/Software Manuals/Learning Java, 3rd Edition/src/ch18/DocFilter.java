package ch18;

import javax.swing.*;
import javax.swing.text.*;

/**
 * From Learning Java, 3rd Edition, p. 627. Demonstrates a document filter, in
 * this case, one which converts all input to a document to upper case.
 */
public class DocFilter {
	public static void main(String[] args) throws Exception {
		JTextField field = new JTextField(30);

		((AbstractDocument) (field.getDocument())).setDocumentFilter(new DocumentFilter() {
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
					throws BadLocationException {
				System.out.println("insert");
				fb.insertString(offset, string.toUpperCase(), attr);
			}

			public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attr)
					throws BadLocationException {
				System.out.println("replace");
				fb.replace(offset, length, string.toUpperCase(), attr);
			}
		});

		JFrame frame = new JFrame("User Information");
		frame.getContentPane().add(field);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
