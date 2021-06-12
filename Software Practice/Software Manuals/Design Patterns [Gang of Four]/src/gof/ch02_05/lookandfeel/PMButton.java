package gof.ch02_05.lookandfeel;

import java.awt.event.ActionEvent;

import gof.ch02_02.structure.GlyphImpl;
import gof.designpatterns.AbstractFactory;

/**
 * <div class="javadoc-text">Adapted from DesignPatterns [Gang of Four], p. 50.
 * A brief intro to the {@linkplain AbstractFactory} design pattern. PM stands
 * for Presentation Manager.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="Widget UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class PMButton extends GlyphImpl implements Button {

	@Override
	public void onPress(ActionEvent actionEvent) {
		// Not functional yet.
		System.out.println("Pressing Button: " + actionEvent.getID());
	}

}
