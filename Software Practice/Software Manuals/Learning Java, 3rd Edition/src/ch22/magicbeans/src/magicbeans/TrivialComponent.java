package ch22.magicbeans.src.magicbeans;

import javax.swing.*;
import java.awt.Graphics;

public class TrivialComponent extends JComponent {

	private static final long serialVersionUID = -8754248473660104244L;

	public void paintComponent(Graphics g) {
		g.setColor(getBackground());
		g.fillRect(0, 0, getSize().width, getSize().height);
	}

}
