package gof.ch02_04.ui;

import gof.ch02_02.structure.GlyphImpl;
import gof.ch02_02.structure.Window;

/**
 * <div class="javadoc-text">Adapted from DesignPatterns [Gang of Four], p. 45.
 * One class in the sample code for a brief intro to the
 * {@linkplain gof.designpatterns.Decorator Decorator} pattern. The manual notes
 * that the drawGlyph() function effectively <i>extends</i> the parent class
 * operation to draw the border (in contrast to merely <i>replacing</i>
 * it.).</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch02_04/ui/UML%20Diagram.jpg"
 * /> </div>
 * 
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
