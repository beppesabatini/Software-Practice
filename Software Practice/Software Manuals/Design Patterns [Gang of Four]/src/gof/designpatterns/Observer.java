package gof.designpatterns;

/**
 * <div class="javadoc-text">An instance of the <b>Observer</b> pattern,
 * described in <i>Design Patterns</i> [{@linkplain GangOfFour Gang of Four}],
 * pp. 293-303. Define a one-to-many dependency between objects so that when one
 * object changes state, all its dependents are notified and updated
 * automatically.
 * <p/>
 * This pattern is more often called <b>subject-observer</b> or
 * <b>publisher-subscriber</b>. It is a standard element in many UIs these days,
 * with widgets sending out alerts, and controller objects subscribing to those
 * alerts and responding. The manual gives the example of a report acting as a
 * publisher, with a spreadsheet, a bar chart, and a pie chart acting as
 * subscribers and responding to changes. In the sample code from the manual, a
 * ClockTimer is the subject (the publisher), and an AnalogClock and a
 * DigitalClock are the Observers (the Subscribers).
 * <p/>
 * The UML diagram for the sample code is below. See also the
 * {@linkplain gof.designpatterns.structures.ObserverStructure Observer
 * Structure} diagram.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch05_07/observer/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <style> div.javadoc-text { width: 580px; }</style>
 * <p/>
 * <style> div.javadoc-diagram {margin-left: 5px;}</style>
 */
public interface Observer extends GangOfFour {

}
