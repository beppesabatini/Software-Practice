package gof.designpatterns;

/**
 * <div class="javadoc-text">An instance of the <b>Facade></b> pattern,
 * described in <i>Design Patterns</i> [{@linkplain GangOfFour Gang of Four}],
 * pp. 185-193. Provide a unified interface to a set of interfaces in a
 * subsystem. Facade defines a higher-level interface that makes the subsystem
 * easier to use.
 * <p/>
 * In the example as given in the manual, the reasoning is that most users will
 * not care about internal details of a certain Compiler, such as its parser and
 * scanner and the code generator. Providing this Facade simplifies use for the
 * majority of users, while still allowing access to system details for those
 * who need it. The UML Diagram is below. See also the
 * {@linkplain gof.designpatterns.structures.FacadeStructure Facade Structure}
 * diagram.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch04_05/facade/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <style> div.javadoc-text { width: 580px; }</style>
 * <p/>
 * <style> div.javadoc-diagram {margin-left: 5px;}</style>
 */
public interface Facade extends GangOfFour {

}
