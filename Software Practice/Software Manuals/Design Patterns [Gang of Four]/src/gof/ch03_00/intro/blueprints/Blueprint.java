package gof.ch03_00.intro.blueprints;

import gof.ch03_00.intro.Maze;

/**
 * <div class="javadoc-text">The Blueprint concept is not in the manual. It is
 * designed to work together with a MazeBuilder.</div>
 * 
 * <link rel="stylesheet" type="text/css" href="../../../styles/gof.css">
 */
public interface Blueprint {
	public Maze build();
}
