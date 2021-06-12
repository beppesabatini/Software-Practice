package gof.ch04_06.flyweight;

import gof.ch04_06.flyweight.FlyweightSupport.*;
import gof.designpatterns.Flyweight;

/**
 * <div class="javadoc-text">From Design Patterns [Gang of Four], pp. 204-205.
 * An element in the sample code used to illustrate the {@linkplain Flyweight}
 * pattern.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class GlyphFactory implements Flyweight {

	public final static int NUMBER_CHAR_CODES = 128;

	private Character[] characters = new Character[NUMBER_CHAR_CODES];

	public GlyphFactory() {
		// We don't really need to do this, the compiler does it.
		for (int i = 0; i < NUMBER_CHAR_CODES; i++) {
			characters[i] = null;
		}
		System.out.println("Initialized the characters array: " + this.characters);
	}

	public Character createCharacter(char charcode) {
		if (this.characters[(int) charcode] == null) {
			this.characters[(int) charcode] = new Character(charcode);
		}
		return (this.characters[(int) charcode]);
	}

	public Row createRow() {
		return (new Row());
	}

	public Column createColumn() {
		return (new Column());
	}

	@Override
	public void finalize() {
		// Called before deallocation.
	}
}
