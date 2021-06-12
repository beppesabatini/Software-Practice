package gof.designpatterns;

import gof.ch02_05.lookandfeel.GUIFactory;
import gof.designpatterns.structures.AbstractFactoryStructure;

/**
 * <div class="javadoc-text">An instance of the <b>AbstractFactory</b> pattern,
 * described in <i>Design Patterns</i> [{@linkplain GangOfFour Gang of Four}],
 * pp. 87-95. Provide an interface for creating families of related or dependent
 * objects without specifying their concrete classes.
 * <p/>
 * In the example of {@linkplain GUIFactory} from the manual (p. 50), the
 * GUIFactoryBuilder is able to work from one interface, and deliver three
 * different factories to use with three different widget libraries. The UML
 * diagram is below. See also the {@linkplain AbstractFactoryStructure
 * AbstractFactory Structure} diagram.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram">
 * <img src="../ch02_05/lookandfeel/Factory UML Diagram.jpg"/> </div>
 * <link rel="stylesheet" href="../styles/gof.css">
 */
public interface AbstractFactory extends GangOfFour {

}
