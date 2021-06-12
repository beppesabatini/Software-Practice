package gof.designpatterns.structures;

import gof.ch05_08.state.*;
import gof.designpatterns.State;

/**
 * <table class="javadoc-structure">
 * <tr>
 * <td><div class="diagram-title">Structure Diagram:
 * {@linkplain gof.designpatterns.State State} </div>
 * <p/>
 * <center>from Design Patterns [{@linkplain gof.designpatterns.GangOfFour Gang
 * of Four}], p. 306. ©1995 by Addison-Wesley.</center></td>
 * </tr>
 * <tr>
 * <td class="structure-diagram"><img src="../../ch05_08/state/image-6853.png"/>
 * </td>
 * </tr>
 * <tr>
 * <td><center><i>Compared to the sample code:</i></center></td>
 * </tr>
 * <tr>
 * <td>
 * <ul>
 * <li><b><i>Context</i></b> — corresponds to the class
 * {@linkplain TCPConnection}</li>
 * <li><b><i>State</i></b> — corresponds to the abstract class
 * {@linkplain TCPState}</li>
 * <p/>
 * <li><b><i>ConcreteStateA</i></b> — corresponds to the subclass
 * {@linkplain TCPEstablished}</li>
 * <li><b><i>ConcreteStateB</i></b> — the subclass {@linkplain TCPListen}</li>
 * <li><b><i>ConcreteStateC</i></b> — the subclass {@linkplain TCPClosed}</li>
 * </ul>
 * </td>
 * </table>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public interface StateStructure extends State {

}
