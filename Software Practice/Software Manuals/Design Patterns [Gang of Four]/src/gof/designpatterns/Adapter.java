package gof.designpatterns;

/**
 * <div class="javadoc-text">An instance of the <b>Adapter</b> pattern,
 * described in <i>Design Patterns</i> [{@linkplain GangOfFour Gang of Four}],
 * pp. 139-150. Convert the interface of a class into another interface clients
 * expect. Adapter lets classes work together that couldn't otherwise because of
 * incompatible interfaces.
 * <p/>
 * An Adapter pattern can be useful in situations such as servicing third-party
 * requests with help from a fourth-party service. The manual notes (p. 219)
 * that the Adapter pattern makes things work <i>after</i> they're designed;
 * {@linkplain Bridge} makes them work <i>before</i> they are designed.
 * <p/>
 * In the UML diagram (below), a developer is using a library of graphics
 * widgets, and incorporates a text widget from a different text library, by
 * applying the "Adapter" pattern, and adapting the text widget into the
 * graphics library. See {@linkplain gof.ch04_01.adapter.drawing.TextShape
 * TextShape} for more details. See also the
 * {@linkplain gof.designpatterns.structures.AdapterStructure Adapter Structure}
 * diagram.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch04_01/adapter/drawing/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <style> div.javadoc-text { width: 580px; }</style>
 * <p/>
 * <style> div.javadoc-diagram {margin-left: 5px;}</style>
 */
public interface Adapter extends GangOfFour {

}
