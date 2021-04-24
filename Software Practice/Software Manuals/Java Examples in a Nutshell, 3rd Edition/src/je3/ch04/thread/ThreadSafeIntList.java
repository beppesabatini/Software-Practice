/*
 * Copyright (c) 2004 David Flanagan.  All rights reserved.
 * This code is from the book Java Examples in a Nutshell, 3nd Edition.
 * It is provided AS-IS, WITHOUT ANY WARRANTY either expressed or implied.
 * You may study, use, and modify it for any non-commercial purpose,
 * including teaching and use in open-source projects.
 * You may distribute it non-commercially as long as you retain this notice.
 * For a commercial use license, or to purchase the book, 
 * please visit http://www.davidflanagan.com/javaexamples3.
 */
package je3.ch04.thread;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 88-89. A growable array of
 * int values, suitable for use with multiple threads.
 */
public class ThreadSafeIntList {

	private static Logger logger = Logger.getLogger(ThreadSafeIntList.class.getName());

	// This array holds the integers:
	private int[] listIntegers;
	// This is how many it currently holds:
	private int listSize;

	// Static final values are constants. This one is private.
	private static final int DEFAULT_CAPACITY = 8;

	// Create a ThreadSafeIntList with a default capacity.
	public ThreadSafeIntList() {
		/*
		 * We don't have to set size to zero because newly created objects automatically
		 * have their fields set to zero, false, and null.
		 */
		// Allocate the array:
		listIntegers = new int[DEFAULT_CAPACITY];
	}

	/*
	 * This constructor returns a copy of an existing ThreadSafeIntList. Note that
	 * it synchronizes its access to the original list.
	 */
	public ThreadSafeIntList(ThreadSafeIntList original) {
		synchronized (original) {
			this.listIntegers = (int[]) original.listIntegers.clone();
			this.listSize = original.listSize;
		}
	}

	// Return the number of ints stored in the list.
	public synchronized int size() {
		return listSize;
	}

	// Return the int stored at the specified index.
	public synchronized int get(int index) {
		// Check that the argument is legitimate.
		if (index < 0 || index >= listSize) {
			throw new IndexOutOfBoundsException(String.valueOf(index));
		}
		return listIntegers[index];
	}

	// Append a new value to the list, reallocating if necessary.
	public synchronized void add(int value) {
		// Reallocate if necessary.
		if (listSize == listIntegers.length) {
			setCapacity(listSize * 2);
		}
		// Add a value to the list.
		listIntegers[listSize++] = value;
	}

	// Remove all elements from the list.
	public synchronized void clear() {
		listSize = 0;
	}

	// Copy the contents of the list into a new array, and return that array.
	public synchronized int[] toArray() {
		int[] copy = new int[listSize];
		System.arraycopy(listIntegers, 0, copy, 0, listSize);
		return copy;
	}

	/*
	 * Reallocate the data array to enlarge or shrink it. Not synchronized, because
	 * it is always called from synchronized methods.
	 */
	private void setCapacity(int capacity) {
		// Check size:
		if (capacity == listIntegers.length) {
			return;
		}
		// Allocate the new array:
		int[] newData = new int[capacity];
		// Copy data into it:
		System.arraycopy(listIntegers, 0, newData, 0, listSize);
		// Replace the old array:
		listIntegers = newData;
	}

	public boolean deepEquals(ThreadSafeIntList threadSafeIntList) {

		ThreadSafeIntList list01 = this;
		ThreadSafeIntList list02 = threadSafeIntList;

		if (list01 == list02) {
			logger.log(Level.INFO, "Exact same objects");
			return (true);
		}

		if (list01.listIntegers == list02.listIntegers) {
			logger.log(Level.INFO, "Different objects, same data collection");
			return (true);
		}

		int size01 = list01.size();
		int size02 = list02.size();
		if (size01 != size02) {
			logger.log(Level.INFO, "different data collection");
			return (false);
		}
		for (int i = 0; i < size01; i++) {
			if (list01.get(i) != list02.get(i)) {
				logger.log(Level.INFO, "different data value");
				return (false);
			}
		}
		logger.log(Level.INFO, "Different objects, different data collection, same data values");
		return (true);
	}

	public static void main(String[] args) throws Exception {
		ThreadSafeIntList threadSafeIntList = new ThreadSafeIntList();
		for (int i = 0; i < 100; i++) {
			threadSafeIntList.add((int) (Math.random() * 40000));
		}
		ThreadSafeIntList threadSafeIntListCopy = new ThreadSafeIntList(threadSafeIntList);
		/*
		 * The Object#equals() function seems broken--all it does is compare the two
		 * pointers to the two objects, to see if they are the exact same Object.
		 */
		if (threadSafeIntList.equals(threadSafeIntListCopy) == false) {
			System.out.println("The threadSafeIntList and the threadSafeIntListCopy fail the System equals() test.");
		}

		if (threadSafeIntList.deepEquals(threadSafeIntListCopy) == true) {
			System.out.println("The threadSafeIntList and the threadSafeIntListCopy pass the deepEquals() test.");
		}
	}
}
