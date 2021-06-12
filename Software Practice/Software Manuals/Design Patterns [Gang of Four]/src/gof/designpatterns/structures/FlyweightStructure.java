package gof.designpatterns.structures;

import gof.ch04_06.flyweight.*;
import gof.designpatterns.Flyweight;

/**
 * <table class="javadoc-structure">
 * <tr>
 * <td><div class="diagram-title">Structure Diagram:
 * {@linkplain gof.designpatterns.Flyweight Flyweight} </div>
 * <p/>
 * <center>from Design Patterns [{@linkplain gof.designpatterns.GangOfFour Gang
 * of Four}], p. 198. ©1995 by Addison-Wesley.</center></td>
 * </tr>
 * <tr>
 * <td class="structure-diagram">
 * <img src="../../ch04_06/flyweight/image-5786.png" /></td>
 * </tr>
 * <tr>
 * <td><center><i>Compared to the sample code:</i></center></td>
 * </tr>
 * <tr>
 * <td>
 * <ul>
 * <li><b><i>FlyweightFactory</i></b> — corresponds to the class
 * {@linkplain GlyphFactory}</li>
 * <li><b><i>Flyweight</i></b> — corresponds to the class
 * {@linkplain Glyph}</li>
 * </ul>
 * <ul>
 * <li><b><i>ConcreteFlyweight</i></b> — corresponds to the class
 * {@linkplain gof.ch04_06.flyweight.Character Character}</li>
 * <li><b><i>UnsharedConcreteFlyweight</i></b> — the manual mentions Rows or
 * Columns for the characters</li>
 * </ul>
 * </td>
 * </table>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public interface FlyweightStructure extends Flyweight {

}
