package gof.designpatterns.structures;

import gof.ch05_09.strategy.*;
import gof.designpatterns.Strategy;

/**
 * <table class="javadoc-structure">
 * <tr>
 * <td><div class="diagram-title">Structure Diagram:
 * {@linkplain gof.designpatterns.Strategy Strategy} </div>
 * <p/>
 * <center>from Design Patterns [{@linkplain gof.designpatterns.GangOfFour Gang
 * of Four}], p. 316. ©1995 by Addison-Wesley.</center></td>
 * </tr>
 * <tr>
 * <td class="structure-diagram">
 * <img src="../../ch05_09/strategy/image-6971.png"/></td>
 * </tr>
 * <tr>
 * <td><center><i>Compared to the sample code:</i></center></td>
 * </tr>
 * <tr>
 * <td>
 * <ul>
 * <li><b><i>Context</i></b> — corresponds to the class
 * {@linkplain Composition}</li>
 * <li><b><i>Strategy</i></b> — corresponds to the interface
 * {@linkplain Compositor}</li>
 * <p/>
 * <li><b><i>ConcreteStrategyA</i></b> — corresponds to the implementing class
 * {@linkplain SimpleCompositor}</li>
 * <li><b><i>ConcreteStrategyB</i></b> — the implementing class
 * {@linkplain TeXCompositor}</li>
 * <li><b><i>ConcreteStrategyC</i></b> — the implementing class
 * {@linkplain ArrayCompositor}</li>
 * </ul>
 * </td>
 * </tr>
 * </table>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public interface StrategyStructure extends Strategy {

}
