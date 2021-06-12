package gof.ch03_03.factorymethod;

import gof.ch03_00.intro.WallWithoutDoor;
import gof.designpatterns.FactoryMethod;

/**
 * <div class="javadoc-text">From Design Patterns [Gang of Four], p. 93, 115. An
 * class used in the sample code to illustrate the {@linkplain FactoryMethod}
 * pattern.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class BombedWallWithoutDoor extends WallWithoutDoor implements FactoryMethod {

	private int damageLevel;

	public int getDamageLevel() {
		return (this.damageLevel);
	}

	public void setDamageLevel(int damageLevel) {
		this.damageLevel = damageLevel;
	}
}
