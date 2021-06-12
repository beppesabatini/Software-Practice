package gof.designpatterns.structures;

import gof.ch03_05.singleton.*;
import gof.designpatterns.*;

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
 * <td class="structure-diagram">
 * <img src="../../ch03_05/singleton/image-5093.png"/></td>
 * </tr>
 * <tr>
 * <td><center><i>Compared to the sample code:</i></center></td>
 * </tr>
 * <tr>
 * <td>
 * <ul>
 * <li><b><i>Singleton</i></b> — corresponds to three classes,
 * {@linkplain StandardMazeFactory}, {@linkplain EnchantedMazeFactory}, and
 * {@linkplain BombedMazeFactory}</li>
 * </ul>
 * </td>
 * </table>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public interface SingletonStructure extends Singleton {

}
