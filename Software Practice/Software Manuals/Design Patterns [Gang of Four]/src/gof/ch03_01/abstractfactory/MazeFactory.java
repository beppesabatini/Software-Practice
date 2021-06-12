package gof.ch03_01.abstractfactory;

import gof.ch03_00.intro.*;
import gof.designpatterns.AbstractFactory;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p. 92.
 * An example to illustrate the design pattern {@linkplain AbstractFactory}. In
 * the example, developers want to take the maze, the floor plan, for a certain
 * game level, and reuse it in a very different game. MazeFactory and
 * EnchantedMazeFactory (a subclass) both present the same signatures, so both
 * can be used as arguments to the MazeGame constructor.
 * <p/>
 * Writing a different factory for each different game preserves the same basic
 * blueprint--same rooms, entrances, and exits--but the behavior and appearance
 * of each game level can now be very different. The UML diagram is below.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"><img src="UML Diagram.jpg"/></div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class MazeFactory implements AbstractFactory {

	public Maze makeMaze() {
		return new Maze();
	}

	public Wall makeWallWithoutDoor() {
		return new WallWithoutDoor();
	}

	public Room makeRoom(int roomNumber) {
		return new Room(roomNumber);
	}

	public Wall makeWallWithDoor(Room room01, Room room02) {
		return new WallWithDoor(room01, room02);
	}
}
