package gof.ch05_07.observer;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p.
 * 302. Part of the sample code that demonstrates the
 * {@linkplain gof.designpatterns.Observer Observer} or
 * <b>publisher-subscriber</b> design pattern.</div>
 *
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class DigitalClock extends Observer {

	private ClockTimer subject;

	public DigitalClock(ClockTimer clockTimer) {
		this.subject = clockTimer;
		this.subject.attach(this);
	}

	@Override
	void update(Subject theChangedSubject, int seconds) {
		if (theChangedSubject != this.subject) {
			return;
		}
		System.out.println(" -- DigitalClock got an update from " + theChangedSubject.getSubjectName());
		draw(seconds);
	}

	public void draw(int seconds) {
		int hour = subject.getHour();
		int minute = subject.getMinute();
		int second = subject.getSecond();
		System.out.printf(" -- DigitalClock says the time is: %d:%d:%d", hour, minute, second + seconds);
		System.out.println();
	}

	@Override
	public void finalize() {
		// Clean-up before deallocation
	}
}
