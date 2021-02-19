package ch21;

import java.awt.*;
import java.awt.image.*;

// From p. 725-726.

public class ObserveImageLoad {

	public static void main(String[] args) {
		ImageObserver myObserver = new ImageObserver() {
			public boolean imageUpdate(Image image, int flags, int x, int y, int width, int height) {
				if ((flags & HEIGHT) != 0) {
					System.out.println("Image height = " + height);
				}
				if ((flags & WIDTH) != 0) {
					System.out.println("Image width = " + width);
				}
				if ((flags & FRAMEBITS) != 0) {
					System.out.println("Another frame finished.");
				}
				if ((flags & SOMEBITS) != 0) {
					System.out.println("Image section :" + new Rectangle(x, y, width, height));
				}
				if ((flags & ALLBITS) != 0) {
					System.out.println("Image finished!");
				}
				if ((flags & ABORT) != 0) {
					System.out.println("Image load aborted...");
				}
				return true;
			}
		};

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		String filename = "";
		if (args.length > 0) {
			filename = args[0];
		} else {
			filename = "src/ch21/cheerleader.jpg";
		}
		Image img = toolkit.getImage(filename);
		toolkit.prepareImage(img, -1, -1, myObserver);
	}

}
