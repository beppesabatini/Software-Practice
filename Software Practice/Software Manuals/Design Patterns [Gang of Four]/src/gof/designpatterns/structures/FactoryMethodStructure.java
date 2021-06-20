package gof.designpatterns.structures;

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
 * <td class="structure-diagram"><img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch03_03/factorymethod/image-4881.png"
 * /></td>
 * </tr>
 * <tr>
 * <td><center><i>Compared to the sample code:</i></center></td>
 * </tr>
 * <tr>
 * <td>
 * <ul>
 * <li><b><i>Product</i></b> — corresponds to the (concrete) class
 * {@linkplain gof.ch03_01.abstractfactory.MazeGame MazeGame}</li>
 * <li><b><i>ConcreteProduct</i></b> — the subclass BombedMazeGame (not included
 * in the sample code)</li>
 * </ul>
 * <ul>
 * <li><b><i>Creator</i></b> — corresponds to the (concrete) class
 * {@linkplain gof.ch03_01.abstractfactory.MazeFactory MazeFactory}</li>
 * <li><b><i>ConcreteCreator</i></b> — corresponds to the class
 * {@linkplain gof.ch03_03.factorymethod.BombedMazeFactory
 * BombedMazeFactory}</li>
 * </ul>
 * </td>
 * </tr>
 * <tr>
 * <td><center><i>In the sample code, the actual FactoryMethod itself is
 * BombedMazeFactory::makeWallWithoutDoor(), which returns a
 * {@linkplain gof.ch03_03.factorymethod.BombedWallWithoutDoor
 * BombedWallWithoutDoor} object.</i></center></td>
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
public interface FactoryMethodStructure extends FactoryMethod {

}
