package gof.ch05_04.iterator;

import java.io.IOException;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p.
 * 265. Part of the sample code used to illustrate the
 * {@linkplain gof.designpatterns.Iterator Iterator} design pattern.</div>
 *
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public interface Iterator<DataType> extends gof.designpatterns.Iterator {

	public void first();

	public void next();

	public boolean isDone();

	public DataType currentItem() throws IOException;
}
