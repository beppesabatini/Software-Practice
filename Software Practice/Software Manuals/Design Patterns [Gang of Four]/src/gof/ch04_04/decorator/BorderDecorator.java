package gof.ch04_04.decorator;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p.
 * 181. Part of the sample code for the {@linkplain gof.designpatterns.Decorator
 * Decorator} design pattern.</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch04_04/decorator/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class BorderDecorator extends Decorator implements gof.designpatterns.Decorator {

	private int width;

	public BorderDecorator(VisualComponent visualComponent, int width) {
		super(visualComponent);
		this.width = width;
	}

	@Override
	public void draw() {
		super.draw();
		drawBorder(width);
	}

	private void drawBorder(int width) {
		System.out.println("Drawing a border");
	}

}
