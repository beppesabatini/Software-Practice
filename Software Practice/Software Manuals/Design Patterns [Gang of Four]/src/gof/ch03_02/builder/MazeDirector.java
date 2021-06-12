package gof.ch03_02.builder;

import gof.ch03_00.intro.blueprints.Blueprint;
import gof.ch03_00.intro.blueprints.SimpleMazeBlueprint;
import gof.ch03_00.intro.Maze;

/**
 * <div class="javadoc-text">Not in the manual. This class is used in
 * demonstrating the {@linkplain Builder} design pattern. In this example, the
 * MazeDirector is able to start with one blueprint, and use two different
 * MazeBuilders to turn the blueprint into two different Maze products.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class MazeDirector {

	public void buildMazes() {

		StandardMazeBuilder standardMazeBuilder = new StandardMazeBuilder();
		Blueprint simpleMazeBlueprint = new SimpleMazeBlueprint(standardMazeBuilder);
		Maze simpleStandardMaze = new Maze(simpleMazeBlueprint);
		if (simpleStandardMaze != null) {
			System.out.println("The simple standard (two-room) Maze is complete.");
		}
		System.out.println();

		Maze simpleStandardMazeClone = simpleStandardMaze.clone();
		System.out.println();

		if (simpleStandardMaze == simpleStandardMazeClone) {
			System.err.println("Sorry, the cloning function returned a pointer to the original.");
		}
		if (simpleStandardMaze.equals(simpleStandardMazeClone)) {
			System.out.println("Good, the clone passes the equals() test");
		} else {
			System.out.println("Sorry, the System clone() output fails the System equals() test.");
		}

		boolean isMazeDeepClone = simpleStandardMaze.deepEquals(simpleStandardMazeClone);
		if (isMazeDeepClone == true) {
			System.out.println("Good, the Maze clone() and deepEquals() functions are working correctly.");
		} else {
			System.out.println("Sorry, the Maze clone() function is NOT deepCloning correctly.");
		}

		System.out.println();
		// The next Maze product is actually just a report on the Maze statistics.
		CountingMazeBuilder countingMazeBuilder = new CountingMazeBuilder();
		Blueprint countingMazeBlueprint = new SimpleMazeBlueprint(countingMazeBuilder);
		new Maze(countingMazeBlueprint);
	}

	public static class Demo {
		public static void main(String[] args) {
			new MazeDirector().buildMazes();
		}
	}
}
