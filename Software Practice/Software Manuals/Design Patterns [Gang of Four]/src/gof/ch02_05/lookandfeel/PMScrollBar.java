package gof.ch02_05.lookandfeel;

import gof.ch02_02.structure.GlyphImpl;

/**
 * <div class="javadoc-text">Adapted from DesignPatterns [Gang of Four], p. 50.
 * One class in the sample code for a brief intro to the
 * {@linkplain gof.designpatterns.AbstractFactory AbstractFactory} design
 * pattern. PM stands for Presentation Manager, an old widget library.</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch02_05/lookandfeel/Widget%20UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class PMScrollBar extends GlyphImpl implements ScrollBar {

	@Override
	public void scrollTo(int position) {
		// Not functional yet.
		System.out.println("Scrolling to: " + position);
	}

}
