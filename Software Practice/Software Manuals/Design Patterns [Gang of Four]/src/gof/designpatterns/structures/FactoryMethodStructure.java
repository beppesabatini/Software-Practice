package gof.designpatterns.structures;

import gof.ch03_01.abstractfactory.*;
import gof.ch03_03.factorymethod.*;
import gof.designpatterns.FactoryMethod;

/**
 * <table class="javadoc-structure">
 * <tr>
 * <td><div <div class="diagram-title">Structure Diagram:
 * {@linkplain gof.designpatterns.FactoryMethod FactoryMethod} </div>
 * <p/>
 * <center>from Design Patterns [{@linkplain gof.designpatterns.GangOfFour Gang
 * of Four}], p. 108. ©1995 by Addison-Wesley.</center></td>
 * </tr>
 * <tr>
 * <td class="structure-diagram">
 * <img src="../../ch03_03/factorymethod/image-4881.png" /></td>
 * </tr>
 * <tr>
 * <td><center><i>Compared to the sample code:</i></center></td>
 * </tr>
 * <tr>
 * <td>
 * <ul>
 * <li><b><i>Product</i></b> — corresponds to the (concrete) class
 * {@linkplain MazeGame}</li>
 * <li><b><i>ConcreteProduct</i></b> — the subclass BombedMazeGame (not included
 * in the sample code)</li>
 * </ul>
 * <ul>
 * <li><b><i>Creator</i></b> — corresponds to the (concrete) class
 * {@linkplain MazeFactory}</li>
 * <li><b><i>ConcreteCreator</i></b> — corresponds to the class
 * {@linkplain BombedMazeFactory}.</li>
 * </ul>
 * </td>
 * </tr>
 * <tr>
 * <td><center>In the sample code, the actual FactoryMethod itself is
 * BombedMazeFactory::makeWallWithoutDoor(), which returns a
 * {@linkplain BombedWallWithoutDoor} object.</center></td>
 * </tr>
 * </table>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public interface FactoryMethodStructure extends FactoryMethod {

}
