package gof.ch03_05.singleton;

import gof.ch03_00.intro.Room;
import gof.ch03_00.intro.WallWithDoor;
import gof.ch03_01.abstractfactory.WallWithDoorNeedingSpell;
import gof.designpatterns.Singleton;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p. 93.
 * An earlier version was used to illustrate a different design pattern. Here a
 * new version is used to illustrate a {@linkplain Singleton}. </div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class EnchantedMazeFactory extends StandardMazeFactory implements Singleton {

	private static EnchantedMazeFactory instance;

	public static EnchantedMazeFactory getInstance() {
		if (instance == null) {
			instance = new EnchantedMazeFactory();
		}
		return instance;
	}

	// No else is allowed to instantiate this because it's a Singleton.
	private EnchantedMazeFactory() {
	}

	private static String magicWord = initMagicWord();

	private static String initMagicWord() {
		String newMagicWord = System.getProperty("magicWord");
		if (newMagicWord != null && "".equals(newMagicWord) == false) {
			return (newMagicWord);
		}
		return ("abraCadabra");
	}

	@Override // This signature works with a MazeFactory, but not with a MazeBuilder.
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
