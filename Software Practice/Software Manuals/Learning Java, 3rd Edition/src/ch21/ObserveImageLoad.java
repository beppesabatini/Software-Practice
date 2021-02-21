package ch21;

import java.awt.*;
import java.awt.image.*;

/**
 * From Learning Java, 3rd Edition, p. 725-726. Seems to be reporting on the
 * status of an image upload, but doesn't try to actually display the picture
 * (apparently by design). A small image seems to get uploaded one row of pixels
 * at a time, while an enormous photo seems to get uploaded all at once, so this
 * is not at clear.
 * 
 * There are two Run Configurations to demonstrate this:
 * 
 * <pre>
 ObserveLargeImageLoad
 ObserveSmallImageLoad
 * </pre>
 */
public class ObserveImageLoad {

	public static void main(String[] args) {

		ImageObserver myObserver = new MyObserver();
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		String filename = "";
		if (args.length > 0) {
			filename = args[0];
		} else {
			System.out.println("Usage: java ch21.ObserveImageLoad <photoName>");
			return;
		}
		Image img = toolkit.getImage(filename);
		toolkit.prepareImage(img, -1, -1, myObserver);
	}
}
