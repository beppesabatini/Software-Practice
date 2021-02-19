package ch17;

import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

/**
 * From p. 596-597. This widget is supposed to demonstrate week-long jumps in a
 * Date Selector, but it does not seem to behave as described.
 */
public class DateSelector {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Date Selector 1.0");

		Calendar now = Calendar.getInstance();
		Calendar earliest = (Calendar) now.clone();
		earliest.add(Calendar.MONTH, -6);
		Calendar latest = (Calendar) now.clone();
		latest.add(Calendar.MONTH, 6);
		SpinnerModel model = new SpinnerDateModel(now.getTime(), earliest.getTime(), latest.getTime(),
				Calendar.WEEK_OF_YEAR);
		final JSpinner spinner = new JSpinner(model);

		model.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				System.out.println(((SpinnerDateModel) e.getSource()).getDate());
			}
		});

		frame.getContentPane().add("North", new JLabel("Choose a week"));
		frame.getContentPane().add("Center", spinner);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
