package gof.designpatterns.structures;

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
 * <td class="structure-diagram"><img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch05_04/iterator/image-6383.png"
 * /></td>
 * </tr>
 * <tr>
 * <td><center><i>Compared to the sample code:</i></center></td>
 * </tr>
 * <tr>
 * <td>
 * <ul>
 * <li><b><i>Aggregate</i></b> — corresponds to the abstract class
 * {@linkplain gof.ch05_04.iterator.List List}</li>
 * <li><b><i>ConcreteAggregate</i></b> — corresponds to the concrete subclass
 * {@linkplain gof.ch05_04.iterator.EmployeeList EmployeeList}</li>
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
 * {@linkplain gof.ch05_04.iterator.Iterator Iterator}</li>
 * <li><b><i>ConcreteIterator</i></b> — corresponds to the concrete
 * implementation {@linkplain gof.ch05_04.iterator.ListIterator
 * ListIterator}</li>
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
 * {@linkplain gof.ch05_04.iterator.ListTraverser ListTraverser}</li>
 * <li><b><i>ConcreteIterator</i></b> — corresponds to the concrete subclass
 * {@linkplain gof.ch05_04.iterator.PrintNEmployees PrintNEmployees}</li>
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
public interface IteratorStructure extends Iterator {

}
