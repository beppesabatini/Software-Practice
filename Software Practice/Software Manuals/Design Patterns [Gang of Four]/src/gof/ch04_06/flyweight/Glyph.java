package gof.ch04_06.flyweight;

import gof.ch04_02.bridge.Window;
import gof.ch04_06.flyweight.FlyweightSupport.Font;
import gof.designpatterns.Flyweight;

/**
 * <div class="javadoc-text">From Design Patterns [Gang of Four], p. 201. A
 * third version of the Glyph, unrelated to the two versions in earlier
 * chapters. One class in the sample code to illustrate the
 * {@linkplain gof.designpatterns.Flyweight Flyweight} design pattern. See the
 * local {@linkplain gof.ch04_06.flyweight.Character Character} object for more
 * detail.</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch04_06/flyweight/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class Glyph implements Flyweight {

	protected Glyph() {
		// Stub
	}

	public void draw(Window window, GlyphContext glyphContext) {
		// Stub
	}

	public void setFont(Font font, GlyphContext glyphContext) {
		// Stub
	}

	public Font getFont(GlyphContext glyphContext) {
		// Stub
		return (null);
	}

	public boolean isDone(GlyphContext glyphContext) {
		// Stub
		return (false);
	}

	public Glyph getCurrent(GlyphContext glyphContext) {
		// Stub
		return (null);
	}

	public void insert(Glyph glyph, GlyphContext glyphContext) {
		// Stub
	}

	public void remove(Glyph glyph, GlyphContext glyphContext) {
		// Stub
	}

	@Override
	public void finalize() {
		// Clean-up before deallocation.
	}
}
