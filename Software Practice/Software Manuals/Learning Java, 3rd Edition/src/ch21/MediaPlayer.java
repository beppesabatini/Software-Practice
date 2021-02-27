package ch21;

import java.net.URL;
import javax.swing.*;

import utils.LearningJava3Utils;

import javax.media.*;
import java.awt.Component;

/**
 * From Learning Java, 3rd edition, p. 748-749. Not functional. Not clear why,
 * but probably just too outdated.
 * <p>
 * A similar MediaPlayer, using different third-party libraries, can be found in
 * this workspace at:
 * <p>
 * Coding Exercises/src/javafx.ex03/MediaPlayerExample.java
 * <p>
 * That one actually works, but it only supports mp3 files (not avi files such
 * as dancing_baby.avi).
 */
public class MediaPlayer {
	public static void main(String[] args) throws Exception {
		LearningJava3Utils.confirmContinueWithDisfunctional();

		final JFrame frame = new JFrame("MediaPlayer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		URL url;
		if (args.length > 0) {
			url = new URL(args[0]);
		} else {
			url = new URL(
					"file:D:/workspaces/Software Practice/Software Manuals/Learning Java, 3rd Edition/src/ch21/dancing_baby.avi");
		}
		final Player player = Manager.createPlayer(url);

		player.addControllerListener(new ControllerListener() {
			public void controllerUpdate(ControllerEvent ce) {
				if (ce instanceof RealizeCompleteEvent) {
					Component visual = player.getVisualComponent();
					Component control = player.getControlPanelComponent();
					if (visual != null) {
						frame.getContentPane().add(visual, "Center");
					}
					frame.getContentPane().add(control, "South");
					frame.pack();
					frame.setVisible(true);
					player.start();
				}
			}
		});

		player.realize();
	}
}
