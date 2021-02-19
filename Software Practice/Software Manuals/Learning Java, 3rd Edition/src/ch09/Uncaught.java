package ch09;

// This code not included in Learning Java ch. 9.
public class Uncaught implements Runnable {

	public static void main(String[] args) {
		Thread thread = new Thread(new Uncaught());
		thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			public void uncaughtException(Thread t, Throwable e) {
				System.err.println(t + " threw exception: " + e);
			}
		});
		thread.start();
	}

	public void run() {
		throw new ArithmeticException();
	}

}
