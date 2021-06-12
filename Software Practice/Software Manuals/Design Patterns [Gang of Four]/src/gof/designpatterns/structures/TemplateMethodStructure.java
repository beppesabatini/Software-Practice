package gof.designpatterns.structures;

import gof.ch05_10.templatemethod.ViewTemplate;
import gof.ch05_10.templatemethod.DailyReportView;
import gof.designpatterns.TemplateMethod;

/**
 * <table class="javadoc-structure">
 * <tr>
 * <td><div class="diagram-title">Structure Diagram:
 * {@linkplain gof.designpatterns.TemplateMethod TemplateMethod} </div>
 * <p/>
 * <center>from Design Patterns [{@linkplain gof.designpatterns.GangOfFour Gang
 * of Four}], p. 327. ©1995 by Addison-Wesley.</center></td>
 * </tr>
 * <tr>
 * <td class="structure-diagram">
 * <img src="../../ch05_10/templatemethod/image-7078.png"/></td>
 * </tr>
 * <tr>
 * <td><center><i>Compared to the sample code:</i></center></td>
 * </tr>
 * <tr>
 * <td>
 * <ul>
 * <li><b><i>AbstractClass</i></b> — corresponds to the abstract class
 * {@linkplain ViewTemplate}</li>
 * <li><b><i>ConcreteClass</i></b> — corresponds to the concrete class
 * {@linkplain DailyReportView}</li>
 * </ul>
 * </td>
 * </tr>
 * </table>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public interface TemplateMethodStructure extends TemplateMethod {

}
