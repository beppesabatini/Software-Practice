package gof.ch04_06.flyweight;

import gof.designpatterns.Flyweight;

/**
 * <div style="width: 580px;">From Design Patterns [Gang of Four], p. 201.
 * Sample code to illustrate the {@linkplain Flyweight} design pattern. This
 * Character class is the "flyweight" object which can be supported in great
 * numbers.
 * <p/>
 * The sample code in the manual seems to be a bad example, because simply
 * storing the character itself will usually take up less space than storing it
 * in a lightweight object and maintaining a memory pointer to the object. The
 * Flyweight pattern may be more helpful in a different scenario.
 * <p/>
 * The manual (p.206) adds a different example, a GUI library in which every
 * widget has a corresponding WidgetLayout object. In that situation, the
 * WidgetLayout objects are implemented as immutable Flyweights, and extrinsic
 * data is passed to them. For further research, a Refactoring
 * <a href="https://refactoring.guru/design-patterns/flyweight">website</a> also
 * has an example of a video game problem for which the Flyweight pattern seems
 * to be a good fit.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class Character extends Glyph implements Flyweight {

	private char charcode;

	public Character(char charcode) {
		this.charcode = charcode;
		System.out.println("Initializing charcode: " + this.charcode);
	}

}
