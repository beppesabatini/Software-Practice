package gof.ch03_01.abstractfactory;

import gof.ch03_00.intro.Room;
import gof.ch03_00.intro.WallWithDoor;
import gof.designpatterns.AbstractFactory;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p. 93.
 * This class is part of the sample code for the
 * {@linkplain gof.designpatterns.AbstractFactory AbstractFactory} design
 * pattern. As implemented in the manual, <b>every</b> door in the maze will now
 * require a magic word, apparently the same magic word for every door. This is
 * probably not how would design your game level. The game play has been
 * simplified for the purpose of illustrating the design pattern.</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch03_01/abstractfactory/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class EnchantedMazeFactory extends MazeFactory implements AbstractFactory {

	private String magicWord;

	public EnchantedMazeFactory(String magicWord) {
		this.magicWord = magicWord;
	}

	@Override
	public WallWithDoor makeWallWithDoor(Room room01, Room room02) {
		WallWithDoor wallWithDoor = makeWallWithDoorNeedingSpell(room01, room02);
		return (wallWithDoor);
	}

	private WallWithDoor makeWallWithDoorNeedingSpell(Room room01, Room room02) {
		WallWithDoorNeedingSpell door;
		door = new WallWithDoorNeedingSpell(room01, room02, magicWord);
		return (door);
	}

}
