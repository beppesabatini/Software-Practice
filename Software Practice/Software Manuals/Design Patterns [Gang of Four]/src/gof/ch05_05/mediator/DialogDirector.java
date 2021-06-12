package gof.ch05_05.mediator;

import gof.designpatterns.Mediator;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p.
 * 278. Part of the sample code illustrating the {@linkplain Mediator} design
 * pattern.</div>
 *
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public abstract class DialogDirector implements Mediator {

	protected DialogDirector() {
		System.out.println(" -- Initializing the root abstract DialogDirector");
	}

	protected abstract void createWidgets();

	public void showDialog() {
		System.out.println(" -- DialogDirector is launching the dialog");
	}

	public abstract void widgetChanged(Widget widget);

	public void finalize() {
		// Clean-up before deallocation
	}
}
