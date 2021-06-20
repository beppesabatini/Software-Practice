package gof.ch04_02.bridge;

import gof.ch04_04.decorator.VisualComponent;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], pp.
 * 156-159. A "View" is a representation of a window's contents. This class is
 * an element in the sample code for the {@linkplain gof.designpatterns.Bridge
 * Bridge} design pattern. See the local {@linkplain Window} class for more
 * detail.</div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class View {

	private VisualComponent visualComponent;

	public VisualComponent getVisualComponent() {
		return visualComponent;
	}

	public void setVisualComponent(VisualComponent visualComponent) {
		this.visualComponent = visualComponent;
	}

	public void drawOn(Window window) {
		// Draw this view on the specified window.
	};
}
