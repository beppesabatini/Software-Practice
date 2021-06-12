package gof.ch02_04.ui;

import gof.ch02_02.structure.GlyphImpl;
import gof.ch02_02.structure.Window;
import gof.designpatterns.Decorator;

/**
 * <div class="javadoc-text">Adapted from DesignPatterns [Gang of Four], p. 45.
 * A brief intro to the {@linkplain Decorator} pattern. The manual notes that
 * the drawGlyph() function effectively <i>extends</i> the parent class
 * operation to draw the border (in contrast to merely <i>replacing</i>
 * it.).</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class Border<DataType extends GlyphImpl> extends MonoGlyph<DataType> {
	@Override
	public void drawGlyph(Window window) {
		window.drawGlyph(this);
		drawBorder(window);
	}

	public void drawBorder(Window window) {
		// Not implemented yet.
		System.out.println("Drawing the Border");
	}
}
