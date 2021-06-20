package gof.designpatterns.structures;

import gof.designpatterns.Mediator;

/**
 * <table class="javadoc-structure">
 * <tr>
 * <td><div class="diagram-title">Structure Diagram:
 * {@linkplain gof.designpatterns.Mediator Mediator} </div>
 * <p/>
 * <center>from Design Patterns [{@linkplain gof.designpatterns.GangOfFour Gang
 * of Four}], p. 276. ©1995 by Addison-Wesley.</center></td>
 * </tr>
 * <tr>
 * <td class="structure-diagram"><img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch05_05/mediator/image-6494.png"
 * /></td>
 * </tr>
 * <tr>
 * <td><center><i>Compared to the sample code:</i></center></td>
 * </tr>
 * <tr>
 * <td>
 * <ul>
 * <li><b><i>Mediator</i></b> — corresponds to the abstract class
 * {@linkplain gof.ch05_05.mediator.DialogDirector DialogDirector}</li>
 * <li><b><i>ConcreteMediator</i></b> — corresponds to the concrete subclass
 * {@linkplain gof.ch05_05.mediator.FontDialogDirector FontDialogDirector}</li>
 * </ul>
 * <ul>
 * <li><b><i>Colleague</i></b> — corresponds to the abstract class
 * {@linkplain gof.ch05_05.mediator.Widget Widget}</li>
 * <li><b><i>ConcreteColleague1</i></b> — corresponds to the concrete subclass
 * {@linkplain gof.ch05_05.mediator.Button Button}</li>
 * <li><b><i>ConcreteColleague2</i></b> — the concrete subclass
 * {@linkplain gof.ch05_05.mediator.EntryField EntryField}</li>
 * <li><b><i>ConcreteColleague3</i></b> — the concrete subclass
 * {@linkplain gof.ch05_05.mediator.ListBox ListBox}</li>
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
public interface MediatorStructure extends Mediator {

}
