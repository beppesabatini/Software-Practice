package gof.ch05_05.mediator;

import gof.ch04_07.proxy.GraphicSupport.MouseEvent;
import gof.designpatterns.Mediator;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], pp.
 * 279-280. Part of the sample code illustrating the
 * {@linkplain gof.designpatterns.Mediator Mediator} design pattern. In the
 * example here, the Mediator acts like Control Central, to coordinate different
 * widgets in a Font Select dialog.</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch05_05/mediator/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class Button extends Widget implements Mediator {

	public Button(DialogDirector dialogDirector) {
		super(dialogDirector);
	}

	public void setText(final String defaultText) {
		System.out.println(" -- In Button, setting the default text: " + defaultText);
	}

	@Override
	public void handleMouse(MouseEvent mouseEvent) {
		System.out.println(" -- Button hears a mouse click");
		this.changed();
	}
}
