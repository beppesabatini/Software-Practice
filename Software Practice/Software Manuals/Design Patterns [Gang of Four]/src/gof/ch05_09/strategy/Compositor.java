package gof.ch05_09.strategy;

import gof.designpatterns.Strategy;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p.
 * 321, but totally changed. Part of the sample code illustrating the
 * {@linkplain Strategy} design pattern. In this example, an end-user simply has
 * his choice of three different algorithms for calculating where to place line
 * breaks in a text document. Each algorithm is defined and encapsulated in a
 * Strategy object.</div>
 *
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public interface Compositor {

	public CompositionInfo compose();
}
