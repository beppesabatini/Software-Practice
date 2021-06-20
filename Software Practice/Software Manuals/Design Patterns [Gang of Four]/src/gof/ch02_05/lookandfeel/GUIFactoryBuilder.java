package gof.ch02_05.lookandfeel;

/**
 * <div class="javadoc-text">Adapted from DesignPatterns [Gang of Four], p. 51.
 * Part of the sample code for a brief intro to the
 * {@linkplain gof.designpatterns.AbstractFactory AbstractFactory} design
 * pattern. The class name GUIFactoryBuilder is not specified in the manual.
 * </div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch02_05/lookandfeel/Factory%20UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class GUIFactoryBuilder {
	private GUIFactoryBuilder() {
		System.out.println("Sorry, you must use \"buildGUIFactory()\" to create a factory");
	}

	// In real life no systems do this any longer.
	public GUIFactory buildGUIFactory() {
		String lookAndFeel = System.getProperty("lookAndFeel");

		switch (lookAndFeel) {
		case "Motif": {
			return new MotifFactory();
		}
		case "PM": {
			return new PMFactory();
		}
		case "Mac": {
			return new MacFactory();
		}
		default:
			System.err.println("Sorry, unidentified look-and-feel: " + lookAndFeel);
			return (null);
		}
	}
}
