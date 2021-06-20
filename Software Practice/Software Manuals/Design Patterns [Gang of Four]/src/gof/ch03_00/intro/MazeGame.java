package gof.ch03_00.intro;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p. 84.
 * Defining some of the classes which will be used in several design pattern
 * illustrations. In this early version, one maze is defined simply by
 * hard-coding a collection of components. </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class MazeGame {

	public Maze createMaze() {

		Maze maze = new Maze();

		Room room01 = new Room(1);
		Room room02 = new Room(2);
		WallWithDoor wallWithDoor01 = new WallWithDoor(room01, room02);

		maze.addRoom(room01);
		maze.addRoom(room02);

		room01.addWall(Direction.NORTH, new WallWithoutDoor());
		room01.addWall(Direction.EAST, wallWithDoor01);
		room01.addWall(Direction.SOUTH, new WallWithoutDoor());
		room01.addWall(Direction.WEST, new WallWithoutDoor());

		room02.addWall(Direction.NORTH, new WallWithoutDoor());
		room02.addWall(Direction.EAST, new WallWithoutDoor());
		room02.addWall(Direction.SOUTH, new WallWithoutDoor());
		room02.addWall(Direction.WEST, wallWithDoor01);

		return (maze);
	}

}
