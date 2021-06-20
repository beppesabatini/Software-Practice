package gof.ch02_02.structure;

import java.util.ArrayList;
import java.util.List;

import gof.designpatterns.Composite;

/**
 * <div class="javadoc-text">Adapted from DesignPatterns [Gang of Four], p. 38.
 * Part of the sample code for an early introduction to the
 * {@linkplain gof.designpatterns.Composite Composite} design pattern.
 * <p/>
 * A Row aggregates 0-to-N number of glyphs, typically instances of
 * Character.</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch02_02/structure/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class Row extends GlyphImpl implements Composite {

	private List<Glyph> children;

	public Row() {
		this.children = new ArrayList<Glyph>();
	}

	public void drawGlyph() {
		// Not functional yet.
		System.out.println("Drawing Glyph");
	}

	/*
	 * The manual gives as pseudocode: "For each child in the children structure, if
	 * child.intersects(point) return true." Actually though, the Row should be
	 * maintaining its own bounds value.
	 */
	public boolean intersects(Point point) {
		boolean intersects = this.bounds.intersects(point);
		return (intersects);
	}

	// Insert the glyph into the children collection at the index position.
	public void insert(Glyph glyph, int index) {
		children.add(index, glyph);
		// The bounds value should be updated here also.
	}
}
