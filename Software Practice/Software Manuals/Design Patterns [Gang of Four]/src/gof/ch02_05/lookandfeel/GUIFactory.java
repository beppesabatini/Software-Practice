package gof.ch02_05.lookandfeel;

import gof.designpatterns.AbstractFactory;

/**
 * <div class="javadoc-text">Adapted from DesignPatterns [Gang of Four], p. 50.
 * One class in the sample code for a brief intro to the
 * {@linkplain gof.designpatterns.AbstractFactory AbstractFactory} design
 * pattern.
 * <p/>
 * The manual gives two different examples of the AbstractFactory design
 * pattern. In this version (section 2.5), this class, GUIFactory, is an
 * interface which gets implemented; in the other example (section 3.1)
 * {@linkplain gui.ch03_01.MazeFactory MazeFactory} is an abstract class which
 * gets subclassed.</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch02_05/lookandfeel/Factory%20UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public interface GUIFactory extends AbstractFactory {

	public ScrollBar createScrollBar();

	public Button createButton();

	public Menu createMenu();
}
