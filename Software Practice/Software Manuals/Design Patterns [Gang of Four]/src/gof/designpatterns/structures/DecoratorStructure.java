package gof.designpatterns.structures;

import gof.ch04_04.decorator.*;
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
 * <td class="structure-diagram">
 * <img src="../../ch04_04/decorator/image-5559.png" /></td>
 * </tr>
 * <tr>
 * <td><center><i>Compared to the sample code:</i></center></td>
 * </tr>
 * <tr>
 * <td>
 * <ul>
 * <li><b><i>Component</i></b> — corresponds to the abstract class
 * {@linkplain VisualComponent}</li>
 * <li><b><i>ConcreteComponent</i></b> — corresponds to its concrete subclass
 * {@linkplain TextView}</li>
 * </ul>
 * <ul>
 * <li><b><i>Decorator</i></b> — corresponds to the abstract class
 * {@linkplain Decorator}</li>
 * <li><b><i>ConcreteDecoratorA</i></b> — corresponds to its concrete subclass
 * {@linkplain BorderDecorator}</li>
 * <li><b><i>ConcreteDecoratorB</i></b> — its concrete subclass
 * {@linkplain ScrollDecorator}</li>
 * </ul>
 * </td>
 * </tr>
 * </table>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public interface DecoratorStructure extends Decorator {

}
