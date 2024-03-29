package gof.ch02_03.formatting;

import gof.designpatterns.Strategy;

/**
 * <div class="javadoc-text">Adapted from DesignPatterns [Gang of Four], p. 42.
 * One class in the sample code for an early introduction to the
 * {@linkplain gof.designpatterns.Strategy Strategy} design pattern. In real
 * life, LaTex examines text a <i>paragraph</i> at a time, taking into account
 * the components' size and stretchability. It also tries to give an even
 * "color" to the paragraph by minimizing the whitespace between
 * components.</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch02_03/formatting/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class TeXCompositor extends CompositorImpl implements Strategy {

	@Override
	public void compose() {
		this.composite();
	}

	@Override
	public void composite() {
		// Not functional yet.
		System.out.println("Compositing LaTex...");
	}
}
