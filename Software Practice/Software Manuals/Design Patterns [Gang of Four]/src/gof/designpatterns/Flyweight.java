package gof.designpatterns;

/**
 * <div class="javadoc-text">An instance of the <b>Flyweight</b> pattern,
 * described in <i>Design Patterns</i> [{@linkplain GangOfFour Gang of Four}],
 * pp. 195-206. Use sharing to support large numbers of fine-grained objects
 * efficiently.
 * <p/>
 * In the example in the manual, a Character object stores exactly one
 * character; the object is immutable, and can be reused repeatedly to implement
 * any number of instances of that Character. Information such as its font and
 * its position in the document is stored externally, in a GlyphContext object.
 * See {@linkplain gof.ch04_06.flyweight.Character Character} for more detail.
 * See also the {@linkplain gof.designpatterns.structures.FlyweightStructure
 * Flyweight Structure} diagram.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch04_06/flyweight/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <style> div.javadoc-text { width: 580px; }</style>
 * <p/>
 * <style> div.javadoc-diagram {margin-left: 5px;}</style>
 */
public interface Flyweight extends GangOfFour {

}
