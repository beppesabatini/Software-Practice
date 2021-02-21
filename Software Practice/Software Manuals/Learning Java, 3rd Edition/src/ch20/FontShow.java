package ch20;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.*;
import javax.swing.*;

/**
 * From Learning Java, 3rd edition, p. 705-706. An example of how to get Font
 * Metrics from the current font in use. These can vary between one system and
 * the next, as the system may provide only the closest it can come to what has
 * been requested. Coordinates and font sizes are both expressed in "points"
 * (1/72"), the x and y axes intersect in the upper left, and extend with
 * positive values to the right and positive downwards. With every mouse click,
 * the font changes at random, and the font size toggles between large and
 * small. BFS has significantly revised this example.
 */
public class FontShow extends JComponent {

	private static final long serialVersionUID = -9187151117753311216L;

	private static final int PAD = 25; // frilly line padding
	private boolean bigFont = true;

	private static Font[] allFonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
	private static Font currentFont = allFonts[0];

	public FontShow() {
		System.out.printf("Total number of fonts currently installed: %d\n\n", allFonts.length);
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				bigFont = !bigFont;
				int fontIndex = (int) (Math.random() * (double) allFonts.length);
				currentFont = allFonts[fontIndex];
				repaint();
			}
		});
	}

	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int size = bigFont ? 96 : 64;
		// String fontName = "Dialog";
		String fontFamilyName = currentFont.getFamily();
		Font font = new Font(fontFamilyName, Font.PLAIN, size);
		g2.setFont(font);
		int width = getSize().width;
		int height = getSize().height;

		FontRenderContext frc = g2.getFontRenderContext();
		LineMetrics metrics = font.getLineMetrics(fontFamilyName, frc);
		float messageWidth = (float) font.getStringBounds(fontFamilyName, frc).getWidth();

		// center text
		float ascent = metrics.getAscent();
		float descent = metrics.getDescent();
		float x = (width - messageWidth) / 2;
		float y = (height + metrics.getHeight()) / 2 - descent;

		g2.setPaint(getBackground());
		g2.fillRect(0, 0, width, height);
		g2.setPaint(getForeground());
		g2.drawString(fontFamilyName, x, y);

		g2.setPaint(Color.white); // Base lines
		drawLine(g2, x - PAD, y, x + messageWidth + PAD, y); // bottom horizontal baseline
		drawLine(g2, x, y + PAD, x, y - ascent - PAD); // left vertical baseline
		g2.setPaint(Color.green); // Ascent line
		drawLine(g2, x - PAD, y - ascent, x + messageWidth + PAD, y - ascent);
		g2.setPaint(Color.red); // Descent line
		drawLine(g2, x - PAD, y + descent, x + messageWidth + PAD, y + descent);

		drawFontMetrics(font, size, y - ascent, y, y + descent);
	}

	private void drawFontMetrics(Font font, int size, float ascent, float baseline, float descent) {
		String fontName = font.getFontName();
		System.out.printf("Font Name: %s\n", fontName);
		System.out.printf("Font size: %d pt\n", size);
		System.out.printf("Ascent (green, max font hight):\t %4.2f pt\n", ascent);
		System.out.printf("Bottom Baseline (white):\t%4.2f pt\n", baseline);
		System.out.printf("Descent (red, below baseline):\t%4.2f pt\n", descent);
		System.out.printf("\n");
	}

	private void drawLine(Graphics2D g2, double x0, double y0, double x1, double y1) {
		Shape line = new java.awt.geom.Line2D.Double(x0, y0, x1, y1);
		g2.draw(line);
	}

	public static void main(String args[]) {

		JFrame frame = new JFrame("FontShow");
		frame.setSize(900, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new FontShow());
		frame.setVisible(true);
	}
}
