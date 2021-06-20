package gof.ch02_02.structure;

/**
 * <div class="javadoc-text">Adapted from DesignPatterns [Gang of Four], p. 38.
 * Part of the sample code for an early introduction to the
 * {@linkplain gof.designpatterns.Composite Composite} design pattern. This is
 * not the standard java.util.Character; this GOF version overrides it for just
 * this one package.
 * <p/>
 * Almost all the code in the Gang of Four manual is pseudo-code, stubs and
 * dummy classes, intended to illustrate the 23 basic GOF design patterns.
 * </div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch02_02/structure/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class Character extends GlyphImpl {

	private char character;

	public char getCharacter() {
		return character;
	}

	public void setCharacter(char character) {
		this.character = character;
	}

	public void drawCharacter(Window window) {
		window.drawCharacter(this);
	}

	public boolean characterIntersects(Point point) {
		boolean insersects = this.bounds.intersects(point);
		return (insersects);
	}
}
