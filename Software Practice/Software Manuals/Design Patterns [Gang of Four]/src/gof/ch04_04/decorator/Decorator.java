package gof.ch04_04.decorator;

import gof.ch04_02.bridge.ApplicationWindow;
import gof.ch04_02.bridge.View;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p.
 * 181. An element in the sample code for the
 * {@linkplain gof.designpatterns.Decorator Decorator} design pattern. This
 * provides the user interface and some default values for a variety of
 * Decorators.</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch04_04/decorator/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
abstract class Decorator extends VisualComponent implements gof.designpatterns.Decorator {

	private VisualComponent visualComponent;

	public Decorator(VisualComponent visualComponent) {
		this.visualComponent = visualComponent;
	}

	// Defaults for the subclasses:

	@Override
	public void draw() {
		this.visualComponent.draw();
	}

	@Override
	public void resize() {
		this.visualComponent.resize();
	}

	/**
	 * From p. 182. Theoretically a typical usage of the Decorator pattern. All the
	 * classes involved are just stubs, though.
	 */
	public static class Demo {
		public static void main(String[] args) {
			BorderDecorator textWithBorder = new BorderDecorator(new TextView(), 1);
			View windowContents = new View();
			windowContents.setVisualComponent(textWithBorder);
			ApplicationWindow applicationWindow = new ApplicationWindow(windowContents);
			System.out.println("This demo is only a stub.");
			applicationWindow.drawContents();
		}
	}
}
