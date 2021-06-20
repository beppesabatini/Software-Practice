package gof.ch05_07.observer;

import java.util.ArrayList;

/**
 * <div class="javadoc-text">Adapted from Design Patterns [Gang of Four], p.
 * 301. One class in the sample code that demonstrates the
 * {@linkplain gof.designpatterns.Observer Observer} or
 * <b>publisher-subscriber</b> design pattern.</div>
 * 
 * <div class="javadoc-diagram"> <img src=
 * "https://raw.githubusercontent.com/beppesabatini/Software-Practice/main/Software%20Practice/Software%20Manuals/Design%20Patterns%20%5BGang%20of%20Four%5D/src/gof/ch05_07/observer/UML%20Diagram.jpg"
 * /> </div>
 * 
 * <link rel="stylesheet" href="../../styles/gof.css">
 */
public class ClockTimer extends Subject {

	public ClockTimer(String clockName) {
		super(clockName);
		this.observers = new ArrayList<Observer>();
	}

	public String getClockName() {
		return this.subjectName;
	}

	public int getHour() {
		return 1;
	}

	public int getMinute() {
		return 2;
	}

	public int getSecond() {
		return 3;
	}

	static int seconds = 0;

	public void tick() {
		System.out.println();
		System.out.println("  * tick *");
		notifyObservers(seconds++);
	}
}
