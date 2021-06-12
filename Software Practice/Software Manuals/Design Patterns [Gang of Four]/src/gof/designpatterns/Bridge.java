package gof.designpatterns;

import gof.designpatterns.structures.BridgeStructure;

/**
 * <div class="javadoc-text">An instance of the <b>Bridge</b> pattern, described
 * in <i>Design Patterns</i> [{@linkplain GangOfFour Gang of Four}], pp.
 * 151-161. Decouple an abstraction from its implementation so that the two can
 * vary independently.
 * <p/>
 * The manual notes (p. 219) that the {@linkplain Adapter} pattern makes things
 * work <i>after</i> they're designed; {@linkplain Bridge} makes them work
 * <i>before</i> they are designed. Sometimes an Adapter or an
 * {@linkplain AbstractFactory} may be a simpler solution to the problem at
 * hand.
 * <p/>
 * In the sample code, an architect is using the Bridge pattern so that a
 * standardized abstract UI will be compatible with a wide range of platforms or
 * services yet to be encountered, some perhaps not yet written. See also the
 * {@linkplain BridgeStructure Bridge Structure} diagram.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="../ch04_02/bridge/UML Diagram.jpg" />
 * </div> <link rel="stylesheet" href="../styles/gof.css">
 */
public interface Bridge extends GangOfFour {

}
