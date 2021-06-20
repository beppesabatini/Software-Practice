package gof.designpatterns.structures;

import gof.designpatterns.Facade;

/**
 * <table class="javadoc-structure">
 * <tr>
 * <td><div class="diagram-title">Structure Diagram:
 * {@linkplain gof.designpatterns.Facade Facade} </div>
 * <p/>
 * <center>from Design Patterns [{@linkplain gof.designpatterns.GangOfFour Gang
 * of Four}], p. 185. ©1995 by Addison-Wesley.</center></td>
 * </tr>
 * <tr>
 * <td class="structure-diagram"><img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch04_05/facade/image-5672.png"/></td>
 * </tr>
 * <tr>
 * <td><center>The client is able to launch one command,
 * {@linkplain gof.ch04_05.facade.Compiler Compiler}, and does not have to learn
 * the details about subsystems such as {@linkplain gof.ch04_05.facade.Scanner
 * Scanner}, Parser, and RISCCodeGenerator. </center></td>
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
public interface FacadeStructure extends Facade {

}
