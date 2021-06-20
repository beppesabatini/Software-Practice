package gof.designpatterns.structures;

import gof.designpatterns.Composite;

/**
 * <table class="javadoc-structure">
 * <tr>
 * <td><div class="diagram-title">Structure Diagram:
 * {@linkplain gof.designpatterns.Composite Composite} </div>
 * <p/>
 * <center>from Design Patterns [{@linkplain gof.designpatterns.GangOfFour Gang
 * of Four}], p. 164. ©1995 by Addison-Wesley.</center></td>
 * </tr>
 * <tr>
 * <td class="structure-diagram"><img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch04_03/composite/image-5439.png"
 * /></td>
 * </tr>
 * <tr>
 * <td><center><i>Compared to the sample code:</i></center></td>
 * </tr>
 * <tr>
 * <td>
 * <ul>
 * <li><b><i>Client</i></b> — end-user or system using the Component
 * interface.</li>
 * <li><b><i>Component</i></b> — corresponds to the abstract class
 * {@linkplain gof.ch04_03.composite.Equipment Equipment}.</li>
 * <li><b><i>Leaf</i></b> — simple Equipment subclasses such as a
 * {@linkplain gof.ch04_03.composite.FloppyDisk FloppyDisk} or a Graphics
 * Card.</li>
 * <li><b><i>Composite</i></b> — complex Equipment subclasses, which manage a
 * collection of Equipment classes, including both the simple and the complex
 * varieties. Examples in the sample code include Chassis and
 * {@linkplain gof.ch04_03.composite.CompositeEquipment
 * CompositeEquipment}.</li>
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
public interface CompositeStructure extends Composite {

}
