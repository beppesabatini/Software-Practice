package gof.ch02_05.lookandfeel;

import gof.designpatterns.AbstractFactory;

/**
 * <div class="javadoc-text">Adapted from DesignPatterns [Gang of Four], p. 50.
 * A brief intro to the {@linkplain AbstractFactory} design pattern. PM stands
 * for Presentation Manager.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="Factory UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class PMFactory implements GUIFactory {

	@Override
	public PMScrollBar createScrollBar() {
		return new PMScrollBar();
	}

	@Override
	public PMButton createButton() {
		return new PMButton();
	}

	@Override
	public PMMenu createMenu() {
		return new PMMenu();
	}

}
