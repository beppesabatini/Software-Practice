package gof.designpatterns;

/**
 * <div class="javadoc-text">An instance of the <b>Command</b> pattern,
 * described in <i>Design Patterns</i> [{@linkplain GangOfFour Gang of Four}],
 * pp. 233-242. Encapsulate a request as an object, thereby letting you
 * parameterize clients with different requests, queue or log requests, and
 * support undoable operations. See also the
 * {@linkplain gof.designpatterns.structures.CommandStructure Command Structure}
 * diagram.
 * <table>
 * <tr>
 * <td>Use the <code>Command</code> pattern when you want to:</td>
 * </tr>
 * <tr>
 * <td>&nbsp;• Parameterize objects such as menu items with an action to
 * perform. <code>Commands</code> are an object-oriented replacement for
 * callback functions.</td>
 * </tr>
 * <tr>
 * <td>&nbsp;• Specify, queue, and execute requests at different times. A
 * <code>Command</code> object can have a lifetime independent of the original
 * request.</td>
 * </tr>
 * <tr>
 * <td>&nbsp;• Support "undo." The <code>Command</code> pattern's execute()
 * operation can store state for reversing its effects in the
 * <code>Command</code> itself.</td>
 * </tr>
 * <tr>
 * <td>&nbsp;• Support the recording of changes so that they can be reapplied in
 * case of a system crash.</td>
 * </tr>
 * <tr>
 * <td>&nbsp;• Structure a system around high-level operations build on
 * primitives' operations. Such a structure is common in information systems
 * that support atomic (all-or-nothing) transactions.</td>
 * </tr>
 * </table>
 * </div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram">
 * <img src="../ch05_02/command/UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../styles/gof.css">
 */
public interface Command extends GangOfFour {

	public void execute();
}
