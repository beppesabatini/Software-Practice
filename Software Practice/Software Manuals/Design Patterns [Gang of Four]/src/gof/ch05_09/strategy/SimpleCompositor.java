package gof.ch05_09.strategy;

import gof.designpatterns.Strategy;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p.
 * 321. This class is part of the sample code illustrating the
 * {@linkplain gof.designpatterns.Strategy Strategy} design pattern. In real
 * life, a SimpleCompositor adds line breaks to text one line at a time.</div>
 *
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch05_09/strategy/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class SimpleCompositor implements Compositor, Strategy {

	@Override
	public CompositionInfo compose() {
		System.out.println("In SimpleCompositor::compose(), adding line breaks the simplest way possible");
		return (null);
	}

}
