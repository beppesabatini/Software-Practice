package gof.ch02_08.spelling;

/**
 * <div class="javadoc-text">From DesignPatterns [Gang of Four], p. 67. One
 * class in the sample code for a brief intro to the
 * {@linkplain gof.designpatterns.Iterator Iterator} design pattern.
 * <p/>
 * Not just a stub, this object is as finished as it ever need be. This odd
 * structure is a little bit like a complicated version of a null pointer. Some
 * programmers use such devices like placeholders when a collection will often
 * or always be empty.</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch02_08/spelling/Iterator%20UML%20Diagram.jpg"
 * /> </div>
 * 
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
