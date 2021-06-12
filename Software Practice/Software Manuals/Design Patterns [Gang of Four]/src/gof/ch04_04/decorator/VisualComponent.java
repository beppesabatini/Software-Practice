package gof.ch04_04.decorator;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p.
 * 180. Part of the Sample Code for the {@linkplain gof.designpatterns.Decorator
 * Decorator} design pattern.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public abstract class VisualComponent {

	protected VisualComponent() {

	}

	abstract void draw();

	abstract void resize();

}
