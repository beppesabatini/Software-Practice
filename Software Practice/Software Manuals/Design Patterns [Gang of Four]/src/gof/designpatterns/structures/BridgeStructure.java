package gof.designpatterns.structures;

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
 * <td class="structure-diagram"><img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch04_02/bridge/image-5332.png"/></td>
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
 * {@linkplain gof.ch04_02.bridge.Window Window}</li>
 * <li><b><i>RefinedAbstraction</i></b> — corresponds to the concrete classes
 * {@linkplain gof.ch04_02.bridge.ApplicationWindow ApplicationWindow} and
 * {@linkplain gof.ch04_02.bridge.IconWindow IconWindow}</li>
 * </ul>
 * <ul>
 * <li><b><i>Implementor</i></b> — corresponds to the abstract class
 * {@linkplain gof.ch04_02.bridge.WindowImpl WindowImpl}</li>
 * <li><b><i>ConcreteImplementorA</i></b> — corresponds to the concrete class
 * {@linkplain gof.ch04_02.bridge.XWindowImpl XWindowImpl}</li>
 * <li><b><i>ConcreteImplementorB</i></b> — corresponds to the class
 * {@linkplain gof.ch04_02.bridge.PMWindowImpl PMWindowImpl}</li>
 * </ul>
 * </td>
 * </tr>
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
public interface BridgeStructure extends Bridge {

}
