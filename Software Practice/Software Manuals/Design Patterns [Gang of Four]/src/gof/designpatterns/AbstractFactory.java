package gof.designpatterns;

/**
 * <div class="javadoc-text">An instance of the <b>AbstractFactory</b> pattern,
 * described in <i>Design Patterns</i> [{@linkplain GangOfFour Gang of Four}],
 * pp. 87-95. Provide an interface for creating families of related or dependent
 * objects without specifying their concrete classes.
 * <p/>
 * The manual gives two examples. In the first example (section 2.5), a client
 * uses an interface {@linkplain gof.ch02_05.lookandfeel.GUIFactory GUIFactory}
 * to define three different widget libraries for three different platforms. In
 * the second (section 3.1), a user subclasses
 * {@linkplain gof.ch03_01.abstractfactory.MazeFactory MazeFactory}, which makes
 * PAC-MAN-style mazes, to construct an
 * {@linkplain gof.ch03_01.abstractfactory.EnchantedMazeFactory
 * EnchantedMazeFactory}, which makes Legend-of-Zelda-style mazes. The UML
 * diagram is below. See also the
 * {@linkplain gof.designpatterns.structures.AbstractFactoryStructure
 * AbstractFactory Structure} diagram.</div>
 * <p/>
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch03_01/abstractfactory/UML%20Diagram.jpg"/>
 * </div>
 * 
 * <style> div.javadoc-text { width: 580px; }</style>
 * <p/>
 * <style> div.javadoc-diagram { margin-left: 5px; }</style>
 */
public interface AbstractFactory extends GangOfFour {

}
