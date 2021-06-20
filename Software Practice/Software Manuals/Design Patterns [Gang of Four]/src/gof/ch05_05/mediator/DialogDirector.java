package gof.ch05_05.mediator;

import gof.designpatterns.Mediator;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p.
 * 278. Part of the sample code illustrating the
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
