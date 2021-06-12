package gof.ch02_02.structure;

import java.util.List;
import gof.designpatterns.Composite;

/**
 * <div class="javadoc-text">Adapted from DesignPatterns [Gang of Four], pp.
 * 38-39. An early introduction to the {@linkplain Composite} design
 * pattern.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class GlyphImpl implements Glyph, Composite {

	Glyph parent;
	List<Glyph> children;
	Rectangle bounds;

	@Override
	public void drawGlyph(Window window) {
		window.drawGlyph(this);
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

	@Override
	public Glyph getChild(int index) {
		Glyph child = children.get(index);
		return (child);
	}

	@Override
	public Glyph getParent() {
		return (parent);
	}
}
