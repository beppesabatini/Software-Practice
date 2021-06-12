package gof.designpatterns.structures;

import gof.ch05_02.command.*;
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
 * <td class="structure-diagram">
 * <img src="../../ch05_02/command/image-6159.png"/></td>
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
 * {@linkplain SimpleCommand}</li>
 * </ul>
 * </td>
 * </tr>
 * </table>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public interface CommandStructure extends Command {

}
