package gof.ch04_06.flyweight;

import gof.ch04_02.bridge.Window;
import gof.ch04_06.flyweight.FlyweightSupport.Font;
import gof.designpatterns.Flyweight;

/**
 * <div class="javadoc-text">From Design Patterns [Gang of Four], p. 201. Sample
 * code to illustrate the {@linkplain Flyweight} design pattern. </div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
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
