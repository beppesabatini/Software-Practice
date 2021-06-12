package gof.designpatterns.structures;

import gof.ch05_03.interpreter.*;
import gof.designpatterns.Interpreter;

/**
 * <table class="javadoc-structure">
 * <tr>
 * <td><div class="diagram-title">Structure Diagram:
 * {@linkplain gof.designpatterns.Interpreter Interpreter} </div>
 * <p/>
 * <center>from Design Patterns [{@linkplain gof.designpatterns.GangOfFour Gang
 * of Four}], pp. 245-246. ©1995 by Addison-Wesley.</center></td>
 * </tr>
 * <tr>
 * <td class="structure-diagram"><center>
 * <img src="../../ch05_03/interpreter/image-6266.png" /> </center></td>
 * </tr>
 * <tr>
 * <td><center><i>Compared to the sample code:</i></center></td>
 * </tr>
 * <tr>
 * <td>
 * <ul>
 * <li><b><i>Client</i></b> — contains information that's global to the
 * interpreter.</li>
 * <li><b><i>Context</i></b> — builds (or is given) an abstract syntax tree
 * representing a particular sentence in the language that the grammar
 * defines.</li>
 * <li><b><i>AbstractExpression</i></b> — corresponds to the class
 * {@linkplain BooleanExpression}</li>
 * <li><b><i>TerminalExpression</i></b> — corresponds to the classes
 * {@linkplain AndExpression}, {@linkplain OrExpression}, and
 * {@linkplain NotExpression}</li>
 * <li><b><i>NonterminalExpression</i></b> — not needed. Every Boolean
 * expression is either binary or a compound of binary expressions. The only
 * exception imaginable would be if we allowed ternary expressions such as
 * <p/>
 * <code> a > b ? return c : return d</code>.</li>
 * </ul>
 * </td>
 * </table>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public interface InterpreterStructure extends Interpreter {

}
