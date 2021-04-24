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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Font;
import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;

import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javafx.scene.media.AudioClip;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 494-497. An applet that
 * counts down from a specified time. When it reaches 00:00, it (optionally)
 * plays a sound and (optionally) moves the browser to a new page. Place the
 * mouse over the applet to pause the count; move it off to resume. This class
 * demonstrates most applet methods and features. Change parameters, if
 * necessary, in the run configuration.
 */
public class Countdown extends JApplet implements ActionListener, MouseListener {

	private static final long serialVersionUID = -936370816500352559L;

	// How many milliseconds remain in the countdown:
	private long remaining;
	// When count was last updated:
	private long lastUpdate;
	// Displays the count:
	private JLabel label;
	// Updates the count every second:
	private Timer timer;
	// Format minutes:seconds with leading zeros:
	private NumberFormat format;
	// Image to display along with the time:
	private Image image;
	// Sound to play when we reach 00:00:
	private AudioClip sound;

	// The init() function is called when the applet is first loaded.
	@Override
	public void init() {
		/*
		 * Figure out how long to count by reading the "minutes" parameter, defined in a
		 * <param> tag inside the <applet> tag. Convert it to microseconds.
		 */
		String minutes = getParameter("minutes");
		if (minutes != null) {
			remaining = Integer.parseInt(minutes) * 60000;
		} else {
			// Five seconds by default:
			remaining = 5000;
		}

		// Create a JLabel to display remaining time, and set some properties.
		label = new JLabel();
		label.setHorizontalAlignment(SwingConstants.CENTER);
		// Make this opaque, because the label draws the background color.
		label.setOpaque(true);

		/*
		 * Read some parameters for this JLabel object. Set parameters in the run
		 * configuration ("Countdown") if desired
		 */
		String font = getParameter("font");
		String foreground = getParameter("foreground");
		String background = getParameter("background");
		String imageFilename = getParameter("image");

		// Set label properties based on those parameters.
		if (font != null) {
			label.setFont(Font.decode(font));
		}
		if (foreground != null) {
			label.setForeground(Color.decode(foreground));
		}
		if (background != null) {
			label.setBackground(Color.decode(background));
		}

		if (imageFilename != null) {
			URL imageURL = Countdown.class.getResource(imageFilename);
			// Load the image, and save it so we can release it later.
			image = this.getImage(imageURL);
			// Now display the image in the JLabel.
			label.setIcon(new ImageIcon(image));
		}

		/*
		 * Now add the label to the applet. Like JFrame and JDialog, JApplet has a
		 * content pane to which you add children.
		 */
		getContentPane().add(label, BorderLayout.CENTER);

		// Get an optional AudioClip to play when the count expires.
		String soundFilename = getParameter("sound");
		if (soundFilename != null) {
			URL soundURL = Countdown.class.getResource(soundFilename);
			AudioClip alarmSound = new AudioClip(soundURL.toString());
			sound = alarmSound;
		}

		/*
		 * Obtain a NumberFormat object to convert number of minutes and seconds to
		 * strings. Set it up to produce a leading 0 if necessary.
		 */
		format = NumberFormat.getNumberInstance();
		// Pad with a leading zero if necessary:
		format.setMinimumIntegerDigits(2);

		/*
		 * Specify a MouseListener to handle mouse events in the applet. Note that the
		 * JApplet implements this interface itself.
		 */
		addMouseListener(this);

		/*
		 * Create a timer to call the actionPerformed() method immediately, and then
		 * every 1000 milliseconds. Note we don't start the timer yet.
		 */
		timer = new Timer(1000, this);
		// The first timer starts immediately:
		timer.setInitialDelay(0);
	}

	// Free up any resources we hold. This is called when the applet is done.
	@Override
	public void destroy() {
		if (image != null)
			image.flush();
	}

	/*
	 * The browser calls this to start the applet running. The resume() method is
	 * defined below.
	 */
	@Override
	public void start() {
		// Start displaying updates:
		resume();
	}

	/*
	 * The browser calls this to stop the applet. It may be restarted later. The
	 * pause() method is defined below.
	 */
	@Override
	public void stop() {
		// Stop displaying updates:
		pause();
	}

	/* Return information about the applet. */
	public String getAppletInfo() {
		return "Countdown applet Copyright (c) 2003 by David Flanagan";
	}

	// Return information about the applet parameters.
	@Override
	public String[][] getParameterInfo() {
		return parameterInfo;
	}

	/*
	 * This is the parameter information. One array of strings for each parameter.
	 * The elements are parameter name, type, and description.
	 */
	static String[][] parameterInfo = { { "minutes", "number", "time, in minutes, to countdown from" },
			{ "font", "font", "optional font for the time display" },
			{ "foreground", "color", "optional foreground color for the time" },
			{ "background", "color", "optional background color" },
			{ "image", "image URL", "optional image to display next to countdown" },
			{ "sound", "sound URL", "optional sound to play when we reach 00:00" },
			{ "newpage", "document URL", "URL to load when timer expires" }, };

	// Start or resume the countdown.
	void resume() {
		// Restore the time from which we're counting down, and restart the timer.
		lastUpdate = System.currentTimeMillis();
		// Start the timer:
		timer.start();
	}

	// Pause the countdown.
	void pause() {
		// Subtract elapsed time from the remaining time, and stop timing;
		long now = System.currentTimeMillis();
		remaining -= (now - lastUpdate);
		// Stop the timer:
		timer.stop();
	}

	/*
	 * Update the displayed time. This method is called from actionPerformed(),
	 * which is itself invoked by the timer.
	 */
	void updateDisplay() {
		// Current time in microseconds:
		long now = System.currentTimeMillis();
		// Microseconds elapsed since last update:
		long elapsed = now - lastUpdate;
		// Adjust the remaining time:
		remaining -= elapsed;
		// Remember this update time:
		lastUpdate = now;

		// Convert the remaining milliseconds to mm:ss format, and display them.
		if (remaining < 0) {
			remaining = 0;
		}
		int minutes = (int) (remaining / 60000);
		int seconds = (int) ((remaining % 60000) / 1000);
		label.setText(format.format(minutes) + ":" + format.format(seconds));

		// If we've completed the countdown, beep and display a new page.
		if (remaining == 0) {
			// Stop updating now:
			timer.stop();
			// If we have an alarm sound clip, play it now:
			if (sound != null) {
				sound.play();
			}
			/*
			 * If there is a URL specified for a new page, make the browser load that page
			 * now.
			 */
			String newpage = getParameter("newpage");
			if (newpage != null) {
				try {
					URL url = new URL(getDocumentBase(), newpage);
					getAppletContext().showDocument(url);
				} catch (MalformedURLException ex) {
					showStatus(ex.toString());
				}
			}
		}
	}

	/*
	 * This method implements the ActionListener interface. It is invoked once a
	 * second by the Timer object, and updates the JLabel to display minutes and
	 * seconds remaining.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		updateDisplay();
	}

	/*
	 * The methods below implement the MouseListener interface. We use two of them
	 * to pause the countdown when the mouse hovers over the timer. Note that we
	 * also display a message in the status line.
	 * 
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		// Pause the countdown:
		pause();
		// Display the status line message:
		showStatus("Paused");
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// Resume the countdown:
		resume();
		// Clear the status line:
		showStatus("");
	}

	// These MouseListener methods are unused.
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
}
