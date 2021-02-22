package ch09;

/**
 * This code is not included in Learning Java chap. 9. This does not run. Note
 * that applets are no longer supported by Java.
 */
public class Clock extends UpdateApplet {

	private static final long serialVersionUID = 1254350238918281618L;

	public void paint(java.awt.Graphics graphics) {
		graphics.drawString(new java.util.Date().toString(), 10, 25);
	}
}
