package gof.ch02_08.spelling;

import java.io.IOException;
import java.util.List;

/**
 * <div class="javadoc-text">From DesignPatterns [Gang of Four], p. 67. One
 * class in the sample code for a brief intro to the
 * {@linkplain gof.designpatterns.Iterator Iterator} design pattern. The manual
 * presents a bare-bones framework of a design that allows a user to invoke one
 * of three different Iterators. This is the most fully functional of the three
 * Iterators. It is invoked by the Row object, which is maintaining a list of
 * its child objects.</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch02_08/spelling/Iterator%20UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class ListIterator<DataType> extends Iterator<DataType> {

	private List<DataType> list;
	private int currentListPosition;

	public ListIterator(Object root) throws IOException {
		if (root == null || (root instanceof List) == false) {
			throw new IOException("ListIterator must be initialized with a List");
		}

		@SuppressWarnings("unchecked")
		List<DataType> initializationList = (List<DataType>) root;
		this.list = initializationList;
		currentListPosition = 0;
	}

	@Override
	public DataType first() {
		DataType listNode = list.get(0);
		return (listNode);
	}

	@Override
	public DataType next() {
		currentListPosition++;
		return (list.get(currentListPosition));
	}

	@Override
	public boolean isDone() {
		if (currentListPosition >= list.size()) {
			return (true);
		}
		return (false);
	}

	@Override
	public DataType currentItem() {
		return (list.get(currentListPosition));
	}
}
