package gof.ch05_04.iterator;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], pp.
 * 263, 369-372. Part of the sample code used to illustrate the
 * {@linkplain gof.designpatterns.Iterator Iterator} design pattern. Don't let
 * your code confuse this List interface with (one of the) official Java List
 * classes.</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch05_04/iterator/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public interface List<DataType> {

	public int count();

	public DataType get(int index);

	public DataType first();

	public DataType last();

	public boolean includes(final DataType listItem);

	public void append(final DataType listItem);

	public void prepend(final DataType listItem);

	public void remove(final DataType listItem);

	public void removeLast();

	public void removeFirst();

	public void removeAll();

	// The next four let a client use the List as a Stack.
	public DataType top();

	public void push(final DataType listItem);

	public DataType pop();

	public void finalize();
}
