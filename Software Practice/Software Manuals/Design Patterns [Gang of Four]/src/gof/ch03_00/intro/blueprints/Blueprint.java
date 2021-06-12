package gof.ch03_00.intro.blueprints;

import gof.ch03_00.intro.Maze;
import gof.ch03_01.abstractfactory.MazeFactory;
import gof.ch03_02.builder.MazeBuilder;

/**
 * The Blueprint concept is not in the manual. It is designed to work together
 * with a MazeBuilder.
 * <p/>
 * Don't confuse a {@link MazeBuilder} with a {@link MazeFactory}. The
 * MazeBuilder takes a Blueprint and builds it up into some kind of Maze product.
 * The Maze Factory decides at runtime what functions will be used to build the
 * Maze. The two patterns can be used with or without each other.
 */
public interface Blueprint {
	public Maze build();
}
