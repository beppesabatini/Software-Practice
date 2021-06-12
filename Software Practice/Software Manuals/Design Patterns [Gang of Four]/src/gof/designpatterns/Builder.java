package gof.designpatterns;

import gof.ch03_00.intro.blueprints.Blueprint;
import gof.ch03_02.builder.MazeBuilder;
import gof.designpatterns.structures.BuilderStructure;

/**
 * <div class="javadoc-text">An instance of the <b>Builder</b> pattern,
 * described in <i>Design Patterns</i> [{@linkplain GangOfFour Gang of Four}],
 * pp. 97-106. Separate the construction of a complex object from its
 * representation so that the same construction process can create different
 * representations.
 * <p/>
 * In the example of {@linkplain MazeBuilder} from the manual (p. 101), the
 * MazeDirector is able to start with one {@linkplain Blueprint}, and use two
 * different Builders to turn the blueprint into two different Maze products.
 * The UML diagram is below. See also the {@linkplain BuilderStructure}
 * diagram.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="../ch03_02/builder/UML Diagram.jpg"/>
 * </div>
 * 
 * <link rel="stylesheet" href="../styles/gof.css">
 */
public interface Builder extends GangOfFour {

}
