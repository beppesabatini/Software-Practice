package ch21;

import java.awt.*;
import javax.swing.*;

// If the image is a command line argument, then filenames with white space 
// in the path must be quoted, and the quotation marks must be passed 
// through. 
// C:/Program Files/mypicture.jpg   // Fails
// "C:/Program Files/mypicture.jpg" // Succeeds
// From p. 727-728.

public class StatusImage extends JComponent {

	private static final long serialVersionUID = -8780903686394880298L;

	boolean loaded = false;
	String message = "Loading...";
	Image image;

	public StatusImage(Image image) {
		this.image = image;
	}

	public void paint(Graphics g) {
		if (loaded)
			g.drawImage(image, 0, 0, this);
		else {
			g.drawRect(0, 0, getSize().width - 1, getSize().height - 1);
			g.drawString(message, 20, 20);
		}
	}

	public void loaded() {
		loaded = true;
		repaint();
	}

	public void setMessage(String msg) {
		message = msg;
		repaint();
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("TrackImage");
		String filename = "";
		if (args.length > 0) {
			filename = args[0];
		} else {
			filename = "src/ch21/cheerleader.jpg";
		}

		Image image = Toolkit.getDefaultToolkit().getImage(filename);
		StatusImage statusImage = new StatusImage(image);
		frame.getContentPane().add(statusImage);
		frame.setSize(300, 300);
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
