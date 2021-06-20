package gof.designpatterns;

/**
 * <div class="javadoc-text">An instance of the <b>FactoryMethod</b> pattern,
 * described in <i>Design Patterns</i> [{@linkplain GangOfFour Gang of Four}],
 * pp. 107-116. Define an interface for creating an object, but let subclasses
 * decide which class to instantiate. FactoryMethod lets a class defer
 * instantiation to subclasses.
 * <p/>
 * The UML Diagram is below. As can be seen there, MazeFactory is subclassed by
 * {@linkplain gof.ch03_03.factorymethod.BombedMazeFactory BombedMazeFactory}.
 * All of the public functions in BombedMazeFactory can be considered
 * FactoryMethods. None of their return values are hard-coded in the original
 * MazeFactory; and all are decided in its subclass. The actual FactoryMethod
 * itself is BombedMazeFactory::makeWallWithoutDoor(), which returns a
 * BombedWallWithoutDoor object. See also the
 * {@linkplain gof.designpatterns.structures.FactoryMethodStructure
 * FactoryMethod Structure} diagram.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch03_03/factorymethod/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <style> div.javadoc-text { width: 580px; }</style>
 * <p/>
 * <style> div.javadoc-diagram {margin-left: 5px;}</style>
 */
public interface FactoryMethod extends GangOfFour {

}
