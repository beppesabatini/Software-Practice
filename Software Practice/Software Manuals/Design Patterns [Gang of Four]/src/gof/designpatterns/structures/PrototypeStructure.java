package gof.designpatterns.structures;

import gof.ch03_04.prototype.*;
import gof.designpatterns.Prototype;

/**
 * <table class="javadoc-structure">
 * <tr>
 * <td><div class="diagram-title">Structure Diagram:
 * {@linkplain gof.designpatterns.Prototype Prototype} </div>
 * <p/>
 * <center>from Design Patterns [{@linkplain gof.designpatterns.GangOfFour Gang
 * of Four}], p. 119. ©1995 by Addison-Wesley.</center></td>
 * </tr>
 * <tr>
 * <td class="structure-diagram">
 * <img src="../../ch03_04/prototype/image-4988.png" /></td>
 * </tr>
 * <tr>
 * <td><center><i>Compared to the sample code:</i></center></td>
 * </tr>
 * <tr>
 * <td>
 * <ul>
 * <li><b><i>Client</i></b> — An overnight-job manager; it does not appear in
 * the sample code
 * <li><b><i>Prototype</i></b> — corresponds to the class
 * {@linkplain JobPriorityPrototypeFactory}</li>
 * <li><b><i>ConcretePrototype1</i></b> — corresponds to the subclass
 * {@linkplain AdTargetingJobPriority}</li>
 * <li><b><i>ConcretePrototype2</i></b> — the subclass
 * {@linkplain UsageStatisticsJobPriority}</li>
 * <li><b><i>ConcretePrototype3</i></b> — the subclass
 * {@linkplain UserNotificationJobPriority}</li>
 * </ul>
 * </td>
 * </table>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public interface PrototypeStructure extends Prototype {

}
