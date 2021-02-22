package ch17;

import javax.swing.*;

/**
 * From Learning Java, 3rd Edition, p. 612. A demonstration of a frame with two
 * tabbed panels; one includes non-functional widgets, and the other one
 * displays an image.
 */
public class TabbedPaneFrame {
	public static void main(String[] args) {
		JFrame frame = new JFrame("TabbedPaneFrame");
		JTabbedPane tabby = new JTabbedPane();

		// create the controls pane
		JPanel controls = new JPanel();
		controls.add(new JLabel("Service:"));
		JList<String> list = new JList<String>(new String[] { "Web server", "FTP server" });
		list.setBorder(BorderFactory.createEtchedBorder());
		controls.add(list);
		controls.add(new JButton("Start"));

		// create an image pane
		String filename = "src/ch17/Piazza di Spagna.jpg";
		JLabel image = new JLabel(new ImageIcon(filename));
		JComponent picture = new JScrollPane(image);
		tabby.addTab("Controls", controls);
		tabby.addTab("Picture", picture);

		frame.getContentPane().add(tabby);

		frame.setSize(200, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
