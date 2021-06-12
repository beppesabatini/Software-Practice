package gof.ch02_02.structure;

import gof.designpatterns.Composite;

/**
 * <div class="javadoc-text">Adapted from DesignPatterns [Gang of Four], p. 39.
 * An early introduction to the {@linkplain Composite} design pattern.
 * <p/>
 * Surprisingly, as represented in the manual, this actually isn't very good
 * data modeling or interface design. Every object is a Glyph, which is so
 * broadly defined it's not much different from every object being defined as
 * "Object." Structural elements, characters, and images are all "Glyphs."</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public interface Glyph extends Composite {

	public void drawGlyph(Window window);

	// This won't do you much good without a "getBounds()" function.
	public void setBounds(Rectangle rectangle);

	/**
	 * This function seems to take the current glyph itself as an argument, which
	 * apparently misses the whole point of OOP. Or, more likely, it's something
	 * such as a row of words on a page (a row is also a glyph), with one more
	 * character being inserted. It's a good example of the core problem: if
	 * everything is a glyph, programmers lose the benefits of strict type-checking.
	 */
	public void insertGlyph(Glyph glyph, int position);

	/*
	 * This will not work. If a developer is trying to remove a letter 'E' from a
	 * row of characters, how will the system know which one to remove?
	 */
	public void removeGlyph(Glyph glyph);

	/*
	 * This implies that the children are in an array, or another structure in which
	 * the order is defined by integer order, which is too implementation-specific.
	 */
	public Glyph getChild(int index);

	// This is not meaningful for a Singleton.
	public Glyph getParent();
}
