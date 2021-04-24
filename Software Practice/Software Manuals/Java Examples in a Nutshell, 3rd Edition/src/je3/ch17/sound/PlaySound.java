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
package je3.ch17.sound;

import utils.LearningJava3Utils;

/**
 * From Java Examples in a Nutshell, 3rd Edition, p. 504. Play a sound file from
 * the network, using the java.applet.Applet API. This example no longer works.
 * For a possible substitute, try launching the Run Configuration
 * "MediaPlayerAudioExample," which uses JavaFX functionality. The working sound
 * player is in javafx.ex02.MediaPlayerExample, in the Coding Exercises project.
 */
public class PlaySound {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws java.net.MalformedURLException {
		LearningJava3Utils.confirmContinueWithDisfunctional();
		if (args.length <= 0) {
			System.out.println("Usage: PlaySound <soundFile>");
		}
		System.out.println("Attempting to play: " + args[0]);
		java.applet.AudioClip clip = java.applet.Applet.newAudioClip(new java.net.URL(args[0]));
		clip.play();
	}
}
