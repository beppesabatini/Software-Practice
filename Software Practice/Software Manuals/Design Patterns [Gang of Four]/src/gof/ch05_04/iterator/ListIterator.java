package gof.ch05_04.iterator;

import java.io.IOException;
import gof.ch05_04.iterator.List;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], pp.
 * 263-265. Part of the sample code used to illustrate the
 * {@linkplain gof.designpatterns.Iterator Iterator} design pattern.
 * <p/>
 * {@linkplain ListTraverser} and {@linkplain PrintNEmployees} represent an
 * <b>internal traverser</b>, while {@linkplain ListIterator} and
 * {@linkplain ListDirection} represent an <b>external traverser</b>. Both
 * traverse the same Genericized List.
 * <p/>
 * This ListIterator is the most finished example in the code of an <b>external
 * iterator</b>, which advances the traversal, and requests the next element
 * explicitly from the iterator (p. 260).</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch05_04/iterator/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class ListIterator<DataType> implements Iterator<DataType>, gof.designpatterns.Iterator {

	public enum ListDirection {
		FORWARDS, BACKWARDS;
	}

	private final List<DataType> list;
	private int current;
	private ListDirection listDirection = ListDirection.FORWARDS;

	public ListIterator(List<DataType> list, ListDirection listDirection) {
		this.list = list;
		this.current = 0;
		this.listDirection = listDirection;
	}

	@Override
	public void first() {
		switch (listDirection) {
		case FORWARDS:
			this.current = 0;
			break;
		case BACKWARDS:
			this.current = this.list.count() - 1;
			break;
		}
	}

	@Override
	public void next() {
		switch (listDirection) {
		case FORWARDS:
			this.current++;
			break;
		case BACKWARDS:
			this.current--;
			break;
		}
	}

	@Override
	public boolean isDone() {
		boolean done = false;
		switch (listDirection) {
		case FORWARDS:
			done = this.current >= this.list.count();
			break;
		case BACKWARDS:
			done = this.current < 0;
			break;
		}
		return (done);
	}

	public DataType currentItem() throws IOException {
		if (this.isDone()) {
			throw new IOException("Iterator out of bounds");
		}
		return (this.list.get(current));
	}
}
