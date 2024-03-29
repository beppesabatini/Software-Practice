package ch20;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * From Learning Java, 3rd Edition, p. 718-719. A simple implementation of a
 * doodle pad, like an Etch-a-Sketch with a mouse instead of control knobs. The
 * mouse movements are captured off screen in a DrawPad and printed to the
 * screen with graphics.drawScreen().
 */
public class DoodlePad {
	public static void main(String[] args) {
		JFrame frame = new JFrame("DoodlePad");
		Container content = frame.getContentPane();
		content.setLayout(new BorderLayout());
		final DrawPad drawPad = new DrawPad();
		content.add(drawPad, BorderLayout.CENTER);
		JPanel panel = new JPanel();
		JButton clearButton = new JButton("Clear");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawPad.clear();
			}
		});
		panel.add(clearButton);
		content.add(panel, BorderLayout.SOUTH);
		frame.setSize(280, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}

class DrawPad extends JComponent {

	private static final long serialVersionUID = 1733966826864943496L;

	Image image;
	Graphics2D graphics2D;
	int currentX, currentY, oldX, oldY;

	public DrawPad() {
		setDoubleBuffered(false);
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				oldX = e.getX();
				oldY = e.getY();
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				currentX = e.getX();
				currentY = e.getY();
				if (graphics2D != null) {
					graphics2D.drawLine(oldX, oldY, currentX, currentY);
				}
				repaint();
				oldX = currentX;
				oldY = currentY;
			}
		});
	}

	public void paintComponent(Graphics g) {
		if (image == null) {
			image = createImage(getSize().width, getSize().height);
			graphics2D = (Graphics2D) image.getGraphics();
			graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			clear();
		}
		g.drawImage(image, 0, 0, null);
	}

	public void clear() {
		graphics2D.setPaint(Color.white);
		graphics2D.fillRect(0, 0, getSize().width, getSize().height);
		graphics2D.setPaint(Color.black);
		repaint();
	}
}
