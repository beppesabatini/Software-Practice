package ch21;

import java.awt.*;
import java.awt.image.*;

/**
 * Similar to code from Learning Java, 3rd Edition, p. 725-726. This class is
 * given as an example of an ImageObserver, which receives information on the
 * status of a photo upload in progress and acts on that info. This class is
 * used by the ObserveImageLoad class, in the current chapter. See that file for
 * more information; its operation is not yet clear.
 */
class MyObserver implements ImageObserver {

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
}
