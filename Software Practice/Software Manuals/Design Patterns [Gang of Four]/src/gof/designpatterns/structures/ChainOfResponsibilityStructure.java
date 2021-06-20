package gof.designpatterns.structures;

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
 * <td class="structure-diagram"><img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch05_01/chainofresponsibility/image-6039.png"
 * /></td>
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
 * {@linkplain gof.ch05_01.chainofresponsibility.HelpHandler HelpHandler} and
 * {@linkplain gof.ch05_01.chainofresponsibility.Widget Widget}</li>
 * <li><b><i>ConcreteHandler1</i></b> — corresponds to the class
 * {@linkplain gof.ch05_01.chainofresponsibility.Application Application}</li>
 * <li><b><i>ConcreteHandler2</i></b> — the class
 * {@linkplain gof.ch05_01.chainofresponsibility.Dialog Dialog}</li>
 * <li><b><i>ConcreteHandler3</i></b> — the class
 * {@linkplain gof.ch05_01.chainofresponsibility.Button Button}</li>
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
public interface ChainOfResponsibilityStructure extends ChainOfResponsibility {

}
