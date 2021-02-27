package magicbeans;

import javax.swing.*;
import java.awt.event.*;

/**
 * From Learning Java, 3rd Edition, p. 770-771.
 */
public class NumericField extends JTextField {

	private static final long serialVersionUID = 8850104061293539723L;

	static int defaultFieldSize = 6;
	private double value;

	public NumericField() {
		super(defaultFieldSize);
		setInputVerifier(new InputVerifier() {
			public boolean verify(JComponent comp) {
				JTextField field = (JTextField) comp;
				try {
					setValue(Double.parseDouble(field.getText()));
				} catch (NumberFormatException e) {
					comp.getToolkit().beep();
					field.selectAll();
					return false;
				}
				return true;
			}
		});

		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getInputVerifier().verify(NumericField.this);
			}
		});

	}

	public double getValue() {
		return value;
	}

	public void setValue(double newValue) {
		double oldValue = value;
		value = newValue;
		setText("" + newValue);
		firePropertyChange("value", oldValue, newValue);
	}
}
