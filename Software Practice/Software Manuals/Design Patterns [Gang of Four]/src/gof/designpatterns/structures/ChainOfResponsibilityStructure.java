package gof.designpatterns.structures;

import gof.ch05_01.chainofresponsibility.*;
import gof.designpatterns.ChainOfResponsibility;

/**
 * <table class="javadoc-structure">
 * <tr>
 * <td><div class="diagram-title">Structure Diagram:
 * {@linkplain gof.designpatterns.ChainOfResponsibility ChainOfResponsibility}
 * </div>
 * <p/>
 * <center>from Design Patterns [{@linkplain gof.designpatterns.GangOfFour Gang
 * of Four}], p. 225. ©1995 by Addison-Wesley.</center></td>
 * </tr>
 * <tr>
 * <td class="structure-diagram">
 * <img src="../../ch05_01/chainofresponsibility/image-6039.png" /></td>
 * </tr>
 * <tr>
 * <td><center><i>Compared to the sample code:</i></center></td>
 * </tr>
 * <tr>
 * <td>
 * <ul>
 * <li><b><i>Client</i></b> — initiates the request to a ConcreteHandler object
 * on the chain</li>
 * <li><b><i>Handler</i></b> — corresponds to the abstract classes
 * {@linkplain HelpHandler} and {@linkplain Widget}</li>
 * <li><b><i>ConcreteHandler1</i></b> — corresponds to the class
 * {@linkplain Application}</li>
 * <li><b><i>ConcreteHandler2</i></b> — the class {@linkplain Dialog}</li>
 * <li><b><i>ConcreteHandler3</i></b> — the class {@linkplain Button}</li>
 * </ul>
 * </td>
 * </tr>
 * </table>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public interface ChainOfResponsibilityStructure extends ChainOfResponsibility {

}
