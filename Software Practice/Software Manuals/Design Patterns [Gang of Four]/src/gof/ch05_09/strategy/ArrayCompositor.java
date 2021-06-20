package gof.ch05_09.strategy;

import gof.designpatterns.Strategy;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p.
 * 322. This class is part of the sample code illustrating the
 * {@linkplain gof.designpatterns.Strategy Strategy} design pattern. The
 * ArrayCompositor is one of three algorithms or "Strategies." In real life, an
 * ArrayCompositor breaks components into lines at regular intervals. Here, it's
 * just a stub.</div>
 *
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch05_09/strategy/UML%20Diagram.jpg"
 * /> </div>
 * 
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
