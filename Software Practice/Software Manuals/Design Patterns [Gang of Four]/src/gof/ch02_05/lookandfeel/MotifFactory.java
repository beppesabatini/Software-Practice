package gof.ch02_05.lookandfeel;

import gof.designpatterns.AbstractFactory;

/**
 * <div class="javadoc-text">Adapted from DesignPatterns [Gang of Four], p. 50.
 * A brief intro to the {@linkplain AbstractFactory} design pattern.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="Factory UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class MotifFactory implements GUIFactory {

	@Override
	public MotifScrollBar createScrollBar() {
		return new MotifScrollBar();
	}

	@Override
	public MotifButton createButton() {
		return new MotifButton();
	}

	@Override
	public MotifMenu createMenu() {
		return new MotifMenu();
	}

}
