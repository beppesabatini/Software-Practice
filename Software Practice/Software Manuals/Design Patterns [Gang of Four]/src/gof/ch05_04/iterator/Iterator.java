package gof.ch05_04.iterator;

import java.io.IOException;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p.
 * 265. Part of the sample code used to illustrate the
 * {@linkplain gof.designpatterns.Iterator Iterator} design pattern.
 * {@linkplain ListTraverser} and {@linkplain PrintNEmployees} represent an
 * <b>internal traverser</b>, while {@linkplain ListIterator} and
 * {@linkplain ListDirection} represent an <b>external traverser</b>. Both
 * traverse the same Genericized List.</div>
 *
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch05_04/iterator/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public interface Iterator<DataType> extends gof.designpatterns.Iterator {

	public void first();

	public void next();

	public boolean isDone();

	public DataType currentItem() throws IOException;
}
