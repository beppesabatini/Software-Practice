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
 * <div class="javadoc-diagram"> <img src="../ch04_05/facade/UML Diagram.jpg" />
 * </div> <link rel="stylesheet" href="../styles/gof.css">
 */
public interface Facade extends GangOfFour {

}
