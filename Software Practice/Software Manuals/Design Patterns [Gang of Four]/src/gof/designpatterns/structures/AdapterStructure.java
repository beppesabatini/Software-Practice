package gof.designpatterns.structures;

import gof.designpatterns.Adapter;

/**
 * <table class="javadoc-structure">
 * <tr>
 * <td><div class="diagram-title">Structure Diagram:
 * {@linkplain gof.designpatterns.Adapter Adapter} </div>
 * <p/>
 * <center>from Design Patterns [{@linkplain gof.designpatterns.GangOfFour Gang
 * of Four}], p. 141. ©1995 by Addison-Wesley.</center></td>
 * </tr>
 * <tr>
 * <td class="structure-diagram"><img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch04_01/adapter/drawing/image-5211.png"
 * /></td>
 * </tr>
 * <tr>
 * <td><center><i>Compared to the sample code:</i></center></td>
 * </tr>
 * <tr>
 * <td>
 * <ul>
 * <li><b><i>Client</i></b> — collaborates with objects conforming to the Target
 * interface</li>
 * <li><b><i>Target</i></b> — corresponds to the interface
 * {@linkplain gof.ch04_01.adapter.drawing.GraphicalElementGUI
 * GraphicalElementGUI}</li>
 * <li><b><i>Adaptee</i></b> — corresponds to the class
 * {@linkplain gof.ch04_01.adapter.text.TextView TextView}</li>
 * <li><b><i>Adapter</i></b> — corresponds to the class
 * {@linkplain gof.ch04_01.adapter.drawing.TextShape TextShape}</li>
 * </ul>
 * </td>
 * </tr>
 * <tr>
 * <td><center><i>Note that the diagram above is from the old C++ version, and
 * uses double inheritance. In the current Java version, we worked around this,
 * by having the Target and the Adapter both conform to the same
 * Interface.</i></center></td>
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
public interface AdapterStructure extends Adapter {

}
