package gof.ch02_08.spelling;

import gof.ch02_02.structure.Rectangle;
import gof.ch02_02.structure.Window;

/**
 * <div class="javadoc-text">Adapted from DesignPatterns [Gang of Four], pp. 39,
 * 67. A brief intro to the {@linkplain gof.designpatterns.Iterator Iterator}
 * design pattern. The Glyph has been modified here to eliminate the dubious
 * index into the collection of children. It still has most of the remaining
 * problems (see the original {@linkplain gof.ch02_02.structure.Glyph
 * Glyph}).</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="Iterator UML Diagram.jpg" /> </div>
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
