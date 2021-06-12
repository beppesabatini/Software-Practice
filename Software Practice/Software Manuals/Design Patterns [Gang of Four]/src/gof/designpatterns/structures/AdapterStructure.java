package gof.designpatterns.structures;

import gof.ch04_01.adapter.drawing.*;
import gof.ch04_01.adapter.text.*;
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
 * "../../ch04_01/adapter/drawing/image-5211.png" /></td>
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
 * {@linkplain GraphicalElementGUI}</li>
 * <li><b><i>Adaptee</i></b> — corresponds to the class
 * {@linkplain TextView}</li>
 * <li><b><i>Adapter</i></b> — corresponds to the class
 * {@linkplain TextShape}</li>
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
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public interface AdapterStructure extends Adapter {

}
