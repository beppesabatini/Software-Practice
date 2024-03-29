package gof.ch03_00.intro;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], pp.
 * 82-83. Defining some of the classes which will be used in several design
 * pattern illustrations. A Room is defined with a room number and a map of four
 * Walls.</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch03_00/intro/UML%20Diagram.jpg"/>
 * </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class Room extends MapSite {

	private static final String DEBUG = "true";

	private final int roomNumber;

	public int getRoomNumber() {
		return this.roomNumber;
	}

	private Map<Direction, Wall> walls;

	public void addWall(Direction direction, Wall wall) {
		if (walls.get(direction) != null && Boolean.valueOf(DEBUG) == true) {
			System.out.println("Replacing " + direction + " wall");
		}
		walls.put(direction, wall);
	}

	public Room(int roomNumber) {
		walls = new EnumMap<Direction, Wall>(Direction.class);
		this.roomNumber = roomNumber;
	}

	public Room(int roomNumber, Wall northWall, Wall southWall, Wall eastWall, Wall westWall) {
		this.roomNumber = roomNumber;
		walls = new EnumMap<Direction, Wall>(Direction.class);
		walls.put(Direction.NORTH, northWall);
		walls.put(Direction.SOUTH, southWall);
		walls.put(Direction.EAST, eastWall);
		walls.put(Direction.WEST, westWall);
		if (Boolean.valueOf(DEBUG) == true) {
			System.out.println("Added room: " + roomNumber);
		}
	}

	@Override
	public boolean enter() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean deepEquals(Room otherRoom) {
		for (Direction direction : Direction.values()) {
			Wall currentWall = this.walls.get(direction);
			Wall otherWall = otherRoom.walls.get(direction);
			Class<?> currentWallClass = currentWall.getClass();
			Class<?> otherWallClass = otherWall.getClass();
			if (currentWallClass != otherWallClass) {
				return (false);
			}
			if (currentWallClass == WallWithoutDoor.class && otherWallClass == WallWithoutDoor.class) {
				// Nothing distinguishes two walls without doors.
				continue;
			}
			if (((currentWall instanceof WallWithDoor) == false) || ((otherWall instanceof WallWithDoor) == false)) {
				String message = "This should never happen. Broken logic in Room.deepEquals()";
				new IOException(message).printStackTrace(System.err);
				System.exit(1);
			}
			WallWithDoor currentWallWithDoor = (WallWithDoor) currentWall;
			WallWithDoor otherWallWithDoor = (WallWithDoor) otherWall;
			if (currentWallWithDoor.deepEquals(otherWallWithDoor) == false) {
				return (false);
			}
		}
		return (true);
	}
}
