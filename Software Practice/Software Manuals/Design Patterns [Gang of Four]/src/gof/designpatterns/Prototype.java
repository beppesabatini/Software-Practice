package gof.designpatterns;

/**
 * <div class="javadoc-text">An instance of the <b>Prototype</b> pattern,
 * described in <i>Design Patterns</i> [{@linkplain GangOfFour Gang of Four}],
 * pp. 117-126. Specify the kinds of objects to create using a prototypical
 * instance, and create new objects by copying this prototype.
 * <p/>
 * In this example (not from the manual), expensive calculations about
 * prioritizing Jobs are cached, as objects, in a widely available Prototype
 * object. The current priorities can then be cloned and accessed, and updated,
 * as needed. See also the
 * {@linkplain gof.designpatterns.structures.PrototypeStructure Prototype
 * Structure} diagram.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram">
 * <img src="../ch03_04/prototype/UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../styles/gof.css">
 */
public interface Prototype extends GangOfFour {

}
