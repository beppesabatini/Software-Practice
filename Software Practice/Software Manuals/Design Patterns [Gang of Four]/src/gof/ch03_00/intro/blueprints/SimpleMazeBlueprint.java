package gof.ch03_00.intro.blueprints;

import gof.ch03_00.intro.Direction;
import gof.ch03_00.intro.Maze;
import gof.ch03_02.builder.MazeBuilder;

/**
 * The Blueprint concept is not in the manual. We have designed it to work
 * together with a MazeBuilder.
 */
public class SimpleMazeBlueprint implements Blueprint {

	private MazeBuilder mazeBuilder;

	public SimpleMazeBlueprint(MazeBuilder mazeBuilder) {
		this.mazeBuilder = mazeBuilder;
	}

	@Override
	public Maze build() {
		Maze maze = blueprintInstructions();
		return (maze);
	}

	// This is adapted from p. 102 of the manual.
	private Maze blueprintInstructions() {
		mazeBuilder.buildMaze();
		mazeBuilder.buildRoom(1);
		mazeBuilder.buildRoom(2);
		mazeBuilder.buildWallWithDoor(1, Direction.EAST, 2, Direction.WEST);
		return mazeBuilder.getMaze();
	}
}
