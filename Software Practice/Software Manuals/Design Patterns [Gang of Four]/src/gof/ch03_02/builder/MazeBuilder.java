package gof.ch03_02.builder;

import gof.ch03_00.intro.Direction;
import gof.ch03_00.intro.Maze;
import gof.ch03_00.intro.MazeGame;
import gof.designpatterns.Builder;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p.
 * 101. An element in a {@linkplain Builder} pattern. An interface for the
 * module that takes blueprint-like logic and from that builds a Maze product.
 * The UML diagram is below.
 * <p/>
 * In this example, the MazeDirector is able to start with one blueprint, and
 * use two different Builders to turn the blueprint into two different Maze
 * outputs. The blueprint is implemented in terms of logic in the new Maze
 * Constructor. The main advantage of using a MazeBuilder is that any number of
 * blueprints can be defined, with much less code in each one. Compare the
 * {@linkplain Maze} Constructor (4 lines of code) with the original
 * {@linkplain MazeGame} object (20 lines of code).</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
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
