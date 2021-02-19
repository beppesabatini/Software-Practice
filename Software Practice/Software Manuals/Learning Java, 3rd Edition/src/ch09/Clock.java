package ch09;

// Does not run. Note applets are no longer supported by Java.
// This code not included in Learning Java ch. 9. 

public class Clock extends UpdateApplet {

	private static final long serialVersionUID = 1254350238918281618L;

	public void paint(java.awt.Graphics graphics) {
		graphics.drawString(new java.util.Date().toString(), 10, 25);
	}
}
