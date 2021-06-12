package gof.designpatterns.structures;

import gof.ch05_04.iterator.*;
import gof.designpatterns.Iterator;

/**
 * <table class="javadoc-structure">
 * <tr>
 * <td><div class="diagram-title">Structure Diagram:
 * {@linkplain gof.designpatterns.Iterator Iterator} </div>
 * <p/>
 * <center>from Design Patterns [{@linkplain gof.designpatterns.GangOfFour Gang
 * of Four}], p. 259. ©1995 by Addison-Wesley.</center></td>
 * </tr>
 * <tr>
 * <td class="structure-diagram">
 * <img src="../../ch05_04/iterator/image-6383.png" /></td>
 * </tr>
 * <tr>
 * <td><center><i>Compared to the sample code:</i></center></td>
 * </tr>
 * <tr>
 * <td>
 * <ul>
 * <li><b><i>Aggregate</i></b> — corresponds to the abstract class
 * {@linkplain List}</li>
 * <li><b><i>ConcreteAggregate</i></b> — corresponds to the concrete subclass
 * {@linkplain EmployeeList}</li>
 * </ul>
 * </td>
 * </tr>
 * <tr>
 * <td><center><i>For the external iterator:</i></center></td>
 * </tr>
 * <tr>
 * <td>
 * <ul>
 * <li><b><i>Iterator</i></b> — corresponds to the Interface
 * {@linkplain Iterator}</li>
 * <li><b><i>ConcreteIterator</i></b> — corresponds to the concrete
 * implementation {@linkplain ListIterator}</li>
 * </ul>
 * </td>
 * </tr>
 * <tr>
 * <td><center><i>For the internal iterator:</i></center></td>
 * </tr>
 * <tr>
 * <td>
 * <ul>
 * <li><b><i>Iterator</i></b> — corresponds to the abstract class
 * {@linkplain ListTraverser}</li>
 * <li><b><i>ConcreteIterator</i></b> — corresponds to the concrete subclass
 * {@linkplain PrintNEmployees}</li>
 * </ul>
 * </td>
 * </tr>
 * </table>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public interface IteratorStructure extends Iterator {

}
