package gof.ch02_02.structure;

import gof.designpatterns.Composite;

/**
 * <div class="javadoc-text">Adapted from DesignPatterns [Gang of Four], p. 39.
 * Part of the sample code for an early introduction to the
 * {@linkplain gof.designpatterns.Composite Composite} design pattern. "Glyph"
 * is a generalized term for characters, images, rows, columns, and most other
 * elements of a word processing programming being designed.
 * <p/>
 * The Glyph object has some weaknesses from the standpoint of data modeling or
 * interface design. Every object is a Glyph, which is so broadly defined it's
 * not much different from every object being handled as an "Object."
 * Improvements to the Glyph will be made in upcoming chapters.</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch02_02/structure/UML%20Diagram.jpg"
 * /> </div>
 * 
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

	/**
	 * This is not meaningful for a {@linkplain gof.designpatterns.Singleton
	 * Singleton}.
	 */
	public Glyph getParent();
}
