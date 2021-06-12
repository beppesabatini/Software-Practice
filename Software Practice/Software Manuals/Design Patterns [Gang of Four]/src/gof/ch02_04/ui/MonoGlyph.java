package gof.ch02_04.ui;

import gof.ch02_02.structure.Glyph;
import gof.ch02_02.structure.GlyphImpl;
import gof.ch02_02.structure.Window;
import gof.designpatterns.Decorator;

/**
 * <div class="javadoc-text">Adapted from DesignPatterns [Gang of Four], p. 45.
 * A brief intro to the {@linkplain Decorator} pattern.
 * <p/>
 * MonoGlyph is a <b>Transparent Enclosure</b> for one particular Glyph instance. The
 * UML diagram in the manual uses a diamond-based arrow from MonoGlyph to Glyph,
 * to indicate that the solo contained Glyph is apparently an aggregation of
 * one.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class MonoGlyph<DataType extends Glyph> extends GlyphImpl implements Decorator {

	private Glyph enclosedGlyph;

	@Override
	public void drawGlyph(Window window) {
		this.enclosedGlyph.drawGlyph(window);
	}
}
