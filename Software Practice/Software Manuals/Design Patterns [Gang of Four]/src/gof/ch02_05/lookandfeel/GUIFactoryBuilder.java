package gof.ch02_05.lookandfeel;

import gof.designpatterns.AbstractFactory;

/**
 * <div class="javadoc-text">Adapted from DesignPatterns [Gang of Four], p. 51.
 * A brief intro to the {@linkplain AbstractFactory} design pattern. The class
 * name is not specified in the manual. This is a somewhat over-simplified and
 * perhaps outdated example.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="Factory UML Diagram.jpg" /> </div>
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
