package gof.designpatterns.structures;

import gof.designpatterns.Decorator;

/**
 * <table class="javadoc-structure">
 * <tr>
 * <td><div class="diagram-title">Structure Diagram:
 * {@linkplain gof.designpatterns.Decorator Decorator} </div>
 * <p/>
 * <center>from Design Patterns [{@linkplain gof.designpatterns.GangOfFour Gang
 * of Four}], p. 177. ©1995 by Addison-Wesley.</center></td>
 * </tr>
 * <tr>
 * <td class="structure-diagram"><img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch04_04/decorator/image-5559.png"
 * /></td>
 * </tr>
 * <tr>
 * <td><center><i>Compared to the sample code:</i></center></td>
 * </tr>
 * <tr>
 * <td>
 * <ul>
 * <li><b><i>Component</i></b> — corresponds to the abstract class
 * {@linkplain gof.ch04_04.decorator.VisualComponent VisualComponent}</li>
 * <li><b><i>ConcreteComponent</i></b> — corresponds to its concrete subclass
 * {@linkplain gof.ch04_04.decorator.TextView TextView}</li>
 * </ul>
 * <ul>
 * <li><b><i>Decorator</i></b> — corresponds to the abstract class
 * {@linkplain gof.ch04_04.decorator.Decorator Decorator}</li>
 * <li><b><i>ConcreteDecoratorA</i></b> — corresponds to its concrete subclass
 * {@linkplain gof.ch04_04.decorator.BorderDecorator BorderDecorator}</li>
 * <li><b><i>ConcreteDecoratorB</i></b> — its concrete subclass
 * {@linkplain gof.ch04_04.decorator.ScrollDecorator ScrollDecorator}</li>
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
public interface DecoratorStructure extends Decorator {

}
