package ch21;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;

import javax.swing.*;
import javafx.scene.media.AudioClip;

import ch12.mypackage.FindResources;

/**
 * From Learning Java, 3rd Edition, p. 747. The version in the manual uses an
 * old version of AudioClip from the old Applet jar. Java no longer supports
 * Applets and that old version no longer works. To get this working, download
 * and install JavaFX and use those jars instead, as done here. One possible
 * source for the jars is below.
 * 
 * @see <a href="https://gluonhq.com/products/javafx/">JavaFX Download</a>
 */
public class NoisyButton {

	public static void main(String[] args) throws Exception {

		JFrame frame = new JFrame("NoisyButton");

		String filename = null;
		if (args.length > 0) {
			filename = args[0];
		} else {
			/**
			 * Note the path here for finding local files with getResource() is shorter than
			 * the path used by some other functions.
			 */
			filename = "/ch21/bark.aiff";
		}
		URL url = FindResources.class.getResource(filename);
		String urlString = url.toString();
		final AudioClip sound = new AudioClip(urlString);

		// set up the button
		JButton button = new JButton("Woof!");
		button.addActionListener(new ActionListener() {
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
