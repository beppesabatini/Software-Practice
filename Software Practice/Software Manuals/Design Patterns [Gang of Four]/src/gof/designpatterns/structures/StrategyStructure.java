package gof.designpatterns.structures;

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
 * <td class="structure-diagram"><img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch05_09/strategy/image-6971.png"/></td>
 * </tr>
 * <tr>
 * <td><center><i>Compared to the sample code:</i></center></td>
 * </tr>
 * <tr>
 * <td>
 * <ul>
 * <li><b><i>Context</i></b> — corresponds to the class
 * {@linkplain gof.ch05_09.strategy.Composition Composition}</li>
 * <li><b><i>Strategy</i></b> — corresponds to the interface
 * {@linkplain gof.ch05_09.strategy.Compositor Compositor}</li>
 * <p/>
 * <li><b><i>ConcreteStrategyA</i></b> — corresponds to the implementing class
 * {@linkplain gof.ch05_09.strategy.SimpleCompositor SimpleCompositor}</li>
 * <li><b><i>ConcreteStrategyB</i></b> — the implementing class
 * {@linkplain gof.ch05_09.strategy.TeXCompositor TeXCompositor}</li>
 * <li><b><i>ConcreteStrategyC</i></b> — the implementing class
 * {@linkplain gof.ch05_09.strategy.ArrayCompositor ArrayCompositor}</li>
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
public interface StrategyStructure extends Strategy {

}
