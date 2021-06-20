package gof.designpatterns;

/**
 * <table width="580">
 * <tr>
 * <td>An example of the <b>Memento</b> pattern, described in <i>Design
 * Patterns</i> [{@linkplain GangOfFour Gang of Four}], pp. 283-291. Without
 * violating encapsulation, capture and externalize an object's internal state
 * so that the object can be restored to this state later.
 * <p/>
 * In the sample code in the manual, the current state of a Graphics document is
 * saved before an edit is implemented, in an object called a "Memento." Memento
 * objects must define two interfaces: a restricted one that lets clients hold
 * and copy mementos, and a privileged one that only the original object can use
 * to store and retrieve state in the memento (p. 14).
 * <p/>
 * For more information on the editing use case, see
 * {@linkplain gof.ch05_06.memento.MoveCommand MoveCommand}. For more on the
 * Memento pattern, see the
 * {@linkplain gof.designpatterns.structures.MementoStructure Memento Structure}
 * diagram.</td>
 * </tr>
 * <tr>
 * <td><img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch05_06/memento/UML%20Diagram.jpg"
 * /></td>
 * </tr>
 * </table>
 * 
 * <style> div.javadoc-text { width: 580px; }</style>
 * <p/>
 * <style> div.javadoc-diagram {margin-left: 5px;}</style>
 */
public interface Memento extends GangOfFour {

}
