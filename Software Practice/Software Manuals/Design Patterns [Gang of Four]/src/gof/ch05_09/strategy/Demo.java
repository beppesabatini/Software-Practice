package gof.ch05_09.strategy;

import gof.designpatterns.Strategy;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p.
 * 322. Part of the sample code illustrating the {@linkplain Strategy} design
 * pattern. In this example, an end-user simply has his choice of three
 * different algorithms for calculating where to place line breaks in a text
 * document. Each algorithm is defined and encapsulated in a Strategy
 * object.</div>
 *
 * <pre></pre>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class Demo {

	public static void main(String args[]) {
		Composition quick = new Composition(new SimpleCompositor());
		quick.repair();
		Composition slick = new Composition(new TeXCompositor());
		slick.repair();
		Composition iconic = new Composition(new ArrayCompositor(100));
		iconic.repair();
	}
}
