package gof.designpatterns;

/**
 * <div class="javadoc-text">An instance of the <b>Bridge</b> pattern, described
 * in <i>Design Patterns</i> [{@linkplain GangOfFour Gang of Four}], pp.
 * 151-161. Decouple an abstraction from its implementation so that the two can
 * vary independently.
 * <p/>
 * The manual notes (p. 219) that the {@linkplain Adapter} pattern makes things
 * work <i>after</i> they're designed; Bridge makes them work
 * <i>before</i> they are designed. Sometimes an Adapter or an
 * {@linkplain AbstractFactory} may be a simpler solution to the problem at
 * hand.
 * <p/>
 * In the sample code, an architect is using the Bridge pattern so that a
 * standardized abstract UI will be compatible with a wide range of platforms or
 * services yet to be encountered, some perhaps not yet written. See also the
 * {@linkplain gof.designpatterns.structures.BridgeStructure Bridge Structure} diagram.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch04_02/bridge/UML%20Diagram.jpg" />
 * </div> 
 * 
 * <style> div.javadoc-text { width: 580px; }</style>
 * <p/>
 * <style> div.javadoc-diagram {margin-left: 5px;}</style>
 */
public interface Bridge extends GangOfFour {

}
