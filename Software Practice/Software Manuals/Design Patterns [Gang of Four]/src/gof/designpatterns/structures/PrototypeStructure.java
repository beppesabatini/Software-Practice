package gof.designpatterns.structures;

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
 * <td class="structure-diagram"><img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch03_04/prototype/image-4988.png"
 * /></td>
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
 * {@linkplain gof.ch03_04.prototype.JobPriorityPrototypeFactory
 * JobPriorityPrototypeFactory}</li>
 * <li><b><i>ConcretePrototype1</i></b> — corresponds to the subclass
 * {@linkplain gof.ch03_04.prototype.AdTargetingJobPriority
 * AdTargetingJobPriority}</li>
 * <li><b><i>ConcretePrototype2</i></b> — the subclass
 * {@linkplain gof.ch03_04.prototype.UsageStatisticsJobPriority
 * UsageStatisticsJobPriority}</li>
 * <li><b><i>ConcretePrototype3</i></b> — the subclass
 * {@linkplain gof.ch03_04.prototype.UserNotificationJobPriority
 * UserNotificationJobPriority}</li>
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
public interface PrototypeStructure extends Prototype {

}
