package gof.designpatterns.structures;

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
 * <td class="structure-diagram"><img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch05_10/templatemethod/image-7078.png"/></td>
 * </tr>
 * <tr>
 * <td><center><i>Compared to the sample code:</i></center></td>
 * </tr>
 * <tr>
 * <td>
 * <ul>
 * <li><b><i>AbstractClass</i></b> — corresponds to the abstract class
 * {@linkplain gof.ch05_10.templatemethod.ViewTemplate ViewTemplate}</li>
 * <li><b><i>ConcreteClass</i></b> — corresponds to the concrete class
 * {@linkplain gof.ch05_10.templatemethod.DailyReportView DailyReportView}</li>
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
public interface TemplateMethodStructure extends TemplateMethod {

}
