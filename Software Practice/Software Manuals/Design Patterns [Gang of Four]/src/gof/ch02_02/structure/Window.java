package gof.ch02_02.structure;

import gof.designpatterns.Composite;

/**
 * <div class="javadoc-text">Adapted from DesignPatterns [Gang of Four], pp. 20,
 * 39. An early introduction to the {@linkplain Composite} design pattern.</div>
 * 
 * <pre></pre>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class Window {

	private Rectangle rectangle;

	public Window() {
	}

	public Window(Rectangle rectangle) {
		this.rectangle = rectangle;
	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}

	public float getArea() {
		if (rectangle == null) {
			return (0);
		}
		float area = rectangle.getArea();
		return (area);
	}

	/* From p.55.*/
	public void drawRectangle() {
		if (this.rectangle == null) {
			return;
		}
		
		// Not functional yet.
		System.out.println("Drawing rectangle: " + this.rectangle);
	}

	public void drawCharacter(Character character) {
		// Not functional yet. This seems like it would never work.
		System.out.println("Drawing character: " + character);
	}

	public void drawGlyph(Glyph glyph) {
		/*
		 * Not functional yet. The catch-all for glyphs which don't have a more
		 * specifically-designed drawing function yet. Like Java's generic all-purpose
		 * toString() function, this won't work very well.
		 */
		System.out.println("Drawing glyph: " + glyph);
	}
}
