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

import java.awt.Component;
import java.awt.Toolkit;

// Ring the bell!
public class Beep {
	public static void main(String[] args) {
		/*
		 * From Java Examples in a Nutshell, 3rd Edition, pp. 502-503. In terminal-based
		 * applications, this is a non-portable, unreliable way to sound the terminal
		 * bell (if there is one) and get the user's attention. \u0007 is the ASCII BEL
		 * or Ctrl-G character.
		 */
		System.out.println("BEEP\u0007!");

		/*
		 * For applications that can use AWT, there is another way to ring the bell.
		 */
		String[] words = new String[] { "Shave ", "and ", "a ", "hair", "cut ", "two ", "bits." };
		int[] pauses = new int[] { 300, 150, 150, 250, 450, 250, 1 };

		for (int i = 0; i < pauses.length; i++) {
			/* Try ringing the bell using AWT. */

			// For console-based systems:
			// java.awt.Toolkit.getDefaultToolkit().beep();

			// For GUI-based systems:
			@SuppressWarnings("serial")
			Component dummyComponent = new Component() {
			};
			Toolkit toolkit = dummyComponent.getToolkit();
			toolkit.beep();
			System.out.print(words[i]);
			System.out.flush();
			// Wait a while before beeping again.
			try {
				Thread.sleep(pauses[i]);
			} catch (InterruptedException interruptedException) {
				interruptedException.printStackTrace();
			}
		}
		System.out.println();
	}
}
