package gof.designpatterns.structures;

import gof.ch05_05.mediator.*;
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
 * <td class="structure-diagram">
 * <img src="../../ch05_05/mediator/image-6494.png" /></td>
 * </tr>
 * <tr>
 * <td><center><i>Compared to the sample code:</i></center></td>
 * </tr>
 * <tr>
 * <td>
 * <ul>
 * <li><b><i>Mediator</i></b> — corresponds to the abstract class
 * {@linkplain DialogDirector}</li>
 * <li><b><i>ConcreteMediator</i></b> — corresponds to the concrete subclass
 * {@linkplain FontDialogDirector}</li>
 * </ul>
 * <ul>
 * <li><b><i>Colleague</i></b> — corresponds to the abstract class
 * {@linkplain Widget}</li>
 * <li><b><i>ConcreteColleague1</i></b> — corresponds to the concrete subclass
 * {@linkplain Button}</li>
 * <li><b><i>ConcreteColleague2</i></b> — the concrete subclass
 * {@linkplain EntryField}</li>
 * <li><b><i>ConcreteColleague3</i></b> — the concrete subclass
 * {@linkplain ListBox}</li>
 * </ul>
 * </td>
 * </table>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public interface MediatorStructure extends Mediator {

}
