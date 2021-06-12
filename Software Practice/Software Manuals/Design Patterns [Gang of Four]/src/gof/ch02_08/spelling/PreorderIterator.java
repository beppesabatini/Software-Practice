package gof.ch02_08.spelling;

/**
 * <div class="javadoc-text">From DesignPatterns [Gang of Four], pp. 67-69. A
 * brief intro to the {@linkplain gof.designpatterns.Iterator Iterator} design
 * pattern. PreorderIterator is an old name for the common Depth-First Search
 * algorithm for a binary tree, which can be implemented by a stack of stacks.
 * There are a few fragments of this stack-of-stacks algorithm in the manual.
 * This is not being implemented at this time, as the point of the exercise is
 * to demonstrate the Iterator pattern.</div>
 * 
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="Iterator UML Diagram.jpg" /> </div>
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
