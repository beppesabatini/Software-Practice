package gof.designpatterns.structures;

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
 * <td class="structure-diagram"><img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch04_06/flyweight/image-5786.png"
 * /></td>
 * </tr>
 * <tr>
 * <td><center><i>Compared to the sample code:</i></center></td>
 * </tr>
 * <tr>
 * <td>
 * <ul>
 * <li><b><i>FlyweightFactory</i></b> — corresponds to the class
 * {@linkplain gof.ch04_06.flyweight.GlyphFactory GlyphFactory}</li>
 * <li><b><i>Flyweight</i></b> — corresponds to the class
 * {@linkplain gof.ch04_06.flyweight.Glyph Glyph}</li>
 * </ul>
 * <ul>
 * <li><b><i>ConcreteFlyweight</i></b> — corresponds to the class
 * {@linkplain gof.ch04_06.flyweight.Character Character}</li>
 * <li><b><i>UnsharedConcreteFlyweight</i></b> — the manual mentions Rows or
 * Columns structuring the Characters</li>
 * </ul>
 * </td>
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
public interface FlyweightStructure extends Flyweight {

}
