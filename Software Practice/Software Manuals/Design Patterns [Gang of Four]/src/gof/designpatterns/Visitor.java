package gof.designpatterns;

/**
 * <div class="javadoc-text">An instance of the "Visitor" pattern, described in
 * <i>Design Patterns</i> [{@linkplain GangOfFour Gang of Four}], pp. 331-344.
 * Represent an operation to be performed on the elements of an object
 * structure. Visitor lets you define a new operation without changing the
 * classes of the elements on which it operates.
 * <p/>
 * In the example in the manual, one complex bundle of equipment (an example of
 * the {@linkplain Composite} pattern) is traversed--"visited"--by two different
 * Visitors. One Visitor calculates the total price of all its parts, but the
 * second visitor visits the same data, and builds an inventory of all its
 * components. The UML diagram is below. See also the
 * {@linkplain gof.designpatterns.structures.VisitorStructure Visitor Structure}
 * diagram.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch05_11/visitor/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <style> div.javadoc-text { width: 580px; }</style>
 * <p/>
 * <style> div.javadoc-diagram {margin-left: 5px;}</style>
 */
public interface Visitor extends GangOfFour {

}
