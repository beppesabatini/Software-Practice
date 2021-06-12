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
public class CompositorImpl implements Compositor {

	private Composition composition;

	@Override
	public void setComposition(Composition composition) {
		this.composition = composition;
	}

	@Override
	public void compose() {
		this.composite();
	}

	public void composite() {
		if (this.composition == null) {
			return;
		}
		// Not functional yet.
		System.out.println("Compositing...");
	}
}
