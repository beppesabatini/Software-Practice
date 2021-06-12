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
 * <div class="javadoc-diagram">
 * <img src="../ch05_11/visitor/UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../styles/gof.css">
 */
public interface Visitor extends GangOfFour {

}
