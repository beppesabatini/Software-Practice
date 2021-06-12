package gof.ch02_03.formatting;

import java.util.List;
import java.util.Map;

import gof.ch02_02.structure.Glyph;
import gof.ch02_02.structure.GlyphImpl;
import gof.designpatterns.Strategy;

/**
 * <div class="javadoc-text">Adapted from DesignPatterns [Gang of Four], p. 42.
 * An early introduction to the {@linkplain Strategy} design pattern. A
 * Composition aggregates zero to N Glyphs, and also aggregates any needed
 * Compositors.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
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
