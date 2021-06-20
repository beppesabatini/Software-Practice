package gof.ch04_04.decorator;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p.
 * 181. An element in the sample code for the
 * {@linkplain gof.designpatterns.Decorator Decorator} design pattern.</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch04_04/decorator/UML%20Diagram.jpg"
 * /> </div>
 * 
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
