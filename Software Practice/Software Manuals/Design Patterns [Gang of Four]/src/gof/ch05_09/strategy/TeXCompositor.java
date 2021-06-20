package gof.ch05_09.strategy;

import gof.designpatterns.Strategy;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p.
 * 322. This class is part of the sample code illustrating the
 * {@linkplain gof.designpatterns.Strategy Strategy} design pattern. In real
 * life, LaTex examines text one <i>paragraph</i> at a time, taking into account
 * the components' size and stretchability. It also tries to give an even
 * "color" to the paragraph by minimizing the whitespace between components.
 * However, below it's just a stub.</div>
 *
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch05_09/strategy/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class TeXCompositor implements Compositor, Strategy {

	@Override
	public CompositionInfo compose() {
		System.out.println("In TeXCompositor::compose(), adding line breaks with the TeX strategy");
		return (null);
	}

}
