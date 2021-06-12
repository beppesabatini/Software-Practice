package gof.ch03_05.singleton;

import gof.ch03_00.intro.Maze;
import gof.ch03_00.intro.Room;
import gof.ch03_00.intro.Wall;
import gof.ch03_00.intro.WallWithDoor;
import gof.ch03_00.intro.WallWithoutDoor;
import gof.designpatterns.Singleton;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p.
 * 132. A variation of an object from earlier chapters, now reimplemented as the
 * design pattern {@linkplain Singleton}. The Singleton is perhaps the
 * best-known of the design patterns. The manual notes (p. 133) that in
 * Smalltalk, the metaclasses are Singletons, and this is true of the Class
 * objects in Java as well.
 * <p/>
 * The main reason to use Singletons is to save on initialization expense. In
 * our own experience, we have used Singletons most often to implement resource
 * bundles, which have a significant initialization expense, which are
 * read-only, and which are globally accessible.</div>
 * <p/>
 * The manual's reasoning is that any one particular Maze pattern might be used
 * in three (or more) different games. The Standard Maze game might be something
 * like PacMan. The Enchanted Maze might have someone like Princess Peach
 * opening Maze doors with a magic spell. The Bombed Maze might have foot
 * soldiers opening doors with a bomb. The basic layout of the maze, that is,
 * the game level, remains the same for all three games. The reusable Maze
 * layout is not defined here yet. It can be defined with a MazeBuilder, or
 * written by hand, in terms of MazeFactory functions.
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class StandardMazeFactory implements Singleton {

	private static StandardMazeFactory instance;

	public static StandardMazeFactory getInstance() {
		if (instance == null) {
			instance = new StandardMazeFactory();
		}
		return instance;
	}

	/*
	 * No one's allowed to see or invoke this Constructor, because we are in a
	 * Singleton and we don't want anyone to build another one. Try letting the
	 * subclasses use this as a dummy function.
	 */
	protected StandardMazeFactory() {
		// Does nothing.
	}

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
