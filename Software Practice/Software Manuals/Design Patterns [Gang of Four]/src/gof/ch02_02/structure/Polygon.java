package gof.ch02_02.structure;

import java.util.List;

/**
 * <div class="javadoc-text">Adapted from DesignPatterns [Gang of Four], p. 38.
 * Part of the sample code for an early introduction to the
 * {@linkplain gof.designpatterns.Composite Composite} design pattern.</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch02_02/structure/UML%20Diagram.jpg"
 * /> </div>
 * 
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
