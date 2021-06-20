package gof.ch03_01.abstractfactory;

import gof.ch03_00.intro.Direction;
import gof.ch03_00.intro.Maze;
import gof.ch03_00.intro.Room;
import gof.ch03_00.intro.Wall;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p. 84.
 * This MazeGame is part of the sample code for the
 * {@linkplain gof.designpatterns.AbstractFactory AbstractFactory} design
 * pattern. In the earlier version of MazeGame, everything was hard-coded. Now
 * we reimplement it using a MazeFactory, which can build different Mazes for
 * different games.
 * <p/>
 * This MazeGame object builds a very simple maze of two rooms with one door
 * between them. The east wall of the first room adjoins the west wall of the
 * second room, and there is one door between the two. As you will read, this
 * simple maze gets used again and again, to demonstrate various design
 * patterns.
 * <p/>
 * In the UML Diagram below, the MazeFactory builds parts for this basic
 * MazeGame (including a WallWithDoor), while its subclass EnchantedMazeFactory
 * builds parts for the EnchantedMazeGame (including a
 * WallWithDoorNeedingSpell). </div>
 * 
 * <div class="javadoc-diagram"><img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch03_01/abstractfactory/UML%20Diagram.jpg"/></div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class MazeGame {

	public MazeGame(MazeFactory mazeFactory) {
		Maze maze = mazeFactory.makeMaze();
		Room room01 = mazeFactory.makeRoom(1);
		Room room02 = mazeFactory.makeRoom(2);
		Wall wallWithDoor01 = mazeFactory.makeWallWithDoor(room01, room02);

		maze.addRoom(room01);
		maze.addRoom(room02);

		room01.addWall(Direction.NORTH, mazeFactory.makeWallWithoutDoor());
		room01.addWall(Direction.EAST, wallWithDoor01);
		room01.addWall(Direction.SOUTH, mazeFactory.makeWallWithoutDoor());
		room01.addWall(Direction.WEST, mazeFactory.makeWallWithoutDoor());

		room02.addWall(Direction.NORTH, mazeFactory.makeWallWithoutDoor());
		room02.addWall(Direction.EAST, mazeFactory.makeWallWithoutDoor());
		room02.addWall(Direction.SOUTH, mazeFactory.makeWallWithoutDoor());
		room02.addWall(Direction.WEST, wallWithDoor01);
	}

	public static class Demo {
		@SuppressWarnings("unused")
		public static void main(String[] args) {
			MazeGame mazeGameLevel = new MazeGame(new MazeFactory());
			MazeGame enchangedMazeGameLevel = new MazeGame(new EnchantedMazeFactory("abraCadabra"));
			String message = "";
			message += "Now you have two versions of this game level, standard and enchanted, ";
			message += "which can be used in two different games.";
			System.out.println(message);
		}
	}
}
