package gof.designpatterns.structures;

import gof.ch05_06.memento.*;
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
 * <td class="structure-diagram">
 * <img src="../../ch05_06/memento/image-6615.png"/></td>
 * </tr>
 * <tr>
 * <td><center><i>Compared to the sample code:</i></center></td>
 * </tr>
 * <tr>
 * <td>
 * <ul>
 * <li><b><i>Originator</i></b> — corresponds to the class
 * {@linkplain MoveCommand}</li>
 * <li><b><i>Memento</i></b> — corresponds to the class
 * {@linkplain ConstraintSolver Memento}</li>
 * <li><b><i>Caretaker</i></b> — corresponds to the class
 * {@linkplain ConstraintSolver}</li>
 * </ul>
 * </td>
 * </table>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public interface MementoStructure extends Memento {

}
