package gof.designpatterns.structures;

import gof.designpatterns.Memento;

/**
 * <table class="javadoc-structure">
 * <tr>
 * <td><div class="diagram-title">Structure Diagram:
 * {@linkplain gof.designpatterns.Memento Memento} </div>
 * <p/>
 * <center>from Design Patterns [{@linkplain gof.designpatterns.GangOfFour Gang
 * of Four}], p. 285. ©1995 by Addison-Wesley.</center></td>
 * </tr>
 * <tr>
 * <td class="structure-diagram"><img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch05_06/memento/image-6615.png"/></td>
 * </tr>
 * <tr>
 * <td><center><i>Compared to the sample code:</i></center></td>
 * </tr>
 * <tr>
 * <td>
 * <ul>
 * <li><b><i>Originator</i></b> — corresponds to the class
 * {@linkplain gof.ch05_06.memento.MoveCommand MoveCommand}</li>
 * <li><b><i>Memento</i></b> — corresponds to the nested class
 * {@linkplain gof.ch05_06.memento.ConstraintSolver Memento}</li>
 * <li><b><i>Caretaker</i></b> — corresponds to the class
 * {@linkplain gof.ch05_06.memento.ConstraintSolver ConstraintSolver}</li>
 * </ul>
 * </td>
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
public interface MementoStructure extends Memento {

}
