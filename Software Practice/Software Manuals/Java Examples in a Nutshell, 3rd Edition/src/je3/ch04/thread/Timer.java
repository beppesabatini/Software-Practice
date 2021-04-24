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

import java.util.Comparator;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * From Java Examples in a Nutshell, 3rd Edition, pp. 96-100. This class is a
 * simple implementation of the Java 1.3 java.util.Timer API.
 */
public class Timer {

	/*
	 * This sorted set stores the tasks for which this Timer is responsible. It uses
	 * a comparator to sort the tasks by scheduled execution time.
	 */
	SortedSet<TimerTask> timerTasks = new TreeSet<TimerTask>(new TimerTaskComparator());

	public static class TimerTaskComparator implements Comparator<TimerTask> {

		@Override
		public int compare(TimerTask timerTask01, TimerTask timerTask02) {
			return (int) (timerTask01.nextTime - timerTask02.nextTime);
		}

		@Override
		public boolean equals(Object timerTask) {
			if ((timerTask instanceof TimerTask) == false) {
				return (false);
			}
			return (this == timerTask);
		}
	}

	/*
	 * This is the thread that the timer uses to execute the tasks. The TimerThread
	 * class is defined below.
	 */
	TimerThread timerThread;

	/** This constructor creates a Timer that does not use a daemon thread. */
	public Timer() {
		this(false);
	}

	/** The main constructor: the internal thread is a daemon, if specified. */
	public Timer(boolean isDaemon) {
		// TimerThread is defined below.
		timerThread = new TimerThread(isDaemon);
		// Start the thread running:
		timerThread.start();
	}

	/** Stop the timer thread, and discard all scheduled tasks. */
	public void cancel() {
		// Only one thread at a time!
		synchronized (timerTasks) {
			// Set a flag asking the thread to stop:
			timerThread.pleaseStop();
			// Discard all tasks:
			timerTasks.clear();
			// Wake up the thread if it is in the wait() state:
			timerTasks.notify();
		}
	}

	/** Schedule a single execution after delay milliseconds. */
	public void schedule(TimerTask timerTask, long delay) {
		timerTask.schedule(System.currentTimeMillis() + delay, 0, false);
		schedule(timerTask);
	}

	/** Schedule a single execution at the specified time. */
	public void schedule(TimerTask timerTask, Date time) {
		timerTask.schedule(time.getTime(), 0, false);
		schedule(timerTask);
	}

	/** Schedule a periodic execution starting at the specified time. */
	public void schedule(TimerTask timerTask, Date firstTime, long period) {
		timerTask.schedule(firstTime.getTime(), period, false);
		schedule(timerTask);
	}

	/** Schedule a periodic execution starting after the specified delay. */
	public void schedule(TimerTask timerTask, long delay, long period) {
		timerTask.schedule(System.currentTimeMillis() + delay, period, false);
		schedule(timerTask);
	}

	/**
	 * Schedule a periodic execution starting after the specified delay. Schedule a
	 * fixed-rate execution period some microseconds after the start of the last
	 * (instead of fixed-interval executions measured from the end of the last).
	 */
	public void scheduleAtFixedRate(TimerTask timerTask, long delay, long period) {
		timerTask.schedule(System.currentTimeMillis() + delay, period, true);
		schedule(timerTask);
	}

	/** Schedule a periodic execution starting after the specified time */
	public void scheduleAtFixedRate(TimerTask task, Date firstTime, long period) {
		task.schedule(firstTime.getTime(), period, true);
		schedule(task);
	}

	// This internal method adds a task to the sorted set of tasks.
	void schedule(TimerTask timerTask) {
		// Only one thread can modify tasks at a time!
		synchronized (timerTasks) {
			// Add the task to the sorted set of tasks:
			timerTasks.add(timerTask);
			// Wake up the thread if it is waiting:
			timerTasks.notify();
		}
	}

	/**
	 * This inner class defines the thread that runs each of the tasks at their
	 * scheduled times.
	 */
	class TimerThread extends Thread {
		/*
		 * This flag will be set to true to tell the thread to stop running. Note that
		 * it is declared volatile, which means that it may be changed asynchronously by
		 * another thread. So, threads must always read its current value, and not use a
		 * cached version.
		 */
		volatile boolean stopped = false;

