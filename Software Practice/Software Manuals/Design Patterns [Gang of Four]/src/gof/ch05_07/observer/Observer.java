package gof.ch05_07.observer;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p.
 * 300. Part of the sample code that demonstrates the
 * {@linkplain gof.designpatterns.Observer Observer} or
 * <b>publisher-subscriber</b> design pattern.
 * <p/>
 * In this sample code, a ClockTimer acts as a subject (or publisher), while a
 * DigitalClock and an AnalogClock act as observers (or subscribers).</div>
 *
 * <pre></pre>
 * 
 * <div class="javadoc-diagram"> <img src="UML Diagram.jpg" /> </div>
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public abstract class Observer implements gof.designpatterns.Observer {

	protected Observer() {

	}

	abstract void update(Subject theChangedSubject, int seconds);

	public void finalize() {
		// Clean-up before deallocation
	}
}
