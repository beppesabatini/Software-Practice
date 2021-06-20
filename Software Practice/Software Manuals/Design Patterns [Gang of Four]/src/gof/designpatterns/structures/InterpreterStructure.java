package gof.designpatterns.structures;

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
 * <td class="structure-diagram"><center> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch05_03/interpreter/image-6266.png"
 * /> </center></td>
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
 * {@linkplain gof.ch05_03.interpreter.BooleanExpression BooleanExpression}</li>
 * <li><b><i>TerminalExpression</i></b> — corresponds to the classes
 * {@linkplain gof.ch05_03.interpreter.AndExpression AndExpression},
 * {@linkplain gof.ch05_03.interpreter.OrExpression OrExpression}, and
 * {@linkplain gof.ch05_03.interpreter.NotExpression NotExpression}</li>
 * <li><b><i>NonterminalExpression</i></b> — not needed. Every Boolean
 * expression is either binary or a compound of binary expressions. The only
 * exception imaginable would be if we allowed ternary expressions such as
 * <p/>
 * <code> a > b ? return c : return d</code>.</li>
 * </ul>
 * </td>
 * </table>
 * 
 * <pre>
 * <style> 
 * table.javadoc-structure { 
 *     padding: 5px; width: 580px; 
 * }
 *
 * div.diagram-title { 
 *     font-size: 16px; font-weight: bold; text-align:center; 
 * }
 *
 * td.structure-diagram { 
 *     padding: 6px 0 0 0; 
 * }
 * </style>
 * </pre>
 */
public interface InterpreterStructure extends Interpreter {

}
