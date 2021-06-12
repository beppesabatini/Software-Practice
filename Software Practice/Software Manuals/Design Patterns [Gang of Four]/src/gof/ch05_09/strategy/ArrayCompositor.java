package gof.ch05_09.strategy;

import gof.designpatterns.Strategy;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p.
 * 322. Part of the sample code illustrating the {@linkplain Strategy} design
 * pattern. In real life this compositor would break components into lines at
 * regular intervals. Here it's just a non-functional stub.</div>
 *
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class ArrayCompositor implements Compositor, Strategy {

	public ArrayCompositor(int interval) {

	}

	@Override
	public CompositionInfo compose() {
		System.out.println("In ArrayCompositor::compose(), adding regular line breaks");
		return (null);
	}

}
