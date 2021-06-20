package gof.ch02_08.spelling;

/**
 * <div class="javadoc-text">From DesignPatterns [Gang of Four], pp. 67-69. One
 * class in the sample code for a brief intro to the
 * {@linkplain gof.designpatterns.Iterator Iterator} design pattern.
 * PreorderIterator is an old name for the common Depth-First Search algorithm
 * for a binary tree, which can be implemented by a stack of stacks. The
 * stack-of-stacks algorithm is not fully implemented either here or in the
 * manual.</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch02_08/spelling/Iterator%20UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class PreorderIterator<DataType> extends Iterator<DataType> {

	@Override
	public DataType first() {
		// Not implemented yet.
		System.out.println("Returning the first item.");
		return (null);
	}

	@Override
	public DataType next() {
		// Not implemented yet.
		System.out.println("Changing the current item pointer to the next item.");
		return (null);
	}

	@Override
	public boolean isDone() {
		// Will need revision if this class is ever fully implemented.
		return (true);
	}

	@Override
	public DataType currentItem() {
		System.out.println("Returning the current item.");
		return (null);
	}
}
