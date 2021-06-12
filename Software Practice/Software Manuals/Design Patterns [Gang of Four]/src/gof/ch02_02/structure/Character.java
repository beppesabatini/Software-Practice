package gof.ch02_02.structure;

import gof.designpatterns.Composite;

/**
 * <div class="javadoc-text">Adapted from DesignPatterns [Gang of Four], p. 38.
 * An early introduction to the {@linkplain Composite} design pattern.
 * <p/>
 * This is not the standard Java complex data type Character, it overrides it in
 * just this one package.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
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
