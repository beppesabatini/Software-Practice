package gof.ch03_02.builder;

import gof.ch03_00.intro.Direction;
import gof.ch03_00.intro.Maze;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p.
 * 104. An element in the sample code for a
 * {@linkplain gof.designpatterns.Builder Builder} pattern. This code gets
 * invoked in a new Constructor for the Maze object. The UML diagram is below.
 * <p/>
 * Here, what is being "built" is a statistics report about what resources are
 * needed to build some certain maze. The thinking appears to be that you might
 * use this to help estimate expenses on, for example, a brick-and-mortar
 * version of the maze. Or, more realistically, to help design a project
 * management plan to develop a ones-and-zeroes version of the maze.
 * <p/>
 * In this example, the MazeDirector is able to start with one blueprint, and
 * use two different MazeBuilders to turn the blueprint into two different Maze
 * products. The blueprint is implemented in terms of logic in the new Maze
 * Constructor.</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch03_02/builder/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class CountingMazeBuilder implements MazeBuilder {

	private int wallsWithDoors;
	private int wallsWithoutDoors;
	private int rooms;

	public CountingMazeBuilder() {
		/*
		 * The compiler does this automatically. This Constructor is just a placeholder.
		 * Expenses such as as start-up and planning costs would go here.
		 */
		this.wallsWithDoors = 0;
		this.wallsWithoutDoors = 0;
		this.rooms = 0;
	}

	@Override
	public void buildMaze() {
		System.out.println("Starting statistical analysis");
	}

	@Override
	public void buildRoom(int roomNumber) {
		this.rooms++;
		this.wallsWithoutDoors += 4;
	}

	@Override
	@Deprecated
	public void buildWallWithDoor(int room01Number, int room02Number) {
		System.err.println("Sorry, this method is not functional. Use this one instead:");
		System.err.println("buildWallWithDoor(room01Number, room01Direction, room02Number, room02Direction)");
	}

	@Override
	public void buildWallWithDoor(int room01Num, Direction room01Direction, int room02Num, Direction room02Direction) {
		this.wallsWithoutDoors = this.wallsWithoutDoors - 2;
		this.wallsWithDoors++;
	}

	@Override
	public Maze getMaze() {
		/*
		 * Wrapping-up expenses such as building-code inspection or QA review get added
		 * on here.
		 */
		getCounts();
		return (null);
	}

	public void getCounts() {
		System.out.println("--- Construction Analysis ---");
		System.out.println("Number of rooms: " + this.rooms);
		System.out.println("Walls With Doors: " + this.wallsWithDoors);
		System.out.println("Walls Without Doors: " + this.wallsWithoutDoors);
	}

	public static class Demo {
		public static void main(String[] args) {
			new MazeDirector().buildMazes();
		}
	}
}
