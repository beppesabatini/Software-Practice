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
 * <img src="https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch05_04/iterator/UML%20Diagram.jpg" /> </div>
 * 
 * <style> div.javadoc-text { width: 580px; }</style>
 * <p/>
 * <style> div.javadoc-diagram {margin-left: 5px;}</style>
 */
public interface Iterator extends GangOfFour {

}
