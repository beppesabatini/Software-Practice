package gof.designpatterns;

/**
 * <div class="javadoc-text"> An instance of the <b>Iterator</b> pattern,
 * described in <i>Design Patterns</i> [{@linkplain GangOfFour Gang of Four}],
 * pp. 257-271. Provide a way to access the elements of an aggregate object
 * sequentially without exposing its underlying representation.
 * <p/>
 * The Iterator design pattern was intentionally bundled into Java with its
 * second release, and a few more variants have been added since then. Java
 * programmers will rarely need to reimplement this pattern. One such instance
 * might be when the sort order of a collection changes dynamically; for
 * example, a display of clothing sorted by its appropriateness to the current
 * weather, or a list of athletes sorted by their statistics.
 * <p/>
 * The sample code in the manual displays several variants of <b>internal</b>
 * and <b>external iterators</b>. A UML Diagram is below. See also the
 * {@linkplain gof.designpatterns.structures.IteratorStructure Iterator
 * Structure} diagram.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram">
 * <img src="../ch05_04/iterator/UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../styles/gof.css">
 */
public interface Iterator extends GangOfFour {

}
