package ch21;

import java.awt.*;
import javax.swing.*;

/**
 * From Learning Java 3rd ed. p. 727-728. Demonstrates how Java could show a
 * status message ("Loading...") while the system was waiting for the upload to
 * complete. This requires an enormous photo or a slow upload to test it
 * meaningfully.
 * <p>
 * Users, note: If the image is a command line argument, then filenames with
 * white space in the path must be quoted, and the quotation marks must be
 * passed through.
 * 
 * <pre>
   C:/Program Files/mypicture.jpg  // Fails
  "C:/Program Files/mypicture.jpg" // Succeeds
 * </pre>
 */
public class StatusImage extends JComponent {

	private static final long serialVersionUID = -8780903686394880298L;

	boolean loaded = false;
	String loadingMessage = "Loading...";
	Image image;

	public StatusImage(Image image) {
		this.image = image;
	}

	public void paint(Graphics g) {
		if (loaded)
			g.drawImage(image, 0, 0, this);
		else {
			g.drawRect(0, 0, getSize().width - 1, getSize().height - 1);
			g.drawString(loadingMessage, 20, 20);
		}
	}

	public void loaded() {
		loaded = true;
		repaint();
	}

	public void setMessage(String msg) {
		loadingMessage = msg;
		repaint();
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("TrackImage");
		String filename = "";
		if (args.length > 0) {
			filename = args[0];
		} else {
			System.out.println("Usage: StatusImage <veryLargePhotoName>");
			return;
		}

		Image image = Toolkit.getDefaultToolkit().getImage(filename);
		StatusImage statusImage = new StatusImage(image);
		/*
		 * We ought to be able to display the enormous photo with scroll bars--see ch17,
		 * Scroll Pane Frame--but so far it's not working.
		 * 
		 * frame.getContentPane().add(new JScrollPane(statusImage));
		 */
		frame.getContentPane().add(statusImage);
		frame.setSize(4000, 1000);
		frame.setVisible(true);

		MediaTracker tracker = new MediaTracker(statusImage);
		int MAIN_IMAGE = 0;
		tracker.addImage(image, MAIN_IMAGE);
		try {
			tracker.waitForID(MAIN_IMAGE);
		} catch (InterruptedException e) {
		}
		if (tracker.isErrorID(MAIN_IMAGE)) {
			statusImage.setMessage("Error");
		} else {
			statusImage.loaded();
		}
	}
}
