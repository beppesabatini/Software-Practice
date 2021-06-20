package gof.designpatterns;

/**
 * <div class="javadoc-text">An instance of the <b>Interpreter</b> pattern,
 * described in <i>Design Patterns</i> [{@linkplain GangOfFour Gang of Four}],
 * pp. 243-255. Given a language, define a representation for its grammar along
 * with an interpreter that uses the representation to interpret sentences in
 * the language.
 * <p/>
 * The sample code in the manual is a bad example of a good idea. The code
 * illustrates the design pattern by implementing a boolean expression
 * interpreter, but such a thing is already bundled with every language. The UML
 * Diagram is below. You would only reimplement a boolean interpreter--reinvent
 * it--if you had some very unusual need, such as a database record of
 * transactions using booleans.
 * <p/>
 * For a better example, imagine a developer has a handful of scriptlets or
 * mark-up language fragments, which are used frequently, but whose output has
 * to be moused in or piped in to the main application. It's the kind of problem
 * we run into when a customer is deeply committed to an odd little language,
 * and won't give it up. The goal of the Interpreter pattern is to capture the
 * syntax and logic of the small third-party language in an object-oriented
 * language library, which can then be included with the primary app. </div>
 * <p/>
 * See also the {@linkplain gof.designpatterns.structures.InterpreterStructure
 * Interpreter Structure} diagram.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch05_03/interpreter/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <style> div.javadoc-text { width: 580px; }</style>
 * <p/>
 * <style> div.javadoc-diagram {margin-left: 5px;}</style>
 */
public interface Interpreter extends GangOfFour {

}
