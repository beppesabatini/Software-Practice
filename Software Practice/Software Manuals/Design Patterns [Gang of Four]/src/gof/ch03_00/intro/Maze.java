package gof.ch03_00.intro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import gof.ch03_00.intro.blueprints.Blueprint;
import gof.ch03_02.builder.MazeDirector;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p. 84.
 * Defining some of the classes which will be used in several design pattern
 * illustrations. The Maze is an aggregation of classes which define the basic
 * layout for one level of a video game or something similar. The Standard Maze
 * game might be something like PacMan. The Enchanted Maze might have a
 * character like Princess Peach opening Maze doors with a magic spell. The
 * Bombed Maze might have foot soldiers opening doors with a bomb. The basic
 * layout of the maze, that is, the game level, remains the same for all three
 * games. </div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch03_00/intro/UML%20Diagram.jpg"/>
 * </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class Maze {

	private static final String DEBUG = "true";

	protected Map<Integer, Room> rooms;
	private Blueprint currentBlueprint = null;

	public Maze() {
		initializeRooms();
	}

	public Maze(Blueprint blueprint) {
		Maze newMaze = blueprint.build();
		if (newMaze != null) {
			this.rooms = newMaze.rooms;
		}
		this.currentBlueprint = blueprint;
	}

	protected void initializeRooms() {
		this.rooms = new HashMap<Integer, Room>();
	}

	public int addRoom(Room room) {

		int roomNumber = room.getRoomNumber();
		if (rooms.containsKey(roomNumber)) {
			System.out.println("Room number " + roomNumber + " is already in the Maze");
			return (roomNumber);
		}
		rooms.put(roomNumber, room);

		return (roomNumber);
	}

	public Room getRoom(int roomNumber) {
		Room room = rooms.get(roomNumber);
		return (room);
	}

	public int createNewRoomNumber() {
		Set<Integer> keySet = rooms.keySet();
		List<Integer> keyList = new ArrayList<Integer>(keySet);
		Collections.sort(keyList);
		int highestKey = keyList.get(keyList.size() - 1);
		return (highestKey + 1);
	}

	/*
	 * Not in the manual, though related issues are discussed.
	 */
	public Maze clone() {
		if (this.currentBlueprint == null) {
			System.err.println("Sorry, unable to clone this Maze");
			return (null);
		}
		Maze cloneMaze = this.currentBlueprint.build();
		return (cloneMaze);
	}

	/*
	 * Not in the manual, though related issues are discussed.
	 */
	public boolean deepEquals(Maze otherMaze) {
		if (this == otherMaze) {
			if (Boolean.valueOf(DEBUG) == true) {
				System.out.println("Caution, you are comparing a maze to itself");
			}
			return (true);
		}
		Set<Integer> keySet01 = this.rooms.keySet();
		Map<Integer, Room> otherMazeRooms = otherMaze.rooms;
		Set<Integer> keySet02 = otherMazeRooms.keySet();
		if (keySet01.equals(keySet02) == false) {
			return (false);
		}
		for (Integer key : keySet01) {
			Room currentRoom = rooms.get(key);
			Room otherRoom = otherMazeRooms.get(key);
			if (currentRoom.deepEquals(otherRoom) == false) {
				return (false);
			}
		}

		return (true);
	}

	public static class Demo {
		public static void main(String[] args) {
			new MazeDirector().buildMazes();
		}
	}
}
