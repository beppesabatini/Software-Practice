package gof.ch05_04.iterator;

import java.io.IOException;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p.
 * 265. Part of the sample code used to illustrate the
 * {@linkplain gof.designpatterns.Iterator Iterator} design pattern. This is no
 * more than a wrapper that uses the deconstructor function (finalize()) to
 * automatically deallocate the wrapped Iterator. The C++ version adds a few
 * more tweaks with overloaded operators, but these are not supported in
 * Java.</div>
 *
 * <pre></pre>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class IteratorPointer<DataType> implements Iterator<DataType>, gof.designpatterns.Iterator {

	private Iterator<DataType> iterator;

	public IteratorPointer(Iterator<DataType> iterator) {
		this.iterator = iterator;
	}

	@Override
	public void first() {
		this.iterator.first();
	}

	@Override
	public void next() {
		this.iterator.next();
	}

	@Override
	public boolean isDone() {
		return (this.iterator.isDone());
	}

	@Override
	public DataType currentItem() throws IOException {
		return (this.iterator.currentItem());
	}

	public void finalize() {
		this.iterator = null;
	}
}
