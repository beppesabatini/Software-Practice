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
 * From Java Examples in a Nutshell, 3rd Edition, pp. 93-94. This is a
 * demonstration of how NOT to write multi-threaded programs. It is a program
 * that purposely causes deadlock between two threads that are both trying to
 * acquire locks for the same two resources. To avoid this sort of deadlock when
 * locking multiple resources, all threads should always acquire their locks in
 * the same order.
 */
public class Deadlock {
	public static void main(String[] args) {
		// These are the two resource objects we'll try to get locks for
		final Object resource01 = "resource01";
		final Object resource02 = "resource02";
		// Here's the first thread. It tries to lock resource1, then resource2.
		Thread thread01 = new Thread() {
			public void run() {
				// Lock resource 1
				synchronized (resource01) {
					System.out.println("Thread 1: locked resource 1");

					/*
					 * Pause for a bit, simulating some file I/O or something. Basically, we just
					 * want to give the other thread a chance to run. Threads and deadlock are
					 * asynchronous things, but we're trying to force deadlock to happen here...
					 */
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// ignore
					}

					// Now wait 'till we can get a lock on resource 2:
					synchronized (resource02) {
						System.out.println("Thread 1: locked resource 2");
					}
				}
			}
		};

		// Here's the second thread. It tries to lock resource2, then resource1
		Thread thread02 = new Thread() {
			public void run() {
				// This thread locks resource 2 right away:
				synchronized (resource02) {
					System.out.println("Thread 2: locked resource 2");

					// Then it pauses, just like the first thread.
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// ignore
					}

					/*
					 * Then it tries to lock resource1. But wait! Thread 1 locked resource1, and
					 * won't release it 'till it gets a lock on resource2. This thread holds the
					 * lock on resource2, and won't release it 'till it gets resource1. We're at an
					 * impasse. Neither thread can run, and the program freezes up.
					 */
					synchronized (resource01) {
						System.out.println("Thread 2: locked resource 1");
					}
				}
			}
		};

		/*
		 * Start the two threads. If all goes as planned, deadlock will occur, and the
		 * program will never exit.
		 */
		thread01.start();
		thread02.start();
	}
}
