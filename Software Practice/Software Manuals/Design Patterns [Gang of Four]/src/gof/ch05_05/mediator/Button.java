package gof.ch05_05.mediator;

import gof.ch04_07.proxy.GraphicSupport.MouseEvent;
import gof.designpatterns.Mediator;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], pp.
 * 279-280. Part of the sample code illustrating the {@linkplain Mediator}
 * design pattern.</div>
 *
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
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
