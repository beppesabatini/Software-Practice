package gof.ch02_08.spelling;

import gof.ch02_02.structure.Rectangle;
import gof.ch02_02.structure.Window;

/**
 * <div class="javadoc-text">Adapted from DesignPatterns [Gang of Four], pp. 39,
 * 67. One class in the sample code for a brief intro to the
 * {@linkplain gof.designpatterns.Iterator Iterator} design pattern. The Glyph
 * here is somewhat improved from the original version (see
 * {@linkplain gof.ch02_02.structure.Glyph Glyph}).</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch02_08/spelling/Iterator%20UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public interface Glyph {

	public void drawGlyph(Window window);

	public void setBounds(Rectangle rectangle);

	public void insertGlyph(Glyph glyph, int position);

	public void removeGlyph(Glyph glyph);

	public Iterator<?> createIterator(Object collectionRoot);

	public Glyph getParent();
}
