package ch09;

/**
 * From Learning Java, 3rd Edition, p. 259. Note that applets are no longer
 * supported by Java. This will not run these days.
 */
@SuppressWarnings("deprecation")
public class UpdateApplet extends java.applet.Applet implements Runnable {

	private static final long serialVersionUID = -5593364989580587986L;

	Thread thread;
	boolean running;
	int updateInterval = 1000;

	public void run() {
		while (running) {
			repaint();
			try {
				Thread.sleep(updateInterval);
			} catch (InterruptedException e) {
				System.out.println("interrupted...");
				return;
			}
		}
	}

	public void start() {
		System.out.println("starting...");
		if (!running) // naive approach
		{
			running = true;
			thread = new Thread(this);
			thread.start();
		}
	}

	public void stop() {
		System.out.println("stopping...");
		thread.interrupt();
		running = false;
	}
}
