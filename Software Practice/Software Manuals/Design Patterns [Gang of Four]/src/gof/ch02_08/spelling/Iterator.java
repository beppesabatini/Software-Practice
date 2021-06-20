package gof.ch02_08.spelling;

import java.util.ArrayList;
import java.util.List;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p. 67.
 * This {@linkplain gof.designpatterns.Iterator Iterator} design pattern was
 * intentionally bundled into Java with its second release. Java programmers
 * will only need to reimplement this pattern in rare instances.
 * <p/>
 * In the sample code, the manual presents a bare-bones framework of a design
 * that allows a user to invoke one of three different Iterators. The
 * ListIterator is the most fully functional of the three Iterators. It is
 * invoked by the Row object, which is maintaining a list of its child objects.
 * Additional examples appear in the {@linkplain gof.designpatterns.Iterator
 * Iterator} mouse-over help.</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch02_08/spelling/Iterator%20UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public abstract class Iterator<DataType> implements gof.designpatterns.Iterator {

	protected List<Iterator<?>> iterators = new ArrayList<Iterator<?>>();

	abstract public DataType first();

	abstract public DataType next();

	abstract public boolean isDone();

	abstract public DataType currentItem();
}
