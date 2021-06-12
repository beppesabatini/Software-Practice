package gof.ch05_09.strategy;

import gof.designpatterns.Strategy;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p.
 * 322. Part of the sample code illustrating the {@linkplain Strategy} design
 * pattern. In real life, LaTex examines test a <i>paragraph</i> at a time,
 * taking into account the components' size and stretchability. It also tries to
 * give an even "color" to the paragraph by minimizing the whitespace between
 * components. However, below, it's just a non-functional stub!</div>
 *
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class TeXCompositor implements Compositor, Strategy {

	@Override
	public CompositionInfo compose() {
		System.out.println("In TeXCompositor::compose(), adding line breaks with the TeX strategy");
		return (null);
	}

}
