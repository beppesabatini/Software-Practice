package gof.ch02_08.spelling;

import java.util.List;

import gof.ch02_02.structure.Rectangle;
import gof.ch02_02.structure.Window;

/**
 * <div class="javadoc-text">Adapted from DesignPatterns [Gang of Four], pp. 39,
 * 67. A brief intro to the {@linkplain gof.designpatterns.Iterator Iterator}
 * design pattern. The Glyph has been modified here to eliminate the dubious
 * index into the collection of children. It's not worth porting all the old
 * classes to the new version of the Glyph; most of them are just stubs.</div>
 * 
 * <pre></pre>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class GlyphImpl implements Glyph {

	Glyph parent;
	List<Glyph> children;
	Rectangle bounds;

	@Override
	public void drawGlyph(Window window) {
		window.drawGlyph(new gof.ch02_02.structure.GlyphImpl());
	}

	@Override
	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	@Override
	public void insertGlyph(Glyph glyph, int index) {
		children.add(index, glyph);
	}

	@Override
	public void removeGlyph(Glyph glyph) {
		children.remove(glyph);
	}

	/*
	 * This is just a dummy function to satisfy the interface, which should be
	 * overridden in any subclass of Glyph which needs it. This is a very odd bit of
	 * data modeling, but this is how the manual wants it done.
	 */
	@Override
	public Iterator<?> createIterator(Object root) {
		System.out.println("Warning: this NullIterator does almost nothing.");
		String message = "";
		message += "The GlymphImpl:createInterator() function needs to be ";
		message += "overridden for any subclass which needs it.";
		System.out.println(message);
		Iterator<Glyph> iterator = new NullIterator<Glyph>();
		return (iterator);
	}

	@Override
	public Glyph getParent() {
		return (parent);
	}
}
