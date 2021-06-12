package gof.designpatterns;

/**
 * <div class="javadoc-text">An instance of the <b>Strategy</b> pattern,
 * described in <i>Design Patterns</i> [{@linkplain GangOfFour Gang of Four}],
 * pp. 315-323. Define a family of algorithms, encapsulate each one, and make
 * them interchangeable. The "Strategy" pattern lets the algorithm vary
 * independently from clients that use it.
 * <p/>
 * In the <a href="../ch05_09/strategy/UML Diagram.jpg">example from the
 * manual</a>, a text compositor simply has the option of three different
 * algorithms to calculate line breaks, each of which is represented by a
 * different Strategy object.
 * <p/>
 * In a different example (from the Internet, not from the manual), a
 * puzzle-solving program maintains an array of three Strategies, each of which
 * represent a puzzle-solving algorithm. The program begins begins by applying
 * the cheapest algorithm, and progressively upgrades to more difficult and more
 * expensive Strategies, until the puzzle is solved. The code and an executable
 * jar file are posted <a href=
 * "https://github.com/beppesabatini/Software-Practice/blob/main/Software%20Practice/Software%20Manuals/Puzzles%20for%20Programmers%20and%20Pros/src/partII/sudoku/Strategies.java">on
 * line</a>. See also the
 * {@linkplain gof.designpatterns.structures.StrategyStructure
 * StrategyStructure} diagram.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram">
 * <img src="../ch05_09/strategy/Sudoku UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../styles/gof.css">
 */
public interface Strategy extends GangOfFour {

}
