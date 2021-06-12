package gof.ch05_09.strategy;

import gof.designpatterns.Strategy;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p.
 * 321. Part of the sample code illustrating the {@linkplain Strategy} design
 * pattern. In real life, the simple compositor adds line breaks to text one
 * line at a time. Below, it's just a non-functional stub.</div>
 *
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class SimpleCompositor implements Compositor, Strategy {

	@Override
	public CompositionInfo compose() {
		System.out.println("In SimpleCompositor::compose(), adding line breaks the simplest way possible");
		return (null);
	}

}
