package gof.ch05_07.observer;

import java.util.List;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p.
 * 301. Part of the sample code that demonstrates the
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
public abstract class Subject implements gof.designpatterns.Observer {

	protected List<Observer> observers;

	protected String subjectName;

	protected Subject(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getSubjectName() {
		return this.subjectName;
	}

	public void attach(Observer observer) {
		observers.add(observer);
	}

	public void detach(Observer observer) {
		observers.remove(observer);
	}

	public void notifyObservers(int seconds) {
		for (Observer observer : observers) {
			observer.update(this, seconds);
		}
	}

	@Override
	public void finalize() {
		// Clean-up before deallocation
	}
}
