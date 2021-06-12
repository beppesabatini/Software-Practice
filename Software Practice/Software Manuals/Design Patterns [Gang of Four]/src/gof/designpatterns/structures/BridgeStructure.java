package gof.designpatterns.structures;

import gof.ch04_02.bridge.*;
import gof.designpatterns.Bridge;

/**
 * <table class="javadoc-structure">
 * <tr>
 * <td><div class="diagram-title">Structure Diagram:
 * {@linkplain gof.designpatterns.Bridge Bridge} </div>
 * <p/>
 * <center>from Design Patterns [{@linkplain gof.designpatterns.GangOfFour Gang
 * of Four}], p. 153. ©1995 by Addison-Wesley.</center></td>
 * </tr>
 * <tr>
 * <td class="structure-diagram">
 * <img src="../../ch04_02/bridge/image-5332.png"/></td>
 * </tr>
 * <tr>
 * <td><center><i>Compared to the sample code:</i></center></td>
 * </tr>
 * <tr>
 * <td>
 * <ul>
 * <li><b><i>Client</i></b> — an implicit client (does not directly
 * participate)</li>
 * <li><b><i>Abstraction</i></b> — corresponds to the abstract class
 * {@linkplain Window}</li>
 * <li><b><i>RefinedAbstraction</i></b> — corresponds to the concrete classes
 * {@linkplain ApplicationWindow} and {@linkplain IconWindow}</li>
 * </ul>
 * <ul>
 * <li><b><i>Implementor</i></b> — corresponds to the abstract class
 * {@linkplain WindowImpl}</li>
 * <li><b><i>ConcreteImplementorA</i></b> — corresponds to the concrete class
 * {@linkplain XWindowImpl}</li>
 * <li><b><i>ConcreteImplementorB</i></b> — corresponds to the class
 * {@linkplain PMWindowImpl}</li>
 * </ul>
 * </td>
 * </tr>
 * </table>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public interface BridgeStructure extends Bridge {

}
