package gof.ch03_00.intro;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], pp.
 * 82-83. Defining some of the classes which will be used in several design
 * pattern illustrations. The manual uses "MapSite" to mean any of the elements
 * which go into building a Maze.</div>
 * 
 * <div class="javadoc-diagram"><img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch03_00/intro/UML%20Diagram.jpg"/></div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public abstract class MapSite {
	public abstract boolean enter();
}
