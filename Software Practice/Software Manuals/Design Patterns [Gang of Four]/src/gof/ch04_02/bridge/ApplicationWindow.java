package gof.ch04_02.bridge;

import gof.designpatterns.Bridge;

/**
 * <div class="javadoc-text">From Design Patterns [Gang of Four], pp. 156-159.
 * An element in the Sample Code for the {@linkplain Bridge} design pattern. See
 * the {@linkplain Window} class for more detail.</div>
 *
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class ApplicationWindow extends Window {

	public ApplicationWindow(View view) {
		super(view);
	}

	@Override
	public void drawContents() {
		System.out.println("Drawing an application window");
		View view = getView();
		view.drawOn(this);
	}
}
