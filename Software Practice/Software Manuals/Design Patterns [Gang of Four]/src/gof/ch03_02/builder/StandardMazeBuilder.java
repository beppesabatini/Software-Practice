package gof.ch03_02.builder;

import gof.ch03_00.intro.Direction;
import gof.ch03_00.intro.Maze;
import gof.ch03_00.intro.Room;
import gof.ch03_00.intro.WallWithDoor;
import gof.ch03_00.intro.WallWithoutDoor;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], pp.
 * 102-104. An element in a {@linkplain gof.designpatterns.Builder Builder}
 * pattern. One of two MazeBuilders which implement the MazeBuilder interface.
 * This code gets invoked and tested from the {@linkplain MazeDirector}. The UML
 * diagram is below.</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch03_02/builder/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class StandardMazeBuilder extends Maze implements MazeBuilder {

	private static final String DEBUG = "true";

	@Override
	public void buildMaze() {
		if (Boolean.valueOf(DEBUG) == true) {
			System.out.println("Starting Maze construction");
		}
		initializeRooms();
	}

	@Override
	public void buildRoom(int roomNumber) {
		Room newRoom = new Room(roomNumber);
		newRoom.addWall(Direction.NORTH, new WallWithoutDoor());
		newRoom.addWall(Direction.SOUTH, new WallWithoutDoor());
		newRoom.addWall(Direction.EAST, new WallWithoutDoor());
		newRoom.addWall(Direction.WEST, new WallWithoutDoor());
		this.addRoom(newRoom);
		if (Boolean.valueOf(DEBUG) == true) {
			System.out.println("Created room: " + roomNumber);
		}

	}

	/*
	 * We have to add this function to the interface to fix a flaw in the
	 * pseudo-code logic. If the specified directions are NORTH and SOUTH, then this
	 * specifies that the NORTH wall of room01 adjoins the SOUTH wall of room01, and
	 * there is a door between the two adjoining rooms.
	 */
	@Override
	public void buildWallWithDoor(int room01Number, Direction direction01, int room02Number, Direction direction02) {
		Room room01 = getRoom(room01Number);
		if (room01 == null) {
			System.err.println("Sorry, Room " + room01Number + " is not initialized.");
			return;
		}
		Room room02 = this.getRoom(room02Number);
		if (room02 == null) {
			System.err.println("Sorry, Room " + room02Number + " is not initialized.");
			return;
		}
		if (direction01 == null || direction02 == null) {
			String message = "Sorry, to build a door between two rooms you must specify the connecting wall for both rooms";
			System.err.println(message);
			return;
		}
		WallWithDoor sharedWall = new WallWithDoor(room01, room02);
		room01.addWall(direction01, sharedWall);
		room02.addWall(direction02, sharedWall);
		if (Boolean.valueOf(DEBUG) == true) {
			String message = "";
			message += "Created door between the room " + room01Number + " " + direction01 + " wall";
			message += " and the room " + room02Number + " " + direction02 + " wall";
			System.out.println(message);
		}
	}

	@Override
	@Deprecated
	public void buildWallWithDoor(int room01Number, int room02Number) {
		System.err.println("Sorry, this method is not functional. Use this one instead:");
		System.err.println("buildWallWithDoor(room01Number, room01Direction, room02Number, room02Direction)");
	}

	@Override
	public Maze getMaze() {
		return (this);
	}

	public static class Demo {
		public static void main(String[] args) {
			new MazeDirector().buildMazes();
		}
	}
}
