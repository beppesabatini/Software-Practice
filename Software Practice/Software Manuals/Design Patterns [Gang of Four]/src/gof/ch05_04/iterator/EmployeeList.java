package gof.ch05_04.iterator;

import gof.ch05_04.iterator.List;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], pp.
 * 263, 369-372. Part of the sample code used to illustrate the
 * {@linkplain gof.designpatterns.Iterator Iterator} design pattern.
 * {@linkplain ListTraverser} and {@linkplain PrintNEmployees} represent an
 * <b>internal traverser</b>, while {@linkplain ListIterator} and
 * {@linkplain ListDirection} represent an <b>external traverser</b>. Both
 * traverse the same Genericized List.</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch05_04/iterator/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class EmployeeList implements List<Employee> {
	private int count = 0;
	private static final int DEFAULT_CAPACITY = 500;

	private Employee[] listItems = new Employee[DEFAULT_CAPACITY];

	public int count() {
		return (this.count);
	}

	public Employee get(int index) {
		return (listItems[index]);
	}

	public Employee first() {
		return (listItems[0]);
	}

	public Employee last() {
		return (listItems[count - 1]);
	}

	public boolean includes(final Employee listItem) {
		for (int i = 0; i < count; i++) {
			if (listItem.equals(listItems[i])) {
				return (true);
			}
		}
		return (false);
	}

	public void append(final Employee listItem) {
		listItems[count++] = listItem;
	}

	public void prepend(final Employee listItem) {
		count++;
		for (int i = count; i > 0; i--) {
			listItems[i] = listItems[i - 1];
		}
		listItems[0] = listItem;
	}

	public void remove(final Employee listItem) {

	}

	public void removeLast() {
		listItems[count - 1] = null;
		count--;
	}

	public void removeFirst() {
		listItems[0] = null;
		for (int i = 0; i < count; i++) {
			listItems[i] = listItems[i + 1];
		}
	}

	public void removeAll() {
		for (int i = 0; i < count; i++) {
			listItems[i] = null;
		}
		this.count = 0;
	}

	public Employee top() {
		Employee topItem = listItems[count - 1];
		return (topItem.clone());
	}

	public void push(final Employee listItem) {
		append(listItem);
	}

	public Employee pop() {
		Employee listItem = listItems[count - 1];
		listItems[count - 1] = null;
		count--;
		return (listItem);
	}

	public void finalize() {
		// Clean-up before deallocation
		removeAll();
	}

}
