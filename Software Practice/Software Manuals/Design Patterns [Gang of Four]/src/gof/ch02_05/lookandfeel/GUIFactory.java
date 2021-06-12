package gof.ch02_05.lookandfeel;

import gof.designpatterns.AbstractFactory;

/**
 * <div class="javadoc-text">Adapted from DesignPatterns [Gang of Four], p. 50.
 * A brief intro to the {@linkplain AbstractFactory} design pattern.
 * <p/>
 * The manual gives two different examples of the {@linkplain AbstractFactory}.
 * In this version, the Factory is an interface; in the other example (see
 * chapter 3) it is an abstract class which gets subclassed.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="Factory UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public interface GUIFactory extends AbstractFactory {

	public ScrollBar createScrollBar();

	public Button createButton();

	public Menu createMenu();
}
