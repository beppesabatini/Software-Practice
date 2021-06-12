package gof.ch05_04.iterator;

import gof.ch05_04.iterator.List;
import gof.ch05_04.iterator.ListIterator.ListDirection;
import java.io.IOException;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], pp.
 * 267-270. Part of the sample code used to illustrate the
 * {@linkplain gof.designpatterns.Iterator Iterator} design pattern. This is the
 * manual's example of an <b>internal iterator</b>, in which the client hands an
 * internal iterator an operation to perform, and the iterator applies that
 * operation to every element in the aggregate (p. 260).</div>
 *
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class ListTraverser<DataType> implements gof.designpatterns.Iterator {

	protected ListIterator<DataType> listIterator;

	public ListTraverser(List<DataType> list) {
		this.listIterator = new ListIterator<DataType>(list, ListDirection.FORWARDS);
	}

	public boolean traverse() {
		boolean result = false;
		for (listIterator.first(); listIterator.isDone() == false; listIterator.next()) {
			try {
				result = processItem(listIterator.currentItem());
			} catch (IOException ioException) {
				ioException.printStackTrace(System.err);
			}
			if (result == false) {
				break;
			}
		}
		return (result);
	}

	/**
	 * This method is abstract in the manual (p. 267). So, in the manual version,
	 * the ListTraverser class exists only to spawn subclasses, such as
	 * PrintNEmployees.
	 */
	protected boolean processItem(final DataType listItem) {
		System.out.println(" -- " + listItem.toString());
		return (true);
	}
}