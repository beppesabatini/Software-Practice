package gof.designpatterns.structures;

import gof.ch03_00.intro.Maze;
import gof.ch03_02.builder.*;
import gof.designpatterns.Builder;

/**
 * <table class="javadoc-structure">
 * <tr>
 * <td><div class="diagram-title">Structure Diagram:
 * {@linkplain gof.designpatterns.Builder Builder} </div>
 * <p/>
 * <center>from Design Patterns [{@linkplain gof.designpatterns.GangOfFour Gang
 * of Four}], p. 98. ©1995 by Addison-Wesley.</center></td>
 * </tr>
 * <tr>
 * <td class="structure-diagram"><img src="../../ch03_02/builder/image-4762.png"
 * /></td>
 * </tr>
 * <tr>
 * <td><center><i>Compared to the sample code:</i></center></td>
 * </tr>
 * <tr>
 * <td>
 * <ul>
 * <li><b><i>Director</i></b> — corresponds to the class
 * {@linkplain MazeDirector}</li>
 * <li><b><i>Builder</i></b> — corresponds to the interface
 * {@linkplain MazeBuilder}</li>
 * <li><b><i>ConcreteBuilder</i></b> — corresponds to the subclasses
 * {@linkplain StandardMazeBuilder} and {@linkplain CountingMazeBuilder}</li>
 * <li><b><i>Product</i></b> — corresponds to the class {@linkplain Maze}</li>
 * </ul>
 * </td>
 * </tr>
 * </table>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public interface BuilderStructure extends Builder {

}
