package ch20;

import java.awt.*;

/**
 * From Learning Java, 3rd Edition, p. 703. Prints a list of every font
 * currently installed on the user's hardware. See also the demonstration of the
 * "FontShow" class, in the current chapter, to see some of the these fonts
 * displayed.
 */
public class ShowFonts {
	public static void main(String[] args) {
		Font[] fonts;
		fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
		for (int i = 0; i < fonts.length; i++) {
			System.out.print(fonts[i].getFontName() + " : ");
			System.out.print(fonts[i].getFamily() + " : ");
			System.out.print(fonts[i].getName());
			System.out.println();
		}
	}
}
