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
 * From Java Examples in a Nutshell, 3rd Edition, pp. 95-96. This class
 * implements the same API as the Java 1.3 java.util.TimerTask. Note that a
 * TimerTask may only be scheduled on one Timer at a time, but that this
 * implementation does not enforce that constraint.
 * <p/>
 * To test this run the Timer() class in this same package.
 */
public abstract class TimerTask implements Runnable {

	// Has it been cancelled?
	boolean cancelled = false;
	// When is it next scheduled?
	long nextTime = -1;
	// What is the execution interval?
	long period;
	// Fixed-rate execution?
	// Such tasks start some specified interval after the start of the previous.
	boolean fixedRate;

	protected TimerTask() {
	}

	/**
	 * Cancel the execution of the task. Return true if it was actually running, or
	 * false if it was already cancelled or never scheduled.
	 */
	public boolean cancel() {
		if (cancelled == true) {
			// The task was already cancelled.
			return false;
		}
		// Cancel it:
		cancelled = true; 
		if (nextTime == -1) {
			// It was never scheduled.
			return false;
		}
		return true;
	}

	/**
	 * When is the timer scheduled to execute? The run() method can use this to see
	 * whether it was invoked when it was supposed to be.
	 */
	public long scheduledExecutionTime() {
		return nextTime;
	}

	/**
	 * Subclasses must override this, to provide that code that is to be run. The
	 * Timer class will invoke this from its internal thread.
	 */
	public abstract void run();

	// This method is used by Timer to tell the Task how it is scheduled.
	void schedule(long nextTime, long period, boolean fixedRate) {
		this.nextTime = nextTime;
		this.period = period;
		this.fixedRate = fixedRate;
	}

	// This will be called by Timer after Timer calls the run method.
	boolean reschedule() {
		if (period == 0 || cancelled == true ) {
			// Don't run it again
			return false;
		}
		if (fixedRate == true) {
			nextTime += period;
		} else
			nextTime = System.currentTimeMillis() + period;
		return true;
	}
}
