package utils;

import javax.swing.*;

public class LearningJava3Utils {

	public static void main(String[] args) {

		// Test util functions
		confirmContinueWithDisfunctional();
	}

	public static void confirmContinueWithDisfunctional() {

		int result = showConfirmDialog();
		if (result == JOptionPane.YES_OPTION) {
			System.out.println("Continuing");
			return;
		}
	}

	private static int showConfirmDialog() {
		String message = "This feature is not functional yet. Do you wish to continue?";
		int result = JOptionPane.showConfirmDialog(null, message);
		switch (result) {
		case JOptionPane.YES_OPTION:
			return (result);
		case JOptionPane.NO_OPTION:
			System.out.println("Exiting");
			System.exit(1);
			break;
		case JOptionPane.CANCEL_OPTION:
			System.out.println("Exiting");
			System.exit(1);
			break;
		}
		return (result);
	}
}

/*
final class ContinueConfirmation extends JComponent {

	static final long serialVersionUID = -7620631490917182011L;

	GridBagConstraints constraints = new GridBagConstraints();

	public ContinueConfirmation(String message, JFrame frame, CodeLauncher codeLauncher, String[] mainArgs) {
		JButton continueButton = new JButton("Continue");
		continueButton.addActionListener(e -> {
			codeLauncher.launchCode(mainArgs);
			frame.dispose();
		});
		JButton exitButton = new JButton("Exit");
		exitButton.addActionListener(e -> {
			System.exit(1);
		});

		// Tells the component how to arrange components which are
		// added to it.
		setLayout(new GridBagLayout());

		constraints.gridwidth = 2;
		addGB(new JLabel(message), 0, 0);

		constraints.gridwidth = 1;
		addGB(continueButton, 0, 1);
		addGB(exitButton, 1, 1);
	}

	void addGB(Component component, int x, int y) {
		constraints.gridx = x;
		constraints.gridy = y;

		constraints.weightx = 0;
		constraints.weighty = 0;
		constraints.anchor = GridBagConstraints.CENTER;
		add(component, constraints);
	}
}
*/