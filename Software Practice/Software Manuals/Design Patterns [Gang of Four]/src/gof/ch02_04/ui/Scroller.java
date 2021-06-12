package gof.ch02_04.ui;

import gof.ch02_02.structure.GlyphImpl;
import gof.ch02_02.structure.Window;
import gof.designpatterns.Decorator;

/**
 * <div class="javadoc-text">Adapted from DesignPatterns [Gang of Four], p. 45.
 * A brief intro to the {@linkplain Decorator} pattern.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class Scroller<DataType extends GlyphImpl> extends MonoGlyph<DataType> {

	@Override
	public void drawGlyph(Window window) {
		window.drawGlyph(this);
		drawScroller(window);
	}

	public void drawScroller(Window window) {
		// Not implemented yet.
		System.out.println("Drawing the Border");
	}
}
