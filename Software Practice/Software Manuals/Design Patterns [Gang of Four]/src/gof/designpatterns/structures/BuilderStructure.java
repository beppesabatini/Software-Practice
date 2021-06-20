package gof.designpatterns.structures;

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
 * <td class="structure-diagram"><img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch03_02/builder/image-4762.png"
 * /></td>
 * </tr>
 * <tr>
 * <td><center><i>Compared to the sample code:</i></center></td>
 * </tr>
 * <tr>
 * <td>
 * <ul>
 * <li><b><i>Director</i></b> — corresponds to the class
 * {@linkplain gof.ch03_02.builder.MazeDirector MazeDirector}</li>
 * <li><b><i>Builder</i></b> — corresponds to the interface
 * {@linkplain gof.ch03_02.builder.MazeBuilder MazeBuilder}</li>
 * <li><b><i>ConcreteBuilder</i></b> — corresponds to the subclasses
 * {@linkplain gof.ch03_02.builder.StandardMazeBuilder StandardMazeBuilder} and
 * {@linkplain gof.ch03_02.builder.CountingMazeBuilder CountingMazeBuilder}</li>
 * <li><b><i>Product</i></b> — corresponds to the class
 * {@linkplain gof.ch03_00.intro.Maze Maze}</li>
 * </ul>
 * </td>
 * </tr>
 * </table>
 * 
 * <pre>
 * <style> 
 * table.javadoc-structure { 
 *     padding: 5px; width: 580px; 
 * }
 *
 * div.diagram-title { 
 *     font-size: 16px; font-weight: bold; text-align:center; 
 * }
 *
 * td.structure-diagram { 
 *     padding: 6px 0 0 0; 
 * }
 * </style>
 * </pre>
 */
public interface BuilderStructure extends Builder {

}
