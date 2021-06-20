package gof.ch05_09.strategy;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p.
 * 321, but totally changed. This class is part of the sample code illustrating
 * the {@linkplain gof.designpatterns.Strategy Strategy} design pattern. In this
 * example, an end-user simply has his choice of three different algorithms for
 * calculating where to place line breaks in a text document. Each algorithm is
 * defined and encapsulated in a Strategy object.</div>
 *
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch05_09/strategy/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public interface Compositor {

	public CompositionInfo compose();
}
