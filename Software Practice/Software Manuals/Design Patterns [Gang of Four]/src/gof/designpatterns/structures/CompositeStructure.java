package gof.designpatterns.structures;

import gof.ch04_03.composite.*;
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
 * <td class="structure-diagram">
 * <img src="../../ch04_03/composite/image-5439.png" /></td>
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
 * {@linkplain Equipment}.</li>
 * <li><b><i>Leaf</i></b> — simple Equipment subclasses such as a
 * {@linkplain FloppyDisk} or a Graphics Card.</li>
 * <li><b><i>Composite</i></b> — complex Equipment subclasses, which manage a
 * collection of Equipment classes, including both the simple and the complex
 * varieties. Examples in the sample code include Chassis and
 * {@linkplain CompositeEquipment}.</li>
 * </ul>
 * </td>
 * </tr>
 * </table>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public interface CompositeStructure extends Composite {

}
