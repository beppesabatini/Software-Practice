package ch18;

import javax.swing.*;

/**
 * From Learning Java, 3rd Edition, p. 628-629. Two text fields are displayed.
 * The first field allows any input, but the second field enforces correct data
 * entry, and will not let the user leave the field until bad data is corrected.
 */
public class Validator {
	public static void main(String[] args) throws Exception {

		Box form = Box.createVerticalBox();
		form.add(new JLabel("Any Number is Legal Input. Bad input is tolerated."));
		form.add(new JTextField("5000"));

		form.add(new JLabel("Only Numbers 0-100 are Allowed. Other input must be corrected."));
		JTextField rangeField = new JTextField("50");
		rangeField.setInputVerifier(new InputVerifier() {
			public boolean verify(JComponent comp) {
				System.out.println("Verify beginning");
				JTextField field = (JTextField) comp;
				boolean passed = false;
				try {
					int n = Integer.parseInt(field.getText());
					passed = (0 <= n && n <= 100);
				} catch (NumberFormatException e) {
				}
				if (!passed) {
					comp.getToolkit().beep();
					field.selectAll();
				}
				return passed;
			}
		});
		form.add(rangeField);

		JFrame frame = new JFrame("User Information");
		frame.getContentPane().add(form);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}
