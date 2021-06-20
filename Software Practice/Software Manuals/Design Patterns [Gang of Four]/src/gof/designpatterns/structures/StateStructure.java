package gof.designpatterns.structures;

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
 * <td class="structure-diagram"><img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch05_08/state/image-6853.png"/>
 * </td>
 * </tr>
 * <tr>
 * <td><center><i>Compared to the sample code:</i></center></td>
 * </tr>
 * <tr>
 * <td>
 * <ul>
 * <li><b><i>Context</i></b> — corresponds to the class
 * {@linkplain gof.ch05_08.state.TCPConnection TCPConnection}</li>
 * <li><b><i>State</i></b> — corresponds to the abstract class
 * {@linkplain gof.ch05_08.state.TCPState TCPState}</li>
 * <p/>
 * <li><b><i>ConcreteStateA</i></b> — corresponds to the subclass
 * {@linkplain gof.ch05_08.state.TCPEstablished TCPEstablished}</li>
 * <li><b><i>ConcreteStateB</i></b> — the subclass
 * {@linkplain gof.ch05_08.state.TCPListen TCPListen}</li>
 * <li><b><i>ConcreteStateC</i></b> — the subclass
 * {@linkplain gof.ch05_08.state.TCPClosed TCPClosed}</li>
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
public interface StateStructure extends State {

}
