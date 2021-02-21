package ch21;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import utils.LearningJava3Utils;

/**
 * From Learning Java, 3rd Edition, sp. 747. Not working; this launches but it
 * doesn't make the barking sound. It seems to require applet functionality,
 * which Oracle no longer supports.
 */
public class NoisyButton {

	public static void main(String[] args) throws Exception {

		LearningJava3Utils.confirmContinueWithDisfunctional();
		JFrame frame = new JFrame("NoisyButton");

		String filename = null;

		// java.io.File file = new java.io.File(args[0]);
		if (args.length > 0) {
			filename = args[0];
		} else {
			filename = "src/ch21/bark.aiff";
		}
		java.io.File file = new java.io.File(filename);

		@SuppressWarnings("deprecation")
		final AudioClip sound = Applet.newAudioClip(file.toURL());

		// set up the button
		JButton button = new JButton("Woof!");
		button.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				sound.play();
			}
		});

		Container content = frame.getContentPane();
		content.setBackground(Color.pink);
		content.setLayout(new GridBagLayout());
		content.add(button);
		frame.setVisible(true);
		frame.setSize(200, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
