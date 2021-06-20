package gof.designpatterns.structures;

import gof.designpatterns.Command;

/**
 * <table class="javadoc-structure">
 * <tr>
 * <td><div class="diagram-title">Structure Diagram:
 * {@linkplain gof.designpatterns.Command Command} </div>
 * <p/>
 * <center>from Design Patterns [{@linkplain gof.designpatterns.GangOfFour Gang
 * of Four}], p. 236. ©1995 by Addison-Wesley.</center></td>
 * </tr>
 * <tr>
 * <td class="structure-diagram"><img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch05_02/command/image-6159.png"/></td>
 * </tr>
 * <tr>
 * <td><center><i>Compared to the sample code:</i></center></td>
 * </tr>
 * <tr>
 * <td>
 * <ul>
 * <li><b><i>Client</i></b> — a word-processing application (not in the sample
 * code)</li>
 * <li><b><i>Invoker</i></b> — a menu-item (not in the sample code)</li>
 * <li><b><i>Receiver</i></b> — a word-processing document (not in the sample
 * code)</li>
 * <li><b><i>Command</i></b> — corresponds to the abstract class
 * {@linkplain gof.ch05_02.command.Command Command}</li>
 * <li><b><i>ConcreteCommand</i></b> — corresponds to the concrete class
 * {@linkplain gof.ch05_02.command.SimpleCommand SimpleCommand}</li>
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
public interface CommandStructure extends Command {

}
