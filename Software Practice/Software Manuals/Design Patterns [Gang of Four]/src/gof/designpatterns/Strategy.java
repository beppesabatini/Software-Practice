package gof.designpatterns;

/**
 * <div class="javadoc-text">An instance of the <b>Strategy</b> pattern,
 * described in <i>Design Patterns</i> [{@linkplain GangOfFour Gang of Four}],
 * pp. 315-323. Define a family of algorithms, encapsulate each one, and make
 * them interchangeable. The Strategy pattern lets the algorithm vary
 * independently from clients that use it.
 * <p/>
 * In Java, defining a Comparator to use with a sorting function is an example
 * of the Strategy design pattern. The Comparator is a Strategy object. In
 * another example, from the sample code from the manual, a text compositor
 * simply has the option of three different algorithms to calculate line breaks,
 * each of which is represented by a different Strategy object. The URL Diagram
 * is below. See also the
 * {@linkplain gof.designpatterns.structures.StrategyStructure
 * StrategyStructure} diagram.
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch05_09/strategy/UML%20Diagram.jpg"/>
 * </div>
 * 
 * <style> div.javadoc-text { width: 580px; }</style>
 * <p/>
 * <style> div.javadoc-diagram { margin-left: 5px;}</style>
 */
public interface Strategy extends GangOfFour {

}
