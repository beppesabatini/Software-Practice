package gof.ch02_05.lookandfeel;

/**
 * <div class="javadoc-text">Adapted from DesignPatterns [Gang of Four], p. 50.
 * One class in the sample code for a brief intro to the
 * {@linkplain gof.designpatterns.AbstractFactory AbstractFactory} design
 * pattern. PM stands for Presentation Manager, an old widget library.</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch02_05/lookandfeel/Factory%20UML%20Diagram.jpg"
 * /> </div>
 * 
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