		// The constructor:
		TimerThread(boolean isDaemon) {
			setDaemon(isDaemon);
		}

		// Ask the thread to stop, by setting the flag above.
		void pleaseStop() {
			stopped = true;
		}

		// This is the body of the thread.
		public void run() {
			// Is there a task to run right now?
			TimerTask readyToRun = null;

			// The thread loops until the stopped flag is set to true.
			while (stopped == false) {
				// If there is a task that is ready to run, then run it!
				if (readyToRun != null) {
					// If it was cancelled, skip it.
					if (readyToRun.cancelled == true) {
						readyToRun = null;
						continue;
					}
					// Run the task.
					readyToRun.run();
					/*
					 * Ask it to reschedule itself. If it wants to run again, then insert it back
					 * into the set of tasks.
					 */
					if (readyToRun.reschedule() == true) {
						schedule(readyToRun);
					}
					// We've run it, so there is nothing to run now.
					readyToRun = null;
					// Go back to top of the loop, to see if we've been stopped.
					continue;
				}

				// Now acquire a lock on the set of tasks.
				synchronized (timerTasks) {
					// How many milliseconds until the next execution?
					long timeout;

					// If there aren't any tasks:
					if (timerTasks.isEmpty()) {
						// ...wait until we are notified of a new task.
						timeout = 0;
					} else {
						/*
						 * If there are scheduled tasks, then get the first one. Since the set is
						 * sorted, this is the next one.
						 */
						TimerTask timerTask = timerTasks.first();
						// How long until it is next run?
						timeout = timerTask.nextTime - System.currentTimeMillis();
						// Check whether it needs to run now.
						if (timeout <= 0) {
							// Save it as ready to run:
							readyToRun = timerTask;
							// Remove it from the set:
							timerTasks.remove(timerTask);
							// Break out of the synchronized section before we run the task.
							continue;
						}
					}

					/*
					 * If we get here, there is nothing ready to run now, so wait for the time to
					 * run out. Or, wait until notify() is called, when something new is added to
					 * the set of tasks.
					 */
					try {
						timerTasks.wait(timeout);
					} catch (InterruptedException e) {
						// ignore
					}
					// When we wake up, go back up to the top of the while loop.
				}
			}
		}
	}

	/**
	 * This inner class defines a test program. Note that TimerTask is an abstract
	 * object. All four of the tasks defined and run here are implemented as
	 * anonymous subclasses (so to speak) of TimerTask.
	 */
	public static class Test {
		public static void main(String[] args) {
			// Task 1: Print "boom":
			final TimerTask timerTask01 = new TimerTask() {
				@Override
				public void run() {
					System.out.println("boom");
				}
			};
			// Task 2: Print "BOOM":
			final TimerTask timerTask02 = new TimerTask() {
				@Override
				public void run() {
					System.out.println("\tBOOM");
				}
			};
			// Task 3: Cancel the tasks.
			final TimerTask timerTask03 = new TimerTask() {
				@Override
				public void run() {
					System.out.println("Time to cancel the threads");
					timerTask01.cancel();
					timerTask02.cancel();
				}
			};

			// Create a timer, and schedule some tasks
			final Timer timer = new Timer();
			// Go "boom" every half-second, starting now.
			timer.schedule(timerTask01, 0, 500);
			// Go "BOOM" every 2 seconds, starting in 2 seconds.
			timer.schedule(timerTask02, 2000, 2000);
			// Stop the first two tasks above after 5 seconds.
			timer.schedule(timerTask03, 5000);

			/*
			 * Schedule a final task: starting in 5 seconds, count down from 5, then destroy
			 * the timer, which, since it is the only remaining thread, will cause the
			 * program to exit.
			 */
			timer.scheduleAtFixedRate(new TimerTask() {

				public int times = 5;

				public void run() {
					System.out.println(times--);
					if (times == 0) {
						System.out.println("Now the timer itself is cancelling");
						timer.cancel();
					}
				}
			}, 5500, 500);
		}
	}
}
