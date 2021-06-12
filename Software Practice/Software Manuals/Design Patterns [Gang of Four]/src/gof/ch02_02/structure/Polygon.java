package gof.ch02_02.structure;

import gof.designpatterns.Composite;

import java.util.List;

/**
 * <div class="javadoc-text">Adapted from DesignPatterns [Gang of Four], p. 38.
 * An early introduction to the {@linkplain Composite} design pattern.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class Polygon extends GlyphImpl {

	List<Point> vertices;

	@Override
	public void drawGlyph(Window window) {
		window.drawGlyph(this);
	}

	public boolean intersects(Point point) {
		// Not functional yet.
		return (false);
	}
}
