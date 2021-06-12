package gof.ch02_05.lookandfeel;

import gof.ch02_02.structure.Glyph;
import gof.designpatterns.AbstractFactory;

/**
 * <div class="javadoc-text">Adapted from DesignPatterns [Gang of Four], p. 50.
 * A brief intro to the {@linkplain AbstractFactory} design pattern.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="Widget UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public interface Menu extends Glyph {
	public void popup();
}
