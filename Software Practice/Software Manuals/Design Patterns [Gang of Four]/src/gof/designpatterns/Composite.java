package gof.designpatterns;

/**
 * <div class="javadoc-text">An instance of the <b>Composite</b> pattern,
 * described in <i>Design Patterns</i> [{@linkplain GangOfFour Gang of Four}],
 * pp. 163-173. Compose objects into tree structures to represent part-whole
 * hierarchies. Composite lets clients treat individual objects and compositions
 * of objects uniformly.
 * <p/>
 * Composite might be an appropriate design pattern for software dealing with a
 * library or a bookstore. Using the Composite pattern, a book, a trilogy, an
 * encyclopedia, and other holdings, could all be handled interchangeably. The
 * manual (p. 173) mentions the use case of a financial portfolio, which uses a
 * Composite to generalize the interface for the portfolio with the interface
 * for an individual asset.
 * <p/>
 * In the sample code from Chapter 2, all the characters and graphics supported
 * in a document have been generalized (perhaps over-generalized) into an
 * all-purpose object called a {@linkplain gof.ch02_02.structure.Glyph Glyph}.
 * Another example, from Section 4.3, generalizes all the hardware in an
 * electronic outlet into an all-purpose object called
 * {@linkplain gof.ch04_03.composite.Equipment Equipment}, as seen in the UML
 * Diagram below. See also the
 * {@linkplain gof.designpatterns.structures.CompositeStructure Composite
 * Structure} diagram.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch04_03/composite/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <style> div.javadoc-text { width: 580px; }</style>
 * <p/>
 * <style> div.javadoc-diagram {margin-left: 5px;}</style>
 */
public interface Composite extends GangOfFour {

}
