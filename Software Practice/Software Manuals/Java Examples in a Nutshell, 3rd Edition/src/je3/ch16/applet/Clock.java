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
package je3.ch16.applet;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Label;

import java.util.Date;
import java.text.DateFormat;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 491-492. This applet
 * displays the time, and updates it every second.
 */
public class Clock extends Applet implements Runnable {

	private static final long serialVersionUID = -2234444073465282486L;

	// A component in which to display the time:
	private Label time;
	// This object converts the time to a string:
	private DateFormat timeFormat;
	// The thread that updates the time:
	private Thread timer;
	// A flag used to stop the thread:
	private volatile boolean running;

	/**
	 * The init() method is called when the browser first starts the applet. It sets
	 * up the Label component and obtains a DateFormat object.
	 */
	@Override
	public void init() {
		time = new Label();
		time.setFont(new Font("helvetica", Font.BOLD, 12));
		time.setAlignment(Label.CENTER);
		setLayout(new BorderLayout());
		add(time, BorderLayout.CENTER);
		timeFormat = DateFormat.getTimeInstance(DateFormat.MEDIUM);
	}

	/**
	 * This browser calls this method to tell the applet to start running. Here, we
	 * create and start a thread that will update the time each second. Note that we
	 * take care never to have more than one thread.
	 */
	@Override
	public void start() {
		// Set the flag:
		running = true;
		// If we don't already have a thread:
		if (timer == null) {
			// ...then create one...
			timer = new Thread(this);
			// ...and, start it running.
			timer.start();
		}
	}

	/**
	 * This method implements Runnable. It is the body of the thread. Once a second,
	 * it updates the text of the Label to display the current time. AWT and Swing
	 * components are not, in general, thread-safe, and should typically only be
	 * updated from the event-handling thread. We can get away with using a separate
	 * thread here because there is no event handling in this applet, and this
	 * component will never be modified by any other thread.
	 */
	@Override
	public void run() {
		// Loop until we're stopped:
		while (running) {
			// Get the current time, convert it to a String, and display it in the Label.
			time.setText(timeFormat.format(new Date()));
			// Now wait 1000 milliseconds:
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// Do nothing.
			}
		}
		/*
		 * If the thread exits, set it to null, so we can create a new thread if start()
		 * is called again.
		 */
		timer = null;
	}

	/**
	 * The browser calls this method to tell the applet that it is not visible, and
	 * should not run. It sets a flag that tells the run() method to exit.
	 */
	@Override
	public void stop() {
		running = false;
	}

	/**
	 * Returns information about the applet for display by the applet viewer:
	 */
	@Override
	public String getAppletInfo() {
		return "Clock applet Copyright (c) 2000 by David Flanagan";
	}
}
