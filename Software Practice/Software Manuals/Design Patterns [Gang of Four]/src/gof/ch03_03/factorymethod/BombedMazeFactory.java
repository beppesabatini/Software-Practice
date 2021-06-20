package gof.ch03_03.factorymethod;

import gof.ch03_00.intro.Room;
import gof.ch03_00.intro.WallWithoutDoor;
import gof.ch03_01.abstractfactory.MazeFactory;
import gof.ch03_01.abstractfactory.MazeGame;
import gof.designpatterns.AbstractFactory;
import gof.designpatterns.FactoryMethod;

/**
 * <div class="javadoc-text">From Design Patterns [Gang of Four], p. 93, 115.
 * Part of the sample code illustrating a
 * {@linkplain gof.designpatterns.FactoryMethod FactoryMethod} pattern. The
 * earlier pattern {@linkplain gof.designpatterns.AbstractFactory
 * AbstractFactory} was implemented using FactoryMethods, and the manual uses
 * exactly the same sample code to illustrate both sections. Below, we have
 * implemented one additional brief FactoryMethod not included in our sample
 * Java code for the AbstractFactory. The UML Diagram is below.
 * <p/>
 * As the manual defines the term, all of the public functions in this class,
 * and in the {@linkplain gof.ch03_01.abstractfactory.EnchantedMazeFactory
 * EnchantedMazeFactory} class, can be considered FactoryMethods. None of their
 * return values are hard-coded in the original MazeFactory; all are decided in
 * its subclasses.</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch03_03/factorymethod/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class BombedMazeFactory extends MazeFactory implements AbstractFactory, FactoryMethod {

	/**
	 * A {@linkplain FactoryMethod}. The original {@linkplain MazeFactory} requires
	 * nothing more specific than a Wall. This subclass specifies that it will be a
	 * BombedWallWithoutDoor.
	 */
	@Override
	public WallWithoutDoor makeWallWithoutDoor() {
		return new BombedWallWithoutDoor();
	}

	@Override
	/**
	 * Another {@linkplain FactoryMethod}. MazeFactory specified a Room, but now it
	 * will be getting a RoomWithABomb.
	 */
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

	public static class Demo {
		@SuppressWarnings("unused")
		public static void main(String[] args) {
			MazeGame mazeGameLevel = new MazeGame(new MazeFactory());
			MazeGame bombedMazeGameLevel = new MazeGame(new BombedMazeFactory());
			String message = "";
			message += "Now you have two versions of this game level, standard and bombed-out, ";
			message += "which can be used in two different games.";
			System.out.println(message);
		}
	}
}
