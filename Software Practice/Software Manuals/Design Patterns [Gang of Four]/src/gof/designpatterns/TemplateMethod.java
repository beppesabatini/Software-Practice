package gof.designpatterns;

/**
 * <table width="580">
 * <tr>
 * <td>An instance of the <b>TemplateMethod</b> pattern, described in <i>Design
 * Patterns</i> [{@linkplain GangOfFour Gang of Four}], pp. 325-330. Define the
 * skeleton of an algorithm in an operation, deferring some steps to subclasses.
 * Template Method lets subclasses redefine certain steps of an algorithm
 * without changing the algorithm's structure.
 * <p/>
 * Many developers will be familiar with the concept from working with
 * half-empty unit test frameworks. Such frameworks specify test methods to be
 * called, which are created and provided by the developer.
 * <p/>
 * In the manual, the template is implemented as an abstract class. The abstract
 * template has abstract methods, or default methods, or empty methods, which
 * are called TemplateMethods. In a concrete subclass, the client must define
 * these methods (if abstract), or override them (if empty or inadequate).
 * <p/>
 * See also the
 * {@linkplain gof.designpatterns.structures.TemplateMethodStructure Template
 * Method Structure} diagram.</td>
 * </tr>
 * <tr>
 * <td><center><img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch05_10/templatemethod/UML%20Diagram.jpg"
 * /></center></td>
 * </tr>
 * </table>
 * 
 * <style> div.javadoc-text { width: 580px; }</style>
 * <p/>
 * <style> div.javadoc-diagram {margin-left: 5px;}</style>
 */
public interface TemplateMethod extends GangOfFour {

}
