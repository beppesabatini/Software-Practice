package gof.designpatterns.structures;

import gof.designpatterns.Singleton;

/**
 * <table class="javadoc-structure">
 * <tr>
 * <td><div class="diagram-title">Structure Diagram:
 * {@linkplain gof.designpatterns.Singleton Singleton} </div>
 * <p/>
 * <center>from Design Patterns [{@linkplain gof.designpatterns.GangOfFour Gang
 * of Four}], p. 127. ©1995 by Addison-Wesley.</center></td>
 * </tr>
 * <tr>
 * <td class="structure-diagram"><img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch03_05/singleton/image-5093.png"/></td>
 * </tr>
 * <tr>
 * <td><center><i>Compared to the sample code:</i></center></td>
 * </tr>
 * <tr>
 * <td>
 * <ul>
 * <li><b><i>Singleton</i></b> — corresponds to three classes,
 * {@linkplain gof.ch03_05.singleton.StandardMazeFactory StandardMazeFactory},
 * {@linkplain gof.ch03_05.singleton.EnchantedMazeFactory EnchantedMazeFactory},
 * and {@linkplain gof.ch03_05.singleton.BombedMazeFactory
 * BombedMazeFactory}</li>
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
public interface SingletonStructure extends Singleton {

}
