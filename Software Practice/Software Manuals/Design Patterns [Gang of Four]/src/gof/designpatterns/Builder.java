package gof.designpatterns;

/**
 * <div class="javadoc-text">An instance of the <b>Builder</b> pattern,
 * described in <i>Design Patterns</i> [{@linkplain GangOfFour Gang of Four}],
 * pp. 97-106. Separate the construction of a complex object from its
 * representation so that the same construction process can create different
 * representations.
 * <p/>
 * In the example of {@linkplain gof.ch03_02.builder.MazeBuilder MazeBuilder}
 * from the manual (p. 101), the MazeDirector is able to start with one
 * {@linkplain gof.ch03_00.intro.blueprints.Blueprint Blueprint}, and use two
 * different Builders to turn the blueprint into two different Maze products.
 * The UML diagram is below. See also the
 * {@linkplain gof.designpatterns.structures.BuilderStructure BuilderStructure}
 * diagram.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch03_02/builder/UML%20Diagram.jpg"/>
 * </div>
 * 
 * <style> div.javadoc-text { width: 580px; }</style>
 * <p/>
 * <style> div.javadoc-diagram {margin-left: 5px;}</style>
 */
public interface Builder extends GangOfFour {

}
