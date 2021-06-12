package gof.ch04_04.decorator;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p.
 * 181. An element in the sample code for the
 * {@linkplain gof.designpatterns.Decorator Decorator} design pattern.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class ScrollDecorator extends Decorator implements gof.designpatterns.Decorator {

	public ScrollDecorator(VisualComponent visualComponent) {
		super(visualComponent);
	}

	@Override
	public void draw() {
		super.draw();
		drawScrollBar();
	}

	private void drawScrollBar() {
		System.out.println("Drawing a scroll bar");
	}
}
