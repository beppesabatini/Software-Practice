package gof.ch02_08.spelling;

/**
 * <div class="javadoc-text">From DesignPatterns [Gang of Four], p. 67. One
 * class in the sample code for a brief intro to the
 * {@linkplain gof.designpatterns.Iterator Iterator} design pattern.</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch02_08/spelling/Iterator%20UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class ArrayIterator<DataType> extends Iterator<DataType> {

	// The index into the array:
	@SuppressWarnings("unused")
	private int currentItem;

	@Override
	public DataType first() {
		// Not implemented yet.
		System.out.println("Returning the first item.");
		return (null);
	}

	@Override
	public DataType next() {
		// Not implemented yet.
		System.out.println("Changing the current item counter to the next item.");
		return (null);
	}

	@Override
	public boolean isDone() {
		// This will need revision if this class is ever fully implemented.
		return (true);
	}

	@Override
	public DataType currentItem() {
		System.out.println("Returning the current item.");
		return (null);
	}
}
