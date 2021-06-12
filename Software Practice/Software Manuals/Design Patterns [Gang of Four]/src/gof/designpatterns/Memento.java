package gof.designpatterns;

import gof.ch05_06.memento.MoveCommand;

/**
 * <table width="580">
 * <tr>
 * <td>An example of the <b>Memento</b> pattern, described in <i>Design
 * Patterns</i> [{@linkplain GangOfFour Gang of Four}], pp. 283-291. Without
 * violating encapsulation, capture and externalize an object's internal state
 * so that the object can be restored to this state later.
 * <p/>
 * In the sample code in the manual, the current state of a Graphics document is
 * saved before an edit is implemented, in an object called a "Memento." It must
 * be readable only by the source object. If the change needs to be undone, the
 * document can be returned to its saved state. This might be as straightforward
 * as maintaining a two-headed queue of clones of the Graphic, allowing the
 * editor to move backwards and forwards through saved states, un-do, un-do,
 * re-do, re-do.
 * <p/>
 * For more information on the editing use case, see {@linkplain MoveCommand}.
 * For more on the Memento pattern, see the
 * {@linkplain gof.designpatterns.structures.MementoStructure Memento Structure}
 * diagram.</td>
 * </tr>
 * <tr>
 * <td><img src="../ch05_06/memento/UML Diagram.jpg" /></td>
 * </tr>
 * </table>
 * <link rel="stylesheet" href="../styles/gof.css">
 */
public interface Memento extends GangOfFour {

}
