package gof.designpatterns;

import gof.ch03_03.factorymethod.BombedMazeFactory;

/**
 * <div class="javadoc-text">An instance of the <b>FactoryMethod</b> pattern,
 * described in <i>Design Patterns</i> [{@linkplain GangOfFour Gang of Four}],
 * pp. 107-116. Define an interface for creating an object, but let subclasses
 * decide which class to instantiate. FactoryMethod lets a class defer
 * instantiation to subclasses.
 * <p/>
 * The UML Diagram is below. As can be seen there, MazeFactory is subclassed by
 * {@linkplain BombedMazeFactory}. All of the public functions in
 * BombedMazeFactory can be considered FactoryMethods. None of their return
 * values are hard-coded in the original MazeFactory; and all are decided in its
 * subclass. The actual FactoryMethod itself is
 * BombedMazeFactory::makeWallWithoutDoor(), which returns a
 * BombedWallWithoutDoor object. See also the
 * {@linkplain gof.designpatterns.structures.FactoryMethodStructure
 * FactoryMethod Structure} diagram.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram">
 * <img src="../ch03_03/factorymethod/UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../styles/gof.css">
 */
public interface FactoryMethod extends GangOfFour {

}
