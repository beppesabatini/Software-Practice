package ch19;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * From Learning Java, 3rd Edition, p. 671. This demonstrates the Card Layout,
 * which is something like a thin deck of cards which can be shuffled. The
 * manual recommends using the JTabbedPane component instead.
 */
public class Card extends JPanel {

	private static final long serialVersionUID = 5386996500984448998L;

	CardLayout cards = new CardLayout();

	public Card() {
		setLayout(cards);
		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cards.next(Card.this);
			}
		};
		JButton button;
		button = new JButton("one");
		button.addActionListener(listener);
		add(button, "one");
		button = new JButton("two");
		button.addActionListener(listener);
		add(button, "two");
		button = new JButton("three");
		button.addActionListener(listener);
		add(button, "three");
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Card");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(200, 200);
		frame.setLocation(200, 200);
		frame.setContentPane(new Card());
		frame.setVisible(true);
	}
}
