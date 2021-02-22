package ch02;

import javax.swing.*;

// Chapter two provides four different ways to write "Hello, Java."

/*
	// This writes to the command line. From Learning Java, 3rd Edition, p. 30.
	public static void main(String[] args) {
		System.out.println("Hello, Java!");
	}
}
*/

/*
// This launches an empty frame; the words appear only in the title bar.
// From Learning Java, 3rd Edition, p. 31.
public class HelloJava {
	public static void main(String[] args) {
		javax.swing.JFrame frame = new javax.swing.JFrame("Hello, Java!");
		frame.setSize(300, 300);
		frame.setVisible(true);
	}
}
*/

/*
// Now the words appear in the center of the frame. From Learning Java, 3rd Edition, p. 31.
public class HelloJava {
	public static void main(String[] args) {
		JFrame frame = new JFrame("HelloJava");
		// After a redraw, the label is redrawn in the center.
		JLabel label = new JLabel("Hello, Java!", JLabel.CENTER);
		frame.getContentPane().add(label);
		frame.setSize(300, 300);
		frame.setVisible(true);
	}
}
*/

/**
 * Discussed in Learning Java, 3rd Edition, p. 36, 41. This is a (sort-of)
 * object-oriented version of three earlier versions.
 */
public class HelloJava {
	public static void main(String[] args) {
		JFrame frame = new JFrame("HelloJava");
		// This invokes the default initializer, which invokes the
		// JComponent default initializer.
		frame.add(new HelloComponent());
		frame.setSize(300, 300);
		frame.setVisible(true);
	}
}

// The JComponent initializer is large and does most of the work. This 
// overrides one function in it. From Learning Java, 3rd Edition, p. 36. 
class HelloComponent extends JComponent {

	private static final long serialVersionUID = -4937192801804587418L;

	public void paintComponent(java.awt.Graphics g) {
		g.drawString("Hello, Java!", 115, 95);
	}
}
