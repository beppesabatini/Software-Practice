package gof.ch02_04.ui;

import gof.ch02_02.structure.Glyph;
import gof.ch02_02.structure.GlyphImpl;
import gof.ch02_02.structure.Window;
import gof.designpatterns.Decorator;

/**
 * <div class="javadoc-text">Adapted from DesignPatterns [Gang of Four], p. 45.
 * One class in the sample code for a brief intro to the
 * {@linkplain gof.designpatterns.Decorator Decorator} pattern. MonoGlyph is a
 * <b>Transparent Enclosure</b> for one particular Glyph instance.</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch02_04/ui/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class MonoGlyph<DataType extends Glyph> extends GlyphImpl implements Decorator {

	private Glyph enclosedGlyph;

	@Override
	public void drawGlyph(Window window) {
		this.enclosedGlyph.drawGlyph(window);
	}
}
