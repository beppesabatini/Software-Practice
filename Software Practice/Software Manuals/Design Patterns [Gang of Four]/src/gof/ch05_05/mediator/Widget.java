package gof.ch05_05.mediator;

import gof.ch04_07.proxy.GraphicSupport.MouseEvent;
import gof.designpatterns.Mediator;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p.
 * 278. Part of the sample code illustrating the {@linkplain Mediator} design
 * pattern.</div>
 *
 * <pre></pre>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public abstract class Widget {

	private DialogDirector dialogDirector;

	public Widget(DialogDirector dialogDirector) {
		this.dialogDirector = dialogDirector;
	}

	public void changed() {
		System.out.println(" -- Widget tells the DialogDirector (the Mediator) something has changed");
		dialogDirector.widgetChanged(this);
	}

	public void handleMouse(MouseEvent mouseEvent) {
		System.out.println("Widget hears a mouse click");
	}
}
