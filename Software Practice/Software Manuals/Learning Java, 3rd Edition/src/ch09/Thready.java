package ch09;

// The point of this, if any, is that priorities are platform-dependent and 
// should be considered just a "hint" for the threads. Similar to p.275.
public class Thready {
	public static void main(String args[]) {
		Thread foo = new ShowThread("Foo");
		foo.setPriority(Thread.NORM_PRIORITY);
		foo.start();

		Thread bar = new ShowThread("Bar");
		bar.setPriority(Thread.NORM_PRIORITY + 1);
		bar.start();
	}

	static class ShowThread extends Thread {
		String message;

		ShowThread(String message) {
			this.message = message;
		}

		public void run() {
			while (true) {
				System.out.println(message);
			}
		}
	}
}
