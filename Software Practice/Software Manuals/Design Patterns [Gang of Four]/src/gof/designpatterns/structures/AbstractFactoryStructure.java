package gof.designpatterns.structures;

import gof.designpatterns.AbstractFactory;

/**
 * <table class="javadoc-structure">
 * <tr>
 * <td><div class="diagram-title">Structure Diagram:
 * {@linkplain gof.designpatterns.AbstractFactory AbstractFactory} </div>
 * <p/>
 * <center>from Design Patterns [{@linkplain gof.designpatterns.GangOfFour Gang
 * of Four}], p. 88. ©1995 by Addison-Wesley.</center></td>
 * </tr>
 * <tr>
 * <td class="structure-diagram"><img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch03_01/abstractfactory/image-4655.png"
 * /></td>
 * </tr>
 * <tr>
 * <td><center><i>Compared to the sample code:</i></center></td>
 * </tr>
 * <tr>
 * <td>
 * <ul>
 * <li><b><i>Client</i></b> — uses only interfaces declared by AbstractFactory
 * and AbstractProduct classes.</li>
 * </ul>
 * <ul>
 * <li><b><i>AbstractFactory</i></b> and <b><i>ConcreteFactory1</i></b> — as
 * implemented in the sample code, the concrete class
 * {@linkplain gof.ch03_01.abstractfactory.MazeFactory MazeFactory} serves both
 * these roles.</li>
 * <li><b><i>ConcreteFactory2</i></b> — corresponds to the subclass
 * {@linkplain gof.ch03_01.abstractfactory.EnchantedMazeFactory
 * EnchantedMazeFactory}</li>
 * </ul>
 * <ul>
 * <li><b><i>AbstractProductA</i></b> and <b><i>ProductA1</i></b> — As
 * implemented in the sample code, the concrete class
 * {@linkplain gof.ch03_00.intro.WallWithDoor WallWithDoor} serves both these
 * roles.</li>
 * <li><b><i>ProductA2</i></b> — corresponds to the subclass
 * {@linkplain gof.ch03_01.abstractfactory.WallWithDoorNeedingSpell
 * WallWithDoorNeedingSpell}.</li>
 * </ul>
 * </td>
 * </tr>
 * <tr>
 * <td><center><i>Above is one of two examples of AbstractFactories in the
 * sample code. This one is from Chapter 3. The other,
 * {@linkplain gof.ch02_05.lookandfeel.GUIFactory GUIFactory} in Section 2.5, is
 * a closer match to the pattern diagram here. </i></center></td>
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
public interface AbstractFactoryStructure extends AbstractFactory {

}
