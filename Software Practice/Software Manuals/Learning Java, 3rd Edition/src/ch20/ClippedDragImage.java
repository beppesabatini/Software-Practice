package ch20;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * From Learning Java, 3rd Edition, p. 716-717. The user can drag around an
 * image in front of a background. This is a subclass of DragImage; the
 * checkboard background pattern is drawn in that parent. The difference is that
 * in ClippedDragImage, this subclass, only the obscured portion (the "affected
 * area") needs to be redrawn.
 */
public class ClippedDragImage extends DragImage {

	private static final long serialVersionUID = 6626155008724302864L;

	int oldX, oldY;

	public ClippedDragImage(Image i) {
		super(i);
	}

	public void mouseDragged(MouseEvent e) {
		imageX = e.getX();
		imageY = e.getY();
		Rectangle r = getAffectedArea(oldX, oldY, imageX, imageY, imageWidth, imageHeight);
		repaint(r); // repaint just the affected part of the component
		oldX = imageX;
		oldY = imageY;
	}

	private Rectangle getAffectedArea(int oldx, int oldy, int newx, int newy, int width, int height) {
		int x = Math.min(oldx, newx);
		int y = Math.min(oldy, newy);
		int w = (Math.max(oldx, newx) + width) - x;
		int h = (Math.max(oldy, newy) + height) - y;
		return new Rectangle(x, y, w, h);
	}

	public static void main(String[] args) {

		String imageFile = "HELLOWORLD.jpg";
		if (args.length > 0) {
			imageFile = args[0];
		}

		// Turn off double buffering
		// RepaintManager.currentManager(null).setDoubleBufferingEnabled(false);

		/*
		 * Note that the getResource() function below can find the image file even
		 * though the pathname is incomplete. The "src/ch17" part of the path is
		 * missing. The getResource() function searches farther down in the directory
		 * tree. See Chapter 12 and FindResources.java.
		 */
		System.out.println("Current Directory: " + System.getProperty("user.dir"));
		Image image = Toolkit.getDefaultToolkit().getImage(ClippedDragImage.class.getResource(imageFile));
		image = image.getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT);
		JFrame frame = new JFrame("ClippedDragImage");
		frame.getContentPane().add(new ClippedDragImage(image));
		frame.setSize(300, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
