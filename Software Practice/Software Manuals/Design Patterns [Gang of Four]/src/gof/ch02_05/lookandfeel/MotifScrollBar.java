package gof.ch02_05.lookandfeel;

import gof.ch02_02.structure.GlyphImpl;
import gof.designpatterns.AbstractFactory;

/**
 * <div class="javadoc-text">Adapted from DesignPatterns [Gang of Four], p. 50.
 * A brief intro to the {@linkplain AbstractFactory} design pattern.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="Widget UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class MotifScrollBar extends GlyphImpl implements ScrollBar {

	@Override
	public void scrollTo(int position) {
		// Not functional yet.
		System.out.println("Scrolling to: " + position);
	}

}
