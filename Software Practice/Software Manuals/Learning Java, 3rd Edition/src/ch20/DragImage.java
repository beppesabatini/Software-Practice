package ch20;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * From Learning Java, 3rd Edition, p. 712-713. The user can drag around an
 * image in front of a checkerboard background. The entire composite image is
 * redrawn with each movement.
 */
public class DragImage extends JComponent implements MouseMotionListener {

	private static final long serialVersionUID = -4878834157208266762L;

	static int imageWidth = 60, imageHeight = 60;
	int grid = 10;
	int imageX, imageY;
	Image image;

	public DragImage(Image i) {
		image = i;
		addMouseMotionListener(this);
	}

	public void mouseDragged(MouseEvent e) {
		imageX = e.getX();
		imageY = e.getY();
		repaint();
	}

	public void mouseMoved(MouseEvent e) {
	}

	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		int w = getSize().width / grid;
		int h = getSize().height / grid;
		boolean black = false;
		for (int y = 0; y <= grid; y++)
			for (int x = 0; x <= grid; x++) {
				g2.setPaint(black ? Color.black : Color.white);
				black = !black;
				g2.fillRect(x * w, y * h, w, h);
			}
		g2.drawImage(image, imageX, imageY, this);
	}

	public static void main(String[] args) {
		String imageFile = "L1-Light.jpg";
		if (args.length > 0) {
			imageFile = args[0];
		}

		// Turn off double buffering
		// RepaintManager.currentManager(null).setDoubleBufferingEnabled(false);

		Image image = Toolkit.getDefaultToolkit().getImage(DragImage.class.getResource(imageFile));
		image = image.getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT);
		JFrame frame = new JFrame("DragImage");
		frame.getContentPane().add(new DragImage(image));
		frame.setSize(300, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
