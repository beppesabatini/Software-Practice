package gof.ch02_03.formatting;

import gof.designpatterns.Strategy;

/**
 * <div class="javadoc-text">Adapted from DesignPatterns [Gang of Four], p. 42.
 * An early introduction to the {@linkplain Strategy} design pattern.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class SimpleCompositor extends CompositorImpl implements Strategy {

	@Override
	public void compose() {
		this.composite();
	}

	@Override
	public void composite() {
		// Not functional yet.
		System.out.println("Compositing the simple case...");
	}
}
