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

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 86-87. This class
 * demonstrates the use of threads. The main() method is the initial method
 * invoked by the interpreter. It defines and starts two more threads and the
 * three threads run at the same time. Note that this class extends Thread and
 * overrides its run() method. That method provides the body of one of the
 * threads started by the main() method
 */
public class ThreadDemo extends Thread {

	/**
	 * This method overrides the run() method of Thread. It provides the body for
	 * this thread.
	 */
	@Override
	public void run() {
		for (int i = 0; i < 5; i++) {
			compute();
		}
	}

	/**
	 * This main method creates and starts two threads in addition to the initial
	 * thread that the interpreter creates to invoke the main() method.
	 */
	public static void main(String[] args) {
		/*
		 * Create the first thread: an instance of this class. Its body is the run()
		 * method above.
		 */
		ThreadDemo thread1 = new ThreadDemo();

		/*
		 * Create the second thread by passing a Runnable object to the Thread()
		 * constructor. The body of this thread is the run() method of the anonymous
		 * Runnable object below.
		 */
		Thread thread2 = new Thread(new Runnable() {
			public void run() {
				for (int i = 0; i < 5; i++) {
					compute();
				}
			}
		});

		// Set the priorities of these two threads, if any are specified.
		if (args.length >= 1) {
			thread1.setPriority(Integer.parseInt(args[0]));
		}
		if (args.length >= 2) {
			thread2.setPriority(Integer.parseInt(args[1]));
		}

		// Start the two threads running
		thread1.start();
		thread2.start();

		/*
		 * This main() method is run by the initial thread created by the Java
		 * interpreter. Now that thread does some stuff, too.
		 */
		for (int i = 0; i < 5; i++) {
			compute();
		}

		/*
		 * We could wait for the threads to stop running with these lines But they
		 * aren't necessary here, so we don't bother.
		 */

		// try {
		// thread1.join();
		// thread2.join();
		// } catch (InterruptedException e) {}

		/*
		 * The Java VM exits only when the main() method returns, and when all threads
		 * stop running (except for daemon threads--see setDaemon()).
		 */
	}

	/*
	 * ThreadLocal objects represent a value accessed with get() and set(). But they
	 * maintain a different value for each thread. This object keeps track of how
	 * many times each thread has called compute().
	 */
	static ThreadLocal<Integer> callCounter = new ThreadLocal<Integer>();

	/** This is the dummy method our threads all call. */
	static synchronized void compute() {
		// Figure out how many times we've been called by the current thread.
		Integer numberOfCalls = (Integer) callCounter.get();
		if (numberOfCalls == null) {
			numberOfCalls = 1;
		} else {
			numberOfCalls = numberOfCalls + 1;
		}
		callCounter.set(numberOfCalls);

		// Display the name of the thread, and the number of times called.
		System.out.println(Thread.currentThread().getName() + ": " + numberOfCalls);

		// Do a long computation, simulating a "compute-bound" thread.
		for (int i = 0, j = 0; i < 1000000; i++) {
			j += i;
			if (j == 1000000) {
				System.out.println("One million calculations performed");
			}
		}

		/*
		 * Alternatively, we can simulate a thread subject to network or I/O delays by
		 * causing it to sleep for a random amount of time:
		 */
		try {
			// Stop running for a random number of milliseconds.
			Thread.sleep((int) (Math.random() * 100 + 1));
		} catch (InterruptedException e) {
			// This can be ignored.
		}

		/*
		 * Each thread politely offers the other threads a chance to run. This is
		 * important so that a compute-bound thread does not "starve" other threads of
		 * equal priority.
		 */
		Thread.yield();
	}
}
