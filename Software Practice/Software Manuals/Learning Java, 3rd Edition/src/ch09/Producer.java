package ch09;

import java.util.*;

/**
 * From Learning Java, 3rd Edition, p. 267-268.
 */
public class Producer implements Runnable {
	static final int MAXQUEUE = 5;
	private List<String> messages = new ArrayList<String>();

	public void run() {
		while (true) {
			putMessage();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
	}

	private synchronized void putMessage() {
		while (messages.size() >= MAXQUEUE)
			try {
				wait();
			} catch (InterruptedException e) {
			}

		messages.add(new java.util.Date().toString());
		notify();
	}

	// called by Consumer
	public synchronized String getMessage() {
		while (messages.size() == 0)
			try {
				notify();
				wait();
			} catch (InterruptedException e) {
			}
		String message = (String) messages.remove(0);
		notify();
		return message;
	}
}
