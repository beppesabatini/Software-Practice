package gof.designpatterns;

/**
 * <div class="javadoc-text">An instance of the <b>Singleton</b> pattern,
 * described in <i>Design Patterns</i> [{@linkplain GangOfFour Gang of Four}],
 * pp. 127-134. Ensure a class only has one instance, and provide a global point
 * of access to it.
 * <p/>
 * The most popular of the GOF design patterns, Singletons are used to save on
 * initialization expenses. They are created only once, the first time they are
 * needed, and are globally available (and usually read-only) after that. In the
 * example from the manual, one basic Singleton has two subclasses which are
 * also Singletons.
 * <p/>
 * See also the {@linkplain gof.designpatterns.structures.SingletonStructure
 * Singleton Structure} diagram.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram">
 * <img src="../ch03_05/singleton/UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../styles/gof.css">
 */
public interface Singleton extends GangOfFour {

}
