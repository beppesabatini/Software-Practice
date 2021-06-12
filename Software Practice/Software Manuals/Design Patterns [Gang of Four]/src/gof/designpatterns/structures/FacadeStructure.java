package gof.designpatterns.structures;

import gof.ch04_05.facade.Compiler;
import gof.ch04_05.facade.Scanner;
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
 * <td class="structure-diagram">
 * <img src="../../ch04_05/facade/image-5672.png"/></td>
 * </tr>
 * <tr>
 * <td><center>The client is able to launch one command, {@linkplain Compiler},
 * and does not have to learn the details about subsystems such as
 * {@linkplain Scanner}, Parser, and RISCCodeGenerator. </center></td>
 * </tr>
 * </table>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public interface FacadeStructure extends Facade {

}
