package gof.designpatterns;

/**
 * <div class="javadoc-text">An instance of the <b>State</b> pattern, described
 * in <i>Design Patterns</i> [{@linkplain GangOfFour Gang of Four}], pp.
 * 305-313. Allow an object to alter its behavior when its internal state
 * changes. The object will appear to change its class.
 * <p/>
 * The point of the sample code is that the TCPConnection class supports several
 * States--Established, Listening, Closed, and so on. Each State can change
 * itself into a different State when its work is done. If a connection reaches
 * the Established State, it can change itself to the Listening State; when the
 * work of the Listening State is done, it can change itself to Closed. Thus,
 * the State design pattern can be used to build up a functional flow chart of
 * connected States. All the logic for each State is in its own State object.
 * See also {@linkplain gof.designpatterns.structures.StateStructure State
 * Structure}.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch05_08/state/UML%20Diagram.jpg"
 * /> </div> 
 * 
 * <style> div.javadoc-text { width: 580px; }</style>
 * <p/>
 * <style> div.javadoc-diagram {margin-left: 5px;}</style>
 */
public interface State extends GangOfFour {

}
