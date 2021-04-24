/*
 * Copyright (c) 2004 David Flanagan.  All rights reserved.
 * This code is from the book Java Examples in a Nutshell, 3nd Edition.
 * It is provided AS-IS, WITHOUT ANY WARRANTY either expressed or implied.
 * You may study, use, and modify it for any non-commercial purpose,
 * including teaching and use in open-source projects.
 * You may distribute it non-commercially as long as you retain this notice.
 * For a commercial use license, or to purchase the book, 
 * please visit http://www.davidflanagan.com/javaexamples3.
 */
package je3.ch14.datatransfer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.TransferHandler;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 439-440. Simple
 * Drag-and-Drop customization: drag the foreground color from the first label
 * and drop it as the background color into the second one. Try it also using
 * the ShowBean program to display a JColorChooser component, with the argument
 * dragEnabled=true.
 * <p>
 * The current installation has a Run Configuration defined for both ColorDrag
 * and also ColorChooser, to help with testing. To test:
 * 
 * <pre>
 *   • Launch the "ColorDrag" Run Configuration.
 *   • Launch the "ColorChooser" Run Configuration.
 *   • Select a color from the ColorChooser.
 *   • Drag the color from the "Preview" panel at the bottom, to the "ColorDrag" frame.
 * </pre>
 */
public class ColorDrag {
	public static void main(String args[]) {
		// Create two JLabel objects:
		final JLabel label1 = new JLabel("Drag here to Change the Foreground Color");
		JLabel label2 = new JLabel("Drag here to Change the Background Color");

		/*
		 * Register TransferHandler objects on them: label1 transfers its foreground
		 * color and label2 transfers its background color.
		 */
		label1.setTransferHandler(new TransferHandler("foreground"));
		label2.setTransferHandler(new TransferHandler("background"));

		// Give label1 a foreground color other than the default.
		label1.setForeground(new Color(100, 100, 200));
		// Make label2 opaque so it displays its background color.
		label2.setOpaque(true);

		/**
		 * Now look for drag gestures over label1. When one occurs, tell the
		 * TransferHandler to begin a drag.
		 * <p>
		 * Exercise: modify this gesture recognition so that the drag doesn't begin
		 * until the mouse has moved 4 pixels. This helps to keep drags distinct from
		 * sloppy clicks. To do this, you'll need both a MouseListener and a
		 * MouseMotionListener.
		 */
		label1.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				TransferHandler handler = label1.getTransferHandler();
				handler.exportAsDrag(label1, e, TransferHandler.COPY);
			}
		});

		// Create a window, add the labels, and make it all visible.
		JFrame jFrame = new JFrame("ColorDrag");
		jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jFrame.getContentPane().setLayout(new FlowLayout());
		jFrame.getContentPane().add(label1);
		jFrame.getContentPane().add(label2);
		jFrame.setPreferredSize(new Dimension(300, 100));
		jFrame.pack();
		jFrame.setVisible(true);
		System.out.println("Launch the Run Configuration to get a \"ColorChooser\"");
	}
}
