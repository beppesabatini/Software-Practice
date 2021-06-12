package gof.designpatterns.structures;

import gof.ch05_07.observer.*;
import gof.designpatterns.Observer;

/**
 * <table class="javadoc-structure">
 * <tr>
 * <td><div class="diagram-title">Structure Diagram:
 * {@linkplain gof.designpatterns.Observer Observer} </div>
 * <p/>
 * <center>from Design Patterns [{@linkplain gof.designpatterns.GangOfFour Gang
 * of Four}], p. 294. ©1995 by Addison-Wesley.</center></td>
 * </tr>
 * <tr>
 * <td class="structure-diagram">
 * <img src="../../ch05_07/observer/image-6732.png" /></td>
 * </tr>
 * <tr>
 * <td><center><i>Compared to the sample code:</i></center></td>
 * </tr>
 * <tr>
 * <td>
 * <ul>
 * <li><b><i>Subject</i></b> — corresponds to the abstract class
 * {@linkplain Subject}</li>
 * <li><b><i>ConcreteSubject</i></b> — corresponds to the concrete subclass
 * {@linkplain ClockTimer}</li>
 * <p/>
 * <li><b><i>Observer</i></b> — corresponds to the abstract class
 * {@linkplain Observer}</li>
 * <li><b><i>ConcreteObserver</i></b> — there are two ConcreteObservers—two
 * concrete subclasses of Observer—{@linkplain AnalogClock} and
 * {@linkplain DigitalClock}</li>
 * </ul>
 * </td>
 * </table>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public interface ObserverStructure extends Observer {

}
