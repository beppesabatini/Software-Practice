package gof.ch03_04.prototype;

import gof.ch03_00.intro.Maze;
import gof.ch03_00.intro.Room;
import gof.ch03_00.intro.WallWithDoor;
import gof.ch03_00.intro.WallWithoutDoor;
import gof.ch03_01.abstractfactory.MazeFactory;
import gof.designpatterns.Prototype;

/**
 * <div class="javadoc-text">From Design Patterns [Gang of Four], p. 122. An
 * example of the {@linkplain Prototype} design pattern. This class will work
 * badly or not at all. The Gang of Four picked a bad example this time. The
 * Prototype pattern will only work for simpler data types.
 * <p/>
 * This pattern depends on being able to clone prototypes, sample classes which
 * can be swapped out and exchanged for different samples during runtime. But,
 * in a maze such as the type we have been working with, most or all rooms will
 * have a pointer to another room. So to create a fully well-formed clone of one
 * single room, or even one single door, means, in effect, to clone the entire
 * maze.
 * <p/>
 * It turns out actually to be quite easy to clone the entire maze. But it's not
 * really helpful or meaningful to try to clone one smaller part of it. Almost
 * every part of the maze is interdependent, and connected with pointers,
 * directly or indirectly. If we really to need to clone one small element of
 * the maze for some reason, your most promising approach is to clone the entire
 * maze, and isolate the relevant portion in which you are interested.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
@Deprecated
public class MazePrototypeFactory extends MazeFactory implements Prototype {

	private Maze prototypeMaze;
	@SuppressWarnings("unused")
	private Room prototypeRoom;
	@SuppressWarnings("unused")
	private WallWithDoor prototypeWallWithDoor;
	private WallWithoutDoor prototypeWallWithoutDoor;

	public MazePrototypeFactory(Maze maze, WallWithoutDoor wallWithoutDoor, Room room, WallWithDoor wallWithDoor) {
		this.prototypeMaze = maze;
		this.prototypeWallWithoutDoor = wallWithoutDoor;
		this.prototypeRoom = room;
		this.prototypeWallWithDoor = wallWithDoor;
	}

	/**
	 * Users can easily clone any Maze directly, if it was created with a Blueprint.
	 * The Java file you are reading, in this case, only adds empty complication.
	 */
	@Override
	public Maze makeMaze() {
		Maze maze = this.prototypeMaze.clone();
		return (maze);
	}

	@Override
	/**
	 * This will work, but it won't do us any good. Just a blank wall floating in
	 * space by itself.
	 */
	public WallWithoutDoor makeWallWithoutDoor() {
		WallWithoutDoor wallWithoutDoor = this.prototypeWallWithoutDoor.clone();
		return (wallWithoutDoor);
	}

	@Deprecated
	@Override
	public WallWithDoor makeWallWithDoor(Room room01, Room room02) {
		String useClone = "";
		useClone += "Sorry, this function will ultimately clone the entire maze, ";
		useClone += "so use the Maze#clone() function instead";
		System.err.println(useClone);
		return (null);
	}

	@Deprecated
	@Override
	public Room makeRoom(int roomNumber) {
		String useClone = "";
		useClone += "Sorry, this function will ultimately clone the entire maze, ";
		useClone += "so use the Maze#clone() function instead";
		System.err.println(useClone);
		return (null);
	}
}
