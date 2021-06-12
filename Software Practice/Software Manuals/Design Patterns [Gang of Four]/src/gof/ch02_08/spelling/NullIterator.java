package gof.ch02_08.spelling;

/**
 * <div class="javadoc-text">From DesignPatterns [Gang of Four], p. 67. A brief
 * intro to the {@linkplain gof.designpatterns.Iterator Iterator} design
 * pattern. Not just a stub, this object is as finished as it ever need be. This
 * odd structure is a little bit like a complicated version of a null pointer.
 * Some programmers use such devices like placeholders when a collection will
 * often or always be empty. This concrete class is a subclass of the abstract
 * class Iterator, in this same directory.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="Iterator UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class NullIterator<DataType> extends Iterator<DataType> {

	@Override
	public DataType first() {
		return (null);
	}

	@Override
	public DataType next() {
		return (null);
	}

	@Override
	public boolean isDone() {
		return (true);
	}

	@Override
	public DataType currentItem() {
		return (null);
	}
}
