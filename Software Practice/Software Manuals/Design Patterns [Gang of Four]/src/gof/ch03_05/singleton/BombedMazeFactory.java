package gof.ch03_05.singleton;

import gof.ch03_00.intro.Room;
import gof.ch03_00.intro.WallWithoutDoor;
import gof.ch03_03.factorymethod.Bomb;
import gof.ch03_03.factorymethod.BombedWallWithoutDoor;
import gof.ch03_03.factorymethod.RoomWithABomb;
import gof.designpatterns.Singleton;

/**
 * <div class="javadoc-text">From Design Patterns [Gang of Four], p. 93, 115.
 * The BombedMazeFactory was used earlier to illustrate different patterns; here
 * it is revised to illustrate the {@linkplain gof.designpatterns.Singleton
 * Singleton}. (In production code a developer would decide on one version or
 * the other, and support only that one.) See also
 * {@linkplain StandardMazeFactory} for more detail.</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch03_05/singleton/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class BombedMazeFactory extends StandardMazeFactory implements Singleton {

	private static BombedMazeFactory instance;

	public static BombedMazeFactory getInstance() {
		if (instance == null) {
			instance = new BombedMazeFactory();
		}
		return (instance);
	}

	// No else is allowed to instantiate this because it's a Singleton.
	private BombedMazeFactory() {
	}

	/**
	 * The original {@link StandardMazeFactory} requires nothing more specific than
	 * a Wall. This subclass specifies that it will be a BombedWallWithoutDoor.
	 */
	@Override
	public WallWithoutDoor makeWallWithoutDoor() {
		return new BombedWallWithoutDoor();
	}

	/**
	 * The StandardMazeFactory specified a Room, but now it will be getting a
	 * RoomWithABomb.
	 */
	@Override
	public Room makeRoom(int roomNumber) {
		return makeRoomWithABomb(roomNumber, null);
	}

	private RoomWithABomb makeRoomWithABomb(int roomNumber, Bomb liveBomb) {
		if (liveBomb == null) {
			liveBomb = new Bomb(25, 50);
		}
		RoomWithABomb roomWithABomb = new RoomWithABomb(roomNumber, liveBomb);
		return (roomWithABomb);
	}
}
