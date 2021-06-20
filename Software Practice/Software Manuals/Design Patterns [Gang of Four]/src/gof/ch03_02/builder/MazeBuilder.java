package gof.ch03_02.builder;

import gof.ch03_00.intro.Direction;
import gof.ch03_00.intro.Maze;
import gof.ch03_00.intro.MazeGame;
import gof.designpatterns.Builder;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p.
 * 101. An element in a {@linkplain gof.designpatterns.Builder Builder} pattern.
 * An interface for the module that takes blueprint-like logic and from that
 * builds a Maze product. The UML diagram is below.
 * <p/>
 * In this example, the MazeDirector is able to start with one blueprint, and
 * use two different Builders to turn the blueprint into two different Maze
 * outputs. The blueprint is implemented in terms of logic in the new Maze
 * Constructor. The main advantage of using a MazeBuilder is that any number of
 * blueprints can be defined, with much less code in each one. Compare the
 * {@linkplain gof.ch03_00.intro.Maze Maze} Constructor (4 lines of code) with
 * the original {@linkplain MazeGame} object (20 lines of code).
 * <p/>
 * Don't confuse a {@linkplain gof.ch03_02.builder.MazeBuilder MazeBuilder} with
 * a {@linkplain gof.ch03_01.abstractfactory.MazeFactory MazeFactory}. The
 * MazeBuilder takes a Blueprint and builds it up into some kind of Maze
 * product. The MazeFactory decides at runtime what functions will be used to
 * build the specified Maze. The two patterns can be used with or without each
 * other.</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch03_02/builder/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public interface MazeBuilder extends Builder {

	public void buildMaze();

	public void buildRoom(int roomNumber);

	@Deprecated // Won't work.
	public void buildWallWithDoor(int sourceRoomNumber, int targetRoomNumber);

	/**
	 * Implement this function instead. See {@link StandardMazeBuilder}. If the
	 * specified directions are NORTH and SOUTH, then this specifies that the NORTH
	 * wall of room01 adjoins the SOUTH wall of room01, and there is a door between
	 * the two adjoining rooms.
	 */
	public void buildWallWithDoor(int room01Num, Direction room01Direction, int room02Num, Direction room02Direction);

	public Maze getMaze();
}
