package ch17;

import java.awt.*;
import java.awt.event.*;

/**
 * This function is not in the Learning Java manual. See the JavaDoc (mouse over
 * "Robot") for more info.
 */
public class RobotExample {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {

		Robot r = new Robot();
		r.mouseMove(35, 35);
		r.mousePress(InputEvent.BUTTON1_MASK);
		r.mouseRelease(InputEvent.BUTTON1_MASK);
		Thread.sleep(50);
		r.mousePress(InputEvent.BUTTON1_MASK);
		r.mouseRelease(InputEvent.BUTTON1_MASK);
	}
}
