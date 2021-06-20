package gof.ch02_03.formatting;

import java.util.List;
import java.util.Map;

import gof.ch02_02.structure.Glyph;
import gof.ch02_02.structure.GlyphImpl;

/**
 * <div class="javadoc-text">Adapted from DesignPatterns [Gang of Four], p. 42.
 * One class in the sample code for an early introduction to the
 * {@linkplain gof.designpatterns.Strategy Strategy} design pattern. A
 * Composition aggregates zero-to-N Glyphs, and also aggregates any needed
 * Compositors.</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch02_03/formatting/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class Composition extends GlyphImpl {

	private List<Glyph> children;
	@SuppressWarnings("unused")
	private Map<String, Compositor> compositors;

	@Override
	public void insertGlyph(Glyph glyph, int index) {
		children.add(index, glyph);
	}

}
