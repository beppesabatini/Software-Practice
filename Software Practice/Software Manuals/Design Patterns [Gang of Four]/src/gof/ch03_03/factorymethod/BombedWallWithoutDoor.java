package gof.ch03_03.factorymethod;

import gof.ch03_00.intro.WallWithoutDoor;
import gof.designpatterns.FactoryMethod;

/**
 * <div class="javadoc-text">From Design Patterns [Gang of Four], p. 93, 115. A
 * class used in the sample code which illustrates the
 * {@linkplain gof.designpatterns.FactoryMethod FactoryMethod} pattern. See
 * {@linkplain BombedMazeFactory} for more information.</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch03_03/factorymethod/UML%20Diagram.jpg"
 * /> </div>
 * 
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
